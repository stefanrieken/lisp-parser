package nl.mad.lisp.type;

import nl.mad.lisp.LispExecutor;
import nl.mad.lisp.Node;

public class MethodObject extends SelfishObject {
	public static SelfishObject METHOD_CLASS = new SelfishObject(SelfishObject.OBJECT_ROOT, "method_class");

	// value = ((args) (body) ...)
	// value zou mogelijk ook weer een ListObject kunnen zijn, maar voor nu gewoon direct de list.
	public MethodObject(Node value) {
		super(METHOD_CLASS, value);
	}

	private Node getArgs() {
		return (Node) ((SelfishObject) ((Node) value).data).value;
	}

	private Node getCode() {
		return ((Node) value).next;
	}

	// ( [obj.]methodname arg1 arg2 ) => (method (arg1 arg2) ( ... ) )
	@Override
	public SelfishObject eval(Node line, SelfishObject namespace, LispExecutor e) {
		SelfishObject subspace = new SelfishObject(namespace, "method_scope");

		Node param = (Node) line.next;
		Node arg = getArgs();

		while (param != null) {
			subspace.assocs.put((String) arg.getValue(), e.eval(param, namespace));
			arg = arg.next;
			param = param.next;
		}

		return e.evalList(getCode(), subspace);
	}

}
