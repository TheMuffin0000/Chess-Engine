import java.util.ArrayList;
import java.util.List;

public class King extends Pieces{
    public King(String color) {
        super.color = color;
        super.image = "K";
        super.value = 100;
    }
    public List<int[]> movement(int x, int y, Pieces[][] grid) {//todo fix check movment can have a variabul in main
        List<int[]> validMoves = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newY][newX].color != color) {
                validMoves.add(new int[]{newX, newY});
            }
        }
        return validMoves;
    }
}
