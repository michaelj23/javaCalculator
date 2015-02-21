public abstract class Operator {
	/* Class from which all operators inherit. */
	protected int numArgs = 2;

	public int getNumArgs() {
		return numArgs;
	}

	public Integer apply(Object[] args) {
		/* The method that all operators will override to implement their own application
		to arguments. Assumes that args.length == numArgs (taken care of in calc.java's READTAIL method).
		Replaces any valid variable names with their Integer values. */
		Object target;
		try {
			for (int i = 0; i < args.length; i++) {
				target = args[i];
				if (!(target instanceof Integer)) {
					if (target instanceof String && calc.BINDINGS.containsKey((String)target)) {
						args[i] = calc.BINDINGS.get((String)target);
					} else {
						throw new Exception();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid symbol");
			System.exit(0);
		}
		return null;
	}
	/* The method that all operators will override to implement their own application
	to arguments. Assumes that args.length == numArgs (taken care of in calc.java's READTAIL method. */

}
