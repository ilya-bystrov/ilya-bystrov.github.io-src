package io.github.ilyabystrov.urlshortener.urlshortener.util;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Utils {

  private static SecureRandom random = new SecureRandom();
  private static char[] dict62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

  private static int indexOf(char c) {
     if (c >= 'A' && c <= 'Z')  {
       return c - 'A';
     } else if (c >= 'a' && c <= 'z')  {
       return c - 'a' + 26;
      } else if (c >= '0' && c <= '9')  {
         return c - '0' + 52;
     } else {
       throw new IllegalArgumentException();
     }
  }

  public abstract static class PasswordGenerator {

    public static String generatePassword() {

      Set<Character> chars = new LinkedHashSet<>();
      while (chars.size() < 8) {
        chars.add(dict62[random.nextInt(dict62.length)]);
      }
      return chars.stream().map(Object::toString).collect(Collectors.joining());
    }
  }

  public abstract static class SimpleBase62Encoder {

    public static String encode(Long l) {

      if(l < 1) {
        throw new IllegalArgumentException();
      }

      List<Character> chars = new ArrayList<>();

      for(long dividend = l, remainder; dividend > 0; dividend /= 62) {
        remainder = dividend % 62;
        chars.add(dict62[(int) remainder]);
      }

      StringBuilder builder = new StringBuilder();
      for(ListIterator it = chars.listIterator(chars.size()); it.hasPrevious();) {
        builder.append(it.previous());
      }
      return builder.toString();
    }

    public static Long decode(String s) {

      if(s == null || s.isEmpty()) {
        throw new IllegalArgumentException();
      }

      long l = 0, m = 1;
      for(int i = s.length() - 1; i >= 0; i--, m *= 62) {
        l += indexOf(s.charAt(i)) * m;
      }

      return l;
    }
  }
}
