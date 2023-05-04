package sqlconnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class H2Contact {

	Connection con = null;

	private void deleteContact(String CID) {

		String sql = "delete from contact where CID = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, CID);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("데이터베이스가 삭제되었습니다.");
	}

	private void insertContact(int CID, String name, String category, String address, String office, String birthday) {

		String sql = "insert into contact (CID, name, category, address, office, birthday ) values(?,?,?,?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, CID);
			ps.setString(2, name);
			ps.setString(3, category);
			ps.setString(4, address);
			ps.setString(5, office);
			ps.setString(6, birthday);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("데이터베이스가 입력되었습니다.");
	}

	private boolean connectDB() {
		try {
			Class.forName("org.h2.Driver"); // 드라이버랑 url만 바꿔주면 H2, MySQL 왔다갔다 가능, JDBC의 장점
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/telephone", "scott", "tiger");
			System.out.println("데이터베이스가 연결되었습니다.");
			return true;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;

	}

	private void closeDB() {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		H2Contact tt = new H2Contact();
		Scanner sc = new Scanner(System.in);

		System.out.println("CID 숫자를 입력하세요");
		int CID = sc.nextInt();
		while (CID > 0) {

			System.out.println("이름을 입력하세요");
			String name = sc.next();
			System.out.println("소속을 입력하세요 : 0:[friends], 1:[company], 2:[family], 그외:[etc]");
			int ctn = sc.nextInt();
			String [] cts = {"friends", "company", "family", "etc"};
			String ct;			
			if(ctn>=0 && ctn<3) {
				ct = cts[ctn];
			}else {
				ct = "etc";				
			}						
			System.out.println("주소를 입력하세요.");
			String address = sc.next();
			System.out.println("직장을 입력하세요");
			String office = sc.next();
			System.out.println("생일을 입력하세요 : yyyy-mm-dd");
			String birthday = sc.next();
			
//			String str = "1999-10-10";			
//			Date dd = Date.valueOf(str);
//			System.out.println(dd);

			if (tt.connectDB()) {
				tt.insertContact(CID, name, ct, address, office, birthday);
				// tt.deleteContact(1);
			}
			System.out.println("CID 숫자를 입력하세요");
			CID = sc.nextInt();

		}
		tt.closeDB();
	}

	// --------------------랜덤글자생성기-----------------------------
	public static String randStr() {
		Random rand = new Random();
		int len = rand.nextInt(10) + 1;
		String res = "";
		for (int i = 0; i < len; i++) {
			int alpha = rand.nextInt(26);
			boolean cap = rand.nextBoolean();
			char c;
			if (cap) {
				c = (char) (alpha + 'A');
			} else {
				c = (char) (alpha + 'a');
			}
			res += c;
		}
		return res;
	}

	// -----------------------------------------------------------
	public static String randDate() {
		Random rand = new Random();
		long d = rand.nextLong(1600000000000l); // 현재시간. 넘지않게. 특정 기준 시간으로 ms.
		Date date = new Date(d);
		return date.toString();
	}

	public static String randCt() {
		Random rand = new Random();
		int i = rand.nextInt(4);
		String[] categories = { "friend", "company", "family", "etc" };
		return categories[i];
	}

	// -----------------------------------------------------------

}
