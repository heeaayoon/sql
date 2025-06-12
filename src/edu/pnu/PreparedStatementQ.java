package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class PreparedStatementQ {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			String url = "jdbc:mysql://localhost:3306/world";
			con = DriverManager.getConnection(url, "root", "abcd"); 

			while (true) {
				System.out.println();
				System.out.print("질문 번호를 입력하시오 1~10 (0은 종료) : ");

				int option = sc.nextInt();
				if (option == 0)
					break;

				int x;
				switch (option) {
//
				case 1: method1(con); break;
				case 2: method2(con); break;
				case 3: method3(con); break;
				case 4: method4(con); break;
				case 5: method5(con); break;
				case 6: method6(con); break;
				case 7: method7(con); break;
				case 8: method8(con); break;
				case 9: method9(con); break;
				case 10: method10(con); break;
				}
			}
		} catch (SQLException e) { // DB관련 문제를 잡는 catch문
			System.out.println("데이터베이스 처리 중 오류가 발생했습니다." + e.getMessage());
		} catch (Exception e) { // DB 이외의 문제를 잡는 catch문
			System.out.println("알 수 없는 오류가 발생했습니다." + e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close(); // 사용한 Connection 자원 해제
			} catch (Exception e) {
			}
		}
	}

	private static void method1(Connection con) throws SQLException {
		System.out.println("인구수를 입력받아서 그보다 많은 인구를 가진 도시를 검색해서 출력하세요.");
		System.out.print("인구수를 입력하시오 :  ");
		int x = sc.nextInt();
		// Statement: 완전한 SQL 문을 문자열로 만듦
		// PreparedStatement: 데이터가 들어갈 자리를 '?'(placeholder)로 둔 '쿼리 템플릿'을 만듦
		PreparedStatement st = con.prepareStatement("select name, population from city where population > ?");
		// Statement: 값(x)이 쿼리 문자열에 직접 포함되어 문법의 일부가 됨 (보안에 취약)
		// PreparedStatement: setInt, setString 등으로 '?' 자리에 '데이터'로서 안전하게 값을 설정
		// 첫 번째 물음표(?)에 int 타입의 변수 x 값을 설정
		st.setInt(1,x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getInt("population"));
		}
	}

	private static void method2(Connection con) throws SQLException {
		System.out.println("국가코드를 입력받아서 해당국가의 도시의 이름과 인구를 검색해서 출력하세요.");
		System.out.print("국가코드를 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select name, population from city where CountryCode=?");
		st.setString(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getInt("population"));
		}
	}

	private static void method3(Connection con) throws SQLException {
		System.out.println("대륙 이름을 입력받아서 해당 대륙에 위치한 국가를 검색해서 출력하세요.");
		System.out.print("대륙의 이름을 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select name from country where continent=?");
		st.setString(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ", ");
		}
	}

	private static void method4(Connection con) throws SQLException {
		System.out.println("면적을 입력 받아서 입력 값보다 작은 면적을 가진 국가의 이름과 면적을 면적오름차순으로 검색해서 출력하세요. ");
		System.out.print("면적 입력하시오 :  ");
		int x = sc.nextInt();
		
		PreparedStatement st = con.prepareStatement("select name, SurfaceArea from country where SurfaceArea<? order by SurfaceArea");
		st.setInt(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getInt("SurfaceArea") + "m^2");
		}
	}

	private static void method5(Connection con) throws SQLException {
		System.out.println("대한민국의 District를 입력 받아서 해당 지역에 있는 모든도시를 검색해서 출력하세요.");
		System.out.print("대한민국의 District를 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select name from city where District=?");
		st.setString(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ", ");
		}
	}

	private static void method6(Connection con) throws SQLException {
		System.out.println("언어를 입력받아서 해당언어가 국가공식언어인 국가코드를 출력하세요. ");
		System.out.print("언어를 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select countrycode from countrylanguage where isOfficial = 'T' and language=?");
		st.setString(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("countrycode") + ", ");
		}
	}

	private static void method7(Connection con) throws SQLException {
		System.out.println("사용자가 입력한 언어비율 이상인 언어의 국가 코드와 비율을 검색해서 출력하세요");
		System.out.print("언어비율울 입력하시오 :  ");
		int x = sc.nextInt();
		
		PreparedStatement st = con.prepareStatement("select countrycode, percentage from countrylanguage where percentage>?");
		st.setInt(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("countrycode") + ",");
			System.out.println(rs.getInt("percentage") + "%");
		}
	}

	// method 8-10은 join사용
	private static void method8(Connection con) throws SQLException {
		System.out.println("국가명의 일부를 입력받아서 해당국가의 도시의 이름과 인구를 검색해서 출력하세요.");
		System.out.print("국가명의 일부를 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select country.name a,city.name b, city.population c from city, country where city.countrycode=country.code and country.name like ?");
		//'%' 와일드카드는 setString으로 값을 설정할 때 Java 코드에서 붙이기
		st.setString(1, "%"+x+"%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("a") + ",");
			System.out.print(rs.getString("b") + ",");
			System.out.println(rs.getInt("c") + "명");
		}
	}

	private static void method9(Connection con) throws SQLException {
		System.out.println("국가코드 또는 국가명의 일부를 입력받아서 해당국가의 도시의 이름과 인구를 검색해서 출력하세요.");
		System.out.print("국가코드 또는 국가명의 일부를 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select country.Code a , country.name b ,city.name c, city.population d from city, country where city.countrycode=country.code and (countrycode like ? or country.name like ?)");
		//SQL 쿼리 템플릿에서 사용한 물음표(?)의 갯수만큼 setString이나 setInt를 호출하여 모든 물음표를 채워줘야함
		st.setString(1, "%"+x+"%");
		st.setString(2, "%"+x+"%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("a") + ",");
			System.out.print(rs.getString("b") + ",");
			System.out.print(rs.getString("c") + ",");
			System.out.println(rs.getInt("d") + "명");
		}
	}

	private static void method10(Connection con) throws SQLException {
		System.out.println("언어를 입력받아서 해당언어가 국가공식언어인 국가명을 출력하세요.");
		System.out.print("언어를 입력하시오 :  ");
		String x = sc.next();
		
		PreparedStatement st = con.prepareStatement("select name, IsOfficial from country,countrylanguage where country.code = countrylanguage.CountryCode and isOfficial = 'T' and language=?");
		st.setString(1, x);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString("name") + ",");
			System.out.println(rs.getString("IsOfficial") + ",");
		}
	}
}