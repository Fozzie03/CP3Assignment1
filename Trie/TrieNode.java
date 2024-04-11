import java.util.*;
public class TrieNode {

    private TrieData data = null;
    private boolean terminal = false;
    private int numChildren = 0;

    private Map<Character, TrieNode> children = new HashMap<>();
    /**
     * Lookup a child node of the current node that is associated with a
     * particular character label.
     *
     * @param label The label to search for
     * @return The child node associated with the provided label
     */
    public TrieNode getChild(char label) {
        return children.get(label);
    }

    public Set<Character> getChildren() {
        return children.keySet();
    }
    /**
     * Add a child node to the current node, and associate it with the provided
     * label.
     *
     * @param label The character label to associate the new child node with
     * @param node The new child node that is to be attached to the current node
     */
    public void addChild(char label, TrieNode node) {
        if(getChild(label) == null){
            children.put(label, node);
            numChildren++;
        }
    }
    /**
     * Add a new data object to the node.
     *
     * @param dataObject The data object to be added to the node.
     */
    public void addData(TrieData dataObject) {
        this.data = dataObject;
    }
    /**
     * Set whether the node is Terminal or not.
     *
     * @param update The new value for terminal.
     */
    public void setTerminal(boolean update){
        terminal = update;
    }
    /**
     * Returns whether the node is terminal or not.
     */
    public boolean isTerminal() {
        return terminal;
    }
    /**
     * The toString() method for the TrieNode that outputs in the format
     *   TrieNode; isTerminal=[true|false], data={toString of data}, #children={number of children}
     * for example,
     *   TrieNode; isTerminal=true, data=3, #children=1
     * @return
     */
    public int getNumChildren(){
        return numChildren;
    }
    @Override
    public String toString() {
        String dataString = "null";
        if(data != null) dataString = data.toString();
        return "TrieNode; isTerminal=" + isTerminal() + ", data=" + dataString + ", #children=" + numChildren;
    }
    public int getFrequency(){
        return data.getFrequency();
    }
    public String getWord(){
        return data.getWord();
    }

}