package pro.belbix.ethparser.web3.layers.blocks.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pro.belbix.ethparser.service.AbiProviderService.MATIC_NETWORK;
import static pro.belbix.ethparser.web3.layers.blocks.parser.EthBlockAssertions.assertContracts;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pro.belbix.ethparser.Application;
import pro.belbix.ethparser.entity.a_layer.EthBlockEntity;
import pro.belbix.ethparser.repositories.a_layer.EthBlockRepository;
import pro.belbix.ethparser.web3.Web3Functions;
import pro.belbix.ethparser.web3.layers.blocks.db.EthBlockDbService;

@SpringBootTest(classes = Application.class)
@ContextConfiguration
public class EthBlockParserMaticTest {

    @Autowired
    private Web3Functions web3Functions;
    @Autowired
    private EthBlockParser ethBlockParser;
    @Autowired
    private EthBlockDbService ethBlockDbService;
    @Autowired
    private EthBlockRepository ethBlockRepository;

    @BeforeEach
    void setUp() {
        ethBlockRepository.deleteAll();
    }

    @Test
    void testBlockParsing_0() {
        EthBlockEntity block = ethBlockParser.parse(
            web3Functions.findBlockByNumber(0, true, MATIC_NETWORK),
            MATIC_NETWORK);
        assertNotNull(block, "EthBlockEntity is null");
        assertAll(
            () -> assertEquals(0, block.getNumber(), "block num"),
            () -> assertEquals(1590824836, block.getTimestamp(), "block timestamp")
        );
        assertNotNull(ethBlockDbService.save(block), "persist result");
    }

    @Test
    void testBlockParsing_19570473() throws IOException, URISyntaxException {
        EthBlockEntity ethBlockEntity = ethBlockParser.parse(
            web3Functions.findBlockByNumber(19570473, true, MATIC_NETWORK),
            MATIC_NETWORK);
        assertContracts(ethBlockEntity, "data/19570473_matic_contracts.txt");

        EthBlockEntity persisted = ethBlockDbService.save(ethBlockEntity);
        assertNotNull(persisted);
        assertContracts(persisted, "data/19570473_matic_contracts.txt");
        assertNull(ethBlockDbService.save(ethBlockEntity));
    }


}
