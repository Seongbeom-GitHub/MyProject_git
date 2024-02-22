package com.hsproject.wishilst.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.User;
import com.hsproject.wishilst.database.DBHelper;

/*
 * table Product
+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| id       | varchar(20) | NO   | PRI | NULL    |       |
| name     | varchar(50) | NO   |     | NULL    |       |
| price    | int(11)     | NO   |     | NULL    |       |
| priority | int(11)     | NO   | PRI | NULL    |       |
| image    | varchar(30) | YES  |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+
 */


public class ProductModel {

	User user;
	private Vector<Product> vProduct = new Vector<Product>();

	public ProductModel() {
		/* �׽�Ʈ�� �ڵ� */
		/*
		vProduct.add(new Product("�ڵ�ũ��", 14000, 1, "handcream.jpg"));
		vProduct.add(new Product("���콺", 30000, 2, "mouse.jpg"));
		vProduct.add(new Product("Ű����", 150000, 3, null));
		vProduct.add(new Product("�ð�", 50000, 4, null));
		vProduct.add(new Product("�Ź�", 10000, 5, null));
		vProduct.add(new Product("��", 59000, 6, null));
		 */
		/* �׽�Ʈ�� �ڵ峡 */
	}

	public void setUser(User user) {
		this.user = user;
		updateAccountListFromDB();
	}

