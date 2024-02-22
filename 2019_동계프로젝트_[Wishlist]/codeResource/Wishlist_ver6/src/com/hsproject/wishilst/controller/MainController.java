package com.hsproject.wishilst.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hsproject.wishilst.Account;
import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.User;
import com.hsproject.wishilst.database.DBHelper;
import com.hsproject.wishilst.model.AccountModel;
import com.hsproject.wishilst.model.ProductModel;
import com.hsproject.wishilst.model.UserModel;
import com.hsproject.wishilst.view.AbstractView;
import com.hsproject.wishilst.view.AccountView;
import com.hsproject.wishilst.view.AddAccountView;
import com.hsproject.wishilst.view.AddProductView;
import com.hsproject.wishilst.view.UserSettingView;
import com.hsproject.wishilst.view.MainView;

public class MainController {
	
	ProductModel productModel;
	AccountModel accountModel;
	UserModel userModel;
	
	Vector<AbstractView> viewList = new Vector<AbstractView>();
	
	public MainController(ProductModel productModel, AccountModel accountModel, UserModel userModel) {
		this.productModel = productModel;
		this.accountModel = accountModel;
		this.userModel = userModel;
		
		/* ���� ���� */
		this.logInUser("testuser", "tkffuwh"); // test phase. fixing only one user
	}
	
	///////////////////��Ʈ�ѷ� ���� �Լ�///////////////////////////////////
	
	/* �信 ���� ���� �޼ҵ� */
	private void announceToView(String msgType, Object o) {
		
		for(int i=0; i<viewList.size(); i++) {
			viewList.get(i).update(msgType, o);
		}
	}
	
	/* ��Ʈ�ѷ��� �� �߰� �޼ҵ� */
	public MainController addView(AbstractView v) {
		viewList.add(v);
		announceToView("ProductList", productModel.getProductListAsVector());
		announceToView("AccountList", accountModel.getAccountListAsVector());

		
		return this;
	}
	
	/* ���� �̾ƿ��� */
	public User getNowUser() {
		return userModel.getUser();
	}
	
	/* ���� ���� �޼ҵ� */
	public boolean logInUser(String userID, String password) {
		User user = userModel.setUserFromDBByIDPW(userID, password);
		
		if(user!=null) {
			/* �α��� ���� */
			productModel.setUser(user);
			accountModel.setUser(user);
			return true;
		}else {
			return false;
		}
	}
	
