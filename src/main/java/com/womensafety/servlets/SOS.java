package com.womensafety.servlets;

import com.womensafety.db.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/SOS")
public class SOS extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.html?error=notloggedin");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String message = request.getParameter("message");
        String location = request.getParameter("location");

        // If no message provided use default
        if(message == null || message.isEmpty()) {
            message = "EMERGENCY! I need help!";
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Save SOS alert to database
            String sql = "INSERT INTO sos_alerts (user_id, message, location) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, message);
            ps.setString(3, location);
            ps.executeUpdate();
            conn.close();

            // Send success response
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\",\"message\":\"SOS Alert Sent!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to send SOS\"}");
        }
    }
}
