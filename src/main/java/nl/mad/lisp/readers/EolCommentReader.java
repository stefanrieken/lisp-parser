package nl.mad.lisp.readers;

import java.io.PushbackReader;

import nl.mad.lisp.Token;

public class EolCommentReader extends Reader {

	@Override
	public Token read(PushbackReader input) {
		StringBuffer result = new StringBuffer();

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;
			if (ch == '\r' || ch == '\n') break;
			result.append(ch);
			
			read = readChar(input);
		}
		
		return new Token(Token.Type.COMMENT, result.toString());
	}
}
