package com.hsproject.wishilst.view;

public interface AbstractView {
	void update(String msgType, Object o); // ��Ʈ�ѷ����� ���� ���޹޴� �޼ҵ�
	void display(); // ������ ǥ�� �޼ҵ�
	void close(); // ������ ����� �޼ҵ�
}
