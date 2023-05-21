package sqlconnection;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.Random;

//contact/phone에 데이터 입력

/*

CREATE TABLE `contact` (

`cid` int NOT NULL AUTO_INCREMENT,

`name` varchar(20) DEFAULT NULL,

`cate` enum('친구','가족','직장동료','기타') DEFAULT '기타',

`addr` varchar(40) DEFAULT NULL,

`company` varchar(40) DEFAULT NULL,

`birth` date DEFAULT NULL,

PRIMARY KEY (`cid`)

)

*/

public class Test03 {

// Statment, executeUpdate

	private void insertContactWithStatement(Connection con) {

		Statement st = null;

		String[] cates = { "친구", "가족", "직장동료", "기타" };

		Random rd = new Random();

		int totcnt = 100;

		try {

			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {

				String name = "name" + i;

				String cate = cates[rd.nextInt(4)];// 0 ~ 3

				String addr = "addr" + i;

				String company = "company" + i;

				String birth = String.format("%4d-%02d-%02d",

						rd.nextInt(1950, 2022), rd.nextInt(1, 13), rd.nextInt(1, 29));

				String sql = String.format("insert into contact (cid, name, cate, addr, company, birth) "

						+ "values (%d,'%s','%s','%s','%s','%s')", i, name, cate, addr, company, birth);

//System.out.println(sql);

//st.executeUpdate(sql);

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

		System.out.println("Contact 입력이 완료되었습니다.");

	}

	/*
	 * 
	 * CREATE TABLE `phone` (
	 * 
	 * `CID` int NOT NULL,
	 * 
	 * `Seq` int NOT NULL,
	 * 
	 * `number` varchar(20) NOT NULL,
	 * 
	 * `type` enum('집','회사','팩스','휴대폰','기타') NOT NULL DEFAULT '기타',
	 * 
	 * PRIMARY KEY (`CID`,`Seq`)
	 * 
	 * )
	 * 
	 */

// PreaparedStatment, executeUpdate

	private void insertPhoneWithPreparedStatement(Connection con) {

		PreparedStatement ps = null;

		String[] cates = { "휴대폰", "집", "회사", "팩스", "기타" };

		Random rd = new Random();

		int totcnt = 10000;

		String sql = "insert into phone (cid, seq, type, number) values (?, ?, ?, ?)";

		try {

			ps = con.prepareStatement(sql);

			for (int i = 0; i < totcnt; i++) {

				ps.setInt(1, i / 3 + 1);

				ps.setInt(2, i % 3 + 1);

				int type = rd.nextInt(5);

				ps.setString(3, cates[type]);

				if (type == 0)

					ps.setString(4, String.format("010-%04d-%04d", rd.nextInt(1, 10000), rd.nextInt(1, 10000)));

				else

					ps.setString(4, String.format("%03d-%04d-%04d", rd.nextInt(1, 99), rd.nextInt(1, 10000),
							rd.nextInt(1, 10000)));

//ps.executeUpdate();

				System.out.println(String.format("%.2f:%d/%d", (i + 1) * 100 / (double) totcnt, i + 1, totcnt));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			if (ps != null) {

				try {

					ps.close();

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		}

		System.out.println("Phone 입력이 완료되었습니다.");

	}

	private Connection connectDB() {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook", "scott", "tiger");

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

		Test03 tt = new Test03();

		Connection con = tt.connectDB();

		tt.insertContactWithStatement(con);

		tt.insertPhoneWithPreparedStatement(con);

		tt.closeDB(con);

	}

}