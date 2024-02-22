package com.hsproject.wishilst;
import java.sql.Date;

public class Account {
	private Date date; // 수입or지출 날짜
	private int index; // 해당 날짜에서의 수입or지출 표현 순번
	private String desc; // 수입or지출 설명
	private int type; // 수입or지출 결정하는 타입 (0:고정수입, 1:고정지출, 2:유동수입, 3:유동지출, 4:제품구매지출)
	private int cost; // 수입or지출 금액

	public Account(Date date, int index, String desc, int type, int cost) {
		super();
		this.date = date;
		this.index = index;
		this.desc = desc;
		this.type = type;
		this.cost = cost;
	}

	public Account(Date date, String desc, int type, int cost) {
		super();
		this.date = date;
		this.desc = desc;
		this.type = type;
		this.cost = cost;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
}
