package nl.mad.lisp;

public class Namespace {
	public Namespace parent;
	public Node names;
	private Node last;

	public Namespace(Namespace parent) {
		this.parent = parent;
	}

	public void add(Name name) {
		name.namespace = this;
		Node node = new Node(new Data(Data.Type.NULL, name)); // slightly mis-using nodes here? Discuss.

		if (last == null)
			names = node;
		else
			last.next = node;

		last = node;
	}

	public Name lookup (String name) {
		Name n = lookupLocal(name);
		if (n == null && parent != null) return parent.lookup(name);

		return n;
	}

	public Name lookupLocal (String name) {
		Node current = names;

		while (current != null) {
			Name n = (Name) current.getValue();
			if (name.equals(n.name)) return n;
			current = current.next;
		}

		return null;
	}
}
