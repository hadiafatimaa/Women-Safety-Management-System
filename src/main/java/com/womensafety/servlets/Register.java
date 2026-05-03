
package com.womensafety.servlets;

import com.womensafety.db.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/Register")
public class Register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get data from HTML form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        // Basic validation
        if(name == null || email == null || password == null || phone == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            response.sendRedirect("index.html?error=empty");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Check if email already exists
            String checkSql = "SELECT id FROM users WHERE email = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, email);
            ResultSet rs = checkPs.executeQuery();

            if(rs.next()) {
                // Email already registered
                conn.close();
                response.sendRedirect("index.html?error=exists");
                return;
            }

            // Insert new user
            String sql = "INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.executeUpdate();
            conn.close();

            // Redirect to login page with success message
            response.sendRedirect("index.html?registered=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?error=server");
        }
    }
}