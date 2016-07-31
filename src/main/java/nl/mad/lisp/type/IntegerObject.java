package nl.mad.lisp.type;

import nl.mad.lisp.LispExecutor;
import nl.mad.lisp.Node;

public class IntegerObject extends SelfishObject {
	public static SelfishObject INTEGER_CLASS = new SelfishObject(SelfishObject.OBJECT_ROOT, "integer_class");

	public IntegerObject(Integer value) {
		super(INTEGER_CLASS, value);
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
		int value = (Integer) obj.value;

		Node current = line.next;

		while (current != null) {
			SelfishObject data = e.eval(current, obj);
			value += (Integer) data.value;
			current = current.next;
		}

		return new IntegerObject(value);
	}

}
