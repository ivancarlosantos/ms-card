package core.ms.card.infra.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.net.InetAddress;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Test implements Serializable {

    private String uuid;
    private String timestamp;
    private InetAddress address;

}
