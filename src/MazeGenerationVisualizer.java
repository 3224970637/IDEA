import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;

public class MazeGenerationVisualizer extends JFrame {

    private static final int SIZE = 20;
    private int[][] maze;
    private boolean[][] visited;
    private Timer timer;

    private static final int[] dx = {0, 1, 0, -1}; // Direction changes for x-coordinate
    private static final int[] dy = {-1, 0, 1, 0}; // Direction changes for y-coordinate

    public MazeGenerationVisualizer() {
        maze = new int[SIZE][SIZE];
        visited = new boolean[SIZE][SIZE];

        setTitle("Maze Generation Visualizer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Generate Maze");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMaze();
            }
        });

        add(startButton, BorderLayout.SOUTH);

        MazePanel mazePanel = new MazePanel();
        add(mazePanel, BorderLayout.CENTER);

        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMazeStep();
                mazePanel.repaint();
            }
        });
    }

    private void generateMaze() {
        // Initialize maze and visited array
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                maze[i][j] = 1;  // Set all cells as walls initially
                visited[i][j] = false;
            }
        }

        // Start from a random cell
        Random random = new Random();
        int startX = random.nextInt(SIZE);
        int startY = random.nextInt(SIZE);

        // Depth-First Search to generate the maze
        depthFirstSearch(startX, startY);

        // Start the timer to visualize the process
        timer.start();
    }

    private void depthFirstSearch(int x, int y) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x, y));
        visited[x][y] = true;

        while (!stack.isEmpty()) {
            Point currentCell = stack.pop();
            int currentX = currentCell.x;
            int currentY = currentCell.y;
            maze[currentX][currentY] = 0;  // Set the cell as a path

            int[] directions = {0, 1, 2, 3};  // Up, Right, Down, Left
            shuffleArray(directions);

            for (int direction : directions) {
                int newX = currentX + dx[direction];
                int newY = currentY + dy[direction];

                if (isValidCell(newX, newY) && !visited[newX][newY]) {
                    stack.push(new Point(newX, newY));
                    visited[newX][newY] = true;
                }
            }
        }
    }

    private void generateMazeStep() {
        if (!timer.isRunning()) {
            return;
        }

        int x = (int) (Math.random() * SIZE);
        int y = (int) (Math.random() * SIZE);

        if (isValidCell(x, y) && !visited[x][y]) {
            visited[x][y] = true;
            maze[x][y] = 0;  // Set the cell as a path
        }

        // Check if the maze generation is completed
        boolean generationCompleted = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!visited[i][j]) {
                    generationCompleted = false;
                    break;
                }
            }
        }

        if (generationCompleted) {
            timer.stop();
        }
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private class MazePanel extends JPanel {
        private static final int CELL_SIZE = 20;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (maze[i][j] == 1) {
                        g.setColor(Color.BLACK);
                        g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MazeGenerationVisualizer visualizer = new MazeGenerationVisualizer();
                visualizer.setVisible(true);
            }
        });
    }
}
