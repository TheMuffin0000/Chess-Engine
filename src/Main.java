import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8 * TILE_SIZE;
    private static final int HEIGHT = 8 * TILE_SIZE;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private ImageView selectedImageView;

    static Pieces[][] grid;
    static Bourd bourd = new Bourd();
    GridPane gridPain = new GridPane();
    List<int[]> moves = new ArrayList<>();
    String turn = "W";
    ChessEnginV4 player = new ChessEnginV4();
    int move = 1;
    ChessEnginV2 player2 = new ChessEnginV2();
    KingCheck check = new KingCheck();

    public static void main(String[] args) {
        launch(args);


    }
    @Override
    public void start(Stage stage) {
        System.out.println("starting game");
        bourd.RestBourd();
        grid = bourd.getBourd();
        bourd.PrintBourd();


        drawBoard();
        Scene scene = new Scene(gridPain, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    private void handleMouseClick(int row, int col) {
        if (moves.isEmpty() && grid[row][col].color == turn) {
            selectedRow = row;
            selectedCol = col;

            Pieces piece = grid[selectedRow][selectedCol];
            moves = piece.movement(selectedCol,selectedRow, grid);
            if (moves.size() > 0) {
                List<Integer> removeIndices = new ArrayList<Integer>();
                for (int i = 0; i < moves.size(); i++) {
                    int[] move = moves.get(i);
                    //System.out.println(move[0] +"+"+ move[1]);
                    if (check.isCheck(grid, selectedRow, selectedCol, move[1], move[0], piece)) {
                        removeIndices.add(i);
                        //System.out.println(move[1] +"removed"+ move[0]);
                    }
                }
                for (int i = removeIndices.size() - 1; i >= 0; i--) {
                    moves.remove((int) removeIndices.get(i));
                }
            }


            drawBoard();
        } else {
            if(row == selectedRow && col == selectedCol){
                moves.clear();
                selectedRow = -1;
                selectedCol = -1;
                drawBoard();
            }
            for(int i = 0; i < moves.size(); i++) {
                if(col == moves.get(i)[0] && row ==  moves.get(i)[1]) {
                    Pieces piece = grid[selectedRow][selectedCol];
                    if(grid[row][col].image == "K"){
                        System.out.println(turn + " wins!!!!");
                        System.exit(0);
                    }
                    grid[selectedRow][selectedCol] = new Empty("E");
                    grid[row][col] = piece;
                    bourd.setBourd(grid);
                    bourd.PrintBourd();
                    gridPain.getChildren().clear();
                    moves.clear();
                    if(turn == "W"){turn = "B";}
                    else{turn = "W";}
                    selectedRow = -1;
                    selectedCol = -1;
                    drawBoard();
                }
            }
        }
    }
    private void drawBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if((row == 0 || row == 7) && grid[row][col].image == "P"){
                    grid[row][col] = new Queen(grid[row][col].color);
                }
                String tile = (row + col) % 2 == 0 ? "W" : "G";
                if (moves.size() > 0) {
                    for (int[] move : moves) {
                        if (col == move[0] && row == move[1]) {
                            tile = "R";
                            break;
                        }
                    }
                }
                Image image = new Image("Images/" + tile + "_" + grid[row][col].color + "_" + grid[row][col].image + ".png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                gridPain.add(imageView, col, row);
                final int row2 = row;
                final int col2 = col;
                if(turn == "W"){


                    grid = player.enginMove(grid);
                    move += 1;
                    bourd.setBourd(grid);
                    bourd.PrintBourd();
                    gridPain.getChildren().clear();
                    turn = "B";
                    drawBoard();
                }
                else {
                    /**
                    grid = player2.enginMove(grid);
                    bourd.setBourd(grid);
                    bourd.PrintBourd();
                    gridPain.getChildren().clear();
                    turn = "B";
                    drawBoard();
                     */
                    imageView.setOnMouseClicked(e -> handleMouseClick(row2, col2));


                }

            }
        }
    }
}