import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainAiOnly extends Application {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8 * TILE_SIZE;
    private static final int HEIGHT = 8 * TILE_SIZE;

    static Pieces[][] grid;
    static Bourd bourd = new Bourd();
    GridPane gridPane = new GridPane();
    List<int[]> moves = new ArrayList<>();
    String turn = "W";
    ChessEnginV6 player = new ChessEnginV6();
    int move = 1;
    ChessEnginV4 player2 = new ChessEnginV4();
    KingCheck check = new KingCheck();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        System.out.println("Starting game");
        bourd.RestBourd();
        grid = bourd.getBourd();
        bourd.PrintBourd();

        Scene scene = new Scene(gridPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();

        // Create a timeline to update the board
        Duration duration = Duration.seconds(1); // Adjust the duration as needed
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            gridPane.getChildren().clear(); // Clear the previous board
            drawBoard();
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Run the animation indefinitely
        timeline.play();
    }

    private void drawBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row == 0 || row == 7) && grid[row][col].image.equals("P")) {
                    grid[row][col] = new Queen(grid[row][col].color);
                }
                String tile = (row + col) % 2 == 0 ? "W" : "G";
                Image image = new Image("Images/" + tile + "_" + grid[row][col].color + "_" + grid[row][col].image + ".png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                gridPane.add(imageView, col, row);
            }
        }

        if (turn.equals("W")) {
            grid = player.enginMove(grid, "W");
            move += 1;
            bourd.setBourd(grid);
            turn = "B";
        } else {
            grid = player.enginMove(grid, "B");
            bourd.setBourd(grid);
            turn = "W";
        }
    }
}
