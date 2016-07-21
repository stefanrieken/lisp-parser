package nl.mad.lisp;

public class LispExecutor {

	public void execute (Node root) {
		Namespace global = new Namespace(null);
		eval(root, global);
	}

	private Data eval (Node node, Namespace namespace) {
		Data result;

		switch (node.data.type) {
			case LITERAL:
				Name name = namespace.lookup((String) node.getValue());
				if (name == null)
					throw new RuntimeException("Name not defined in scope: " + node.getValue());
				result = eval((Node) name.value, name.value.data.type == Data.Type.LIST ? new Namespace(name.namespace) : namespace);
				break;
			case LIST:
				result = evalList(node, new Namespace(namespace));
				break;
			default:
				result = node.data;
				break;
		}
		
		return result;
	}

	private Data evalList (Node node, Namespace namespace) {
		Data result = null;

		while (node != null) {
			Node line = (Node) node.getValue();
			
			switch(line.data.type) {
				case LITERAL:
					result = executeLine(line, namespace);
					break;
				case LIST:
					result = evalList(line, new Namespace(namespace));
					break;
				default:
					result = line.data;
					break;
			}

			node = node.next;
		}
		
		return result;
	}
	
	private Data executeLine(Node line, Namespace namespace) {
		Data result = null;
		String command = (String) line.getValue();

		if ("define".equals(command))
			define(line, namespace);
		else if ("print".equals(command))
			print(line, namespace);
		else
			result = call(line, namespace);
		
		return result;
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
			System.out.print(eval(current, namespace).value);
			current = current.next;
		}

		System.out.println();
	}


	// ( methodname arg1 arg2 ) => (method (arg1 arg2) ( ... ) )
	private Data call(Node line, Namespace namespace) {
		Name name = namespace.lookup((String)line.getValue());

		Namespace subspace = new Namespace(namespace);

		Node param = line.next;

		Node arg = name.args;

		while (param != null) {
			subspace.add(new Name((String) arg.getValue(), null, param));
			arg = arg.next;
			param = param.next;
		}

		return evalList((Node) name.value, subspace);
	}
}
