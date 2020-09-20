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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static it.carcassi.wordtarget.core.LetterCounter.*;

/**
 *
 * @author Matteo
 */
public class WordDatabase {

    private final Set<Word> words = new HashSet<>();
    private final Set<Link> links = new HashSet<>();
    private Map<Word, Set<Link>> linksByWord = new HashMap<>();
    public Map<BigInteger, Set<Word>> wordsByHist = new HashMap<>();

    public void addWord(Word word) {
        if (words.contains(word)) {
            return;
        }
        words.add(word);
        LetterCounter counter = LetterCounter.countLetters(word);
        BigInteger index = counter.toBigInteger();
        
        Set<Word> similarWords = wordsByHist.get(index);
        if (similarWords == null) {
            similarWords = new HashSet<>();
            wordsByHist.put(index, similarWords);
        }
        
        for (Word other : similarWords) {
            Link link = new Link(word, other, LinkType.Anagram);
            addLink(link);
        }
        
        similarWords.add(word);
        
        for (int i = 0; i < 26; i++) {
            // Checks for one letter add
            BigInteger otherIndex = index.multiply(BigInteger.valueOf(CHAR_TO_PRIME[i]));
            similarWords = wordsByHist.get(otherIndex);
            if (similarWords != null) {
                for (Word other : similarWords) {
                    if (LinkTypes.isOneLetterAdd(word.getText(), other.getText())) {
                        Link link = new Link(word, other, LinkType.OneLetterAddOrRemove);
                        addLink(link);
                    }
                }
            }
            
            // Checks for one letter remove
            if (counter.countFor((char) ('A' + i)) != 0) {
                otherIndex = index.divide(BigInteger.valueOf(CHAR_TO_PRIME[i]));
                similarWords = wordsByHist.get(otherIndex);
                if (similarWords != null) {
                    for (Word other : similarWords) {
                        if (LinkTypes.isOneLetterRemove(word.getText(), other.getText())) {
                            Link link = new Link(word, other, LinkType.OneLetterAddOrRemove);
                            addLink(link);
                        }
                    }
                }
            }
            
            // Check for one letter change
            if (counter.countFor((char) ('A' + i)) != 0) {
                otherIndex = index.divide(BigInteger.valueOf(CHAR_TO_PRIME[i]));
                for (int j = 0; j < 26; j++) {
                    BigInteger indexAdded = otherIndex.multiply(BigInteger.valueOf(CHAR_TO_PRIME[j]));
                    similarWords = wordsByHist.get(indexAdded);
                    if (similarWords != null) {
                        for (Word other : similarWords) {
                            if (LinkTypes.isOneLetterChange(word.getText(), other.getText())) {
                                Link link = new Link(word, other, LinkType.OneLetterChange);
                                addLink(link);
                            }
                        }
                    }
                }
            }
        }

        // Check if word can be linked to others
//        for (Word other : words) {
//            if (LinkTypes.isOneLetterChange(word.getText(), other.getText())) {
//                Link link = new Link(word, other, LinkType.OneLetterChange);
//                addLink(link);
//            }
//            if (LinkTypes.isOneLetterAddOrRemove(word.getText(), other.getText())) {
//                Link link = new Link(word, other, LinkType.OneLetterAddOrRemove);
//                addLink(link);
//            }
//            if (LinkTypes.isAnagram(word.getText(), other.getText())) {
//                Link link = new Link(word, other, LinkType.Anagram);
//                addLink(link);
//            }
//        }
    }

    public boolean containsWord(Word word) {
        return words.contains(word);
    }

    public boolean containsLink(Word wordA, Word wordB) {
        return words.contains(wordA) && getLinksFor(wordA).stream().anyMatch((x) -> {
            return x.getWordB().equals(wordB);
        });
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

    /**
     * Returns all the links that start with word but do not end with an
     * excluded word.
     * 
     * @param word the word that has to match wordA in the link
     * @param excluded the words that cannot be wordB in the link
     * @return the set of links that match the criteria
     */
    public Set<Link> getLinksFor(Word word, Collection<Word> excluded) {
        if (linksByWord.containsKey(word)) {
            return linksByWord.get(word).stream()
                    .filter(x -> { return !excluded.contains(x.getWordB()); })
                    .collect(Collectors.toSet());
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
            db.addWord(Word.of(word));
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
        List<Link> storedLinks = new ArrayList<>();
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
                if (!storedLinks.contains(link.reverse())) {
                    if (!link.getType().isAutomatic()) {
                        writer.write(link.getWordA().getText());
                        writer.write(" ");
                        writer.write(link.getWordB().getText());
                        writer.write(" ");
                        writer.write(link.getType().toString());
                        writer.write("\n");
                        storedLinks.add(link);
                    }
                }
            }
        }
        writer.flush();
    }

    public Chain findChain(Word start, Word end) {
        Chain chain = new Chain(start);
        boolean result = completeChain(chain, end);
        if (result) {
            return chain;
        } else {
            return null;
        }
    }

    private boolean completeChain(Chain chain, Word end) {
        Set<Link> newLinks = getLinksFor(chain.getFinalWord(), chain.words());
        for (Link link : newLinks) {
            Word newLastWord = link.getWordB();

            // Check if the new word is the last
            if (newLastWord.equals(end)) {
                chain.add(link);
                return true;
            }

            // Try completing the chain
            chain.add(link);
            boolean result = completeChain(chain, end);
            if (result) {
                return true;
            }
            chain.removeLast();
        }

        return false;
    }
}
