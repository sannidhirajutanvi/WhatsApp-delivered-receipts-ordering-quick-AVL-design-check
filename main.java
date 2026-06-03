class Node {
    long timestamp;
    Node left, right;
    int height;

    Node(long timestamp) {
        this.timestamp = timestamp;
        height = 1;
    }
}

public class Main {

    Node root;

    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, long key) {

        if (node == null)
            return new Node(key);

        if (key < node.timestamp)
            node.left = insert(node.left, key);

        else if (key > node.timestamp)
            node.right = insert(node.right, key);

        else
            return node;

        node.height = 1 + Math.max(height(node.left),
                                   height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.timestamp)
            return rightRotate(node);

        // RR
        if (balance < -1 && key > node.right.timestamp)
            return leftRotate(node);

        // LR
        if (balance > 1 && key > node.left.timestamp) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && key < node.right.timestamp) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    long findOldest(Node node) {
        while (node.left != null)
            node = node.left;

        return node.timestamp;
    }

    // Print tree sideways
    void printTree(Node root, int space) {

        if (root == null)
            return;

        space += 8;

        printTree(root.right, space);

        System.out.println();

        for (int i = 8; i < space; i++)
            System.out.print(" ");

        System.out.println(root.timestamp);

        printTree(root.left, space);
    }

    public static void main(String[] args) {

        Main tree = new Main();

        long timestamps[] = {10, 20, 30, 40, 50, 60};

        for (long t : timestamps)
            tree.root = tree.insert(tree.root, t);

        System.out.println("AVL Tree Structure:\n");
        tree.printTree(tree.root, 0);

        System.out.println("\nOldest Receipt = "
                + tree.findOldest(tree.root));

        System.out.println("Height = "
                + tree.height(tree.root));
    }
}