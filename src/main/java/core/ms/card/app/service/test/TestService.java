package core.ms.card.app.service.test;

import core.ms.card.infra.domain.Test;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TestService {

    public List<Test> test() throws UnknownHostException {

        Test test = Test
                .builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(new Date().toString())
                .address(InetAddress.getLocalHost())
                .build();

        return Collections.singletonList(test);
    }
}
