package core.ms.card;

import core.ms.card.cross.utils.ValidationParameter;
import core.ms.card.exceptions.BusinessException;
import core.ms.card.infra.domain.Card;
import core.ms.card.infra.repository.CardRepository;
import core.ms.card.status.CardStatus;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardConfigurationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private CardRepository cardRepository;

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:11"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

        log.info("url {}", container.getJdbcUrl());
        log.info("username {}", container.getUsername());
        log.info("password {}", container.getPassword());
        log.info("spring.datasource.driver-class-name {}", container.getJdbcDriverInstance());
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        cardRepository.deleteAll();
    }

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void rateCard() {

        Long id = 1L;
        String nome = "Card Name";
        String conta = "00000000-00";
        String agencia = "00000000-00";
        String bandeira = "BANDEIRA";
        BigDecimal limiteDisponivel = BigDecimal.valueOf(1000000);
        String nodeID = UUID.randomUUID().toString();
        String status = CardStatus.ATIVO.toString();

        Card card = new Card(id, nome, conta, agencia, bandeira, limiteDisponivel, nodeID, status);

        cardRepository.save(card);

        List<Card> accounts = cardRepository.findAll();
        assertEquals(1, accounts.size());
        assertEquals("Card Name", accounts.get(0).getNome());
    }

    @Test
    void testBuilderCard(){
        Card c = Card.builder()
                .id(1L)
                .nome("Fulano")
                .conta("00000000-00")
                .agencia("00000000-00")
                .bandeira("BANDEIRA")
                .limiteDisponivel(BigDecimal.valueOf(10000))
                .nodeID("5af81cc7-06ca-4073-ab02-bdd4bec2d5dc")
                .status(CardStatus.ATIVO.toString())
                .build();
        cardRepository.save(c);
        Assertions.assertInstanceOf(Card.class, c);
    }

    @Test
    void testNameCard() {
        Card c = new Card();
        c.setNome("Fulano");
        cardRepository.save(c);
        Assertions.assertEquals("Fulano", c.getNome());
    }

    @Test
    void testNameCardNull() {
        Card c = new Card();
        c.setNome(null);
        cardRepository.save(c);
        Assertions.assertNull(c.getNome());
    }

    @Test
    void testNodeID(){
        Card c = new Card();
        c.setNodeID("5af81cc7-06ca-4073-ab02-bdd4bec2d5dc");
        cardRepository.save(c);
        Assertions.assertEquals("5af81cc7-06ca-4073-ab02-bdd4bec2d5dc", c.getNodeID());
    }

    @Test
    void testCardStatusATIVO() {
        Card c = new Card();
        c.setStatus(CardStatus.ATIVO.toString());
        cardRepository.save(c);
        Assertions.assertEquals(CardStatus.ATIVO.toString(), c.getStatus());
    }

    @Test
    void testCardStatusINATIVO() {
        Card c = new Card();
        c.setStatus(CardStatus.INATIVO.toString());
        cardRepository.save(c);
        Assertions.assertEquals(CardStatus.INATIVO.toString(), c.getStatus());
    }

    @Test
    void testValidationParameter(){
        Long id = ValidationParameter.validate("1");
        Card c = new Card();
        c.setId(id);
        cardRepository.save(c);
        Assertions.assertEquals(id, c.getId());
    }

    @Test
    void testValidationParameterInvalid(){
        Long id = ValidationParameter.validate("1");
        Card c = new Card();
        c.setId(id);
        cardRepository.save(c);
        Assertions.assertNotEquals("A", c.getId());
    }
}
