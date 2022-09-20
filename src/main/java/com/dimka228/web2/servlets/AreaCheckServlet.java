package com.dimka228.web2.servlets;

import com.dimka228.web2.beans.Raw;
import com.dimka228.web2.beans.RawBean;

import com.dimka228.web2.exceptions.WrongDataException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "AreaCheckServlet", value = "/check")
public class AreaCheckServlet extends HttpServlet {

    private List<Double> xRange = Arrays.asList(-4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0);
    private List<Double> rRange = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.nanoTime();

        String x = req.getParameter("x_coordinate");
        String y = req.getParameter("y_coordinate");
        String r = req.getParameter("r_coordinate");
        double xValue,yValue,rValue;
        try{
            if(x==null || x.equals("")) throw new WrongDataException("x not set");
            if(y==null || y.equals("")) throw  new WrongDataException("y not set");
            if(r==null || r.equals("")) throw  new WrongDataException("r not set");

            x = x.trim();
            y = y.trim();
            r = r.trim();
            xValue = validateX(x);
            yValue = validateY(y);
            rValue = validateR(r);

            boolean isInside =  insideCircle(xValue, yValue, rValue) ||
                                insideTriangle(xValue, yValue, rValue) ||
                                insideRectangle(xValue, yValue, rValue);

            OffsetDateTime currentTimeObject = OffsetDateTime.now(ZoneOffset.UTC);
            String currentTime;
            try {
                currentTimeObject = currentTimeObject.minusMinutes(Long.parseLong(req.getParameter("timezone")));
                currentTime = currentTimeObject.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            } catch (Exception exception) {
                currentTime = "no info";
            }
            String executionTime = String.valueOf((double)(System.nanoTime() - startTime)/1_000_000_000.0);

            RawBean raws = (RawBean) req.getSession().getAttribute("table");
            if (raws == null) raws = new RawBean();

            Raw raw = new Raw(xValue, yValue, rValue, currentTime, executionTime, isInside);
            raws.getRaws().add(raw);
            req.getSession().setAttribute("table", raws);
            req.getSession().setAttribute("check", raw);
            //System.out.println(raw.toString());

            getServletContext().getRequestDispatcher("/result_page.jsp").forward(req, resp);

        } catch (WrongDataException e){
            req.setAttribute("error_message", e.getMessage());
            getServletContext().getRequestDispatcher("/wrong_data.jsp").forward(req, resp);

        }


    }

    private double validateX(String x) throws  WrongDataException{
        double dx;
        try{
            dx = Double.parseDouble(x);
            if(dx>4||dx<-4) throw new WrongDataException("x is wrong format: " + dx);
        } catch (NumberFormatException e){
            throw new WrongDataException("x is wrong format");
        }
        return dx;
    }

    private double validateY(String y) throws WrongDataException{
        double dy;
        try{
            dy = Double.parseDouble(y);
            if(!( -5.0 <= dy && dy <= 3.0)) throw new WrongDataException("y is wrong format: " + dy );
        } catch (NumberFormatException e){
            throw new WrongDataException("y is wrong format");
        }
        return dy;
    }

    private double validateR(String r) throws WrongDataException{
        double dr;
        try{
            dr = Double.parseDouble(r);
            if(dr<1||dr>3) throw new WrongDataException("r is wrong format");
        } catch (NumberFormatException e){
            throw new WrongDataException("r is wrong format");
        }
        return  dr;
    }

    private boolean insideCircle(double x, double y, double r){
        return x >= 0 && y <= 0 && x*x + y*y <= (r/2)*(r/2);
    }

    private boolean insideTriangle(double x, double y, double r){
        return x >= 0 && y >= 0 && y<=-x+r/2;
    }

    private boolean insideRectangle(double x, double y, double r){
        return x <= 0 && y >= 0 && x>=-r/2 && y<=r;
    }
}
