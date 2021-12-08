package pro.belbix.ethparser.web3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pro.belbix.ethparser.service.AbiProviderService.BSC_NETWORK;
import static pro.belbix.ethparser.service.AbiProviderService.ETH_NETWORK;
import static pro.belbix.ethparser.service.AbiProviderService.MATIC_NETWORK;
import static pro.belbix.ethparser.web3.abi.FunctionsNames.PROFITSHARING_NUMERATOR;
import static pro.belbix.ethparser.web3.abi.FunctionsNames.UNDERLYING_UNIT;
import static pro.belbix.ethparser.web3.abi.FunctionsNames.NAME;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pro.belbix.ethparser.Application;
import pro.belbix.ethparser.web3.abi.FunctionsUtils;

@SpringBootTest(classes = Application.class)
@ContextConfiguration
public class FunctionsUtilsTest {

  @Autowired
  private FunctionsUtils functionsUtils;

  @Test
  public void testRewardToken() {
    String result = functionsUtils.callAddressByName(
        "rewardToken", "0xa81363950847aC250A2165D9Fb2513cA0895E786", 11616080L, ETH_NETWORK)
        .orElseThrow();
    assertNotNull(result, "reward token ");
  }

  @Test
  public void testprofitSharingDenominator() {
    double result = functionsUtils.callIntByName(
        PROFITSHARING_NUMERATOR, "0x9e315822a18f8d332782d1c3f3f24bb10d2161ad", 12086139L, ETH_NETWORK)
        .orElse(BigInteger.ZERO).doubleValue();
    assertEquals(30.0, result, "PROFITSHARING_NUMERATOR");
  }

  @Test
  public void testBoolByName() {
    Boolean result =
        functionsUtils.callBoolByName(
            "liquidateRewardToWethInSushi", "0x636A37802dA562F7d562c1915cC2A948A1D3E5A0", 11694023L,
            ETH_NETWORK)
            .orElse(null);
    assertEquals(true, result, "liquidateRewardToWethInSushi");
  }

  @Test
  public void testUNDERLYING_UNIT_PC_CAKE() {
    assertEquals(1000000000000000000L,
        functionsUtils.callIntByName(
            UNDERLYING_UNIT, "0x3D5B0a8CD80e2A87953525fC136c33112E4b885a", null, BSC_NETWORK)
            .orElse(BigInteger.ZERO).longValue(),
        "test " + UNDERLYING_UNIT);
  }

  @Test
  public void testNAME_POOL() {
    assertEquals("pbfSLP",
        functionsUtils.callStrByName(
            NAME, "0xb25e2c1efdd4b79cd5d63c0f5a45326fa4ca2139", null, MATIC_NETWORK)
            .orElse(""),
        "test " + NAME);
  }
}
