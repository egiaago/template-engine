package org.jack.tools.templates;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public interface TemplateEngine<T extends TemplateEngine> {
	
	T init(TemplateEngineProperties properties);
	T init(Reader properties);
	
	boolean process(String templateName, Map<String, Object> model, Writer out);
	boolean process(Reader template, Map<String, Object> model, Writer out);
	
	Object getProperty(String id);
}
