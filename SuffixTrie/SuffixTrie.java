import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SuffixTrie {

    private SuffixTrieNode root = new SuffixTrieNode();

    /**
     * Insert a String into the suffix trie.  For the assignment the string str
     * is a sentence from the given text file.
     *
     * @param str the sentence to insert
     * @param startPosition the starting index/position of the sentence
     * @return the final node inserted
     */
    public SuffixTrieNode insert(String str, int startPosition) {
        SuffixTrieNode pointer = root;
        char[] processedStr = str.toLowerCase().toCharArray();
        // if(startPosition == 53) System.out.println(str);

        for(int i = 0; i < processedStr.length; i++){
            pointer = root;
            for(int j = i; j < processedStr.length; j++){
                if(pointer.getChild(processedStr[j]) == null) {

                    SuffixTrieNode newNode = new SuffixTrieNode();
                    pointer.addChild(processedStr[j], newNode);

                }

                pointer = pointer.getChild(processedStr[j]);
                pointer.addData(startPosition, i);

            }
        }
        return pointer;
    }

    /**
     * Get the suffix trie node associated with the given (sub)string.
     *
     * @param str the (sub)string to search for
     * @return  the final node in the (sub)string
     */
    public SuffixTrieNode get(String str) {
        
            char[] chars = str.toCharArray();
            SuffixTrieNode pointer = root;
            for(char letter: chars){
                if(pointer.getChild(letter) == null) return null;
                pointer = pointer.getChild(letter);
            }
    
            return pointer;
    }

    /**
     * Helper/Factory method to build a SuffixTrie object from the text in the 
     * given file.  The text file is broken up into sentences and each sentence
     * is inserted into the suffix trie.
     * 
     * It is called in the following way
     * <code>SuffixTrie st = SuffixTrie.readInFromFile("Frank01e.txt");</code>
     *
     * @param fileName
     * @return 
     */
    public static SuffixTrie readInFromFile(String fileName) {
        SuffixTrie newSuffixTrie = new SuffixTrie();
        try{
            Scanner s = new Scanner(new FileReader(fileName)).useDelimiter("[.?!]\\s*");
            int sentenceNum = 0; 
            String fileIn = "";

            while (s.hasNext()){
                newSuffixTrie.insert(s.next(), sentenceNum++);
            }
            s.close();
            return newSuffixTrie;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
    }
}