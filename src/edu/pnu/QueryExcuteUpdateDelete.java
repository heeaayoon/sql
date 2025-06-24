package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

//mySQL delete문 : DELETE FROM myfirstdb.phonebook WHERE (`id` = '13');

public class QueryExcuteUpdateDelete {
	
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement psmt = null;
//		try { //statment 사용
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/myfirstdb";
//			con = DriverManager.getConnection(url, "root", "abcd");
//			stmt = con.createStatement();
//			int id = 13;
//			int res = stmt.executeUpdate("delete from phonebook "+String.format("where id='%d'",id));
//			System.out.println(res+"건이 삭제됨");
//		} catch (Exception e) {
		
		try { //statment 사용
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/myfirstdb";
		con = DriverManager.getConnection(url, "root", "abcd");
		stmt = con.createStatement();
		int res = stmt.executeUpdate("delete from phonebook where id=12");
		System.out.println(res+"건이 삭제됨");
	} catch (Exception e) {
		
//		try { //PreparedStatment 사용
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/myfirstdb";
//			con = DriverManager.getConnection(url, "root", "abcd");
//			String sql = "delete from phonebook where id=?";
//			int id = 11;
//			psmt = con.prepareStatement(sql);
//			psmt.setInt(1, id);
//			int res = psmt.executeUpdate();
//			System.out.println(res+"건이 삭제됨");
//		} catch (Exception e) {
			System.out.println("연결실패: " + e.getMessage());
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
	}
	
	

}
