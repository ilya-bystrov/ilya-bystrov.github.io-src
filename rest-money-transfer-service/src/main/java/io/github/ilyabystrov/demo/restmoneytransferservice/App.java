package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.resource.AccountResource;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/*
public class App {

private static final URI BASE_URI = URI.create("http://localhost:8080/cdi-webapp/");

public static void main(String[] args) {
try {
System.out.println("Jersey CDI Example App");

final Weld weld = new Weld();
weld.initialize();

final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createJaxRsApp(), false);
Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
@Override
public void run() {
server.shutdownNow();
weld.shutdown();
}
}));
server.start();

System.out.println(String.format("Application started.\nTry out %s%s\nStop the application using CTRL+C",
BASE_URI, "application.wadl"));

Thread.currentThread().join();
} catch (IOException | InterruptedException ex) {
Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
}

}

public static ResourceConfig createJaxRsApp() {
//        return new ResourceConfig(new MyApplication().getClasses());
return new ResourceConfig(HelloWorldResource.class);
}
}
*/
public class App {
  
  private static final URI BASE_URI = URI.create("http://localhost:8080/");
  public static final String ROOT_ACCOUNT_PATH = "account";
  
  public static void main(String[] args) {
    try {
      StandardServiceRegistry registry
          = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
      Metadata metadata = new MetadataSources(registry).addAnnotatedClass(Account.class).buildMetadata();
      final SessionFactory factory = metadata.getSessionFactoryBuilder().build();
      
      ResourceConfig resourceConfig = new ResourceConfig();
      resourceConfig.register(AccountResource.class);
      resourceConfig.registerInstances(new AbstractBinder() {
        @Override
        protected void configure() {
          bind(factory).to(SessionFactory.class).in(Singleton.class);
        }
      });

      final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        server.shutdown();
        StandardServiceRegistryBuilder.destroy(registry);
      }));
      
      server.start();
      
      System.out.println("Stop the application using CTRL+C");
      
      Thread.currentThread().join();
    } catch (IOException | InterruptedException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
//            System.out.println("Application started.\nTry out");
//            System.out.println(String.format("%s%s", BASE_URI, ROOT_HELLO_PATH));
//            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_COUNTER_PATH, "/request"));
//            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_COUNTER_PATH, "/application"));