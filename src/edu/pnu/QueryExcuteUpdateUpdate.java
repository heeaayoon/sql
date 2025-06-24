package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//mySQL update문 : UPDATE myfirstdb.phonebook SET home = 'korea' WHERE (id = '13');

public class QueryExcuteUpdateUpdate {
	
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement psmt = null;
//		try { //statment 사용
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/myfirstdb";
//			con = DriverManager.getConnection(url, "root", "abcd");
//			String sql = "update phonebook set "; //set뒤에 공백 필요함
//			String home = "Korea";
//			int id = 11;
//			stmt = con.createStatement();
//			int res = stmt.executeUpdate(sql+String.format("home = '%s' where id = %d",home, id ));
//			System.out.println(res+"건이 입력됨");
//		} catch (Exception e) {
		try { //PreparedStatment 사용
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/myfirstdb";
			con = DriverManager.getConnection(url, "root", "abcd");
			String sql = "update phonebook set home =? where id =?";
			String home = "Korea2";
			int id = 12;
			psmt = con.prepareStatement(sql);
			psmt.setString(1, home);
			psmt.setInt(2, id);
			int res = psmt.executeUpdate();
			System.out.println(res+"건이 수정됨");
		} catch (Exception e) {				
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

	private static void methodUpdate(Connection con, PreparedStatement psmt, String home, int id) throws SQLException {
		System.out.println("주소와 변경할 키 값.");
		psmt = con.prepareStatement("update phonebook set home =? where id =?");
		psmt.setString(1, home);
		psmt.setInt(2, id);
	}
	
	

}
