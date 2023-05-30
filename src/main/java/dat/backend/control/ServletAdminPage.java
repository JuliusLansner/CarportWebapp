package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * The ServletAdminPage class handles administrative functions that only an admin can and should access.
 * It offers the functionality to update a materials unit price and change the status of an order.
 * This class is used by users with the role of admin.
 * adminPage.jsp sends request to this servlet in the doPost method.
 * This servlet forwards to WEB-INF/adminPage.jsp and error.jsp
 */
@WebServlet(name = "ServletAdminPage", value = "/ServletAdminPage")
public class ServletAdminPage extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * Handles the POST request sent to this servlet, and offers the admin the ability to perform administrative tasks.
     *
     * @param request  comes from the client
     * @param response is sent back to the client
     * @throws ServletException if servlet errors occurs
     * @throws IOException      if an I/O error happens during the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("newPrice".equals(action)) {
            newPrice(request, response);
        } else if ("changeStatus".equals(action)) {
            changeStatus(request, response);
        } else {
            request.removeAttribute("error.jsp");
        }
    }

    /**
     * This method allows the admin to update the unit price of a material
     * @param request comes from the client
     * @param response is sent back to the client
     * @throws ServletException if servlet errors occurs
     * @throws IOException if an I/O error happens during the request
     */
    private void newPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String materialIdFromParameter = request.getParameter("materialId");
        String updatedPricePrUnitFromParameter = request.getParameter("updatedPricePrUnit");

        int materialId = 0;
        int updatedPricePrUnit = 0;
        if (materialIdFromParameter != null && !materialIdFromParameter.isEmpty()) {
            materialId = Integer.parseInt(materialIdFromParameter);
        }
        if (updatedPricePrUnitFromParameter != null && !updatedPricePrUnitFromParameter.isEmpty()) {
            updatedPricePrUnit = Integer.parseInt(updatedPricePrUnitFromParameter);
        }
        try {
            MaterialFacade.updateMaterialPricePrUnit(updatedPricePrUnit, materialId, connectionPool);
            HttpSession session = request.getSession();
            List<Material> updatedMaterialList = (List<Material>) session.getAttribute("materialList");
            for (Material material : updatedMaterialList) {
                if (material.getIdMaterial() == materialId) {
                    material.setPricePerUnit(updatedPricePrUnit);
                    break;
                }
            }
            session.setAttribute("materialList", updatedMaterialList);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.getRequestDispatcher("WEB-INF/adminPage.jsp").forward(request,response);
    }

    /**
     * This method allows the admin to change the status of an ordre
     * @param request comes from the client
     * @param response is sent back to the client
     * @throws ServletException if servlet errors occurs
     * @throws IOException if an I/O error happens during the request
     */
    private void changeStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String statusParameter = request.getParameter("status");
        String orderIdParameter = request.getParameter("orderId");

        int status = 0;
        int orderId = 0;

        if (statusParameter != null && !statusParameter.isEmpty()) {
            status = Integer.parseInt(statusParameter);
        }
        if (orderIdParameter != null && !orderIdParameter.isEmpty()) {
            orderId = Integer.parseInt(orderIdParameter);
        }
        try {
            OrderFacade.updateOrderStatus(status, orderId, connectionPool);
            HttpSession session = request.getSession();
            List<Order> updatedOrderStatusList = (List<Order>) session.getAttribute("userOrders");
            for (Order order : updatedOrderStatusList) {
                if (order.getOrderId() == orderId) {
                    order.setStatus(status);
                    break;
                }
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.getRequestDispatcher("WEB-INF/adminPage.jsp").forward(request, response);
    }
}