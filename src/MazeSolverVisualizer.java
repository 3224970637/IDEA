import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MazeSolverVisualizer extends JFrame {

    private static final int SIZE = 20;
    private int[][] maze;
    private boolean[][] visited;
    private boolean[][] path;
    private int startX, startY, endX, endY;
    private Timer timer;

    private static final int[] dx = {0, 1, 0, -1}; // Direction changes for x-coordinate
    private static final int[] dy = {-1, 0, 1, 0}; // Direction changes for y-coordinate

    public MazeSolverVisualizer() {
        maze = new int[SIZE][SIZE];
        visited = new boolean[SIZE][SIZE];
        path = new boolean[SIZE][SIZE];

        setTitle("Maze Solver Visualizer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton generateButton = new JButton("Generate Maze");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMaze();
            }
        });

        JButton solveButton = new JButton("Solve Maze");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        buttonPanel.add(solveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        MazePanel mazePanel = new MazePanel();
        add(mazePanel, BorderLayout.CENTER);

        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazePanel.repaint();
            }
        });
    }

    private void generateMaze() {
        // Initialize maze, visited array, and path array
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                maze[i][j] = 1;  // Set all cells as walls initially
                visited[i][j] = false;
                path[i][j] = false;
            }
        }

        // Start from a random cell
        Random random = new Random();
        startX = random.nextInt(SIZE);
        startY = random.nextInt(SIZE);

        // Depth-First Search to generate the maze
        depthFirstSearch(startX, startY);

        // Set start and end points
        endX = SIZE - 1;
        endY = SIZE - 1;
        maze[startX][startY] = 0;
        maze[endX][endY] = 0;

        // Reset visited array for pathfinding
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                visited[i][j] = false;
            }
        }

        // Breadth-First Search to find the path
        breadthFirstSearch(startX, startY);

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

    private void breadthFirstSearch(int startX, int startY) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Point currentCell = queue.poll();
            int currentX = currentCell.x;
            int currentY = currentCell.y;

            int[] directions = {0, 1, 2, 3};  // Up, Right, Down, Left
            shuffleArray(directions);

            for (int direction : directions) {
                int newX = currentX + dx[direction];
                int newY = currentY + dy[direction];

                if (isValidCell(newX, newY) && !visited[newX][newY] && maze[newX][newY] == 0) {
                    queue.add(new Point(newX, newY));
                    visited[newX][newY] = true;
                    path[newX][newY] = true;
                }
            }
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

    private void solveMaze() {
        timer.stop();
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] result = breadthFirstSearchForPath();
                if (result != null) {
                    path[result[0]][result[1]] = true;
                } else {
                    timer.stop();
                }
                repaint();
            }
        });

        timer.start();
    }

    private int[] breadthFirstSearchForPath() {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(0, 0));
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Point currentCell = queue.poll();
            int currentX = currentCell.x;
            int currentY = currentCell.y;

            int[] directions = {0, 1, 2, 3};  // Up, Right, Down, Left
            shuffleArray(directions);

            for (int direction : directions) {
                int newX = currentX + dx[direction];
                int newY = currentY + dy[direction];

                if (isValidCell(newX, newY) && !visited[newX][newY] && maze[newX][newY] == 0) {
                    queue.add(new Point(newX, newY));
                    visited[newX][newY] = true;
                    if (newX == SIZE - 1 && newY == SIZE - 1) {
                        return new int[]{newX, newY};
                    }
                }
            }
        }

        return null;
    }

    private class MazePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int cellWidth = getWidth() / SIZE;
            int cellHeight = getHeight() / SIZE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    switch (maze[i][j]) {
                        case 1:
                            g.setColor(Color.BLACK);
                            break;
                        case 0:
                            g.setColor(Color.WHITE);
                            break;
                        case 3:
                            g.setColor(Color.GREEN);
                            break;
                        case 4:
                            g.setColor(Color.RED);
                            break;
                    }

                    g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                    if (path[i][j]) {
                        g.setColor(Color.BLUE);
                        g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }

                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MazeSolverVisualizer visualizer = new MazeSolverVisualizer();
                visualizer.setVisible(true);
            }
        });
    }
}
