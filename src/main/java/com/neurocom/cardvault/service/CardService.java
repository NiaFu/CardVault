package com.neurocom.cardvault.service;
/**
 * Service layer for managing cards.
 *
 * Handles:
 * - Creating new cards (encrypts PAN and stores last 4 digits)
 * - Searching cards by last 4 digits (optionally filtered by name)
 *
 * Sensitive data is encrypted via CryptoService entity and masked in responses.
 */

import com.neurocom.cardvault.crypto.CryptoService;
import com.neurocom.cardvault.domain.Card;
import com.neurocom.cardvault.domain.CardSecret;
import com.neurocom.cardvault.dto.CardResponseDTO;
import com.neurocom.cardvault.dto.CreateCardRequestDTO;
import com.neurocom.cardvault.repo.CardRepository;
import com.neurocom.cardvault.util.LuhnUtil;
import com.neurocom.cardvault.util.MaskingUtil;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CardService {
    private final CardRepository cardRepo;
    private final CryptoService crypto;

    public CardService(CardRepository cardRepo, CryptoService crypto) {
        this.cardRepo = cardRepo;
        this.crypto = crypto;
    }

    /**
     * Create and save a new card.
     * @param req  request containing cardholder name and raw PAN
     * @return response with masked PAN and created timestamp
     */
    //create new card
    @Transactional
    public CardResponseDTO create(CreateCardRequestDTO req) {
        String name = req.getCardholderName() == null ? "" : req.getCardholderName().trim();
        String pan = req.getPan();
        if (pan == null) {
            throw new IllegalArgumentException("PAN is required");
        }

        // Standardize: keep only digits
        pan = pan.replaceAll("\\D", "");

        // Validate length
        if (!pan.matches("\\d{12}")) {
            throw new IllegalArgumentException("PAN must be exactly 12 digits");
        }

        // // Extract last 4
        String last4 = pan.substring(pan.length() - 4);

        // encrypt
        var enc = crypto.encrypt(pan);

        // Build entity
        Card card = new Card();
        card.setCardholderName(name);
        card.setLast4(last4);

        CardSecret secret = new CardSecret();
        secret.setPanCiphertext(enc.ciphertextB64());
        secret.setPanIv(enc.ivB64());

        card.setSecret(secret);

        // save
        log.info("before save createdAt={}", card.getCreatedAt());
        Card saved = cardRepo.saveAndFlush(card);
        log.info("after  save createdAt={}", saved.getCreatedAt());
        // response masked response
        return new CardResponseDTO(
                saved.getCardholderName(),
                MaskingUtil.maskFromLast4(saved.getLast4()),
                saved.getCreatedAt()
        );
    }

    /**
     * Search cards by last 4 digits, optionally filtered by cardholder name.
     * @param last4 last 4 digits of the PAN
     * @param nameOpt optional cardholder name filter
     * @return list of matching cards with masked PAN
     */
    @Transactional
    public List<CardResponseDTO> search(String last4, @Nullable String nameOpt){
        List<Card> results;
        if (nameOpt == null || nameOpt.isBlank()) {
            results = cardRepo.findByLast4(last4);
        } else {
            results = cardRepo.findByLast4AndCardholderName(last4, nameOpt);
        }
        return results.stream().map(c -> new CardResponseDTO(
                c.getCardholderName(),
                MaskingUtil.maskFromLast4(c.getLast4()),
                c.getCreatedAt()
        )).toList();
    }
}
