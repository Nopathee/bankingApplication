package BankingApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankingConnection {
    public static Connection connect(){
        String URL = "jdbc:mysql://127.0.0.1:3306/mydb1";
        String username = "root" ;
        String password = "Moofat123";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(BankingConnection.class.getName()).log(Level.SEVERE,null,e);
        }

        return connection;
    }
}