	////////////////////�ܺ� �Լ�////////////////////////////////////////
	/* ���� ÷�� ���̾�α� */
	public File selectImageFile() {

		JFileChooser chooser = new JFileChooser();
		// ���� ���� ����
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"images",
				"jpg", "jpeg", "gif", "png");
		chooser.setFileFilter(filter);
		
		
		//���� ���̾�α� ���
		int ret = chooser.showOpenDialog(null);
		if(ret != JFileChooser.APPROVE_OPTION ) {
			JOptionPane.showMessageDialog(null,"������ �������� �ʾҽ��ϴ�","���", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		
		//���ϼ��� �� ���� ��ư ������
		String filePath = chooser.getSelectedFile().getPath();
		String filename = chooser.getSelectedFile().getName();
		
		
		return chooser.getSelectedFile();
	}
	/* �̹��� ���� */
	public String copyImageFileFrom(String oldImagePathName) {
		
		String newImageName = this.getRandomString(10);
		int pos = oldImagePathName.lastIndexOf( "." );
		String extension = oldImagePathName.substring( pos );
		//�̹������� ����
		FileInputStream input = null;
		FileOutputStream output = null;
		File file = new File(oldImagePathName);
		int c;
		try {
		input = new FileInputStream(file);
		//--------------������ġ �ڵ����� �޾ƿ��� ����-------------
		output = new FileOutputStream(new File(System.getProperty("user.dir")+"\\image\\" + newImageName + extension));
        
        while((c = input.read()) != -1) {
        	output.write((char) c);
        }
        
        System.out.println("������ ����Ǿ����ϴ�.");
        input.close();
        output.close();
		}
		catch(Exception ex) {
			System.out.println("�̹��� ���� ���� ����");
			return null;
		}
		
		return newImageName + extension;
	}
	
	/* �̹��� ���� ���� */
	public void deleteImageFile(String fileName) {
		File file = new File(System.getProperty("user.dir")+"\\image\\" + fileName);
		if( file.exists()) { 
			if(file.delete()) { 
				System.out.println("���ϻ��� ����");
			}else { 
				System.out.println("���ϻ��� ����");
			}
		}else {
			System.out.println("������ �������� �ʽ��ϴ�."); 
		}

	}
	
	/* ��ǰ �߰� ����1 */
	public boolean addProduct(String name, int price, int priority, String imageName) {
		return this.addProduct(new Product(name, price, priority, imageName));
	}
	
	/* ��ǰ �߰� ����2 */
	public boolean addProduct(Product product) {
		System.out.println("MainController: addProduct()");
		
		// �켱���� �ߺ� üũ
		if(this.isDuplicatedPriorityExist(product.getPriority())) {
			/* �ߺ� �켱������ ������ */
			/* ����ڿ��� ���� */
			int yesno = JOptionPane.showConfirmDialog(null, "�ߺ��� �켱������ �ֽ��ϴ�.\\n�о�ðڽ��ϱ�?", "Q", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
			
			if(yesno == JOptionPane.YES_OPTION) {
				/* �켱���� �о�� �������� �Ѿ */
				// �𵨿��� ���� ����
				productModel.pushProductPriorityFromTo(product.getPriority(), productModel.getContinuousPriorityFrom(product.getPriority()));
			} else {
				// �ƹ� ���۵� ���� ����
				return false;
			}
		}
		// �𵨿� ��ǰ �߰�
		productModel.addProduct(product);
		// �信 ����� ���� ����
		announceToView("ProductList", productModel.getProductListAsVector());
		
		return true;
	}
	
	/* ��ǰ ���� ����1 */
	public void deleteProduct(Product product) {
		this.deleteProductByPriority(product.getPriority());
	}
	
	/* ��ǰ ���� ����2 */
	public void deleteProductByPriority(int productPriority) {
		System.out.println("MainController: deleteProductByPriority()");
		/* ����ڿ��� ���� */
		int yesno = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Q", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
		
		if(yesno == JOptionPane.YES_OPTION) {
			// �̹��� ���� ����
			this.deleteImageFile(productModel.getProductByPriority(productPriority).getImageName());
			// �𵨿� ��ǰ ����
			productModel.deleteProduct(productPriority);
			// �信 ����� ���� ����
			announceToView("ProductList", productModel.getProductListAsVector());
		}
		
	}
	
	/* ��ü ��ǰ ���� �����ϼ� ������Ʈ �޼ҵ� */
	public void updateProductRemainTimeAll() {
		productModel.updateRemainTimeAll();
		// �信 ����� ���� ����
		announceToView("ProductList", productModel.getProductListAsVector());
	}
	
	/* Ư�� ��ǰ ���� �����ϼ� ������Ʈ */
	public void updateProductRemainTime(Product product) {
		productModel.d_time_Calculation(product);
		// �信 ����� ���� ����
		announceToView("ProductList", productModel.getProductListAsVector());
	}
	
	/* ��ǰ �ߺ� �켱���� ���� Ȯ�� */
	public boolean isDuplicatedPriorityExist(int priority) {
		return productModel.isDuplicatedPriorityExist(priority);
	}
	
	/* ��ǰ �켱���� ���� */
	public void modifyProductPriorityByPriority(int oldPriority, int newPriority) {
		System.out.println("MainController: modifyProductPriorityByPriority()");
		
		/* �ߺ� �켱���� üũ */
		if(productModel.isDuplicatedPriorityExist(newPriority)) {
			/* �ߺ� �켱������ ������ */
			/* ����ڿ��� ���� */
			int yesno = JOptionPane.showConfirmDialog(null, "�ߺ��� �켱������ �ֽ��ϴ�.\\n�о�ðڽ��ϱ�?", "Q", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
			
			if(yesno == JOptionPane.YES_OPTION) {
				/* �켱���� �о�� �������� �Ѿ */
				// �ӽ÷� �켱���� �ű�
				productModel.modifyPriority(oldPriority, -1);
				// �켱���� �о
				productModel.pushProductPriorityFromTo(oldPriority, productModel.getContinuousPriorityFrom(oldPriority));
				// �ӽ÷� ������ �켱���� ���ο� �켱������ ����
				productModel.modifyPriority(-1, newPriority);
			} else {
				// �ƹ� ���۵� ���� ����
				return;
			}
		}else {
			/* �ߺ� �켱������ ������ �׳� ���� */
			// �𵨿��� ���� ����
			productModel.modifyPriority(oldPriority, newPriority);
		}
		
		// �信 ����� ���� ����
		announceToView("ProductList", productModel.getProductListAsVector());
	}
	
	/* �ִ� �켱���� �������� */
	public int getMaxPriority() {
		return productModel.getMaxPriority();
	}

	
	
	
	
	
	
	
	
	
	/* ��/��� ���� �߰� ����1 */
	public boolean addAccount(Date date, String desc, int type, int cost) {
		return this.addAccount(new Account(date, desc, type, cost));
	}
	
	/* ��/��� ���� �߰� ����2 */
	public boolean addAccount(Account account) {
		System.out.println("MainController: addAccount()");
		// �𵨿� ��ǰ �߰�
		accountModel.addAccount(account);
		// �信 ����� ���� ����
		announceToView("AccountList", productModel.getProductListAsVector());
		
		return true;
	}
	
	/* ��/��� ���� ���� ����1 */
	public void deleteAccount(Account account) {
		this.deleteAccountByDateAndIndex(account.getDate(), account.getIndex());
	}

	/* ��/��� ���� ���� ����2 */
	public void deleteAccountByDateAndIndex(Date date, int index) {
		System.out.println("MainController: deleteAccountByDateAndIndex()");
		// �𵨿� ��ǰ ����
		accountModel.deleteAccount(date, index);
		// �信 ����� ���� ����
		announceToView("AccountList", accountModel.getAccountListAsVector());
	}
	
	/* ����� ���� ���� 01 By user instance */
	public void setUser(User user) {
		this.setUser(user.getBalance(), user.getF_expense(), user.getF_income(), user.getF_minbalance());
	}
	
	/* ����� ���� ���� 02 By parameters */
	public void setUser(int balance, int f_expense, int f_income, int minbalance) {
		userModel.setBalance(balance); // �ܰ�
		userModel.setF_expense(f_expense); // ��������
		userModel.setF_income(f_income); // ��������
		userModel.setF_minbalance(minbalance); // �ּҺ����ܰ�
	}
	
	/* ����� ���� �������� */
	public User getUser() {
		return userModel.getUser();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/* ��ǰ �߰� â Ȱ��ȭ */
	public void showAddProductView() {
		for(int i=0; i<viewList.size(); i++) {
			if( viewList.get(i) instanceof AddProductView ) {
				viewList.get(i).display();
				break;
			}
		}
	}
	
	/* ����ݳ��� �߰� â Ȱ��ȭ */
	public void showAddAccountView() {
		for(int i=0; i<viewList.size(); i++) {
			if( viewList.get(i) instanceof AddAccountView ) {
				viewList.get(i).display();
				break;
			}
		}
	}
	
	/* ��������� â Ȱ��ȭ */
	public void showUserSettingView() {
		for(int i=0; i<viewList.size(); i++) {
			if( viewList.get(i) instanceof UserSettingView ) {
				viewList.get(i).display();
				break;
			}
		}
	}
	
	
	/* ���� ���ڿ� */
	public String getRandomString(int length) {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = length;
	    Random random = new Random();
	 
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	 
	    return generatedString;
	}
	
	
}
