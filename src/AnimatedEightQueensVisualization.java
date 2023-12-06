import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimatedEightQueensVisualization extends Application {
    private int[] queens; // 用于存储每行皇后的列位置
    private GridPane chessBoard;
    private int currentRow = 0;

    @Override
    public void start(Stage primaryStage) {
        queens = new int[8];
        chessBoard = new GridPane();

        // 初始化棋盘
        initializeChessBoard();

        Scene scene = new Scene(chessBoard, 400, 400);

        primaryStage.setTitle("Animated Eight Queens Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 使用Timeline创建动画
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // 添加关键帧
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (currentRow < 8) {
                solveNQueens(currentRow);
                currentRow++;
            } else {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void initializeChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(50, 50, (i + j) % 2 == 0 ? Color.LIGHTGRAY : Color.DARKGRAY);
                chessBoard.add(square, j, i);
            }
        }
    }

    private void solveNQueens(int row) {
        for (int col = 0; col < 8; col++) {
            if (isValidPlacement(row, col)) {
                queens[row] = col;
                drawQueens();
                return;
            }
        }
        // 如果无法找到合适的位置，则重新尝试上一行的下一个位置
        currentRow -= 2; // 退回上一行
    }

    private boolean isValidPlacement(int row, int col) {
        for (int i = 0; i < row; i++) {
            // 检查同一列和对角线
            if (queens[i] == col || Math.abs(queens[i] - col) == row - i) {
                return false;
            }
        }
        return true;
    }

    private void drawQueens() {
        chessBoard.getChildren().clear();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(50, 50, (i + j) % 2 == 0 ? Color.LIGHTGRAY : Color.DARKGRAY);
                chessBoard.add(square, j, i);

                if (queens[i] == j) {
                    // 在皇后位置绘制一个圆形
                    Rectangle queen = new Rectangle(50, 50, Color.RED);
                    chessBoard.add(queen, j, i);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
