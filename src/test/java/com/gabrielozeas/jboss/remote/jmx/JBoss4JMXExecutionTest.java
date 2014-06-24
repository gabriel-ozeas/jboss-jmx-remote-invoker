package com.gabrielozeas.jboss.remote.jmx;

import org.junit.Ignore;
import org.junit.Test;

import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

public class JBoss4JMXExecutionTest {
	@Test
	@Ignore
	public void executeAJBoss4JMXWithSuccess() throws Exception {
		new JBoss4JMXExecution(new JNDIConfiguration("jndi.jboss4.properties"))
			.forJMX("jbosscustommbean:custom=JBoss4JMX")
			.invokeMethod("test")
			.execute();
	}
}
