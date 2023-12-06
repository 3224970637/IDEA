import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class GraphAlgorithmVisualizer extends JFrame {

    private int size;
    private boolean[][] graph;
    private boolean[] visitedDFS, visitedBFS;
    private Timer timer;

    public GraphAlgorithmVisualizer(int size) {
        this.size = size;
        graph = new boolean[size][size];
        visitedDFS = new boolean[size];
        visitedBFS = new boolean[size];

        setTitle("Graph Algorithm Visualizer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton dfsButton = new JButton("Run DFS");
        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runDFS();
            }
        });

        JButton bfsButton = new JButton("Run BFS");
        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runBFS();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);

        add(buttonPanel, BorderLayout.NORTH);

        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            int currentVertex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!visitedDFS[currentVertex]) {
                    runDFS(currentVertex);
                    currentVertex++;
                } else if (!visitedBFS[currentVertex]) {
                    runBFS(currentVertex);
                    currentVertex++;
                }

                if (currentVertex == size) {
                    timer.stop();
                }

                graphPanel.repaint();
            }
        });
    }

    private void runDFS() {
        for (int i = 0; i < size; i++) {
            visitedDFS[i] = false;
        }

        timer.start();
    }

    private void runDFS(int vertex) {
        visitedDFS[vertex] = true;

        for (int i = 0; i < size; i++) {
            if (graph[vertex][i] && !visitedDFS[i]) {
                graph[i][i] = true;
                runDFS(i);
            }
        }
    }

    private void runBFS() {
        for (int i = 0; i < size; i++) {
            visitedBFS[i] = false;
        }

        timer.start();
    }

    private void runBFS(int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visitedBFS[startVertex] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int i = 0; i < size; i++) {
                if (graph[vertex][i] && !visitedBFS[i]) {
                    graph[i][i] = true;
                    visitedBFS[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    private class GraphPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int cellWidth = getWidth() / size;
            int cellHeight = getHeight() / size;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (graph[i][j]) {
                        g.setColor(Color.BLACK);
                        g.drawLine(i * cellWidth + cellWidth / 2, j * cellHeight + cellHeight / 2,
                                j * cellWidth + cellWidth / 2, i * cellHeight + cellHeight / 2);
                    }
                }
            }

            g.setColor(Color.RED);
            for (int i = 0; i < size; i++) {
                g.drawOval(i * cellWidth + cellWidth / 4, i * cellHeight + cellHeight / 4,
                        cellWidth / 2, cellHeight / 2);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int size = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of vertices:"));
                GraphAlgorithmVisualizer visualizer = new GraphAlgorithmVisualizer(size);
                visualizer.setVisible(true);
            }
        });
    }
}
