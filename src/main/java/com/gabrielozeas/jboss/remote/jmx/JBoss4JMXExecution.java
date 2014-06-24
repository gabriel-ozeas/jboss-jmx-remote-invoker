package com.gabrielozeas.jboss.remote.jmx;

import javax.management.Attribute;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import org.jboss.jmx.adaptor.rmi.RMIAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gabrielozeas.jboss.remote.isolation.IsolatedCallable;
import com.gabrielozeas.jboss.remote.isolation.IsolatedClassLoaderContext;
import com.gabrielozeas.jboss.remote.isolation.JBoss4IsolatedClassLoaderContext;
import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

/**
 * Instance de execucao de um metodo ou atualizacao de uma config de um JMX no JBoss 4.
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public class JBoss4JMXExecution extends JMXExecution {
	private static final Logger logger = LoggerFactory.getLogger(JBoss4JMXExecution.class);
	
	private static final String RMI_ADAPTOR_JNDI_NAME = "jmx/invoker/RMIAdaptor";
	
	private JNDIConfiguration jndiConfiguration;
	
	public JBoss4JMXExecution(JNDIConfiguration jndiConfiguration) {
		this.jndiConfiguration = jndiConfiguration;
	}

	@Override
	protected IsolatedClassLoaderContext getContext() {
		return new JBoss4IsolatedClassLoaderContext();
	}
	
	/**
	 * Executa o JMX via RMIAdaptor em um classloader isolado para evitar conflito com bibliotecas mais novas.
	 */
	@Override
	protected Object invoke() throws Exception {
		IsolatedClassLoaderContext isolatedContext = getContext();
		
		return isolatedContext.execute(new IsolatedCallable<Object>() {
			public Object run() throws Exception {
				InitialContext ctx = new JBoss4JNDIConnectionManager().getInitialContextInstance(jndiConfiguration);
				
				Object objectServer = ctx.lookup(RMI_ADAPTOR_JNDI_NAME);
				RMIAdaptor server = (RMIAdaptor) objectServer;
				
				ObjectName name = new ObjectName(getJmxName());
				if (getJmxMethod() != null) {
					Object result = server.invoke(name, getJmxMethod(), getJmxParameters(), getSignature());
					logger.info(getJmxName() + "." + getJmxMethod() + "(" + getJmxParameters() + "):" + result);
					return result;
				} else {
					server.setAttribute(name, new Attribute(getAttributeName(), getAttributeValue()));
					return null;
				}
		}
		});
	}
}
