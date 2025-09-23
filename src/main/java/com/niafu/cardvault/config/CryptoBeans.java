package com.niafu.cardvault.config;
/**
 * CryptoService Injection into Spring
 */

import com.niafu.cardvault.crypto.CryptoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class CryptoBeans {
    @Bean
    public CryptoService cryptoService(SecretKey aesKey) {
        return new CryptoService(aesKey);
    }
}
