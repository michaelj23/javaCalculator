public class Div extends Operator {
	/* The division operator. */

	public Integer apply(Object[] args) {
		super.apply(args);
		try {
			if ((Integer)args[1] == 0) {
				throw new ArithmeticException();
			}
		} catch (ArithmeticException e) {
			System.out.println("Can't divide by 0");
		}
		return (Integer)args[0] / (Integer)args[1];
	}
}