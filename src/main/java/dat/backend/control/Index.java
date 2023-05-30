package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Purpose of this servlet is to be a kind of setup and data loader, that gets run before anything else.
 */
@WebServlet(name = "Index", value = "/index")
public class Index extends HttpServlet {
    /**
     *Loads some data from orders, users and materials from the database into lists and sets them as attributes in the session scope
     * Also sets a boolean to tell if logged in.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        Boolean loggedIn = false;
        session.setAttribute("loggedin",loggedIn);

        try {
            
            ArrayList<Order> userOrders = OrderFacade.orderList(connectionPool);
            session.setAttribute("userOrders", userOrders);

            ArrayList<Material> materialArrayList = MaterialFacade.materialList(connectionPool);
            session.setAttribute("materialList", materialArrayList);

            ArrayList<User> allUsersList = (ArrayList<User>) UserFacade.allUsers(connectionPool);
            session.setAttribute("allUsersList", allUsersList);

        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
