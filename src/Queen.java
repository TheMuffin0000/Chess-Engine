import java.util.ArrayList;
import java.util.List;

public class Queen extends Pieces{
    public Queen(String coulor) {
        super.coulor = coulor;
        super.image = "Q";
        super.value = 9;
    }
    public List<int[]> movement(int x, int y, Pieces[][] grid) {
        List<int[]> validMoves = new ArrayList<>();
        int[] dxBishop = {-1, -1, 1, 1};
        int[] dyBishop = {-1, 1, -1, 1};
        int[] dxRook = {-1, 1, 0, 0};
        int[] dyRook = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int newX = x + dxBishop[i];
            int newY = y + dyBishop[i];
            while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newY][newX].coulor != coulor) {
                if(grid[newY][newX].coulor == "E") {
                    validMoves.add(new int[]{newX, newY});
                    newX += dxBishop[i];
                    newY += dyBishop[i];
                }else {
                    validMoves.add(new int[]{newX, newY});
                    newX += 100;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            int newX = x + dxRook[i];
            int newY = y + dyRook[i];
            while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newY][newX].coulor != coulor) {
                if(grid[newY][newX].coulor == "E") {
                    validMoves.add(new int[]{newX, newY});
                    newX += dxRook[i];
                    newY += dyRook[i];
                }else{
                    validMoves.add(new int[]{newX, newY});
                    newX += 100;
                }
            }
        }
        return validMoves;
    }
}
