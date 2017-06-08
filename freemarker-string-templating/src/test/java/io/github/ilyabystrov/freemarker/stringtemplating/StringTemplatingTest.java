package io.github.ilyabystrov.freemarker.stringtemplating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Ilya Bystrov <ilya.bystrov at outlook.com>
 */
public class StringTemplatingTest {
  
  @Test
  public void test() throws IOException, TemplateException {
    
    String templateStr="Hello, ${person.firstName} ${person.lastName}!";
    Map<String, Person> parameters = Collections.singletonMap("person", new Person("Ilya", "Bystrov"));
    
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
    cfg.setLogTemplateExceptions(false);
    Template t = new Template(null, new StringReader(templateStr), cfg);
    
    StringWriter out = new StringWriter();
    t.process(parameters, out);
    
    Assert.assertEquals("Hello, Ilya Bystrov!", out.toString());
  }
}
