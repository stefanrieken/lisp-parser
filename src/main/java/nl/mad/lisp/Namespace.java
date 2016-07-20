package nl.mad.lisp;

public class Namespace {
	public Namespace parent;
	public Node names;
	private Node last;

	public Namespace(Namespace parent) {
		this.parent = parent;
	}

	public void add (Name name) {
		Node node = new Node(name);

		if (last == null)
			names = node;
		else
			last.next = node;

		last = node;
	}

	public Name lookup (String name) {
		Node current = names;

		while (current != null) {
			Name n = (Name) current.value;
			if (name.equals(n.name)) return n;
			current = current.next;
		}

		if (parent != null) return parent.lookup(name);

		return null;
	}
}
