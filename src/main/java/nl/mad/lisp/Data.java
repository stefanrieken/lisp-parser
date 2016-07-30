package nl.mad.lisp;

public class Data {
	public enum Type {
		NULL,
		LIST,
		LITERAL,
		STRING,
		INTEGER,
		FLOAT,
		BOOLEAN
	};

	public Type type;
	//int refcount; GC!
	public Object value;
	
	public Data(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
}
