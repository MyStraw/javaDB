package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class MillionData_contact_MySql {

	
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
		MillionData_contact_MySql tt = new MillionData_contact_MySql();
		Connection con = tt.connectDB();
		if (con != null) {
			tt.insertContactwithStatement(con);
			tt.closeDB(con);
		}
	}

	private void insertContactwithStatement(Connection con) {

		Statement st = null;
		String[] cates = { "friends", "company", "family", "etc" };
		Random rd = new Random();
		int totcnt = 1_000_000;

		try {
			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {
				
				String name = "name" + i;
				String ct = cates[rd.nextInt(4)]; // 0~3
				String address = "address" + i;
				String office = "office" + i;
				String birthday = String.format("%4d-%02d-%02d", rd.nextInt(1950, 2022), rd.nextInt(1, 13),
						rd.nextInt(1, 29));

				String sql = String.format(
						"insert into contact (CID, name, category, address, office, birthday) "
								+ "values (%d, '%s', '%s', '%s', '%s', '%s')",
						i, name, ct, address, office, birthday);
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