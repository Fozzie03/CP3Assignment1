import java.util.HashMap;
import java.util.Map;


public class SuffixTrieNode {

    private SuffixTrieData data = new SuffixTrieData();

    private Map<Character, SuffixTrieNode> children = new HashMap<>();

    int numChildren = 0;

    public SuffixTrieNode getChild(char label) {
        return(children.get(label));
    }

    public void addChild(char label, SuffixTrieNode node) {
        children.put(label, node);
    }

    public void addData(int sentencePos, int characterPos) {
        data.addStartIndex(new SuffixIndex(sentencePos, characterPos));
    }

    public String toString() {
        return data.toString();
    }
}
