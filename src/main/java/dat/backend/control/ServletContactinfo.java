package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Bom;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.*;
import dat.backend.model.services.MaterialVariantListMaker;
import dat.backend.model.services.PasswordSecurityCheck;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServletContactinfo", value = "/ServletContactinfo")
public class ServletContactinfo extends HttpServlet {
    /**checks for the user by email in database - Then either signing up or adding a user in the session
    that can be used to add the order to the account.
    Zipcode and phonenumber gets checked for not being 0, as isEmpty and != null doesn't work for int.
    It works in practice, but it's not super clean and has several issues.
    name, email, address,zipcode and phonenumber stored in session for further use.

     Sends user to valgtBestilling.jsp. En exception sends user to error.jsp. An error in passwordCheck sends user
     to contactInfo.jsp again
     * @param userFind - finds user by email, has access to and uses the database.
     * @return user- returns a user. If null, we make a user with the given email/password.
     * @throws DatabaseException - if anything goes wrong in try/catch, we get a database exception(e)
     * @param passwordCheck - checks password against security measures.
     *
     */
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
        int zipcode = Integer.parseInt(request.getParameter("zipcode"));
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String passwordCheck = request.getParameter("passwordCheck");

        try {
            User userFind = UserFacade.findUserByEmail(email, connectionPool);
            if (userFind != null && !address.isEmpty() && zipcode != 0 && phoneNumber != 0 && !name.isEmpty()) {
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
        session.setAttribute("currentName",name);
        session.setAttribute("currentEmail",email);
        session.setAttribute("currentAddress",address);
        session.setAttribute("currentZip",zipcode);
        session.setAttribute("currentPhone",phoneNumber);

//        //width, length and user for creating order and bom
//        int width = (int) session.getAttribute("width");
//        int length = (int) session.getAttribute("length");
//        User user = (User) session.getAttribute("user");




//        try {
//            //creates order and stores id for order in int, so a bom with orderID can be created.
//            int orderId = OrderFacade.createOrder(length,width,0,user.getIdUser(),connectionPool);
//
//
//            //creates materialvariants based on length and width of the carport and creates a bom which is put into a objekt.
//            Bom bom = MaterialVariantListMaker.carportMaterialList(length,width,orderId,connectionPool);
//
//            //updates the price of the order.
//            OrderMapper.updateOrderPrice(bom.getPrice(),orderId,connectionPool);
//
//            //makes a session attribute to store bomID in for later when user needs to recieve it.
//            session.setAttribute("bom",bom);
//
//        } catch (DatabaseException | SQLException e) {
//            e.printStackTrace();
//        }


        request.getRequestDispatcher("valgtBestilling.jsp").forward(request, response);
    }
}

