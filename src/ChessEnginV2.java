import java.util.ArrayList;
import java.util.List;

public class ChessEnginV2 {//todo improve the scoring system to take in to account the value of a position
    List<int[]> moves = new ArrayList<>();
    Pieces[][] savedGrid;
    Pieces[][] grid2;
    KingCheck check = new KingCheck();

    public Pieces[][] enginMove(Pieces[][] grid){
        int score = -100000;

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
                                System.out.println(move[1] +"removed"+ move[0]);
                            }
                        }
                        for (int i = removeIndices.size() - 1; i >= 0; i--) {
                            moves.remove((int) removeIndices.get(i));
                        }
                    }

                    for (int[] a : moves) {
                        grid2 = copyArray(grid, grid2);
                        grid2[row][col] = new Empty("E");
                        grid2[a[1]][a[0]] = piece;
                        int newScore = score(grid2);
                        if(score < newScore){
                            score = newScore;
                            //System.out.println(score);
                            savedGrid = copyArray(grid2, savedGrid);

                        }
                    }
                }
            }
        }
        return savedGrid;
    }
    public int score(Pieces[][] grid){
        int score = 0;

        int wightMaterial = 0;
        int blackMaterial = 0;

        int wightMobility = 0;
        int blackMobility = 0;

        int wightPawnChain = 0;
        int blackPawnChain = 0;
        int[] dx = new int[]{-1, 1};

        int wightProtection = 0;
        int blackProtection = 0;

        int wightAttack = 0;
        int blackAttack = 0;


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pieces piece = grid[row][col];
                moves = piece.movement(col, row, grid);
                if (piece.color == "B") {
                    blackMaterial += piece.value;
                    blackMobility += moves.size();
                    if(piece.image != "P") {
                        for (int[] a : moves) {
                            if (grid[a[1]][a[0]].color == "B") {
                                blackProtection += 1;
                            }else if(grid[a[1]][a[0]].color == "W"){
                                blackAttack += grid[a[1]][a[0]].value/2;
                            }
                        }
                    }

                }else if(piece.color == "W") {
                    wightMaterial += piece.value;
                    wightMobility += moves.size();
                    if(piece.image != "P") {
                        for (int[] a : moves) {
                            if (grid[a[1]][a[0]].color == "W") {
                                wightProtection += grid[a[1]][a[0]].value/2;
                            }else if(grid[a[1]][a[0]].color == "B"){
                                wightAttack += grid[a[1]][a[0]].value/2;
                            }
                        }
                    }
                }
                if(piece.image == "P") {
                    for (int i = 0; i < 2; i++) {
                        if(piece.color == "W") {
                            int newX = col + dx[i];
                            int newY = row + 1;
                            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newY][newX].color == "W") {
                                wightPawnChain += 2 + row;
                            }
                        }else{
                            int newX = col + dx[i];
                            int newY = row - 1;
                            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newY][newX].color == "B") {
                                blackPawnChain += 2 + (8-row);

                            }
                        }
                    }
                }
            }
        }
        score += (wightMaterial - blackMaterial) * 100;
        score += (wightMobility - blackMobility) * 2;
        score += (wightPawnChain - blackPawnChain) * 10;
        score += (wightProtection - blackProtection) * 10;
        score += (wightAttack - blackAttack) * 10;
        System.out.println(score);
        return score;
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
