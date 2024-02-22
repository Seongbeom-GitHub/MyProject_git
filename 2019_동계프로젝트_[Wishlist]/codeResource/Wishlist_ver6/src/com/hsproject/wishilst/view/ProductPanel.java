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
	private JPanel panTopDesc = new JPanel(); // ����, �̸� ���� �г�
	private JPanel panMiddleDesc = new JPanel(); // ���� ���� �г�
	private JPanel panBottomDesc = new JPanel(); // ���� ������ ���� �г�
	
	public ProductPanel(MainController mainController, Product product) {
		this.setPreferredSize(new Dimension(425, 130));
		this.setMaximumSize(new Dimension(425, 130));
		this.setMinimumSize(new Dimension(425, 130));
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		// �ϳ��� panDescription���� ����� �� �ִ� ������ 325x120
		// ����,������,��,�� ���� 5�� �����ϸ� 315x110
		panDescription.setSize(new Dimension(325,120));
		panDescription.setLocation(0,0);
		panDescription.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		panDescription.setLayout(null);
		panTopDesc.setLayout(null);
		panMiddleDesc.setLayout(null);
		panBottomDesc.setLayout(null);
		
		// top, middle, bottom �г� ��ġ �� ũ�� ����
		panTopDesc.setLocation(5, 5); // ����
		panTopDesc.setSize(315, 30); 
		panMiddleDesc.setLocation(5, 45); // ����
		panMiddleDesc.setSize(315, 30); 
		panBottomDesc.setLocation(5, 80); // ����
		panBottomDesc.setSize(315, 30); 
		
		/* �󺧵� ����(��Ʈ, ��ġ, ũ��, �ؽ�Ʈ) ���� */
		lblPriority.setFont(lblPriority.getFont().deriveFont(18.0f).deriveFont(Font.BOLD));
		lblPriority.setLocation(0,0);
		lblPriority.setSize(80, 20);
		lblPriority.setText("["+product.getPriority()+"����]");
		
		lblName.setFont(lblName.getFont().deriveFont(16.0f));
		lblName.setLocation(80,0);
		lblName.setSize(235, 20);
		lblName.setText(product.getName());
		
		lblPrice.setText("�ݾ�: ��"+product.getPrice());
		lblPrice.setLocation(0,0);
		lblPrice.setSize(315,20);
		
		lblPurchaseableTime.setText(product.getFormattedRemainTime());
		lblPurchaseableTime.setLocation(0,0);
		lblPurchaseableTime.setSize(315,20);
		
		// ������ �̸� ���� top�гο� �߰�
		panTopDesc.add(lblPriority);
		panTopDesc.add(lblName);
		
		// ���� ���� middle�гο� �߰�
		panMiddleDesc.add(lblPrice);
		
		// ���Ű����� ���� bottom�гο� �߰�
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
