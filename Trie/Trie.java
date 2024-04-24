import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.util.Collections.sort;

public class Trie {

    private TrieNode root = new TrieNode();

    /**
     * Inserts a string into the trie and returns the last node that was
     * inserted.
     *
     * @param str The string to insert into the trie
     * @param data	The data associated with the string
     * @return The last node that was inserted into the trie
     */
    public TrieNode insert(String str, TrieData data) {
        // hint you can use str.toCharArray() to get the char[] of characters

        char[] chars = str.toLowerCase().toCharArray();
        TrieNode pointer = this.root;

        for(char letter: chars){
            //Come back to: Constructor might change in the near future
            if(pointer.getChild(letter) == null){
                TrieNode newNode = new TrieNode();
                pointer.addChild(letter, newNode);
            }
        
            pointer = pointer.getChild(letter);
        }
        pointer.setTerminal(true);
        pointer.addData(data);
        return pointer;
    }

    /**
     * Search for a particular prefix in the trie, and return the final node in
     * the path from root to the end of the string, i.e. the node corresponding
     * to the final character. getNode() differs from get() in that getNode()
     * searches for any prefix starting from the root, and returns the node
     * corresponding to the final character of the prefix, whereas get() will
     * search for a whole word only and will return null if it finds the pattern
     * in the trie, but not as a whole word.  A "whole word" is a path in the
     * trie that has an ending node that is a terminal node.
     *
     * @param str The string to search for
     * @return the final node in the path from root to the end of the prefix, or
     * null if prefix is not found
     */
    public TrieNode getNode(String str) {
        // hint you can use str.toCharArray() to get the char[] of characters
        char[] chars = str.toCharArray();
        TrieNode pointer = this.root;
        for(char letter: chars){
            if(pointer.getChild(letter) == null) return null;
            pointer = pointer.getChild(letter);
        }

        return pointer;
    }

    /**
     * Searches for a word in the trie, and returns the final node in the search
     * sequence from the root, i.e. the node corresponding to the final
     * character in the word.
     *
     * getNode() differs from get() in that getNode() searches for any prefix
     * starting from the root, and returns the node corresponding to the final
     * character of the prefix, whereas get() will search for a whole word only
     * and will return null if it finds the pattern in the trie, but not as a
     * whole word. A "whole word" is a path in the
     * trie that has an ending node that is a terminal node.
     *
     * @param str The word to search for
     * @return The node corresponding to the final character in the word, or
     * null if word is not found
     */
    public TrieNode get(String str) {
        TrieNode pointer = getNode(str);
        if(pointer != null && pointer.isTerminal()) return pointer;
        return null;
    }

    /**
     * Retrieve from the trie an alphabetically sorted list of all words
     * beginning with a particular prefix.
     *
     * @param prefix The prefix with which all words start.
     * @return The list of words beginning with the prefix, or an empty list if
     * the prefix was not found.
     */
    public List<String> getAlphabeticalListWithPrefix(String prefix) {

        TrieNode pointer = getNode(prefix);//Starting Node (After Prefix)
        List<String> words = new ArrayList<>();         //List of Words

        if(pointer != null){
            if(pointer.isTerminal()) words.add(prefix);     //Are we starting on a Terminal Word?

            //Iterate Through Every Branch
            for(Character letter: pointer.getChildren()) findWords(words, prefix, letter, pointer);

            //Return Words
            sort(words);
        }

        return words;
    }

    private void findWords(List<String> foundWords, String prefix, Character letter, TrieNode pointer){
        //Add Letter
        prefix += letter;
        pointer = pointer.getChild(letter);

        if(pointer.getNumChildren() != 0){
            for(Character child: pointer.getChildren()){
                findWords(foundWords, prefix, child, pointer);
            }
        }
        if(pointer.isTerminal() && !foundWords.contains(prefix)) foundWords.add(prefix);
    }

    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Finds the most frequently
     * occurring word represented in the trie (according to the dictionary file)
     * that begins with the provided prefix.
     *
     * @param prefix The prefix to search for
     * @return The most frequent word that starts with prefix
     */
    public String getMostFrequentWordWithPrefix(String prefix) {
        TrieNode pointer = getNode(prefix);
        if (pointer == null){
            return prefix;
        }
        
        return findMostFrequent(pointer).getWord();
    }


    public TrieData findMostFrequent(TrieNode pointer) {
        TrieData frequencyData = new TrieData(-1);

        if (pointer.isTerminal()) {
            frequencyData.setFrequency(pointer.getFrequency());
            frequencyData.setWord(pointer.getWord());
        }

        if(pointer.getNumChildren() != 0) {
            for (Character child : pointer.getChildren()) {

                TrieNode childPointer = pointer.getChild(child);
                TrieData newData = findMostFrequent(childPointer);

                if (newData.getFrequency() > frequencyData.getFrequency()) frequencyData = newData;
            }
        }

        return frequencyData;
    }



    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Reads in a dictionary from file
     * and places all words into the trie.
     *
     * @param fileName the file to read from
     * @return the trie containing all the words
     */
    public static Trie readInDictionary(String fileName) {
        try {
            Trie dictionary = new Trie();
            Scanner s = new Scanner(new FileReader(fileName));

            while (s.hasNextLine()){
                String[] dictionaryEntry = s.nextLine().split(" ");
                dictionary.insert(dictionaryEntry[1],new TrieData(Integer.parseInt(dictionaryEntry[2]), dictionaryEntry[1]));
            }

            s.close();
            return dictionary;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}