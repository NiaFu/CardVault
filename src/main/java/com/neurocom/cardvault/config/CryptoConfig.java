package com.neurocom.cardvault.config;
/**
 * Read the AES Key from the environment variable and register it to the Spring container.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class CryptoConfig {

    @Bean
    public SecretKey aesKey() {
        String b64 = System.getenv("CARD_AES_KEY");
        if (b64 == null || b64.isBlank()){
            throw new IllegalStateException("CARD_AES_KEY env var is required");
        }

        byte[] keyBytes = Base64.getDecoder().decode(b64);
        if (!(keyBytes.length == 16 || keyBytes.length == 24 || keyBytes.length == 32)) {
            throw new IllegalStateException("CARD_AES_KEY length must be 16, 24 or 32 bytes after Base64 decoding.");
        }

        return new SecretKeySpec(keyBytes, "AES");
    }
}
