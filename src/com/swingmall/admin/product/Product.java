package com.swingmall.admin.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.swingmall.admin.AdminMain;
import com.swingmall.admin.Page;
import com.swingmall.util.db.DBManager;

public class Product extends Page{
	JPanel p_west;//tree 올 영역
	JPanel p_center;
	JTree tree;
	JTable table;
	JScrollPane s1, s2;
	JButton bt_regist;
	
	ArrayList<String> topList = new ArrayList<String>();
	ArrayList<ArrayList> subList = new ArrayList<ArrayList>();
	
	ProductModel model;
	
	Connection con;
	DBManager dbManager;
	
	public Product(AdminMain adminMain) {
		super(adminMain);
		con = adminMain.getCon();
		dbManager = adminMain.getDbManager();
		
		//카테고리 구성
		getTobList();
		for (int i = 0; i < topList.size(); i++) {
			ArrayList<String> list= getSubList(topList.get(i));
			subList.add(list);
		}
		
		//노드 만들기
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("옷");
		for (int i = 0; i < topList.size(); i++) {
			top.add(getCreatedNode(topList.get(i), subList.get(i)));
		}
		
		//생성
		p_west = new JPanel();
		p_center = new JPanel();
		tree = new JTree(top);//노드 넣을 예정
		table = new JTable();//모델은 쿼리문으로 product를 불러올때 새로 정하자!
		s1 = new JScrollPane(tree);
		s2 = new JScrollPane(table);
		bt_regist = new JButton("등록하기");
		
		//스타일 적용
		s1.setPreferredSize(new Dimension(200, AdminMain.HEIGHT-100));
		p_west.setBackground(Color.WHITE);
		s2.setPreferredSize(new Dimension(AdminMain.WIDTH-300, AdminMain.HEIGHT-150));
		
		//조립
		setLayout(new BorderLayout());
		
		
		
		p_west.add(s1);
		p_center.add(s2);
		p_center.add(bt_regist);
		add(p_west,BorderLayout.WEST);
		add(p_center);
		
		//tree는 이벤트가 별도로 지원된다.
		tree.addTreeSelectionListener((e)->{
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			getProductList(selectedNode.toString());
		});
		
		getProductList(null);//모든 상품 가져오기
	}
	
	//상품 가져오기
	public void  getProductList(String name) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = null;

		if(name == null) {
			sql = "select * from product";
		}else {
			sql = "select * from product where subcategory_id = "
					+ "(select subcategory_id from subcategory where name = '"+name+"')";
		}
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//메타정보를 이용하여 ProductModel의 column ArrayList를 채우자
			ArrayList<String> column = new ArrayList<String>();
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				column.add(meta.getColumnName(i));
			}
			
			//rs의 레코드를 ProductModel의 record ArrayList에 채우자
			ArrayList<ProductVO> record = new ArrayList<ProductVO>();
			while(rs.next()) {
				ProductVO vo = new ProductVO();
				
				vo.setProduct_id(rs.getInt("product_id"));
				vo.setSubcategory_id(rs.getInt("subcategory_id"));
				vo.setProduct_name(rs.getString("product_name"));
				vo.setBrand(rs.getString("brand"));
				vo.setPrice(rs.getInt("price"));
				vo.setFilename(rs.getString("filename"));
				vo.setDetail(rs.getString("detail"));
				
				record.add(vo);
			}
			model = new ProductModel();
			model.record = record;
			model.column = column;
			table.setModel(model);
			table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.close(pstmt, rs);
		}
	}
	
	//상위카테고리 가져오기
	public void getTobList() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from topcategory";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				topList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.close(pstmt, rs);
		}
		
	}
	
	//하위카테로기 가져오기
	public ArrayList getSubList(String name) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from subcategory where topcategory_id=( select topcategory_id from topcategory where name=?)";

		ArrayList<String> list = new ArrayList<String>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.close(pstmt, rs);
		}
		return list;
	}
	
	//트리노드 생성하기
	public DefaultMutableTreeNode getCreatedNode(String parentName, ArrayList childName) {
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(parentName);
		
		//넘겨받은 매개변수인 ArrayList 만큼 반복하여 자식노드 부착
		for (int i = 0; i < childName.size(); i++) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(childName.get(i));
			parent.add(child);
		}
		
		return parent;
	}
	
}



























