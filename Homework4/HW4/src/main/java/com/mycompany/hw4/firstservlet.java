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
public class firstservlet extends HttpServlet {

    @Resource(name = "jdbc/testdbRE")//This is connecting to the JDBC Resource.
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
        try (PrintWriter out = response.getWriter()) {
            
            Connection connection;//Creating a connection object
            String question = request.getParameter("question");//THis gets the input from the html file for question input box
            int numberOfOp = Integer.parseInt(request.getParameter("numOp"));//This gets the input from the html for the number of options
            try {
                connection = datasource.getConnection();//setting up the connection with sql database
                
                //Used inclass example as guide to set up.
                //This is getting the next number of ID in the question table, and gives a new id number 
                String sql = "select max(ID) from xiao.QUESTION";//sql code
                PreparedStatement questionId = connection.prepareStatement(sql);
                ResultSet results = questionId.executeQuery();
                results.next();
                int nextId = results.getInt(1) + 1;
                results.close();
                questionId.close();
                
                //This stores the question into the database into question table
                sql = "insert into xiao.QUESTION (ID,QUESTION_TEXT) values (?,?)";
                PreparedStatement insertQuestion = connection.prepareStatement(sql);
                insertQuestion.setInt(1, nextId);
                insertQuestion.setString(2,question);
                int recordsAffected = insertQuestion.executeUpdate();
                insertQuestion.close();
                
                //This stores the options into the database by using a for loop to go through number of options provided
                sql = "insert into xiao.CHOICE (QUESTION_ID, CHOICE_TEXT) values (?,?)";
                PreparedStatement insertChoice = connection.prepareStatement(sql);
                for(int i = 1; i < numberOfOp + 1; i++){
                    String choiceOp = request.getParameter("option" + i);
                    insertChoice.setInt(1, nextId);
                    insertChoice.setString(2, choiceOp);
                    insertChoice.executeUpdate();
                }
                insertChoice.close();
                connection.close();
                
            }
            catch (SQLException ex) {
                Logger.getLogger(firstservlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    