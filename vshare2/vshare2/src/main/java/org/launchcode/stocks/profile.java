

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "profile", urlPatterns = {"/profile"})
public class profile extends HttpServlet {

    private static final String DBNAME = "StockExchange";
    private static final String DB_USERNAME = "chhipa";
    private static final String DB_PASSWORD = "";
    private static final String SELECT_QUERY = "select * from user where ID=?";
    private static final String UPDATE_QUERY = "update user set name=?, age=? where ID=?";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet profile</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet profile at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
            String strErrMsg = null;
            HttpSession session = request.getSession();
            int ID = (Integer)session.getAttribute("ID");
            Connection con;
            con = null;
            ResultSet res;

            try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(SELECT_QUERY);
                prepStmt.setInt(1, ID);
                res = prepStmt.executeQuery();
                res.next();
                
                session.setAttribute("up_login", res.getString(2));
                session.setAttribute("up_name", res.getString(4));
                session.setAttribute("up_age", res.getInt(5));
                session.setAttribute("up_cash", res.getFloat(6));
            } catch(Exception e) {
                System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            response.sendRedirect("profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int ID = (Integer)session.getAttribute("ID");
        String strName = request.getParameter("name").toString();
        int strAge = Integer.parseInt(request.getParameter("age").toString());
        
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(UPDATE_QUERY);
            prepStmt.setString(1, strName);
            prepStmt.setInt(2,strAge);
            prepStmt.setInt(3,ID);
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
        session.setAttribute("name", strName);
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
