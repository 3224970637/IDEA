import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SortApp {
    private JFrame frame;
    private JButton sortButton;
    private JButton closeButton;
    private JTextField inputField;
    private JTextField resultField;

    public SortApp() {
        frame = new JFrame("排序应用程序");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        inputField = new JTextField("在此输入一组数据");
        resultField = new JTextField();
        resultField.setEditable(false);

        sortButton = new JButton("排序");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortData();
            }
        });

        closeButton = new JButton("关闭");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(new JLabel("输入数据:"));
        frame.add(inputField);
        frame.add(new JLabel("排序结果:"));
        frame.add(resultField);
        frame.add(sortButton);
        frame.add(closeButton);

        frame.pack();
        frame.setVisible(true);
    }

    private void sortData() {
        String inputData = inputField.getText();
        String[] data = inputData.split("\\s+"); // 假定数据用空格分隔
        Arrays.sort(data); // 排序数据
        String sortedData = String.join(" ", data); // 将排序后的数据重新组合
        resultField.setText(sortedData);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SortApp();
            }
        });
    }
}
