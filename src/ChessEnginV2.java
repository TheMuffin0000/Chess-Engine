import java.util.ArrayList;
import java.util.List;

public class ChessEnginV2 {//todo improve the scoring system to take in to account the value of a position
    List<int[]> moves = new ArrayList<>();
    Pieces[][] savedGrid;
    Pieces[][] savedGrid2;
    Pieces[][] grid2;
    KingCheck check = new KingCheck();

    public Pieces[][] enginMove(Pieces[][] grid){
        int score = 1000;
        int avalabulMoves = 0;
        ArrayList<Pieces[][]> savedList = new ArrayList<Pieces[][]>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(grid[row][col].color == "W"){
                    Pieces piece = grid[row][col];
                    moves = piece.movement(col, row, grid);

                    if (moves.size() > 0) {
                        List<Integer> removeIndices = new ArrayList<Integer>();
                        for (int i = 0; i < moves.size(); i++) {
                            int[] move = moves.get(i);
                            //System.out.println(move[0] +"+"+ move[1]);
                            if (check.isCheck(grid, row, col, move[1], move[0], piece)) {
                                removeIndices.add(i);
                                //System.out.println(move[1] +"removed"+ move[0]);
                            }
                        }
                        for (int i = removeIndices.size() - 1; i >= 0; i--) {
                            moves.remove((int) removeIndices.get(i));
                        }
                    }



                    for(int i = 0; i < moves.size(); i++) {
                        grid2 = copyArray(grid, grid2);
                        //System.out.println(moves.get(i)[0] + ", " + moves.get(i)[1]);
                        grid2[row][col] = new Empty("E");
                        grid2[moves.get(i)[1]][moves.get(i)[0]] = piece;
                        if(score > score(grid2)){
                            score = score(grid2);
                            savedGrid = copyArray(grid2, savedGrid);
                            savedList.clear();
                            savedList.add(savedGrid);
                            //printGrid(savedGrid);

                        } else if (score == score(grid2)) {
                            savedGrid = copyArray(grid2, savedGrid);
                            savedList.add(savedGrid);
                            //printGrid(savedGrid);
                        }
                    }
                    for(int i = 0; i < savedList.size(); i++) {
                        if(AvalabulMoves(savedList.get(i)) > avalabulMoves) {
                            printGrid(savedList.get(i));
                            avalabulMoves = AvalabulMoves(savedList.get(i));
                            savedGrid2 = copyArray(savedList.get(i), savedGrid);
                        }
                    }
                }
            }
        }
        return savedGrid2;
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
        //System.out.println(score);
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
    public int AvalabulMoves(Pieces[][] grid){
        int avalabulMoves = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pieces piece = grid[row][col];
                moves = piece.movement(col, row, grid);
                avalabulMoves += moves.size();
            }
        }

        return avalabulMoves;
    }
}
