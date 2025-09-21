package com.neurocom.cardvault.service;

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

    //create new card
    @Transactional
    public CardResponseDTO create(CreateCardRequestDTO req) {
        String name = req.getCardholderName() == null ? "" : req.getCardholderName().trim();
        String pan = req.getPan();
        if (pan == null) {
            throw new IllegalArgumentException("PAN is required");
        }

        // ✅ 统一标准化：删除所有“非数字”字符（空格、短横线、全角空格等统统去掉）
        pan = pan.replaceAll("\\D", "");

        // ✅ 只检查固定长度 12 位
        if (!pan.matches("\\d{12}")) {
            throw new IllegalArgumentException("PAN must be exactly 12 digits");
        }

//        // ✅ Luhn
//        if (!LuhnUtil.isValidPan(pan)) {
//            throw new IllegalArgumentException("Invalid PAN: Luhn check failed");
//        }

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
        log.info("before save createdAt={}", card.getCreatedAt());
        Card saved = cardRepo.saveAndFlush(card);
        log.info("after  save createdAt={}", saved.getCreatedAt());
        // response DTO
        return new CardResponseDTO(
                saved.getCardholderName(),
                MaskingUtil.maskFromLast4(saved.getLast4()),
                saved.getCreatedAt()
        );
    }

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
