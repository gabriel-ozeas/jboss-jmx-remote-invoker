package com.gabrielozeas.jboss.remote.jmx;

import org.junit.Test;
import org.junit.Ignore;

import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

public class MixedJBossExecutionTest {
	@Test
	@Ignore
	public void callingMixedJBossExecutionTest() throws Exception {
		new JBoss4JMXExecution(new JNDIConfiguration("jndi.jboss7.properties"))
			.forJMX("jbosscustommbean:custom=JBoss7JMX")
			.invokeMethod("test")
			.execute();
			
		new JBoss7JMXExecution(new JNDIConfiguration("jndi.jboss7.properties"))
			.forJMX("jbosscustommbean:custom=JBoss7JMX")
			.invokeMethod("test")
			.execute();
	}
}
