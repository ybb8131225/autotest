package performance;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskControl extends TimerTask {

	private static long DebugTime = -1;
//	private static String timenow;
//	private static JSONObject jo;

	private CpuTask cpuTask;
	private MemTask memTask;

//	 private static StringBuffer cpuTaskResult = new StringBuffer();
	 private static ArrayList<String> cpuArrResult = new ArrayList<String>();
	 
//	private static ArrayList<Map> cpuMapResult = new ArrayList<Map>();
//	private static ArrayList<String> memArrResult = new ArrayList<String>();
//	private static ArrayList<Map> memMapResult = new ArrayList<Map>();
//	private static Map<String, String> cpuMap = new HashMap<String, String>();
//	private static Map<String, String> memMap = new HashMap<String, String>();

	public void init(long debugTime) {
		DebugTime = debugTime;
		cpuTask = new CpuTask();
//		memTask = new MemTask();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//map定义在外面就都是一样的数据为什么
//		Map<String, String> cpuMap = new HashMap<String, String>();
//		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//		timenow = df.format(new Date());
//		System.out.println("time: " + timenow);
//		cpuMap.put("time", timenow);
//		cpuMap.put("cpu", cpuTask.runTask());
//		cpuMapResult.add(cpuMap);
		cpuArrResult.add(cpuTask.runTask());
//		memArrResult.add(memTask.runTask());
//		memMap.put("time", timenow);
//		memMap.put("value", memTask.runTask());
//		memMapResult.add(memMap);

		if (DebugTime > 0) {
			DebugTime--;
		}
	}

	/**
	 * write里应该放入参数，方便其它数据调用write
	 * 
	 * @throws IOException
	 */
	public static void write() throws IOException {

		System.out.println("start write");
		File resultCpu = new File("/Users/sunyingying/autotest/KeepTestApp/testResult");
		if(!resultCpu.exists()){
			resultCpu.mkdir();
		}
		 ExcelUtils.writeXls(cpuArrResult,
		 "/Users/sunyingying/autotest/KeepTestApp/testResult/cpu.xls");
//		 ExcelUtils.writeXls(memArrResult,
//		 "/Users/sunyingying/autotest/KeepTest/testResult/mem.xls");
//		System.out.println("start write");
//		 FileUtils.write(jo.toString(),
//		 "/Users/sunyingying/autotest/KeepTest/testResult/Log.json");
//		 FileUtils.write(jo.toString(),
//				 "/Users/sunyingying/autotest/KeepTestApp/testResult/cpu.js");
	}

	public static long getLeftTime() {
		return DebugTime;
	}

//	public static void buildJson() {
//
//		jo = new JSONObject();
//		JSONArray jocpu = JSONArray.fromObject(cpuMapResult);
////		JSONArray jomem = JSONArray.fromObject(memMapResult);
//
//		jo.put("cpu", jocpu);
////		jo.put("mem", jomem);
//		System.out.println(jo.toString());
//
//	}

	 public static void main(String[] str){

	 	 Timer timer;
	 	 timer = new Timer();
	 	 TaskControl task = new TaskControl();

	 	 //性能测试
	 	 task.init(1);
	 	 timer.schedule(task, 0, 1000);

		 try {
			 write();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }

}
