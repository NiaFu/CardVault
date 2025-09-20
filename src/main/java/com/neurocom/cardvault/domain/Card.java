package com.neurocom.cardvault.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cards", indexes = {
        @Index(name = "idx_cards_last4", columnList = "last4")
})
public class Card {
    @Id
    @GeneratedValue
    private UUID id;

    private String cardholderName;
    private String last4;
    private Instant createdAt;
    @OneToOne(mappedBy = "card",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private CardSecret secret;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public CardSecret getSecret() {
        return secret;
    }

    public void setSecret(CardSecret secret) {
        this.secret = secret;
        if (secret != null) secret.setCard(this);
    }
}
