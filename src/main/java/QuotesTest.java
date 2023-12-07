import contacts_manager.dao.MySqlQuotesDAO;
import contacts_manager.models.Quote;

import java.util.List;

public class QuotesTest {

    public static void main(String[] args) {
        MySqlQuotesDAO quotesDAO = new MySqlQuotesDAO();
        quotesDAO.createConnection();
    List<Quote> quotesFromDb = quotesDAO.getQuotes();
    for (Quote quote: quotesFromDb) {
        System.out.println(quote);
        }
        quotesDAO.closeConnection();
    }
}
