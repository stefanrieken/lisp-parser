package nl.mad.lisp.type;

public class AtomObject extends SelfishObject {
	public static SelfishObject ATOM_CLASS = new SelfishObject(SelfishObject.OBJECT_ROOT, "atom_class");

	public AtomObject(String value) {
		super(ATOM_CLASS, value);
	}
}
