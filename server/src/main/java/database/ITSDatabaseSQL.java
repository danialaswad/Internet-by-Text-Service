package database;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ITSDatabaseSQL {

    // drivers
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/its?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC\n";

    // credentials
    private static final String USER = "its";
    private static final String PASS = "itsservices";

    // control
    private static Connection connection = null;
    private static Statement statement = null;

    public static void main(String[] args){

        try {
            addTwitterToken("daasdsadnial","ASsssdasda","aaaaaaa");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(getTwitterToken("danial").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String> getPage(String telNum, String url) throws SQLException {
        String res =  execute("SELECT PAGES FROM Website WHERE NUMURL = \'" + telNum+url+"\'", "PAGES");
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(res.substring(1, res.length() - 1).split(", ")));
        return myList;
    }

    public static ArrayList<String> getTwitterToken(String twitterID) throws SQLException {
        String token =  execute("SELECT TOKEN FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "TOKEN");
        String secret = execute("SELECT SECRET FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "SECRET");
        ArrayList<String> list = new ArrayList<>();
        list.add(token);
        list.add(secret);
        return list;
    }

    public static String getMaxTweetID(String twitterID) throws SQLException {
        return execute("SELECT MAXTWEET FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "MAXTWEET");
    }

    private static String execute(String query, String var) throws SQLException {
        String result = "";
        connection = getDBConnection();
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()){
            result = rs.getString(var);
        }
        return result;
    }

    public static void addPages(String telNum, String url, ArrayList<String> pages) throws SQLException {
        executer("SELECT count(*) from Website WHERE NUMURL=?",
                telNum+url,
                "UPDATE Website SET PAGES = \'" +pages.toString() +"\' WHERE NUMURL = \'" + telNum+url+"\'",
                "INSERT INTO Website(NUMURL,PAGES) VALUES(\'" + telNum+url+"\',\'" +pages.toString() +"\')");
    }


    public static void addTwitterToken(String twitterID, String twitterToken, String twitterSecret) throws SQLException {
        executer("SELECT count(*) from TweetToken WHERE TWITTERID=?",
                twitterID,
                "UPDATE TweetToken SET TOKEN = \'" +twitterToken +"\' , SECRET = \'"+twitterSecret+"\' WHERE TWITTERID = \'" + twitterID+"\'",
                "INSERT INTO TweetToken(TWITTERID,TOKEN,SECRET) VALUES(\'" + twitterID+"\',\'" +twitterToken +"\',\'" + twitterSecret + "\')");
    }

    public static void addMaxTweet(String twitterID, String maxTweet) throws SQLException {
        executer("SELECT count(*) from TweetToken WHERE TWITTERID=?",
                twitterID,
                "UPDATE TweetToken SET MAXTWEET = \'" +maxTweet +"\' WHERE TWITTERID = \'" + twitterID+"\'",
                "INSERT INTO TweetToken(TWITTERID,MAXTWEET) VALUES(\'" + twitterID+"\',\'" +maxTweet +"\')");
    }

    private static void executer(String check, String verif,String update, String insert) throws SQLException {
        connection = getDBConnection();
        PreparedStatement ps = connection.prepareStatement(check);
        ps.setString(1,verif);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()){
            if (resultSet.getInt("count(*)") > 0){
                statement = connection.createStatement();
                statement.executeUpdate(update);
            }
            else{
                statement = connection.createStatement();
                statement.executeUpdate(insert);
            }
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
