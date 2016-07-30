package nl.mad.lisp;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class FactorialTest {
	@Test
	public void testFactorial() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/factorial.lisp"), out(stream));
		assertValue("6\n", stream);
	}

	private InputStream in(String file) {
		return getClass().getResourceAsStream(file);
	}
	
	private PrintStream out(OutputStream stream) {
		return new PrintStream(stream);
	}
	
	private void assertValue(String value, ByteArrayOutputStream stream) {
		String val = new String(stream.toByteArray());
		System.out.println(val);
		assertEquals(value, val);
	}

}
