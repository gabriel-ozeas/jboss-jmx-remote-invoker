package com.gabrielozeas.jboss.remote.isolation;

/**
 * Contexto para a execucao de trechos de codigo com dependencias de JARs
 * que devem permanecer isolados.
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public abstract class IsolatedClassLoaderContext {
	
	/**
	 * Retorna o caminho relativo do diretorio em que sao armazenados os arquivos 
	 * especificos de cada servidor.
	 */
	public abstract String getIsolatedJarsDirectory();
	
	/**
	 * Executa um trecho de codigo com o contexto isolado.
	 * 
	 * @param command Comando para ser executado isoladamente
	 * @throws Exception Excecao que pode ser lancada pelo trecho de codigo
	 */
	public <T> T execute(IsolatedCallable<T> command) throws Exception {
		ClassLoader actualClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(getIsolatedClassLoader());
			return command.run();
		} finally {
			Thread.currentThread().setContextClassLoader(actualClassLoader);
		}
	}
	
	/**
	 * Retorna um classloader com JARs clientes do JBOSS 4
	 */
	public ClassLoader getIsolatedClassLoader() throws Exception {
		IsolatedClassLoaderFactory factory = new IsolatedClassLoaderFactory();
		return factory.createIsolatedSystemClassLoaderFromDir(getClass().getResource(getIsolatedJarsDirectory()).getFile());
	}
}
