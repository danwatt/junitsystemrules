package org.googlecode.junitsystemrules;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.Verifier;

public class SystemOutputRule extends Verifier {
	public static enum SystemOutType {
		Out, Error
	};

	private PrintStream old;
	private ByteArrayOutputStream outputStream;
	private PrintStream printStream;

	private final SystemOutType type;
	private List<Matcher<String>> expectations = new ArrayList<Matcher<String>>();

	public static SystemOutputRule sysOut() {
		return new SystemOutputRule(SystemOutType.Out);
	}
	public static SystemOutputRule sysErr() {
		return new SystemOutputRule(SystemOutType.Error);
	}
	
	public SystemOutputRule(SystemOutType type) {
		this.type = type;
		outputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(outputStream);
		switch (type) {
		case Error:
			old = System.err;
			System.setErr(printStream);
			break;
		case Out:
			old = System.out;
			System.setOut(printStream);
		}
	}

	@Override
	protected void verify() throws Throwable {
		printStream.close();
		switch (type) {
		case Error:
			System.setErr(old);
			break;
		case Out:
			System.setOut(old);
		}
		String contents = new String(outputStream.toByteArray());
		for (Matcher<String> m : expectations) {
			Assert.assertThat(contents, m);
		}
	}

	public void expectToContain(String string) {
		System.err.println("Adding a matcher, currently there are " + this.expectations.size());
		expect(JUnitMatchers.containsString(string));
	}

	private void expect(Matcher<String> containsString) {
		this.expectations.add(containsString);
	}

	public void expectToBeEmpty() {
		expect(CoreMatchers.equalTo(""));
		
	}

}
