package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Doer {

  @Autowired
  private JdbcTemplate mysqlJdbcTemplate;

  @Autowired
  private JdbcTemplate postgresJdbcTemplate;

  @PostConstruct
  public void execute() {
    System.out.println("MySQL: time zone name count = " + mysqlJdbcTemplate.queryForList("select count(*) from mysql.time_zone_name"));
    System.out.println("PostgreSQL: time zone name count = " +postgresJdbcTemplate.queryForList("select count(1) from pg_timezone_names"));
  }
}
