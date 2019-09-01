

package com.ntpc;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            String empId=request.getParameter("username");
            String password=request.getParameter("password");

            
                
            Class.forName("com.mysql.jdbc.Driver");
           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ntpc","root","password");
           PreparedStatement ps=con.prepareStatement("select password, username from employee where username=?");
            ps.setString(1, empId);

           
           ResultSet rs=ps.executeQuery();
            if(rs!=null && rs.next())
            {
                String passwordInDB=rs.getString("password");
                String name=rs.getString("username");
                
                if(password.equals(passwordInDB))
                {
                    
                    out.println("Login Authentication Success...");
                    HttpSession session=request.getSession(true);
                    session.setAttribute("USER", name);
                     session.setAttribute("USER1",empId);
                   
                    RequestDispatcher disp=null;
                   
                    disp.forward(request, response);
                }
                else{
                    out.println("Invalid Password. Retry");
                }
                
                
            }
            else
            {
                out.println("Invalid UserName or Member Type. Retry");
            }
          
                }
                catch(Exception e)
                {
                    System.out.println("NEAM Exception :"+e);
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
