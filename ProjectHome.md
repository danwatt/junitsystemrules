Occasionally applications written in a TDD environment need to interact with java.lang.System. Sometimes writing tests for this behavior gets rather involved, and is sometimes skipped. It is my hope with this library to add a few Rules (requiring Junit 4.7 or higher) to make testing these types of calls easier.

For now, the following calls can be tested:
  * System.out
  * System.err
  * System.exit

Here is a quick example of how these rules can be used:
```
public class JunitSystemRulesIntegration {
	@Rule
	public ExpectedExit expectedExit = ExpectedExit.none();

	@Rule
	public SystemOutputRule sysout = SystemOutputRule.sysOut();
	
	@Test
	public void iDoNotWantSysout() {
		sysout.expectToBeEmpty();
		//Fails
		System.out.println("something");
	}
	
	@Test
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
```