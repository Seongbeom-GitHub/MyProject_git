package com.hsproject.wishilst.database;

/*
 * �����ͺ��̽� ���� Ŭ����
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.hsproject.wishilst.Product;

public class DBHelper {

	private static Connection con = null;	
	private static boolean loggedIn = false; // test phase. always logged in state
	private static String userID = null; // null means anybody logged in
	
	private static void getInitConnection(){
		
        String url ="jdbc:mysql://kara.iptime.org:3306/wishlist?characterEncoding=UTF-8&serverTimezone=UTC";
        String db_user = "wishuser";
        String db_password = "wish1234";
		
        Statement stmt;
    	PreparedStatement pstmt = null;
    	ResultSet rs;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC ����̹� ���� ����");
			
			con = DriverManager.getConnection(url, db_user, db_password);
			System.out.println("DB ���� ����");
			
		} catch (ClassNotFoundException e) {
			con = null;
			e.printStackTrace();
			System.out.println("JDBC ����̹� ���� ����");
			
		} catch (SQLException e) {
			con = null;
			e.printStackTrace();
			System.out.println("DB ���� ����");
		}
		
		/*
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from user where id='" + machine_id +"'");
			rs.last();      
	        int rowcount = rs.getRow();
	        rs.beforeFirst();
	        
	        if(rowcount < 1) {
				System.out.println("���̵� ����");
				return;
			}else {
				rs.first();
			}
	        
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Statement ���� ����");
			System.out.println("SQL ���� ����");
		}
		try {
			if(machine_pw.equals(rs.getString("pw"))){
				System.out.println("Machine ID: " + rs.getString("id"));
				System.out.println("Machine Name: " + rs.getString("name"));
				this.userID = rs.getString("id");
			}else {
				System.out.println("��й�ȣ ����");
				return;
			}
		} catch (SQLException e) {
			System.out.println("���̺� �б� ����");
			
			e.printStackTrace();
		}
		*/
	}
	
	public static String getUserID() {
		return userID;
	}
	
	public static boolean logInUserByIDPW(String id, String password) {
		userID = id;
		
		return true; // test phase. always return true
	}
	
	public static synchronized Connection getConnection() {
		if(con==null)
			getInitConnection();
		
		return con;
	}
	
}
