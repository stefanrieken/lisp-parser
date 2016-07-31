package nl.mad.lisp;

import nl.mad.lisp.type.NilObject;
import nl.mad.lisp.type.SelfishObject;

public class Node {
	public SelfishObject data;
	public Node next;
	
	public Node (SelfishObject data) {
		this(data, null);
	}

	public Node (SelfishObject data, Node next) {
		if (data == null)
			data = NilObject.NO_DATA; // feature seems to be unused?
		this.data = data;
		this.next = next;
	}

	public Object getValue() {
		return data.value;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(data.value);
		
		Node current = next;
		while (current != null) {
			buffer.append(" ");
			buffer.append(current.getValue());
			current = current.next;
		}
		
		buffer.append(")");
		return buffer.toString();
	}
}
