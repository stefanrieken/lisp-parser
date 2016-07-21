package nl.mad.lisp;

public class Node {
	public Data data;
	public Node next;
	
	public Node (Data data) {
		if (data == null)
			data = new Data(Data.Type.NULL, null);
		this.data = data;
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
