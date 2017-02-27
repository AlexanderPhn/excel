package popenov.miniexcel;

import java.util.HashSet;
import java.util.Set;

public class Cell extends Node{
	
	private String key;
	private String content;
	private Operation operation;
	private Set<Cell> references = new HashSet<>();	
		
	public Cell(String key, String data) {
		super(0);
		this.key = key;
		content = data;
	}
	
	public Cell(int value, String data) {
		super(value);
		key="";
		content = data;
	}
	
	public Cell(Operation operation, Set<Cell> references) {
		super();
		this.operation = operation;
		this.references = references;
	}
	
	public String getKey() {
		return key;
	}
	
	@Override
	public String toString() {
		return content;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public Set<Cell> getReferences() {
		return references;
	}
	
	public void updateCell(Cell cell) {
		this.setIntValue(cell.getIntValue());
		this.setCalculated(cell.isCalculated());
		content = cell.content;
		references = cell.references;
		operation = cell.operation;
	}
	
	@Override
	public int calculate() throws ArithmeticException {
		if (this.isCalculated() && operation != null) {
			return this.getIntValue();
		}
		if (operation == null) {
			throw new ArithmeticException();
		}
		int value = 0;
		try {
			value = operation.calculate();
		} catch (ArithmeticException e) {
			this.setIntValue(0);
			this.setCalculated(true);
			content = "#DivByZero";
			references = null;
			operation = null;
			throw e;
		}		
		this.setCalculated(true);
		this.setIntValue(value);
		content = String.valueOf(value);
		return value;
	}
}
