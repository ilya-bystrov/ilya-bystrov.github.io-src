package com.example.demo;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
  private EntityManagerFactoryBuilder entityManagerFactoryBuilder;

	@Bean
	@ConfigurationProperties("mysql-datasource.datasource")
  public DataSource mysqlDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean
  @ConfigurationProperties("postgres-datasource.datasource")
  public DataSource postgresDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(autowire = Autowire.BY_NAME)
  public JdbcTemplate mysqlJdbcTemplate() {
    return new JdbcTemplate(mysqlDataSource());
  }

  @Bean(autowire = Autowire.BY_NAME)
  public JdbcTemplate postgresJdbcTemplate() {
    return new JdbcTemplate(postgresDataSource());
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
    return entityManagerFactoryBuilder
        .dataSource(mysqlDataSource())
        .packages(Doer.class) //required, if case of absence: java.lang.IllegalArgumentException: No persistence unit with name 'mysql' found
        .persistenceUnit("mysql")
        .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory() {
    return entityManagerFactoryBuilder
        .dataSource(postgresDataSource())
        .packages(Doer.class) //required, if case of absence: java.lang.IllegalArgumentException: No persistence unit with name 'mysql' found
        .persistenceUnit("postgres")
        .build();
  }

  @Bean
  public PlatformTransactionManager mysqlTransactionManager(){
   return new JpaTransactionManager(mysqlEntityManagerFactory().getObject());
  }

  @Bean
  public PlatformTransactionManager postgresTransactionManager(){
    return new JpaTransactionManager(mysqlEntityManagerFactory().getObject());
  }
}
