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
            addPages("12344","hello.fr","asdasdaff'sdae'a'a''''as'd\"feaf\"aefaefaef'dfafef");
            addMaxTweet("12313ff","123412423423");
            addTwitterToken("1123123","1efdew43r23r","23r2f'2vrv2v\"ewvwev\"wefwfwev");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPage(String telNum, String url) throws SQLException {
        return  executer("SELECT PAGES FROM Website WHERE NUMURL = \'" + telNum+url+"\'", "PAGES");
    }

    public static ArrayList<String> getTwitterToken(String twitterID) throws SQLException {
        String token =  executer("SELECT TOKEN FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "TOKEN");
        String secret = executer("SELECT SECRET FROM TweetToken WHERE TWITTERID = \'" + twitterID + "\'", "SECRET");
        ArrayList<String> list = new ArrayList<>();
        list.add(token);
        list.add(secret);
        return list;
    }

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

    public static void addPages(String telNum, String url, String pages) throws SQLException {
        executer(telNum+url,pages,"SELECT count(*) from Website WHERE NUMURL=?",
                "UPDATE Website SET PAGES = ? WHERE NUMURL = ?",
                "INSERT INTO Website(NUMURL,PAGES) VALUES(?,?)");
    }
    public static void addMaxTweet(String twitterID, String maxTweet) throws SQLException {
        executer(twitterID,maxTweet,"SELECT count(*) from TweetToken WHERE TWITTERID=?",
                "UPDATE TweetToken SET MAXTWEET = ? WHERE TWITTERID = ?",
                "INSERT INTO TweetToken(TWITTERID,MAXTWEET) VALUES(?,?)");
    }

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

        if (resultSet.next()){
            if (resultSet.getInt("count(*)")>0){
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1,pages);
                updateStatement.setString(2,telURL);
                updateStatement.executeUpdate();
            }
            else {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1,telURL);
                insertStatement.setString(2,pages);
                insertStatement.executeUpdate();
            }
        }
    }

    private static void executer(String id, String token, String secret,String checkQuery, String updateQuery, String insertQuery) throws SQLException {
        connection = getDBConnection();
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1,id);
        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next()){
            if (resultSet.getInt("count(*)")>0){
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1,token);
                updateStatement.setString(2,secret);
                updateStatement.setString(3,id);
                updateStatement.executeUpdate();
            }
            else {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1,id);
                insertStatement.setString(2,token);
                insertStatement.setString(3,secret);
                insertStatement.executeUpdate();
            }
        }
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
