package chapter9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/24
 * Time:10:36
 */
public class WordLadders {
    public static List<String> readWords(BufferedReader in) throws IOException {
        String oneLine;
        List<String> list = new ArrayList<>();
        while ((oneLine = in.readLine()) != null) {
            list.add(oneLine);
        }
        return list;
    }

    /**
     * Returns true is word1 and word2 are the same length
     * and differ in only one character.
     */
    public static boolean oneCharOff(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int diff = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                if (++diff > 1) {
                    return false;
                }
            }
        }

        return diff == 1;
    }

    private static <Keytype> void update(Map<Keytype, List<String>> m, Keytype key, String value) {
        List<String> list = m.get(key);
        if (list == null) {
            list = new ArrayList<>();
            m.put(key, list);
        }
        list.add(value);
    }

    /**
     * Computes a map in which the keys are words and values are Lists of words
     * that differ in only one character from the corresponding key.
     * Uses a quadratic algorithm (with appropriate Map).
     */
    public static Map<String, List<String>> computeAdjacentWordsSlow(List<String> theList) {
        Map<String, List<String>> adjWords = new HashMap<>();
        String[] theWords = new String[theList.size()];
        theList.toArray(theWords);

        for (int i = 0; i < theWords.length; i++) {
            for (int j = i + 1; j < theWords.length; j++) {
                if (oneCharOff(theWords[i], theWords[j])) {
                    update(adjWords, theWords[i], theWords[j]);
                    update(adjWords, theWords[j], theWords[i]);
                }
            }
        }
        return adjWords;
    }

    /**
     * Computes a map in which the keys are words and values are Lists of words
     * that differ in only one character from the corresponding key.
     * Uses a quadratic algorithm (with appropriate Map), but speeds things up a little by
     * maintaining an additional map that groups words by their length.
     */
    public static Map<String, List<String>> computeAdjacentWordsMedium(List<String> theList) {
        Map<String, List<String>> adjWords = new HashMap<>();
        Map<Integer, List<String>> wordsByLength = new HashMap<>();
        // Group the words by their length
        for (String w : theList) {
            update(wordsByLength, w.length(), w);
        }
        // Work on each group separately
        for (List<String> groupsWords : wordsByLength.values()) {
            String[] word = new String[groupsWords.size()];
            groupsWords.toArray(word);
            for (int i = 0; i < word.length; i++) {
                for (int j = i + 1; j < word.length; j++) {
                    if (oneCharOff(word[i], word[j])) {
                        update(adjWords, word[i], word[j]);
                        update(adjWords, word[j], word[i]);
                    }
                }
            }
        }
        return adjWords;
    }

    /**
     * Computes a map in which the keys are words and values are Lists of words
     * that differ in only one character from the corresponding key.
     * Uses an efficient algorithm that is O(N log N) with a TreeMap, or
     * O(N) if a HashMap is used.
     */
    public static Map<String, List<String>> computeAdjcentWords(List<String> words) {
        Map<String, List<String>> adjWords = new TreeMap<>();
        Map<Integer, List<String>> wordsByLength = new TreeMap<>();
        // Group the words by their length
        for (String w : words) {
            update(wordsByLength, w.length(), w);
        }
        // Work on each group separately
        for (Map.Entry<Integer, List<String>> m : wordsByLength.entrySet()) {
            List<String> groupsWords = m.getValue();
            int grounpNum = m.getKey();

            // Work on each position in each group
            for (int i = 0; i < grounpNum; i++) {
                // Remove one character in specified position, computing representative.
                // Words with same representative are adjacent, so first populate
                // a map
                Map<String, List<String>> repToWord = new HashMap<>();
                for (String str : groupsWords) {
                    String rep = str.substring(0, i) + str.substring(i + 1);
                    update(repToWord, rep, str);
                }

                // and then look for map values with more than one string
                for (List<String> wordClique : repToWord.values()) {
                    if (wordClique.size() >= 2) {
                        for (String s1 : wordClique) {
                            for (String s2 : wordClique) {
                                // must be same string; equals not needed
                                if (s1 != s2) {
                                    update(adjWords, s1, s2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return adjWords;
    }

    /**
     * Find most changeable word: the word that differs in only one
     * character with the most words. Return a list of these words, in case of a tie.
     */
    public static List<String> findMostChangeable(Map<String, List<String>> adjacentWords) {
        List<String> mostChangeableWords = new ArrayList<>();
        int maxNumberOfAdjcentWords = 0;

        for (Map.Entry<String, List<String>> m : adjacentWords.entrySet()) {
            List<String> changes = m.getValue();
            if (changes.size() > maxNumberOfAdjcentWords) {
                maxNumberOfAdjcentWords = changes.size();
                mostChangeableWords.clear();
            }
            if (changes.size() == maxNumberOfAdjcentWords) {
                mostChangeableWords.add(m.getKey());
            }
        }

        return mostChangeableWords;
    }

    public static void
    printMostChangeableWords(List<String> mostChangeableWords, Map<String, List<String>> adjacentWords) {
        for (String word : mostChangeableWords) {
            System.out.print(word + ":");
            List<String> adjacents = adjacentWords.get(word);
            for (String str : adjacents) {
                System.out.print("  " + str);
            }
            System.out.println(" (" + adjacents.size() + " words)");
        }
    }

    public static void printHighChangeables(Map<String, List<String>> adjacentWords, int minWords) {
        for (Map.Entry<String, List<String>> m : adjacentWords.entrySet()) {
            List<String> words = m.getValue();
            if (words.size() > minWords) {
                System.out.print(m.getKey() + ")" + words.size() + "):");
                for (String w : words) {
                    System.out.print(" " + w);
                }
                System.out.println();
            }
        }
    }

    /**
     * After the shortest path calculation has run, computes the List that
     * contains the sequence of word changes to get from first to second.
     */
    public static List<String> getChainFromPrevioxusMap(Map<String, String> prev, String first, String second) {
        LinkedList<String> result = null;
        if (prev.get(second) != null) {
            result = new LinkedList<>();
            for (String str = second; str != null; str = prev.get(str)) {
                result.addFirst(str);
            }
        }
        return result;
    }

    /**
     * Runs the shortest path calculation from the adjacency map, returning a List
     * that contains the sequence of words changes to get from first to second.
     */
    public static List<String> findChain(Map<String, List<String>> adjacentWords, String first, String second) {
        Map<String, String> previous = new HashMap<>();
        LinkedList<String> q = new LinkedList<>();

        q.addLast(first);
        while (!q.isEmpty()) {
            String current = q.removeFirst();
            List<String> adj = adjacentWords.get(current);
            if (adj != null) {
                for (String adjWord : adj) {
                    if (previous.get(adjWord) == null) {
                        previous.put(adjWord, current);
                        q.addLast(adjWord);
                    }
                }
            }
        }
        previous.put(first, null);

        return getChainFromPrevioxusMap(previous, first, second);
    }

    /**
     * Runs the shortest path calculation from the original list of words, returning
     * a List that contains the sequence of word changes to get from first to
     * second. Since this calls computeAdjacentWords, it is recommended that the
     * user instead call computeAdjacentWords once and then call other findChar for
     * each word pair.
     */
    public static List<String> findChain(List<String> words, String first, String second) {
        Map<String, List<String>> adjacentWords = computeAdjcentWords(words);
        return findChain(adjacentWords, first, second);
    }

    public static void main(String[] args) throws IOException {
        long start, end;
        FileReader fin = new FileReader("D://temp//b.txt");
        BufferedReader bin = new BufferedReader(fin);
        List<String> words = readWords(bin);
        System.out.println("Read the words..." + words.size());
        Map<String, List<String>> adjacentWords;

        start = System.currentTimeMillis();
        adjacentWords = computeAdjcentWords(words);
        end = System.currentTimeMillis();
        System.out.println("Elapsed time fast:" + (end - start));

        start = System.currentTimeMillis();
        adjacentWords = computeAdjacentWordsMedium(words);
        end = System.currentTimeMillis();
        System.out.println("Elapsed time Medium:" + (end - start));

        start = System.currentTimeMillis();
        adjacentWords = computeAdjacentWordsSlow(words);
        end = System.currentTimeMillis();
        System.out.println("Elapsed time fast:" + (end - start));

        System.out.println("Adjacents computed...");
        List<String> mostChangeable = findMostChangeable(adjacentWords);
        System.out.println("Most changeable computed...");
        printMostChangeableWords(mostChangeable, adjacentWords);
    }
}
