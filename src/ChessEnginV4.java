import java.util.ArrayList;
import java.util.List;

public class ChessEnginV4 {//todo crate a recursive way for the engine to look at posibul future bourd states
    List<int[]> moves = new ArrayList<>();
    Pieces[][] savedGrid;
    Pieces[][] savedGrid2;
    Pieces[][] grid2;
    KingCheck check = new KingCheck();

    int leafs = 0;
    NaryTreeNode root;


    public Pieces[][] enginMove(Pieces[][] grid){
        System.out.println("################################################");
        int length = 0;


        int score2 = -100000;
        String color = "W";

        root = new NaryTreeNode(grid);

        System.out.println(depth(root, 4, "W"));

        System.out.println(leafs);

        return savedGrid2;
    }
    public int depth(NaryTreeNode start, int depth, String color) {
        if (depth <= 0) {
            int value = score(start.getValue());
            leafs += 1;
            return value;
        }

        if(color == "W") {

            int max = -1000;

            List<Pieces[][]> storedGames;
            storedGames = boardStates(start.getValue(), color);

            for (Pieces[][] storedGame : storedGames) {
                NaryTreeNode child1 = new NaryTreeNode(storedGame);


                int value = depth(child1, depth - 1, "B");

                if(value > max){
                    max = value;
                    if(depth == 4){
                        savedGrid2 = child1.getValue();
                    }
                }



                start.addChild(child1);
            }
            return max;
        }else{

            int min = 1000;

            List<Pieces[][]> storedGames;
            storedGames = boardStates(start.getValue(), color);

            for (Pieces[][] storedGame : storedGames) {
                NaryTreeNode child1 = new NaryTreeNode(storedGame);


                int value = depth(child1, depth - 1, "W");

                if (value < min){
                    min = value;
                }


                start.addChild(child1);
            }
            return min;
        }
    }


    public int score(Pieces[][] grid){
        int score = 0;

        int wightMaterial = 0;
        int blackMaterial = 0;


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pieces piece = grid[row][col];
                moves = piece.movement(col, row, grid);
                if (piece.color == "B") {
                    blackMaterial += piece.value;

                }else if(piece.color == "W") {
                    wightMaterial += piece.value;
                }
            }
        }
        score += (wightMaterial - blackMaterial);
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
