import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class calc {
	private static final String[] KEYWORDS = {"add", "sub", "mult", "div", "let"};

	public static void main(String[] args) {
		try {
			ArrayList<String> parsed = parse(args[0]);
			// Pair command = makePair(parsed);
			// for (int i = 0; i < parsed.size(); i++) {
			// 	System.out.print(parsed.get(i) + " ");
			// }

		} catch (IndexOutOfBoundsException e) {
			System.out.println("Please enter a command!");
			System.exit(0);
		}

	}

	private static Pair analyze(ArrayList<String> parsed) {
		
	}

	private static ArrayList<String> parse(String input) {
		/* Parses user input into separate tokens in a String ArrayList. 
		Assumes that whitespace does not matter. Ensures that all symbols are valid.*/
		ArrayList<String> tokens = new ArrayList<String>();
		int count = 0;
		// boolean subExpression = false;
		char target;
		// char next;
		String keyword;
		try {
			while (count < input.length()) {
				print(tokens);
				keyword = keywordNext(input.substring(count));
				if (keyword != null) {
					// next = input.charAt(count + 1);
					// if (next != '(') {
					// 	throw new Exception();
					// }
					tokens.add(keyword);
					// subExpression = true;
					count += keyword.length() - 1;
				}
				else {
					target = input.charAt(count);
					if (target != ' ') {
						if (!isProperSymbol(target)) {
							throw new Exception("Invalid symbol");
						}
						// if (isCharacter(target)) {

						// 	target = (int)target;
						// 	next = input.charAt(count + 1);
						// 	if (subExpression) {
						// 		next = input.charAt(count + 1);
						// 		if (next != ',' && next != ')') {
						// 			throw new Exception();
						// 		}
						// 	}
						// }
						tokens.add(String.valueOf(target));
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

	private static String keywordNext(String input) {
		String ret = null;
		try {
			for (String s : KEYWORDS) {
				if (s.equals(input.substring(0, 3)) || s.equals(input.substring(0, 4))) {
					ret = s;
				}
			}
		}
		catch (Exception e) {
		}
		return ret;
	}

	private static boolean isProperSymbol(char a) {
		return Character.isLetterOrDigit(a) || a == '(' || a == ')' || a == ',';
	}

	//print is for debugging purposes
	private static void print(ArrayList<String> tokens) {
		for (int i = 0; i < tokens.size(); i++) {
			System.out.print(tokens.get(i) + " ");
		}
		System.out.println();
	}

}