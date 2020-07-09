package org.launchcode.stocks.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


public class Stock {

    private final String symbol;
    private final float price;
    private final String name;

    private Stock(String symbol, String name, float price) {
        this.symbol = symbol;
        this.price = price;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName() + " (" + getSymbol() + ")";
    }


    private static final String urlBase = "http://download.finance.yahoo.com/d/quotes.csv?f=snl1&s=";

    public static Stock lookupStock(String symbol) throws StockLookupException {

        URL url;
        try {
            url = new URL(urlBase + symbol);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new StockLookupException("Problem resolving URL", symbol);
        }
        CSVParser parser;
        CSVRecord stockInfo;
        try {
            parser = CSVParser.parse(url, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            stockInfo = parser.getRecords().get(0);
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new StockLookupException("Problem parsing fetched data", symbol);
        }

        if (stockInfo.get(1).equals("N/A") || stockInfo.get(2).equals("N/A")) {
            throw new StockLookupException("Not a valid stock symbol", symbol);
        }

        return new Stock(stockInfo.get(0), stockInfo.get(1), Float.parseFloat(stockInfo.get(2)));
    }

}