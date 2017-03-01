package popenov.miniexcel;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import org.junit.Rule;

public class AppTest {
	
	@Test
	public void defaultTest() {
		TableReader reader = new TableReader(new Scanner("3	4\n"
				+ "12	=C2	3	'Sample\n"
				+ "=A1+B1*C1/5	=A2*B1	=B3-C3	'Spread\n"
				+ "'Test	=4-3	5	'Sheet\n"));
		String excepted = "12	-4	3	Sample\n"
				+ "4	-16	-4	Spread\n"
				+ "Test	1	5	Sheet\n";
		Table excelTable = null;
		try {
			excelTable = reader.createTable();
			Calculator tableCalculator = new Calculator(excelTable);
			tableCalculator.checkTableReferences();
			tableCalculator.calculateCellsValue();
			Assert.assertEquals(excepted, excelTable.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void cycleTest() {
		TableReader reader = new TableReader(new Scanner("2	2\n"
				+ "=b1+a2	=a1+b1\n"
				+ "=5-9	3"));
		String excepted = "#CycleError	#CycleError\n"
				+ "-4	3\n";
		Table excelTable = null;
		try {
			excelTable = reader.createTable();
			Calculator tableCalculator = new Calculator(excelTable);
			tableCalculator.checkTableReferences();
			tableCalculator.calculateCellsValue();
			Assert.assertEquals(excepted, excelTable.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void badReferenceTest() {
		TableReader reader = new TableReader(new Scanner("2	2\n"
				+ "=b1+a2	10\n"
				+ "'text	=8*/5"));
		String excepted = "#InvalidReference	10\n"
				+ "text	#InvalidContent\n";
		Table excelTable = null;
		try {
			excelTable = reader.createTable();
			Calculator tableCalculator = new Calculator(excelTable);
			tableCalculator.checkTableReferences();
			tableCalculator.calculateCellsValue();
			Assert.assertEquals(excepted, excelTable.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void emptyDataTest() {
		TableReader reader = new TableReader(new Scanner("2	2\n"
				+ "\n"
				+ "\n"));
		String excepted = "	\n"
				+ "	\n";
		Table excelTable = null;
		try {
			excelTable = reader.createTable();
			Calculator tableCalculator = new Calculator(excelTable);
			tableCalculator.checkTableReferences();
			tableCalculator.calculateCellsValue();
			Assert.assertEquals(excepted, excelTable.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void notEnoughArgumentsTest() throws IOException {
		thrown.expect(IOException.class);
		thrown.expectMessage("Not Enough Arguments For Table Size");
		TableReader reader = new TableReader(new Scanner("2\n"
				+ "\n"
				+ "\n"));
		reader.createTable();
	}
	
	@Test
	public void negativeArgumentsTest() throws IOException {
		thrown.expect(IOException.class);
		thrown.expectMessage("Negative Arguments For Table Size");
		TableReader reader = new TableReader(new Scanner("1	-3\n"
				+ "\n"
				+ "\n"));
		reader.createTable();
	}
	
	@Test
	public void invalidArgumentsTest() throws IOException {
		thrown.expect(IOException.class);
		thrown.expectMessage("Invalid Arguments For Table Size");
		TableReader reader = new TableReader(new Scanner("2	2s\n"
				+ "\n"
				+ "\n"));
		reader.createTable();
	}
}
