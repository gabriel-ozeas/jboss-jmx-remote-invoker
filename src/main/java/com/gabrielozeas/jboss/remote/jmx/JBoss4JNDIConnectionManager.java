
package com.gabrielozeas.jboss.remote.jmx;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

public class JBoss4JNDIConnectionManager {
	private static Map<String, InitialContext> initialContextCache = new HashMap<String, InitialContext>();
	
	public InitialContext getInitialContextInstance(JNDIConfiguration jndiConfiguration) throws Exception {
		Properties configurations = jndiConfiguration.loadConfigurations();
		String connectionURL = (String)configurations.get("java.naming.provider.url");
		
		if (!initialContextCache.containsKey(connectionURL)) {
			InitialContext ctx = new InitialContext(jndiConfiguration.loadConfigurations());
			initialContextCache.put(connectionURL, ctx);
		}
		return initialContextCache.get(connectionURL);
	}
}
