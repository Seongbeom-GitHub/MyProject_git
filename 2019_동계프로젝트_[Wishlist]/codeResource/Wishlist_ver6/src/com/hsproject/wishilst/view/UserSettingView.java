package com.hsproject.wishilst.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hsproject.wishilst.User;
import com.hsproject.wishilst.controller.MainController;

public class UserSettingView extends JFrame implements AbstractView {

	MainController mainController;

	// 필요한 컴포넌트 선언 목록--------------------------------------
	// 패널
	private JPanel toppan = new JPanel();
	private JPanel midpan[] = new JPanel[3];
	private JPanel bottompan[] = new JPanel[2];
	private JPanel smallpan1[] = new JPanel[5];
	private JPanel smallpan2[] = new JPanel[5];

	private JLabel lblEx1 = new JLabel("현재 설정되어있는 값 입니다");
	private JLabel lblEx2 = new JLabel("수정을 원하시면 입력 후 확인을 눌러주세요");

	private JLabel lblUserID = new JLabel("사용자  ID : ");
	private JLabel lblBalance = new JLabel("현재 잔고 : ");
	private JLabel lblFixExpense = new JLabel("고정 지출 : ");
	private JLabel lblFixIncome = new JLabel("고정 수입 : ");
	private JLabel lblMinimumBalance = new JLabel("최소보유금액 : ");

	private JLabel lblBalance2 = new JLabel("현재 잔고 : ");
	private JLabel lblFixExpense2 = new JLabel("고정 지출 : ");
	private JLabel lblFixIncome2 = new JLabel("고정 수입 : ");
	private JLabel lblMinimumBalance2 = new JLabel("최소보유금액 : ");

	private JTextField tf[] = new JTextField[4];

	private JButton btnRefresh = new JButton("새로고침");
	private JButton btnFix = new JButton("수정하기");
	private JButton btnReset = new JButton("초기화");

	public UserSettingView(MainController mainController) {
		this.mainController = mainController;

		// 패널 생성
		midpan[0] = new JPanel();
		midpan[1] = new JPanel();
		midpan[2] = new JPanel();
		bottompan[0] = new JPanel(new GridLayout(0, 1));
		bottompan[1] = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < smallpan1.length; i++) {
			smallpan1[i] = new JPanel();
			smallpan2[i] = new JPanel();
		}
		// 윤곽선
		bottompan[0].setBorder(BorderFactory.createTitledBorder("기존"));
		bottompan[1].setBorder(BorderFactory.createTitledBorder("변경"));

		midpan[0].add(lblUserID);
		midpan[1].setLayout(new GridLayout(1, 2));
		midpan[1].add(bottompan[0]);
		midpan[1].add(bottompan[1]);

		smallpan2[0].add(lblEx2);

		for (int i = 0; i < tf.length; i++) {
			tf[i] = new JTextField(7);
		}

		smallpan2[1].add(lblBalance2);
		smallpan2[1].add(tf[0]);
		smallpan2[2].add(lblFixExpense2);
		smallpan2[2].add(tf[1]);
		smallpan2[3].add(lblFixIncome2);
		smallpan2[3].add(tf[2]);
		smallpan2[4].add(lblMinimumBalance2);
		smallpan2[4].add(tf[3]);

		for (int i = 0; i < smallpan1.length; i++) {
			bottompan[0].add(smallpan1[i]);
			bottompan[1].add(smallpan2[i]);

		}
		smallpan1[0].add(lblEx1);
		smallpan1[1].add(lblBalance);
		smallpan1[2].add(lblFixExpense);
		smallpan1[3].add(lblFixIncome);
		smallpan1[4].add(lblMinimumBalance);

		midpan[2].add(btnRefresh);
		midpan[2].add(btnFix);
		midpan[2].add(btnReset);
		
		//새로고침 버튼 클릭
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				User user = mainController.getUser();
				lblUserID.setText("사용자  ID : " + user.getUserID());
				lblBalance.setText("현재 잔고 : " + Integer.toString(user.getBalance()));
				lblFixExpense.setText("고정 지출 : " + Integer.toString(user.getF_expense()));
				lblFixIncome.setText("고정 수입 : " + Integer.toString(user.getF_income()));
				lblMinimumBalance.setText("최소보유금액 : " + Integer.toString(user.getF_minbalance()));
				JOptionPane.showMessageDialog(null, "내용을 업데이트 하였습니다!");
			}	
		});
		
		//수정버튼 클릭
		btnFix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				User user = mainController.getUser();
				int a = user.getBalance();
				int b = user.getF_expense();
				int c = user.getF_income();
				int d = user.getF_minbalance();
				if(!tf[0].getText().contentEquals("")) {
					a = Integer.parseInt(tf[0].getText());
				}
				if(!tf[1].getText().contentEquals("")) {
					b = Integer.parseInt(tf[1].getText());
				}
				if(!tf[2].getText().contentEquals("")) {
					c = Integer.parseInt(tf[2].getText());
				}
				if(!tf[3].getText().contentEquals("")) {
					d = Integer.parseInt(tf[3].getText());
				}

				mainController.setUser(a,b,c,d);
				System.out.println("사용자 정보 수정 동작 확인");
				JOptionPane.showMessageDialog(null, "내용을 수정 하였습니다!");
			}
		});
		
		//초기화 버튼 클릭
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				for (int i = 0; i < tf.length; i++) {
					tf[i].setText("");
				}

			}
		});
		
		
		
		
		
		
		
		
		
		
		
		

		toppan.add(midpan[0]);
		toppan.add(midpan[1]);
		toppan.add(midpan[2]);

		this.add(toppan);

		this.setTitle("사용자 정보");
		this.setSize(550, 300);

		// 기본설정, 크기고정
		this.setVisible(true);
		// this.setResizable(false);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update(String msgType, Object o) {
		// TODO Auto-generated method stub
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}
}
