package com.hsproject.wishilst.view;

//import java.sql.Date;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hsproject.wishilst.controller.MainController;

public class AddAccountView extends JFrame implements AbstractView {

	private MainController mainController;

	// 필요한 컴포넌트 추가 목록-------------------------------------------
	// 패널
	private JPanel allpan = new JPanel();
	private JPanel toppan = new JPanel(new GridLayout(5, 0));
	private JPanel smallpan[] = new JPanel[5];
	private JPanel bottompan = new JPanel();

	// 컴포넌트
	private JLabel lblTime= new JLabel("");
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
	
	private JLabel lblToday[] = new JLabel[3];

	private String TodayName[] = { "년", "월", "일"};
	private JTextField[] tfToday = new JTextField[3];

	private JLabel lblContents = new JLabel("내역 : ");
	private JTextField tfContents = new JTextField(15);

	private JLabel lblPrice = new JLabel("금액 : ");
	private JTextField tfPrice = new JTextField(15);

	private JLabel lblPriceKindName[] = new JLabel[5];
	private String PriceKindName[] = { "고정수입", "고정지출", "유동수입", "유동지출" , "제품구매지출"};
	private JCheckBox cbPriceKind[] = new JCheckBox[5];

	private JButton btnReset = new JButton("초기화");
	private JButton btnAdd = new JButton("추가");
	// ------------------------------------------------------------

	
	
	public AddAccountView(MainController mainController) {
		this.mainController = mainController;

		this.setTitle("입출금 내역 추가하기");
		this.setSize(460, 270);

		// smallpan 생성 및 toppan에 붙이기
		for (int i = 0; i < smallpan.length; i++) {
			smallpan[i] = new JPanel();
			toppan.add(smallpan[i]);
		}

		//---------------------smallpan---------------------
		lblTime.setText("오늘은 " + date.format(today) + " 입니다");
		smallpan[0].add(lblTime);
		
		tfToday[0] = new JTextField("2020",4);
		tfToday[1] = new JTextField(2);
		tfToday[2] = new JTextField(2);

		for (int i = 0; i < lblToday.length; i++) {
			lblToday[i] = new JLabel(TodayName[i]);
		}
		for (int i = 0; i < lblToday.length; i++) {
			smallpan[1].add(tfToday[i]);
			smallpan[1].add(lblToday[i]);
		}
		// smallpan[1]~[3]
		smallpan[2].add(lblContents);
		smallpan[2].add(tfContents);
		smallpan[3].add(lblPrice);
		smallpan[3].add(tfPrice);

		for (int i = 0; i < lblPriceKindName.length; i++) {
			lblPriceKindName[i] = new JLabel(PriceKindName[i]);
		}
		for (int i = 0; i < cbPriceKind.length; i++) {
			cbPriceKind[i] = new JCheckBox();
			smallpan[4].add(cbPriceKind[i]);
			smallpan[4].add(lblPriceKindName[i]);
		}
		//---------------------------------------------------

		// 버튼그룹으로 묶어주기
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < cbPriceKind.length; i++) {
			bg.add(cbPriceKind[i]);
		}

		// ---------------------bottompan---------------------
		bottompan.add(btnReset);
		bottompan.add(btnAdd);
		//----------------------------------------------------
		
		// 테두리 효과 넣기
		toppan.setBorder(BorderFactory.createTitledBorder("입출금 정보"));
				
		//추가 버튼 클릭시 이벤트 발생처리
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Date 타입으로 변환
				java.sql.Date inserDay;// = (java.sql.Date) new Date();
				String date = tfToday[0].getText() + "-" + tfToday[1].getText() + "-" + tfToday[2].getText();
				
				inserDay = java.sql.Date.valueOf(date);//(java.sql.Date) format.parse(date);
					
				//수입지출의 타입을 반환
				int selectNum = 0;
				for(int i=0; i<lblPriceKindName.length; i++) {
					if(cbPriceKind[i].isSelected())
						selectNum = i;
				}
				
				mainController.addAccount(inserDay, tfContents.getText(), selectNum, Integer.parseInt(tfPrice.getText()));
			}	
		});
		
		//초기화 버튼 클릭시 이벤트 발생처리
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfToday[0].setText("2020");
				tfToday[1].setText("");
				tfToday[2].setText("");
				tfContents.setText("");
				tfPrice.setText("");
			}
		});
		


		// 각 패널들에 붙이기
		for (int i = 0; i < smallpan.length; i++) {
			toppan.add(smallpan[i]);
		}
		allpan.add(toppan);
		allpan.add(bottompan);
		this.add(allpan);
		


		// 프레임 기본설정
		this.setVisible(false);
		//this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update(String msgType, Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}

}
