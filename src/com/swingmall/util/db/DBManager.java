/*
 * 데이터베이스와 관련된 업무를 처리하고, 또는 중복되는 로직을 공통화시켜
 * 재사용성을 높이기 위한 클래스..
 * */

package com.swingmall.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user ="user1104";
	private String password = "1234";
	
	//이 메서드를 호출하는 자는 Connection 객체를 반환받을 수 있돌고...
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName(driver);//드라이버 로드
			con = DriverManager.getConnection(url, user, password);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		return con;
	}
	
	//Connection을 받아와서 처리
	public void disConnect(Connection con) {
		try {
			if(con != null) con.close();
			System.exit(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//쿼리문 수행과 관련된 자원을 닫아주는 매서드(DML)
	public void close(PreparedStatement pstmt) {
		try {
			if(pstmt!=null) 	pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//select문 수행과 관련된 자원을 닫을 때..
	public void close(PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) 	pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}








