package com.niafu.cardvault.dto;
/**
 * Request DTO for creating a new card.
 *
 * Carries the cardholder name and the raw PAN provided by the user.
 * - cardholderName: required, max 100 characters
 * - pan: required, must be 12 digits (validated here, encrypted later)
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCardRequestDTO {
    @NotBlank
    @Size(max = 100)
    private  String cardholderName;
    @NotBlank
    @Pattern(regexp = "\\d{12,19}", message = "PAN must be 12-19 digits")
    private String pan;

    public CreateCardRequestDTO(){}

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
