package org.jack.tools.templates;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTemplateEngine implements TemplateEngine<FreemarkerTemplateEngine> {

	private static final String TEMPLATE_DIR_PREFIX = "template-dir";
	private static final String TEMPLATE_CLASS_PREFIX = "template-class";
	private Configuration cfg;
	private Map<String, Object> privateCfg = null;
	
	public FreemarkerTemplateEngine() {
		super();
		cfg = new Configuration(Configuration.VERSION_2_3_23);
		privateCfg = new HashMap<String, Object>();
	}

	public FreemarkerTemplateEngine init(TemplateEngineProperties properties) {
		List<TemplateLoader> loaders = new LinkedList<TemplateLoader>();
		for (Entry<String, Object> entry : properties) {
			try {
				if ( entry.getKey().startsWith(TEMPLATE_DIR_PREFIX) ) {
					loaders.add(new FileTemplateLoader(new File(entry.getValue().toString())));
					privateCfg.put(entry.getKey(), entry.getValue());
				}
				else if ( entry.getKey().startsWith(TEMPLATE_CLASS_PREFIX) ) {
					loaders.add(new ClassTemplateLoader(FreemarkerTemplateEngine.class, entry.getValue().toString()));
					privateCfg.put(entry.getKey(), entry.getValue());
				}
				else {
					cfg.setSetting(entry.getKey(), entry.getValue().toString());
				}
			} catch (TemplateException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders.toArray(new TemplateLoader[0]));
		cfg.setTemplateLoader(mtl);
		return this;
	}

	public FreemarkerTemplateEngine init(Reader properties) {
		return init(TemplateEngineProperties.fromProperties(properties));
	}

	public boolean process(String templateName, Map<String, Object> model, Writer out) {
		Template template;
		try {
			template = cfg.getTemplate(templateName);
			if ( template != null ) {
				template.process(model, out);
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public boolean process(Reader reader, Map<String, Object> model, Writer out) {
		try {
			Template template = new Template("", reader, cfg);
			template.process(model, out);
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public Object getProperty(String id) {
		Object res = privateCfg.get(id);
		if ( res == null ) {
			res = cfg.getSetting(id);
		}
		return res;
	}

}
