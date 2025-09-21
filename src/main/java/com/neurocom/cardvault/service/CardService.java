package com.neurocom.cardvault.service;

import com.neurocom.cardvault.crypto.CryptoService;
import com.neurocom.cardvault.domain.Card;
import com.neurocom.cardvault.domain.CardSecret;
import com.neurocom.cardvault.dto.CardResponseDTO;
import com.neurocom.cardvault.dto.CreateCardRequestDTO;
import com.neurocom.cardvault.repo.CardRepository;
import com.neurocom.cardvault.util.LuhnUtil;
import com.neurocom.cardvault.util.MaskingUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final CardRepository cardRepo;
    private final CryptoService crypto;

    public CardService(CardRepository cardRepo, CryptoService crypto) {
        this.cardRepo = cardRepo;
        this.crypto = crypto;
    }

    //create new card
    @Transactional
    public CardResponseDTO create(CreateCardRequestDTO req) {
        String pan = req.getPan().trim();
        String name = req.getCardholderName().trim();

        // verify PAN
        if (!LuhnUtil.isValidPan(pan)) {
            throw new IllegalArgumentException("Invalid PAN: Luhn check failed");
        }
        // find last4
        String last4 = pan.substring(pan.length() - 4);

        // encrypt
        var enc = crypto.encrypt(pan);

        //make entity
        Card card = new Card();
        card.setCardholderName(name);
        card.setLast4(last4);

        CardSecret secret = new CardSecret();
        secret.setPanCiphertext(enc.ciphertextB64());
        secret.setPanIv(enc.ivB64());

        card.setSecret(secret);

        // save to db
        Card saved = cardRepo.save(card);
        // response DTO
        return new CardResponseDTO(
                saved.getCardholderName(),
                MaskingUtil.maskFromLast4(saved.getLast4()),
                saved.getCreatedAt()
        );
    }

    @Transactional
    public List<CardResponseDTO> search(String last4, String nameOpt){
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
        )).collect(Collectors.toList());
    }
}
