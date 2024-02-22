package com.hsproject.wishilst.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.hsproject.wishilst.Product;
import com.hsproject.wishilst.controller.MainController;

public class AccountView extends JFrame implements AbstractView {

   MainController mainController;

   private JPanel contentPL = new JPanel();

   private String[] columnName = {"날짜","입출금","내역","가격"};
   private String[][] data = {{"날짜?","출금","사과","3000"},
         {"날짜?","출금","사과","13000"},
         {"날짜?","출금","사과","4000"},
         {"날짜?","출금","사과","6000"}
   };
   private DefaultTableModel model = new DefaultTableModel(data,columnName);
   private JTable table = new JTable(model);

   Date today = new Date();
   SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");

   public AccountView(MainController mainController) {
      this.mainController = mainController;

      this.setTitle("입출금내역");
      setAlwaysOnTop(true);
      this.setSize(425, 130);   

      JScrollPane scrollPane = new JScrollPane(table);   

      this.add(scrollPane,BorderLayout.CENTER);
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   //   public DefaultTableModel getModel() {
   //      return model;
   //   }
   //
   //   public void setModel(DefaultTableModel model) {
   //      this.model = model;
   //   }


   @Override
   public void update(String objectType, Object o) {
      // TODO Auto-generated method stub

   }

   @Override
   public void display() {
      // TODO Auto-generated method stub

   }

   @Override
   public void close() {
      // TODO Auto-generated method stub

   }

}