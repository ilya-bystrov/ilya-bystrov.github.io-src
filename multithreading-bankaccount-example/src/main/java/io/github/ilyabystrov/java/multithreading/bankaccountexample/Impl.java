package io.github.ilyabystrov.java.multithreading.bankaccountexample;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Impl {
  
  AtomicReference<BigInteger[]> lastTwoValues = new AtomicReference<>(new BigInteger[] {
    BigInteger.ZERO, BigInteger.ONE
  });
  
  public BigInteger next() {
    
    BigInteger[] state;
    BigInteger[] newState;
    do {
      state = lastTwoValues.get();
      newState = new BigInteger[] {
        state[1], state[0].add(state[1])
      };
    } while (!lastTwoValues.compareAndSet(state, newState));
    return newState[1];
  }
  
  public static void main(String[] args) {
    Impl impl = new Impl();
    for (int i = 0; i < 10; i++) {
      System.out.println("impl.next() = " + impl.next());
    }
  }
}
