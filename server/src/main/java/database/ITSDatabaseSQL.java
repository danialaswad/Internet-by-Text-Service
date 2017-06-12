package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ITSDatabaseSQL {

    // drivers
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    // credentials
    private static final String USER = "its";
    private static final String PASS = "itsservices";



    public static void main(String[] args){
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting ....");

            connection = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Connection success ...");

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void addUser(String telNum){

    }

    public void addPages(String telNum, String url, ArrayList<String> pages){

    }

    public void addTwitterToken(String telNum, String twitterToken){

    }

    public String getPage(String telNum, String url){
        return "";
    }

    public String getTwitterToken(String telNum){
        return "";
    }

}
