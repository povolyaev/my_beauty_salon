package main.java.Repositories;

import main.java.Models.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientRepository {
    private static final Logger logger = Logger.getLogger(ClientRepository.class.getName());
    private static final String PROPERTIES_PATH =
            Path.of("main").toAbsolutePath() + "/resources/config.properties";
    private Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    static {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(fis);

            String logPath = properties.getProperty("logger.logPath");

            logger.addHandler(new FileHandler(logPath));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException trying to read properties file", e);
        }
    }

    public Client getByLogin(String login) throws SQLException {
        logger.log(Level.INFO, "trying to get data by login");
        String query = "SELECT client_id, login, password, email " +
                "FROM \"client\" WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ClientRepository clientRepository = new ClientRepository(connection);
                return new Client(resultSet.getInt("user_id"), login,
                        resultSet.getString("password"),
                        resultSet.getString("email"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "error trying to get client by login", e);
            throw e;
        }
        return null;
    }
}
