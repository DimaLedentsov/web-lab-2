package com.dimka228.web2.servlets;

import com.dimka228.web2.beans.RawBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", value = "/control")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("x_coordinate") != null && req.getParameter("y_coordinate") != null
                && req.getParameter("r_coordinate") != null) {
            getServletContext().getNamedDispatcher("AreaCheckServlet").forward(req, resp);
        }
        else if (req.getParameter("clear") != null){
            //Получение Beans для очистки
            RawBean beans = (RawBean) req.getSession().getAttribute("table");
            if (beans == null) beans = new RawBean();
            beans.getRaws().clear();
            req.getSession().setAttribute("table", beans);
            getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
        }
        else {
            getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }
}
