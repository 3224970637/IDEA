import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileCharacterAndLineCounter {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\ww\\Desktop\\sample.txt"; //设置文件路径

        int characterCount = 0;
        int lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                characterCount += line.length() + 1; //加1表示换行符
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("字符数：" + characterCount);
        System.out.println("行数：" + lineCount);
    }
}
