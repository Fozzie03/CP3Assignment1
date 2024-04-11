public class TrieData {

    private int frequency = -1;
    private String word = null;

    /**
     * Construct a new TrieData with the given frequency
     * @param frequency the frequency of the data associated with the TrieNode
     */
    public TrieData(int frequency) {
        this.frequency = frequency;
    }
    public TrieData(int frequency, String word){
        this.frequency = frequency;
        this.word = word;
    }

    /**
     * Gets the frequency of this TrieData.
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets the frequency of this TrieData
     * @param frequency the frequency
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Sets the word of this TrieData.
     * @param word the word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Gets the word of this TrieData.
     * @return the word
     */
    public String getWord() {
        return word;
    }


    @Override
    public String toString() {
        return String.valueOf(frequency);
    }
}