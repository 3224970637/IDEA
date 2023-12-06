import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DijkstraAlgorithmVisualizer extends JFrame {

    private static final int SIZE = 5;
    private List<Edge>[] graph;
    private int[] distance;
    private boolean[] visited;
    private Timer timer;

    public DijkstraAlgorithmVisualizer() {
        graph = new List[SIZE];
        for (int i = 0; i < SIZE; i++) {
            graph[i] = new ArrayList<>();
        }

        distance = new int[SIZE];
        Arrays.fill(distance, Integer.MAX_VALUE);
        visited = new boolean[SIZE];

        setTitle("Dijkstra Algorithm Visualizer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Run Dijkstra Algorithm");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runDijkstraAlgorithm();
            }
        });

        add(startButton, BorderLayout.SOUTH);

        DijkstraPanel dijkstraPanel = new DijkstraPanel();
        add(dijkstraPanel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            int currentVertex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                visited[currentVertex] = true;

                for (Edge edge : graph[currentVertex]) {
                    int neighbor = edge.destination;
                    if (!visited[neighbor] && distance[currentVertex] + edge.weight < distance[neighbor]) {
                        distance[neighbor] = distance[currentVertex] + edge.weight;
                    }
                }

                currentVertex++;

                if (currentVertex == SIZE) {
                    timer.stop();
                }

                dijkstraPanel.repaint();
            }
        });
    }

    private void runDijkstraAlgorithm() {
        for (int i = 0; i < SIZE; i++) {
            visited[i] = false;
        }

        distance[0] = 0;

        timer.stop();
        timer.start();
    }

    private class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private class DijkstraPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int cellWidth = getWidth() / SIZE;
            int cellHeight = getHeight() / SIZE;

            for (int i = 0; i < SIZE; i++) {
                for (Edge edge : graph[i]) {
                    int j = edge.destination;

                    g.setColor(Color.BLACK);
                    g.drawLine(i * cellWidth + cellWidth / 2, i * cellHeight + cellHeight / 2,
                            j * cellWidth + cellWidth / 2, j * cellHeight + cellHeight / 2);

                    g.drawString(Integer.toString(edge.weight), (i * cellWidth + j * cellWidth) / 2,
                            (i * cellHeight + j * cellHeight) / 2);
                }
            }

            g.setColor(Color.RED);
            for (int i = 0; i < SIZE; i++) {
                g.drawOval(i * cellWidth + cellWidth / 4, i * cellHeight + cellHeight / 4,
                        cellWidth / 2, cellHeight / 2);
                g.drawString(Integer.toString(distance[i]), i * cellWidth + cellWidth / 2, i * cellHeight + cellHeight / 2);
            }
        }
    }

    public static void main(String[] args) {
        DijkstraAlgorithmVisualizer visualizer = new DijkstraAlgorithmVisualizer();
        visualizer.graph[0].add(visualizer.new Edge(1, 2));
        visualizer.graph[0].add(visualizer.new Edge(2, 4));
        visualizer.graph[1].add(visualizer.new Edge(2, 1));
        visualizer.graph[1].add(visualizer.new Edge(3, 7));
        visualizer.graph[2].add(visualizer.new Edge(3, 1));
        visualizer.graph[3].add(visualizer.new Edge(4, 3));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                visualizer.setVisible(true);
            }
        });
    }
}
