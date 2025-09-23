package com.niafu.cardvault.repo;
/**
 * Repository for Card entities.
 *
 * Provides methods to search cards by last 4 digits and by cardholder name.
 */

import com.niafu.cardvault.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository <Card, UUID> {
    //save, findById, findAll, deleteById

    /***
     * Find cards by last 4 digits.
     * @param last4
     * @return
     */
    List<Card> findByLast4(String last4);

    /***
     * Find cards by last 4 digits and cardholder name.
     * @param last4
     * @param cardholderName
     * @return
     */
    List<Card> findByLast4AndCardholderName(String last4, String cardholderName);


}
