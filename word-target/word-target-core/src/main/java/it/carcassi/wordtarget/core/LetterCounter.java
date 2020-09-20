/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author carcassi
 */
public class LetterCounter {

    private Map<Character, Integer> counter = new HashMap<>();

    public void addLetter(char letter) {
        if (counter.containsKey(letter)) {
            counter.put(letter, counter.get(letter) + 1);
        } else {
            counter.put(letter, 1);
        }
    }

    public int countFor(char letter) {
        if (counter.containsKey(letter)) {
            return counter.get(letter);
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.counter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LetterCounter other = (LetterCounter) obj;
        if (!Objects.equals(this.counter, other.counter)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return counter.toString();
    }

    public BigInteger toBigInteger() {
        BigInteger index = BigInteger.ONE;
        for (int j = 0; j < 26; j++) {
            long factor = pow(CHAR_TO_PRIME[j], countFor((char) ('A' + j)));
            index = index.multiply(BigInteger.valueOf(factor));
        }
        return index;
    }
    
    public static LetterCounter countLetters(String text) {
        return countLetters(Word.of(text));
    }
    
    public static LetterCounter countLetters(Word word) {
        LetterCounter counter = new LetterCounter();
        for (char c : word.getText().toCharArray()) {
            counter.addLetter(c);
        }
        return counter;
    }
    
    public static final int[] CHAR_TO_PRIME = charToPrime();
    private static int[] charToPrime() {
        int[] primes = new int[26];
        int i = 0;
        int n = 2;
        while (i < 26) {
            boolean isPrime = true;
            for (int j = 2; j <= n / 2; j++) {
                // If divisible, not prime
                if (n % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primes[i] = n;
                i++;
            }
            n++;
        }

        return primes;
    }
    
    public static long pow(long a, int b) {
        if (b < 0) {
            throw new IllegalArgumentException("The exponent can't be negative");
        }
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return a;
        }
        long result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }
    
}
