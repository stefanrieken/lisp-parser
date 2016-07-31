package nl.mad.lisp;

import java.io.PrintStream;

import nl.mad.lisp.type.AtomObject;
import nl.mad.lisp.type.ListObject;
import nl.mad.lisp.type.MessageMarker;
import nl.mad.lisp.type.SelfishObject;

public class LispExecutor {
	public PrintStream out;

	public LispExecutor(PrintStream out) {
		this.out = out;
	}

	public SelfishObject eval (Node node, SelfishObject namespace) {
		SelfishObject result;

		if (node.data instanceof AtomObject) {
			// single object reference; we do not yet support dot-invocation from here, so this is still simple
			result = access(node, namespace);
		} else if (node.data instanceof ListObject) {
			// single invocation
			result = evalList((Node) node.data.value, namespace);
		} else {
			// direct value; we don't store these separately yet
			result = node.data;
		}

		return result;
	}

	public SelfishObject evalList (Node node, SelfishObject namespace) {
		SelfishObject result = null;

		if (node.data instanceof AtomObject || (node.next != null && node.next.data == MessageMarker.MESSAGE_MARKER))
			return executeLine(node, namespace);

		// these are purely-scope brackets. They can contain any mix of values and lists
		SelfishObject sub = new SelfishObject(namespace, "a_namespace");
		while (node != null) {
			result = eval((Node) node, sub);

			node = node.next;
		}
		
		return result;
	}

	private SelfishObject access(Node line, SelfishObject namespace) {
		SelfishObject result = namespace.access((String) line.data.value);
		if (result == null)
			throw new RuntimeException("Lookup failed: " + line.data.value);
		return result;
	}

	private SelfishObject executeLine(Node line, SelfishObject namespace) {
		if (line.next != null && line.next.data == MessageMarker.MESSAGE_MARKER) {
			// remote execution
			return line.data.execute(line.next.next, eval(line, namespace), this);
		} else {
			// local execution
			return namespace.execute(line, namespace, this);
		}
	}
}
