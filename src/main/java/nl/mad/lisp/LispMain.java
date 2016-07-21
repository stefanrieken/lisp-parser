package nl.mad.lisp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;

public class LispMain {
	
	public static void main(String ... args) {
		InputStream inputStream;

		if (args.length == 0) {
			System.out.println("Give me some LISP: ");
			inputStream = System.in;
		} else {
			inputStream = new ByteArrayInputStream(args[0].getBytes());
		}

		doit(inputStream);
	}
	
	public static void doit(InputStream inputStream) {
		PushbackReader reader = new PushbackReader(new InputStreamReader(inputStream));

		//List<Token> tokens = new LispTokenizer().tokenize(reader);
		Node root = new LispParser().parse(reader);
		new LispExecutor().evalList(root, null);

		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
