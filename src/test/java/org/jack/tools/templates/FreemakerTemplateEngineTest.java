package org.jack.tools.templates;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FreemakerTemplateEngineTest {

	@Test
	public void testProcessStringMapOfStringObjectWriter() {
		TemplateEngineProperties p = new TemplateEngineProperties();
		p.setProperty("template-class", "/freemarker");
		
		TemplateEngine te = new FreemarkerTemplateEngine();
		te.init(p);
		Assert.assertEquals("/freemarker", te.getProperty("template-class"));
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Jack");
		StringWriter wr = new StringWriter();
		te.process("helloworld", model, wr);
		Assert.assertEquals("Hello, Jack", wr.toString());
	}

	@Test
	public void testProcessReaderMapOfStringObjectWriter() {
		TemplateEngineProperties p = new TemplateEngineProperties();
		p.setProperty("template-class", "/freemaker/helloworld.ftl");
		TemplateEngine<?> te = new FreemarkerTemplateEngine().init(p);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Jack");
		StringWriter wr = new StringWriter();
		StringReader sr = new StringReader("Hello, ${name}");
		te.process(sr, model, wr);
		Assert.assertEquals("Hello, Jack", wr.toString());
	}

	@Test
	public void testInitTemplateEngineProperties() {
		TemplateEngineProperties p = new TemplateEngineProperties();
		p.setProperty("template-class", "/freemaker/helloworld.ftl");
		
		TemplateEngine<?> te = new FreemarkerTemplateEngine();
		te.init(p);
		Assert.assertEquals("/freemaker/helloworld.ftl", te.getProperty("template-class"));
		
	}

	@Test
	public void testInitReader() {
		TemplateEngine te = new VelocityTemplateEngine();
		StringReader sr = new StringReader("template_loader=freemarker.cache.MultiTemplateLoader");
		te.init(sr);
		Assert.assertEquals("freemarker.cache.MultiTemplateLoader",te.getProperty("template_loader"));
	}

}
