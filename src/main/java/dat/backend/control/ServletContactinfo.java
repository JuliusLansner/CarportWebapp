package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.PasswordSecurityCheck;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServletContactinfo", value = "/ServletContactinfo")
public class ServletContactinfo extends HttpServlet {
    //Checks for the user by email in database - Then either signing up or adding a user ot the session
    //that can be used to add the order to the account.
    //Zipcode and phonenumber gets checked for not being 0, as isEmpty and != null doesn't work for int.
    //It works in practice, but it's not super clean and has several issues.

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("adress");
        int zipcode = Integer.parseInt(request.getParameter("zipcode"));
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String passwordCheck = request.getParameter("passwordCheck");

        try {
            User userFind = UserFacade.findUserByEmail(email, connectionPool);
            if (userFind != null && !address.isEmpty() && zipcode != 0 && phoneNumber != 0) {
                User user = UserFacade.login(email, password, connectionPool);
                session = request.getSession();
                session.setAttribute("user", user);
                request.getRequestDispatcher("valgtBestilling.jsp").forward(request, response);
            } else {
                if (!PasswordSecurityCheck.securityCheck(password)) {
                    request.getRequestDispatcher("contactInfo.jsp").forward(request, response);
                }

                if (!password.equals(passwordCheck)) {
                    request.getRequestDispatcher("contactInfo.jsp").forward(request, response);
                }
                try {
                    UserFacade.createUser(email, password, address, zipcode, phoneNumber, connectionPool);
                } catch (DatabaseException e) {
                    e.printStackTrace();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("valgtBestilling.jsp").forward(request, response);
    }
}

