import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DPVisualizerLIS extends JFrame {

    private int[] sequence;
    private int[] lisLength;
    private int currentStep;

    private Timer timer;

    private LISPanel lisPanel; // 直接嵌套LISPanel

    public DPVisualizerLIS() {
        setTitle("Dynamic Programming Visualizer - Longest Increasing Subsequence");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sequence = generateRandomSequence(15); // 生成包含15个元素的随机序列
        lisLength = new int[sequence.length];
        currentStep = 0;

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLISAlgorithm();
            }
        });

        add(startButton, BorderLayout.SOUTH);

        lisPanel = new LISPanel(); // 创建 LISPanel 实例
        add(lisPanel, BorderLayout.CENTER); // 直接添加 LISPanel 到 JFrame

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep < sequence.length) {
                    calculateLIS(currentStep);
                    currentStep++;
                } else {
                    // Algorithm completed, stop the timer
                    timer.stop();
                }

                // Trigger repaint to update the visualization
                lisPanel.repaint(); // 更新LISPanel
            }
        });
    }

    private void startLISAlgorithm() {
        sequence = generateRandomSequence(15); // 重新生成随机序列
        lisLength = new int[sequence.length];
        currentStep = 0;

        // Start the timer to simulate steps
        timer.start();
    }

    private void calculateLIS(int endIndex) {
        lisLength[endIndex] = 1; // LIS长度至少为1

        for (int i = 0; i < endIndex; i++) {
            if (sequence[i] < sequence[endIndex] && lisLength[i] + 1 > lisLength[endIndex]) {
                lisLength[endIndex] = lisLength[i] + 1;
            }
        }
    }

    private int[] generateRandomSequence(int size) {
        int[] sequence = new int[size];
        for (int i = 0; i < size; i++) {
            sequence[i] = (int) (Math.random() * 50);
        }
        return sequence;
    }

    private class LISPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int barWidth = getWidth() / sequence.length;

            // Draw bars representing the sequence
            for (int i = 0; i < sequence.length; i++) {
                int barHeight = sequence[i] * 5; // Scale the height for better visualization
                int x = i * barWidth;
                int y = getHeight() - barHeight;
                g.setColor(Color.BLUE);
                g.fillRect(x, y, barWidth, barHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);
            }

            // Draw LIS lengths
            for (int i = 0; i < sequence.length; i++) {
                int x = i * barWidth + barWidth / 2 - 10;
                int y = getHeight() - lisLength[i] * 20;
                g.setColor(Color.RED);
                g.drawString(Integer.toString(lisLength[i]), x, y);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DPVisualizerLIS visualizer = new DPVisualizerLIS();
                visualizer.setVisible(true);
            }
        });
    }
}
