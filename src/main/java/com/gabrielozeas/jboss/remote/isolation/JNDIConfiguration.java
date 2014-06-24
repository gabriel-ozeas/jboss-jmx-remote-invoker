package com.gabrielozeas.jboss.remote.isolation;

import java.io.InputStream;
import java.util.Properties;

/**
 * Arquivos de configuracao de conexoes remotas. Ex: Criacao de contextos JNDI e invocacoes de JMX.
 * JNDI Remote connection configuration files.
 * 
 * {@code 
 * # JNDI Remote Configuration for JBoss 7.1.1
 * jmx.connection.url=service:jmx:remoting-jmx://localhost:9999
 * jmx.connection.user=admin
 * jmx.connection.password=123
 * }
 * 
 */
public class JNDIConfiguration {

	private String configurationFile;

	public JNDIConfiguration(String configurationFile) {
		this.configurationFile = configurationFile;
	}

	public Properties loadConfigurations() {
		try {
			InputStream in = getClass().getResourceAsStream(configurationFile);
			Properties properties = null;

			if (in != null) {
				properties = new Properties();
				properties.load(in);
			}

			if (properties == null) {
				throw new RuntimeException("ERRO: Arquivo " + configurationFile + " não encontrado!");
			} else {
				return properties;
			}

		} catch (Exception e) {
			throw new RuntimeException("ERRO: Não foi possível criar InitialContext: ", e);
		}
	}
}
