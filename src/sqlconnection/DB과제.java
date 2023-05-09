package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

class QueryExe {
	int num;
	String text;

	QueryExe(int num, String text) {
		this.num = num;
		this.text = text;
	}

	int getNum() {
		return num;
	}

	String getText() {
		return text;
	}
}

public class DB과제 {

	private static Connection connectDB() {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");
			System.out.println("데이터베이스가 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	private static void closeDB(Connection con) {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void exe01() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println("select jname FROM j where city = 'London';");

		try {
			st = con.createStatement();
			rs = st.executeQuery("select city, jname FROM j where city = 'London';");

			System.out.println("이것은 실행 결과");

			while (rs.next()) {

				System.out.println(String.format("|%s|", rs.getString("jname")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe02() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println("select sname from s, spj where s.sno = spj.sno and jno = 'j1';");

		try {
			st = con.createStatement();
			rs = st.executeQuery("select sname from s, spj where s.sno = spj.sno and jno = 'j1';");

			System.out.println("이것은 실행 결과");

			while (rs.next()) {

				System.out.println(String.format("|%s|", rs.getString("sname")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe03() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println("select distinct sno, pno, qty from spj where qty>=300 and qty<=700;");

		try {
			st = con.createStatement();
			rs = st.executeQuery("select distinct sno, pno, qty from spj where qty>=300 and qty<=700;");

			System.out.println("이것은 실행 결과");
			System.out.println("┌─────────┐");
			while (rs.next()) {

				System.out.println(
						String.format("│%s│%s│%d│", rs.getString("sno"), rs.getString("pno"), rs.getInt("qty")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe04() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println("select jname FROM j where city = 'London';");

		try {
			st = con.createStatement();
			rs = st.executeQuery("select distinct color, city from p;");

			System.out.println("이것은 실행 결과");

			while (rs.next()) {

				System.out.println(String.format("│%6s│%6s│", rs.getString("color"), rs.getString("city")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe05() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println(
				"select spj.sno, spj.pno, spj.jno from s,p,j,spj where s.city = j.city and j.city = p.city and s.city = p.city and s.sno = spj.sno and p.pno = spj.pno and j.jno = spj.jno;");

		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"select distinct sno, pno, jno from s,p,j where s.city = j.city and j.city = p.city and s.city = p.city;");

			System.out.println("이것은 실행 결과");

			while (rs.next()) {

				System.out.println(
						String.format("│%s│%s│%s│", rs.getString("sno"), rs.getString("pno"), rs.getString("jno")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe06() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println(
				"select distinct sno, pno, jno from s,p,j where s.city <> j.city and j.city <> p.city and s.city <> p.city;");

		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"select distinct sno, pno, jno from s,p,j where s.city <> j.city and j.city <> p.city and s.city <> p.city;");

			System.out.println("이것은 실행 결과");

			while (rs.next()) {

				System.out.println(
						String.format("│%s│%s│%s│", rs.getString("sno"), rs.getString("pno"), rs.getString("jno")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe07() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println(
				"select pname, pno from p where pno in (select pno from spj where sno in (select sno from s where city = 'london'));");

		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"select pname, pno from p where pno in (select pno from spj where sno in (select sno from s where city = 'london'));");

			System.out.println("이것은 실행 결과");

			while (rs.next()) {

				System.out.println(String.format("│%s│%s│", rs.getString("pname"), rs.getString("pno")));
			}

			System.out.println("-".repeat(80));
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static void exe08() {
		Connection con = connectDB();
		Statement st = null;
		ResultSet rs = null;

		System.out.println("이것은 쿼리");
		System.out.println(
				"select p.pname, p.pno from p,spj,s, j where p.pno = spj.pno and spj.sno = s.sno and spj.jno = j.jno and s.city = 'london' and j.city = 'london';");

		try {
			String sql = "select p.pname, p.pno from p,spj,s, j where p.pno = spj.pno and spj.sno = s.sno and spj.jno = j.jno and s.city = 'london' and j.city = 'london'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCnt = rsmd.getColumnCount(); // 컬럼의 수
			if (rs.next()) {
				for (int i = 1; i <= columnCnt; i++) { // 컬럼명 //데이터
					System.out.print(rsmd.getColumnName(i) + "│");
				}
			}

			st = con.createStatement();
			rs = st.executeQuery(
					"select p.pname, p.pno from p,spj,s, j where p.pno = spj.pno and spj.sno = s.sno and spj.jno = j.jno and s.city = 'london' and j.city = 'london';");

			System.out.println("이것은 실행 결과");
			while (rs.next()) {
				System.out.println(String.format("│%s│%s│", rs.getString("pname"), rs.getString("pno")));

			}
			System.out.println("-".repeat(80));

		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeDB(con);
	}

	public static String[] mission = { "London에 있는 프로젝트 이름을 찾아라.", "프로젝트 j1에 참여하는 공급자의 이름을 찾아라.",
			"공급수량이 300이상 750이하인 모든 공급의 sno, pno, qty를 찾아라.", "부품의 color와 city의 모든 쌍을 찾아라. 같은 쌍은 한 번만 검색되어야 한다.",
			"같은 도시에 있는 공급자, 부품, 프로젝트의 모든 sno, pno, jno 쌍을 찾아라. 찾아진 sno, pno, jno의 city들은 모두 같아야 한다.",
			"같은 도시에 있지 않는 공급자, 부품, 프로젝트의 sno, pno, jno을 찾아라. 찾아진 sno, pno, jno의 city 들은 같은 것이 없어야 한다.",
			"London에 있는 공급자에 의해 공급된 부품의 번호, 이름을 찾아라.", "London에 있는 공급자가 London의 프로젝트에 공급한 부품의 부품 번호와 이름을 찾아라." };

	public static void main(String[] args) {
		ArrayList<QueryExe> list = new ArrayList<>();

		for (int i = 0; i < mission.length; i++) {
			list.add(new QueryExe(i + 1, mission[i]));
		}

		Scanner sc = new Scanner(System.in);

		while (true) {
			for (QueryExe qe : list) {
				System.out.println(String.format("%d. %s", qe.getNum(), qe.getText()));
			}

			System.out.print("선택<0:exit>:");
			int sel = sc.nextInt();
			if (sel == 0)
				break;

			switch (sel) {
			case 1:
				exe01();
				break;

			case 2:
				exe02();
				break;

			case 3:
				exe03();
				break;

			case 4:
				exe04();
				break;

			case 5:
				exe05();
				break;

			case 6:
				exe06();
				break;

			case 7:
				exe07();
				break;

			case 8:
				exe08();
				break;

			default:
				System.out.println("----------제대로 선택하셈----------");

			}
		}
	}
}
