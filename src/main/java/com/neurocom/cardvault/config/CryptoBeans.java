package com.neurocom.cardvault.config;

import com.neurocom.cardvault.crypto.CryptoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * CryptoService Injection into Spring
 */
@Configuration
public class CryptoBeans {

    @Bean
    public CryptoService cryptoService(SecretKey aesKey) {
        return new CryptoService(aesKey);
    }
}
