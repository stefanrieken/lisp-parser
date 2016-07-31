package nl.mad.lisp.type;

import nl.mad.lisp.Node;

public class ListObject extends SelfishObject {
	public static SelfishObject LIST_CLASS = new SelfishObject(SelfishObject.OBJECT_ROOT, "list_class");

	public ListObject(Node value) {
		super(LIST_CLASS, value);
	}
}
