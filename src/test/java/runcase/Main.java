package runcase;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import performance.TaskControl;
import testcase.Login;
import testcase.TcCase;
import testcase.TestPerformance;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class Main {

//	private static Main instance = new Main();
	public static AppiumDriver driver;
	CreateDriver createDriver;
	Timer timer;
	public TcCase tcCase;
	public Login login;
	public TestPerformance testPerformance;
	TaskControl task;

	private Main(){}
	
//	public static Main getInstance(){
//		if(instance == null){
//			instance = new Main();
//			return instance;
//		}
//		return instance;
//	}
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"channel", "url"})
	public void setup(String channel, String url) throws Exception{
		//删除上一次测试时截屏的图片
		String imgFilePath = "ScreenShot";
		File imgFile = new File(imgFilePath);
		deleteDir(imgFile);

		createDriver = new CreateDriver();
		if(channel.equals("android")){
			createDriver.androidDriver(url);
		}
		if(channel.equals("ios")){
			createDriver.iosDriver(url);
		}
		driver = createDriver.driver;

		login = new Login();
		tcCase = new TcCase();
		testPerformance = new TestPerformance();

		//性能测试
//		timer = new Timer();
//		task = new TaskControl();
//
//		task.init(-1);
//		timer.schedule(task,5000,1000);

	}

	@Test (groups = {"login", "all"})
	@Parameters({"username", "password"})
	public void testLogin(String username, String password){
		login.testLogin(username, password);
	}

	@Test(groups = {"OutTrain", "all"})
	public void testOutTrain(){
		tcCase.testOutTrain();
	}

	@Test(groups = {"Train", "all"})
	public void testTrain(){
		tcCase.testTrain();
	}

	@Test(groups = {"Train", "all"})
	public void testPublish(){
		tcCase.testPublish();
	}

	@Test(groups = {"Train", "all"})
	public void testFinishTrain(){
		tcCase.testHistoryTrain();
	}

	@Test (groups = {"KeepSchedule", "all"})
	public void testSchedule(){
		tcCase.testSchedule();
	}

	@Test (groups = {"KeepSchedule", "all"})
	public void testNextSchedule(){
		tcCase.testNextSchedule();
	}

	@Test (groups = {"KeepSchedule", "all"})
	public void testOutSchedule(){
		tcCase.testOutSchedule();
	}

	@Test(groups = {"DefinedSchedule", "all"})
	public void testDefinedSchedule(){
		tcCase.testDefinedSchedule();
	}

	@Test (groups = {"TestPerformance", "all"})
	public void testSystrace(){
		testPerformance.testSystrace();
	}

//	@Test (priority = 2)
//	public void testTrain() throws Exception{

//		timer = new Timer();
//		task = new TaskControl();
//		
//		//性能测试
//		task.init(-1);
//		timer.schedule(task,0,1000);
		
//		testcase tc = new testcase();
//		tc.testTrain();
		
//	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	@AfterClass
	public void thearDown(){
		if(driver!=null){
			driver.quit();
//			timer.cancel();
//			try {
////				task.buildJson();
//				task.write();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	

}
