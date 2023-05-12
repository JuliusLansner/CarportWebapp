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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

        HttpSession session = request.getSession();

        try {
            connectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<Order> userOrders = OrderFacade.orderList(connectionPool);
            session.setAttribute("userOrders", userOrders);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<Material> materialArrayList = MaterialFacade.materialList(connectionPool);
            session.setAttribute("materialList", materialArrayList);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
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
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    }
}
