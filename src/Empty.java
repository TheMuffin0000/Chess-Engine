import java.util.ArrayList;
import java.util.List;

public class Empty extends Pieces{
    public Empty(String coulor) {
        super.coulor = coulor;
        super.image = "-";
        super.value = 0;
    }
    public List<int[]> movement(int x, int y) {
        List<int[]> validMoves = new ArrayList<>();
        return validMoves;
    }
}
