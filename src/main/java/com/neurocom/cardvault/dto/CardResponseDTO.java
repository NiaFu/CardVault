package com.neurocom.cardvault.dto;

import java.time.Instant;

public class CardResponseDTO {
    private String cardholderName;
    private String maskedPan; // **** **** 1234
    private Instant createdAt; // UTC time

    public CardResponseDTO(String cardholderName, String maskedPan, Instant createdAt) {
        this.cardholderName = cardholderName;
        this.maskedPan = maskedPan;
        this.createdAt = createdAt;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
