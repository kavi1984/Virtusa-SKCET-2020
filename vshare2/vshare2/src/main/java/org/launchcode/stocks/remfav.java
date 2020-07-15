

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


@WebServlet(name = "remfav", urlPatterns = {"/remfav"})
public class remfav extends HttpServlet {

    
    private static final String DBNAME = "StockExchange";
    private static final String DB_USERNAME = "chhipa";
    private static final String DB_PASSWORD = "";
    private static final String ADD_FAV="delete from Favorites where User_ID=? and Stock_ID=?";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet remfav</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet remfav at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            HttpSession session = request.getSession();
            int ID=(Integer)session.getAttribute("ID");
            int SID=(Integer)session.getAttribute("REMFAV_SID");
            Connection con;
            con = null;
            int id = 0;
            System.out.println(id);
            String name = "Anonymous";
            System.out.println(name);
            int res;   
            try {
                con=connect(); 
                PreparedStatement prepStmt;
                prepStmt = con.prepareStatement(ADD_FAV);
                prepStmt.setInt(1,ID);
                prepStmt.setInt(2,SID);
                res = prepStmt.executeUpdate();
             
             } catch(Exception e) {
                System.out.println("settingAttribute: Error while executing querry and setting attribute: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            response.sendRedirect("showFavorites");
 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
