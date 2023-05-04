package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class H2Phone {

	Connection con = null;

	private void deletePhone(String CID) {

		String sql = "delete from phone where dno = CID ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, CID);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("데이터베이스가 삭제되었습니다.");
	}

	private void insertPhone(int CID, int Seq, String number, String type) {

		String sql = "insert into phone (CID, Seq, number, type) values(?,?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, CID);
			ps.setInt(2, Seq);
			ps.setString(3, number);
			ps.setString(4, type);
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
		H2Phone tt = new H2Phone();
		if (tt.connectDB()) {
			tt.insertPhone(1, 1, "010-1234-5678", "mobile");
			// tt.deletePhone("d"+i);
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
