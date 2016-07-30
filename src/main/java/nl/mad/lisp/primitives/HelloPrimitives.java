package nl.mad.lisp.primitives;

import nl.mad.lisp.Data;
import nl.mad.lisp.LispExecutor;
import nl.mad.lisp.Name;
import nl.mad.lisp.Namespace;
import nl.mad.lisp.Node;

/**
 * Primitives required for the 'Hello, World!' set of examples,
 * so arguably a 'minimal set'; or 'hello complete'.
 */
public class HelloPrimitives implements Primitives {
	private LispExecutor e;

	public HelloPrimitives(LispExecutor lispExecutor) {
		this.e = lispExecutor;
	}

	@Override
	public Data executeLine(Node line, Namespace namespace) {
		Data result = null;
		String command = (String) line.getValue();

		if ("define".equals(command))
			result = define(line, namespace);
		else if ("println".equals(command))
			result = println(line, namespace);
		else if ("+".equals(command))
			result = add(line, namespace);
		
		return result;
	}

	// (define fnark (arg1 arg2) ( ... ) ) of (define fnark "hoi")
	private Data define(Node line, Namespace namespace) {
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
		
		return NO_RESULT;
	}

	// ( print "a" "b" "c" )
	private Data println(Node line, Namespace namespace) {

		Node current = line.next;

		while (current != null) {
			e.out.println(e.eval(current, namespace).value);
			current = current.next;
		}
		e.out.flush();

		return NO_RESULT;
	}

	// implemented only for int (and String, through concat)
	private Data add(Node line, Namespace namespace) {
		int value = 0;

		Node current = line.next;

		while (current != null) {
			Data data = e.eval(current, namespace);
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
			buffer.append(e.eval(current, namespace).value);
			current = current.next;
		}
	
		return new Data(Data.Type.STRING, buffer.toString());
	}

}
