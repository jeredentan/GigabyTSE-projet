package interface_basededonnee;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

public class API_YahooFinance {



	public static String recuperernom(String identreprise) throws IOException{

		Stock stocksymbole=YahooFinance.get(identreprise);
		String nomentreprise=stocksymbole.getName();
		return nomentreprise;
	}

	public List<HistoricalQuote> historique(String identreprise) throws IOException{
		Stock google = YahooFinance.get(identreprise);
		List<HistoricalQuote> googleHistQuotes = google.getHistory();
		return googleHistQuotes;
	}


	public static Double valeuraction(String identreprise) throws IOException{
		Stock stock = YahooFinance.get(identreprise);
		BigDecimal price = stock.getQuote().getPrice();
		double pricedouble = price.doubleValue();

		return pricedouble;
	}

}
