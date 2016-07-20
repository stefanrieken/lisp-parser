package nl.mad.lisp;

public class LispExecutor {

	public void execute (Node root) {
		Namespace global = new Namespace(null);
		execute(root, global);
	}

	public void execute (Node node, Namespace namespace) {
		while (node != null) {
			Node line = (Node) node.value;
			String command = ((Literal) line.value).value;
			
			if ("define".equals(command))
				define(line, namespace);
			else if ("print".equals(command))
				print(line, namespace);
			else
				call(line, namespace);
			
			node = node.next;
		}
	}

	// (define fnark (arg1 arg2) ( ... ) ) of (define fnark "hoi")
	private void define(Node line, Namespace namespace) {
		Node current = line.next;
		String name = ((Literal) current.value).value;
		current = current.next;
		Object value = current.value;

		Node args = null;
		if (current.next != null) {
			args = (Node) current.value;
			current = current.next;
			value = current;
		}

		namespace.add(new Name(name, args, value));
	}

	// ( print "a" "b" "c" )
	private void print(Node line, Namespace namespace) {

		Node current = line.next;

		while (current != null) {
			if (current.value instanceof Literal)
				System.out.print(namespace.lookup(((Literal) current.value).value).value);
			else
				System.out.print(current.value);
			current = current.next;
		}

		System.out.println();
	}

	// ( methodname arg1 arg2 ) => (method (arg1 arg2) ( ... ) )
	private void call(Node line, Namespace namespace) {
		Name name = namespace.lookup(((Literal) line.value).value);

		Namespace subspace = new Namespace(namespace);

		Node param = line.next;

		Node arg = name.args;

		while (param != null) {
			subspace.add(new Name(((Literal) arg.value).value, null, param.value));
			arg = arg.next;
			param = param.next;
		}

		execute((Node) name.value, subspace);
	}
}
