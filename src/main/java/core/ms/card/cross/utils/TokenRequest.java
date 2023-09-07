package core.ms.card.cross.utils;

import core.ms.card.infra.domain.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "generate-nodeID", url = "http://localhost:8086")
public interface TokenRequest {

    @GetMapping(path = "/api/token")
    Token generateToken();
}
