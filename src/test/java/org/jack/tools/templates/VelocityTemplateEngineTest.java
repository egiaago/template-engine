package org.jack.tools.templates;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class VelocityTemplateEngineTest {

	@Test
	public void testProcessStringMapOfStringObjectWriter() {
		TemplateEngineProperties p = new TemplateEngineProperties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.description", "Class resource loader");
		p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		
		TemplateEngine<?> te = new VelocityTemplateEngine();
		te.init(p);
		Assert.assertEquals("Class resource loader", te.getProperty("class.resource.loader.description"));
		Assert.assertEquals("org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader", te.getProperty("class.resource.loader.class"));
		Assert.assertEquals("class", te.getProperty("resource.loader"));
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Jack");
		StringWriter wr = new StringWriter();
		te.process("velocity/helloworld", model, wr);
		Assert.assertEquals("Hello, Jack", wr.toString());
	}

	@Test
	public void testProcessReaderMapOfStringObjectWriter() {
		TemplateEngine<?> te = new VelocityTemplateEngine();
		
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
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.description", "Class resource loader");
		p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		
		TemplateEngine te = new VelocityTemplateEngine();
		te.init(p);
		Assert.assertEquals("Class resource loader", te.getProperty("class.resource.loader.description"));
		Assert.assertEquals("org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader", te.getProperty("class.resource.loader.class"));
		Assert.assertEquals("class", te.getProperty("resource.loader"));
		
	}

	@Test
	public void testInitReader() {
		TemplateEngine te = new VelocityTemplateEngine();
		StringReader sr = new StringReader("resource.loader=file");
		te.init(sr);
		Assert.assertEquals("file",te.getProperty("resource.loader"));
	}

}
