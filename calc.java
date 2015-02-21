import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class calc {

	/* Hash map containing all the calculator's operators as keys and each operator's number of arguments as values. 
	If an operator's number of arguments is -1, it means that the operator does not have a limit on number of arguments. */
	private static final HashMap<String, Integer> OPERATORS;
	static {
		OPERATORS = new HashMap<String, Integer>();
		OPERATORS.put("add", 2);
		OPERATORS.put("sub", 2);
		OPERATORS.put("mult", 2);
		OPERATORS.put("div", 2);
		OPERATORS.put("let", -1);
	}

	public static void main(String[] args) {
		try {
			ArrayList<String> parsed = parse(args[0]);
			print(parsed);
			Object analyzed = read(parsed, true);
			System.out.println(analyzed);
			// Pair command = makePair(parsed);
			// for (int i = 0; i < parsed.size(); i++) {
			// 	System.out.print(parsed.get(i) + " ");
			// }

		} catch (IndexOutOfBoundsException e) {
			System.out.println("Please enter a command!");
			System.exit(0);
		}

	}

	private static Object read(ArrayList<String> parsed, boolean isOuter) {
		/* Converts a parsed ArrayList into an Object that can be evaluated. If the input is
		only an integer or a character, returns it. If it is an expression, returns a Pair instance
		by calling READTAIL. Also keeps track of whether you are reading the outermost expression. */
		try {
			String curr = parsed.remove(0);
			char test = curr.charAt(0);
			if (OPERATORS.containsKey(curr)) {
				if (!"(".equals(parsed.remove(0))) {
					throw new Exception();
				}
				return new Pair(curr, readTail(parsed, curr, isOuter));
			}
			if (Character.isDigit(test) || test == '-') {
				return new Integer(curr);
			}
			if (Character.isLetter(test)) {
				return curr;
			}
			throw new Exception();
		} catch (Exception e) {
			System.out.println("Invalid expression");
			System.exit(0);
		}
		return null;
	}

	private static Pair readTail(ArrayList<String> rest, String key, boolean isOuter) {
		/* Recursively calls READ on all of operator KEY's arguments and makes sure
		that a comma follows every argument and an end parenthesis exists after the last
		argument. If READTAIL is called on the outermost expression, checks that nothing follows
		the last parenthesis. Returns the resulting Pair. */
		try {
			int numArgs = OPERATORS.get(key);
			if (numArgs == 0) {
				return null;
			}
			Pair ret = new Pair(read(rest, false), null);
			Pair runner = ret;
			while (numArgs > 1 || (numArgs == -1 && !")".equals(rest.get(0)))) {
				if (!",".equals(rest.remove(0))) {
					throw new Exception();
				}
				runner.tail = new Pair(read(rest, false), null);
				runner = runner.tail;
				if (numArgs != -1) {
					numArgs -= 1;
				}
			}
			if (!")".equals(rest.remove(0))) {
				throw new Exception();
			}
			if (isOuter && rest.size() != 0) {
				throw new Exception();
			}
			return ret;
		} catch (Exception e) {
			System.out.println("Invalid expression");
			System.exit(0);
		}
		return null;
	}

	private static ArrayList<String> parse(String input) {
		/* Parses user input into separate tokens in a String ArrayList. 
		Assumes that whitespace does not matter. Ensures that all symbols are valid.*/
		ArrayList<String> tokens = new ArrayList<String>();
		int count = 0; //maybe take out count and just shorten input every time in the while loop
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
		try {
			for (Map.Entry<String, Integer> s : OPERATORS.entrySet()) {
				if (input.indexOf(s.getKey()) == 0) {
					ret = s.getKey();
					break;
				}
			}
		}
		catch (Exception e) {
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