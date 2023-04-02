import java.util.ArrayList;
import java.util.List;

public class ChessEnginV3 {//todo crate a recursive way for the engine to look at posibul future bourd states
    List<int[]> moves = new ArrayList<>();
    Pieces[][] savedGrid;
    Pieces[][] savedGrid2;
    Pieces[][] grid2;
    KingCheck check = new KingCheck();
    NaryTreeNode root;


    public Pieces[][] enginMove(Pieces[][] grid){

        int score = 100000;
        int score2 = -100000;
        String color = "W";

        NaryTreeNode root = new NaryTreeNode(grid);

        List<Pieces[][]> storedGames;

        storedGames = boardStates(copyArray(grid), color);
        for (int i = 0; i < storedGames.size(); i++) {
            NaryTreeNode child1 = new NaryTreeNode(storedGames.get(i));


            List<Pieces[][]> storedGames2;
            storedGames2 = boardStates(copyArray(storedGames.get(i)),"B");

            for (Pieces[][] a : storedGames2) {//calculates blacks posibul moves
                NaryTreeNode child2 = new NaryTreeNode(a);

                List<Pieces[][]> storedGames3;
                storedGames3 = boardStates(copyArray(a), "W");

                for (Pieces[][] b : storedGames3){
                    NaryTreeNode child3 = new NaryTreeNode(b);
                    child3.addChild(child3);
                }
                child2.addChild(child2);

            }
            root.addChild(child1);
        }

        List<NaryTreeNode> children = root.getChildren();

        // Iterate through each child node and print its leaf nodes
        for (NaryTreeNode child : children) {
            int newScore;
            List<NaryTreeNode> leafNodes = new ArrayList<>();
            findLeafNodes(child, leafNodes);
            for(NaryTreeNode leaf : leafNodes) {
                newScore = score(leaf.getValue());
                if(newScore > score2){
                    score2 = newScore;
                    savedGrid2 = copyArray(child.getValue());
                }
            }
        }


        return savedGrid2;
    }
    public void depth(int depth, String color){
        color = "W";
        List<Pieces[][]> storedGames;
        List<NaryTreeNode> children = root.getChildren();

        for (NaryTreeNode child : children) {
            List<NaryTreeNode> leafNodes = new ArrayList<>();
            findLeafNodes(child, leafNodes);

            for(NaryTreeNode leaf : leafNodes) {
                storedGames = boardStates(copyArray(leaf.getValue()), color);

                for (int i = 0; i < storedGames.size(); i++) {
                    NaryTreeNode child1 = new NaryTreeNode(storedGames.get(i));

                    leaf.addChild(child1);
                }
            }

        }
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
        //System.out.println(score);
        return score;
    }
    public List<Pieces[][]> boardStates(Pieces[][] grid, String color){
        List<Pieces[][]> storedGames = new ArrayList<Pieces[][]>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(grid[row][col].color == color){
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

                    for (int[] a : moves) {
                        grid2 = copyArray(grid);
                        grid2[row][col] = new Empty("E");
                        grid2[a[1]][a[0]] = piece;
                        savedGrid = copyArray(grid2);
                        storedGames.add(savedGrid);
                    }
                }
            }
        }
        return storedGames;
    }




    public Pieces[][] copyArray(Pieces[][] grid){
        grid2 = new Pieces[8][8]; // create a new 2D array
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                grid2[j][k] = grid[j][k]; // copy each element of the array
            }
        }
        return grid2;
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
    public static void findLeafNodes(NaryTreeNode node, List<NaryTreeNode> leafNodes) {
        if (node.getChildren().isEmpty()) {
            leafNodes.add(node);
        } else {
            for (NaryTreeNode child : node.getChildren()) {
                findLeafNodes(child, leafNodes);
            }
        }
    }
}
