package dat.backend.control;

import dat.backend.model.entities.Material;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletQuickbyg", value = "/ServletQuickbyg")
public class ServletQuickbyg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String width = request.getParameter("width");
        String length = request.getParameter("length");
        String roof = request.getParameter("roof");


        session.setAttribute("width",width);
        session.setAttribute("length",length);
        session.setAttribute("roof",roof);
        request.getRequestDispatcher("valgtBestilling.jsp").forward(request,response);
    }
}
