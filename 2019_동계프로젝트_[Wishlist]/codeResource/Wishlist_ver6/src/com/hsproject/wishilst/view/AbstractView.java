package com.hsproject.wishilst.view;

public interface AbstractView {
	void update(String msgType, Object o); // 컨트롤러에서 정보 전달받는 메소드
	void display(); // 프레임 표시 메소드
	void close(); // 프레임 숨기기 메소드
}
