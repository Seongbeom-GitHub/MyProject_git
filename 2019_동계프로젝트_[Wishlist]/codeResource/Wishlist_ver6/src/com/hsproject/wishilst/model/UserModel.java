package com.hsproject.wishilst.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hsproject.wishilst.Account;
import com.hsproject.wishilst.User;
import com.hsproject.wishilst.database.DBHelper;

/*
+-------------+-------------+------+-----+---------+-------+
| Field       | Type        | Null | Key | Default | Extra |
+-------------+-------------+------+-----+---------+-------+
| id          | varchar(20) | NO   | PRI | NULL    |       |
| pw          | varchar(20) | NO   |     | NULL    |       |
| balance     | int(11)     | YES  |     | NULL    |       |
| f_expense   | int(11)     | YES  |     | NULL    |       |
| f_income    | int(11)     | YES  |     | NULL    |       |
| min_balance | int(11)     | NO   |     | NULL    |       |
+-------------+-------------+------+-----+---------+-------+
 */
public class UserModel {
	User user;
	
	public UserModel() {
		
	}
	
	/* �ܰ� �ҷ����� */
	public int getBalance() {
		return user.getBalance();
	}
	
	/* �������� �ҷ����� */
	public int getF_income() {
		return user.getF_income();
	}
	
	/* �������� �ҷ����� */
	public int getF_expense() {
		return user.getF_expense();
	}
	
	/* �ּҺ����ݾ� �ҷ����� */
	public int getF_minbalance() {
		return user.getF_minbalance();
	}
	
	/* �ܰ� �����ϱ� */
	public void setBalance(int balance) {
		String sql = "update user set balance=? where id=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setInt(1, balance);
			pstmt.setString(2, this.user.getUserID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.user.setBalance(balance);
	}
	
	/* �������� �����ϱ� */
	public void setF_income(int f_income) {
		String sql = "update user set f_income=? where id=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setInt(1, f_income);
			pstmt.setString(2, this.user.getUserID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.user.setF_income(f_income);
	}
	
	/* �������� �����ϱ� */
	public void setF_expense(int f_expense) {
		String sql = "update user set balance=? where id=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setInt(1, f_expense);
			pstmt.setString(2, this.user.getUserID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.user.setF_expense(f_expense);
	}
	
	/* �ּҺ����ݾ� �����ϱ� */
	public void setF_minbalance(int minbalance) {
		String sql = "update user set balance=? where id=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setInt(1, minbalance);
			pstmt.setString(2, this.user.getUserID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.user.setF_minbalance(minbalance);
	}
	
	public User setUserFromDBByIDPW(String userID, String password) {
		if(DBHelper.logInUserByIDPW(userID, password)) {
			
			try {
				Statement stmt = DBHelper.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select * from user where id='" + userID +"'");
				while(rs.next()) {
					this.user = new User(rs.getString(1),
							rs.getInt(3),
							rs.getInt(4),
							rs.getInt(5),
							rs.getInt(6)
							);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.user = user;
		}
		
		return user;
	}

	public User getUser() {
		
		return user;
	}
}
