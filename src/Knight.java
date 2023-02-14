import java.util.ArrayList;
import java.util.List;
public class Knight extends Pieces{
    public Knight(String coulor) {
        super.coulor = coulor;
        super.image = "N";
        super.value = 3;
    }
    public List<int[]> movement(int x, int y) {
        List<int[]> validMoves = new ArrayList<>();
        int[] dx = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dy = {-1, 1, -2, 2, -2, 2, -1, 1};
        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                validMoves.add(new int[]{newX, newY});
            }
        }

        return validMoves;
    }
}

