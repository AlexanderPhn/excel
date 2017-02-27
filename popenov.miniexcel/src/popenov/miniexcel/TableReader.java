package popenov.miniexcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableReader {
	
	Scanner in;
	
	public TableReader(Scanner in) {
		this.in = in;
	}
	
	public Table createTable() throws IOException {
		int heidth = 0;
		int width = 0;
		List<String> inputData = new ArrayList<>();		
				
		String[] args = in.nextLine().trim().split("\t");		
		if (args.length < 2) {
			throw new IOException("Not Enough Arguments For Table Size");			
		}
		
		try {
			heidth = Integer.parseInt(args[0]);
			width = Integer.parseInt(args[1]);
			if (heidth <= 0 || width <= 0) {
				throw new IOException("Negative Arguments For Table Size");
			}
		} catch (NumberFormatException e){
			throw new IOException("Invalid Arguments For Table Size");
		}		
		Table table = new Table(heidth,width);
		
		for (int i = 0; i < heidth; i++) {
			inputData.add(in.nextLine());
		}
		table.initializeTable(inputData);
		
		return table;	
	}
}
