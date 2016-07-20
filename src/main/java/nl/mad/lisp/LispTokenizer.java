package nl.mad.lisp;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

import nl.mad.lisp.readers.AtomReader;
import nl.mad.lisp.readers.EolCommentReader;
import nl.mad.lisp.readers.StringReader;

public class LispTokenizer {
	private EolCommentReader comment;
	private StringReader string;
	private AtomReader atom;
	
	private int parens = 0;

	public LispTokenizer() {
		comment = new EolCommentReader();
		string = new StringReader();
		atom = new AtomReader();
	}

	public List<Token> tokenize(PushbackReader input) {
		List<Token> tokens = new ArrayList<>();

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;

			switch (ch) {
				case '"':
					tokens.add(string.read(input));
					break;
				case '(':
					parens++;
					tokens.add(new Token(Token.Type.LIST_OPEN));
					break;
				case ')':
					if (parens == 0) throw new RuntimeException("Parse error: too many closing parentheses.");
					parens--;
					tokens.add(new Token(Token.Type.LIST_CLOSE));
					break;
				case ';':
					tokens.add(comment.read(input));
					break;
				default:
					if (!Character.isWhitespace(ch)) {
						unreadChar(input, read);
						tokens.add(atom.read(input));
					}

					break;
			}
			
			read = readChar(input);
		}

		if (parens != 0) throw new RuntimeException("Parse error: too few closing parentheses.");

		return tokens;
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
