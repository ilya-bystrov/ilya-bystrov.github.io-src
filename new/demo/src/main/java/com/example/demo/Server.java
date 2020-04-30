package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Server {

    @Autowired
    private A a;

    @PostConstruct
    private void printInfo() {
        System.out.println("a = " + a);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Server{");
        sb.append("a=").append(a);
        sb.append('}');
        return sb.toString();
    }
}
