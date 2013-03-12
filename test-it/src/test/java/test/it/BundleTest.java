package test.it;

import static org.ops4j.pax.exam.CoreOptions.bundle;

import org.ops4j.pax.exam.Option;

public abstract class BundleTest {

	private boolean isEclipseRun = isEclipseRun();

	private static boolean isEclipseRun() {
		StackTraceElement[] trace = new RuntimeException().getStackTrace();
		for (StackTraceElement frame : trace) {
			if (frame.getClassName().contains("org.eclipse.jdt")) {
				System.out.println("JDT test run - using Bndtools output");
				return true;
			}
		}
		System.out.println("Surefire test run - using maven output");
		return false;
	}

	protected Option workspaceBundle(String module) {
		return bundle(String.format("file:../%1$s/%2$s/%1$s.jar", module,
				isEclipseRun ? "target/bnd" : "target"));
	}
}
