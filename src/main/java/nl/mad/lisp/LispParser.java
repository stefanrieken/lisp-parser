package nl.mad.lisp;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

import nl.mad.lisp.readers.AtomReader;
import nl.mad.lisp.readers.EolCommentReader;
import nl.mad.lisp.readers.StringReader;

/**
 * Parse directly, without tokenizing separately.
 */
public class LispParser {
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

		List<Token> tokens = new ArrayList<>();

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;

			switch (ch) {
				case '"':
					node = new Node(string.read(input).data);
					break;
				case '(':
					System.err.print("(");
					parens++;
					node = parse(input);
					break;
				case ')':
					if (parens == 0) throw new RuntimeException("Parse error: too many closing parentheses.");
					System.err.print(")");
					parens--;
					return root;
				case ';':
					comment.read(input);
					break;
				default:
					if (!Character.isWhitespace(ch)) {
						unreadChar(input, read);
						node = new Node(atom.read(input).data);
					}

					break;
			}

			// chain the node
			if (node != null) {
				if (root == null) {
					root = node;
				} else {
					if (current == null) current = root;
					current.next = node;
					current = node;
				}
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
