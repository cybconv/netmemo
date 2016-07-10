package net.sf.netmemo;

import net.sf.netmemo.gui.NetMemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Launcher {
	public static final String FS = System.getProperty("file.separator");
	public static final String PATH_MANIFEST = "/META-INF/MANIFEST.MF";
	public static final String CLASSPATH_ATTRIBUTE     = "Class-Path";
	public static final String JAVA_COMMAND_PROPERTY = "sun.java.command";
	public static final String JAVA_CLASSPATH_PROPERTY = "java.class.path";
	public static final String DEFAULT_JAR_NAME = "netmemo.jar";

	public static void main (String[] argv) throws Throwable {
//		System.out.println("argv.length="+argv.length);
//		for (String s: argv) {
//			System.out.println(s);
//		}

//		java.util.Map<String,String> m = System.getenv();
//		java.util.Iterator<String> it = m.keySet().iterator();
//		String key;
//		System.out.println("environnement is:");
//		while (it.hasNext()) {
//			key = it.next();
//			System.out.println("["+key+"]="+m.get(key));
//		}
		//System.getProperties().store(System.out, "system properties:\n");
//		loadJars();
		NetMemo nm = new NetMemo("NetMemo - ");
	}

//	private static void loadJars() throws IOException {
//		InputStream input = Launcher.class.getResourceAsStream(PATH_MANIFEST);
//		BufferedReader br   = new BufferedReader(new InputStreamReader(input));
//		String line;
//		String[] libs;
//
//		while ((line = br.readLine()) != null) {
//			line = line.trim();
//			if (line.startsWith(CLASSPATH_ATTRIBUTE)) {
//				libs = line.substring(CLASSPATH_ATTRIBUTE.length()).split("\\s");
//				for (String s: libs) {
//					System.out.println(s);
//					if (isJar(s)) {
//						
//					} else {
//						
//					}
//				}
//			}
//			//System.out.println(line);
//		}
//		br.close();
//	}

//	private static void loadJars() throws IOException, URISyntaxException {
//		String mainJarFile = System.getProperty(JAVA_COMMAND_PROPERTY,
//				System.getProperty(JAVA_CLASSPATH_PROPERTY, DEFAULT_JAR_NAME)).
//				split("\\s")[0];
//		URI jarURI = Launcher.class.getResource(PATH_MANIFEST).toURI();
//		String jus = jarURI.toString();
//		System.out.println("jar URI="+jarURI);
//		JarFile jarFile   = new JarFile(new File(jus.
//				substring(jarURI.getScheme().length()+1)));
//
//		System.out.println("main jar filename="+mainJarFile);
//		Manifest mf = jarFile.getManifest();
//		System.out.println("manifest="+mf);
//		Attributes mainAttrs = mf.getMainAttributes();
//		System.out.println("main jar attributes="+mainAttrs);
//		String v = mainAttrs.getValue(CLASSPATH_ATTRIBUTE);
//		String[] libs = (v == null? null: v.split("\\s"));
//
//		System.out.println("libs="+libs);
//		for (String s: libs) {
//			System.out.println(s);
//		}
//	}

	private static boolean isJar(String path) {
		return path.endsWith(".jar");// &&
			//new JAR
	}
}