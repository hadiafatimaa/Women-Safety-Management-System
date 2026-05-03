package com.womensafety.servlets;

import com.womensafety.db.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/Incident")
public class Incident extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.html?error=notloggedin");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String description = request.getParameter("description");
        String location = request.getParameter("location");

        // Basic validation
        if(description == null || description.isEmpty()) {
            response.sendRedirect("dashboard.html?error=empty");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Save incident to database
            String sql = "INSERT INTO incidents (user_id, description, location) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.executeUpdate();

            // Get all incidents for this user to show on page
            String selectSql = "SELECT * FROM incidents WHERE user_id = ? ORDER BY created_at DESC";
            PreparedStatement selectPs = conn.prepareStatement(selectSql);
            selectPs.setInt(1, userId);
            ResultSet rs = selectPs.executeQuery();

            // Build JSON response
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            while(rs.next()) {
                if(!first) json.append(",");
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"description\":\"").append(rs.getString("description")).append("\",");
                json.append("\"location\":\"").append(rs.getString("location")).append("\",");
                json.append("\"created_at\":\"").append(rs.getString("created_at")).append("\"");
                json.append("}");
                first = false;
            }
            json.append("]");
            conn.close();

            response.setContentType("application/json");
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to save incident\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.html?error=notloggedin");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM incidents WHERE user_id = ? ORDER BY created_at DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            while(rs.next()) {
                if(!first) json.append(",");
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"description\":\"").append(rs.getString("description")).append("\",");
                json.append("\"location\":\"").append(rs.getString("location")).append("\",");
                json.append("\"created_at\":\"").append(rs.getString("created_at")).append("\"");
                json.append("}");
                first = false;
            }
            json.append("]");
            conn.close();

            response.setContentType("application/json");
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("[]");
        }
    }
}