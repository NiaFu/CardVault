package com.neurocom.cardvault.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "card_secrets")
public class CardSecret {
    @Id
    private UUID id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "card_id")
    private Card card;
    @Column(name = "pan_ciphertext", nullable = false, length = 4096)
    private String panCiphertext; // secret AES-GCM
    @Column(name = "pan_iv", nullable = false, length = 64)
    private String panIv; // random IV

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getPanCiphertext() {
        return panCiphertext;
    }

    public void setPanCiphertext(String panCiphertext) {
        this.panCiphertext = panCiphertext;
    }

    public String getPanIv() {
        return panIv;
    }

    public void setPanIv(String panIv) {
        this.panIv = panIv;
    }
}
