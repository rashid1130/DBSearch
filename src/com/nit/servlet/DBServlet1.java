package com.nit.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DBServlet1 extends HttpServlet {
	Connection con;
	PreparedStatement ps;
	int eno=0;

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","khan");
			ps = con.prepareStatement("select stdno,stdname,stddesg,stdsal from student where stdno=?");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection creation problem occures........");
		}
		
		try {
			PrintWriter pw = res.getWriter();
			res.setContentType("text/html");

			int teno = Integer.parseInt(req.getParameter("teno"));
            System.out.println("student id : "+teno);
			ps.setInt(1,teno);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
			    pw.println("<br>Student no :" + rs.getString(1));
				pw.println("<br>Student name : " + rs.getString(2));
				pw.println("<br>Student Desg : " + rs.getString(3));
				pw.println("<br>Student Salary : " + rs.getFloat(4));
				

			}

			else {
				pw.println("<br> No Student Record Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public void destroy() {
		try {
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
