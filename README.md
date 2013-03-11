Bndtools / maven-bundle-plugin / m2e integration example
========================================================

Pre-requisites
--------------
* Eclipse Juno 3.8 http://download.eclipse.org/eclipse/downloads/drops/R-3.8-201206081200/
* EGit 2.2.0 http://download.eclipse.org/releases/juno
* M2E 1.3.0 http://download.eclipse.org/releases/juno
* Bndtools 2.0+ https://bndtools.ci.cloudbees.com/job/bndtools.master/386/artifact/bndtools.build/generated/p2/
* M2E Tycho connector http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-tycho/0.6.0/N/0.6.0.201207302152/

There are plenty other configrations that would work as well. That's just what I have used to create the example.

Building it using Maven on commandline (optinal)
-------------------------------------------------
In case you want to give this a spin on the commandline first, make sure you build the parent project first:
mvn clean install -N -f test-parent/pom.xml

Then build everything in reactor:
mvn clean install -f test-parent/pom.xml


Setting up the workspace
------------------------

Since EGit refuses to clone a repository into the root directory of the current workspace, it's probably most convenient to clone the repository first and then launch Eclipse using top level directory of the cloned repository as Eclipse workspace root. This should be the default configuration since Bndtools expect the cnf/ project to reside physically within the root directory of the workspace.

After starting eclipse, you should import the project. Use 'Import > General > Existing Projects into Workspace' to import the cnf/ project, then use 'Import > Maven > Existing Maven Projects' to import the various test- projects.

At this point you should be able to run an offline maven build starting with 'test-parent' project. Of course you can use 'Run As > Maven install' to do that from withing Eclipse.

Next, you should use 'Add Bndtools project nature' on test-api, test-impl and test-app project. It's possible that a circullar dependency error will be reported on test-app project (this also happens after Eclipse restart). You can get rid of it using Run > Clean ...' on this project. It's irritating but harmless.

Running the example
-------------------

Open test-app/bnd.bnd, go to Run tab and use 'Run OSGi' button. You should see the cheerful greeting offered by test.Greeter interface implemention provided by test.impl.GreeterImpl injected into test.app.GreeterClient class by Service Components Runtime, followed by Gogo shell prompt.


