package path;

import tools.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class Path {

    private Node start;
    private int startIterator;
    private Map<Integer, Node> availableNodes;
    private double length;
    private Map<Integer, Node> usedNodes;
    private Node lastUsedNode;
    private int lastUsedIterator;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Path(Node[] nodes)
    {
        this.length = 0;
        this.availableNodes = new LinkedHashMap<Integer, Node>();
        this.usedNodes = new LinkedHashMap<Integer, Node>();
        for (int i = 0; i< nodes.length; i++) {
            availableNodes.put(i, nodes[i]);
        }
    }

    public void ChoseStartingNode(Node[] nodes) throws IOException, InterruptedException
    {
        Helper.clearConsole();
        System.out.println("Available nodes:");
        for (int i = 0; i < nodes.length; i++)
        {
            System.out.println((i+1)+". ("+nodes[i].getX()+";"+nodes[i].getY()+")");
        }
        System.out.println(" ");
        System.out.println("Chose your starting node.");
        try
        {
            int key = Integer.parseInt(br.readLine());
            if (key < 0 || key > nodes.length)
            {
                throw new IndexOutOfBoundsException();
            }
            else
            {
                this.start = this.availableNodes.get(key - 1);
                this.startIterator = key - 1;
            }
        }
        catch (Exception e)
        {
            Helper.handleReadError(e);
            ChoseStartingNode(nodes);
        }
    }

    public void calculateOptimalPath()
    {
        int nodesLength = this.availableNodes.size();
        this.availableNodes.remove(this.startIterator);
        this.usedNodes.put(this.startIterator,this.start);
        this.lastUsedNode = this.start;
        this.lastUsedIterator = this.startIterator;

        for (int i = 0; i < (nodesLength-1); i++)
        {
            calculateNextNode();
        }
    }

    private void calculateNextNode()
    {
        double shortestLength = Double.POSITIVE_INFINITY;
        int shortestNodeIterator = this.lastUsedIterator;
        double tempLength = 0;

        for (Map.Entry<Integer, Node> entry : this.availableNodes.entrySet())
        {
            tempLength = calculateSubPath(this.lastUsedNode, entry.getValue());
            if (Double.isInfinite(shortestLength) || shortestLength > tempLength)
            {
                shortestLength = tempLength;
                shortestNodeIterator = entry.getKey();
            }
        }

        this.length = this.length + shortestLength;
        this.lastUsedIterator = shortestNodeIterator;
        this.lastUsedNode = this.availableNodes.get(shortestNodeIterator);
        this.usedNodes.put(this.lastUsedIterator,this.lastUsedNode);
        this.availableNodes.remove(this.lastUsedIterator);
    }

    private static double calculateSubPath(Node firstNode, Node secondNode)
    {
        return Math.sqrt( Math.pow((firstNode.getX() - secondNode.getX()),2) + Math.pow((firstNode.getY() - secondNode.getY()),2) );
    }

    public void displayPath() throws IOException, InterruptedException
    {
        this.length = this.length + calculateSubPath(this.lastUsedNode, this.start);

        Helper.clearConsole();
        System.out.println("Starting node: "+(this.startIterator+1)+". ("+this.start.getX()+";"+this.start.getY()+")");
        System.out.println("Total length: "+this.length);
        System.out.println(" ");
        System.out.println("Path:  ");
        for (Map.Entry<Integer, Node> entry : this.usedNodes.entrySet())
        {
            System.out.println((entry.getKey()+1)+". ("+entry.getValue().getX()+";"+entry.getValue().getY()+")");
        }
        System.out.println((this.startIterator+1)+". ("+start.getX()+";"+this.start.getY()+")");
        System.in.read();
    }


}
