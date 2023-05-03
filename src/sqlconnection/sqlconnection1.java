package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlconnection1 {
	public static void main(String[] args) { //spj 불러오기.

		Connection con = null; //밑에 final, catch에서 써먹으려고 밖으로빼놨다.
		Statement st = null; // 시스템 리소스. 이런건 이용하고 닫아야한다. 가비지 컬렉터가 하는양 줄여야.
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //open java database connectivity.
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");
			//con 객체 만들어서 연결. con이 안만들어지면 null. 
			//아래에 첫날이랑 다른 코드
			st = con.createStatement(); //st(참조변수) Statement 객체 생성
			// mysql에 도움을 얻으러갈 중간도우미, 질의를 날리기 위한 객체가 st.
			rs = st.executeQuery("Select sno, pno, jno, qty from spj order by sno");
			// execute라는걸 하는 객체. 워크벤치에서 하는 쿼리문 그대로.
			// resultset에 execute로 리턴되어 돌아와야 하는데, rs (참조변수)에 resultset으로.
			
			// 제주도에 질의 날리면 100개 질의날리면 하나 하나 하나 받는데
			// 실제로는 워크벤치에서 100개의 레코드 보는 프로그램이 되는거. 100번 읽어온 뒤에 보여주는거지
			// 우린 워크벤치 하는 역할 애를 만들어 주는거.
			
			while (rs.next()) { //루프를 돈다. 커서 프로세싱을 하는거. 하나하나. 
				System.out.println(String.format("%s,%s,%s,%d", //포메팅 형식으로. 이렇게 출력. 
						rs.getString("sno"), rs.getString("pno"),
						rs.getString("jno"), rs.getInt("qty")));
			} //문자열은 %s, %c 문자 1개, %d = int, %l = long?, %f = float, double
			 // %5.2f = 21.11같은... %5d= 5칸 맞춰서... 확보.

		} catch (Exception e) {
			e.printStackTrace();
		} finally { //가비지 컬렉터 하는양 줄이기. 뭐든 밑에껀 무조건.
			try { //닫기. 오류방지.
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
