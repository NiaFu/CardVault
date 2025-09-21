package com.neurocom.cardvault.repo;

import com.neurocom.cardvault.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository <Card, UUID> {
    //save, findById, findAll, deleteById

    /***
     * search by last4
     * @param last4
     * @return
     */
    List<Card> findByLast4(String last4);

    /***
     * search by last4 and cardholder
     * @param last4
     * @param cardholderName
     * @return
     */
    List<Card> findByLast4AndCardholderName(String last4, String cardholderName);


}
