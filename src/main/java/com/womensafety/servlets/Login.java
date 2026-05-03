
package com.womensafety.servlets;

import com.womensafety.db.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/Login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic validation
        if(email == null || password == null ||
                email.isEmpty() || password.isEmpty()) {
            response.sendRedirect("index.html?error=empty");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                // Login successful - save user info in session
                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getInt("id"));
                session.setAttribute("userName", rs.getString("name"));
                session.setAttribute("userEmail", rs.getString("email"));
                session.setAttribute("userPhone", rs.getString("phone"));
                conn.close();

                // Go to dashboard
                response.sendRedirect("dashboard.html");

            } else {
                // Wrong email or password
                conn.close();
                response.sendRedirect("index.html?error=invalid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?error=server");
        }
    }
}