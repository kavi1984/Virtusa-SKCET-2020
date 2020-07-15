

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "updatepass", urlPatterns = {"/updatepass"})
public class updatepass extends HttpServlet {

   
    
     private static final String DBNAME = "StockExchange";
    private static final String DB_USERNAME = "chhipa";
    private static final String DB_PASSWORD = "";
    private static final String UPDATE_QUERY = "update user set password=? where ID=? and password=?";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updatepass</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatepass at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int ID = (Integer)session.getAttribute("ID");
        String old1 = request.getParameter("oldpass").toString();
        String new1 = request.getParameter("newpass1").toString();
        
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(UPDATE_QUERY);
            prepStmt.setString(1, new1);
            prepStmt.setInt(2, ID);
            prepStmt.setString(3, old1);
            int rs = prepStmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("validateLogon: Error while validating password: "+e.getMessage());
                    try {
                        throw e;
                    } catch (Exception ex) {
                        Logger.getLogger(profile.class.getName()).log(Level.SEVERE, null, ex);
                    }
        } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(profile.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }
        response.sendRedirect("showHome");
    }

    Connection connect() throws Exception
    {
        Connection con=null;
        try
        {
            String url = "jdbc:mysql://192.168.0.102:3306/"+DBNAME+"?user="+DB_USERNAME;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
        } 
        catch (SQLException sqle) 
        {
            System.out.println("SQLException: Unable to open connection to db: "+sqle.getMessage());
            throw sqle;
        }
         catch(Exception e)
        {
            System.out.println("Exception: Unable to open connection to db: "+e.getMessage());
            throw e;
        }
        
        return con;
        
        
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
