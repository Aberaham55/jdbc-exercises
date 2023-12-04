import com.mysql.cj.jdbc.Driver;
import config.Config;
import dao.MySqlQuotesDAO;
import models.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
