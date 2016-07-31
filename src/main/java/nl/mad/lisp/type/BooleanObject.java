package nl.mad.lisp.type;

public class BooleanObject extends SelfishObject {
	public static SelfishObject BOOLEAN_CLASS = new SelfishObject(SelfishObject.OBJECT_ROOT, "boolean_class");

	public BooleanObject(Boolean value) {
		super(BOOLEAN_CLASS, value);
	}
}
