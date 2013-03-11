package test.app;

import test.Greeter;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class GreeterClient {

	private Greeter greeter;

	@Reference
	public void setGreeter(Greeter greeter) {
		this.greeter = greeter;
	}

	@Activate
	public void activate() {
		System.out.println(greeter.greeting());
	}
}
