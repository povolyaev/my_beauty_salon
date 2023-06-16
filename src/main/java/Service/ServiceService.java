package main.java.Service;

import main.java.Models.ServiceType;
import main.java.Repositories.ServiceRepository;
import main.java.Models.Service;
import main.java.DatabaseUtils.DatabaseUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceService {
    private static final Logger logger = Logger.getLogger(ServiceService.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties";
    private final ServiceRepository serviceRepository;

    public ServiceService(Connection connection) {
        serviceRepository = new ServiceRepository(connection);
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

    public ServiceService() {
        Properties properties = new Properties();
        Connection connection = null;

        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(fileInputStream);
            DatabaseUtils databaseUtils = new DatabaseUtils(properties.getProperty("db.host"));
            connection = databaseUtils.getConnection();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException trying to read properties file", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException trying to get connection", e);
        }
        serviceRepository = new ServiceRepository(connection);
    }

    public List<Service> getUserService(int id, LocalDate localDate) throws SQLException {
        return serviceRepository.getServiceDataOfUserInMonth(id, localDate);
    }
}
