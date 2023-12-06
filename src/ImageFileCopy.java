import java.io.*;

public class ImageFileCopy {
    public static void main(String[] args) {
        String sourceFilePath = "C:\\Users\\ww\\Desktop\\大师段位.png"; // 源图片文件路径
        String byteStreamCopyPath = "C:\\Users\\ww\\Desktop\\byteStreamCopy\\byteStreamCopy.png"; // 字节流复制后的文件路径
        String bufferedStreamCopyPath = "C:\\Users\\ww\\Desktop\\bufferedStreamCopy\\bufferedStreamCopy.png"; // 字节缓冲流复制后的文件路径

        // 复制并测量字节流复制时间
        long byteStreamStartTime = System.currentTimeMillis();
        copyUsingByteStream(sourceFilePath, byteStreamCopyPath);
        long byteStreamEndTime = System.currentTimeMillis();
        long byteStreamTime = byteStreamEndTime - byteStreamStartTime;

        // 复制并测量字节缓冲流复制时间
        long bufferedStreamStartTime = System.currentTimeMillis();
        copyUsingBufferedStream(sourceFilePath, bufferedStreamCopyPath);
        long bufferedStreamEndTime = System.currentTimeMillis();
        long bufferedStreamTime = bufferedStreamEndTime - bufferedStreamStartTime;

        System.out.println("使用字节流复制耗时：" + byteStreamTime + " 毫秒");
        System.out.println("使用字节缓冲流复制耗时：" + bufferedStreamTime + " 毫秒");
    }

    public static void copyUsingByteStream(String sourceFilePath, String copyFilePath) {
        try (InputStream inputStream = new FileInputStream(sourceFilePath);
             OutputStream outputStream = new FileOutputStream(copyFilePath)) {
            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                outputStream.write(byteRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyUsingBufferedStream(String sourceFilePath, String copyFilePath) {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFilePath));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(copyFilePath))) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
