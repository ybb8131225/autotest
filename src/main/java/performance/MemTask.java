package performance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemTask {

	public static String getMemory(String packageName) throws IOException, InterruptedException {

		String str3 = null;
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec("/Users/sunyingying/Library/Android/sdk/platform-tools/adb shell dumpsys meminfo " + packageName + " | grep TOTAL");
		try {

			if (proc.waitFor() != 0) {
				System.err.println("exit value = " + proc.exitValue());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line + " ");
			}
			String str1 = stringBuffer.toString();
//			System.out.println("str1===========" + str1);
			String[] memMessage = str1.split("    ");
//			for(String str : memMessage){
//				System.out.println(str);
//			}
			str3 = memMessage[3];
//			System.out.println("内存：" + str3);
		} catch (InterruptedException e) {
			System.err.println(e);
		} finally {
			try {
				proc.destroy();
			} catch (Exception e2) {
			}
		}
		return str3;
	}
	
	public String runTask(){

		String memStr;
		try {
			memStr = MemTask.getMemory("com.gotokeep.keep");
			System.out.println("内存："+memStr);
			return memStr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
//	public static void main(String[] str){
//		MemTask mem = new MemTask();
//		try {
//			mem.getMemory("com.gotokeep.keep");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
