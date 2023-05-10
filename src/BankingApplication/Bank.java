package BankingApplication;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {
    private String name;

    public Bank(){

    }

    public Bank(String name){
        this.name = name;
    }
    public void listAccounts(){
        Connection connection = BankingConnection.connect();
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "SELECT *  FROM account";
            ResultSet results = statement.executeQuery(sql);

            while (results.next()){
                System.out.println(results.getInt(1) +" "+ results.getString(2)
                        + " "+results.getDouble(3));
            }
        } catch (SQLException e) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE,null,e);
        }

    }

    public void openAccount(Account account){
        Connection connection = BankingConnection.connect();
        String sql = "INSERT INTO account (accNumber ,accName, accBalance ) VALUES( ? , ? , ? )";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , account.getNumber());
            preparedStatement.setString(2 , account.getName());
            preparedStatement.setDouble(3 , account.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE,null,e);
        }

    }

    public void closeAccount(int number){
        Connection connection = BankingConnection.connect();
        String sql = "DELETE FROM account WHERE accNumber = ? ";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public void depositMoney(int number , double amount){
        Account account = getAccount(number);
        account.deposit(amount);
        Connection connection = BankingConnection.connect();
        String sql = "UPDATE account SET accBalance = ? WHERE accNumber = ? ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1 , account.getBalance());
            preparedStatement.setInt(2 , account.getNumber());
            System.out.println("Balance: "+account.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE,null,e);
        }

    }

    public void withdrawMoney(int number , double amount){
        Account account = getAccount(number);
        account.withdraw(amount);
        System.out.println("Balance : " + account.getBalance());
    }

    public Account getAccount(int number){
        Connection connection = BankingConnection.connect();
        String sql = "SELECT * FROM account WHERE accNumber = ? ";
        PreparedStatement preparedStatement;
        Account account = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , number);
            ResultSet results = preparedStatement.executeQuery();

            results.next();
            String accName = results.getString(2);
            double balance = results.getDouble(3);
            account = new Account(number , accName , balance);


        } catch (SQLException e) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE,null,e);
        }



        return account;
    }


    public void saveAccount(Account account){

    }



}
