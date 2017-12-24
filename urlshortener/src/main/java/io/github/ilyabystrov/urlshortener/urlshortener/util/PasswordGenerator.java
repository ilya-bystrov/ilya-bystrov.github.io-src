package io.github.ilyabystrov.urlshortener.urlshortener.util;

import java.security.SecureRandom;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PasswordGenerator {

  private static SecureRandom random = new SecureRandom();
  private static char[] dictionary = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

  public static String generatePassword() {

    Set<Character> chars = new LinkedHashSet<>();
    while (chars.size() < 8) {
      chars.add(dictionary[random.nextInt(dictionary.length)]);
    }
    return chars.stream().map(Object::toString).collect(Collectors.joining());
  }
}
