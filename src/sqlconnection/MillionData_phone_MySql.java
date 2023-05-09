package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class MillionData_phone_MySql {
	private Connection connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");
			System.out.println("데이터베이스가 연결되었습니다.");
			return con;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	private void closeDB(Connection con) {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MillionData_phone_MySql tt = new MillionData_phone_MySql();
		Connection con = tt.connectDB();
		if (con != null) {
			tt.insertContactwithStatement(con);
			tt.closeDB(con);
		}
	}

	private void insertContactwithStatement(Connection con) {

		Statement st = null;
		String[] types = { "mobile", "home", "office", "fax", "etc" };
		Random rd = new Random();
		int totcnt = 1_000_000;

		try {
			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {

				int Seq = rd.nextInt(3) + 1;
				String type = types[rd.nextInt(5)]; // 0~3
				String number = String.format("010-%04d-%04d", rd.nextInt(0, 10000), rd.nextInt(0, 10000));

				String sql = String.format(
						"insert into phone (CID, Seq, number, type)" + "values (%d, %d, '%s', '%s')", i, Seq, number,
						type);
//				System.out.println(sql);
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
