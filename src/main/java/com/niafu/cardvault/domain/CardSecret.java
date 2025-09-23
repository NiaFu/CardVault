package com.niafu.cardvault.domain;
/**
 * CardSecret entity.
 *
 * Stores sensitive card data in encrypted form:
 * - Encrypted PAN (AES-GCM ciphertext)
 * - Initialization Vector (IV) used for encryption
 *
 * Linked one-to-one with Card, sharing the same primary key (card_id).
 */

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "card_secrets")
public class CardSecret {
    @Id
    private UUID id;

    /** Reference to the associated Card  */
    @OneToOne
    @MapsId
    @JoinColumn(name = "card_id")
    private Card card;

    /** Encrypted PAN stored as ciphertext */
    @Column(name = "pan_ciphertext", nullable = false, length = 4096)
    private String panCiphertext; // secret AES-GCM

    /** Random IV used during encryption */
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
