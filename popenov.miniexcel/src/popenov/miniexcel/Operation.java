package popenov.miniexcel;

public class Operation extends Node{

	private char operation;
	private Node leftOperand;
	private Node rightOperand;
	
	public Operation() {
		super();
	}
	
	public Operation(char operation, Node leftOperand, Node rightOperand) {
		super();
		this.operation = operation;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}
	
	public Node getLeft() {
		return leftOperand;
	}
	
	public Node getRight() {
		return rightOperand;
	}
	
	@Override
	public int calculate() throws ArithmeticException {
		int result = 0;
		try {
			switch (operation) {
			case '+':
				result = leftOperand.calculate() + rightOperand.calculate();
				break;
			case '-':
				result = leftOperand.calculate() - rightOperand.calculate();
				break;
			case '*':
				result = leftOperand.calculate() * rightOperand.calculate();
				break;
			case '/':
				result = leftOperand.calculate() / rightOperand.calculate();
				break;
			case '=':
				result = leftOperand.calculate();
				break;
			}
		} catch (ArithmeticException e) {
			throw e;
		}
		this.setCalculated(true);
		this.setIntValue(result);
		return result;
	}
}
