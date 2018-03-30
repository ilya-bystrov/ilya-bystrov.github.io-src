package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.resource.ExceptionLogger;
import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.resource.AccountNotFoundExceptionMapper;
import io.github.ilyabystrov.demo.restmoneytransferservice.resource.AccountResource;
import io.github.ilyabystrov.demo.restmoneytransferservice.resource.TransferExceptionMapper;
import io.github.ilyabystrov.demo.restmoneytransferservice.service.AccountServce;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class App {
  
  private static final URI BASE_URI = URI.create("http://localhost:8080/");
  
  private static ServiceRegistry createHibernateServiceRegistry() {
        return new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
  }

  private static SessionFactory createHibernateSessionFactory(ServiceRegistry registry) {
      Metadata metadata = new MetadataSources(registry).addAnnotatedClass(Account.class).buildMetadata();
      return metadata.getSessionFactoryBuilder().build();
  }

  public static ResourceConfig createResourceConfig() {

      final ServiceRegistry registry = createHibernateServiceRegistry();

      ResourceConfig resourceConfig = new ResourceConfig();
      resourceConfig.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true); 
      resourceConfig.register(JacksonFeature.class);
      resourceConfig.register(ExceptionLogger.class);
      resourceConfig.register(AccountNotFoundExceptionMapper.class);
      resourceConfig.register(TransferExceptionMapper.class);
      resourceConfig.register(AccountResource.class);
      resourceConfig.registerInstances(new AbstractBinder() {
        @Override
        protected void configure() {
          bind(createHibernateSessionFactory(registry)).to(SessionFactory.class).in(Singleton.class);
          bind(AccountServce.class).to(AccountServce.class).in(Singleton.class);
        }
      });
      resourceConfig.registerInstances(new ContainerLifecycleListener() {
        @Override
        public void onStartup(Container container) {
        }

        @Override
        public void onReload(Container container) {
        }

        @Override
        public void onShutdown(Container container) {
          StandardServiceRegistryBuilder.destroy(registry);
        }
      });
      return resourceConfig;
  }

  public static void main(String[] args) {
    try {

      final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createResourceConfig(), false);
      
      Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));      
      server.start();
      
      System.out.println("Application started");
      System.out.println("Stop the application using CTRL+C");
      
      Thread.currentThread().join();
    } catch (IOException | InterruptedException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}