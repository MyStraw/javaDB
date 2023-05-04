package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

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
		Random rand = new Random();
		H2Contact tt = new H2Contact();
		if (tt.connectDB()) {		
				tt.insertContact(2, "이현진", "friends", "사상", "부산대", "2000-11-18");
				// tt.deleteContact(1);
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
}
