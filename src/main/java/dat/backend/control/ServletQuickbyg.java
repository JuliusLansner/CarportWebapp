package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Bom;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;
import dat.backend.model.services.MaterialVariantListMaker;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Purpose of this servlet is to set an order for the customer that is logged in, and redirect customers that aren't logged in to the contact info page.
 */
@WebServlet(name = "ServletQuickbyg", value = "/ServletQuickbyg")
public class ServletQuickbyg extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     *If logged in it creates an order and makes a bill of materials which gets set as a session attribute and directs to valgtbestilling.jsp, else it directs to the
     * page contactinfo.jsp.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        boolean loggedIn = (boolean) session.getAttribute("loggedin");

        User user = (User) session.getAttribute("user");

        int width = Integer.parseInt(request.getParameter("width"));
        int length = Integer.parseInt(request.getParameter("length"));


        session.setAttribute("width", width);
        session.setAttribute("length", length);

        if (loggedIn == true) {
            try {
                //creates order and stores id for order in int, so a bom with orderID can be created.
                int orderId = OrderFacade.createOrder(length,width,0,user.getIdUser(),connectionPool);


                //creates materialvariants based on length and width of the carport and creates a bom which is put into an object.
                Bom bom = MaterialVariantListMaker.carportMaterialList(length,width,orderId,connectionPool);

                //updates the price of the order.
                OrderMapper.updateOrderPrice(bom.getPrice(),orderId,connectionPool);

                //makes a session attribute to store bomID in for later when user needs to recieve it.
                session.setAttribute("bom",bom);
                request.getRequestDispatcher("valgtBestilling.jsp").forward(request,response);
            } catch (DatabaseException | SQLException e) {
                e.printStackTrace();
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
                throw new RuntimeException(e);
            }
        } else {
            request.getRequestDispatcher("contactInfo.jsp").forward(request, response);
        }

        //updates the current users orderlist
        try {
            List<Order> userOrders = (List<Order>) session.getAttribute("userOrders");

            List<Order> updatedOrderList = OrderFacade.orderList(connectionPool);

            if (!updatedOrderList.isEmpty()) {
                userOrders.clear();
            }
            session.setAttribute("userOrders",updatedOrderList);
        } catch (DatabaseException e) {
            e.printStackTrace();
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
