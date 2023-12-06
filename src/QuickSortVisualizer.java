import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class QuickSortVisualizer extends JFrame {

    private static final int ARRAY_SIZE = 50;
    private int[] array;
    private int[] indices;
    private int pivotIndex;
    private Timer timer;

    public QuickSortVisualizer() {
        array = generateRandomArray(ARRAY_SIZE);
        indices = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            indices[i] = i;
        }
        pivotIndex = -1;

        setTitle("Quick Sort Visualizer");
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

        QuickSortPanel quickSortPanel = new QuickSortPanel();
        add(quickSortPanel, BorderLayout.CENTER);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pivotIndex == -1) {
                    quickSortStep(0, ARRAY_SIZE - 1);
                } else {
                    timer.stop();
                }
                quickSortPanel.repaint();
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
        shuffleArray();
        quickSort(0, ARRAY_SIZE - 1);
        timer.start();
    }

    private void shuffleArray() {
        Random random = new Random();
        for (int i = ARRAY_SIZE - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;

            int tempIndex = indices[i];
            indices[i] = indices[j];
            indices[j] = tempIndex;
        }
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                // Swap array[i] and array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                // Swap indices[i] and indices[j]
                int tempIndex = indices[i];
                indices[i] = indices[j];
                indices[j] = tempIndex;
            }
        }

        // Swap array[i+1] and array[high]
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        // Swap indices[i+1] and indices[high]
        int tempIndex = indices[i + 1];
        indices[i + 1] = indices[high];
        indices[high] = tempIndex;

        return i + 1;
    }

    private void quickSortStep(int low, int high) {
        if (low < high) {
            pivotIndex = partition(low, high);
            timer.restart();
        } else {
            pivotIndex = -1;
        }
    }

    private class QuickSortPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int barWidth = getWidth() / ARRAY_SIZE;

            for (int i = 0; i < ARRAY_SIZE; i++) {
                if (i == pivotIndex) {
                    g.setColor(Color.RED); // Highlight the pivot element
                } else {
                    g.setColor(Color.BLUE);
                }

                int barHeight = array[i];
                int x = indices[i] * barWidth;
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
                QuickSortVisualizer visualizer = new QuickSortVisualizer();
                visualizer.setVisible(true);
            }
        });
    }
}
