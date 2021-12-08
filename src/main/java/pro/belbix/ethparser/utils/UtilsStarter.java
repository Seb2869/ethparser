package pro.belbix.ethparser.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pro.belbix.ethparser.properties.AppProperties;
import pro.belbix.ethparser.utils.download.BancorDownloader;
import pro.belbix.ethparser.utils.download.DeployerTransactionsDownloader;
import pro.belbix.ethparser.utils.download.EthBlockDownloader;
import pro.belbix.ethparser.utils.download.HardWorkDownloader;
import pro.belbix.ethparser.utils.download.NewStrategyDownloader;
import pro.belbix.ethparser.utils.download.PriceDownloader;
import pro.belbix.ethparser.utils.download.RewardDownloader;
import pro.belbix.ethparser.utils.download.TransferDownloader;
import pro.belbix.ethparser.utils.download.UniswapLpDownloader;
import pro.belbix.ethparser.utils.download.VaultActionsDownloader;
import pro.belbix.ethparser.utils.recalculation.AddressFiller;
import pro.belbix.ethparser.utils.recalculation.ContractUpdater;
import pro.belbix.ethparser.utils.recalculation.DeployerRecalculation;
import pro.belbix.ethparser.utils.recalculation.HardWorkRecalculate;
import pro.belbix.ethparser.utils.recalculation.HarvestProfitRecalculate;
import pro.belbix.ethparser.utils.recalculation.LpTvlRecalculate;
import pro.belbix.ethparser.utils.recalculation.MigrationRecalculate;
import pro.belbix.ethparser.utils.recalculation.OwnerBalanceRecalculate;
import pro.belbix.ethparser.utils.recalculation.OwnerCountRecalculate;
import pro.belbix.ethparser.utils.recalculation.RewardRecalculate;
import pro.belbix.ethparser.utils.recalculation.TransfersRecalculate;
import pro.belbix.ethparser.utils.recalculation.TvlRecalculate;

@Service
@Log4j2
public class UtilsStarter {

  private final AppProperties appProperties;
  private final UniswapLpDownloader uniswapLpDownloader;
  private final VaultActionsDownloader vaultActionsDownloader;
  private final TvlRecalculate tvlRecalculate;
  private final HardWorkDownloader hardWorkDownloader;
  private final HardWorkRecalculate hardWorkRecalculate;
  private final RewardDownloader rewardDownloader;
  private final LpTvlRecalculate lpTvlRecalculate;
  private final OwnerBalanceRecalculate ownerBalanceRecalculate;
  private final OwnerCountRecalculate ownerCountRecalculate;
  private final MigrationRecalculate migrationRecalculate;
  private final TransferDownloader transferDownloader;
  private final TransfersRecalculate transfersRecalculate;
  private final RewardRecalculate rewardRecalculate;
  private final NewStrategyDownloader newStrategyDownloader;
  private final PriceDownloader priceDownloader;
  private final HarvestProfitRecalculate harvestProfitRecalculate;
  private final DeployerTransactionsDownloader deployerTransactionsDownloader;
  private final EthBlockDownloader ethBlockDownloader;
  private final AddressFiller addressFiller;
  private final DeployerRecalculation deployerRecalculation;
  private final ContractUpdater contractUpdater;
  private final BancorDownloader bancorDownloader;

