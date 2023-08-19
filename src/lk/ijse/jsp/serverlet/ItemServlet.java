package lk.ijse.jsp.serverlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/pages/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/possystemaad", "root", "root123");
            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();
            resp.addHeader("Content-Type","application/json");

            String json="[";
            while (rst.next()) {
                String item="{";
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String price = rst.getString(4);
                item+="\"id\":\""+id+"\",";
                item+="\"name\":\""+name+"\",";
                item+="\"address\":\""+address+"\"";
                item+="\"price\":\""+price+"\"";
                item+="},";
                json+=item;
            }
            json=json+"]";

            resp.getWriter().print(json.substring(0,json.length()-2)+"]");
            // create a json response including customer data
            // String s= "[{id:""}]";
            //send the output through the ajax response




        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
//
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        String itemName = req.getParameter("description");
        String qty = req.getParameter("qty");
        String unitPrice = req.getParameter("unitPrice");
        String option = req.getParameter("option");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/possystemaad", "root", "root123");
            switch (option) {
                case "add":
                    PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
                    pstm.setObject(1, code);
                    pstm.setObject(2, itemName);
                    pstm.setObject(3, qty);
                    pstm.setObject(4, unitPrice);
                    if (pstm.executeUpdate() > 0) {
                        resp.getWriter().println("Item Added..!");
                        resp.sendRedirect("item");
                    }
                    break;
                case "delete":
                    PreparedStatement pstm2 = connection.prepareStatement("delete from Item where code=?");
                    pstm2.setObject(1, code);
                    if (pstm2.executeUpdate() > 0) {
                        resp.getWriter().println("Item Deleted..!");
                        resp.sendRedirect("/jsonp/pages/item.jsp");
                    }
                    break;
                case "update":
                    PreparedStatement pstm3 = connection.prepareStatement("update Item set description=?,qtyOnHand=?,unitPrice=? where code=?");
                    pstm3.setObject(1, itemName);
                    pstm3.setObject(2, qty);
                    pstm3.setObject(3, unitPrice);
                    pstm3.setObject(4, code);
                    if (pstm3.executeUpdate() > 0) {
                        resp.getWriter().println("Item Updated..!");
                        resp.sendRedirect("/jsonp/pages/item.jsp");
                    }
                    break;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
