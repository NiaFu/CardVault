package com.niafu.cardvault.repo;

import com.niafu.cardvault.domain.CardSecret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardSecretRepository extends JpaRepository<CardSecret, UUID> {

}
