package org.googlecode.junitsystemrules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SystemOutputRuleTests {
	@Rule
	public SystemOutputRule sysout = SystemOutputRule.sysOut();
	public SystemOutputRule syserr = SystemOutputRule.sysErr();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void captureSysOut() {
		sysout.expectToContain("Hello world");
		System.out.println("Hello world");
	}

	@Test
	public void expectItToContainSomething() {
		syserr.expectToContain("Hello world");
		System.err.println("Hello world");
	}

	@Test
	public void doesntContainWhatWeAreLookingFor() {
		expectedException.expect(AssertionError.class);
		sysout.expectToContain("Hello world");
		System.out.println("something");
	}

	@Test
	public void expectNoOutput() {
		sysout.expectToBeEmpty();
	}

	@Test
	public void expectNoOutputButGotSome() {
		expectedException.expect(AssertionError.class);
		sysout.expectToBeEmpty();
		System.out.println("something");
	}
}
