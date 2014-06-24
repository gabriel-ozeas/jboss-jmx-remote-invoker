package com.gabrielozeas.jboss.remote.isolation;

/**
 * Interface utilizada para implementar trechos de codigos que 
 * devam ser executados em contextos isolados.
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public interface IsolatedCallable<T> {
	public T run() throws Exception;
}
