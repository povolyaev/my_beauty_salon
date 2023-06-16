package Servlets;

import main.java.Models.Client;
import main.java.Service.ClientService;
import main.java.DatabaseUtils.DatabaseUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServiceServlet.class.getName());
    private static final String PROPERTIES_PATH = Path.of("main").toAbsolutePath() + "/resources/config.properties";
    private ClientService clientService;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("web/jsp/login.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        RequestDispatcher requestDispatcher;
        Client client;
        try {
            client = clientService.login(login, password);
        } catch (SQLException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/error?errorMessage=internalServerError");
            return;
        }
        if (client != null) {
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("id", user.getUserId());
            request.getSession().setAttribute("monthOffset", 0);
            requestDispatcher = request.getRequestDispatcher("web/jsp/welcome.jsp");
        } else {
            request.setAttribute("loggingAttempt", "invalid data");
            requestDispatcher = request.getRequestDispatcher("web/jsp/login.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}
