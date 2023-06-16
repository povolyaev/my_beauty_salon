package main.java.Repositories;

import main.java.Models.ServiceType;

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

public class ServiceTypeRepository {
    private static final Logger logger = Logger.getLogger(ServiceTypeRepository.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties";
    private Connection connection;

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

    public ServiceTypeRepository(Connection connection) {
        this.connection = connection;
    }

    public ServiceType getServiceTypeById(int id) throws SQLException {
        logger.log(Level.INFO, "started trying to get data from table service_type");
        String query = "SELECT service_type_id, \"service_type_name\" " +
                "FROM service_type WHERE service_type_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String serviceType = resultSet.getString("service_type_name");
                logger.log(Level.INFO, "finished getting data from table service_type");
                return new ServiceType(id, serviceType);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "failed to get data from table service_type", e);
            throw e;
        }
        return null;
    }
}
