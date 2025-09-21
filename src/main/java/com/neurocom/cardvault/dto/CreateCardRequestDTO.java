package com.neurocom.cardvault.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/****
 * create new card
 */
public class CreateCardRequestDTO {
    @NotBlank
    @Size(max = 100)
    private  String cardholderName;
    @NotBlank
    @Pattern(regexp = "\\d{12,19}", message = "PAN must be 12-19 digits")
    private String pan;

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
