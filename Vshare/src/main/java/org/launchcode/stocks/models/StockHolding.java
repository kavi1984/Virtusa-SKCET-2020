package org.launchcode.stocks.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "stock_holdings")
public class StockHolding extends AbstractEntity {

    private String symbol;
    private int sharesOwned;
    private int ownerId;
    private List<StockTransaction> transactions;
    private StockHolding() {}

    private StockHolding(String symbol, int ownerId) {
        this.symbol = symbol.toUpperCase();
        this.sharesOwned = 0;
        this.ownerId = ownerId;
        transactions = new ArrayList<StockTransaction>();
    }

    @NotNull
    @Column(name = "owner_id")
    public int getOwnerId(){
        return ownerId;
    }

    protected void setOwnerId(int ownerId){
        this.ownerId = ownerId;
    }

    @NotNull
    @Column(name = "symbol")
    public String getSymbol() {
        return symbol;
    }

    protected void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @NotNull
    @Column(name = "shares_owned")
    public int getSharesOwned() {
        return sharesOwned;
    }

    protected void setSharesOwned(int sharesOwned) {
        this.sharesOwned = sharesOwned;
    }

    @OneToMany(mappedBy = "stockHolding", cascade = CascadeType.ALL)
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    protected void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }
    private void buyShares(int numberOfShares) throws StockLookupException {

        if (numberOfShares < 0) {
            throw new IllegalArgumentException("Can not purchase a negative number of shares.");
        }

        setSharesOwned(sharesOwned + numberOfShares);

        StockTransaction transaction = new StockTransaction(this, numberOfShares, StockTransaction.TransactionType.BUY);
        this.transactions.add(transaction);
    }

    private void sellShares(int numberOfShares) throws StockLookupException {

        if (numberOfShares > sharesOwned) {
            throw new IllegalArgumentException("Number to sell exceeds shares owned for stock" + symbol);
        }

        setSharesOwned(sharesOwned - numberOfShares);

        StockTransaction transaction = new StockTransaction(this, numberOfShares, StockTransaction.TransactionType.SELL);
        this.transactions.add(transaction);
    }

    
    public static StockHolding buyShares(User user, String symbol, int numberOfShares) throws StockLookupException {
        Map<String, StockHolding> userPortfolio = user.getPortfolio();
        StockHolding holding;
        if (!userPortfolio.containsKey(symbol)) {
            holding = new StockHolding(symbol, user.getUid());
            user.addHolding(holding);
        }

        holding = userPortfolio.get(symbol);
        holding.buyShares(numberOfShares);

        return holding;
    }

    
    public static StockHolding sellShares(User user, String symbol, int numberOfShares) throws StockLookupException {

        Map<String, StockHolding> userPortfolio = user.getPortfolio();
        StockHolding holding;

        if (!userPortfolio.containsKey(symbol)) {
            return null;
        }
        holding = userPortfolio.get(symbol);
        holding.sellShares(numberOfShares);
        return holding;
    }
}
