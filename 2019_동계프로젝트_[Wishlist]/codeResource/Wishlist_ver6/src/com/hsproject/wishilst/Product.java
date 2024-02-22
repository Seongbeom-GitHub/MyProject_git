package com.hsproject.wishilst;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Product implements Comparable<Product> {
	private String name; // 상품 이름
	private int price; // 상품 가격
	private int priority; // 상품 우선순위
	private String imageName; // 상품 이미지 파일 이름
	private ImageIcon image; // 상품 이미지
	private double remainHour = -1; // 상품 구매까지 남은 시간 (unix time difference.) -1: 설정 안됨
	
	
	public Product(String name, int price, int priority, String imageName) {
		super();
		this.name = name;
		this.price = price;
		this.priority = priority;
		this.imageName = imageName;
		this.image = new ImageIcon("image/"+imageName);
		//크기조절
		this.image = new ImageIcon(this.image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getImageName() {
		return imageName;
	}
	public ImageIcon getImage() {
		return image;
	}
	public double getRemainTime() {
		return remainHour;
	}
	public void setRemainTime(double remainTime) {
		this.remainHour = remainTime;
	}
	
	public String getFormattedRemainTime() {
		int day;
		double hour;
		
		day = (int) (remainHour / 24);
		hour = remainHour%24.0;
		
		return day==0 && hour<24 ? "오늘 구매 가능!" : "예상구매 가능일: " + (day + "일 후" + (int)hour + "시간");
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
		this.image = new ImageIcon("image/"+imageName);
		//크기조절
		this.image = new ImageIcon(this.image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	}
	
	public boolean isPurchasable() {
		return remainHour<24;
	}

	@Override
	public int compareTo(Product o) {
		return this.getPriority() > o.getPriority() ?1:-1;
	}
	
}
