package pro.belbix.ethparser.repositories.v0;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.belbix.ethparser.dto.v0.HardWorkDTO;
import pro.belbix.ethparser.dto.v0.TransferDTO;

public interface TransferRepository extends JpaRepository<TransferDTO, String> {

    @Query("select t from TransferDTO t where "
        + "(lower(t.owner) = lower(:owner) or lower(t.recipient) = lower(:recipient)) "
        + "and t.name = 'FARM'"
        + "and t.blockDate between :from and :to "
        + "and t.network = :network "
        + "order by t.blockDate asc")
    List<TransferDTO> fetchAllByOwnerAndRecipient(
        @Param("owner") String owner,
        @Param("recipient") String recipient,
        @Param("from") long from,
        @Param("to") long to,
        @Param("network") String network
    );

    @Query(nativeQuery = true, value = ""
        + "select  coalesce(buys.buy, 0) - coalesce(sells.sell, 0) sum from "
        + "(select sum(value) buy from transfers "
        + "where block_date <= :before and lower(recipient) = lower(:address) and network = :network) buys "
        + "    left join "
        + "(select sum(value) sell from transfers "
        + "where block_date <= :before and lower(owner) = lower(:address) and network = :network) sells on 1=1")
    Double getBalanceForOwner(
        @Param("address") String address,
        @Param("before") long before,
        @Param("network") String network
    );

    @Query("select t from TransferDTO t where "
        + "t.blockDate > :date "
        + "and t.network = :network "
        + "order by t.blockDate")
    List<TransferDTO> fetchAllFromBlockDate(
        @Param("date") long date,
        @Param("network") String network
    );

    @Query(nativeQuery = true, value = ""
        + "select * from transfers where "
        + "(method_name is null or method_name like '0x%') "
        + "and network = :network "
        + "order by block_date")
    List<TransferDTO> fetchAllWithoutMethods(@Param("network") String network);

    @Query(nativeQuery = true, value = ""
        + "select * from transfers where "
        + "(transfers.price is null or price = 0) "
        + "and network = :network "
        + "order by block_date")
    List<TransferDTO> fetchAllWithoutPrice(@Param("network") String network);

    @Query(nativeQuery = true, value = ""
        + "select * from transfers t where "
        + "(t.profit is null) "
        + "and type in ('PS_EXIT', 'REWARD', 'LP_SELL') "
        + "and network = :network "
        + "order by block_date")
    List<TransferDTO> fetchAllWithoutProfits(@Param("network") String network);

    @Query("select t from TransferDTO t where "
        + "t.tokenAddress is null or t.tokenAddress = ''")
    List<TransferDTO> fetchAllWithoutAddresses();
}
