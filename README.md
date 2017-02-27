# Mini Excel

Spreadsheet with a command-line interface. The spreadsheet is to be able to process cells that conatin either primitive values or expressions.
Each cell may contain:

 * Nothing
 * A positive integer
 * Strings that begin with symbol `
 * Expression lines, that begin with ‘=’ and incorporate positive integers, cell references, and simple arithmetic expressions. Parentheses are not allowed. All operations have an equal priority.

The program receives the description of a table from standard input. The first line contains a pair of tab-separated numbers, which are the height and width of the table correspondingly. Lines that follow contain the table cells.
