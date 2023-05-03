package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlconnection2 {

	Connection con = null;
	Statement st = null;

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

	private void insertDeptStatement(String dno, String dname, int budget) {

		String sql = String.format("insert into dept (dno, dname, budget) values('%s','%s','%d')", dno, dname, budget);
		//이건 prepared 안써도 돼. 밑에 봐봐. 안썼당. 

		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("데이터베이스가 입력되었습니다.");
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
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");
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

	public static void main(String[] args) {

		sqlconnection2 tt = new sqlconnection2();
		if (tt.connectDB()) {
			tt.insertDept("d30", "dname30", 123123123);
			tt.insertDept("d31", "dname31", 123321321);
			tt.deleteDept("d21");
			tt.deleteDept("d22");
			tt.insertDeptStatement("d40", "dname40", 44444444);
			tt.closeDB();
		}
	}

}
