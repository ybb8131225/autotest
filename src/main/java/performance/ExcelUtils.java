package performance;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ExcelUtils {

	public static void writeXls(ArrayList<String> list,String url) {
		int countColumnNum = 1;
		// 创建excl文档
		HSSFWorkbook hwb = new HSSFWorkbook();
		// sheet 对应一个工作页
		HSSFSheet sheet = hwb.createSheet("cpu");
		HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
		HSSFCell firstcell = firstrow.createCell(0);
		firstcell.setCellValue("CPU");
//		HSSFCell[] firstcell = new HSSFCell[countColumnNum];
//		String[] firstRowName = new String[countColumnNum];
//		for (int j = 0; j < countColumnNum; j++) {
//			firstcell[j] = firstrow.createCell(j);
//			firstcell[j].setCellValue(new HSSFRichTextString(firstRowName[j]));
//		}

		for (int i = 0; i < list.size(); i++) {
			// 创建一行
			HSSFRow row = sheet.createRow(i + 1);
			HSSFCell xh = row.createCell(0);
			Double d = changeDouble(list.get(i));
			xh.setCellValue(d);
//			xh.setCellValue(list.get(i));
		}

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
	
	public static Double changeDouble(String str){
		
		String numStr = str.substring(0, str.length() - 1);
		Double num = Double.valueOf(numStr).doubleValue();
		
		return num;
		
	}

}