  public UtilsStarter(AppProperties appProperties,
      UniswapLpDownloader uniswapLpDownloader,
      VaultActionsDownloader vaultActionsDownloader,
      TvlRecalculate tvlRecalculate,
      HardWorkDownloader hardWorkDownloader,
      HardWorkRecalculate hardWorkRecalculate,
      RewardDownloader rewardDownloader,
      LpTvlRecalculate lpTvlRecalculate,
      OwnerBalanceRecalculate ownerBalanceRecalculate,
      OwnerCountRecalculate ownerCountRecalculate,
      MigrationRecalculate migrationRecalculate,
      TransferDownloader transferDownloader,
      TransfersRecalculate transfersRecalculate,
      RewardRecalculate rewardRecalculate,
      NewStrategyDownloader newStrategyDownloader,
      HarvestProfitRecalculate harvestProfitRecalculate,
      PriceDownloader priceDownloader,
      DeployerTransactionsDownloader deployerTransactionsDownloader,
      EthBlockDownloader ethBlockDownloader,
      AddressFiller addressFiller,
      DeployerRecalculation deployerRecalculation,
      ContractUpdater contractUpdater,
      BancorDownloader bancorDownloader) {
    this.appProperties = appProperties;
    this.uniswapLpDownloader = uniswapLpDownloader;
    this.vaultActionsDownloader = vaultActionsDownloader;
    this.tvlRecalculate = tvlRecalculate;
    this.hardWorkDownloader = hardWorkDownloader;
    this.hardWorkRecalculate = hardWorkRecalculate;
    this.rewardDownloader = rewardDownloader;
    this.lpTvlRecalculate = lpTvlRecalculate;
    this.ownerBalanceRecalculate = ownerBalanceRecalculate;
    this.ownerCountRecalculate = ownerCountRecalculate;
    this.migrationRecalculate = migrationRecalculate;
    this.transferDownloader = transferDownloader;
    this.transfersRecalculate = transfersRecalculate;
    this.rewardRecalculate = rewardRecalculate;
    this.newStrategyDownloader = newStrategyDownloader;
    this.harvestProfitRecalculate = harvestProfitRecalculate;
    this.priceDownloader = priceDownloader;
    this.deployerTransactionsDownloader = deployerTransactionsDownloader;
    this.ethBlockDownloader = ethBlockDownloader;
    this.addressFiller = addressFiller;
    this.deployerRecalculation = deployerRecalculation;
    this.contractUpdater = contractUpdater;
    this.bancorDownloader = bancorDownloader;
  }

  public void startUtils() {
    log.info("Start utils {}", appProperties.getStartUtil());
    if ("uniswap-download".equals(appProperties.getStartUtil())) {
      uniswapLpDownloader.start();
    } else if ("harvest-download".equals(appProperties.getStartUtil())) {
      vaultActionsDownloader.start();
    } else if ("tvl-recalculate".equals(appProperties.getStartUtil())) {
      tvlRecalculate.start();
    } else if ("hardwork-download".equals(appProperties.getStartUtil())) {
      hardWorkDownloader.start();
    } else if ("hardwork-recalculate".equals(appProperties.getStartUtil())) {
      hardWorkRecalculate.start();
    } else if ("reward-download".equals(appProperties.getStartUtil())) {
      rewardDownloader.start();
    } else if ("lp-tvl-recalculate".equals(appProperties.getStartUtil())) {
      lpTvlRecalculate.start();
    } else if ("balances-recalculate".equals(appProperties.getStartUtil())) {
      ownerBalanceRecalculate.start();
    } else if ("owners-count-recalculate".equals(appProperties.getStartUtil())) {
      ownerCountRecalculate.start();
    } else if ("migration-recalculate".equals(appProperties.getStartUtil())) {
      migrationRecalculate.start();
    } else if ("rewards-recalculate".equals(appProperties.getStartUtil())) {
      rewardRecalculate.start();
    } else if ("transfer-download".equals(appProperties.getStartUtil())) {
      transferDownloader.start();
    } else if ("transfer-recalculate".equals(appProperties.getStartUtil())) {
      transfersRecalculate.start();
    } else if ("new-strategy-download".equals(appProperties.getStartUtil())) {
      newStrategyDownloader.start();
    } else if ("price-download".equals(appProperties.getStartUtil())) {
      priceDownloader.start();
    } else if ("profit-recalculate".equals(appProperties.getStartUtil())) {
      harvestProfitRecalculate.start();
    } else if ("deployer-download".equals(appProperties.getStartUtil())) {
      deployerTransactionsDownloader.start();
    } else if ("block-download".equals(appProperties.getStartUtil())) {
      ethBlockDownloader.start();
    } else if ("address-filler".equals(appProperties.getStartUtil())) {
      addressFiller.start();
    }else if ("deployer-recalculation".equals(appProperties.getStartUtil())) {
      deployerRecalculation.start();
    }else if ("contract-updater".equals(appProperties.getStartUtil())) {
      contractUpdater.start();
    }else if ("bancor-download".equals(appProperties.getStartUtil())) {
      bancorDownloader.start();
    }
    log.info("Utils completed");
    System.exit(0);
  }


}
