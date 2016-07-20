package nl.mad.lisp;

import java.io.IOException;

import org.junit.Test;

public class LispMainTest {

	@Test
	public void shouldDoSomething() throws IOException {
		LispMain.main( new String[] {
			"(define hello (who) (print \"hello, \" who \"!\"))\n" +
			"(hello \"world\")"
		});
	}

	@Test
	public void shouldDoSomethingToo() throws IOException {
		LispMain.main( new String[] {
			"(define greeting \"hello, \")" +
			"(define who \"who\")" +
			"(define hello (who) (print greeting who \"!\"))\n" +
			"(hello \"world\")"
		});
	}
}
