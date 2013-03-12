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

	@Configuration
	public Option[] paxExamConfig() throws IOException {
		return options(
				junitBundles(),
				mavenBundle(maven("org.apache.felix", "org.apache.felix.scr",
						"1.6.2")),
				mavenBundle(maven("org.apache.felix",
						"org.apache.felix.metatype", "1.0.6")),
				bundle("file:../test-api/target/bnd/test-api.jar"),
				bundle("file:../test-impl/target/bnd/test-impl.jar"));
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
