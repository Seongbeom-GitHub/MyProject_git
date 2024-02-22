package com.hsproject.wishilst.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.controller.MainController;
import com.hsproject.wishilst.model.UserModel;;

public class MainView extends JFrame implements AbstractView {
	
	private MainController mainController;
	private UserModel usermodel;
	
	private JButton btnTest = new JButton("�׽�Ʈ");
	private JPanel productListPanel = new JPanel();
	private JScrollPane pane = new JScrollPane(productListPanel);
	
	public MainView(MainController mainController) {
		this.mainController = mainController;
		
		this.setTitle("���ø���Ʈ ���� ���α׷�");
		
		this.setSize(460,700);
		
		// ��ũ�ѹ� �׻� ǥ��
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
		this.add(pane);

		/* ���� ���ٿ� �޴��� ���� �� ������ ����*/
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("�޴�");
		JMenuItem menuRefresh = new JMenuItem("���ΰ�ħ");
		JMenuItem menuAdd = new JMenuItem("��ǰ�߰�");
		JMenuItem menuAdd2 = new JMenuItem("����� �߰�");
		JMenuItem menuSetting = new JMenuItem("����");
		JMenuItem menuUserSetting = new JMenuItem("����� ����");
		
		menuRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* ��ǰ ���� �����ð� ���ΰ�ħ */
				mainController.updateProductRemainTimeAll();
			}
			
		});

		menuAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* ������ Ȱ��ȭ */
				mainController.showAddProductView();
			}
			
		});
		
		menuAdd2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* ������ Ȱ��ȭ */
				mainController.showAddAccountView();
			}
			
		});
		
		
		menuUserSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* ������ Ȱ��ȭ */
				new UserSettingView(mainController);
			}
			
		});

		jm.add(menuRefresh);
		jm.add(menuAdd);
		jm.add(menuAdd2);
		jm.add(menuSetting);
		jm.add(menuUserSetting);
		jmb.add(jm);
		this.setJMenuBar(jmb);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update(String msgType, Object o) {
		if(msgType.equals("ProductList")) {
			productListPanel.removeAll();
			
			Vector<Product> v = (Vector<Product>) o;
			for(int i=0; i<v.size(); i++) {
				productListPanel.add(new ProductPanel(mainController, v.get(i)));
			}

			this.revalidate();
			this.repaint();
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
