package main.java.Repositories;

import main.java.Models.Service;
import main.java.Models.ServiceType;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceRepository {
    private static final Logger logger = Logger.getLogger(ServiceRepository.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties";
    private final Connection connection;

    public ServiceRepository(Connection connection) {
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

    public List<Service> getServiceDataOfUserInMonth(int clientId, LocalDate date) throws SQLException {
        List<Service> services = new ArrayList<>();
        String query = "SELECT service_id, service_type_id, service_date" +
                "FROM service" +
                "WHERE client_id = ? AND date_part('MONTH', service_date) = ? AND date_part('YEAR', service_date) = ?";
        logger.log(Level.INFO, "trying to get data from db");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, date.getMonthValue());
            preparedStatement.setInt(3, date.getYear());
            ResultSet resultSet = preparedStatement.executeQuery();
            ServiceTypeRepository serviceTypeRepository = new ServiceTypeRepository(connection);

            while (resultSet.next()) {
                LocalDate serviceDate = LocalDate.parse(resultSet.getString("service_date"));
                int serviceId = resultSet.getInt("service_id");
                int serviceTypeId = resultSet.getInt("service_type_id");
                services.add(new Service(serviceId,clientId,serviceDate,
                        serviceTypeRepository.getServiceTypeById(serviceTypeId)));
            }
        } catch (SQLException e) {
             logger.log(Level.SEVERE, "failed to get data from service", e);
             throw e;
        }
        logger.log(Level.INFO, "finished getting data from service");
        return services;
    }
}
