package nl.mad.lisp.readers;

import java.io.PushbackReader;

import nl.mad.lisp.LispParser;
import nl.mad.lisp.type.AtomObject;
import nl.mad.lisp.type.IntegerObject;
import nl.mad.lisp.type.SelfishObject;

/**
 * Parse any Atom that is not a String (which is parsed separately due to having to support escaping).
 * 
 * Additionally, and in the interest of runtime performance, we will recognize a number of basic types and convert them
 * to their Java equivalent. Types not converted here are passed as literals to the runtime environment, where they may or may
 * not hold any special meaning. 
 * 
 * Specifically, we leave the interpretation of Boolean values to the runtime, both as an illustration of this possibility, and
 * because different Lisp implementations do not agree on their exact representation (so we leave some flexibility there).
 * 
 * So in practice we choose to only support ints and floats natively.
 */
public class AtomReader extends Reader {

	@Override
	public SelfishObject read(PushbackReader input) {
		StringBuffer result = new StringBuffer();

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;

			// Seems like we have to check for all the other tokens here.
			// This is because the Atom syntax is not otherwise limited, e.g. by only allowing alphanumeric chars.
			if (Character.isWhitespace(ch) || ch == '(' || ch == ')' || ch == ';' || ch == LispParser.MESSAGE_MARKER) {
				unreadChar(input, read);
				break;
			}
			result.append(ch);

			read = readChar(input);
		}
		
		return makeObject(result.toString());
	}

	private SelfishObject makeObject(String data) {
		// There are still no good (no-exception-throwing) methods to check for type conversion in Java. 
		try {
			return new IntegerObject(Integer.parseInt(data));
		} catch (NumberFormatException e) {
//			try {
//				return new Data(Data.Type.FLOAT, Float.parseFloat(data));
//			} catch (NumberFormatException f) {
				return new AtomObject(data);
//			}
		}
	}
}
