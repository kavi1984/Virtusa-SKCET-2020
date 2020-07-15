

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "register", urlPatterns = {"/register"})
public class register extends HttpServlet {

    private static final String DBNAME = "StockExchange";
    private static final String DB_USERNAME = "chhipa";
    private static final String DB_PASSWORD = "";
    private static final String LOGIN_QUERY = "select * from user where login=?";
    private static final String INSERT_QUERY = "insert into user(login, password, name, age, cash) values(?,?,?,?,?)";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet register at " + request.getContextPath() + "</h1>");
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

            String strUserName = request.getParameter("username").toString();
            String strPassword = request.getParameter("password").toString();
            String strName = request.getParameter("name").toString();
            int strAge = Integer.parseInt(request.getParameter("age").toString());
            int cash = 1000;
            System.out.println(strUserName);
            String strErrMsg = null;
            HttpSession session = request.getSession();
            boolean isValidLogon = false;
            try {
                isValidLogon = authenticateLogin(strUserName);
                if(isValidLogon) {
                    strErrMsg = "User name not available. Please try a different one.";
                    response.sendRedirect("register.jsp");
                } else {
                    createUser(strUserName, strPassword, strName, strAge, cash);
                    response.sendRedirect("login.jsp");
                }
            } catch(Exception e) {
                strErrMsg = "Unable to validate user / password in database";
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
        
    private boolean createUser(String strUserName, String strPassword, String strName, int strAge, int cash) throws Exception {
        boolean isValid = false;
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(INSERT_QUERY);
            prepStmt.setString(1, strUserName);
            prepStmt.setString(2,strPassword);
            prepStmt.setString(3,strName);
            prepStmt.setInt(4,strAge);
            prepStmt.setInt(5,cash);
            int rs = prepStmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            throw e;
        } finally {
            con.close();
        }
        return isValid;
    }        
        
    private boolean authenticateLogin(String strUserName) throws Exception {
        boolean isValid = false;
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(LOGIN_QUERY);
            prepStmt.setString(1, strUserName);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                System.out.println("User login is valid in DB");
                isValid = true; 
            }
        } catch(Exception e) {
            System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            throw e;
        } finally {
            con.close();
        }
        return isValid;
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
