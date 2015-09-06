package ju.michael.app;

public abstract class Operator {
	/* Class from which all operators inherit. */
	protected int numArgs = 2;

	public int getNumArgs() {
		return numArgs;
	}

	public abstract Integer apply(Integer[] args);
		/* The method that all operators will override to implement their own application
		to arguments. Assumes that args.length == numArgs (taken care of in calc.java's READTAIL method). */
}
