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
		/* 테스트용 코드 */
		/*
		vProduct.add(new Product("핸드크림", 14000, 1, "handcream.jpg"));
		vProduct.add(new Product("마우스", 30000, 2, "mouse.jpg"));
		vProduct.add(new Product("키보드", 150000, 3, null));
		vProduct.add(new Product("시계", 50000, 4, null));
		vProduct.add(new Product("신발", 10000, 5, null));
		vProduct.add(new Product("옷", 59000, 6, null));
		 */
		/* 테스트용 코드끝 */
	}

	public void setUser(User user) {
		this.user = user;
		updateAccountListFromDB();
	}

	public void updateAccountListFromDB() {
		/* DB에서 제품목록 불러오기 */
		try {
			Statement stmt = DBHelper.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from product where id='" + DBHelper.getUserID() +"' order by priority");
			while(rs.next()) {
				Product product = new Product(rs.getString("name"), rs.getInt("price"), 
						rs.getInt("priority"), rs.getString("image"));

				vProduct.add(product);

			}

			// 구매가능일수 계산
			this.updateRemainTimeAll();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector<Product> getProductListAsVector() {
		/* 정렬 */
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
		// 구매가능일수 계산
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
			// 땡겨
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
		/* 땡겨 */
		for(int i=0; i<vProduct.size(); i++) {
			if(productPriority <= vProduct.get(i).getPriority()) {
				vProduct.get(i).setPriority(vProduct.get(i).getPriority()-1);
			}
		}

		// 구매가능일수 계산
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

		// 구매가능일수 계산
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

		// 구매가능일수 계산
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

		// 구매가능일수 계산
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

		// 구매가능일수 계산
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

	// product remaintime set 함수 내에서 자체 해결하게 수정 
	// 우선순위별로 더해지게 수정

	/* 특정 제품 남은 구매가능시간 계산 */
	public void updateRemainTime(Product product)
	{
		double lastRemainTime = 0;

		// 계산하기 전 우선순위별로 제품 리스트 정렬
		Collections.sort(vProduct);

		/* 우선순위에 따라 구매시간 더해줌 */
		for(int i=1; i<product.getPriority(); i++) {
			lastRemainTime += d_time_Calculation(vProduct.get(i));
		}

		// 구매가능시간 설정
		product.setRemainTime(d_time_Calculation(product));
	}

	/* 전체 제품 남은 구매가능시간 업데이트 */
	public void updateRemainTimeAll() {
		double lastRemainTime = 0;

		// 계산하기 전 우선순위별로 제품 리스트 정렬
		Collections.sort(vProduct);

		/* 우선순위에 따라 구매시간 더해줌 */
		for(int i=0; i<vProduct.size(); i++) {
			lastRemainTime += d_time_Calculation(vProduct.get(i));

			// 구매가능시간 설정
			vProduct.get(i).setRemainTime(lastRemainTime);
		}
	}


	/* 연희 추가 */

	//1시간당 얼마인지 구하는 함수
	// * 정확한 double 타입으로 수정 - 재혁 // 시간당 쓸수있는 금액??
	// 그럼 만약에 돈을 쓰지 않으면 시간이 갈수록 시간당 쓸수있는 금액이 늘어나야 되질않나?? 쌓이니까.
	// 지금 식대로면 이걸 고려 안해서 시간이 지나거나 하루가 지나도 디데이가 줄어들지 않는 상황이 발생함.

	// 이번달의 시작부터 현재까지 흐른 시간 고려하여, 잔여 금액만큼 시간당 금액을 더해주는 새로운 계산식 필요.

	// 따라서 F_income과 F_expense는 한달 단위니까 그냥 통으로 취급하면 안되고
	// (F_income-F_expense)를 해당 월의 일 수로 나누어(하루동안 쌓이는 금액) 그걸 또 24로 나눈거(시간당 쌓이는 금액)에다가
	// 월의 시작부터 현재까지 흐른 시간을 곱하여 더해줘야 될듯??

	// 다음 달이 되면 남은 잔액을 현재 내 잔고로 이월하는 작업도 필요(Account 역할)

	// 시간당 쓸수있는 금액 ( money / month_day_count )
	
	public double amount_per_hour_Calculation() { 
		// 1시간당 내가 버는 금액 (amount per hour) < 받아와야됨 (1000원은 test값)
		//필요한 변수 : 현재 내 잔고, 고정수익, 고정지출, 최소보유금액, 수익, 지출, 해당 월의 일 수
		//계산 : {현재 내 잔고 + (고정 수익 - 고정 지출) - 최소보유금액 } / 해당 월의 일 수 <<mounth_last_day()
		//수익이나 지출을 생각할 필요가 없는게 이건 현재 내 잔고에 변화가 생기니까 여기서 추가적으로 더하고 ??필요가 없음 ㅇㅋ이해
		double aph = (double) ( user.getBalance() + (user.getF_income() - user.getF_expense()) - user.getF_minbalance() ) / (double)mounth_last_day();

		return aph ;
	}


	//월의 마지막 일수를 출력 (기준 : 오늘)
	public int mounth_last_day()
	{
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);

		cal.set(year, month-1, day); //월은 -1해줘야 해당월로 인식

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}


	//구매까지 남은 일수 계산하는 함수
	//단위가 x일 뒤 기준! = 일(day) 단위로 올림
	//순위별로 D_Day가 더 해져서 밀려야 되는데 이거는 여기보단 출력하는 부분(성범햄)에서 구현하는게 더 좋아보임 (그냥 더하기만 해주면됨)
	/*
	 	for(int i = 0; i < 상품의 갯수 ;i++) {
	 		if(i의 중요도에 상품이 있다){
				if(this의 중요도 < i의 중요도) this의 d_day + i의 d_day;
			}
		}

	 */

	// 정확하게 하기위해 디데이 대신 "소숫점을 포함한 남은 시간"을 반환하게 수정. 일수 처리는 Product클래스에서. - 재혁
	public double d_time_Calculation(Product product) { 
		double d_day = 0; // 구매까지 남은 일 수

		// 금액 / 수입/금액 = 금액 * 시간/금액 = 시간(남은)
		//상품의 금액 = 상품의 금액 / 1시간당 버는 금액 
		double price_p_aph = product.getPrice() / amount_per_hour_Calculation();


		//일 단위로 올림
		//if (price_p_aph % 24 != 0) d_day = ( price_p_aph / 24 ) + 1;
		//else d_day = price_p_aph / 24;


		return price_p_aph;
		//return d_day;
	}

}
