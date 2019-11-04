package path; // tworzenie sciezkiPath tworzenie linii łączącej węzły na układzie współrzędnych (sciezka Hamiltona)

import tools.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class Path {

    private Node start; //punkt startowy 1 wezel
    private int startIterator; //liczba czalkowita , kolejnosc w tablicy wezla , miejsce z ktorego startujemy
    private Map<Integer, Node> availableNodes; //wszystkie wezly dostepne (na poczatku wszystkie)
    private double length; //dlugosc calkowita przebytej drogi
    private Map<Integer, Node> usedNodes;
    private Node lastUsedNode; //
    private int lastUsedIterator;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //po to zeby moc wprowadzac do konsoli

    public Path(Node[] nodes) //obiekt path
    {
        this.length = 0; //dlugosc 0
        this.availableNodes = new LinkedHashMap<Integer, Node>(); //inicjujemy tablice dostepnych wezlow
        this.usedNodes = new LinkedHashMap<Integer, Node>(); //inicjujemy
        for (int i = 0; i< nodes.length; i++) {               //uzupelnianie tablicy wolnymi punktami
            availableNodes.put(i, nodes[i]);
        }
    }

    public void ChoseStartingNode(Node[] nodes) throws IOException, InterruptedException //punkt startowy
    {
        Helper.clearConsole(); //helper
        System.out.println("Available nodes:");
        for (int i = 0; i < nodes.length; i++) // wyswietla wszystkie wezly dostepne
        {
            System.out.println((i+1)+". ("+nodes[i].getX()+";"+nodes[i].getY()+")");
        }
        System.out.println(" ");
        System.out.println("Chose your starting node.");
        try //sprawdza, jesli jest blad patrzy na catch
        {
            int key = Integer.parseInt(br.readLine()); //pobierasz od uzytkownika, i starasz sie na liczbe calkowita, zmiana typu
            if (key < 0 || key > nodes.length) //miesci sie w zakresie wezlow podanych
            {
                throw new IndexOutOfBoundsException(); //wyjatek ze wybrales element spoza tablicy
            }
            else
            {
                this.start = this.availableNodes.get(key - 1); //robi to startowym wezlem, key-1
                this.startIterator = key - 1;
            }
        }
        catch (Exception e)
        {
            Helper.handleReadError(e);
            ChoseStartingNode(nodes); //ponowna prosba o punkt
        }
    }

    public void calculateOptimalPath()
    {
        int nodesLength = this.availableNodes.size(); //przypisanie dlugosci tablicy
        this.availableNodes.remove(this.startIterator); //usuwamy z dostepnych startowy wezel
        this.usedNodes.put(this.startIterator,this.start); // do zuzytych dodajemy startowy
        this.lastUsedNode = this.start; //wezel startowy ostatnio uzyty na sciezce
        this.lastUsedIterator = this.startIterator;

        for (int i = 0; i < (nodesLength-1); i++) //-1 bo usunelismy startowy jesli 5 to 4 razy uzywa funkcje
        {
            calculateNextNode();
        }
    }

    private void calculateNextNode()
    {
        double shortestLength = Double.POSITIVE_INFINITY; //najdluzsza ustawiamy na nieskonczonosc bo nie znamy
        int shortestNodeIterator = this.lastUsedIterator; //najblizszy wezel przypisany np. jesli a to a
        double tempLength = 0; //tymczasowa odleglosc

        for (Map.Entry<Integer, Node> entry : this.availableNodes.entrySet())
            //mapy bo tablicach nie mozna ustawic wlasnych kluczy
            // (definiujemy ze for dotyczy mapy integer i wartosci ktora jest obiektem klasy node , entry to pojedynczy wezel
            // MAPA Z KLUCZAMI KTORE SA LICZBA CALKOWITA
            //skad  bierzemy wezly do mapy
        {
            tempLength = calculateSubPath(this.lastUsedNode, entry.getValue()); //wartosc z punktu do puntu
            if (Double.isInfinite(shortestLength) || shortestLength > tempLength) //jesli najkrotsza to nieskon albo najkrotsza jest wieksza od tej obliczonej
            {
                shortestLength = tempLength; //jesli tymczasowa jest krotsza to wchodzimy do ifa i ustawiamy najkrotsza dlugosc
                shortestNodeIterator = entry.getKey(); // bierzemy klucz z tablicy, zapamietujemy pozycje w tab wezla ktory jest najblizej
            }
        }

        this.length = this.length + shortestLength; //sumujemy dlugosc co mielismy z ta co wlasnie znalezlismy, clakowita sciezka
        this.lastUsedIterator = shortestNodeIterator; // podmiana punkt
        this.lastUsedNode = this.availableNodes.get(shortestNodeIterator);
        this.usedNodes.put(this.lastUsedIterator,this.lastUsedNode); //dodajemy do zuzytych wezlow ten ktory znalezlismy i usuwamy go z mozliwosci
        this.availableNodes.remove(this.lastUsedIterator);
    }

    private static double calculateSubPath(Node firstNode, Node secondNode) //obliczanie dlugosci miedzy 2 punktami
    {
        return Math.sqrt( Math.pow((firstNode.getX() - secondNode.getX()),2) + Math.pow((firstNode.getY() - secondNode.getY()),2) );
    }

    public void displayPath() throws IOException, InterruptedException // na koncu wyswietla dlugosc sciezki, wezel startowy, po kolei sciezka
    {
        this.length = this.length + calculateSubPath(this.lastUsedNode, this.start);

        Helper.clearConsole();
        System.out.println("Starting node: "+(this.startIterator+1)+". ("+this.start.getX()+";"+this.start.getY()+")");
        System.out.println("Total length: "+this.length);
        System.out.println(" ");
        System.out.println("Path:  ");
        for (Map.Entry<Integer, Node> entry : this.usedNodes.entrySet()) // po zuzytych + startyowy
        {
            System.out.println((entry.getKey()+1)+". ("+entry.getValue().getX()+";"+entry.getValue().getY()+")");
        }
        System.out.println((this.startIterator+1)+". ("+start.getX()+";"+this.start.getY()+")");
        System.in.read();
    }


}
