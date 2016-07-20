package nl.mad.lisp;

public class Literal {
	String value;

	public Literal(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "\\" + value;
	}
}
