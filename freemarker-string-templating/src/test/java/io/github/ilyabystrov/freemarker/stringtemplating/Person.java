package io.github.ilyabystrov.freemarker.stringtemplating;

/**
 *
 * @author Ilya Bystrov <ilya.bystrov at outlook.com>
 */
public class Person {
  
  private final String firstName;
  private final String lastName;

  public Person(String name, String lastName) {
    this.firstName = name;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
