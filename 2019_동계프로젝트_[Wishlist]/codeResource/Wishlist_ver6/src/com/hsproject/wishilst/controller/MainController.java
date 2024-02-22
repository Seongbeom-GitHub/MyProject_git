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
		
		/* 유저 설정 */
		this.logInUser("testuser", "tkffuwh"); // test phase. fixing only one user
	}
	
	///////////////////컨트롤러 내장 함수///////////////////////////////////
	
	/* 뷰에 정보 전달 메소드 */
	private void announceToView(String msgType, Object o) {
		
		for(int i=0; i<viewList.size(); i++) {
			viewList.get(i).update(msgType, o);
		}
	}
	
	/* 컨트롤러에 뷰 추가 메소드 */
	public MainController addView(AbstractView v) {
		viewList.add(v);
		announceToView("ProductList", productModel.getProductListAsVector());
		announceToView("AccountList", accountModel.getAccountListAsVector());

		
		return this;
	}
	
	/* 유저 뽑아오기 */
	public User getNowUser() {
		return userModel.getUser();
	}
	
	/* 유저 설정 메소드 */
	public boolean logInUser(String userID, String password) {
		User user = userModel.setUserFromDBByIDPW(userID, password);
		
		if(user!=null) {
			/* 로그인 성공 */
			productModel.setUser(user);
			accountModel.setUser(user);
			return true;
		}else {
			return false;
		}
	}
	
	////////////////////외부 함수////////////////////////////////////////
	/* 파일 첨부 다이얼로그 */
	public File selectImageFile() {

		JFileChooser chooser = new JFileChooser();
		// 파일 필터 설정
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"images",
				"jpg", "jpeg", "gif", "png");
		chooser.setFileFilter(filter);
		
		
		//파일 다이얼로그 출력
		int ret = chooser.showOpenDialog(null);
		if(ret != JFileChooser.APPROVE_OPTION ) {
			JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다","경고", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		
		//파일선택 후 열기 버튼 누를시
		String filePath = chooser.getSelectedFile().getPath();
		String filename = chooser.getSelectedFile().getName();
		
		
		return chooser.getSelectedFile();
	}
	/* 이미지 복사 */
	public String copyImageFileFrom(String oldImagePathName) {
		
		String newImageName = this.getRandomString(10);
		int pos = oldImagePathName.lastIndexOf( "." );
		String extension = oldImagePathName.substring( pos );
		//이미지파일 복사
		FileInputStream input = null;
		FileOutputStream output = null;
		File file = new File(oldImagePathName);
		int c;
		try {
		input = new FileInputStream(file);
		//--------------실행위치 자동으로 받아오게 변경-------------
		output = new FileOutputStream(new File(System.getProperty("user.dir")+"\\image\\" + newImageName + extension));
        
        while((c = input.read()) != -1) {
        	output.write((char) c);
        }
        
        System.out.println("파일이 복사되었습니다.");
        input.close();
        output.close();
		}
		catch(Exception ex) {
			System.out.println("이미지 파일 복사 에러");
			return null;
		}
		
		return newImageName + extension;
	}
	
	/* 이미지 파일 삭제 */
	public void deleteImageFile(String fileName) {
		File file = new File(System.getProperty("user.dir")+"\\image\\" + fileName);
		if( file.exists()) { 
			if(file.delete()) { 
				System.out.println("파일삭제 성공");
			}else { 
				System.out.println("파일삭제 실패");
			}
		}else {
			System.out.println("파일이 존재하지 않습니다."); 
		}

	}
	
	/* 제품 추가 동작1 */
	public boolean addProduct(String name, int price, int priority, String imageName) {
		return this.addProduct(new Product(name, price, priority, imageName));
	}
	
	/* 제품 추가 동작2 */
	public boolean addProduct(Product product) {
		System.out.println("MainController: addProduct()");
		
		// 우선순위 중복 체크
		if(this.isDuplicatedPriorityExist(product.getPriority())) {
			/* 중복 우선순위가 있으면 */
			/* 사용자에게 질문 */
			int yesno = JOptionPane.showConfirmDialog(null, "중복된 우선순위가 있습니다.\\n밀어내시겠습니까?", "Q", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
			
			if(yesno == JOptionPane.YES_OPTION) {
				/* 우선순위 밀어내는 동작으로 넘어감 */
				// 모델에서 동작 수행
				productModel.pushProductPriorityFromTo(product.getPriority(), productModel.getContinuousPriorityFrom(product.getPriority()));
			} else {
				// 아무 동작도 하지 않음
				return false;
			}
		}
		// 모델에 제품 추가
		productModel.addProduct(product);
		// 뷰에 변경된 정보 전달
		announceToView("ProductList", productModel.getProductListAsVector());
		
		return true;
	}
	
	/* 제품 삭제 동작1 */
	public void deleteProduct(Product product) {
		this.deleteProductByPriority(product.getPriority());
	}
	
	/* 제품 삭제 동작2 */
	public void deleteProductByPriority(int productPriority) {
		System.out.println("MainController: deleteProductByPriority()");
		/* 사용자에게 질문 */
		int yesno = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "Q", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
		
		if(yesno == JOptionPane.YES_OPTION) {
			// 이미지 파일 삭제
			this.deleteImageFile(productModel.getProductByPriority(productPriority).getImageName());
			// 모델에 제품 삭제
			productModel.deleteProduct(productPriority);
			// 뷰에 변경된 정보 전달
			announceToView("ProductList", productModel.getProductListAsVector());
		}
		
	}
	
	/* 전체 제품 구매 가능일수 업데이트 메소드 */
	public void updateProductRemainTimeAll() {
		productModel.updateRemainTimeAll();
		// 뷰에 변경된 정보 전달
		announceToView("ProductList", productModel.getProductListAsVector());
	}
	
	/* 특정 제품 구매 가능일수 업데이트 */
	public void updateProductRemainTime(Product product) {
		productModel.d_time_Calculation(product);
		// 뷰에 변경된 정보 전달
		announceToView("ProductList", productModel.getProductListAsVector());
	}
	
	/* 제품 중복 우선순위 존재 확인 */
	public boolean isDuplicatedPriorityExist(int priority) {
		return productModel.isDuplicatedPriorityExist(priority);
	}
	
	/* 제품 우선순위 수정 */
	public void modifyProductPriorityByPriority(int oldPriority, int newPriority) {
		System.out.println("MainController: modifyProductPriorityByPriority()");
		
		/* 중복 우선순위 체크 */
		if(productModel.isDuplicatedPriorityExist(newPriority)) {
			/* 중복 우선순위가 있으면 */
			/* 사용자에게 질문 */
			int yesno = JOptionPane.showConfirmDialog(null, "중복된 우선순위가 있습니다.\\n밀어내시겠습니까?", "Q", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
			
			if(yesno == JOptionPane.YES_OPTION) {
				/* 우선순위 밀어내는 동작으로 넘어감 */
				// 임시로 우선순위 옮김
				productModel.modifyPriority(oldPriority, -1);
				// 우선순위 밀어냄
				productModel.pushProductPriorityFromTo(oldPriority, productModel.getContinuousPriorityFrom(oldPriority));
				// 임시로 변경한 우선순위 새로운 우선순위로 설정
				productModel.modifyPriority(-1, newPriority);
			} else {
				// 아무 동작도 하지 않음
				return;
			}
		}else {
			/* 중복 우선순위가 없으면 그냥 수정 */
			// 모델에서 동작 수행
			productModel.modifyPriority(oldPriority, newPriority);
		}
		
		// 뷰에 변경된 정보 전달
		announceToView("ProductList", productModel.getProductListAsVector());
	}
	
	/* 최대 우선순위 가져오기 */
	public int getMaxPriority() {
		return productModel.getMaxPriority();
	}

	
	
	
	
	
	
	
	
	
	/* 입/출금 내역 추가 동작1 */
	public boolean addAccount(Date date, String desc, int type, int cost) {
		return this.addAccount(new Account(date, desc, type, cost));
	}
	
	/* 입/출금 내역 추가 동작2 */
	public boolean addAccount(Account account) {
		System.out.println("MainController: addAccount()");
		// 모델에 제품 추가
		accountModel.addAccount(account);
		// 뷰에 변경된 정보 전달
		announceToView("AccountList", productModel.getProductListAsVector());
		
		return true;
	}
	
	/* 입/출금 내역 삭제 동작1 */
	public void deleteAccount(Account account) {
		this.deleteAccountByDateAndIndex(account.getDate(), account.getIndex());
	}

	/* 입/출금 내역 삭제 동작2 */
	public void deleteAccountByDateAndIndex(Date date, int index) {
		System.out.println("MainController: deleteAccountByDateAndIndex()");
		// 모델에 제품 삭제
		accountModel.deleteAccount(date, index);
		// 뷰에 변경된 정보 전달
		announceToView("AccountList", accountModel.getAccountListAsVector());
	}
	
	/* 사용자 정보 설정 01 By user instance */
	public void setUser(User user) {
		this.setUser(user.getBalance(), user.getF_expense(), user.getF_income(), user.getF_minbalance());
	}
	
	/* 사용자 정보 설정 02 By parameters */
	public void setUser(int balance, int f_expense, int f_income, int minbalance) {
		userModel.setBalance(balance); // 잔고
		userModel.setF_expense(f_expense); // 고정지출
		userModel.setF_income(f_income); // 고정수입
		userModel.setF_minbalance(minbalance); // 최소보유잔고
	}
	
	/* 사용자 정보 가져오기 */
	public User getUser() {
		return userModel.getUser();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/* 제품 추가 창 활성화 */
	public void showAddProductView() {
		for(int i=0; i<viewList.size(); i++) {
			if( viewList.get(i) instanceof AddProductView ) {
				viewList.get(i).display();
				break;
			}
		}
	}
	
	/* 입출금내역 추가 창 활성화 */
	public void showAddAccountView() {
		for(int i=0; i<viewList.size(); i++) {
			if( viewList.get(i) instanceof AddAccountView ) {
				viewList.get(i).display();
				break;
			}
		}
	}
	
	/* 사용자정보 창 활성화 */
	public void showUserSettingView() {
		for(int i=0; i<viewList.size(); i++) {
			if( viewList.get(i) instanceof UserSettingView ) {
				viewList.get(i).display();
				break;
			}
		}
	}
	
	
	/* 랜덤 문자열 */
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
