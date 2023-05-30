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
import dat.backend.model.services.PasswordSecurityCheck;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "ServletContactinfo", value = "/ServletContactinfo")
/**
 * checks for the user by email in database - Then either signing up or adding a user in the session
 * that can be used to add the order to the account.
 * name, email, address,zipcode and phonenumber stored in session for further use.
 * <p>
 * Sends user to valgtBestilling.jsp. an exception sends user to error.jsp. An error in passwordCheck sends user
 * to contactInfo.jsp. An error in number/zipcode not being an int, sends user to contactinfo.jsp with an error message.
 *
 * @param userFind - finds user by email, has access to and uses the database.
 * @return user- returns a user. If null, we make a user with the given email/password.
 * @throws DatabaseException - if anything goes wrong in try/catch, we get a database exception(e)
 * @param passwordCheck - checks password against security measures.
 */
public class ServletContactinfo extends HttpServlet {

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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("adress");
        String zipcode = request.getParameter("zipcode");
        String phoneNumber = request.getParameter("phoneNumber");
        User user = null;

        //checks if user exists in the system, if so log in and use the user for the order. If not, create user.
        try {
            user = UserFacade.findUserByEmail(email, connectionPool);
            if (user != null && !address.isEmpty() && zipcode.isEmpty() && phoneNumber.isEmpty() && !name.isEmpty()) {
                user = UserFacade.login(email, password, connectionPool);
                session = request.getSession();
                session.setAttribute("user", user);
                request.getRequestDispatcher("valgtBestilling.jsp").forward(request, response);
            } else if (address.isEmpty() || zipcode.isEmpty() || phoneNumber.isEmpty() || name.isEmpty()) {
                request.setAttribute("Fejl", "Alle felter skal udfyldes korrekt. ");
                request.getRequestDispatcher("contactInfo.jsp").forward(request, response);
            } else {
                //tests the password against our password rules.
                if (!PasswordSecurityCheck.securityCheck(password)) {
                    request.setAttribute("Fejl", "Adgangskoden skal skal have mindst 1 stort bogstav, 1 lille bogstav, 3 tal og mindst 6 karakterer");
                    request.getRequestDispatcher("contactInfo.jsp").forward(request, response);
                } else {
                    try {
                        try {
                            //tests if the zipcode and phonenumber string are numbers, if not throw an error.
                            int zipInt = Integer.parseInt(zipcode);
                            int phoneInt = Integer.parseInt(phoneNumber);
                            user = UserFacade.createUser(email, password, address, zipInt, phoneInt, connectionPool);

                        } catch (NumberFormatException e) {
                            request.setAttribute("Fejl", "Postnummer og telefonnummer skal være tal. ");
                            request.getRequestDispatcher("contactInfo.jsp").forward(request, response);
                        }
                    } catch (DatabaseException | SQLException e) {
                        request.setAttribute("errormessage", e.getMessage());
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                }
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //width, length and user for creating order and bom
        int width = (int) session.getAttribute("width");
        int length = (int) session.getAttribute("length");

        try {
            //creates order and stores id for order in int, so a bom with orderID can be created.
            int orderId = OrderFacade.createOrder(length, width, 0, user.getIdUser(), connectionPool);


            //creates materialvariants based on length and width of the carport and creates a bom which is put into a objekt.
            Bom bom = MaterialVariantListMaker.carportMaterialList(length, width, orderId, connectionPool);

            //updates the price of the order.
            OrderMapper.updateOrderPrice(bom.getPrice(), orderId, connectionPool);

            //makes a session attribute to store bomID in for later when user needs to recieve it.
            session.setAttribute("bom", bom);

        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("valgtBestilling.jsp").forward(request, response);
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
