/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hw4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author xiaowei
 */
public class secondservlet extends HttpServlet {

    @Resource(name = "jdbc/testdbRE")
    private javax.sql.DataSource datasource;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int numberOfQuestion = 0;
        int numberOfChoice = 0;
        
        try{
            Connection connection;
             
            /* TODO output your page here. You may use following sample code. */
            //This servlet is used to return a web page to show the questions and choices inside the database.
            try (PrintWriter out = response.getWriter()){
               connection = datasource.getConnection();
               out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head>");
               out.println("<title>Database Results</title>");            
               out.println("</head>");
               out.println("<body>");
               
               String sql = "select max(id) from xiao.QUESTION";
               PreparedStatement nextIdInsert = connection.prepareStatement(sql);
               ResultSet endResults = nextIdInsert.executeQuery();
               endResults.next();
               numberOfQuestion = endResults.getInt(1);
               endResults.close();
               nextIdInsert.close();
               
               //This for loop is used to go through number of questions and choices to show on the webpage.
               for(int i = 1; i < numberOfQuestion + 1; i++){
                   sql = "select question_text from xiao.QUESTION where id = " + i;
                   PreparedStatement getQuestion = connection.prepareStatement(sql);
                   ResultSet questionResult = getQuestion.executeQuery();
                   questionResult.next();
                   String questionString = questionResult.getString("question_text");
                   questionResult.close();
                   getQuestion.close();
                   out.println("<h1>"+questionString+"</h1>");
                   
                   sql = "select count(question_id) from xiao.CHOICE where question_id = " + i;
                   PreparedStatement numberOfId = connection.prepareStatement(sql);
                   ResultSet countResult = numberOfId.executeQuery();
                   countResult.next();
                   numberOfChoice = countResult.getInt(1);
                   countResult.close();
                   numberOfId.close();
                   
                   sql = "select choice_text from xiao.CHOICE where question_id = "+ i;
                   PreparedStatement getCount = connection.prepareStatement(sql);
                   ResultSet getCountResult = getCount.executeQuery();
                   for(int j = 1; j < numberOfChoice + 1; j++){
                       getCountResult.next();
                       String c = getCountResult.getString("choice_text");
                       
                       out.println("<input type =\"radio\" name=\""+ questionString +"\">"+ c +"<br><br>");
                   }
                   getCountResult.close();
                   getCount.close();
                   out.println("<hr>");
               }
          
            out.println("</body>");
            out.println("</html>");
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(firstservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
