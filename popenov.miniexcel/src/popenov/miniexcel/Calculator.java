package popenov.miniexcel;

import java.util.HashSet;
import java.util.Set;

public class Calculator {
			
	private Table table;
		
	public Calculator(Table table) {
		this.table = table;
	}
	
	/**
	 * Check table for all references:
	 * - cycles
	 * - unsupported references (for example cell has references on text cell)
	 */
	public void checkTableReferences() {
		Set<Cell> visitedCells = new HashSet<>();
		for (Cell cell : table.getSetCells()) {
			try {
				dfs(cell.getReferences(), visitedCells);
			} catch (IllegalArgumentException e) {
				cell.updateCell(new Cell("", "#" + e.getMessage()));
			}
		}
	}
		
	/**
	 * Method for depth-first-search
	 * @param cells
	 * @param visitedCells
	 * @throws IllegalArgumentException if found bad references
	 */
	private void dfs(Set<Cell> cells, Set<Cell> visitedCells) throws IllegalArgumentException {
		for (Cell cell : cells) {
			if (visitedCells.contains(cell)) {
				throw new IllegalArgumentException("CycleError");
			}
			if (cell.getOperation() == null) {
				throw new IllegalArgumentException("InvalidReference");
			}
			visitedCells.add(cell);
			try {
				dfs(cell.getReferences(), visitedCells);
			} catch (IllegalArgumentException e) {
				cell.updateCell(new Cell("", "#" + e.getMessage()));
				throw e;
			}
			visitedCells.remove(cell);
		}
	}
	
	public void calculateCellsValue() {
		for (Cell cell : table.getSetCells()) {
			if (!cell.isCalculated()) {
				try {
				     cell.calculate();
				} catch (ArithmeticException e) {
				}
			}
		}
	}
}
