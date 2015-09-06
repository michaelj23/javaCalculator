package ju.michael.app;

public class Mult extends Operator {
	/* The multiplication operator. */

	public Integer apply(Integer[] args) {
		return args[0] * args[1];
	}
}