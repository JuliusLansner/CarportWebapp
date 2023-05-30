package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.PasswordSecurityCheck;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServletSignup", value = "/ServletSignup")
public class ServletSignup extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String address = request.getParameter("adress");
        int zipcode = Integer.parseInt(request.getParameter("zipcode"));
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String password = request.getParameter("password");
        String passwordCheck = request.getParameter("passwordCheck");

        if (PasswordSecurityCheck.securityCheck(password) == false) {
            //password is not secure and user goes back to signup.jsp
            request.setAttribute("error","Kodeord er for svagt");
            request.getRequestDispatcher("signUp.jsp").forward(request,response);
        } else if (!password.equals(passwordCheck)) {
            request.setAttribute("error","Passwords must match");
            request.getRequestDispatcher("signUp.jsp").forward(request,response);
        } else try {

            UserFacade.createUser(email,password,address,zipcode,phoneNumber,connectionPool);

        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }

        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
}
