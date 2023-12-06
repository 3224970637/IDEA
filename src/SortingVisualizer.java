import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SortingVisualizer extends JFrame {

    private static final int ARRAY_SIZE = 50;
    private int[] array;
    private int currentIndex;
    private Timer timer;

    public SortingVisualizer() {
        array = generateRandomArray(ARRAY_SIZE);
        currentIndex = 0;

        setTitle("Sorting Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start Sorting");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSorting();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        add(buttonPanel, BorderLayout.SOUTH);

        SortingPanel sortingPanel = new SortingPanel();
        add(sortingPanel, BorderLayout.CENTER);

        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < ARRAY_SIZE - 1) {
                    bubbleSortStep();
                    currentIndex++;
                } else {
                    timer.stop();
                    currentIndex = 0;
                }
                sortingPanel.repaint();
            }
        });
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(400) + 50; // Set random heights between 50 and 450
        }
        return arr;
    }

    private void startSorting() {
        currentIndex = 0;
        timer.start();
    }

    private void bubbleSortStep() {
        if (array[currentIndex] > array[currentIndex + 1]) {
            // Swap elements
            int temp = array[currentIndex];
            array[currentIndex] = array[currentIndex + 1];
            array[currentIndex + 1] = temp;
        }
    }

    private class SortingPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int barWidth = getWidth() / ARRAY_SIZE;

            for (int i = 0; i < ARRAY_SIZE; i++) {
                if (i == currentIndex || i == currentIndex + 1) {
                    g.setColor(Color.RED); // Highlight the elements being compared
                } else {
                    g.setColor(Color.BLUE);
                }

                int barHeight = array[i];
                int x = i * barWidth;
                int y = getHeight() - barHeight; // Draw from the bottom up
                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SortingVisualizer visualizer = new SortingVisualizer();
                visualizer.setVisible(true);
            }
        });
    }
}
