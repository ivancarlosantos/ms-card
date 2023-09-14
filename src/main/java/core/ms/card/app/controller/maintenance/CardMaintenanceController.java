package core.ms.card.app.controller.maintenance;

import core.ms.card.app.dto.request.CardRequest;
import core.ms.card.app.dto.response.CardResponse;
import core.ms.card.app.service.maintenance.CardMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/domain/card")
public class CardMaintenanceController {
    @Autowired
    private CardMaintenanceService cardMaintenanceService;

    @PostMapping
    public ResponseEntity<CardResponse> save(@RequestBody CardRequest cardRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardMaintenanceService.save(cardRequest));
}


    @PutMapping("/update/{id}")
    public  ResponseEntity<CardResponse> upate(@PathVariable String id, @RequestBody CardRequest cardRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardMaintenanceService.update(id, cardRequest));
}
    @PutMapping("/delete/{id}/{status}")
    public ResponseEntity<CardResponse>delete(@PathVariable String id, @PathVariable String status){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardMaintenanceService.cardStatus(id, status));
    }



}
