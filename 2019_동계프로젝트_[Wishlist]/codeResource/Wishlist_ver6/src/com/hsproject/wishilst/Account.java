package com.hsproject.wishilst;
import java.sql.Date;

public class Account {
	private Date date; // ����or���� ��¥
	private int index; // �ش� ��¥������ ����or���� ǥ�� ����
	private String desc; // ����or���� ����
	private int type; // ����or���� �����ϴ� Ÿ�� (0:��������, 1:��������, 2:��������, 3:��������, 4:��ǰ��������)
	private int cost; // ����or���� �ݾ�

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
