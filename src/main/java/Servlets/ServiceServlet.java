package Servlets;

import Models.Service;
import Service.ServiceService;
import Service.ClientService;
import DatabaseUtils.DatabaseUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServiceServlet", value = "/service")
public class ServiceServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServiceServlet.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties"
    private ClientService clientService;


    {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(fis);

            String logPath = properties.getProperty("logger.logPath");
            logger.addHandler(new FileHandler(logPath));

            DatabaseUtils dbUtils = new DatabaseUtils("db.host");
            clientService = new ClientService(dbUtils.getConnection());

        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException trying to read properties file", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException trying to read properties file", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceService serviceService = new ServiceService();
        try {
            request.getSession().setAttribute("service",
                    serviceService.getUserService(clientService.getIdByLogin(request.getSession().getAttribute("login").toString()),
                            LocalDate.now().plusMonths(Integer.parseInt(request.getSession().getAttribute("monthOffset").toString()))));
        } catch (SQLException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/error?errorMessage=internalServerError");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/service.jsp");
        requestDispatcher.forward(request, response);
    }
}
