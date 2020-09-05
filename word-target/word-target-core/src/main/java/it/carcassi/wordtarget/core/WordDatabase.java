/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Matteo
 */
public class WordDatabase {

    private final Set<Word> words = new HashSet<>();
    private final Set<Link> links = new HashSet<>();
    private Map<Word, Set<Link>> linksByWord = new HashMap<>();

    public void addWord(String word) {
        addWord(new Word(word));
    }

    public void addWord(Word word) {
        if (words.contains(word)) {
            return;
        }
        words.add(word);

        // Check if word can be linked to others
        for (Word other : words) {
            if (LinkTypes.isOneLetterChange(word.getText(), other.getText())) {
                Link link = new Link(word, other, LinkType.OneLetterChange);
                addLink(link);
            }
            if (LinkTypes.isOneLetterAddOrRemove(word.getText(), other.getText())) {
                Link link = new Link(word, other, LinkType.OneLetterAddOrRemove);
                addLink(link);
            }
            if (LinkTypes.isAnagram(word.getText(), other.getText())) {
                Link link = new Link(word, other, LinkType.Anagram);
                addLink(link);
            }
        }
    }

    public boolean containsWord(String word) {
        return containsWord(new Word(word));
    }

    public boolean containsWord(Word word) {
        return words.contains(word);
    }

    public boolean containsLink(String wordA, String wordB) {
        Word theWordA = new Word(wordA);
        Word theWordB = new Word(wordB);
        return containsLink(theWordA, theWordB);
    }

    public boolean containsLink(Word wordA, Word wordB) {
        return words.contains(wordA) && getLinksFor(wordA).stream().anyMatch((x) -> {
            return x.getWordB().equals(wordB);
        });
    }

    public Link getLinkBetween(String wordA, String wordB) {
        Word theWordA = new Word(wordA);
        Word theWordB = new Word(wordB);
        return getLinkBetween(theWordA, theWordB);
    }

    public Link getLinkBetween(Word wordA, Word wordB) {
        return words.contains(wordA) ? getLinksFor(wordA).stream()
                .filter((x) -> {
                    return x.getWordB().equals(wordB);
                })
                .findFirst()
                .orElse(null) : null;
    }

    public void addLink(Link link) {
        if (links.contains(link)) {
            return;
        }
        links.add(link);
        links.add(link.reverse());
        addLinkForWord(link.getWordA(), link);
        addLinkForWord(link.getWordB(), link.reverse());
    }

    private void addLinkForWord(Word word, Link link) {
        if (linksByWord.containsKey(word)) {
            linksByWord.get(word).add(link);
        } else {
            Set<Link> newLinks = new HashSet<>();
            newLinks.add(link);
            linksByWord.put(word, newLinks);
        }
    }

    public void addWords(List<Word> words) {
        for (Word word : words) {
            addWord(word);
        }
    }

    public Set<Word> getAllWords() {
        return words;
    }

    public Set<Link> getLinksFor(Word word) {
        if (linksByWord.containsKey(word)) {
            return linksByWord.get(word);
        } else {
            return new HashSet<>();
        }
    }

    // TODO: Add tests
    public void removeLink(Link link) {
        // TODO: we are not checking whether the link is not there

        if (linksByWord.containsKey(link.getWordA())) {
            linksByWord.get(link.getWordA()).remove(link);
        } else {
            throw new IllegalArgumentException("Word " + link.getWordA() + " not present");
        }

        if (linksByWord.containsKey(link.getWordB())) {
            linksByWord.get(link.getWordB()).remove(link.reverse());
        } else {
            throw new IllegalArgumentException("Word " + link.getWordB() + " not present");
        }
    }

    // TODO: add tests?
    public void removeWord(Word word) {
        for (Link link : new ArrayList<>(getLinksFor(word))) {
            removeLink(link);
        }
        words.remove(word);
    }

    public static WordDatabase deserialize(BufferedReader reader) throws IOException {
        WordDatabase db = new WordDatabase();
        reader.readLine();
        for (String word : reader.readLine().split(",")) {
            db.addWord(word.trim());
        }

        reader.readLine();
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            db.addLink(new Link(new Word(tokens[0]), new Word(tokens[1]), LinkType.valueOf(tokens[2])));
        }
        return db;
    }

    public void serialize(BufferedWriter writer) throws IOException {
        writer.write("Words:\n");
        List<Word> sortedWords = words.stream().sorted(Comparator.comparing(Word::getText)).collect(Collectors.toList());
        Boolean addComma = false;
        for (Word word : sortedWords) {
            if (addComma) {
                writer.write(", ");
            }

            writer.write(word.getText());
            addComma = true;
        }
        writer.write("\n");
        writer.write("\n");
        writer.write("Links:\n");

        for (Word word : sortedWords) {
            var wordLinks = getLinksFor(word);
            var sortedLinks = wordLinks.stream().sorted(Comparator.comparing((x) -> {
                return x.getWordB().getText();
            }))
                    .collect(Collectors.toList());
            for (Link link : sortedLinks) {
                writer.write(link.getWordA().getText());
                writer.write(" ");
                writer.write(link.getWordB().getText());
                writer.write(" ");
                writer.write(link.getType().toString());
                writer.write("\n");
            }
        }
        writer.flush();
    }

    public Chain findChain(Word start, Word end) {
        Chain chain = new Chain();
        boolean result = completeChain(chain, start, end);
        if (result) {
            return chain;
        } else {
            return null;
        }
    }

    private boolean completeChain(Chain chain, Word lastWord, Word end) {
        Set<Link> newLinks = getLinksFor(lastWord);
        for (Link link : newLinks) {
            Word newLastWord = link.getWordB();

            // Check if the new word is the last
            if (newLastWord.equals(end)) {
                chain.add(link);
                return true;
            }

            // Check the new word does not repeat
            if (chain.contains(newLastWord)) {
                continue;
            }

            // Try completing the chain
            chain.add(link);
            boolean result = completeChain(chain, newLastWord, end);
            if (result) {
                return true;
            }
            chain.removeLast();
        }

        return false;
    }
}
