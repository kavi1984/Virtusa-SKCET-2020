package org.launchcode.stocks.models.dao;

import org.launchcode.stocks.models.StockTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public interface StockTransactionDao extends CrudRepository<StockTransaction, Integer> {

    List<StockTransaction> findBySymbolAndUid(String symbol, int uid);

}
