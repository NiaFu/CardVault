package com.neurocom.cardvault.dto;
/**
 * Response DTO for returning card details.
 *
 * Contains only non-sensitive information:
 * - cardholderName: the name of the cardholder
 * - maskedPan: PAN masked for display (e.g., **** **** 1234)
 * - createdAt: record creation time (UTC)
 */

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
