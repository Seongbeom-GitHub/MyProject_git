package com.hsproject.wishilst;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.hsproject.wishilst.model.*;
import com.hsproject.wishilst.view.*;
import com.hsproject.wishilst.controller.*;

public class Start {

	public static void main(String[] args) {
	    try {
            // Swing 테마 설정
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    	
	    	
	    	/* 01. 모델 생성 */
	    	// 제품 모델 생성
	    	ProductModel productModel = new ProductModel();
	    	// 계좌 모델 생성
	    	AccountModel accountModel = new AccountModel();
	    	// 유저 모델 생성
	    	UserModel userModel = new UserModel();
	    	

	    	/* 02. 컨트롤러 생성 */
	    	// 제품 컨트롤러 생성 및 컨트롤러에 뷰 추가
	    	MainController mainController = new MainController(productModel, accountModel, userModel);
	    	
	    	/* 03. 뷰 생성 */
	    	// 메인 창(제품 리스트)
	    	MainView mainView = new MainView(mainController);
	    	// 가계부(지출,수입) 창
	    	AccountView accountView = new AccountView(mainController);
	    	// 제품 추가 창
	    	AddProductView addProductView = new AddProductView(mainController);
	    	// 가계부 추가 창
	    	AddAccountView addAccountView = new AddAccountView(mainController);
	    	
	    	/* 04. 컨트롤러에 뷰 할당 */
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
