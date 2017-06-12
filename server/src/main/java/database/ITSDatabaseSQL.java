package database;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<String> p = new ArrayList<>();
        p.add("eafae");p.add("asd");p.add("asf");
        try {
            addTwitterToken("danial","newer");
            addPages("asda","asdasd",p);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(getTwitterToken("danial"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(getPage("asda","asdasd").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<String> getPage(String telNum, String url) throws SQLException {
        String res =  execute("SELECT PAGES FROM Website WHERE NUMURL = \'" + telNum+url+"\'", "PAGES");
        List<String> myList = new ArrayList<>(Arrays.asList(res.substring(1, res.length() - 1).split(", ")));
        return myList;
    }

    public static String getTwitterToken(String numTel) throws SQLException {
        return execute("SELECT TOKEN FROM TweetToken WHERE NUMTEL = \'" + numTel + "\'", "TOKEN");
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

    public static void addPages(String telNum, String url, List<String> pages) throws SQLException {
        executer("SELECT count(*) from Website WHERE NUMURL=?",
                telNum+url,
                "UPDATE Website SET PAGES = \'" +pages.toString() +"\' WHERE NUMURL = \'" + telNum+url+"\'",
                "INSERT INTO Website(NUMURL,PAGES) VALUES(\'" + telNum+url+"\',\'" +pages.toString() +"\')");
    }


    public static void addTwitterToken(String telNum, String twitterToken) throws SQLException {
        executer("SELECT count(*) from TweetToken WHERE NUMTEL=?",
                telNum,
                "UPDATE TweetToken SET TOKEN = \'" +twitterToken +"\' WHERE NUMTEL = \'" + telNum+"\'",
                "INSERT INTO TweetToken(NUMTEL,TOKEN) VALUES(\'" + telNum+"\',\'" +twitterToken +"\')");
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
