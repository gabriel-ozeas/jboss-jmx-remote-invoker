package com.gabrielozeas.jboss.remote.isolation;

/**
 * Contexto isolado para execucao de trechos de codigo com dependencias do JBoss 4
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public class JBoss4IsolatedClassLoaderContext extends IsolatedClassLoaderContext {
	
	/** Diretorio dos arquivos clientes do JBoss 4 */
	private static final String JBOSS_4_CLIENT_JARS_DIR = "/client/jboss4";

	@Override
	public String getIsolatedJarsDirectory() {
		return JBOSS_4_CLIENT_JARS_DIR;
	}
}
