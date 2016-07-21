package nl.mad.lisp;

public class LispExecutor {

	public void execute (Node root) {
		Namespace global = new Namespace(null);
		eval(root, global);
	}

	private Node eval (Node node, Namespace namespace) {
		Node result;

		switch (node.data.type) {
			case LITERAL:
				Name name = namespace.lookup((String) node.getValue());
				if (name == null)
					throw new RuntimeException("Name not defined in scope: " + node.getValue());
				result = eval((Node) name.value, name.value.data.type == Data.Type.LIST ? new Namespace(name.namespace) : namespace);
				break;
			case LIST:
				evalList(node, new Namespace(namespace));
				return null; // TODO no mechanism yet for function results
			default:
				result = node;
				break;
		}
		
		return result;
	}

	private void evalList (Node node, Namespace namespace) {
		while (node != null) {
			Node line = (Node) node.getValue();
			
			switch(line.data.type) {
				case LITERAL:
					executeLine(line, namespace);
					break;
				case LIST:
					evalList(line, new Namespace(namespace));
					break;
			}

			node = node.next;
		}
	}
	
	private void executeLine(Node line, Namespace namespace) {
		String command = (String) line.getValue();
		
		if ("define".equals(command))
			define(line, namespace);
		else if ("print".equals(command))
			print(line, namespace);
		else
			call(line, namespace);
	}

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

		Name n = namespace.lookup(name);
		if (n == null) {
			namespace.add(new Name(name, args, value));
		} else {
			// override; this is where you should GC!
			n.args = args;
			n.value = value;
		}
	}

	// ( print "a" "b" "c" )
	private void print(Node line, Namespace namespace) {

		Node current = line.next;

		while (current != null) {
			System.out.print(eval(current, namespace).getValue());
			current = current.next;
		}

		System.out.println();
	}


	// ( methodname arg1 arg2 ) => (method (arg1 arg2) ( ... ) )
	private void call(Node line, Namespace namespace) {
		Name name = namespace.lookup((String)line.getValue());

		Namespace subspace = new Namespace(namespace);

		Node param = line.next;

		Node arg = name.args;

		while (param != null) {
			subspace.add(new Name((String) arg.getValue(), null, param));
			arg = arg.next;
			param = param.next;
		}

		evalList((Node) name.value, subspace);
	}
}
