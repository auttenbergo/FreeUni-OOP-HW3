import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private static final String DB = "metropolises";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    protected static final String MAIN_TABLE_NAME = "metropolises";
    protected static final String TEST_TABLE_NAME = "metropolises_test";

    private static Connection connection;

    public DBConfig() throws ClassNotFoundException, SQLException {
        System.out.println(DBConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver initialized");

        String URL = "jdbc:mysql://" + HOST + ":" + PORT +"/" + DB + "?characterEncoding=UTF8&user=" + USER;
        connection = DriverManager.getConnection(URL);
        System.out.println("Successfully connected to DB");
    }
    public Connection getConnection(){
        return connection;
    }
}
