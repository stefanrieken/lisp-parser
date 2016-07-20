package nl.mad.lisp;

public class Token {
	public enum Type {
		STRING,
		LIST_OPEN,
		LIST_CLOSE,
		COMMENT,
		INTEGER,
		FLOAT,
		LITERAL
	}

	private Type type;
	public Object data;

	public Token (Type type, Object data) {
		System.err.println(type + " " + (data == null ? "" : data));
		this.type = type;
		this.data = data;
	}
	
	public Token (Type type) {
		this(type, null);
	}

}
