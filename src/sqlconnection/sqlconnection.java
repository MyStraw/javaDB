package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlconnection { // mysql 에서 무조건 기본적으로 하는 연결코드
	public static void main(String[] args) { //기본이다. 암기.
		Connection con = null;
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/world";//db에 접근할수 있는 경로. 서버가 다른데 있으면 localhost에 아이피주소.

			String username = "scott";
			String password = "tiger";

			Class.forName(driver); //mysql에서쓸수있는 드라이버 로드과정. 스태틱 클래스.
			con = DriverManager.getConnection(url, username, password);
			//con을 이용해서 조회 넣고 배고.

			System.out.println("Connection Succes");

		} catch (Exception e) {
			e.printStackTrace();
		} finally { //에러나도 무조건 실행
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}

// try {
//a = 10/0
//}
//catch (Exception e) {
//} 
//finally {
//	if(conn!=null) conn.close(); 클로즈까지 해놔야 완벽한코드.
//}