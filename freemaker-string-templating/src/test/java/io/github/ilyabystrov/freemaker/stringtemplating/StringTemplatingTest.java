package io.github.ilyabystrov.freemaker.stringtemplating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ilya Bystrov <ilya.bystrov at outlook.com>
 */
public class StringTemplatingTest {
  
  @Test
  public void test() throws IOException, TemplateException {
    
    String templateStr="Hello, ${user.name} ${user.login}!";
    Template t = new Template("name", new StringReader(templateStr), new Configuration(Configuration.VERSION_2_3_26));
    
    StringWriter out = new StringWriter();
    t.process(Collections.singletonMap("user", new Person("Ilya Bystrov", "i")), out);
    
    System.out.println("out = " + out);
  }
  
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
}
