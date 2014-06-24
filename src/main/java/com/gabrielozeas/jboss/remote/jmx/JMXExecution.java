package com.gabrielozeas.jboss.remote.jmx;

import com.gabrielozeas.jboss.remote.isolation.IsolatedClassLoaderContext;

/**
 * Encapsula uma execucao de JMX para ser executado em um contexto isolado. 
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public abstract class JMXExecution {
	/** Nome do jmx a ser executado */
	private String jmxName;
	
	/** Metodo do jmx a ser executado */
	private String jmxMethod;
	
	/** Parametros do JMX */
	private Object[] jmxParameters = {};
	
	private String[] signature = new String[] {};
	
	/** Atributo para ser configurado no JMX */
	private String attributeName;
	private Object attributeValue;
	
	private Boolean alreadyInvoked = Boolean.FALSE;
	
	/** 
	 * Retorna um contexto para a execucao do JMX. Pode ser utilizado para criar um contexto 
	 * isolado para a execucao do JMX. */
	protected abstract IsolatedClassLoaderContext getContext();
	
	/** Executa efetivamente a chamada ao JMX retornando um objeto caso haja um. */
	protected abstract Object invoke() throws Exception;
	
	/** Configura o nome do JMX a ser executado */
	public JMXExecution forJMX(String jmxName) {
		this.jmxName = jmxName;
		return this;
	}
	
	/** Configura o metodo do JMX a ser executado */
	public JMXExecution invokeMethod(String jmxMethod) {
		this.jmxMethod = jmxMethod;
		return this;
	}
	
	/** Configura o atributo a ser alterado no JMX */
	public JMXExecution setAttribute(String attribute, Object value) {
		this.attributeName = attribute;
		this.attributeValue = value;
		return this;
	}
	
	/** Adiciona parametros a execucao do JMX */
	public JMXExecution withParameters(Object[] jmxParameters) {
		this.jmxParameters = jmxParameters;
		return this;
	}
	
	public JMXExecution withSignature(String[] signature) {
		this.signature = signature;
		return this;
	}
	
	/** Concretiza a execucao do JMX */
	public Object execute() throws Exception {
		if (jmxName == null) {
			throw new IllegalStateException("Nome do JMX nao pode ser nulo");
		}
		
		if (jmxMethod == null && attributeName == null) {
			throw new IllegalStateException("Metodo/Atributo do JMX nao pode ser nulo");
		}
		
		if (alreadyInvoked) {
			throw new IllegalStateException("Execucao ja invocada uma vez.");
		}
		
		try {
			return invoke();
		} finally {
			alreadyInvoked = Boolean.TRUE;
		}
	}

	public String getJmxName() {
		return jmxName;
	}

	public String getJmxMethod() {
		return jmxMethod;
	}

	public Object[] getJmxParameters() {
		return jmxParameters;
	}

	public String[] getSignature() {
		return signature;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}
}
