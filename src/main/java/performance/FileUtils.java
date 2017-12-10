package performance;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

	public static void write(String data, String url) throws IOException {
		FileWriter fw = new FileWriter(url);
		fw.write("var cpu = ");
		fw.write("'" + data + "';");
		fw.write("\n");
		fw.write("var obj = eval (\"(\" + cpu + \")\");");
		fw.flush();
		fw.close();

	}

	public static void write(HSSFWorkbook hwb,String url) {
		// 创建文件输出流，准备输出电子表格
		OutputStream out = null;
		try {
			out = new FileOutputStream(url);
			hwb.write(out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
