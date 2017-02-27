package popenov.miniexcel;

import java.util.HashSet;
import java.util.Set;

public class CellCreator {

	private Table table;
	
	public CellCreator(Table table) {
		this.table = table;
	}
	
	/**
	 * Fill cell by input data. Each cell may contain:
	 * <ul>
	 * 	<li>nothing</li>
	 * 	<li>a positive integer</li>
	 * <li>strings that begin with symbol '</li>
	 * <li>Expression lines, that begin with ‘=’ and incorporate positive integers, cell references, and simple arithmetic expressions.</li>
	 * <li>Error with message, that begin with '#'</li>
	 * </ul>
	 * 
	 * @param data - cell description
	 * @return filled cell
	 */
	public Cell getCell(String data) {
		if (data == null || data.isEmpty()) {
			return new Cell("","");
		} 
		
		else if (data.startsWith("'")) {
			return new Cell("", data.substring(1));
		} 
		
		else if (data.startsWith("=")) {
			String[] terms = data.substring(1).toUpperCase().replaceAll("\\s", "").split("[-+*/]", -1);
			Set<Cell> references = new HashSet<>();
			for (String term : terms) {
				if (table.containsCell(term)) {
					references.add(table.getCell(term));
				} else {
					if (!term.matches("\\d+$")) {
						return new Cell("", "#InvalidContent");
					}
				}
			}
			try {
				return new Cell(getOperation(data.substring(1).toUpperCase().replaceAll("\\s", "")), references);
			} catch (IllegalArgumentException e) {
				return new Cell("", "#InvalidCellContent");
			}
		} 
		
		else {
			try {
				if (Integer.parseInt(data) >= 0) {
					return new Cell(getOperation(data), new HashSet<>());
				}
			} catch(NumberFormatException e) {
				return new Cell("", "#InvalidCellContent");
			}
		}
		return new Cell("", "#InvalidCellContent");
	}	
	
	/**
	 * Create operation by input data in a some kind of a tree.
	 * Example: for input data 5+C1/10-B1 create a tree: <br>
	 *  
	 * &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp - <br>
	 * &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp / &nbsp \<br>
	 * &nbsp &nbsp &nbsp &nbsp &nbsp &divide &nbsp B1<br>
	 * &nbsp &nbsp &nbsp &nbsp / &nbsp \ <br>
	 * &nbsp &nbsp &nbsp + &nbsp 10<br>
	 * &nbsp &nbsp / &nbsp \ <br>
	 * &nbsp 5 &nbsp C1 <br>
	 * </div>
	 * 
	 * @param data - expression
	 * @return operation as a tree
	 * @throws IllegalArgumentException if could not create operation structure by input data
	 */
	/*Example: for input data 5+C1/10-B1 create a tree:
	 *       - 
     *      / \
     *     ÷  B1
     *    / \ 
     *   +  10
     *  / \ 
     * 5  C1
	 * 
	 * */
	private Operation getOperation(String data) throws IllegalArgumentException {
		String[] terms = data.split("[-+*/]", -1);
		if (terms.length == 1) {
			return new Operation('=', getNode(data), null);
		}
		
		int operatorIndex = -1;
		Operation result = new Operation('=', getNode(terms[0]), null);
		for (int i = 0; i < terms.length - 1; i++) {
			Node rightOperand = getNode(terms[i + 1]);
			operatorIndex += terms[i].length();
			operatorIndex++;
			char operation = data.charAt(operatorIndex);
			result = new Operation(operation, result, rightOperand);
		}
		return result;
	}
	
	private Node getNode(String data) throws IllegalArgumentException {
		if (table.containsCell(data)) {
			return table.getCell(data);
		} else {
			try {
				return new Node(Integer.parseInt(data));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}
	}
}
