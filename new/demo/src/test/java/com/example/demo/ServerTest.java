package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerTest {

    @TestConfiguration
    public static class MockOverrideConfig {

        @Bean
        @Primary
        public A AImpl() {
            return Mockito.mock(A.class);
        }

    }


        @Autowired
    private Server server;

    @Test
    public void test() {
        System.out.println("server = " + server);
    }

}