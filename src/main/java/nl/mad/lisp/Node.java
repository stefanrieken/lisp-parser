package nl.mad.lisp;

public class Node {
	public Object value;
	public Node next;
	
	public Node (Object value) {
		this.value = value;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(value);
		
		Node current = next;
		while (current != null) {
			buffer.append(" ");
			buffer.append(current.value);
			current = current.next;
		}
		
		buffer.append(")");
		return buffer.toString();
	}
}
