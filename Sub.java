public class Sub extends Operator {
	/* The subtraction operator. */

	public Integer apply(Object[] args) {
		super.apply(args);
		return (Integer)args[0] - (Integer)args[1];
	}
}