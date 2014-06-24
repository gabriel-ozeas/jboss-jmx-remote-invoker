package com.gabrielozeas.jboss.remote.isolation;

/**
 * Contexto isolado para execucao de trechos de codigo com dependencias do JBoss 7
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public class JBoss7IsolatedClassLoaderContext extends IsolatedClassLoaderContext {
	/** Diretorio dos arquivos clientes do JBoss 7 */
	private static final String JBOSS_7_CLIENT_JARS_DIR = "/client/jboss7";

	@Override
	public String getIsolatedJarsDirectory() {
		return JBOSS_7_CLIENT_JARS_DIR;
	}
}
