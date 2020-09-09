/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matteo
 */
public class NewChain {

    private Word initialWord;
    private final List<Link> links = new ArrayList<>();

    public NewChain(Word initialWord) {
        this.initialWord = initialWord;
    }
    
    public static NewChain of(Link firstLink, Link... links) {
        NewChain chain = new NewChain(firstLink.getWordA());
        chain.add(firstLink);
        for (Link link : links) {
            chain.add(link);
        }
        return chain;
    }
    
    public Word getInitialWord() {
        return initialWord;
    }
    
    public Word getFinalWord() {
        if (links.isEmpty()) {
            return initialWord;
        } else {
            return links.get(links.size() - 1).getWordB();
        }
    }
    
    public List<Link> links() {
        return Collections.unmodifiableList(links);
    }
    
    public List<Word> words() {
        return new AbstractList<Word>() {
            @Override
            public Word get(int index) {
                if (index == 0) {
                    return initialWord;
                } else {
                    return links.get(index - 1).getWordB();
                }
            }

            @Override
            public int size() {
                return links.size() + 1;
            }
        };
    }

    public int size() {
        return links.size() + 1;
    }

    public void add(Link newLink) {
        if (links.size() == 0 && !newLink.getWordA().equals(initialWord)) {
            throw new IllegalArgumentException(initialWord + " is not a valid link with " + newLink);
        }
        if (links.size() != 0 && !(links.get(links.size() - 1).getWordB().equals(newLink.getWordA()))) {
            throw new IllegalArgumentException(links.get(links.size() - 1) + " is not a valid link with " + newLink);
        }
        if (words().contains(newLink.getWordB())) {
            throw new IllegalArgumentException(newLink.getWordB() + " is already found in the chain");
        }
        links.add(newLink);
    }

    public void add(NewChain newChain) {
        if (!newChain.getInitialWord().equals(getFinalWord())) {
            throw new IllegalArgumentException(this + " is not a valid chain with " + newChain);
        }

        links.addAll(newChain.links);
    }

    public void prepend(Link newLink) {
        links.add(0, newLink);
        initialWord = newLink.getWordA();
    }

    public void prepend(NewChain newChain) {
        if (newChain.size() == 0) {
            return;
        }
        if (this.size() != 0 && this.links.get(0).getWordA() != newChain.links.get(newChain.links.size() - 1).getWordB()) {
            throw new IllegalArgumentException(this.links.get(0) + " is not a valid link with " + newChain.links.get(newChain.links.size() - 1));
        }
        links.addAll(0, newChain.links);
    }

    public void reverse() {
        Collections.reverse(links);
    }

    @Override
    public String toString() {
        return getInitialWord() + " -> " + getFinalWord() + " [" + words().size() + "]";
    }

    public void removeFirst() {
        if (this.links.size() == 0) {
            throw new IllegalArgumentException("Cannot remove elements from an empty chain");
        }
        this.links.remove(0);

    }

    public void removeLast() {
        if (this.links.size() == 0) {
            throw new IllegalArgumentException("Cannot remove elements from an empty chain");
        }
        this.links.remove(links.size() - 1);
    }
}
