package ju.michael.app;

public class Pair{
	/* A LinkedList class used for syntactic analysis. */
	public Object head;
	public Pair tail;

	public Pair(Object h, Pair t) {
		head = h;
		tail = t;
	}

	public Pair() {
		this(null, null);
	}

	public String toString() {
		Pair runner = this;
		String ret = "(";
		while (runner != null) {
			ret += "Pair:" + runner.head.toString() + ", ";
			runner = runner.tail;
		}
		return ret + "null)";
	}

	public int length() {
		if (tail == null) {
			return 1;
		}
		return 1 + tail.length();
	}
}