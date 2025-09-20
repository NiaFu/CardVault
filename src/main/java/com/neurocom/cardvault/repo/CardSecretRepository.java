package com.neurocom.cardvault.repo;

import com.neurocom.cardvault.domain.CardSecret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardSecretRepository extends JpaRepository<CardSecret, UUID> {

}
