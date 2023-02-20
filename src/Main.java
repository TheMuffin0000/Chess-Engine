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
    ChessEnginV1 player = new ChessEnginV1();

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

            drawBoard();
        } else {
            for(int i = 0; i < moves.size(); i++) {
                if(col == moves.get(i)[0] && row ==  moves.get(i)[1]) {
                    Pieces piece = grid[selectedRow][selectedCol];
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
                    bourd.setBourd(grid);
                    bourd.PrintBourd();
                    gridPain.getChildren().clear();
                    turn = "B";
                    drawBoard();
                }
                else {
                    imageView.setOnMouseClicked(e -> handleMouseClick(row2, col2));
                }

            }
        }
    }
}