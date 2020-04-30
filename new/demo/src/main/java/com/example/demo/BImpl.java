package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BImpl implements B {

    @Autowired
    private A a;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BImpl{");
        sb.append("a=").append(a);
        sb.append('}');
        return sb.toString();
    }
}
