package main.java.DatabaseUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {
    private final String url;
    private static String clientName;
    private static String password;
    private final static Logger logger = Logger.getLogger(DatabaseUtils.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties";

    public DatabaseUtils(String url) {
        this.url = url;
    }

    static {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(fileInputStream);

            clientName = properties.getProperty("db.clientName");
            password = properties.getProperty("db.password");
            String logPath = properties.getProperty("logger.logPath");

            logger.addHandler(new FileHandler(logPath));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException trying to read properties file", e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection;
        logger.log(Level.INFO, "trying to get connection to db");
        try {
            connection = DriverManager.getConnection(url,clientName,password);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException trying to get connection to db", e);
            throw e;
        }
        logger.log(Level.INFO, "finished trying to ge connection to db");
        return connection;
    }
}
