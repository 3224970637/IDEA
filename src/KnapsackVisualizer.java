import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KnapsackVisualizer extends JFrame {

    private int[] weights = {2, 2, 4, 5, 3};
    private int[] values = {3, 4, 8, 8, 6};
    private int capacity = 9;

    private int[] currentSelection;
    private int currentValue;
    private int currentWeight;
    private int currentItem;

    private DrawingPanel drawingPanel;
    private Timer timer;

    public KnapsackVisualizer() {
        setTitle("Knapsack Visualizer");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentSelection = new int[weights.length];
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startKnapsackAlgorithm();
            }
        });

        add(startButton, BorderLayout.SOUTH);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentItem < weights.length) {
                    knapsackBacktrack();
                    currentItem++;
                } else {
                    // Algorithm completed, stop the timer
                    timer.stop();
                }
            }
        });
    }

    private void startKnapsackAlgorithm() {
        currentSelection = new int[weights.length];
        currentValue = 0;
        currentWeight = 0;
        currentItem = 0;

        // Start the timer to simulate steps
        timer.start();
    }

    private void knapsackBacktrack() {
        if (currentItem < weights.length) {
            if (currentWeight + weights[currentItem] <= capacity) {
                currentSelection[currentItem] = 1;
                currentValue += values[currentItem];
                currentWeight += weights[currentItem];
            }
            // Trigger repaint to update the visualization
            drawingPanel.repaint();
        }
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int x = 50;
            int y = 50;

            // Draw rectangles representing selected items
            for (int i = 0; i < currentSelection.length; i++) {
                if (currentSelection[i] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, 30, 30);
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(i + 1), x + 10, y + 20);
                    x += 40;
                }
            }

            g.setColor(Color.BLACK);
            g.drawString("Current Value: " + currentValue, 20, 400);
            g.drawString("Current Weight: " + currentWeight, 20, 420);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KnapsackVisualizer().setVisible(true);
            }
        });
    }
}
