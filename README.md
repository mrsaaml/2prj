Project Report: Java Calculator Program

Project Overview:
The project involves the development of a command-line arithmetic calculator that allows users to input arithmetic expressions and get results. It supports basic arithmetic operations such as addition, subtraction, multiplication, division, and modulus. Additionally, advanced functions like square root, absolute value, rounding, and exponentiation are included. The program also tracks the history of calculations, allowing users to view previous results.

Design Choices:

•	User Interface: A simple command-line interface is used for interaction. The user is prompted to enter an expression or a command such as "history" to view past calculations.
•	History Feature: The calculator maintains a history of calculations, allowing users to access previous results.
•	Input Validation: The program handles invalid expressions gracefully, displaying error messages and prompting the user to re-enter a valid expression. The code also ensures that division by zero does not occur.
•	Function Parsing: An ExpressionParser class is used to parse and evaluate the arithmetic expressions. This class employs a recursive descent parser approach for parsing expressions, terms, and factors.

Code Breakdown:

•	Array List: We used an ArrayList(history) that stores past calculations. This allows the user to view previous results.
•	Infinite Loop: The program enters a loop, constantly asking for user input until the user chooses to exit. 
•	 Input Prompt: The user is prompted to enter an arithmetic expression or a command (history to view past calculations).
•	Try and catch blocks: 
o	The try block contains the code that might throw an exception. 
o	 If an exception occurs inside the try block, the program jumps to the catch block, which handles the error. 
o	If no exception occurs, the catch block is skipped, and execution continues normally.
•	showHistory Method: The showHistory() method checks if the history list is empty. If not, it iterates over the history and prints each record.
•	evaluateExpression Method: 
1.	Takes a mathematical expression as a String input. 
2.	Creates a new instance of ExpressionParser. 
3.	Calls the parse(expression) method to evaluate the expression. 
4.	Returns the result as a double.
•	ExpressionParser Class: This class is responsible for parsing and evaluating the arithmetic expressions entered by the user.
o	Variables: pos is the current position in the input string, and ch holds the current character. input stores the arithmetic expression as a string.
o	parse Method: This method starts the parsing process. It sets the input string, initializes pos to -1, and calls nextChar() to move the position pointer to the first character. It then calls parseExpression() to evaluate the entire expression. If there are leftover characters, an error is thrown.
o	nextChar Method: This method updates the ch variable to the next character in the input string. If there are no more characters left, it sets ch to -1, which represents the end of the input.
o	eat Method: This method skips over spaces and checks if the current character matches charToEat. If it does, it moves the position to the next character and returns true; otherwise, it returns false.
•	parseExpression Method: This method processes addition and subtraction. It first calls parseTerm() to evaluate the first term. Then, it checks for + or - operators and recursively calls parseTerm() to process subsequent terms.
•	parseTerm Method: This method processes multiplication, division, and modulus. It calls parseFactor() to handle factors, and then checks for the operators *, /, and %. It ensures that division by zero is prevented.
•	parseFactor Method: This method handles parentheses, numbers, and functions (e.g., sqrt, abs, round, power). It recursively calls parseExpression() for expressions inside parentheses and processes numbers or function names.
