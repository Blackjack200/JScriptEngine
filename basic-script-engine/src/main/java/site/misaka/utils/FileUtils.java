package site.misaka.utils;


import java.io.*;

public class FileUtils {
    public static String file_get_content(File file) throws IOException {
        byte buf[] = new byte[512];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileInputStream fileStream = new FileInputStream(file);

        int len = buf.length;
        while ((len = fileStream.read(buf, 0, len)) != -1) {
            output.write(buf, 0, len);
        }

        return output.toString();
    }

    public static boolean file_put_content(String path, String content) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(content);
            out.close();
            return true;
        } catch (IOException ignored) {

        }
        return false;
    }
}
