public class Let extends Operator {
	/* The Let operator. */ 

	public Let() {
		numArgs = 3;
	}

	public Integer apply(Object[] args) {
		/* If the first and second arguments are valid types, adds a new binding between
		them. */
		try {
			if (!(args[0] instanceof String)) {
				throw new Exception("Invalid variable in let expression");
			}
			if (!(args[1] instanceof Integer)) {
				throw new Exception("Invalid value assigned to variable");
			}
			calc.BINDINGS.put((String)args[0], (Integer)args[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return null;
	}

}