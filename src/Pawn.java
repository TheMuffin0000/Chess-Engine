import java.util.ArrayList;
import java.util.List;

public class Pawn extends Pieces{
    public Pawn(String coulor) {
        super.coulor = coulor;
        super.image = "P";
        super.value = 1;

    }
    public List<int[]> movement(int x, int y) {//todo
        int direction;
        if(coulor == "W"){direction = 1;}
        else{direction = -1;}
        System.out.println(x + "," + y);
        List<int[]> validMoves = new ArrayList<>();
        int[] dx = {0};
        int[] dy = {direction};
        int newX = x + dx[0];
        int newY = y + dy[0];
        if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
            validMoves.add(new int[]{newX, newY});
            if ((coulor == "W" && y == 1) || (coulor == "B" && y == 6)) {
                newY = y + 2 * dy[0];
                validMoves.add(new int[]{newX, newY});
            }
        }
        dx = new int[]{-1, 1};
        dy = new int[]{direction, direction};
        for (int i = 0; i < 2; i++) {
            newX = x + dx[i];
            newY = y + dy[i];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                validMoves.add(new int[]{newX, newY});
            }
        }
        return validMoves;

    }
}
