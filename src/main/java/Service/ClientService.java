package main.java.Service;

import main.java.Models.Client;
import main.java.Repositories.ClientRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientService {
    private final ClientRepository clientRepository;
    private static final Logger logger = Logger.getLogger(ClientService.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties";

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

    public ClientService(Connection connection) {
        clientRepository = new ClientRepository(connection);
    }

    public Client login (String login, String password) throws SQLException {
        Client client = clientRepository.getByLogin(login);
        if (client != null && client.getPassword().equals(password)) {
            return client;
        }
        return null;
    }

    public int getIdByLogin(String login) throws SQLException {
        Client client = clientRepository.getByLogin(login);
        return client == null ? -1 : client.getClientId();
    }
}
