/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Matteo
 */
public class Chain {



    private Word initialWord;
    private final List<Link> links = new ArrayList<>();

    public Chain(Word initialWord) {
        this.initialWord = initialWord;
    }
    
    public static Chain of(Link firstLink, Link... links) {
        Chain chain = new Chain(firstLink.getWordA());
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

    /**
     * The size of the chain in number of words.
     * 
     * @return the number of words; cannot be less than 1
     */
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

    public void add(Chain newChain) {
        if (!newChain.getInitialWord().equals(getFinalWord())) {
            throw new IllegalArgumentException(this + " is not a valid chain with " + newChain);
        }

        links.addAll(newChain.links);
    }

    public void prepend(Link newLink) {
        links.add(0, newLink);
        initialWord = newLink.getWordA();
    }

    public void prepend(Chain newChain) {
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
        for (int i = 0; i < links.size(); i++) {
            links.set(i, links.get(i).reverse());
        }
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

    public void serialize(BufferedWriter writer) throws IOException{
        List<Link> storedLinks = new ArrayList<>();
        storedLinks = this.links();
        for (Link link : storedLinks) {
            writer.write(link.getWordA().toString());
            writer.write(" ");
            writer.write(link.getWordB().toString());
            writer.write(" ");
            writer.write(link.getType().toString());
            writer.write("\n");
        }
        writer.flush();
    }
    
    public static Chain deserialize(BufferedReader reader) throws IOException{
        List<Link> links = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            links.add(new Link(new Word(tokens[0]), new Word(tokens[1]), LinkType.valueOf(tokens[2])));
        }
        Chain chain = Chain.of(links.get(0));
        for (int i = 1; i < links.size(); i++) {
            chain.add(links.get(i));
        }

        return chain;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.initialWord);
        hash = 11 * hash + Objects.hashCode(this.links);
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
        final Chain other = (Chain) obj;
        if (!Objects.equals(this.initialWord, other.initialWord)) {
            return false;
        }
        if (!Objects.equals(this.links, other.links)) {
            return false;
        }
        return true;
    }
    
}

