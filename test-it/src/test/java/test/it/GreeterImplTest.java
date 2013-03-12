package test.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;

import test.Greeter;

@RunWith(PaxExam.class)
public class GreeterImplTest {

	private boolean isEclipseRun;

	private boolean isEclipseRun() {
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

	private Option workspaceBundle(String module) {
		return bundle(String.format("file:../%1$s/%2$s/%1$s.jar", module,
				isEclipseRun ? "target/bnd" : "target"));
	}

	@Configuration
	public Option[] paxExamConfig() throws IOException {
		isEclipseRun = isEclipseRun();
		return options(
				junitBundles(),
				mavenBundle(maven("org.apache.felix", "org.apache.felix.scr",
						"1.6.2")),
				mavenBundle(maven("org.apache.felix",
						"org.apache.felix.metatype", "1.0.6")),
				workspaceBundle("test-api"), workspaceBundle("test-impl"));
	}

	@Inject
	private Greeter greeter;

	@Test
	public void greeterSevicePresent() {
		assertNotNull("Greeter service not available", greeter);
	}

	@Test
	public void greeterSaysHello() {
		assertTrue("Unexpected greeting", greeter.greeting().toLowerCase()
				.contains("hello"));
	}
}
