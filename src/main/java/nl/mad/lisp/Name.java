package nl.mad.lisp;

public class Name {
	public String name;
	public Node args;
	public Object value;

	public Name (String name, Node args, Object value) {
		this.name = name;
		this.args = args;
		this.value = value;
	}
}
