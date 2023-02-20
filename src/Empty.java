import java.util.ArrayList;
import java.util.List;

public class Empty extends Pieces{
    public Empty(String color) {
        super.color = color;
        super.image = "-";
        super.value = 0;
    }
    public List<int[]> movement(int x, int y, Pieces[][] grid) {
        List<int[]> validMoves = new ArrayList<>();
        return validMoves;
    }
}
