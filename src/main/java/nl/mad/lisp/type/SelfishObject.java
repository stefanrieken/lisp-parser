package nl.mad.lisp.type;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.mad.lisp.LispExecutor;
import nl.mad.lisp.Node;

public class SelfishObject {
	public static SelfishObject OBJECT_ROOT = new SelfishObject(null, "object_root");

	public Object value = null;
	public SelfishObject parent; // a 1 parent per child policy to help transition from lists to objects
	public Map<String, SelfishObject> assocs = new LinkedHashMap<String,SelfishObject>();

	public SelfishObject(SelfishObject parent, Object value) {
		this.parent = parent;
		this.value = value;
	}

	// evaluate current object
	public SelfishObject eval(Node line, SelfishObject namespace, LispExecutor e) {
		return this;
	}

	// local lookup only; primitives not supported (for now)
	// example: a
	public SelfishObject access(String key) {
		SelfishObject result = assocs.get(key);
		if (result == null && parent != null) result = parent.access(key);
		return result;
	}

	// lookup-message; same as access (for now)
	// example: obj.a
	public SelfishObject lookup(String key) {
		return access(key);
	}

	// lookup-and-execute message
	// example: (obj.a) [lisp style] obj.a(); [c style]
	public SelfishObject execute(Node line, SelfishObject namespace, LispExecutor e) {
		SelfishObject result = null;
		String message = (String) line.getValue();

		if ("=".equals(message))
			result = eq(line, namespace, e);
		else if ("*".equals(message))
			result = times(line, namespace, e);
		else if ("-".equals(message))
			result = minus(line, namespace, e);
		else if ("if".equals(message))
			result = iff(line, namespace, e);
		else if ("define".equals(message))
			result = define(line, namespace, e);
		else if ("method".equals(message))
			result = method(line, namespace, e);
		else if ("println".equals(message))
			result = println(line, namespace, e);
		else if ("+".equals(message))
			result = add(line, namespace, e);
		else {
			// TODO bit weird here that we don't use 'this' as namespace. Might disappear after transition to oo
			result = namespace.lookup(message);
			if (result == null)
				throw new RuntimeException("Lookup failed: " + message);
			result = result.eval(line, namespace, e);
		}

		return result;
	}

	// (= a b)
	private SelfishObject eq(Node line, SelfishObject namespace, LispExecutor e) {
		Node a = line.next;
		Node b = a.next;
		return new BooleanObject(e.eval(a, namespace).value.equals(e.eval(b, namespace).value));
	}

	// (* a b)
	private SelfishObject times(Node line, SelfishObject namespace, LispExecutor e) {
		Node a = line.next;
		Node b = a.next;

		return new IntegerObject(((Integer) e.eval(a, namespace).value).intValue() * ((Integer) e.eval(b, namespace).value).intValue());
	}

	// (- a b)
	private SelfishObject minus(Node line, SelfishObject namespace, LispExecutor e) {
		Node a = line.next;
		Node b = a.next;
		return new IntegerObject(((Integer) e.eval(a, namespace).value).intValue() - ((Integer) e.eval(b, namespace).value).intValue());
	}

	// ( if cond a [b] )
	private SelfishObject iff(Node line, SelfishObject namespace, LispExecutor e) {

		Node cond = line.next;
		Node a = cond.next;
		Node b = a.next;
		
		if (Boolean.TRUE.equals(e.eval(cond, namespace).value)) {
			return e.eval(a, namespace);
		} else if (b != null) {
			return e.eval(b, namespace);
		}
		
		return NilObject.NO_DATA;
	}

	// (define fnark "hoi")
	private SelfishObject define(Node line, SelfishObject namespace, LispExecutor e) {
		Node current = line.next;
		String name = (String) current.getValue();
		current = current.next;
		// potential override; this is where you should GC!
		namespace.assocs.put(name, e.eval(current, namespace));
		return NilObject.NO_DATA;
	}

	// (method (arg1 arg2) ( ... ) )
	private MethodObject method(Node line, SelfishObject namespace, LispExecutor e) {
		return new MethodObject(line.next);
	}

	// ( print "a" "b" "c" )
	private SelfishObject println(Node line, SelfishObject namespace, LispExecutor e) {

		Node current = line.next;

		while (current != null) {
			e.out.println(e.eval(current, namespace).value);
			current = current.next;
		}
		e.out.flush();

		return NilObject.NO_DATA;
	}

	// implemented only for int (and String, through concat)
	private SelfishObject add(Node line, SelfishObject namespace, LispExecutor e) {
		int value = 0;

		Node current = line.next;

		while (current != null) {
			SelfishObject data = e.eval(current, namespace);
			if (data instanceof StringObject) return concat(line, namespace, e);

			value += (Integer) data.value;
			current = current.next;
		}

		return new IntegerObject(value);
	}

	private SelfishObject concat(Node line, SelfishObject namespace, LispExecutor e) {
		StringBuffer buffer = new StringBuffer();

		Node current = line.next;

		while (current != null) {
			buffer.append(e.eval(current, namespace).value);
			current = current.next;
		}
	
		return new StringObject(buffer.toString());
	}

	public String toString() {
		return value.toString();
	}
}
