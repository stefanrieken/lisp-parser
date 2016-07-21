package nl.mad.lisp;

public class Name {
	public String name;
	public Node args;
	public Node value;
	public Namespace namespace;

	public Name (String name, Node args, Node value) {
		this.name = name;
		this.args = args;
		this.value = value;
	}
	
	public Object directValue() {
		return value.data.value;
	}
	
	public String toString() {
		return "[" + name + ":" + value + "]";
	}
}
