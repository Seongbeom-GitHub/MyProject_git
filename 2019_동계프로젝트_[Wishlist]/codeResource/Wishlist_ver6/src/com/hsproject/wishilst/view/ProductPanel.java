package com.hsproject.wishilst.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.controller.MainController;

public class ProductPanel extends JPanel {
	
	private JLabel lblPriority = new JLabel();
	private JLabel lblName = new JLabel();
	private JLabel lblPrice = new JLabel();
	private JLabel lblPurchaseableTime = new JLabel();
	
	private JButton imgProduct = new JButton();
	
	private JPanel panDescription = new JPanel();
	private JPanel panTopDesc = new JPanel(); // 순위, 이름 포함 패널
	private JPanel panMiddleDesc = new JPanel(); // 가격 포함 패널
	private JPanel panBottomDesc = new JPanel(); // 구매 가능일 포함 패널
	
	public ProductPanel(MainController mainController, Product product) {
		this.setPreferredSize(new Dimension(425, 130));
		this.setMaximumSize(new Dimension(425, 130));
		this.setMinimumSize(new Dimension(425, 130));
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		// 하나의 panDescription에서 사용할 수 있는 공간은 325x120
		// 왼쪽,오른쪽,위,밑 여백 5씩 제외하면 315x110
		panDescription.setSize(new Dimension(325,120));
		panDescription.setLocation(0,0);
		panDescription.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		panDescription.setLayout(null);
		panTopDesc.setLayout(null);
		panMiddleDesc.setLayout(null);
		panBottomDesc.setLayout(null);
		
		// top, middle, bottom 패널 위치 및 크기 설정
		panTopDesc.setLocation(5, 5); // 여백
		panTopDesc.setSize(315, 30); 
		panMiddleDesc.setLocation(5, 45); // 여백
		panMiddleDesc.setSize(315, 30); 
		panBottomDesc.setLocation(5, 80); // 여백
		panBottomDesc.setSize(315, 30); 
		
		/* 라벨들 정보(폰트, 위치, 크기, 텍스트) 설정 */
		lblPriority.setFont(lblPriority.getFont().deriveFont(18.0f).deriveFont(Font.BOLD));
		lblPriority.setLocation(0,0);
		lblPriority.setSize(80, 20);
		lblPriority.setText("["+product.getPriority()+"순위]");
		
		lblName.setFont(lblName.getFont().deriveFont(16.0f));
		lblName.setLocation(80,0);
		lblName.setSize(235, 20);
		lblName.setText(product.getName());
		
		lblPrice.setText("금액: ￦"+product.getPrice());
		lblPrice.setLocation(0,0);
		lblPrice.setSize(315,20);
		
		lblPurchaseableTime.setText(product.getFormattedRemainTime());
		lblPurchaseableTime.setLocation(0,0);
		lblPurchaseableTime.setSize(315,20);
		
		// 순위와 이름 정보 top패널에 추가
		panTopDesc.add(lblPriority);
		panTopDesc.add(lblName);
		
		// 가격 정보 middle패널에 추가
		panMiddleDesc.add(lblPrice);
		
		// 구매가능일 정보 bottom패널에 추가
		panBottomDesc.add(lblPurchaseableTime);
		
		panDescription.add(panTopDesc);
		panDescription.add(panMiddleDesc);
		panDescription.add(panBottomDesc);
		
		imgProduct.setLocation(325, 10);
		imgProduct.setSize(100, 100);
		imgProduct.setIcon(product.getImage());
		
		this.add(panDescription);
		this.add(imgProduct);
		
		imgProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.deleteProduct(product);
			}
			
		});
	}
}
