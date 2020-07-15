

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


@WebServlet(name = "checkLogin", urlPatterns = {"/checkLogin"})
public class checkLogin extends HttpServlet {

    private static final String DBNAME = "StockExchange";
    private static final String DB_USERNAME = "chhipa";
    private static final String DB_PASSWORD = "";
    private static final String LOGIN_QUERY = "select * from user where login=? and password=?";
    private static final String DATA_QUERY = "select o.ID, st.ticker, c.name, case when s.volume is NULL then o.volume else o.volume-s.volume end as volume, o.price from Ownership o left outer join Sell s on o.ID=s.Ownership_ID, Company c, Stock st where o.User_ID=? and o.Stock_ID=st.ID and st.Company_ID=c.ID";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           
                
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet checkLogin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet checkLogin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
    

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String strUserName = request.getParameter("username").toString();
            String strPassword = request.getParameter("password").toString();
            System.out.println(strUserName);
            String strErrMsg = null;
            HttpSession session = request.getSession();
            boolean isValid = false;
            Connection con;
            con = null;
            int id = 0;
            ResultSet rs;
            try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(LOGIN_QUERY);
                prepStmt.setString(1, strUserName);
                prepStmt.setString(2, strPassword);
                rs = prepStmt.executeQuery();
                if(rs.next()) {
                    System.out.println("User login is valid in DB");
                    isValid = true; 
                }
            
                if(isValid) {
                    id = rs.getInt("ID");
                    System.out.println(id);
                    String name = rs.getString("name");
                    System.out.println(name);
                    session.setAttribute("ID", id);
                    session.setAttribute("name", name);
                } else {
                    strErrMsg = "User name or Password is invalid. Please try again.";
                }
            } catch(Exception e) {
                System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            } 
            if(isValid) {
                session.setAttribute("loginflag", true);   
                response.sendRedirect("showHome");
            
            } else {
                session.setAttribute("errorMsg", strErrMsg);
                response.sendRedirect("login.jsp");
            }
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
