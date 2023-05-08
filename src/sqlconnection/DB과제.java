package sqlconnection;

import java.util.ArrayList;
import java.util.Scanner;

class QueryExe{
	int num;
	String text;
	
	QueryExe(int num, String text){
		this.num = num;
		this.text = text;
	}
	
	int getNum() {return num;}
	String getText() {return text;}	
}


public class DB과제 {
	
	public static void exe01 () {
		System.out.println("이것은 쿼리");
		System.out.println("이것은 실행 결과");
		
	}
	
	public 
	
	public static void main(String[] args) {
		
		ArrayList<QueryExe> list = new ArrayList<>();
		
		list.add(new QueryExe(1,"London에 있는 프로젝트 이름을 찾아라."));
		
		
		
		Scanner sc = new Scanner(System.in);
		while (true) {
			
			for (QueryExe qe : list) {
				System.out.println(String.format("%d. %s", qe.getNum(),qe.getText()));
			}
			
			System.out.print("선택<0:exit>:");
			int sel = sc.nextInt();
			if (sel == 0)
				break;
			
			for (QueryExe qe : list) {
				if(qe.getNum()==sel ) { //스위치 문, else if문, 등등~ //자신있으면 메인 빼고 static 없이 해보세용
					
				}
			}
			
			switch(sel) {
			case 1:exe01(); break;
			default 
			}
		}
	}

}
