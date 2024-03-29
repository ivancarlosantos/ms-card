package core.ms.card.app.service.query;

import core.ms.card.app.dto.response.CardResponse;
import core.ms.card.cross.utils.ValidationParameter;
import core.ms.card.exceptions.BusinessException;
import core.ms.card.infra.domain.Card;
import core.ms.card.infra.repository.CardRepository;
import core.ms.card.status.CardStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class CardQueryService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ModelMapper mapper;

    public List<CardResponse> findAll(){
        List<Card>cardList= cardRepository.findAll()
                .stream()
                .filter(c->c.getStatus().equals(CardStatus.ATIVO.toString()))
                .toList();

        Type listType = new TypeToken<List<CardResponse>>(){}.getType();
        return mapper.map(cardList, listType);
    }

    public CardResponse findById(String value){
        Long id= ValidationParameter.validate(value);
        Optional<Card> cardId= Optional.of(cardRepository.findById(id).orElseThrow(() -> new BusinessException("Card ID: "+id+" NOT FOUND")));
        return mapper.map(cardId.get(), CardResponse.class);

    }
}
