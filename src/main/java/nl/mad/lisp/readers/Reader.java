package nl.mad.lisp.readers;

import java.io.IOException;
import java.io.PushbackReader;

import nl.mad.lisp.Data;

/**
 * Read (parse) different aspects of the (LISP) language.
 * 
 * Note: for the sake of runtime simplicity, the character and comment reader are invoked
 * with the first char already read. Otherwise, where they matter, chars already read should
 * be pushed back on input. 
 */
public abstract class Reader {
	public abstract Data read(PushbackReader input);
	
	protected int readChar(PushbackReader input) {
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
