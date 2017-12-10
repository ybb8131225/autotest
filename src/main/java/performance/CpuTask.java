package performance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CpuTask {

	public static String getCpu(String packageName) throws IOException {
		String cpuStr = null;
//		ArrayList<String> cpuArr = new ArrayList<String>();
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec(
				"/Users/sunyingying/Library/Android/sdk/platform-tools/adb shell dumpsys cpuinfo |grep " + packageName);
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
			System.out.println("str1=====" + str1);
			String str2 = str1.substring(str1.indexOf(packageName), str1.indexOf(packageName) + 28);
			System.out.println("str2=====" + str2);
			String[] cpuUser = new String[3];
			cpuUser = str2.split(" ");
//			cpuArr.add(cpuUser[1]);
			cpuStr = cpuUser[1];
//			writeLog("/Users/sunyingying/Documents/workspace/KeepTestController/cpuLog",cpuStr);

		} catch (InterruptedException e) {
			System.err.println(e);
		} finally {
			try {
				proc.destroy();
			} catch (Exception e2) {
			}
		}
		return cpuStr;
	}
	
	public String runTask() {
		// TODO Auto-generated method stub
//		ArrayList<String> cpuArr = null;
		try {
			String cpuStr = CpuTask.getCpu("com.gotokeep.keep");
//			cpuStr = CpuTask.getCpu("com.gotokeep.keep");
			System.out.println("cpu：" + cpuStr);
			return cpuStr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}


}
