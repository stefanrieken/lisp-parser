package nl.mad.lisp.primitives;

import nl.mad.lisp.Data;
import nl.mad.lisp.LispExecutor;
import nl.mad.lisp.Namespace;
import nl.mad.lisp.Node;

public class OtherPrimitives implements Primitives {
	private LispExecutor e;

	public OtherPrimitives(LispExecutor lispExecutor) {
		this.e = lispExecutor;
	}

	@Override
	public Data executeLine(Node line, Namespace namespace) {
		Data result = null;
		String command = (String) line.getValue();

		if ("if".equals(command))
			result = iff(line, namespace);
		if ("=".equals(command))
			result = eq(line, namespace);
		else if ("*".equals(command))
			result = times(line, namespace);
		else if ("-".equals(command))
			result = minus(line, namespace);
		
		return result;
	}

	// ( if cond a [b] )
	private Data iff(Node line, Namespace namespace) {

		Node cond = line.next;
		Node a = cond.next;
		Node b = a.next;
		
		if (Boolean.TRUE.equals(e.eval(cond, namespace).value)) {
			return e.eval(a, namespace);
		} else if (b != null) {
			return e.eval(b, namespace);
		}
		
		return NO_RESULT;
	}

	// (= a b)
	private Data eq(Node line, Namespace namespace) {
		Node a = line.next;
		Node b = a.next;
		return new Data(Data.Type.BOOLEAN, e.eval(a, namespace).value.equals(e.eval(b, namespace).value));
	}

	// (* a b)
	private Data times(Node line, Namespace namespace) {
		Node a = line.next;
		Node b = a.next;

		return new Data(Data.Type.INTEGER, ((Integer) e.eval(a, namespace).value).intValue() * ((Integer) e.eval(b, namespace).value).intValue());
	}

	// (- a b)
	private Data minus(Node line, Namespace namespace) {
		Node a = line.next;
		Node b = a.next;
		return new Data(Data.Type.INTEGER, ((Integer) e.eval(a, namespace).value).intValue() - ((Integer) e.eval(b, namespace).value).intValue());
	}
}
