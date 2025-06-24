package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

//mySQL insert문 : INSERT INTO myfirstdb.phonebook (`name`, `mobile`) VALUES ('홍', '010-');

public class QueryExcuteUpdateInsert {
	
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement psmt = null;
//		try { //statment 사용
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/myfirstdb";
//			con = DriverManager.getConnection(url, "root", "abcd");
//			String name = "홍길동";
//			String mobile = "010-111-222";
//			String sql = "insert into phonebook(name, mobile) values";
//			stmt = con.createStatement();
//			int res = stmt.executeUpdate(sql+String.format("('%s','%s')",name,mobile ));
//			System.out.println(res+"건이 입력됨");
//		} catch (Exception e) {
		
		try { //statment 사용
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/myfirstdb";
		con = DriverManager.getConnection(url, "root", "abcd");
		String sql = "insert into phonebook(name, mobile) values";
		stmt = con.createStatement();
		int res = stmt.executeUpdate(sql+"('홍','010-0000-0000')");
		System.out.println(res+"건이 입력됨");
	} catch (Exception e) {
		
//		try { //PreparedStatment 사용
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/myfirstdb";
//			con = DriverManager.getConnection(url, "root", "abcd");
//			String sql = "insert into phonebook(name, mobile) values(?,?)";
//			String name = "홍길동";
//			String mobile = "010-111-222";
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, name);
//			psmt.setString(2, mobile);
//			int res = psmt.executeUpdate();
//			System.out.println(res+"건이 입력됨");
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
