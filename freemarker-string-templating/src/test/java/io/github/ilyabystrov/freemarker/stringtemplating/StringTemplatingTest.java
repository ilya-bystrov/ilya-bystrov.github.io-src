package io.github.ilyabystrov.freemarker.stringtemplating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Ilya Bystrov <ilya.bystrov at outlook.com>
 */
public class StringTemplatingTest {
  
  public static class Person {
    
    private final String firstName;
    private final String lastName;
    
    public Person(String name, String login) {
      this.firstName = name;
      this.lastName = login;
    }
    
    public String getFirstName() {
      return firstName;
    }
    
    public String getLastName() {
      return lastName;
    }
  }

  @Test
  public void test() throws IOException, TemplateException {
    
    String templateStr="Hello, ${person.firstName} ${person.lastName}!";
    Map<String, Person> parameters = Collections.singletonMap("person", new Person("Ilya", "Bystrov"));

    Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
    configuration.setLogTemplateExceptions(false);
    Template t = new Template(null, new StringReader(templateStr), configuration);
    
    StringWriter out = new StringWriter();
    t.process(parameters, out);

    System.out.println("out = " + out);
  }
}
