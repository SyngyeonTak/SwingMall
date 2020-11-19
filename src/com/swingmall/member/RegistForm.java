package com.swingmall.member;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.swingmall.main.Page;
import com.swingmall.main.ShopMain;

public class RegistForm extends Page{
	JPanel p_content; //로그인폼과 동일한 목적으로 ...
	JTextField t_mid;
	JPasswordField t_pass;
	JTextField t_name;
	JTextField t_phone;
	JTextField t_email;
	JButton bt_regist;
	
	public RegistForm(ShopMain shopMain) {
		super(shopMain);
		p_content = new JPanel();
		
		t_mid = new JTextField("아이디 입력");
		t_pass = new JPasswordField();
		t_name = new JTextField("이름 입력");
		t_phone = new JTextField("연락처 입력");
		t_email = new JTextField("이메일 입력");
		bt_regist = new JButton("회원가입");
		
		//스타일 
		p_content.setPreferredSize(new Dimension(400, 350));
		p_content.setBackground(Color.WHITE);
		Dimension d = new Dimension(380,25);
		t_mid.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_phone.setPreferredSize(d);
		t_email.setPreferredSize(d);
		
		//조립 
		p_content.add(t_mid);
		p_content.add(t_pass);
		p_content.add(t_name);
		p_content.add(t_phone);
		p_content.add(t_email);
		p_content.add(bt_regist);
		
		add(p_content);
		
	}
	
}



