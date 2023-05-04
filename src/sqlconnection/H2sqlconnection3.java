package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class H2sqlconnection3 {

	Connection con = null;

	private void deleteDept(String dno) {

		String sql = "delete from dept where dno = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dno);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("데이터베이스가 삭제되었습니다.");
	}

	private void insertDept(String dno, String dname, int budget) {

		String sql = "insert into dept (dno, dname, budget) values(?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dno);
			ps.setString(2, dname);
			ps.setInt(3, budget);
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
		H2sqlconnection3 tt = new H2sqlconnection3();
		if (tt.connectDB()) {
			for (int i = 1; i <= 100; i++) {
				int c = rand.nextInt(100_000);
				tt.insertDept("d" + i, "dname" + i, rand.nextInt(i) * (c + 1));
				// tt.insertDept("d" + i, "dname" + i, c*10_000);
				// tt.deleteDept("d"+i);
			}

			tt.closeDB();
		}
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
