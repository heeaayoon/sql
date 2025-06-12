package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class QueryStatement {
	//모든 메소드에서 쓰기 위해 static으로 Scanner 객체를 선언
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 1. 드라이버 클래스가 없을 수 있음 -> ClassNotFoundException 발생 가능
			String url = "jdbc:mysql://localhost:3306/world";
			con = DriverManager.getConnection(url, "root", "abcd"); // 2. DB 정보가 틀리거나 서버가 꺼져있을 수 있음 -> SQLException 발생 가능 //DriverManager를 사용해 데이터베이스에 연결 (URL, 사용자명, 비밀번호)
			st = con.createStatement(); // 연결된 Connection 객체를 통해 SQL 문을 실행할 Statement 객체를 생성

			while (true) {
				System.out.println();
				System.out.print("질문 번호를 입력하시오 1~10 (0은 종료) : ");

				int option = sc.nextInt();
				if (option == 0) break;

				int x;
				switch (option) {

				case 1: method1(st); break;
				case 2: method2(st); break;
				case 3: method3(st); break;
				case 4: method4(st); break;
				case 5: method5(st); break;
				case 6: method6(st); break;
				case 7: method7(st); break;
				case 8: method8(st); break;
				case 9: method9(st); break;
				case 10: method10(st); break;
				}
			}
		} catch (SQLException e) { // DB관련 문제를 잡는 catch문
			System.out.println("데이터베이스 처리 중 오류가 발생했습니다."+e.getMessage());
		} catch (Exception e) { // DB 이외의 문제를 잡는 catch문
			System.out.println("알 수 없는 오류가 발생했습니다."+e.getMessage());
		} finally {
			try {
				if (st != null)
					st.close(); // 사용한 Statement 자원 해제
				if (con != null)
					con.close(); // 사용한 Connection 자원 해제
			} catch (Exception e) {
			}
		}
	}


	private static void method1(Statement st) throws SQLException {
		System.out.println("인구수를 입력받아서 그보다 많은 인구를 가진 도시를 검색해서 출력하세요.");
		System.out.print("인구수를 입력하시오 :  ");
		int x = sc.nextInt();

		ResultSet rs = st.executeQuery("select name, population from city where population>" + x);
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getInt("population") + ",");
		}
	}
	
	private static void method2(Statement st) throws SQLException {
		System.out.println("국가코드를 입력받아서 해당국가의 도시의 이름과 인구를 검색해서 출력하세요.");
		System.out.print("국가코드를 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select name, population from city where CountryCode='"+x+"'");
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getInt("population") + ",");
		}
	}
	
	private static void method3(Statement st) throws SQLException {
		System.out.println("대륙 이름을 입력받아서 해당 대륙에 위치한 국가를 검색해서 출력하세요.");
		System.out.print("대륙의 이름을 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select name from country where continent='"+x+"'");
		while (rs.next()) {
			System.out.print(rs.getString("name") + ", ");
		}
	}
	
	private static void method4(Statement st) throws SQLException {
		System.out.println("면적을 입력 받아서 입력 값보다 작은 면적을 가진 국가의 이름과 면적을 면적오름차순으로 검색해서 출력하세요. ");
		System.out.print("면적 입력하시오 :  ");
		int x = sc.nextInt();

		ResultSet rs = st.executeQuery("select name, SurfaceArea from country where SurfaceArea<"+x+" order by SurfaceArea");
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getInt("SurfaceArea") + "m^2");
		}
	}
	
	private static void method5(Statement st) throws SQLException {
		System.out.println("대한민국의 District를 입력 받아서 해당 지역에 있는 모든도시를 검색해서 출력하세요.");
		System.out.print("대한민국의 District를 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select name from city where District='"+x+"'");
		while (rs.next()) {
			System.out.print(rs.getString("name") + ", ");
		}
	}
	
	private static void method6(Statement st) throws SQLException {
		System.out.println("언어를 입력받아서 해당언어가 국가공식언어인 국가코드를 출력하세요. ");
		System.out.print("언어를 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select countrycode from countrylanguage where isOfficial = 'T' and language='"+x+"'");
		while (rs.next()) {
			System.out.println(rs.getString("countrycode") + ", ");
		}
	}
	
	private static void method7(Statement st) throws SQLException {
		System.out.println("사용자가 입력한 언어비율 이상인 언어의 국가 코드와 비율을 검색해서 출력하세요");
		System.out.print("언어비율울 입력하시오 :  ");
		int x = sc.nextInt();

		ResultSet rs = st.executeQuery("select countrycode, percentage from countrylanguage where percentage>"+x);
		while (rs.next()) {
			System.out.print(rs.getString("countrycode") + ",");
			System.out.println(rs.getInt("percentage") + "%");
		}
	}
	
	//method 8-10은 join사용
	private static void method8(Statement st) throws SQLException {
		System.out.println("국가명의 일부를 입력받아서 해당국가의 도시의 이름과 인구를 검색해서 출력하세요.");
		System.out.print("국가명의 일부를 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select country.name a,city.name b, city.population c from city, country where city.countrycode=country.code and country.name like '%"+x+"%'");
		while (rs.next()) {
			System.out.print(rs.getString("a") + ",");
			System.out.print(rs.getString("b") + ",");
			System.out.println(rs.getInt("c") + "명");
		}
	}
	
	private static void method9(Statement st) throws SQLException {
		System.out.println("국가코드 또는 국가명의 일부를 입력받아서 해당국가의 도시의 이름과 인구를 검색해서 출력하세요.");
		System.out.print("국가코드 또는 국가명의 일부를 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select country.Code a , country.name b ,city.name c, city.population d from city, country where city.countrycode=country.code and (countrycode like '%"+x+"%' or country.name like '%"+x+"%')");
		while (rs.next()) {
			System.out.print(rs.getString("a") + ",");
			System.out.print(rs.getString("b") + ",");
			System.out.print(rs.getString("c") + ",");
			System.out.println(rs.getInt("d") + "명");
		}
	}
	
	private static void method10(Statement st) throws SQLException {
		System.out.println("언어를 입력받아서 해당언어가 국가공식언어인 국가명을 출력하세요.");
		System.out.print("언어를 입력하시오 :  ");
		String x = sc.next();

		ResultSet rs = st.executeQuery("select name, IsOfficial from country,countrylanguage where country.code = countrylanguage.CountryCode and isOfficial = 'T' and language='"+x+"'");
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getString("IsOfficial") + ",");
		}
	}
}