package path;

import tools.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Graph {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Node[] nodes;
    private Path path;

    public Graph() throws IOException, InterruptedException
    {
        boolean exitVar = false;

        do
        {
            Helper.clearConsole();
            System.out.println("How many nodes do you want?");
            try
            {
                int i = Integer.parseInt(br.readLine());
                this.nodes = new Node[i];

                for (int j = 0; j < i; j++)
                {
                    readNodeCoordinates(j);
                }

                this.path = new Path(this.nodes);
                this.path.ChoseStartingNode(this.nodes);

                exitVar = true;
            }
            catch (Exception e)
            {
                Helper.handleReadError(e);
            }
        } while (!exitVar);
    }

    private void readNodeCoordinates(int i) throws IOException, InterruptedException
    {
        try
        {
            Helper.clearConsole();
            System.out.println("Write X coordinate for "+(i+1)+" node");
            int x = Integer.parseInt(br.readLine());
            System.out.println("Write Y coordinate for "+(i+1)+" node");
            int y = Integer.parseInt(br.readLine());
            this.nodes[i] = new Node(x, y);
        }
        catch (Exception e)
        {
            Helper.handleReadError(e);
            readNodeCoordinates(i);
        }
    }

    public void displayPath() throws IOException, InterruptedException
    {
        this.path.calculateOptimalPath();
        this.path.displayPath();
    }
}
