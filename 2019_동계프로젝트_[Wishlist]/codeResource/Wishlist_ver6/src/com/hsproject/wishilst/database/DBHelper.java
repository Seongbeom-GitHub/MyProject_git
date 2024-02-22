package com.hsproject.wishilst.database;

/*
 * 데이터베이스 연동 클래스
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
			System.out.println("JDBC 드라이버 연결 성공");
			
			con = DriverManager.getConnection(url, db_user, db_password);
			System.out.println("DB 연결 성공");
			
		} catch (ClassNotFoundException e) {
			con = null;
			e.printStackTrace();
			System.out.println("JDBC 드라이버 연결 오류");
			
		} catch (SQLException e) {
			con = null;
			e.printStackTrace();
			System.out.println("DB 연결 오류");
		}
		
		/*
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from user where id='" + machine_id +"'");
			rs.last();      
	        int rowcount = rs.getRow();
	        rs.beforeFirst();
	        
	        if(rowcount < 1) {
				System.out.println("아이디 오류");
				return;
			}else {
				rs.first();
			}
	        
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Statement 생성 오류");
			System.out.println("SQL 실행 오류");
		}
		try {
			if(machine_pw.equals(rs.getString("pw"))){
				System.out.println("Machine ID: " + rs.getString("id"));
				System.out.println("Machine Name: " + rs.getString("name"));
				this.userID = rs.getString("id");
			}else {
				System.out.println("비밀번호 오류");
				return;
			}
		} catch (SQLException e) {
			System.out.println("테이블 읽기 오류");
			
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
