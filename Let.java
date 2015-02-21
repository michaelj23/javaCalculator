public class Let extends Operator {
	/* The Let operator. */ 

	public Let() {
		numArgs = 3;
	}

	public void assign(Pair exp) {
		/* Creates a new binding between the first and second item in exp.*/
		try {
			if (!(exp.head instanceof String)) {
				throw new Exception("Invalid variable in let expression");
			}
			calc.BINDINGS.put((String)exp.head, calc.eval(exp.tail.head));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public Integer apply(Integer[] args) {
		/* Not used in Let since Let itself does not actually do any application. */
		return null;
	}

}