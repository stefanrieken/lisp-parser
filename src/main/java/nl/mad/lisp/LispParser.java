package nl.mad.lisp;

import java.io.IOException;
import java.io.PushbackReader;

import nl.mad.lisp.readers.AtomReader;
import nl.mad.lisp.readers.EolCommentReader;
import nl.mad.lisp.readers.StringReader;
import nl.mad.lisp.type.MessageMarker;
import nl.mad.lisp.type.ListObject;

/**
 * Parse directly, without tokenizing separately.
 */
public class LispParser {
	/** 
	 * Extracted here because it may be subject to flavor.
	 * For one thing, using '.' may cause confusion with cons cell syntax.
	 */
	public static final char MESSAGE_MARKER = '.';

	private EolCommentReader comment;
	private StringReader string;
	private AtomReader atom;
	
	private int parens = 0;

	public LispParser() {
		comment = new EolCommentReader();
		string = new StringReader();
		atom = new AtomReader();
	}

	public Node parse(PushbackReader input) {
		Node root = null;
		Node current = null;
		Node node = null;

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;

			switch (ch) {
				case MESSAGE_MARKER:
					node = new Node(MessageMarker.MESSAGE_MARKER);
					break;
				case '"':
					node = new Node(string.read(input));
					break;
				case '(':
					parens++;
					node = new Node(new ListObject(parse(input)));
					break;
				case ')':
					if (parens == 0) throw new RuntimeException("Parse error: too many closing parentheses.");
					parens--;
					return root;
				case ';':
					comment.read(input);
					break;
				default:
					if (!Character.isWhitespace(ch)) {
						unreadChar(input, read);
						node = new Node(atom.read(input));
					}

					break;
			}

			// chain the node
			if (node != null) {
				if (root == null) {
					root = node;
					current = root;
				} else {
					current.next = node;
					current = node;
				}
				
				node = null;
			}

			read = readChar(input);
		}

		if (parens != 0) throw new RuntimeException("Parse error: too few closing parentheses.");

		return root;
	}

	private int readChar(PushbackReader input) {
		try {
			return input.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void unreadChar(PushbackReader input, int read) {
		try {
			input.unread(read);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
