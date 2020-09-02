/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Matteo
 */
public class WordTargetLayout {
    private final List<String> words;
        private final List<List<String>> wordsInCircle = List.of(null, null, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
        private final List<List<Double>> fracsInCircle = List.of(null, null, new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>());
        private final List<List<Integer>> anglesOfSeparators = List.of(null, null, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>());
        private final List<List<Integer>> anglesOfWords = List.of(null, null, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>());
        private final List<Integer> startingAngles = List.of(0, 0, 0, 0, 0, 0);
        private final List<Double> minFracBetweenWords = List.of(0.0, 0.0, 20.0 / 360.0, 12.0 / 360.0, 10.0 / 360.0, 6.0 / 360.0);

        public List<String> getAllWords()
        {
            return Collections.unmodifiableList(words);
        }

        public List<String> getWordsInCircle(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 5");
            }
            List<String> wordList = wordsInCircle.get(circle);
            return Collections.unmodifiableList(wordList);
        }

        public List<Double> getFracsInCircle(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 5");
            }
            List<Double> fractionList = fracsInCircle.get(circle);
            return Collections.unmodifiableList(fractionList);
        }

        public int getStartingAngle(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 5");
            }
            return startingAngles.get(circle);
        }

        public void setStartingAngle(int circle, int startingAngle)
        {
            if (circle < 2 || circle > 4)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 4");
            }
            startingAngles.add(circle, startingAngle);
        }

        public List<Integer> getAnglesOfSeparators(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 5");
            }
            List<Integer> angOfSepList = anglesOfSeparators.get(circle);
            return Collections.unmodifiableList(angOfSepList);
        }
        public List<Integer> getAnglesOfWords(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 5");
            }
            List<Integer> angOfWordsList = anglesOfWords.get(circle);
            return Collections.unmodifiableList(angOfWordsList);
        }
        
        public Double getMinFracBetweenWords(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new IllegalArgumentException("Circle must be between 2 and 5");
            }
            return minFracBetweenWords.get(circle);
        }

        public void calculateAnglesInCircle()
        {
            for (int circle = 2; circle < 6; circle++)
            {
                anglesOfSeparators.get(circle).clear();
                anglesOfWords.get(circle).clear();

                double spaceBetweenWords = (1 - getFracsInCircle(circle).stream().mapToDouble(Double::doubleValue).sum()) / getFracsInCircle(circle).size();

                if (spaceBetweenWords < getMinFracBetweenWords(circle))
                {
                    throw new RuntimeException("Words do not fit in the circle");
                }

                int startingAngleDegrees;
                if (circle == 5)
                {
                    startingAngleDegrees = 45 - (int)(360 * (getFracsInCircle(circle).get(0) + spaceBetweenWords) / 2);
                }
                else
                {
                    startingAngleDegrees = getStartingAngle(circle);
                }

                double circleSoFar = 0.0;
                for (int i = 0; i < getWordsInCircle(circle).size(); i++)
                {
                    anglesOfSeparators.get(circle).add((startingAngleDegrees + (int)(circleSoFar * 360) ) % 360);
                    circleSoFar += spaceBetweenWords / 2;
                    anglesOfWords.get(circle).add((startingAngleDegrees + (int)(circleSoFar * 360)) % 360);
                    circleSoFar += getFracsInCircle(circle).get(i);
                    circleSoFar += spaceBetweenWords / 2;
                }
            }
        }

        public String getWordInCenter() 
        {
            return words.get(words.size() - 1);
        }

        public WordTargetLayout(List<String> words)
        {
            this.words = words;
            wordsInCircle.get(5).add(words.get(0));
            fracsInCircle.get(5).add(Renderer.fracForWord(words.get(0), 5));
        }

        public void assignWord(String word, int circle)
        {
            if (word.equals(this.words.get(0)) || word.equals(this.getWordInCenter()))
            {
                throw new IllegalArgumentException("The first and last word cannot be assigned to a different circle");
            }
            if (circle <= 1 || circle > 5)
            {
                throw new IllegalArgumentException("Words can only be assigned to circles 2-5");
            }
            if (!this.words.contains(word))
            {
                throw new IllegalArgumentException("This word is not in the list of words");
            }
            for(int i = 2; i <= 5; i++)
            {
                if(this.getWordsInCircle(i).contains(word))
                {
                    throw new IllegalArgumentException("This word has already been assigned to a circle");
                }
            }
            wordsInCircle.get(circle).add(word);
            fracsInCircle.get(circle).add(Renderer.fracForWord(word, circle));
        }

        public void removeWord(String word, int circle)
        {
            if (word.equals(this.words.get(0)) || word.equals(this.getWordInCenter()))
            {
                throw new IllegalArgumentException("The first and last word cannot be assigned to a different circle");
            }
            if (circle <= 1 || circle > 5)
            {
                throw new IllegalArgumentException("Words can only be assigned to circles 2-5");
            }
            if (!this.words.contains(word))
            {
                throw new IllegalArgumentException("This word is not in the list of words");
            }
            int wordPosition = wordsInCircle.get(circle).indexOf(word);
            wordsInCircle.get(circle).remove(wordPosition);
            fracsInCircle.get(circle).remove(wordPosition);
        }

        public double getEmptySpace(int circle)
        {
            if (circle <= 1 || circle > 5)
            {
                throw new IllegalArgumentException("Invalid input");
            }
            return 1 - getFracsInCircle(circle).stream().mapToDouble(Double::doubleValue).sum() - (minFracBetweenWords.get(circle) * wordsInCircle.get(circle).size());
        }

        public double getRescaledEmptySpace(int circle)
        {
            double scaleFactor = minFracBetweenWords.get(circle) / minFracBetweenWords.get(5);
            return getEmptySpace(circle) * scaleFactor;
        }

        public boolean isCircleFull(int circle)
        {
            if(getEmptySpace(circle) < getMinFracBetweenWords(circle))
            {
                return true;
            }

            return false;
        }

        public Integer findWord(String word)
        {
            if(word.equals(this.getWordInCenter()))
            {
                return 1;
            }
            for(int i = 2; i <= 5; i++)
            {
                if (this.wordsInCircle.get(i).contains(word))
                {
                    return i;
                }
            }
            return null;
        }


        public String getNextWord(String word)
        {
            if(this.words.contains(word) == false)
            {
                throw new IllegalArgumentException("Word is not in the list of words");
            }
            int position = this.words.indexOf(word);
            if(position == words.size() - 1)
            {
                return null;
            }
            position++;
            return words.get(position);
        }
        public String getPreviousWord(String word)
        {
            if (this.words.contains(word) == false)
            {
                throw new IllegalArgumentException("Word is not in the list of words");
            }
            int position = this.words.indexOf(word);
            if (position == 0)
            {
                return null;
            }
            position--;
            return words.get(position);
        }

        public Set<String> getUnassignedWords()
        {
            Set<String> unassigned = new HashSet<>(words.subList(0, words.size() - 1));
            unassigned.removeAll(wordsInCircle.get(2));
            unassigned.removeAll(wordsInCircle.get(3));
            unassigned.removeAll(wordsInCircle.get(4));
            unassigned.removeAll(wordsInCircle.get(5));
            return unassigned;
        }

        private int[] weights = new int[] { 3, 5, 7, 10 };

        private int pickCircle(Random rand, int circleToAvoid)
        {
            int weightSum = IntStream.of(weights).sum();
            while (true) {
                int i = rand.nextInt(weightSum);
                int circle = 0;
                while (i >= weights[circle])
                {
                    i -= weights[circle];
                    circle++;
                }
                circle += 2;
                if (circle != circleToAvoid)
                {
                    return circle;
                }
            }
        }

        public void distributeRandomly(Random rand)
        {
            Set<String> words = getUnassignedWords();

            int previousCircle = -1;
            for(String word : words)
            {
                int circle = pickCircle(rand, previousCircle);
                assignWord(word, circle);
                previousCircle = circle;
            }
        }

        public void equilibrateOnce(Random rand)
        {
            int originCircle = 0;
            int destinationCircle = 0;
            for (int circle = 2; circle < 6; circle++)
            {
                if (getRescaledEmptySpace(circle) < 0)
                {
                    if (originCircle == 0 || getRescaledEmptySpace(circle) < getRescaledEmptySpace(originCircle))
                    {
                        originCircle = circle;
                    }
                }
                if (getRescaledEmptySpace(circle) > 0)
                {
                    if (destinationCircle == 0 || getRescaledEmptySpace(circle) > getRescaledEmptySpace(destinationCircle))
                    {
                        destinationCircle = circle;
                    }
                }
            }
            if (originCircle != 0 && destinationCircle != 0)
            {
                int min = originCircle == 5 ? 1 : 0;
                String word = getWordsInCircle(originCircle).get(min + rand.nextInt(getWordsInCircle(originCircle).size() - min));
                removeWord(word, originCircle);
                assignWord(word, destinationCircle);
            }
        }

        public boolean isAnyCircleOverflowing()
        {
            for (int circle = 2; circle < 6; circle++)
            {
                if (getRescaledEmptySpace(circle) < 0)
                {
                    return true;
                }
            }
            return false;
        }

        public boolean isAllCirclesOverflowing()
        {
            for (int circle = 2; circle < 6; circle++)
            {
                if (getRescaledEmptySpace(circle) > 0)
                {
                    return false;
                }
            }
            return true;
        }

        public boolean isAngleBetweenCiclesGood(int extCircle, int intCircle)
        {
            List<Integer> all = new ArrayList<>();
            all.addAll(getAnglesOfSeparators(extCircle));
            all.addAll(getAnglesOfSeparators(intCircle));
            all.sort(null);
            for (int i = 0; i < all.size() - 1; i++)
            {
                if (Math.abs(all.get(i+1)-all.get(i)) < 5)
                {
                    return false;
                }
            }
            if (Math.abs(360 + all.get(0) - all.get(all.size() - 1)) % 360 < 5)
            {
                return false;
            }
            return true;
        }

        public void shuffleCircle(int circle, Random rand)
        {
            int firstElement = circle != 5 ? 0 : 1;
            int n = wordsInCircle.get(circle).size();
            while (n > firstElement + 1)
            {
                n--;
                int k = rand.nextInt(n + 1 - firstElement) + firstElement;
                String word = wordsInCircle.get(circle).get(k);
                wordsInCircle.get(circle).set(k, wordsInCircle.get(circle).get(n));
                wordsInCircle.get(circle).set(n, word);
                double frac = fracsInCircle.get(circle).get(k);
                fracsInCircle.get(circle).set(k, fracsInCircle.get(circle).get(n));
                fracsInCircle.get(circle).set(n, frac);
            }
        }

        public void doLayout(Random rand)
        {
            distributeRandomly(rand);
            while (isAnyCircleOverflowing())
            {
                equilibrateOnce(rand);
                if (isAllCirclesOverflowing())
                {
                    return;
                }
            }
            for (int circle = 2; circle < 6; circle++) {
                shuffleCircle(circle, rand);
            }
            for (int circle = 2; circle < 5; circle++)
            {
                setStartingAngle(circle, rand.nextInt(60) - 30);
            }
            calculateAnglesInCircle();
            for (int circle = 4; circle >= 2; circle--)
            {
                while (!isAngleBetweenCiclesGood(circle + 1, circle))
                {
                    setStartingAngle(circle, getStartingAngle(circle) + 1);
                    calculateAnglesInCircle();
                }
            }
            return;
        }
}
