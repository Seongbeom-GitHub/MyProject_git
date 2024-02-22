package com.hsproject.wishilst;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.hsproject.wishilst.model.*;
import com.hsproject.wishilst.view.*;
import com.hsproject.wishilst.controller.*;

public class Start {

	public static void main(String[] args) {
	    try {
            // Swing �׸� ����
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    	
	    	
	    	/* 01. �� ���� */
	    	// ��ǰ �� ����
	    	ProductModel productModel = new ProductModel();
	    	// ���� �� ����
	    	AccountModel accountModel = new AccountModel();
	    	// ���� �� ����
	    	UserModel userModel = new UserModel();
	    	

	    	/* 02. ��Ʈ�ѷ� ���� */
	    	// ��ǰ ��Ʈ�ѷ� ���� �� ��Ʈ�ѷ��� �� �߰�
	    	MainController mainController = new MainController(productModel, accountModel, userModel);
	    	
	    	/* 03. �� ���� */
	    	// ���� â(��ǰ ����Ʈ)
	    	MainView mainView = new MainView(mainController);
	    	// �����(����,����) â
	    	AccountView accountView = new AccountView(mainController);
	    	// ��ǰ �߰� â
	    	AddProductView addProductView = new AddProductView(mainController);
	    	// ����� �߰� â
	    	AddAccountView addAccountView = new AddAccountView(mainController);
	    	
	    	/* 04. ��Ʈ�ѷ��� �� �Ҵ� */
	    	mainController.addView(mainView).addView(accountView).addView(addProductView).addView(addAccountView);
	    	
	    	
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    	e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    	e.printStackTrace();
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    	e.printStackTrace();
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    	e.printStackTrace();
	    }
	}

}
