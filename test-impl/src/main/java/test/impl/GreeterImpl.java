package test.impl;

import aQute.bnd.annotation.component.Component;
import test.Greeter;

@Component
public class GreeterImpl implements Greeter {
	public String greeting() {
		return "Hello, World!";
	}
}
