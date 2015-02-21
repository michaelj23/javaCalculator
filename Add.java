public class Add extends Operator {
	/* The Add operator. */

	public Integer apply(Object[] args) {
		super.apply(args);
		return (Integer)args[0] + (Integer)args[1];
	}
}