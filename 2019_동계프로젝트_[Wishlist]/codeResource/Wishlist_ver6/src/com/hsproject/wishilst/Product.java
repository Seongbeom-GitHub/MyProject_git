package com.hsproject.wishilst;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Product implements Comparable<Product> {
	private String name; // ��ǰ �̸�
	private int price; // ��ǰ ����
	private int priority; // ��ǰ �켱����
	private String imageName; // ��ǰ �̹��� ���� �̸�
	private ImageIcon image; // ��ǰ �̹���
	private double remainHour = -1; // ��ǰ ���ű��� ���� �ð� (unix time difference.) -1: ���� �ȵ�
	
	
	public Product(String name, int price, int priority, String imageName) {
		super();
		this.name = name;
		this.price = price;
		this.priority = priority;
		this.imageName = imageName;
		this.image = new ImageIcon("image/"+imageName);
		//ũ������
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
		
		return day==0 && hour<24 ? "���� ���� ����!" : "���󱸸� ������: " + (day + "�� ��" + (int)hour + "�ð�");
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
		this.image = new ImageIcon("image/"+imageName);
		//ũ������
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
