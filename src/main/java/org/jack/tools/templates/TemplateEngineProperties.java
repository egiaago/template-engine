package org.jack.tools.templates;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class TemplateEngineProperties implements Iterable<Map.Entry<String, Object>> {
	private Map<String, Object> properties;
	
	public TemplateEngineProperties() {
		super();
		properties = new HashMap<String, Object>();
	}

	public void setProperty(String id, Object value) {
		properties.put(id, value);
	}
	
	public TemplateEngineProperties put(String id, Object value) {
		properties.put(id, value);
		return this;
	}
	
	public Object getProperty(String id) {
		return properties.get(id);
	}

	public Iterator<Entry<String, Object>> iterator() {
		return properties.entrySet().iterator();
	}

	public static TemplateEngineProperties fromProperties(Reader propReader) {
		if ( propReader != null ) {
			Properties p = new Properties();
			try {
				p.load(propReader);
				TemplateEngineProperties tep = new TemplateEngineProperties();
				for(Entry<Object, Object> item : p.entrySet()) {
					tep.setProperty(item.getKey().toString(), item.getValue());
				}
				return tep;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
