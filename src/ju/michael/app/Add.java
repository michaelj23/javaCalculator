package ju.michael.app;

public class Add extends Operator {
	/* The Add operator. */

	public Integer apply(Integer[] args) {
		return args[0] + args[1];
	}
}