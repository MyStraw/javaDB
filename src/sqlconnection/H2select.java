package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2select {
	public static void main(String[] args) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/telephone", "scott", "tiger");

			st = con.createStatement();
			rs = st.executeQuery("Select dno, dname, budget from dept order by convert(substr(dno,2),signed)");//subst로 d 뒤의 숫자만 뽑고, convert signed로 문자를 숫자로 바꾼다.
			// 그냥 order by dno 했을때 d1, d10 이런식으로 정렬되는걸 d1, d2가 되도록. 그냥 order by 이하 부분을 다 없애도 되는데 sql로 없애는걸 해본거.

			while (rs.next()) {
				System.out.println(
						String.format("  %s | %s| %7d", rs.getString("dno"), rs.getString("dname").substring(0, 8), rs.getInt("budget")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

	}

}
