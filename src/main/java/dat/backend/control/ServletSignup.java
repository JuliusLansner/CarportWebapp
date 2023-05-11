package dat.backend.control;

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

@WebServlet(name = "ServletSignup", value = "/ServletSignup")
public class ServletSignup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionPool connectionPool = new ConnectionPool();

        String email = request.getParameter("username");
        String address = request.getParameter("adress");
        int zipcode = Integer.parseInt(request.getParameter("zipcode"));
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String password = request.getParameter("password");
        String passwordCheck = request.getParameter("passwordCheck");

        if (PasswordSecurityCheck.securityCheck(password) == false) {
            //password is not secure and user goes back to signup.jsp
            request.setAttribute("error","Kodeord er for svagt");
            request.getRequestDispatcher("signUp.jsp").forward(request,response);
        }

        if (password != passwordCheck) {
            request.setAttribute("error","your passwords dont match");
            request.getRequestDispatcher("signUp.jsp").forward(request,response);
        }

        try {

            UserFacade.createUser(email,password,address,zipcode,phoneNumber,connectionPool);

        } catch (DatabaseException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }

       request.getRequestDispatcher("login.jsp").forward(request,response);
    }
}
