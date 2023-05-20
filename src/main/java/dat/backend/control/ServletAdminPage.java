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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            // updates the price in the database
            MaterialFacade.updateMaterialPricePrUnit(updatedPricePrUnit, materialId, connectionPool);

            // updates the price in the session scope
            HttpSession session = request.getSession();
            List<Material> updatedMaterialList = (List<Material>) session.getAttribute("materialList");
            for (Material material : updatedMaterialList) {
                if (material.getIdMaterial() == materialId) {
                    material.setPricePerUnit(updatedPricePrUnit);
                    break;
                }
            }
            session.setAttribute("materialList",updatedMaterialList);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        //   --- changes the status of an order via. the approval and decline buttons ---
        String statusParameter = request.getParameter("status");
        String orderIdParameter = request.getParameter("orderId");
        String action = request.getParameter("action");

        int status = 0;
        int orderId = 0;

        if ( statusParameter != null && !statusParameter.isEmpty()) {
            status = Integer.parseInt(statusParameter);
        }

        if (orderIdParameter != null && !orderIdParameter.isEmpty()) {
            orderId = Integer.parseInt(orderIdParameter);
        }

        if ("afvis".equals(action)) {
            status = 1;
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
            e.printStackTrace();
        }

        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    }
}