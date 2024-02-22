package com.hsproject.wishilst.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.controller.MainController;

public class AddProductView extends JFrame implements AbstractView {

	MainController mainController;
	
	//필요한 컴포넌트 추가 목록-------------------------------------------
	//패널
	private JPanel allpan = new JPanel();
	private JPanel toppan = new JPanel(new GridLayout(0,2));
	private JPanel smallpan[] = new JPanel[8];
	private JPanel bottompan = new JPanel();
	
	//라벨
	private JLabel lblProductName = new JLabel("제품명 : ");
	private JLabel lblProductPicture = new JLabel("제품사진 : ");
	private JLabel lblProductPrice = new JLabel("금액(원) : ");
	private JLabel lblProductImportance = new JLabel("중요도 : ");
	private JLabel lblProductImportanceValue = new JLabel("50");
	
	//컴포넌트
	private JTextField tfProductName = new JTextField(15);
	private JButton btnProductPicture = new JButton("파일첨부");
	private JLabel lblProductPictureName = new JLabel("");
	private JTextField tfProductPrice = new JTextField(15);
	private JSlider sdProductImportance = new JSlider(1,100);
	private JButton btnReset = new JButton("초기화");
	private JButton btnAdd = new JButton("추가");
	
	String selectedFilePathName = null;
	
	
	
	//------------------------------------------------------------
	
	
	
	public AddProductView(MainController mainController) {
		this.mainController = mainController;

		this.setTitle("위시리스트 물건 추가하기");
		this.setSize(490, 300);

		// toppan에 붙이기
		for (int i = 0; i < smallpan.length; i++) {
			smallpan[i] = new JPanel();
			toppan.add(smallpan[i]);
		}
		
		smallpan[3].setLayout(new GridLayout(2,0));
		
		smallpan[0].add(lblProductName);
		smallpan[1].add(tfProductName);
		smallpan[2].add(lblProductPicture);
		smallpan[3].add(btnProductPicture);
		smallpan[3].add(lblProductPictureName);
		smallpan[4].add(lblProductPrice);
		smallpan[5].add(tfProductPrice);
		smallpan[6].add(lblProductImportance);
		smallpan[7].add(sdProductImportance);
		sdProductImportance.setMajorTickSpacing(1);
		sdProductImportance.setPaintTicks(true);
		sdProductImportance.setPaintTrack(true);
		sdProductImportance.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				lblProductImportanceValue.setText("" + sdProductImportance.getValue());
			}
		});
		smallpan[7].add(lblProductImportanceValue);
		
		
		
		//파일첨부 버튼 누를시 이벤트 처리
		btnProductPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = mainController.selectImageFile();
				selectedFilePathName = selectedFile.getPath();
				lblProductPictureName.setText(selectedFile.getName());
			}
			
		});

		
		// bottompan에 붙이기
		bottompan.add(btnReset);
		bottompan.add(btnAdd);
		
		
		// 추가버튼 클릭시 이벤트처리
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String newImageName = null;
				
				if(selectedFilePathName!=null)
					newImageName = mainController.copyImageFileFrom(selectedFilePathName);
				
				mainController.addProduct(tfProductName.getText(), 
						Integer.parseInt(tfProductPrice.getText()),
						Integer.parseInt(lblProductImportanceValue.getText()), 
						newImageName);
				
				dispose();
				JOptionPane.showMessageDialog(null, "제품이 추가되었습니다!");

			}
		});
		
		//초기화버튼 클릭시 이벤트처리
		btnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tfProductName.setText("");
				tfProductPrice.setText("");
				sdProductImportance.setValue(1);
				lblProductImportanceValue.setText("1");		
				lblProductPictureName.setText("");
				
			}
		});
		
		// allpan패널에 붙이기
		allpan.add(toppan);
		toppan.setBorder(BorderFactory.createTitledBorder("제품 정보"));
		allpan.add(bottompan);
		this.add(allpan);

		// 기본설정, 크기고정
		this.setVisible(false);
		this.setResizable(false);
	}

	@Override
	public void update(String msgType, Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		selectedFilePathName = null;
		tfProductName.setText("");
		tfProductPrice.setText("");
		sdProductImportance.setValue(1);
		lblProductImportanceValue.setText("1");		
		lblProductPictureName.setText("");
		this.sdProductImportance.setMaximum(mainController.getMaxPriority());
		this.setVisible(true);
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}
}
