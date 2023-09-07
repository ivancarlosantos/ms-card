package core.ms.card.cross.assembler;

import core.ms.card.app.dto.request.CardRequest;
import core.ms.card.app.dto.response.CardResponse;
import core.ms.card.infra.domain.Card;

public interface CardMapper {

    Card toEntity(CardRequest request);

    CardResponse toResponse(Card card);
}
