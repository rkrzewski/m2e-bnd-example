Bndtools / maven-bundle-plugin / m2e integration example
========================================================

Pre-requisites
--------------
* Eclipse Juno 3.8 http://download.eclipse.org/eclipse/downloads/drops/R-3.8-201206081200/
* EGit 2.2.0 http://download.eclipse.org/releases/juno
* M2E 1.3.0 http://download.eclipse.org/releases/juno
* Bndtools 2.0+ https://bndtools.ci.cloudbees.com/job/bndtools.master/386/artifact/bndtools.build/generated/p2/

There are plenty other configrations that would work as well. That's just what I have used to create the example.

Setting up the workspace
------------------------

Since EGit refuses to clone a repository into the root directory of the current workspace, it's probably most convenient to clone the repository first and then launch Eclipse using top level directory of the cloned repository as Eclipse workspace root. This should be the default configuration since Bndtools expect the cnf/ project to reside physically within the root directory of the workspace.

After starting eclipse, you should import the project. Use 'Import > General > Existing Projects into Workspace' to import the cnf/ project, then use 'Import > Maven > Existing Maven Projects' to import the various test- projects.

At this point you should be able to run an offline maven build starting with 'test-parent' project. Of course you can use 'Run As > Maven install' to do that from withing Eclipse.

Next, you should use 'Add Bndtools project nature' on test-api, test-impl and test-app project. This manual step is required because Bndtools does not have a m2e connctor plugin yet.

Running the example
-------------------

The project provides two predefined launchers: 'OSGi framework' will use test-app/run.bndrun to run the application live from the workspace. You should see the cheerful greeting offered by test.Greeter interface implemention provided by test.impl.GreeterImpl injected into test.app.GreeterClient class by Service Components Runtime, followed by Gogo shell prompt.

'Integration test' launcher will run tests in 'test-it' module. The tests are using Pax-Exam, JUnit 4 and Mockito and demostrate basic testing of SCR components. Note that when the tests are launched using JDT JUnit runner they will provision bundles built incrementally by bndtools into the test environment. When run by maven-surefire-plugin as a part of Maven build (eiter outside of Eclipse, or when using 'Run as > Maven Test', bundles built by maven-bundle-plugin will be used instead.

