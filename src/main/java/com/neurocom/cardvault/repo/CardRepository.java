package com.neurocom.cardvault.repo;

import com.neurocom.cardvault.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository <Card, UUID> {
    //save, findById, findAll, deleteById
    List<Card> findByLast4(String last4);
}
