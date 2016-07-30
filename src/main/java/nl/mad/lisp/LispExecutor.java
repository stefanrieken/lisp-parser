package nl.mad.lisp;

import java.io.PrintStream;

import nl.mad.lisp.primitives.HelloPrimitives;
import nl.mad.lisp.primitives.OtherPrimitives;
import nl.mad.lisp.primitives.Primitives;

public class LispExecutor {
	private Primitives helloPrimitives = new HelloPrimitives(this);
	private Primitives otherPrimitives = new OtherPrimitives(this);

	public PrintStream out;

	public LispExecutor(PrintStream out) {
		this.out = out;
	}

	public Data eval (Node node, Namespace namespace) {
		Data result;

		switch (node.data.type) {
			case LITERAL:
				Name name = namespace.lookup((String) node.getValue());
				if (name == null)
					throw new RuntimeException("Name not defined in scope: " + node.getValue());
				result = eval((Node) name.value, namespace);
				break;
			case LIST:
				result = evalList((Node) node.getValue(), namespace);
				break;
			default:
				result = node.data;
				break;
		}
		
		return result;
	}

	public Data evalList (Node node, Namespace namespace) {
		Data result = null;

		if (node.data.type == Data.Type.LITERAL)
			return executeLine(node, namespace);

		// these are purely-scope brackets
		Namespace sub = new Namespace(namespace);
		while (node != null) {
			result = eval(node, sub);

			node = node.next;
		}
		
		return result;
	}

	private Data executeLine(Node line, Namespace namespace) {
		Data result = null;

		// trying primitives first
		result = helloPrimitives.executeLine(line, namespace);
		if (result == null) result = otherPrimitives.executeLine(line, namespace);

		// well, then it's a user-defined function
		if (result == null) result = call(line, namespace);
		
		return result;
	}

	// ( methodname arg1 arg2 ) => (method (arg1 arg2) ( ... ) )
	private Data call(Node line, Namespace namespace) {
		Name name = namespace.lookup((String)line.getValue());
		
		if (name == null) throw new RuntimeException("Name not defined in scope: " + line.getValue());

		Namespace subspace = new Namespace(namespace);

		Node param = (Node) line.next;

		Node arg = name.args;

		while (param != null) {
			subspace.add(new Name((String) arg.getValue(), null, new Node(eval(param, namespace))));
			arg = arg.next;
			param = param.next;
		}

		return evalList((Node) name.value, subspace);
	}
}
