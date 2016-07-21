package nl.mad.lisp;

import java.io.PrintStream;

public class LispExecutor {
	private PrintStream out;
	
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
				result = eval((Node) name.value, name.value.data.type == Data.Type.LIST ? new Namespace(name.namespace) : namespace);
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

		// we hebben te maken met scope haakjes
		Namespace sub = new Namespace(namespace);
		while (node != null) {
			result = eval(node, sub);

			node = node.next;
		}
		
		return result;
	}
	
	private Data executeLine(Node line, Namespace namespace) {
		Data result = null;
		String command = (String) line.getValue();

		if ("define".equals(command))
			define(line, namespace);
		else if ("println".equals(command))
			println(line, namespace);
		else if ("+".equals(command))
			result = add(line, namespace);
		else
			result = call(line, namespace);
		
		return result;
	}

	//
	// all primitives from here
	//
	
	// (define fnark (arg1 arg2) ( ... ) ) of (define fnark "hoi")
	private void define(Node line, Namespace namespace) {
		Node current = line.next;
		String name = (String) current.getValue();
		current = current.next;
		Node value = current;

		Node args = null;
		if (current.next != null) {
			args = (Node) current.getValue();
			current = current.next;
			value = current;
		}

		Name n = namespace.lookupLocal(name);
		if (n == null) {
			namespace.add(new Name(name, args, value));
		} else {
			// override; this is where you should GC!
			n.args = args;
			n.value = value;
		}
	}

	// ( print "a" "b" "c" )
	private void println(Node line, Namespace namespace) {

		Node current = line.next;

		while (current != null) {
			out.println(eval(current, namespace).value);
			current = current.next;
		}
	}

	// implemented only for int (and String, through concat)
	private Data add(Node line, Namespace namespace) {
		int value = 0;

		Node current = line.next;

		while (current != null) {
			Data data = eval(current, namespace);
			if (data.type == Data.Type.STRING) return concat(line, namespace);

			value += (Integer) data.value;
			current = current.next;
		}

		return new Data(Data.Type.INTEGER, value);
	}

	private Data concat(Node line, Namespace namespace) {
		StringBuffer buffer = new StringBuffer();

		Node current = line.next;

		while (current != null) {
			buffer.append(eval(current, namespace).value);
			current = current.next;
		}
	
		return new Data(Data.Type.STRING, buffer.toString());
	}

	// ( methodname arg1 arg2 ) => (method (arg1 arg2) ( ... ) )
	private Data call(Node line, Namespace namespace) {
		Name name = namespace.lookup((String)line.getValue());
		
		if (name == null) throw new RuntimeException("Name not defined in scope: " + line.getValue());

		Namespace subspace = new Namespace(namespace);

		Node param = (Node) line.next;

		Node arg = name.args;

		while (param != null) {
			subspace.add(new Name((String) arg.getValue(), null, param));
			arg = arg.next;
			param = param.next;
		}

		return evalList((Node) name.value, subspace);
	}
}
