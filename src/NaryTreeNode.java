import java.util.ArrayList;
import java.util.List;

public class NaryTreeNode {
    private Pieces[][] gameState;
    private List<NaryTreeNode> children;

    public NaryTreeNode(Pieces[][] gameState) {
        this.gameState = gameState;
        this.children = new ArrayList<>();
    }

    public void addChild(NaryTreeNode child) {
        children.add(child);
    }

    public List<NaryTreeNode> getChildren() {
        return children;
    }

    public Pieces[][] getValue() {
        return gameState;
    }
}
