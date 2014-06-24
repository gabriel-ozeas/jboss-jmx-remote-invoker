package com.gabrielozeas.jboss.remote.isolation;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaria para criar um classloader isolado com os JARs clientes do JBoss.
 * 
 * @author <a href="gabriel.ozeas@gmail.com">Gabriel Ozeas</a> 
 */
public class IsolatedClassLoaderFactory {
	
	/**
	 * Carrega um classLoader isolado com os jars listados + classLoader raiz.
	 * 
	 * @param directory Diretorio onde estao os jars
	 * @return Retona um classLoader isolado com os jars listados + classLoader raiz
	 */
	public ClassLoader createIsolatedSystemClassLoaderFromDir(String... directories) {
		List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
		for (String dir : directories) {	
			URLClassLoader classLoader = (URLClassLoader) createIsolatedClassLoaderFromDir(dir);
			classLoaders.add(classLoader);
		}
		classLoaders.add(ClassLoader.getSystemClassLoader());
		return new AluniteClassLoader((ClassLoader[]) classLoaders.toArray(new ClassLoader[0]));
	}
	
	/**
	 * Carrega um clasLoader isolado (sem classloader pai)
	 * 
	 * @param directory Diretorio onde estao os jars
	 * @return Classloader isolado.
	 */
	public ClassLoader createIsolatedClassLoaderFromDir(String directory) {
		URL[] urls = getJarUrlsFromDirectory(directory);
		return new URLClassLoader(urls, null);
	}
	
	protected URL[] getJarUrlsFromDirectory(String jarDir) {
		FileFilter jarFilter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".jar");
			}
		};
		
		// create URL for each JAR file found
		File[] jarFiles = new File(jarDir).listFiles(jarFilter);
		URL[] urls;

		if (null != jarFiles) {
			urls = new URL[jarFiles.length];

			for (int i = 0; i < jarFiles.length; i++) {
				try {
					urls[i] = jarFiles[i].toURI().toURL();
				} catch (MalformedURLException e) {
					throw new RuntimeException("Could not get URL for JAR file: " + jarFiles[i], e);
				}
			}

		} else {
			// no JAR files found
			urls = new URL[0];
		}
		
		return urls;
	}
}
