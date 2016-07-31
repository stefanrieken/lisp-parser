package nl.mad.lisp;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * All 'hello' tests require only the 'HelloPrimitives' set.
 * The 'OtherPrimitives' may be disabled just to prove this.
 */
public class HelloTest {

	@Test
	public void testHello() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/hello.lisp"), out(stream));
		assertValue("Hello, world!\n", stream);
	}

	@Test
	public void testHelloFn() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/hello_fn.lisp"), out(stream));
		assertValue("Hello, world!\n", stream);
	}

	@Test
	public void testHelloInt() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/hello_int.lisp"), out(stream));
		assertValue("Hello, 2 you too!\n", stream);
	}

	@Test
	public void testHelloScope() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/hello_scope.lisp"), out(stream));
		assertValue("Hello, world!\nGoodbye, cruel world!\n", stream);
	}

	@Test
	public void testHelloClosures() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/hello_closures.lisp"), out(stream));
		assertValue("Hello, who?!\nHello, me!\n", stream);
	}

	@Test
	public void testHelloOo() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		LispMain.run(in("/hello_oo.lisp"), out(stream));
		assertValue("Hello, 42!\nHello, world!\n", stream);
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
