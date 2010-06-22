package org.googlecode.junitsystemrules;

import java.security.Permission;

import org.junit.Assert;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ExpectedExit implements MethodRule {
	private int expectedValue = 0;
	private boolean expected = false;
	private SecurityManager old;

	protected static class ExitException extends SecurityException {
		private static final long serialVersionUID = 1L;
		public final int status;

		public ExitException(int status) {
			this.status = status;
		}
	}

	private class CaptureExitSecurityManager extends SecurityManager {
		public CaptureExitSecurityManager() {
		}

		@Override
		public void checkPermission(Permission perm) {
			if (!"setSecurityManager".equals(perm.getName())) {
				old.checkPermission(perm);
			}
		}

		@Override
		public void checkExit(int status) {
			throw new ExitException(status);
		}
	}

	public ExpectedExit() {
	}

	public Statement apply(final Statement base, FrameworkMethod method,
			Object target) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				old = System.getSecurityManager();
				System.setSecurityManager(new CaptureExitSecurityManager());

				boolean happened = false;
				int actual = 0;
				try {
					base.evaluate();
				} catch (ExitException e) {
					actual = e.status;
					happened = true;
				} finally {
					System.setSecurityManager(old);
				}
				if (expected) {
					Assert
							.assertTrue(
									"System.exit was supposed to be called, but wasn't",
									happened);
					Assert.assertEquals("System.exit(" + expectedValue
							+ ") was expected, instead was called with "
							+ actual, expectedValue, actual);
				} else {
					Assert.assertFalse(
							"System.exit was not supposed to be called, but it was with "
									+ actual, happened);
				}
			}

		};
	}

	public void expectToExitWithStatus(int status) {
		expected = true;
		expectedValue = status;
	}

}
