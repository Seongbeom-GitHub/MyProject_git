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
	
	//�ʿ��� ������Ʈ �߰� ���-------------------------------------------
	//�г�
	private JPanel allpan = new JPanel();
	private JPanel toppan = new JPanel(new GridLayout(0,2));
	private JPanel smallpan[] = new JPanel[8];
	private JPanel bottompan = new JPanel();
	
	//��
	private JLabel lblProductName = new JLabel("��ǰ�� : ");
	private JLabel lblProductPicture = new JLabel("��ǰ���� : ");
	private JLabel lblProductPrice = new JLabel("�ݾ�(��) : ");
	private JLabel lblProductImportance = new JLabel("�߿䵵 : ");
	private JLabel lblProductImportanceValue = new JLabel("50");
	
	//������Ʈ
	private JTextField tfProductName = new JTextField(15);
	private JButton btnProductPicture = new JButton("����÷��");
	private JLabel lblProductPictureName = new JLabel("");
	private JTextField tfProductPrice = new JTextField(15);
	private JSlider sdProductImportance = new JSlider(1,100);
	private JButton btnReset = new JButton("�ʱ�ȭ");
	private JButton btnAdd = new JButton("�߰�");
	
	String selectedFilePathName = null;
	
	
	
	//------------------------------------------------------------
	
	
	
	public AddProductView(MainController mainController) {
		this.mainController = mainController;

		this.setTitle("���ø���Ʈ ���� �߰��ϱ�");
		this.setSize(490, 300);

		// toppan�� ���̱�
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
		
		
		
		//����÷�� ��ư ������ �̺�Ʈ ó��
		btnProductPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = mainController.selectImageFile();
				selectedFilePathName = selectedFile.getPath();
				lblProductPictureName.setText(selectedFile.getName());
			}
			
		});

		
		// bottompan�� ���̱�
		bottompan.add(btnReset);
		bottompan.add(btnAdd);
		
		
		// �߰���ư Ŭ���� �̺�Ʈó��
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
				JOptionPane.showMessageDialog(null, "��ǰ�� �߰��Ǿ����ϴ�!");

			}
		});
		
		//�ʱ�ȭ��ư Ŭ���� �̺�Ʈó��
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
		
		// allpan�гο� ���̱�
		allpan.add(toppan);
		toppan.setBorder(BorderFactory.createTitledBorder("��ǰ ����"));
		allpan.add(bottompan);
		this.add(allpan);

		// �⺻����, ũ�����
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
