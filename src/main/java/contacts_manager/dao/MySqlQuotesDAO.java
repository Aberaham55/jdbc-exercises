package contacts_manager.dao;

import contacts_manager.models.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlQuotesDAO extends MySQLAlbumsDAO {
    protected Connection connection = null;
    public List<Quote> getQuotes () {

        List<Quote> quotes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM quotes");
            while (resultSet.next()) {
                quotes.add(new Quote(
                        resultSet.getString("author"),
                        resultSet.getString("content")
                ));
            }

        } catch (SQLException sqlx) {
            System.out.println(sqlx.getMessage());
        }

        return quotes;
    }


    public long insertQuotes (){


        return 0;
    }

}
