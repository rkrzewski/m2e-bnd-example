package test.it;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.felix.scr.Component;
import org.apache.felix.scr.ScrService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.BundleContext;

import test.Greeter;

@RunWith(PaxExam.class)
public class GreeterClientTest extends BundleTest {

	@Configuration
	public Option[] paxExamConfig() throws IOException {
		return options(
				junitBundles(),
				mavenBundle(maven("org.apache.felix", "org.apache.felix.scr",
						"1.6.2")),
				mavenBundle(maven("org.apache.felix",
						"org.apache.felix.metatype", "1.0.6")),
				mavenBundle(maven("org.mockito", "mockito-core", "1.9.5")),
				mavenBundle(maven("org.objenesis", "objenesis", "1.3")),
				workspaceBundle("test-api"), workspaceBundle("test-app"));
	}

	@Inject
	private BundleContext bundleContext;

	@Inject
	private ScrService scrService;

	@Test
	public void testGreeterClientActivation() throws InterruptedException {
		Component[] comps = scrService.getComponents();
		Component greeterClient = null;
		for (Component comp : comps) {
			if (comp.getName().equals("test.app.GreeterClient")) {
				greeterClient = comp;
			}
		}
		assertNotNull("GreeterClient component not found", greeterClient);

		// component is not satisfied yet
		Assert.assertEquals(Component.STATE_UNSATISFIED,
				greeterClient.getState());

		// provide a mock service
		Greeter greeter = Mockito.mock(Greeter.class);
		Mockito.when(greeter.greeting()).thenReturn("Ahoj, przygodo!");
		bundleContext.registerService(Greeter.class, greeter, null);

		// check that component got enabled
		Thread.sleep(500);
		Assert.assertEquals(Component.STATE_ACTIVE, greeterClient.getState());
	}
}
