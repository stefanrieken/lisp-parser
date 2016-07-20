package nl.mad.lisp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;

public class LispMain {
	
	public static void main(String ... args) throws IOException {
		PushbackReader reader = new PushbackReader(new InputStreamReader(System.in));

		System.out.println("Give me some LISP: ");
		
		//List<Token> tokens = new LispTokenizer().tokenize(reader);
		new LispParser().parse(reader);

		reader.close();
		
		
	}
}
