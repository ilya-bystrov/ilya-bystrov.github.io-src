/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.ilyabystrov.demo.restmoneytransferservice;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class AppTest extends JerseyTest {

  private static ServiceRegistry registry;

  public AppTest() {
  }

  @Override
  protected Application configure() {
    return App.createResourceConfig();
  }

  /**
   * Test of main method, of class App.
   */
  @Test
  public void testMain() {
//    System.out.println("main");
//    String[] args = null;
//    App.main(args);
//     TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
  }
  
}
