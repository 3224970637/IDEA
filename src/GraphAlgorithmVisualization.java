import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GraphAlgorithmVisualization extends Application {
    private int[][] adjacencyMatrix = {
            {0, 1, 1, 0, 0, 0},
            {1, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0}
    };

    private boolean[] visited;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        visited = new boolean[adjacencyMatrix.length];

        // Draw vertices
        Circle[] circles = new Circle[adjacencyMatrix.length];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle(30 + i * 60, 150, 15, Color.LIGHTBLUE);
            root.getChildren().add(circles[i]);
        }

        // Draw edges
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    Line line = new Line(circles[i].getCenterX(), circles[i].getCenterY(),
                            circles[j].getCenterX(), circles[j].getCenterY());
                    root.getChildren().add(line);
                }
            }
        }

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Graph Algorithm Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Run DFS algorithm and visualize
        dfs(0, circles);
    }

    private void dfs(int vertex, Circle[] circles) {
        if (!visited[vertex]) {
            visited[vertex] = true;
            circles[vertex].setFill(Color.RED);

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                if (adjacencyMatrix[vertex][i] == 1) {
                    dfs(i, circles);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
