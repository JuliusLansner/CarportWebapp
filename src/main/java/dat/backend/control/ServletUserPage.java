package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.User;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.UserFacade;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The ServletUserPage class handles user specific actions
 * It provides the user with the functionality to delete their own account.
 * userPage.jsp sends a request to this servlet.
 * The ServletUserPage forwards to either the Index servlet if succesful else userPage.jsp or error.jsp
 */
@WebServlet(name = "ServletUserPage", value = "/ServletUserPage")
public class ServletUserPage extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * this method allows a user to delete their account, after they have typed their password
     * @param request  comes from the client
     * @param response is sent back to the client
     * @throws ServletException if servlet errors occurs
     * @throws IOException      if an I/O error happens during the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String deleteOwnAccount = request.getParameter("deleteOwnAccount");

        if (deleteOwnAccount != null) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email != null && password != null) {
                try {
                    User user = UserFacade.findUserByEmail(email, connectionPool);
                    if (user != null && Objects.equals(user.getPassword(), password)) {
                        UserFacade.deleteMyAccount(email, password, connectionPool);
                        HttpSession session = request.getSession();
                        session.invalidate();
                        response.sendRedirect(request.getContextPath() + "/index");
                    } else {
                        request.getRequestDispatcher("userPage.jsp").forward(request, response);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("errormessage", e.getMessage());
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        }
    }
}
