package core.ms.card.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class CardRequest {

    private Long id;
    private String nome;
    private String conta;
    private String agencia;
    private String bandeira;
    private BigDecimal limiteDisponivel;
    private String nodeID;
    private String status;
}
