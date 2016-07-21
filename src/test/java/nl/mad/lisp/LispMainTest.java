package nl.mad.lisp;

import java.io.IOException;

import org.junit.Test;

public class LispMainTest {

	@Test
	public void shouldDoSomething() throws IOException {
//		new LispMain().doit(getClass().getResourceAsStream("/hello.lisp"));
//		new LispMain().doit(getClass().getResourceAsStream("/hello_scope.lisp"));
		new LispMain().doit(getClass().getResourceAsStream("/hello_closures.lisp"));
	}
}
