package com.gabrielozeas.jboss.remote.jmx;

import org.junit.Test;
import org.junit.Ignore;

import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

public class JBoss7JMXExecutionTest {
	@Test
	@Ignore
	public void executeAJBoss7JMXWithSuccess() throws Exception {
		new JBoss7JMXExecution(new JNDIConfiguration("jndi.jboss7.properties"))
			.forJMX("jbosscustommbean:custom=JBoss7JMX")
			.invokeMethod("test")
			.execute();
	}
}
