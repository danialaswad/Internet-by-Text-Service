package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ITSDatabaseSQL class
 *
 * @Author : ITS Team
 **/
public class ITSDatabaseSQL {

    // drivers
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/its?useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC\n";

    // credentials
    private static final String USER = "its";
    private static final String PASS = "itsservices";

    // control
    private static Connection connection = null;
    private static Statement statement = null;

    /**
     * Retrieve a web page from the Database
     * Using the given arguments(telNum and url) the method finds the web page
     * @param telNum
     * @param url
     * @return String
     * @throws SQLException
     */
    public static String getPage(String telNum, String url) throws SQLException {
        return  executer("SELECT PAGES FROM Website WHERE NUMURL = \'" + telNum+url+"\'", "PAGES");
    }

    /**
     * Retrieve the twitter token and twitter secret from the database
     * Using the given twitter id, the method finds token and secret
     * Returns an arraylist containing token and secret
     * @param twitterID
     * @return ArrayList
     * @throws SQLException
     */
    public static ArrayList<String> getTwitterToken(String twitterID) throws SQLException {
        String token =  executer("SELECT TOKEN FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "TOKEN");
        String secret = executer("SELECT SECRET FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "SECRET");
        ArrayList<String> list = new ArrayList<>();
        list.add(token);
        list.add(secret);
        return list;
    }

    /**
     * Get the max tweet id from the given twitter ID
     * @param twitterID
     * @return String
     * @throws SQLException
     */
    public static String getMaxTweetID(String twitterID) throws SQLException {
        return executer("SELECT MAXTWEET FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "MAXTWEET");
    }

    private static String executer(String query, String var) throws SQLException {
        String result = "";
        connection = getDBConnection();
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()){
            result = rs.getString(var);
        }
        return result;
    }

    /**
     * Add the given argument in the database
     * If the given argument is already present in the database, the method will update the element
     * @param telNum
     * @param url
     * @param pages
     * @throws SQLException
     */
    public static void addPages(String telNum, String url, String pages) throws SQLException {
        executer(telNum+url,pages,"SELECT count(*) from Website WHERE NUMURL=?",
                "UPDATE Website SET PAGES = ? WHERE NUMURL = ?",
                "INSERT INTO Website(NUMURL,PAGES) VALUES(?,?)");
    }

    /**
     * Add the given argument in the database
     * If the given argument is already present in the database, the method will update the element
     * @param twitterID
     * @param maxTweet
     * @throws SQLException
     */
    public static void addMaxTweet(String twitterID, String maxTweet) throws SQLException {
        executer(twitterID,maxTweet,"SELECT count(*) from TweetToken WHERE TWITTERID=?",
                "UPDATE TweetToken SET MAXTWEET = ? WHERE TWITTERID = ?",
                "INSERT INTO TweetToken(TWITTERID,MAXTWEET) VALUES(?,?)");
    }

    /**
     * Add the given argument in the database
     * If the given argument is already present in the database, the method will update the element
     * @param twitterID
     * @param twitterToken
     * @param twitterSecret
     * @throws SQLException
     */
    public static void addTwitterToken(String twitterID, String twitterToken, String twitterSecret) throws SQLException {
        executer(twitterID, twitterToken, twitterSecret, "SELECT count(*) from TweetToken WHERE TWITTERID=?",
                "UPDATE TweetToken SET TOKEN = ? , SECRET = ? WHERE TWITTERID = ?",
                "INSERT INTO TweetToken(TWITTERID,TOKEN,SECRET) VALUES(?,?,?)");
    }



    private static void executer(String telURL, String pages,String checkQuery, String updateQuery, String insertQuery) throws SQLException {
        connection = getDBConnection();
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1,telURL);
        ResultSet resultSet = checkStatement.executeQuery();


        PreparedStatement statement;
        if (resultSet.next()){
            if (resultSet.getInt("count(*)")>0){
                statement = connection.prepareStatement(updateQuery);
                statement.setString(1,pages);
                statement.setString(2,telURL);
            }
            else {
                statement = connection.prepareStatement(insertQuery);
                statement.setString(1,telURL);
                statement.setString(2,pages);
            }
            statement.executeUpdate();
        }
    }

    private static void executer(String id, String token, String secret,String checkQuery, String updateQuery, String insertQuery) throws SQLException {
        connection = getDBConnection();
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1,id);
        ResultSet resultSet = checkStatement.executeQuery();
        PreparedStatement statement;
        if (resultSet.next()){
            if (resultSet.getInt("count(*)")>0){
                statement = connection.prepareStatement(updateQuery);
                statement.setString(1,token);
                statement.setString(2,secret);
                statement.setString(3,id);
            }
            else {
                statement = connection.prepareStatement(insertQuery);
                statement.setString(1,id);
                statement.setString(2,token);
                statement.setString(3,secret);

            }
            statement.executeUpdate();
        }
    }


    public static void removePages(String numTel, String url) throws SQLException {
        connection = getDBConnection();
        statement = connection.createStatement();
        String sql = "DELETE FROM WebSite WHERE NUMURL = \'" + numTel + url + "\'";
        statement.executeUpdate(sql);
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(JDBC_DRIVER);
            dbConnection = DriverManager.getConnection(
                    DB_URL, USER,PASS);
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dbConnection;

    }

}
