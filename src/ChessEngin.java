import java.util.ArrayList;
import java.util.List;

public class ChessEngin {
    List<int[]> moves = new ArrayList<>();
    Pieces[][] savedGrid;
    Pieces[][] grid2;

    public Pieces[][] enginMove(Pieces[][] grid){
        int score = 1000;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(grid[row][col].color == "W"){
                    Pieces piece = grid[row][col];
                    moves = piece.movement(col, row, grid);



                    for(int i = 0; i < moves.size(); i++) {
                        grid2 = copyArray(grid, grid2);
                        //System.out.println(moves.get(i)[0] + ", " + moves.get(i)[1]);
                        grid2[row][col] = new Empty("E");
                        grid2[moves.get(i)[1]][moves.get(i)[0]] = piece;
                        if(score > score(grid2)){
                            score = score(grid2);
                            savedGrid = copyArray(grid2, savedGrid);
                            printGrid(savedGrid);

                        }
                    }
                }
            }
        }
        return savedGrid;
    }
    public int score(Pieces[][] grid){
        int score = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (grid[row][col].color == "B") {
                    Pieces piece = grid[row][col];
                    score += piece.value;
                }
            }
        }
        System.out.println(score);
        return score;
    }
    public void printGrid(Pieces[][] grid){
        for (int i = 0; i < grid.length; i++) {
            //System.out.print(8-i);
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j].image);
            }
            System.out.println("");
        }
    }
    public Pieces[][] copyArray(Pieces[][] grid, Pieces[][] grid2){
        grid2 = new Pieces[8][8]; // create a new 2D array
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                grid2[j][k] = grid[j][k]; // copy each element of the array
            }
        }
        return grid2;
    }
}
