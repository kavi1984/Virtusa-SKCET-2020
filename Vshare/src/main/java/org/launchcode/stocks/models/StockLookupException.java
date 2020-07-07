package org.launchcode.stocks.models;


@SuppressWarnings("serial")
public class StockLookupException extends Exception {

    private String symbol;

    public StockLookupException(){}

    public StockLookupException(String message, String symbol) {
        super(message);
    }

    public StockLookupException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Unable to lookup data for stock: " + symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
