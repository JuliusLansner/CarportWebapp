package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ServletBuyOrder handles a specific order, retrieving and filtering the relevant data that is needed to display a BOM (Bill of Materials)
 * On approved orders, a user has the ability to see his BOM when pressing the pay button.
 * The userPage.jsp requests the post method, and which referes back to
 * jsp requests it and jsp it referes to userPayBOMList.jsp or to error.jsp
 */
@WebServlet(name = "ServletBuyOrder", value = "/ServletBuyOrder")
public class ServletBuyOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * Handles the POST request sent to this servlet, and displays the a specific orders BOM list.
     * @param request comes from the client
     * @param response is sent to the client
     * @throws ServletException if servlet errors occurs
     * @throws IOException if an I/O error happens during the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String orderIdFromParameter = request.getParameter("orderId");

        int orderId = 0;

        if (orderIdFromParameter != null){
            orderId = Integer.parseInt(orderIdFromParameter);
        }
        try {
            List<Bom> bomList = BomFacade.getBoms(connectionPool);
            List<Bom> filteredBOMList = new ArrayList<>();

            for (Bom bom : bomList){
                if (bom.getOrderId() == orderId){
                    filteredBOMList.add(bom);
                }
            }
            session.setAttribute("bomList",filteredBOMList);

            // get all material variants from the database
            List<MaterialVariant> allMaterialVariants = MaterialVariantFacade.getAllMaterialVariants(connectionPool);

            // new list for only the relevant materialVariants
            List<MaterialVariant> materialVariantList = new ArrayList<>();

            // loop through and only take out the ones that matches the partslist id
            for (MaterialVariant materialVariant : allMaterialVariants) {
                for (Bom bom : filteredBOMList) {
                    if (materialVariant.getPartslistID() == bom.getId()) {
                        materialVariantList.add(materialVariant);
                        break;
                    }
                }
            }
            session.setAttribute("materialVariantList", materialVariantList);

            List<Material> materialList = MaterialFacade.materialList(connectionPool);
            session.setAttribute("material", materialList);
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.getRequestDispatcher("userPayBOMList.jsp").forward(request, response);
    }
}