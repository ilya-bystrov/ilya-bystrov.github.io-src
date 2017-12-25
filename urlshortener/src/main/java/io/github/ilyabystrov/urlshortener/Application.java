package io.github.ilyabystrov.urlshortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Map<String, String> hostAndPort() {
    return new HashMap<>();
  }

  @Bean
  public ApplicationListener<EmbeddedServletContainerInitializedEvent> embeddedServletContainerListener(@Value("#{hostAndPort}") Map<String, String> map) {
    return new ApplicationListener<EmbeddedServletContainerInitializedEvent>() {

      @Override
      public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int port = event.getEmbeddedServletContainer().getPort();
        map.put("port", Integer.toString(port));
        map.put("host", "localhost"); // use 'localhost' for simplicity
      }
    };
  }

  @Bean
  public WebSecurityConfigurer<WebSecurity> webSecurityConfigurer() {
    return new WebSecurityConfigurerAdapter() {

      @Override
      public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/config/account", "/*");
      }

      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated().and().httpBasic();
      }
    };
  }
}
