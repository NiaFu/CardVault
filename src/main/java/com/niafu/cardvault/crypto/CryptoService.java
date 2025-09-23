package com.niafu.cardvault.crypto;
/**
 * Provides AES-GCM encryption and decryption for PAN values.
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoService {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128; // Length of the certification label
    private static final int IV_LENGTH = 12;     // 12-byte IV

    private final SecretKey key;
    private final SecureRandom random = new SecureRandom();

    public CryptoService(SecretKey key) {
        this.key = key;
    }

    /** Result container for ciphertext + IV (both Base64-encoded). */
    public static record EncResult(String ciphertextB64, String ivB64) {}

    /**
     * Encrypt plaintext PAN and return ciphertext + IV.
     * @param plaintext
     * @return
     */
    public EncResult encrypt(String plaintext) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            random.nextBytes(iv); // random IV

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] ct = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            return new EncResult(
                    Base64.getEncoder().encodeToString(ct),
                    Base64.getEncoder().encodeToString(iv)
            );
        } catch (Exception e) {
            throw new IllegalStateException("Encryption failed", e);
        }
    }

    /**
     * Decrypt ciphertext + IV and return the original PAN.
     * @param ciphertextB64
     * @param ivB64
     * @return
     */
    public String decrypt(String ciphertextB64, String ivB64) {
        try {
            byte[] ct = Base64.getDecoder().decode(ciphertextB64);
            byte[] iv = Base64.getDecoder().decode(ivB64);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] pt = cipher.doFinal(ct);

            return new String(pt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Decryption failed", e);
        }
    }

}
