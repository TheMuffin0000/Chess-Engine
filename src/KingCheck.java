import java.util.List;

public class KingCheck {
    private int[] whightKingLocation;
    private int[] blackKingLocation;
    public Pieces[][] grid;

    public boolean isCheck(Pieces[][] grid, int x, int y, int newX, int newY){
        Pieces piece = grid[x][y];
        grid[x][y] = new Empty("E");
        grid[newX][newY] = piece;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(grid[row][col].color != piece.color) {
                    Pieces piece2 = grid[row][col];
                    List<int[]> moves = piece2.movement(row, col, grid);
                    if (moves.size() > 0) {
                        for (int[] move : moves) {
                            piece2 = grid[move[0]][move[1]];//todo this should hgo through the moves and check none of them are attacking the king

                        }
                    }
                    
                }
            }
        }
        return false;
    }

}
