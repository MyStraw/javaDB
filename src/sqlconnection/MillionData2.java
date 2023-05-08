package sqlconnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class MillionData2 {

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
		MillionData2 tt = new MillionData2();



		if (tt.connectDB()) {
//				tt.insertContact(CID, name, ct, address, office, birthday);
			// tt.deleteContact(1);
			tt.insertContactwithStatement();
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
	private void insertContactwithStatement(Connection con) {

		Statement st = null;
		String[] cates = { "친구", "가족", "직장동료", "기타" };
		Random rd = new Random();
		int totcnt = 100;

		try {
			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {

				int CID = i;
				String name = "name" + i;
				String ct = cates[rd.nextInt(4)]; // 0~3
				String address = "addr" + i;
				String office = "company" + i;
				String birthday = String.format("%4d-%02d-%02d", rd.nextInt(1950, 2022), rd.nextInt(1, 13),
						rd.nextInt(1, 29));

				String sql = String.format("insert into contact (CID, name, category, address, company, birthday) "
						+ "values (%d, '%s', '%s', '%s', '%s', '%s')", CID, name, ct, address, office, birthday);
				System.out.println(sql);

				st.executeUpdate(sql);

				System.out.println(String.format("%.2f:%d/%d", i * 100 / (double) totcnt, i, totcnt));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}