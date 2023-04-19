import java.util.ArrayList;
import java.util.List;

public class ChessEnginV5 {//todo crate a recursive way for the engine to look at posibul future bourd states
    List<int[]> moves = new ArrayList<>();
    Pieces[][] savedGrid;
    Pieces[][] savedGrid2;
    Pieces[][] grid2;
    KingCheck check = new KingCheck();

    int leafs = 0;
    int depth2 = 4;
    NaryTreeNode root;


    public Pieces[][] enginMove(Pieces[][] grid){
        System.out.println("################################################");
        leafs = 0;


        String color = "W";

        root = new NaryTreeNode(grid);

        System.out.println(depth(root, depth2, "W", -100000, 100000));

        System.out.println(leafs);

        return savedGrid2;
    }
    public int depth(NaryTreeNode start, int depth, String color,int alpha,int beta) {
        if (depth <= 0) {
            int value = score(start.getValue());
            leafs += 1;
            return value;
        }

        if(color == "W") {

            int max = -100000;

            List<Pieces[][]> storedGames;
            storedGames = boardStates(start.getValue(), color);

            for (Pieces[][] storedGame : storedGames) {
                NaryTreeNode child1 = new NaryTreeNode(storedGame);


                int value = depth(child1, depth - 1, "B", alpha, beta);

                if(value > max){
                    max = value;
                    if(depth == depth2){
                        savedGrid2 = child1.getValue();
                    }
                }
                if(alpha < value){
                    alpha = value;
                }
                if(beta <= alpha){
                    break;
                }


                start.addChild(child1);
            }
            return max;
        }else{

            int min = 100000;

            List<Pieces[][]> storedGames;
            storedGames = boardStates(start.getValue(), color);

            for (Pieces[][] storedGame : storedGames) {
                NaryTreeNode child1 = new NaryTreeNode(storedGame);


                int value = depth(child1, depth - 1, "W", alpha, beta);

                if (value < min){
                    min = value;
                }

                if(beta > value){
                    beta = value;
                }

                if(beta <= alpha){
                    break;
                }

                start.addChild(child1);
            }
            return min;
        }
    }


    public int score(Pieces[][] grid) {
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

        int wightCenter = 0;
        int blackCenter = 0;


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pieces piece = grid[row][col];
                moves = piece.movement(col, row, grid);
                if (piece.color == "B") {
                    blackMaterial += piece.value;
                    blackMobility += moves.size();
                    if(piece.image != "P") {
                        for (int[] a : moves) {
                            if(a[1] == 3 || a[0] == 3){
                                if(a[1] == 4 || a[0] == 4){
                                    blackCenter += 1;
                                }
                            }
                            if (grid[a[1]][a[0]].color == "B") {
                                blackProtection += grid[a[1]][a[0]].value/2;
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
                            if(a[1] == 3 || a[0] == 3){
                                if(a[1] == 4 || a[0] == 4){
                                    wightCenter += 1;
                                }
                            }
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
        score += (wightAttack - blackAttack) * 5;
        score += (wightCenter - blackCenter) * 10;
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
