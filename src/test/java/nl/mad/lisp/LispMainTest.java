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
}
