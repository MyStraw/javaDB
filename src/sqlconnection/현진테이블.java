package sqlconnection;

public class 현진테이블 {
	private static void printResult(String query, Connection con) throws SQLException {
		ResultSet rs = con.createStatement().executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		int[] colSizes = new int[colCount];
		colSizes[0] = rsmd.getColumnDisplaySize(1);
		String columnLabel = rsmd.getColumnLabel(1);
		int labelLen = columnLabel.length();
		if (colSizes[0] < labelLen) {
			colSizes[0] = labelLen;
		}
		String upline = " ┌" + "─".repeat(colSizes[0] + 2);
		String nameline = " │ " + columnLabel + " ".repeat(colSizes[0] - labelLen);
		String downline = " ├" + "─".repeat(colSizes[0] + 2);
		String lastline = " └" + "─".repeat(colSizes[0] + 2);
		for (int i = 1; i < colCount; i++) {
			int colSize;
			colSize = rsmd.getColumnDisplaySize(i + 1);
			columnLabel = rsmd.getColumnLabel(i + 1);
			labelLen = columnLabel.length();
			if (colSize < labelLen) {
				colSize = labelLen;
			}
			colSizes[i] = colSize;
			upline += "┬" + "─".repeat(colSizes[i] + 2);
			nameline += " │ " + columnLabel + " ".repeat(colSizes[i] - labelLen);
			downline += "┼" + "─".repeat(colSizes[i] + 2);
			lastline += "┴" + "─".repeat(colSizes[i] + 2);
		}
		System.out.println(upline + "┐");
		System.out.println(nameline + " │");
		System.out.println(downline + "┤");
		while (rs.next()) {
			for (int i = 1; i <= colCount; i++) {
				int colSize = colSizes[i - 1];
				int type = rsmd.getColumnType(i);
				if (type == 1 || type == 12) {
					String element = rs.getString(i);
					System.out.print(" │ " + " ".repeat(colSize - element.length()) + element);
				} else {
					int element = rs.getInt(i);
					System.out.print(" │ " + " ".repeat(colSize - numLen(element)) + element);
				}
			}
			System.out.println(" │");
		}
		System.out.println(lastline + "┘");
	}

}
