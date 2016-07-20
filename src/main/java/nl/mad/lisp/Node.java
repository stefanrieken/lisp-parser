package nl.mad.lisp;

public class Node {
	public Object value;
	public Node next;
	
	public Node (Object value) {
		System.err.print(value + " ");
		this.value = value;
	}
}
