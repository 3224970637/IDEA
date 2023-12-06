import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickSortVisualizerSwing extends JFrame {

    private static final int ARRAY_SIZE = 50;
    private static final int BAR_WIDTH = 800 / ARRAY_SIZE;

    private List<Integer> array;
    private int pivotIndex;
    private int step;

    public QuickSortVisualizerSwing() {
        array = generateRandomArray(ARRAY_SIZE);
        pivotIndex = -1;
        step = 0;

        setTitle("Quick Sort Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        QuickSortPanel quickSortPanel = new QuickSortPanel();
        add(quickSortPanel);

        Timer timer = new Timer(100, e -> quickSortStep());
        timer.start();
    }

    private List<Integer> generateRandomArray(int size) {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }
        List<Integer> list = Arrays.asList(arr);
        Collections.shuffle(list);
        return list;
    }

    private void quickSortStep() {
        if (step < array.size()) {
            pivotIndex = partition(0, array.size() - 1);
            step++;
            repaint();
        }
    }

    private int partition(int low, int high) {
        int pivot = array.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array.get(j) < pivot) {
                i++;
                Collections.swap(array, i, j);
            }
        }

        Collections.swap(array, i + 1, high);

        return i + 1;
    }

    private class QuickSortPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < array.size(); i++) {
                if (i == pivotIndex) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLUE);
                }

                int barHeight = array.get(i) * 6; // Scale the height for better visualization
                int x = i * BAR_WIDTH;
                int y = getHeight() - barHeight;
                g.fillRect(x, y, BAR_WIDTH, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, BAR_WIDTH, barHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuickSortVisualizerSwing visualizer = new QuickSortVisualizerSwing();
            visualizer.setVisible(true);
        });
    }
}
