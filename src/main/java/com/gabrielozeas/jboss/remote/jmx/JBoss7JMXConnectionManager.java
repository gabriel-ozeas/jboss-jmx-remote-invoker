package com.gabrielozeas.jboss.remote.jmx;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.gabrielozeas.jboss.remote.isolation.JNDIConfiguration;

/**
 * Gerenciador de conexao com JMX. Realiza a conexao com o JMX do  atraves do JMX Connector. 
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public class JBoss7JMXConnectionManager {
	/** URL de conexao com o JMX Connector */
	public static final String JMX_CONNECTION_URL = "jmx.connection.url";
	
	/** Usuario de conexao com o JMX Connector */
	public static final String JMX_CONNECTION_USER = "jmx.connection.user";
	
	/** Password de conexao com o JMX Connector */
	public static final String JMX_CONNECTION_PASSWORD = "jmx.connection.password";
	
	private Properties configurations;
	
	private static Map<String, JMXConnector> jmxConnectorCache = new HashMap<String, JMXConnector>();
	
	public JBoss7JMXConnectionManager(JNDIConfiguration configurations) {
		if (configurations != null) {
			this.configurations = configurations.loadConfigurations();
		}
	}
	
	public JBoss7JMXConnectionManager(Properties jmxConfigurations) {
		if (jmxConfigurations != null) {
			configurations = jmxConfigurations;
		}
	}
	
	/**
	 * Retorna uma conexao com o servidor JMX.
	 */
	public JMXConnector getServerConnectionInstance() throws Exception {
		String jmxURL = configurations.getProperty(JMX_CONNECTION_URL);
		
		if (!jmxConnectorCache.containsKey(jmxURL) ) {
			JMXServiceURL jmxServiceURL = new JMXServiceURL(jmxURL);
		        
	        String[] credentials = new String[] {
	        		configurations.getProperty(JMX_CONNECTION_USER), 
	        		configurations.getProperty(JMX_CONNECTION_PASSWORD)
	        };
	        
	        Map<String, String[]> env = new HashMap<String, String[]>();
			env.put(JMXConnector.CREDENTIALS, credentials);
			
			JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, env);
			jmxConnectorCache.put(jmxURL, jmxConnector);
		}

        return jmxConnectorCache.get(jmxURL);
	}
}
