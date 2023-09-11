package core.ms.card.app.controller.query;

import core.ms.card.app.dto.response.CardResponse;
import core.ms.card.app.service.query.CardQueryService;
import core.ms.card.infra.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/domain/card")
public class CardQueryController {

    @Autowired
    CardQueryService cardQueryService;
    @GetMapping
    public ResponseEntity<List<CardResponse>> getllAll(){
        List<CardResponse>listCard= cardQueryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listCard);
    }

    @GetMapping("/{id}")
    public ResponseEntity <CardResponse> findById(@PathVariable String id){
        CardResponse cardResponse= cardQueryService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(cardResponse);
    }
}
