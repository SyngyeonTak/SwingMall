/*
모든 페이지가 공통적으로 가져야할 속성, 메서드등을 
정의하기 위한 최상위 페이지 클래스
따라서  Home, Product, Q&A, MyPage, Login 등의 페이지들이 
이 클래스를 상속받을 경우, 코드를 중복해서 작성하지 않아도 된다!!
*/
package com.swingmall.main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Page extends JPanel{
	private ShopMain shopMain;
	
	public ShopMain getShopMain() {
		return shopMain;
	}
	
	public Page(ShopMain shopMain) {
		this.shopMain=shopMain;
		this.setPreferredSize(new Dimension(ShopMain.WIDTH, ShopMain.HEIGHT-100));
	}
	
}





