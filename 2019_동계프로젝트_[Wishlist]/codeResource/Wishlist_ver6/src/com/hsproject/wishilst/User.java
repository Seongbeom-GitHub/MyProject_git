package com.hsproject.wishilst;

public class User {
	private String userID; // 사용자 ID
	private int balance; // 사용자 잔고
	private int fExpense; // 사용자 고정지출(한달)
	private int fIncome; // 사용자 고정수입(한달)
	private int minbalance; //최소 보유금액 <<연희 추가
	
	public User(String userID, int balance, int fExpense, int fIncome, int minbalance) {
		super();
		this.userID = userID;
		this.balance = balance;
		this.fExpense = fExpense;
		this.fIncome = fIncome;
		this.minbalance = minbalance;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getF_expense() {
		return fExpense;
	}
	public void setF_expense(int f_expense) {
		this.fExpense = f_expense;
	}
	public int getF_income() {
		return fIncome;
	}
	public void setF_income(int f_income) {
		this.fIncome = f_income;
	}
	
	//연희 추가
	public int getF_minbalance() {
		return minbalance;
	}
	public void setF_minbalance(int f_minbalance) {
		this.minbalance = f_minbalance;
	}
	//
	
}
