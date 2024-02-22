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
	
	private JButton btnTest = new JButton("테스트");
	private JPanel productListPanel = new JPanel();
	private JScrollPane pane = new JScrollPane(productListPanel);
	
	public MainView(MainController mainController) {
		this.mainController = mainController;
		
		this.setTitle("위시리스트 관리 프로그램");
		
		this.setSize(460,700);
		
		// 스크롤바 항상 표시
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
		this.add(pane);

		/* 설정 접근용 메뉴바 생성 및 리스너 정의*/
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("메뉴");
		JMenuItem menuRefresh = new JMenuItem("새로고침");
		JMenuItem menuAdd = new JMenuItem("제품추가");
		JMenuItem menuAdd2 = new JMenuItem("입출금 추가");
		JMenuItem menuSetting = new JMenuItem("설정");
		JMenuItem menuUserSetting = new JMenuItem("사용자 설정");
		
		menuRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* 제품 구매 남은시간 새로고침 */
				mainController.updateProductRemainTimeAll();
			}
			
		});

		menuAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* 프레임 활성화 */
				mainController.showAddProductView();
			}
			
		});
		
		menuAdd2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* 프레임 활성화 */
				mainController.showAddAccountView();
			}
			
		});
		
		
		menuUserSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* 프레임 활성화 */
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
