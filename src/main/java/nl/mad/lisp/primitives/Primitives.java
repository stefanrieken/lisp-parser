package nl.mad.lisp.primitives;

import nl.mad.lisp.Data;
import nl.mad.lisp.Namespace;
import nl.mad.lisp.Node;

public interface Primitives {
	static Data NO_RESULT = new Data(Data.Type.NULL, null);

	Data executeLine(Node line, Namespace namespace);
}
