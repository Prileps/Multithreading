import java.util.concurrent.CopyOnWriteArrayList;

public class Node {
    private volatile Node parent;
    private volatile int depth;
    private String url;
    private volatile CopyOnWriteArrayList<Node> children;

    public Node(String url) {
        depth = 0;
        this.url = url;
        parent = null;
        children = new CopyOnWriteArrayList<>();
    }


    public CopyOnWriteArrayList<Node> getChildren() {
        return children;
    }


    public synchronized void addChild(Node element) {
        Node root = getRoot();

        if(!root.contains(element.getUrl())) {
            element.setParent(this);
            children.add(element);
        }

    }


    private int calculateDepth() {

        int result = 0;

        if (parent == null) {
            return result;
        }
        result = parent.calculateDepth() + 1;
        return result;
    }


    public String getUrl() {
        return url;
    }


    public Node getRoot() {
        return parent == null ? this : parent.getRoot();
    }


    private boolean contains(String url) {

        if (this.url.equals(url)) {
            return true;
        }

        for (Node child : children) {
            if(child.contains(url)) {
                return true;
            }
        }
        return false;
    }


    private void setParent(Node node) {
        synchronized (this) {
            this.parent = node;
            this.depth = calculateDepth();
        }
    }

}
