package path; //Node pojedynczy wezel – pojemnik na współrzędne x , y

public class Node {
    private int x;
    private int y;

    public Node(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }
}
