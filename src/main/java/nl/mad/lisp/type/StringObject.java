package nl.mad.lisp.type;

import nl.mad.lisp.LispExecutor;
import nl.mad.lisp.Node;

public class StringObject extends SelfishObject {
	public static SelfishObject STRING_CLASS = new SelfishObject(SelfishObject.OBJECT_ROOT, "string_class");

	public StringObject(String value) {
		super(STRING_CLASS, value);
	}

	public SelfishObject execute(Node line, SelfishObject namespace, LispExecutor e) {
		SelfishObject result = null;
		String message = (String) line.getValue();

		if ("add".equals(message)) {
			result = add_oo(line, namespace, e);
		} else {
			result = parent.execute(line, namespace, e);
		}

		return result;
	}

	private SelfishObject add_oo(Node line, SelfishObject obj, LispExecutor e) {
		String value = (String) obj.value;

		Node current = line.next;

		while (current != null) {
			SelfishObject data = e.eval(current, obj);
			value += data.value;
			current = current.next;
		}

		return new StringObject(value);
	}

}
