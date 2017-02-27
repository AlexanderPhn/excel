package popenov.miniexcel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	
	private int tableHeight;
	private int tableWidth;
	private Map<String, Cell> table = new HashMap<>();
	
	public Table(int heidth, int width) {
		tableHeight = heidth;
		tableWidth = width;
		for (int i = 0; i < tableHeight; i++) {
			for (int j = 0; j < tableWidth; j++) {
				String key = getKey(i + 1, j);
				table.put(key, new Cell(key, ""));
			}
		}
	}
	
	/**
	 * Fill table cell according cell description in inputData.
	 * Because cell can contain references to other cells, we update exicting cells in table, instead of creating new
	 * @param inputData the description of a table where line elements are tab-separated
	 */
	public void initializeTable(List<String> inputData) {		
		CellCreator cellCreator = new CellCreator(this);
		for (int i = 0; i < inputData.size(); i++) {
			String[] args = inputData.get(i).split("\t", -1);
			for (int j = 0; j < args.length; j++) {
				String key = getKey(i + 1, j);
				if (table.containsKey(key)) {
					table.get(key).updateCell(cellCreator.getCell(args[j]));
				}
			}
		}			
	}
	
	public boolean containsCell(String key) {
		return table.containsKey(key);
	}
	
	public Cell getCell(String key) {
		return table.get(key);
	}
	
	public Collection<Cell> getSetCells() {
		return table.values();
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tableHeight; i++) {
			for (int j = 0; j < tableWidth; j++) {
				String key = getKey(i+1,j);
				result.append(table.get(key).toString());
				if (j < tableWidth - 1) {
					result.append("\t");
				}
			}
			result.append("\n");
		}
		return result.toString();
	}
	
	/**
	 * @param row
	 * @param column
	 * @return cell address
	 */
	private String getKey(int row, int column) {
		StringBuffer key = new StringBuffer();
		int ratio = column;
		int remainder = ratio % 26;
		while(ratio >= 26) {
			remainder = ratio % 26;
			ratio = (ratio / 26) - 1;
			key.append((char)('A' + remainder));
		}
		key.append((char)('A' + ratio));
		key.reverse();
		key.append(row);		
		return key.toString();
	}
}
