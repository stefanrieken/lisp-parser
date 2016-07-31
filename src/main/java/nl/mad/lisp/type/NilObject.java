package nl.mad.lisp.type;

public class NilObject extends SelfishObject {
	public static NilObject NO_DATA = new NilObject();
	
	public NilObject() {
		super(SelfishObject.OBJECT_ROOT, "nil");
	}
}
