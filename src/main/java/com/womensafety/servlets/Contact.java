package com.womensafety.servlets;

import com.womensafety.db.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/Contact")
public class Contact extends HttpServlet {

    // Add new contact
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.html?error=notloggedin");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String contactName = request.getParameter("contact_name");
        String contactPhone = request.getParameter("contact_phone");

        // Basic validation
        if(contactName == null || contactPhone == null ||
                contactName.isEmpty() || contactPhone.isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"All fields required\"}");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Save contact to database
            String sql = "INSERT INTO contacts (user_id, contact_name, contact_phone) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, contactName);
            ps.setString(3, contactPhone);
            ps.executeUpdate();
            conn.close();

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Contact added!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to add contact\"}");
        }
    }

    // Get all contacts
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
            String sql = "SELECT * FROM contacts WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            while(rs.next()) {
                if(!first) json.append(",");
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"contact_name\":\"").append(rs.getString("contact_name")).append("\",");
                json.append("\"contact_phone\":\"").append(rs.getString("contact_phone")).append("\"");
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

    // Delete a contact
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.html?error=notloggedin");
            return;
        }

        String contactId = request.getParameter("id");

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM contacts WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(contactId));
            ps.executeUpdate();
            conn.close();

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Contact deleted!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to delete\"}");
        }
    }
}