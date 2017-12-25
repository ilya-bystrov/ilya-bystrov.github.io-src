package io.github.ilyabystrov.urlshortener.util;

import org.junit.Test;

import java.util.Arrays;

import static io.github.ilyabystrov.urlshortener.util.Utils.SimpleBase62Encoder.decode;
import static io.github.ilyabystrov.urlshortener.util.Utils.SimpleBase62Encoder.encode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBase62EncoderTest {

  @Test
  public void testEncodeDecode() {
    for(Long l: Arrays.asList(1l, 2l, 61l, 62l, 63l, 123l, 124l, 125l, 1000000l, Long.MAX_VALUE)) {
      assertThat(decode(encode(l)), is(l));
    }
  }
}