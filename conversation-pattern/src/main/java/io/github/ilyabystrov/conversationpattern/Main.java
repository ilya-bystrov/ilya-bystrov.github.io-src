package io.github.ilyabystrov.conversationpattern;

//import org.apache.log4j.ConsoleAppender;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PatternLayout;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class Main {
  
  static {
    Logger.().getLoggerRepository().resetConfiguration();
    
    ConsoleAppender console = new ConsoleAppender(); //create appender
    //configure the appender
//    String PATTERN = "%d [%p|%c|%C{1}] %m%n";
    String PATTERN = "TEST %d{yyyy-MM-dd HH:mm:ss} %X{AWSRequestId} %-5p %c{1}:%L - %m%n";
    console.setLayout(new PatternLayout(PATTERN));
    console.setThreshold(Level.ALL);
    console.activateOptions();
    //add appender to any Logger (here is root)
    Logger.getRootLogger().addAppender(console);
    
  }
  
  static Logger logger = Logger.getLogger(Main.class.getName());
  
  public static void main(String[] args) {
    
    logger.debug("hi!");
  }
  
}
