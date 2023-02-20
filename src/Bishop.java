import java.util.ArrayList;
import java.util.List;

public class Bishop extends Pieces{
    public Bishop(String color) {
        super.color = color;
        super.image = "B";
        super.value = 3;

    }
    public List<int[]> movement(int x, int y, Pieces[][] grid) {
        List<int[]> validMoves = new ArrayList<>();
        int[] dx = {-1, -1, 1, 1};
        int[] dy = {-1, 1, -1, 1};
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newY][newX].color != color) {
                if(grid[newY][newX].color == "E") {
                    validMoves.add(new int[]{newX, newY});
                    newX += dx[i];
                    newY += dy[i];
                }else{
                    validMoves.add(new int[]{newX, newY});
                    newX += 100;
                }
            }
        }
        return validMoves;
    }
}
