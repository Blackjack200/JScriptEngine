package site.misaka.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

	public static void main(String args[]) throws IOException {
		System.out.println(file_get_content(new File("/Users/sunxiaochuan/Desktop/Nukkit/server.properties")));
	}
}
