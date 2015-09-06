package ju.michael.app;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class calc {

	/* Hash map containing all the calculator's operators as keys and each operator's class as values. */
	private static final HashMap<String, Operator> OPERATORS;
	static {
		OPERATORS = new HashMap<String, Operator>();
		OPERATORS.put("add", new Add());
		OPERATORS.put("sub", new Sub());
		OPERATORS.put("mult", new Mult());
		OPERATORS.put("div", new Div());
		OPERATORS.put("let", new Let());
	}

	/* Hash map containing all bindings fixed during the current calculation. */
	public static final HashMap<String, Integer> BINDINGS = new HashMap<String, Integer>();

	public static void main(String[] args) {
		try {
			ArrayList<String> parsed = parse(args[0]);
			Object analyzed = read(parsed, true);
			System.out.println(eval(analyzed));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Please enter a command!");
			System.exit(0);
		}

	}

	public static Integer eval(Object exp) {
		/* Evaluates the Object returned by READ. If Object is an Integer, returns it.
		If it is a variable, looks it up in BINDINGS. If it is an expression represented
		by a Pair instance, evaluates each operand and applies the expression's operator
		to the operands UNLESS operator is a Let. In this special case, first creates a 
		new binding between the first and second argument. */
		try {
			if (exp instanceof Integer) {
				return (Integer)exp;
			}
			if (exp instanceof String) {
				if (BINDINGS.containsKey((String)exp)) {
					return BINDINGS.get((String)exp);
				}
				throw new Exception("Invalid symbol");
			}
			if (exp instanceof Pair) {
				Pair temp = (Pair)exp;
				Operator operator = OPERATORS.get(temp.head);
				temp = temp.tail;
				//specail evaluation process for let
				if (operator instanceof Let) {
					((Let)operator).assign(temp);
					temp = temp.tail.tail;
					return eval(temp.head);
				}
				Integer[] operands = new Integer[operator.getNumArgs()];
				for (int i = 0; i < operands.length; i++) {
					operands[i] = eval(temp.head);
					temp = temp.tail;
				}
				return operator.apply(operands);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return null;
	}

	public static Object read(ArrayList<String> parsed, boolean isOuter) {
		/* Converts a parsed ArrayList into an Object that can be evaluated. If the input is
		only an integer or a character, returns it. If it is an expression, returns a Pair instance
		by calling READTAIL. Also keeps track of whether you are reading the outermost expression. */
		try {
			String curr = parsed.remove(0);
			char test = curr.charAt(0);
			if (OPERATORS.containsKey(curr)) {
				if (!"(".equals(parsed.remove(0))) {
					throw new Exception("( expected");
				}
				return new Pair(curr, readTail(parsed, curr, isOuter));
			}
			if (isOuter && !parsed.isEmpty()) {
				throw new Exception("unexpected additional characters");
			}
			if (Character.isDigit(test) || test == '-') {
				return new Integer(curr);
			}
			if (Character.isLetter(test)) {
				return curr;
			}
			throw new Exception();
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null) {
				System.out.println("Invalid expression");
			} else {
				System.out.println(message);
			}
			System.exit(0);
		}
		return null;
	}

	private static Pair readTail(ArrayList<String> rest, String key, boolean isOuter) {
		/* Recursively calls READ on all of operator KEY's arguments and makes sure
		that a comma follows every argument and an end parenthesis exists after the last
		argument. If READTAIL is called on the outermost expression, checks that nothing follows
		the last parenthesis. Returns the resulting Pair. */
		Pair ret = null;
		try {
			int numArgs = OPERATORS.get(key).getNumArgs();
			if (numArgs == 0) {
				return null;
			}
			ret = new Pair(read(rest, false), null);
			Pair runner = ret;
			while (numArgs > 1) {
				if (!",".equals(rest.remove(0))) {
					throw new Exception(", expected after argument");
				}
				runner.tail = new Pair(read(rest, false), null);
				runner = runner.tail;
				numArgs -= 1;
			}
			if (!")".equals(rest.remove(0))) {
				throw new Exception(") expected at end of expression");
			}
			if (isOuter && !rest.isEmpty()) {
				throw new Exception("unexpected additional characters");
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null) {
				System.out.println("Invalid expression");
			} else {
				System.out.println(message);
			}
			System.exit(0);
		}
		return ret;
	}

	public static ArrayList<String> parse(String input) {
		/* Parses user input into separate tokens in a String ArrayList. 
		Assumes that whitespace does not matter. Ensures that all symbols are valid.*/
		ArrayList<String> tokens = new ArrayList<String>();
		int count = 0;
		char target;
		String keyword;
		String number;
		try {
			while (count < input.length()) {
				keyword = operatorNext(input.substring(count));
				if (keyword != null) {
					tokens.add(keyword);
					count += keyword.length() - 1;
				}
				else {
					target = input.charAt(count);
					if (target != ' ') {
						if (!isProperSymbol(target)) {
							throw new Exception("Invalid symbol");
						}
						if (target == '-') {
							number = makeStringNumber(input.substring(count+1));
							if (number.length() == 0) {
								throw new Exception("Invalid symbol");
							}
							tokens.add(target + number);
							count += number.length();
						} else if (Character.isDigit(target)) {
							number = makeStringNumber(input.substring(count));
							tokens.add(number);
							count += number.length() - 1;
						} else {
							tokens.add(String.valueOf(target));
						}
					}
				}
				count += 1;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return tokens;
	}

	private static String makeStringNumber(String input) {
		/* Deals with multi-character integers, like 24 or 123. Returns a String of the
		next multi-character integer in input starting from the beginning. */
		String ret = "";
		int count = 0;
		while (count < input.length() && Character.isDigit(input.charAt(count))) {
			ret += input.charAt(count);
			count += 1;
		}
		return ret;
	}

	private static String operatorNext(String input) {
		/* Checks whether input has an operator at the front. If so, returns it. Otherwise
		returns null. */
		String ret = null;
		for (Map.Entry<String, Operator> s : OPERATORS.entrySet()) {
			if (input.indexOf(s.getKey()) == 0) {
				ret = s.getKey();
				break;
			}
		}
		return ret;
	}

	private static boolean isProperSymbol(char a) {
		/* Checks that a character is a valid symbol for the calculator. */
		return Character.isLetterOrDigit(a) || a == '(' || a == ')' || a == ',' || a == '-';
	}

	//print is for debugging purposes
	private static void print(ArrayList<String> tokens) {
		for (int i = 0; i < tokens.size(); i++) {
			System.out.print(tokens.get(i) + " ");
		}
		System.out.println();
	}

}