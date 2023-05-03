package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// Statement : 완결된 Query 문장을 실행할 때
// PreparedStatement : 변수로 값을 추가할 수 없는 Query문을 실행할때

// select -> executeQuery : PreparedStatment, Statement
// insert/delete/update ==> executeUpdate : PreparedStatment, Statement

public class sqlconnection2 {

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

	// Statement, executeUpdate 사용

	private void insertDeptStatement(String dno, String dname, int budget) {

//		String sql = String.format("insert into dept (dno, dname, budget) values('%s','%s','%d')", dno, dname, budget);
		// 이건 prepared 안써도 돼. 밑에 봐봐. 안썼당.

		try {
			Statement st = con.createStatement();

//			st.executeUpdate(sql); 이거 쓰려면 위에 String sql = String.format("insert~적기)
			int cnt = st.executeUpdate(
					String.format("insert into dept (dno, dname, budget) values('%s','%s','%d')", dno, dname, budget));
//	int cnt = con.createStatment().executeUpdate(String.format("insert into dept (dno, dname, budget) values('%s','%s','%d')", dno, dname, budget));
			System.out.println("데이터가" + cnt + "개가 입력되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("데이터베이스가 입력되었습니다.");
	}

	// PreparedStatment, executUpdate를 사용

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

	// -------------------------------------------------------------------------------------------------------------
	private void deleteDepttriggerPrepared(int from, int to) {
		String sql = "delete from depttrigger where ? <= id and id <= ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, from);
			ps.setInt(2, to);

			int cnt = ps.executeUpdate();

			System.out.println("데이터가" + cnt + "개가 삭제되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void deleteDepttriggerStatement(int from, int to) {
		try {
			Statement st = con.createStatement();

			int cnt = st.executeUpdate(String.format("delete from depttrigger where %d <= id and id <= %d", from, to));

			System.out.println("데이터가" + cnt + "개가 삭제되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------------------------------------------------

	private void updateDeptPrepared(String dno, String dname, int budget) {

		try {
			PreparedStatement ps = con.prepareStatement("update dept set dname =?, budget = ? where dno=?");
			ps.setString(1, dname);
			ps.setInt(2, budget);
			ps.setString(3, dno);
			int cnt = ps.executeUpdate();

			System.out.println("데이터가" + cnt + "개가 수정되었습니다.");

			int a = 10 / 0; // 에러 예외 한번 만들어보자. 0으로 나누는거 자체가 에러.

		} catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception"); // sql 에서 에러가 난 범위 벗어나면 이거에서 걸리도록. 돌려봐. 이게 콘솔에 뜬다
			e.printStackTrace(); // 빨간글자 에러
		}

	}

	private void updateDeptStatement(String dno, String dname, int budget) {

		try {
			Statement st = con.createStatement();

			int cnt = st.executeUpdate(
					String.format("update dept set dname = '%s', budget = %d where dno= '%s'", dname, budget, dno));

			System.out.println("데이터가" + cnt + "개가 수정되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {

		sqlconnection2 tt = new sqlconnection2();
		if (tt.connectDB()) {
//			tt.insertDept("d30", "dname30", 123123123);
//			tt.insertDept("d31", "dname31", 123321321);
//			tt.deleteDept();
//			tt.deleteDepttriggerStatement(6, 19);
//			tt.deleteDepttriggerPrepared(21, 30);
			tt.updateDeptPrepared("d1", "dname1", 1000);
//			tt.updateDeptStatement("d1", "dname", 2000);
//	
//			tt.insertDeptStatement("d40", "dname40", 44444444);
			tt.closeDB();
		}
	}

}

// insert into ~ () values (%s, %s,~~ ) 문자인 경우에 이러면 안돼. 문자형 데이터니까 '%s'로 해줘야 한다.
//					values ('?') setString은 알아서 해쥼

// "insert into ~ ) values ("%s",") 이렇게 해버리면 맨앞 쌍따옴표랑 %앞 "까지 한 문장으로 인식해
// 문장에 "를 굳이 쓰고싶다면.  \" 이렇게 하면 "를 무시하고 하라는 말로 쓸수있다. 