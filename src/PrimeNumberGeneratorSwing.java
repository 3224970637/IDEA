import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimeNumberGeneratorSwing extends JFrame {

    private JTextField inputField;
    private JTextArea outputArea;

    public PrimeNumberGeneratorSwing() {
        // 设置窗口标题
        super("Prime Number Generator");

        // 创建组件
        JLabel label = new JLabel("请输入一个整数 n：");
        inputField = new JTextField(10);
        JButton generateButton = new JButton("生成素数");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        // 设置布局管理器
        setLayout(new java.awt.FlowLayout());

        // 添加组件到容器
        add(label);
        add(inputField);
        add(generateButton);
        add(new JScrollPane(outputArea));

        // 添加按钮点击事件监听器
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePrimes();
            }
        });
    }

    // 生成素数并在文本区域中显示
    private void generatePrimes() {
        try {
            int n = Integer.parseInt(inputField.getText());
            outputArea.setText("");
            outputArea.append("在范围 2 到 " + n + " 内的素数有：\n");

            for (int i = 2; i <= n; i++) {
                if (isPrime(i)) {
                    outputArea.append(i + " ");
                }
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("请输入一个有效的整数！");
        }
    }

    // 判断是否为素数
    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        PrimeNumberGeneratorSwing frame = new PrimeNumberGeneratorSwing();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
