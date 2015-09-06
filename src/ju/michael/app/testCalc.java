package ju.michael.app;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class testCalc {

	@Test
	public void testParse() {
		ArrayList<String> parsed = calc.parse("add(-2, sub(3, 40))");
		assertEquals(new String[]{"add", "(", "-2", ",", "sub", "(", "3", ",", "40", ")", ")"}, parsed.toArray());
	}

	@Test
	public void testRead() {
		ArrayList<String> parsedOne = calc.parse("-4");
		ArrayList<String> parsedTwo = calc.parse("let(a, 50, add(a, a))");
		assertEquals(-4, calc.read(parsedOne, true));
		assertEquals("(Pair:let, Pair:a, Pair:50, Pair:(Pair:add, Pair:a, Pair:a, null), null)", 
					calc.read(parsedTwo, true).toString());
	}

	@Test
	public void testEvalNotLet() {
		Integer evalOne = calc.eval(calc.read(calc.parse("-420000"), true));
		Integer evalTwo = calc.eval(calc.read(calc.parse("mult(add(20, sub(-10, 4)),   div(9, 3))"), true));
		assertEquals(new Integer(-420000), evalOne);
		assertEquals(new Integer(18), evalTwo);
	}

	@Test
	public void testEvalLet() {
		Integer evalOne = calc.eval(calc.read(calc.parse("let(a, 5, let(b, mult(a, 10), add(b, a)))"), true));
		Integer evalTwo = calc.eval(calc.read(calc.parse("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))"), true));
		assertEquals(new Integer(55), evalOne);
		assertEquals(new Integer(40), evalTwo);
	}


	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main("ju.michael.app.testCalc");
	}
}