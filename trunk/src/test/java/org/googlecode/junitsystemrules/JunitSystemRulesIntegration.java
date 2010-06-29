package org.googlecode.junitsystemrules;

import org.googlecode.junitsystemrules.SystemOutputRule.SystemOutType;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class JunitSystemRulesIntegration {
	@Rule
	public ExpectedExit expectedExit = new ExpectedExit();

	@Rule
	public SystemOutputRule sysout = new SystemOutputRule(SystemOutType.Out);
	
	@Test
	@Ignore
	public void iDoNotWantSysout() {
		sysout.expectToBeEmpty();
		//Fails
		System.out.println("something");
	}
	
	@Test
	@Ignore
	public void iDoNotWantExit() {
		//Fails
		System.exit(1);
	}
	
	@Test
	public void exitWithDiffentStatus() {
		expectedExit.expectToExitWithStatus(2);
		//Passes
		System.exit(2);
	}
}
