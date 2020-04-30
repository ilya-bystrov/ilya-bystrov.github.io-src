package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AImpl implements A {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AImpl{");
        sb.append('}');
        return sb.toString();
    }
}
