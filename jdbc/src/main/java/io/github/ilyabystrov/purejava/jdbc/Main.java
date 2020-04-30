package io.github.ilyabystrov.purejava.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class Main {
  
  public static void main(String args[]) throws Exception {
//    Class.forName("com.mysql.jdbc.Driver");
//    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root");
//    String url = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443?max_error_retries=20&connection_timeout=20000";
    String url = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443"
        + "?s3_staging_dir=s3://jrg-glue-operations/temp/"
//        + "&aws_credentials_provider_class=com.amazonaws.auth.DefaultAWSCredentialsProviderChain";
        + "&aws_credentials_provider_class=com.amazonaws.athena.jdbc.shaded.com.amazonaws.auth.DefaultAWSCredentialsProviderChain"
        +"&accessId=AKIAJHRC7QOLPRBYP6QQ"
        +"&secretKey=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"
        ;
    System.out.println(url);
    //here sonoo is database name, root is username and password
    try (Connection con = DriverManager.getConnection(url, "", "")) {
      //here sonoo is database name, root is username and password
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select 1");
      while(rs.next())
        System.out.println(rs.getString(1));
    }
  }
}
