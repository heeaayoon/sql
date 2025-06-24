package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class PhoneBookDao {
	private static Scanner sc = new Scanner(System.in);
	private static String url = "jdbc:mysql://localhost:3306/myfirstdb";
	public static void main(String[] args) throws Exception {
		Connection con = DriverManager.getConnection(url, "root", "abcd");
		boolean flag = true;
		while(flag) {
			System.out.print("[I]nsert/[U]pdate/[D]elete/[S]electAll/[N]ative/[Q]uit:");
			char c = sc.next().toUpperCase().charAt(0);
			switch(c) {
			case 'I' : insertPhonebook(con); break;
			case 'U' : updatePhonebook(con); break;
			case 'D' : deletePhonebook(con); break;
			case 'S' : selectAllPhonebook(con); break;
			case 'N' : nativeQurey(con); break;
			case 'Q' : flag = false; break;
			}
		}
		System.out.println("bye");
	}

	private static void insertPhonebook(Connection con) throws SQLException { //원하는 콜론의 값만 넣는 방법은? // 그냥 null을 집어넣거나, 빈칸을 만들기(enter)
		System.out.print("이름 :");
		String a = sc.next();
		System.out.print("휴대폰번호 :");
		String b = sc.next();
		System.out.print("집 전화번호/주소 :");
		String c = sc.next();
		System.out.print("회사 전화번호/주소 :");
		String d = sc.next();
		System.out.print("e-mail :");
		String e = sc.next();
		
		String sql = "insert into phonebook(name, mobile, home, company, email) values(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, a);
		ps.setString(2, b);
		ps.setString(3, c);
		ps.setString(4, d);
		ps.setString(5, e);
		int res = ps.executeUpdate();
		System.out.println(res+"건의 데이터를 입력했습니다.");
	}
	
	private static void updatePhonebook(Connection con) { //무슨 정보를 update할지 고르는 메소드 추가
		
		try {
			System.out.print("update할 ID 값을 입력하시오 : ");
			int id = sc.nextInt();
			
			while(true) {
				System.out.println("어떤 컬럼을 수정하겠습니까?");
				String colName = whichContent(); //어떤 컬럼을 선택할지에 관한 메소드 호출
				
				// 사용자가 '업데이트 종료(0)'를 선택한 경우 메소드 종료
				if (colName.isEmpty() || colName.isBlank()) {
					System.out.println("업데이트를 종료합니다.");
					break;
				}
            //선택한 컬럼에 새로운 값을 입력 받음
            System.out.print("새로운 값을 입력하세요: ");
            String newV = sc.next();
            
            String sql = String.format("UPDATE phonebook SET %s = ? WHERE id = ?", colName);
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newV);
            ps.setInt(2,id);
            
            int res = ps.executeUpdate();
            if (res > 0) {
                System.out.println("=> 성공: " + res + "건의 데이터가 수정되었습니다.");
            	} else {
                System.out.println("=> 실패: 해당 ID를 찾지 못했거나 값이 동일하여 수정되지 않았습니다.");
            	}
			} //while문 종료
		}catch(Exception e){
			System.err.println("데이터베이스 업데이트 중 오류가 발생했습니다.");
            e.printStackTrace();
		}
	}
	
	static String whichContent() {
		String content = "";
		System.out.print("[1]name/[2]mobile/[3]home/[4]company/[5]email/[0]해당 id의 업데이트 종료:");
		int i = sc.nextInt();
		switch(i) {
		case 1 : content = "name"; break;
		case 2 : content = "mobile"; break;
		case 3 : content = "home"; break;
		case 4 : content = "company"; break;
		case 5 : content = "email"; break;
		case 0 : break;
		default:  System.out.println("잘못된 선택입니다."); break;
		}
		return content;
	}
	
	private static void deletePhonebook(Connection con) throws SQLException {
		System.out.print("삭제할 데이터의 id를 입력하시오 : ");
		int id = sc.nextInt();
		String sql = "delete from phonebook where id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1,id);
		int res = ps.executeUpdate();
		System.out.println(res+"건의 데이터를 삭제했습니다.");
	}
	
	private static void selectAllPhonebook(Connection con) throws SQLException { //SELECT * FROM phonebook;
		String sql = "SELECT * FROM phonebook";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.print(rs.getString("mobile")+",");
			System.out.print(rs.getString("home") + ",");
			System.out.print(rs.getString("company")+",");
			System.out.println(rs.getString("email"));
		}
	}

	//쿼리문(select만) 입력 가능한 메소드
//	private static void nativeQurey(Connection con) throws SQLException {
//		System.out.println("쿼리문을 입력하시오");
//		sc.nextLine();
//		String qStat = sc.nextLine();
//		PreparedStatement ps = con.prepareStatement(qStat);
//		ResultSet rs = ps.executeQuery();
//		ResultSetMetaData meta = rs.getMetaData(); //1. ResultSet으로부터 MetaData를 얻어옴
//		int count = meta.getColumnCount(); //2. MetaData로부터 컬럼 개수를 얻어옴
//		while (rs.next()) {
//			for(int i = 1;i<=count;i++) {  //인덱스 1부터 시작함
//				if(i>1)	System.out.print(", ");
//				System.out.print(rs.getString(i)); //MetaData로부터 인덱스로 데이터 가져올 수 있음
//			}
//			System.out.println();
//		}
//	}	
	
	 //쿼리문 입력 메소드
	private static void nativeQurey(Connection con) throws SQLException {
		System.out.println("쿼리문을 입력하시오");
		sc.nextLine();
		String qStat = sc.nextLine();
		PreparedStatement ps = con.prepareStatement(qStat);
		boolean hasResultSet = ps.execute(); //반환값이 ResultSet이면(select문) true 반환/ 아니면(select 이외) false 반환
		if(hasResultSet) { //select믄인 경우
			ResultSet rs = ps.getResultSet();
			ResultSetMetaData meta = rs.getMetaData(); //1. ResultSet으로부터 MetaData를 얻어옴
			int count = meta.getColumnCount(); //2. MetaData로부터 컬럼 개수를 얻어옴
			while (rs.next()) {
				for(int i = 1;i<=count;i++) {  //인덱스 1부터 시작함
					if(i>1)	System.out.print(", ");
					System.out.print(rs.getString(i)); //MetaData로부터 인덱스로 데이터 가져올 수 있음
				}
				System.out.println();
			}			
		}else { //select문이 아닌 경우
			int res = ps.getUpdateCount();
			System.out.println(res+"건의 데이터가 변경되었습니다.");
		}
	}	
}
