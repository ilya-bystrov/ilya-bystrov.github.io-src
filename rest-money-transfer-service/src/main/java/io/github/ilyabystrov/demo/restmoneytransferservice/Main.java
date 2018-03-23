package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.service.TransferManager;
import java.math.BigDecimal;
import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class Main {
  
    static {
        BasicConfigurator.configure();
//        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Jersey"));
    }

    public static void main(String[] args) {
//        Configuration configuration = new Configuration();
//        configuration.setProperty("hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext");
//        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost/test");
//        configuration.setProperty("hibernate.connection.username", "test");
//        configuration.setProperty("hibernate.connection.password", "test");
//        configuration.setProperty("hibernate.dialect.password", "org.hibernate.dialect.PostgreSQL9Dialect");
//        configuration.setProperty("hibernate.show_sql", "true");
//        configuration.setProperty("hibernate.format_sql", "true");
//        configuration.setProperty("hibernate.use_sql_comments", "true");
//        configuration.addAnnotatedClass(T.class);
//                new StandardServiceRegistryBuilder().configure(configurationFile)
//        StandardServiceRegistry registry
//                = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        StandardServiceRegistry registry
                = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).addAnnotatedClass(Account.class).buildMetadata();
        SessionFactory factory = metadata.getSessionFactoryBuilder().build();
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.beginTransaction();
//        {
//            T t = new T();
//            t.setD(new Date());
//            t.setTs(new Date());
//            t.setTstz(new Date());
//        session.save(t);
//        }
//        Criteria c = session.createCriteria(T.class);
//        Query q = session.createQuery(
//        "select t from t t");
//        List<T> list = c.list();
//        System.out.println("---------------------------------------");
//        System.out.println("=======================================");
//        System.out.printf("%s%n%+.1f%n",
//                TimeZone.getDefault(), TimeZone.getDefault().getRawOffset() / 3600000f);
//        for (T t : list) {
//            System.out.printf("%s%n", t);
//        }
//        System.out.println("=======================================");
//        System.out.println("---------------------------------------");
//        tx.commit();
        new TransferManager(factory).transferMoney(1l, 3l, new BigDecimal(10000));
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
