package dat.backend.control;

import dat.backend.model.entities.Material;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Index", value = "/index")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ConnectionPool connectionPool = new ConnectionPool();

        try {
            connectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            ArrayList<Order>orderList = OrderFacade.orderList(connectionPool);
            session.setAttribute("orderlist",orderList);

            ArrayList<Order> userOrders = OrderFacade.orderList(connectionPool);
            session.setAttribute("userOrders", userOrders);

            ArrayList<Material> materialArrayList = MaterialFacade.materialList(connectionPool);
            session.setAttribute("materialList", materialArrayList);

            ArrayList<User> allUsersList = (ArrayList<User>) UserFacade.allUsers(connectionPool);
            session.setAttribute("allUsersList", allUsersList);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
