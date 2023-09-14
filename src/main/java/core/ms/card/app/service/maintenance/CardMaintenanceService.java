package core.ms.card.app.service.maintenance;

import core.ms.card.app.dto.request.CardRequest;
import core.ms.card.app.dto.response.CardResponse;
import core.ms.card.cross.utils.TokenRequest;
import core.ms.card.cross.utils.ValidationParameter;
import core.ms.card.exceptions.BusinessException;
import core.ms.card.infra.domain.Card;
import core.ms.card.infra.domain.Token;
import core.ms.card.infra.repository.CardRepository;
import core.ms.card.status.CardStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardMaintenanceService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TokenRequest tokenRequest;

    public CardResponse save(CardRequest cardRequest){
        Token token= tokenRequest.generateToken();
        Card card= Card.builder()
                .nome(cardRequest.getNome())
                .conta(cardRequest.getConta())
                .agencia(cardRequest.getAgencia())
                .bandeira(cardRequest.getBandeira())
                .limiteDisponivel(cardRequest.getLimiteDisponivel())
                .nodeID(token.getToken())
                .status(CardStatus.ATIVO.toString())
                .build();
        cardRepository.save(card);
        return mapper.map(card, CardResponse.class);
    }

    public CardResponse update(String value, CardRequest cardRequest){
        Long id= ValidationParameter.validate(value);
        Card card= cardRepository.findById(id).orElseThrow(()-> new BusinessException("Card "+ id+"Not Found"));

        card.setNome(cardRequest.getNome());
        card.setAgencia(cardRequest.getAgencia());
        card.setBandeira(cardRequest.getBandeira());
        card.setLimiteDisponivel(cardRequest.getLimiteDisponivel());

        cardRepository.save(card);
        return mapper.map(card, CardResponse.class);

    }

    public CardResponse cardStatus(String value, String status){
        Long id = ValidationParameter.validate(value);
        Optional<Card> findCard = Optional
                .ofNullable(cardRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException("Client ID: "+id+" NOT FOUND")));

        if (findCard.get().getStatus().equals(CardStatus.ATIVO.toString())){
            findCard.get().setStatus(status);
            cardRepository.save(findCard.get());
        }

        if (findCard.get().getStatus().equals(CardStatus.INATIVO.toString())){
            findCard.get().setStatus(status);
            cardRepository.save(findCard.get());
        }

        return mapper.map(findCard.get(), CardResponse.class);
    }
}
