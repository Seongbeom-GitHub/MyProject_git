package com.hsproject.wishilst.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.hsproject.wishilst.Account;
import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.User;
import com.hsproject.wishilst.database.DBHelper;

/* table account
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | varchar(20) | NO   | PRI | NULL    |                |
| date  | date        | NO   | PRI | NULL    |                |
| index | int(11)     | NO   | PRI | NULL    | auto_increment |
| desc  | varchar(30) | NO   |     | NULL    |                |
| type  | int(11)     | NO   |     | NULL    |                |
| cost  | int(11)     | NO   |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+
 */

public class AccountModel {
	User user;
	
	Vector<Account> vAccount = new Vector<Account>();
	
	public AccountModel() {
		/* 테스트용 코드 */
		/*
		
		*/
		/* 테스트용 코드끝 */
	}
	
	public void setUser(User user) {
		this.user = user;
		/* DB에서 제품목록 불러오기 */
		updateAccountListFromDB();
	}
	
	public void updateAccountListFromDB() {
		try {
			Statement stmt = DBHelper.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from account where id='" + DBHelper.getUserID() +"' order by date desc, 'index' desc");
			while(rs.next()) {
				vAccount.add(new Account(rs.getDate("date"), rs.getInt("index"), 
						rs.getString("desc"), rs.getInt("type"), rs.getInt("cost")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<Account> getAccountListAsVector(){
		
		return vAccount;
	}
	
	public void addAccount(Account account) {
		String sql = "insert into account(id,`date`,`index`,`desc`,type,cost) values(?,?,?,?,?,?);";
		
		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setDate(2, account.getDate());
			pstmt.setNull(3,java.sql.Types.INTEGER);
			pstmt.setString(4, account.getDesc());
			pstmt.setInt(5, account.getType());
			pstmt.setInt(6, account.getCost());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateAccountListFromDB();
	}
	
	public void deleteAccount(Date date, int index) {
		String sql = "delete from account where id=? and `date`=? and `index`=?;";
		
		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setDate(2, date);
			pstmt.setInt(3, index);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<vAccount.size(); i++) {
			if(date==vAccount.get(i).getDate()) {
				if(index==vAccount.get(i).getIndex()) {
					vAccount.remove(i);
					break;
				}
			}
		}
	}
}
