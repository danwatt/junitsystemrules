package org.googlecode.junitsystemrules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExpectedExitTests {

	@Rule
	public ExpectedExit exit = new ExpectedExit();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void expectNoExitAndGetNone() {

	}

	@Test
	public void expectNoExitAndGetOne() {
		exception.expect(AssertionError.class);
		System.exit(1);
	}

	@Test
	public void expectExitAndGetNone() {
		exit.expectToExitWithStatus(1);
		exception.expect(AssertionError.class);
	}

	@Test
	public void expectExitWithACertainStatusAndGetAMatch() {
		exit.expectToExitWithStatus(1);
		System.exit(1);
	}

	@Test
	public void expectExitWithACertainStatusAndGetADifferentStatus() {
		exception.expect(AssertionError.class);
		exit.expectToExitWithStatus(1);
		System.exit(2);
	}
}
