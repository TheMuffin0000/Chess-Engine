import java.util.List;
abstract class Pieces {
    protected String image;
    public String color;
    protected int value;

    abstract public List<int[]> movement(int x, int y, Pieces[][] grid);
}
