package nl.mad.lisp.readers;

import java.io.PushbackReader;

public class EolCommentReader extends Reader {

	@Override
	public String read(PushbackReader input) {
		StringBuffer result = new StringBuffer();

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;
			if (ch == '\r' || ch == '\n') break;
			result.append(ch);
			
			read = readChar(input);
		}

		return result.toString();
	}
}
