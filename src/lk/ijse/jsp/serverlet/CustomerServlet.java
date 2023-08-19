package lk.ijse.jsp.serverlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/possystemaad", "root", "root123");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Content-Type","application/json");


            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (rst.next()) {

                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",id);
                objectBuilder.add("name",name);
                objectBuilder.add("address",address);

                arrayBuilder.add(objectBuilder.build());

            }

            resp.getWriter().print(arrayBuilder.build());


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.addHeader("Content-Type","application/json");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");
        String option = req.getParameter("option");
        resp.addHeader("Content-Type","application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/possystemaad", "root", "root123");

            switch (option) {
                case "add":
                    PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?)");
                    pstm.setObject(1, cusID);
                    pstm.setObject(2, cusName);
                    pstm.setObject(3, cusAddress);
                    if (pstm.executeUpdate() > 0) {
                        resp.getWriter().println("Customer Added..!");


                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("state","OK");
                        objectBuilder.add("message","Successfully Added.....");
                        objectBuilder.add("Data"," ");
                        resp.getWriter().print(objectBuilder.build());

                    }
                    break;
                case "delete":
                    PreparedStatement pstm2 = connection.prepareStatement("delete from Customer where id=?");
                    pstm2.setObject(1, cusID);
                    if (pstm2.executeUpdate() > 0) {
                        resp.getWriter().println("Customer Deleted..!");

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("state", "Ok");
                        objectBuilder.add("message", "Successfully Deleted...!");
                        objectBuilder.add("data", " ");
                        resp.getWriter().print(objectBuilder.build());

                    }
                    break;
                case "update":
                    PreparedStatement pstm3 = connection.prepareStatement("update Customer set name=?,address=? where id=?");
                    pstm3.setObject(3, cusID);
                    pstm3.setObject(1, cusName);
                    pstm3.setObject(2, cusAddress);
                    if (pstm3.executeUpdate() > 0) {
                        resp.getWriter().println("Customer Updated..!");
                    }
                    break;
            }

            resp.addHeader("Content-Type","application/json");

//            resp.sendRedirect("/jsonp/pages/customer");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        } catch (SQLException e) {
//            throw new RuntimeException(e);

            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getMessage());
            error.add("data", " ");

            resp.sendError(400);

            resp.getWriter().print(error.build());
        }
    }
}