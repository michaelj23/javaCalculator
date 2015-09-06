package ju.michael.app;

public class Div extends Operator {
	/* The division operator. */

	public Integer apply(Integer[] args) {
		try {
			if (args[1] == 0) {
				throw new ArithmeticException();
			}
		} catch (ArithmeticException e) {
			System.out.println("Can't divide by 0");
			System.exit(0);
		}
		return args[0] / args[1];
	}
}