package com.niafu.cardvault.web;

/**
 * REST controller for card operations.
 *
 * Exposes endpoints to:
 * - Create a new card (POST /api/cards)
 * - Search cards by last 4 digits (GET /api/cards)
 */

import com.niafu.cardvault.dto.CardResponseDTO;
import com.niafu.cardvault.dto.CreateCardRequestDTO;
import com.niafu.cardvault.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    /**
     * Create a new card record.
     * @param req request containing cardholder name and PAN
     * @return response with masked PAN and created timestamp
     */
    @PostMapping
    public CardResponseDTO create(@Valid @RequestBody CreateCardRequestDTO req){
        return service.create(req);
    }

    /**
     * Search cards by last 4 digits, optionally filtered by cardholder name.
     * @param last4 required, must be 4 digits
     * @param name optional cardholder name filter
     * @return list of matching cards with masked PAN
     */
    @GetMapping
    public List<CardResponseDTO> search(@RequestParam("last4")
                                        @Pattern(regexp = "\\d{4}", message = "last4 must be 4 digits")
                                        String last4,
                                        @RequestParam(value = "name", required = false) String name
    ){
        return service.search(last4, name);
    }


}
