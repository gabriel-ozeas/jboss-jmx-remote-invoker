package com.gabrielozeas.jboss.remote.jmx;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gabrielozeas.jboss.remote.isolation.IsolatedCallable;
import com.gabrielozeas.jboss.remote.isolation.IsolatedClassLoaderContext;
import com.gabrielozeas.jboss.remote.isolation.JBoss7IsolatedClassLoaderContext;
import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

/**
 * Instance de execucao de um metodo ou atualizacao de uma config de um JMX no JBoss 7.
 * Deve ser utilizado para executar somente uma unica chamada. Depois deve ser descartado.
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public class JBoss7JMXExecution extends JMXExecution {
	private static final Logger logger = LoggerFactory.getLogger(JBoss7JMXExecution.class);
	
	private JNDIConfiguration jndiConfiguration;
	
	public JBoss7JMXExecution(JNDIConfiguration jndiConfiguration) {
		this.jndiConfiguration = jndiConfiguration;
	}
	
	@Override
	protected IsolatedClassLoaderContext getContext() {
		return new JBoss7IsolatedClassLoaderContext();
	}

	protected JMXConnector getServerConnection() throws Exception {
		return new JBoss7JMXConnectionManager(jndiConfiguration).getServerConnectionInstance();
	}

	/**
	 * Executa o JMX via JMX Connector em um classloader isolado para evitar conflito com outras bibliotecas.
	 */
	@Override
	protected Object invoke() throws Exception {
		IsolatedClassLoaderContext isolatedContext = getContext();
		
		return isolatedContext.execute(new IsolatedCallable<Object>() {
			public Object run() throws Exception {
				MBeanServerConnection server  = getServerConnection().getMBeanServerConnection();
				
				ObjectName name = new ObjectName(getJmxName());
				Object result = server.invoke(name, getJmxMethod(), getJmxParameters(), getSignature());
				
				logger.info(getJmxName() + "." + getJmxMethod() + "(" + getJmxParameters() + "):" + result);
				
				return result;
			}
		});
	}
}
