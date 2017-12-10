package operate;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.Reporter;
import tools.Elements;
import tools.Sleep;
import tools.WaitElement;
import runcase.Main;
import tools.Swipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OperateCommon {

	private AppiumDriver driver;
	private Sleep sleep;
	private Swipe swipe;

	int width, height;
	
	public OperateCommon(){
		driver = Main.driver;
		sleep = new Sleep();
		swipe = new Swipe();
		height = driver.manage().window().getSize().height;
	}

	public static void logReport(String logMessage){
		Reporter.log(logMessage + "<br>");
        System.out.println(logMessage);
	}

	public void skipSpread(){
		try{
			if(driver instanceof AndroidDriver){
				driver.findElementByXPath(Elements.skipAndroid).click();
			}else{
				driver.findElementByXPath(Elements.skipIOS).click();
			}
			logReport("有开屏幕广告，跳过广告");
		}catch (Exception e){
			logReport("没有开屏广告");
		}
	}

	public void skipGuid(){
		try{
			WaitElement.wait(driver, 10, Elements.runGuide);
			logReport("有引导，跳过引导");
		}catch (Exception e){
			logReport("没有引导");
		}
	}

	public void swipeUpToFind(String element){
		boolean found = true;
		while(found){
			try{
				WaitElement.wait(driver, 10, element);
				found = false;
				logReport("找到了元素");
			}catch (Exception e){
				swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 3, 1000);
				logReport("滑动屏幕寻找元素");
			}
		}
	}

	public void swipeDownToFind(String element){
		boolean found = true;
		while(found){
			try{
				WaitElement.wait(driver, 10, element);
				found = false;
				logReport("找到了元素");
			}catch (Exception e){
				swipe.swipeOwn(driver, width * 1 / 2, height * 1 / 3, width * 1 / 2, height * 4 / 4, 1000);
				logReport("滑动屏幕寻找元素");
			}
		}
	}

	public void loginerror(){
		sleep.sleep(10000);
		driver.findElement(By.name("登录")).click();
        driver.findElementById("com.gotokeep.keep:id/login_btn").click();
        driver.findElement(By.name("请输入正确的手机号"));
        sleep.sleep(2000);
	}
	
	public void clickFrameLayout(){
		sleep.sleep(2000);
		driver.findElement(By.className("android.widget.FrameLayout")).click();
		driver.findElement(By.className("android.widget.FrameLayout")).click();
	}

	public void waitSpread(){
		//同一个测试中用了多个driver对象，应该传一个driver对象，不浪费资源
		WaitElement.waitElement(driver, 60, Elements.tablin1);
	}

	public void screenShot(){
		String imgFilePath = "ScreenShot";
		File imgFile = new File(imgFilePath);
		if(!imgFile.exists()){
			imgFile.mkdir();
		}
		String screenPath = imgFilePath + "/" + getCurrentDateTime() + ".jpg";
		File screenFile = new File(screenPath);

		File screenShotFile = driver.getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(screenShotFile.toPath(), screenFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCurrentDateTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
		return df.format(new Date());
	}
	

}
