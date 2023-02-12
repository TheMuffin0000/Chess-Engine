import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class javafxBoard extends Application {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8 * TILE_SIZE;
    private static final int HEIGHT = 8 * TILE_SIZE;
    private Pieces[][] board;


    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
                //Label label = new Label(dsboard[row][col]);
                //label.setPrefSize(TILE_SIZE, TILE_SIZE);
                //label.setAlignment(Pos.CENTER);
                grid.add(tile, col, row);
            }
        }
        Scene scene = new Scene(grid, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
