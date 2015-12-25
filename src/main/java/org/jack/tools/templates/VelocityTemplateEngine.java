package org.jack.tools.templates;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class VelocityTemplateEngine implements TemplateEngine<VelocityTemplateEngine> {

	private static Logger logger = LoggerFactory.getLogger(VelocityTemplateEngine.class);
	private VelocityEngine engine;
	
	
	public VelocityTemplateEngine() {
		super();
		this.engine = new VelocityEngine();
	}

	public boolean process(String templateName, Map<String, Object> model, Writer out) {
		Preconditions.checkNotNull(templateName, "templateName parameter cannot be null");
		Preconditions.checkNotNull(model, "model parameter cannot be null");
		Preconditions.checkNotNull(out, "out parameter cannot be null");

		Template t = engine.getTemplate(templateName);
		Context ctx = new VelocityContext();
		for (Map.Entry<String, Object> item : model.entrySet()) {
			ctx.put(item.getKey(), item.getValue());
		}
		t.merge(ctx, out);
		
		return true;
	}

	public boolean process(Reader template, Map<String, Object> model, Writer out) {
		Preconditions.checkNotNull(template, "template parameter cannot be null");
		Preconditions.checkNotNull(model, "model parameter cannot be null");
		Preconditions.checkNotNull(out, "out parameter cannot be null");
		
		Context ctx = new VelocityContext();
		for (Map.Entry<String, Object> item : model.entrySet()) {
			ctx.put(item.getKey(), item.getValue());
		}
		engine.evaluate(ctx, out, "", template);
		return true;
	}

	public VelocityTemplateEngine init(TemplateEngineProperties properties) {
		if ( engine == null ) {
			return null;
		}
		for (Entry<String, Object> entry : properties) {
			engine.setProperty(entry.getKey(), entry.getValue());
		}
		return this;
	}

	public VelocityTemplateEngine init(Reader properties) {
		return init(TemplateEngineProperties.fromProperties(properties));
	}

	public Object getProperty(String id) {
		Preconditions.checkNotNull(id);
		return engine.getProperty(id);
	}

}
