package org.iMage.geometrify;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import org.iMage.geometrify.AbstractPrimitivePictureFilter;


public final class FilterManager {
	
	FilterManager() {
	}
	
	

		 
		
		public static List<AbstractPrimitivePictureFilter> getPlugins() {
			ServiceLoader<AbstractPrimitivePictureFilter> serviceLoader =
					ServiceLoader.load(AbstractPrimitivePictureFilter.class);
					List<AbstractPrimitivePictureFilter> p = new ArrayList<>();
					for (final AbstractPrimitivePictureFilter plugin : serviceLoader) {
					p.add(plugin);
					}
					// Sortieren mit dem Comparator aus JmjrstPlugin
					//Collections.sort(p);
					return p;
	
	}

}