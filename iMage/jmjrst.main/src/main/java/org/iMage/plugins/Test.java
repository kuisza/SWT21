package org.iMage.plugins;

import java.util.List;
import java.util.ServiceLoader;


public class Test {
	public static void main(String[] args) {
		
		//ServiceLoader<JmjrstPlugin> ld = ServiceLoader.load(JmjrstPlugin.class);
	//	JmjrstPlugin plugin = ld.iterator().next();
	//	System.out.println(plugin.getName());
		
		
		
		

		 List<JmjrstPlugin> pluginloads = PluginManager.getPlugins();
		 
	for ( JmjrstPlugin p : pluginloads) {
			 System.out.println(p.getName());
		}
	}

}