import java.util.ArrayList;
import java.util.List;

public class ChessEngin {
    List<int[]> moves = new ArrayList<>();
    public Pieces[][] enginMove(Pieces[][] grid){//todo why are you taking you own pieces how are you even doing it
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(grid[row][col].color == "W"){
                    Pieces piece = grid[row][col];
                    moves = piece.movement(row,col, grid);
                    if(moves.size() > 0) {
                        grid[row][col] = new Empty("E");
                        grid[moves.get(0)[0]][moves.get(0)[1]] = piece;
                        return grid;
                    }
                }
            }
        }
        return grid;
    }
}
