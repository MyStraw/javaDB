package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

class QueryExe1{
	
	int num;
	String text;
	
	public QueryExe1(int i, String string) {
	this.num = num;
	this.text = text;
	}
	
	int getNum() {
		return num;
	}
	
	String getText(){
		return text;
	}
	
	
}




public class DB과제_제출{
	private static Connection connectDB() {
		Connection con = null;
	}
	
	
	
	
	public static void main(String[] args) {
		
		ArrayList<QueryExe1> list = new ArrayList<>();
		
		QueryExe1 qe = new QueryExe1(1, "냐하하");
		
		
		list.add(new QueryExe1(1, "쏼라"));
		
		Scanner sc = new Scanner(System.in);
		System.out.println(String.format("%d. %s",qe.getNum(), qe.getText()));
		
	}
}





