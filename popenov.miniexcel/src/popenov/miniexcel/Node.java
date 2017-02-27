package popenov.miniexcel;

public class Node {

	private int value;
	private boolean isCalculated;
	
	public Node() {
		value = 0;
		isCalculated = false;
	}
	
	public Node(int value) {
		this.value = value;
		isCalculated = true;
	}
	
	public int getIntValue() {
		return value;
	}
	
	public void setIntValue(int value) {
		this.value = value;
	}
	
	public boolean isCalculated() {
		return isCalculated;
	}
	
	public void setCalculated(boolean calculated) {
		isCalculated = calculated;
	}
	
	public int calculate() {
		return value;
	}
}
