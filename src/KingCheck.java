import java.util.ArrayList;
import java.util.List;

public class KingCheck {
    public Pieces[][] grid2 = new Pieces[8][8];
    List<int[]> moves = new ArrayList<>();

    public boolean isCheck(Pieces[][] grid, int x, int y, int newX, int newY, Pieces piece){

        grid2 = copyArray(grid);
        System.out.println("grid");
        PrintBourd(grid);
        System.out.println("grid2");
        PrintBourd(grid2);

        grid2[x][y] = new Empty("E");
        grid2[newX][newY] = piece;
        System.out.println("grid");
        PrintBourd(grid);
        System.out.println("grid2 v2");
        PrintBourd(grid2);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(grid2[row][col].color != piece.color && grid2[row][col].color != "E") {
                    Pieces piece2 = grid2[row][col];
                    moves = piece2.movement(col,row, grid2);
                    System.out.println(piece2.image);
                    if (moves.size() > 0) {
                        for (int[] move : moves) {
                            System.out.println(move[1] +" fuck move "+ move[0] + " cunt " + grid2[move[1]][move[0]].image);

                            if (grid2[move[1]][move[0]].image == "K") {
                                System.out.println(newX +" kill me "+ newY);
                                System.out.println(piece2.image +" kill me ");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public Pieces[][] copyArray(Pieces[][] grid){

        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                grid2[j][k] = grid[j][k]; // copy each element of the array
            }
        }
        return grid2;
    }
    public void PrintBourd(Pieces[][] grid){
        for (int i = 0; i < grid.length; i++) {
            //System.out.print(8-i);
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j].image);
            }
            System.out.println("");
        }
        //System.out.println("  a b c d e f g h");
    }
}
