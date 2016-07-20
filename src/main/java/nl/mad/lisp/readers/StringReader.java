package nl.mad.lisp.readers;

import java.io.PushbackReader;

public class StringReader extends Reader {
	
	private boolean escape = false;

	@Override
	public String read(PushbackReader input) {
		StringBuffer result = new StringBuffer();

		int read = readChar(input);

		while (read != -1) {
			char ch = (char) read;
			
			if (escape) {
				// accept any char (or is that too much?)
				result.append(ch);
				escape = false;
			} else if  (ch == '\\') {
				escape = true;
			} else if (ch == '"') {
				break;
			} else {
				result.append(ch);
			}
			
			read = readChar(input);
		}
		
		if (escape) throw new RuntimeException("Parse error: escape char");

		return result.toString();
	}

}
