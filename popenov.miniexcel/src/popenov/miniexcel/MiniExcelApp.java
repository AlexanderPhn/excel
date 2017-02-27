package popenov.miniexcel;

import java.io.IOException;
import java.util.Scanner;

public class MiniExcelApp {

	public static void main(String[] args) {				
		TableReader reader = new TableReader(new Scanner(System.in));
		Table excelTable = null;
		try {
			excelTable = reader.createTable();
			Calculator tableCalculator = new Calculator(excelTable);
			tableCalculator.checkTableReferences();
			tableCalculator.calculateCellsValue();
			System.out.println(excelTable.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}