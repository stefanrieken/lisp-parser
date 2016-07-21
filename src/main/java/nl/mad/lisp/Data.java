package nl.mad.lisp;

public class Data {
	public enum Type {
		NULL,
		LIST,
		LITERAL,
		STRING,
		INTEGER,
		FLOAT
	};

	Type type;
	//int refcount; GC!
	Object value;
	
	public Data(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
}