	public void updateAccountListFromDB() {
		/* DB���� ��ǰ��� �ҷ����� */
		try {
			Statement stmt = DBHelper.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from product where id='" + DBHelper.getUserID() +"' order by priority");
			while(rs.next()) {
				Product product = new Product(rs.getString("name"), rs.getInt("price"), 
						rs.getInt("priority"), rs.getString("image"));

				vProduct.add(product);

			}

			// ���Ű����ϼ� ���
			this.updateRemainTimeAll();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector<Product> getProductListAsVector() {
		/* ���� */
		Collections.sort(vProduct);

		return vProduct;
	}

	public int getProductSize() {
		return vProduct.size();
	}

	public Product getProductByPriority(int productPriority) {
		for(int i=0; i<vProduct.size(); i++) {
			if(vProduct.get(i).getPriority() == productPriority) return vProduct.get(i);
		}
		return null;
	}

	public void addProduct(Product product) {
		String sql = "insert into product(id,name,price,priority,image) values(?,?,?,?,?);";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getPriority());
			pstmt.setString(5, product.getImageName());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���Ű����ϼ� ���
		this.d_time_Calculation(product);

		vProduct.add(product);
	}

	public void deleteProduct(int productPriority) {
		String sql = "delete from product where id=? and priority=?;";
		String sql2 = "update product set priority=priority-1 where id=? and priority>=? order by priority";

		/*
		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setInt(2, productPriority);
			pstmt.executeUpdate();
			// ����
			pstmt = DBHelper.getConnection().prepareStatement(sql2);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setInt(2, productPriority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 */
		for(int i=0; i<vProduct.size(); i++) {
			if(productPriority==vProduct.get(i).getPriority()) {
				vProduct.remove(i);
				break;
			}
		}
		/* ���� */
		for(int i=0; i<vProduct.size(); i++) {
			if(productPriority <= vProduct.get(i).getPriority()) {
				vProduct.get(i).setPriority(vProduct.get(i).getPriority()-1);
			}
		}

		// ���Ű����ϼ� ���
		this.updateRemainTimeAll();
	}

	public void modifyName(int productPriority, String newName) {
		String sql = "update product set name=? where id=? and priority=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, newName);
			pstmt.setString(2, DBHelper.getUserID());
			pstmt.setInt(3, productPriority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<vProduct.size(); i++) {
			if(productPriority==vProduct.get(i).getPriority()) {
				vProduct.get(i).setName(newName);
				break;
			}
		}
	}

	public void modifyPrice(int productPriority, int newPrice) {
		String sql = "update product set price=? where id=? and priority=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setInt(1, newPrice);
			pstmt.setString(2, DBHelper.getUserID());
			pstmt.setInt(3, productPriority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<vProduct.size(); i++) {
			if(productPriority==vProduct.get(i).getPriority()) {
				vProduct.get(i).setPrice(newPrice);;
				break;
			}
		}

		// ���Ű����ϼ� ���
		this.updateRemainTimeAll();
	}

	public void modifyPriority(int productPriority, int newPriority) {
		String sql = "update product set priority=? where id=? and priority=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setInt(1, newPriority);
			pstmt.setString(2, DBHelper.getUserID());
			pstmt.setInt(3, productPriority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<vProduct.size(); i++) {
			if(productPriority==vProduct.get(i).getPriority()) {
				vProduct.get(i).setPriority(newPriority);
				break;
			}
		}

		// ���Ű����ϼ� ���
		this.updateRemainTimeAll();
	}

	public int getContinuousPriorityFrom(int startPriority) {
		if(this.isDuplicatedPriorityExist(startPriority))
			return this.getContinuousPriorityFrom(startPriority+1);
		else
			return startPriority;
	}

	public boolean isDuplicatedPriorityExist(int priority) {
		for(int i=0; i<vProduct.size(); i++) {
			if(priority==vProduct.get(i).getPriority()) {
				return true;
			}
		}
		return false;
	}

	public void pushProductPriorityFromTo(int startPriority, int endPriority) {
		System.out.println("ProductModel: pushProductPriorityFromTo("+startPriority+", "+endPriority+")");
		String sql = "update product set priority=priority+1 where id=? and priority>=? and priority<=? order by priority DESC";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setInt(2, startPriority);
			pstmt.setInt(3, endPriority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<vProduct.size(); i++) {
			if(startPriority <= vProduct.get(i).getPriority() && vProduct.get(i).getPriority() <= endPriority ) {
				vProduct.get(i).setPriority(vProduct.get(i).getPriority()+1);
			}
		}

		// ���Ű����ϼ� ���
		this.updateRemainTimeAll();
	}

	public void swapProductPriority(int oldPriority, int newPriority) {
		String sql = "update product as t1, product as t2 "
				+ "set t1.priority=t2.priority, t2.priority=t1.priority "
				+ "where t1.id=? and t2.id=? and t1.priority=? and t2.priority=?";

		try {
			PreparedStatement pstmt = DBHelper.getConnection().prepareStatement(sql);
			pstmt.setString(1, DBHelper.getUserID());
			pstmt.setString(2, DBHelper.getUserID());
			pstmt.setInt(3, oldPriority);
			pstmt.setInt(4, newPriority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<vProduct.size(); i++) {
			if(oldPriority==vProduct.get(i).getPriority()) {
				vProduct.get(i).setPriority(newPriority);
				continue;
			}
			if(newPriority==vProduct.get(i).getPriority()) {
				vProduct.get(i).setPriority(oldPriority);
				continue;
			}
		}

		// ���Ű����ϼ� ���
		this.updateRemainTimeAll();

	}

	public int getMaxPriority() {
		try {
			Statement stmt = DBHelper.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select max(priority) from product where id='" + DBHelper.getUserID() +"'");
			while(rs.next()) {
				return rs.getInt(1)+1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	// product remaintime set �Լ� ������ ��ü �ذ��ϰ� ���� 
	// �켱�������� �������� ����

	/* Ư�� ��ǰ ���� ���Ű��ɽð� ��� */
	public void updateRemainTime(Product product)
	{
		double lastRemainTime = 0;

		// ����ϱ� �� �켱�������� ��ǰ ����Ʈ ����
		Collections.sort(vProduct);

		/* �켱������ ���� ���Žð� ������ */
		for(int i=1; i<product.getPriority(); i++) {
			lastRemainTime += d_time_Calculation(vProduct.get(i));
		}

		// ���Ű��ɽð� ����
		product.setRemainTime(d_time_Calculation(product));
	}

	/* ��ü ��ǰ ���� ���Ű��ɽð� ������Ʈ */
	public void updateRemainTimeAll() {
		double lastRemainTime = 0;

		// ����ϱ� �� �켱�������� ��ǰ ����Ʈ ����
		Collections.sort(vProduct);

		/* �켱������ ���� ���Žð� ������ */
		for(int i=0; i<vProduct.size(); i++) {
			lastRemainTime += d_time_Calculation(vProduct.get(i));

			// ���Ű��ɽð� ����
			vProduct.get(i).setRemainTime(lastRemainTime);
		}
	}


	/* ���� �߰� */

	//1�ð��� ������ ���ϴ� �Լ�
	// * ��Ȯ�� double Ÿ������ ���� - ���� // �ð��� �����ִ� �ݾ�??
	// �׷� ���࿡ ���� ���� ������ �ð��� ������ �ð��� �����ִ� �ݾ��� �þ�� �����ʳ�?? ���̴ϱ�.
	// ���� �Ĵ�θ� �̰� ��� ���ؼ� �ð��� �����ų� �Ϸ簡 ������ ���̰� �پ���� �ʴ� ��Ȳ�� �߻���.

	// �̹����� ���ۺ��� ������� �帥 �ð� ����Ͽ�, �ܿ� �ݾ׸�ŭ �ð��� �ݾ��� �����ִ� ���ο� ���� �ʿ�.

	// ���� F_income�� F_expense�� �Ѵ� �����ϱ� �׳� ������ ����ϸ� �ȵǰ�
	// (F_income-F_expense)�� �ش� ���� �� ���� ������(�Ϸ絿�� ���̴� �ݾ�) �װ� �� 24�� ������(�ð��� ���̴� �ݾ�)���ٰ�
	// ���� ���ۺ��� ������� �帥 �ð��� ���Ͽ� ������� �ɵ�??

	// ���� ���� �Ǹ� ���� �ܾ��� ���� �� �ܰ�� �̿��ϴ� �۾��� �ʿ�(Account ����)

	// �ð��� �����ִ� �ݾ� ( money / month_day_count )
	
	public double amount_per_hour_Calculation() { 
		// 1�ð��� ���� ���� �ݾ� (amount per hour) < �޾ƿ;ߵ� (1000���� test��)
		//�ʿ��� ���� : ���� �� �ܰ�, ��������, ��������, �ּҺ����ݾ�, ����, ����, �ش� ���� �� ��
		//��� : {���� �� �ܰ� + (���� ���� - ���� ����) - �ּҺ����ݾ� } / �ش� ���� �� �� <<mounth_last_day()
		//�����̳� ������ ������ �ʿ䰡 ���°� �̰� ���� �� �ܰ� ��ȭ�� ����ϱ� ���⼭ �߰������� ���ϰ� ??�ʿ䰡 ���� ��������
		double aph = (double) ( user.getBalance() + (user.getF_income() - user.getF_expense()) - user.getF_minbalance() ) / (double)mounth_last_day();

		return aph ;
	}


	//���� ������ �ϼ��� ��� (���� : ����)
	public int mounth_last_day()
	{
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);

		cal.set(year, month-1, day); //���� -1����� �ش���� �ν�

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}


	//���ű��� ���� �ϼ� ����ϴ� �Լ�
	//������ x�� �� ����! = ��(day) ������ �ø�
	//�������� D_Day�� �� ������ �з��� �Ǵµ� �̰Ŵ� ���⺸�� ����ϴ� �κ�(������)���� �����ϴ°� �� ���ƺ��� (�׳� ���ϱ⸸ ���ָ��)
	/*
	 	for(int i = 0; i < ��ǰ�� ���� ;i++) {
	 		if(i�� �߿䵵�� ��ǰ�� �ִ�){
				if(this�� �߿䵵 < i�� �߿䵵) this�� d_day + i�� d_day;
			}
		}

	 */

	// ��Ȯ�ϰ� �ϱ����� ���� ��� "�Ҽ����� ������ ���� �ð�"�� ��ȯ�ϰ� ����. �ϼ� ó���� ProductŬ��������. - ����
	public double d_time_Calculation(Product product) { 
		double d_day = 0; // ���ű��� ���� �� ��

		// �ݾ� / ����/�ݾ� = �ݾ� * �ð�/�ݾ� = �ð�(����)
		//��ǰ�� �ݾ� = ��ǰ�� �ݾ� / 1�ð��� ���� �ݾ� 
		double price_p_aph = product.getPrice() / amount_per_hour_Calculation();


		//�� ������ �ø�
		//if (price_p_aph % 24 != 0) d_day = ( price_p_aph / 24 ) + 1;
		//else d_day = price_p_aph / 24;


		return price_p_aph;
		//return d_day;
	}

}
