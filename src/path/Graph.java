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
        //ciezko wwyolac konstruktor, zamiast rekurencji do metody petla, jezeli w try zadziala to exit var na true
        //jesli nie to wywali mu blad

        do
        {
            Helper.clearConsole();
            System.out.println("How many nodes do you want?");
            try
            {
                int i = Integer.parseInt(br.readLine());
                this.nodes = new Node[i];

                for (int j = 0; j < i; j++) //tablica z wezlami(node)
                {
                    readNodeCoordinates(j);
                }

                this.path = new Path(this.nodes); //tworzymy sciezke i wrzucamy utworzone wezly (Path)
                this.path.ChoseStartingNode(this.nodes); // ktory startujemy (Path)

                exitVar = true; //nie spelnilo iwec konczy petle
            }
            catch (Exception e)
            {
                Helper.handleReadError(e);
            }
        } while (!exitVar);
    }

    private void readNodeCoordinates(int i) throws IOException, InterruptedException //pozycja wezlow w tablicy
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
        this.path.calculateOptimalPath(); //oblicza sie sciezka
        this.path.displayPath(); //wyswietla wynik
    }
}
