import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EightQueensVisualization extends Application {
    private int[] queens; // 用于存储每行皇后的列位置
    private GridPane chessBoard;

    @Override
    public void start(Stage primaryStage) {
        queens = new int[8];
        chessBoard = new GridPane();

        // 初始化棋盘
        initializeChessBoard();

        // 回溯算法求解八皇后问题
        solveNQueens(0);

        Scene scene = new Scene(chessBoard, 400, 400);

        primaryStage.setTitle("Eight Queens Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        if (row == 8) {
            // 解决方案找到，绘制棋盘
            drawQueens();
        } else {
            for (int col = 0; col < 8; col++) {
                if (isValidPlacement(row, col)) {
                    queens[row] = col;
                    solveNQueens(row + 1);
                }
            }
        }
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
