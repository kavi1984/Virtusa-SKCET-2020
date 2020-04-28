package v2vcourier.login.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import sql.*;
import javax.servlet.http.*;

//import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping("/")
	public ModelAndView enter() {
	return new ModelAndView("home");
	}
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req , HttpServletResponse res) throws IOException{
	return new ModelAndView("login");
	}
	    @RequestMapping("/neww")
	    public ModelAndView neww() {
	    return new ModelAndView("neww");
	    }
	    @RequestMapping("/AboutUs")
	    public ModelAndView AboutUs() {
	    return new ModelAndView("AboutUs");
	    }
	    @RequestMapping("/check")
	    public ModelAndView check(HttpServletRequest req, HttpServletResponse res) {
	    try{
	       String username =req.getParameter("username");  
	       String password = req.getParameter("password");
	       Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
	       Connection conn=DriverManager.getConnection(  
	        "jdbc:mysql://localhost:8080/login","root","rivitha123");
	       
	       PreparedStatement pst = conn.prepareStatement("Select id,password from login.login_det");
	       ResultSet rs = pst.executeQuery();
	       while(rs.next()){
	        if (rs.getString("id").equals(username)){
	        return new ModelAndView("alertOfExisting");
	        }
	       }
	       pst = conn.prepareStatement("INSERT INTO login.login_det (id,password)VALUES (?,?)");
	       pst.setString(1, username);
	       pst.setString(2, password);
	       int n=pst.executeUpdate();
	    }
	       catch (Exception e) {
	System.out.print(e);
	}
	    return new ModelAndView("home");
	    }
	@RequestMapping(value="/validate")
	public ModelAndView test(HttpServletRequest req , HttpServletResponse res) throws IOException{
	try{
	       String username = req.getParameter("username");  
	       String password = req.getParameter("password");
	       Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
	       Connection conn=DriverManager.getConnection(  
	        "jdbc:mysql://localhost:8080/login","root","rivitha123");      
	       PreparedStatement pst = conn.prepareStatement("Select id,password from login.login_det where id=? and password=?");
	       pst.setString(1, username);
	       pst.setString(2, password);
	       ResultSet rs = pst.executeQuery();    
	       
	       if(rs.next()) {
	        return new ModelAndView("welcome");
	       }
	       
	       else {
	           return new ModelAndView("home1");
	           
	       }
	  }
	  catch(Exception e){      
	  System.out.print(e);
	  return new ModelAndView("home1");
	  }      

	}

	
	}