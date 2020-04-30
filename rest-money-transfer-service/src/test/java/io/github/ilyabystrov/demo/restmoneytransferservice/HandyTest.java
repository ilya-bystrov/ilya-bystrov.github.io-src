package io.github.ilyabystrov.demo.restmoneytransferservice;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HandyTest {

    @Test
    public void testAlphaLib() {
        System.out.println("analyzeString(\"\") = " + analyzeString(""));
        System.out.println("analyzeString(\"a\") = " + analyzeString("a"));
        System.out.println("analyzeString(\"aa\") = " + analyzeString("aa"));
        System.out.println("analyzeString(\"aabb\") = " + analyzeString("aabb"));
        System.out.println("analyzeString(\"aabb\") = " + analyzeString("aabbcc"));
    }

    /**
     *
     * @param stream
     * @return
     */
    private Map<Character, Integer> analyzeString(Stream<Character> stream) { // TOOD: rename
        if(stream == null) {
            throw new IllegalArgumentException("Parameter is null");
        }
//        return str.chars().mapToObj(i -> (char) i).collect(groupingBy(identity(), reducing(0, e -> 1, Integer::sum)));
        return stream.collect(groupingBy(identity(), reducing(0, e -> 1, Integer::sum)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfNull() {
//       analyzeString(null);
    }

//    @Test
//    public void testIfEmpty() {
//        assertThat(analyzeString(""), is(emptyMap()));
//    }
//    @Test
//    public void testIfNormal() {
//        assertThat(analyzeString("a"), is(Collections.singleton()));
//    }
    private Map<Character, Integer> analyzeString(Iterator<Character> iter) { // TOOD: rename
//        if(stream == null) {
//            throw new IllegalArgumentException("Parameter is null");
//        }
//        Spliterators.
//        return str.chars().mapToObj(i -> (char) i).collect(groupingBy(identity(), reducing(0, e -> 1, Integer::sum)));
//        return stream.collect(groupingBy(identity(), reducing(0, e -> 1, Integer::sum)));
        return null;
    }
}
