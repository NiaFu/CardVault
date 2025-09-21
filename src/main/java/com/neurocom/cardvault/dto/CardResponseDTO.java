package com.neurocom.cardvault.dto;

import java.time.Instant;

public class CardResponseDTO {
    private String cardholderName;
    private String maskedPan; // **** **** 1234
    private Instant createdTime; // UTC time

    public CardResponseDTO(String cardholderName, String maskedPan, Instant createdTime) {
        this.cardholderName = cardholderName;
        this.maskedPan = maskedPan;
        this.createdTime = createdTime;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }
}
