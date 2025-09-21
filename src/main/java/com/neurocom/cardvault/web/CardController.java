package com.neurocom.cardvault.web;

import com.neurocom.cardvault.domain.Card;
import com.neurocom.cardvault.dto.CardResponseDTO;
import com.neurocom.cardvault.dto.CreateCardRequestDTO;
import com.neurocom.cardvault.service.CardService;
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

    //create new card
    @PostMapping
    public CardResponseDTO create(@Valid @RequestBody CreateCardRequestDTO req){
        return service.create(req);
    }

    @GetMapping
    public List<CardResponseDTO> search(@RequestParam("last4")
                                        @Pattern(regexp = "\\d{4}", message = "last4 must be 4 digits")
                                        String last4,
                                        @RequestParam(value = "name", required = false) String name
    ){
        return service.search(last4, name);
    }


}
