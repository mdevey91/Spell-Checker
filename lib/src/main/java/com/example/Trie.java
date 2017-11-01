package com.example;

public class Trie implements ITrie {
    private Node root;
    private int word_count;
    private int node_count;
    private StringBuilder huge_string;
    Trie()
    {
        root = new Node();
        word_count = 0;
        node_count = 1;
        huge_string = new StringBuilder();

    }
    public void add(String word)
    {
        StringBuilder sb = new StringBuilder(word.toLowerCase());
        root.addHelper(sb);
    }
    public INode find(String word)
    {
        StringBuilder sb = new StringBuilder(word.toLowerCase());
        return root.findHelper(sb);
    }
    public int getWordCount()
    {
        return word_count;
    }
    public int getNodeCount()
    {
        return node_count;
    }
    @Override
    public String toString() //need to over ride
    {
        huge_string.setLength(0);
        root.toStringHelper("");
        return huge_string.toString();
    }
    @Override
    public int hashCode() //need to override
    {
        return node_count * word_count * 31;
    }
    @Override
    public boolean equals(Object o) //need to override
    {
//      have to traverse the nodes and check if they have the same count at every node. If at any node there is a null pointer at one and a node at the other, return false;
        if(o == null) {
            return false;
        }
        if(!(o instanceof Trie)) {
            return false;
        }
        Trie my_trie = (Trie)o;
        if(((Trie) o).getNodeCount() != node_count)
            return false;
        if(((Trie) o).getWordCount() != word_count)
            return false;
        return this.equalsHelper(root, my_trie.root);
    }
    public boolean equalsHelper(Node trie_Node, Node o_node)
    {
        for(int i = 0; i < 26; i++)
        {
            if(trie_Node == null && o_node == null)
                return true;
            else if(trie_Node != null && o_node != null)
                return equalsHelper(trie_Node.getChildren()[i], o_node.getChildren()[i]);
            else
                return false;
        }
        return true;
    }
    public class Node implements INode {
        private int count;
        private Node[] children;

        public Node() {
            children = new Node[26];
            count = 0;
            node_count++;
        }
        public int getValue() {
            return count;
        }
        public Node[] getChildren(){return children;}
        public INode findHelper(StringBuilder word)
        {
            if(word.length() == 0)
            {
                if(count == 0)
                    return null;
                else
                    return this;
            }
            char c = word.charAt(0);
            System.out.println("looking for character: " + c);
            int i = c - 'a';
            word.deleteCharAt(0);
            if(children[i] == null)
                return null;
            else
                return children[i].findHelper(word);
        }
        public void addHelper(StringBuilder word)
        {
            if(word.length() == 0)
            {
                count++;
                word_count++;
                return;
            }
            char c = word.charAt(0);
            int i = c - 'a';
            if(children[i] == null)
            {
                children[i] = new Node();
            }
            word.deleteCharAt(0);
            children[i].addHelper(word);
        }
        public void toStringHelper(String word)
        {
            char c;
            if(count > 0)
            {
                huge_string.append(word);
                huge_string.append("\n");
            }
            for(int i = 0; i < 26; i++)
            {

                if(children[i] != null)
                {
                    c = (char)('a' + i);
                    children[i].toStringHelper(word + c);
                }
            }
        }
    }
}

