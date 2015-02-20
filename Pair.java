public class Pair{
	public Object head;
	public Pair tail;

	public Pair(Object h, Pair t) {
		head = h;
		tail = t;
	}

	public Pair() {
		this(null, null);
	}
}