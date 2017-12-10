package runcase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sunyingying on 2017/1/18.
 */
public class CreateDriver {

    public String packageName = "com.gotokeep.keep";
    public String activity = ".activity.SplashActivity";
    public DesiredCapabilities capabilities;
    public AppiumDriver driver;

    File classPathRoot = new File(System.getProperty("user.dir"));
    File appDir = new File(classPathRoot,"apps");
    File appAndroid = new File(appDir,"keep.apk");
    File appIOS = new File(appDir, "Keep.app");

    public void androidDriver(String url) throws MalformedURLException {
        capabilities = new DesiredCapabilities();
        //android
        capabilities.setCapability("deviceName", "android");
        capabilities.setCapability("app", appAndroid.getAbsolutePath());
        capabilities.setCapability("appPackage", packageName);
        capabilities.setCapability("appActivity", activity);
        capabilities.setCapability("noReset", true);

        //设置键盘在测试完成后自动回复原始状态，unicodekeyboard可以输入unicode字符
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

        driver = new AndroidDriver<AndroidElement>(new URL(url),capabilities);
    }

    public void iosDriver(String url) throws MalformedURLException {
        capabilities = new DesiredCapabilities();
        //ios sim
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("bundleId", "com.gotokeep.keep");
        capabilities.setCapability("udid", "2825875A-5809-42D8-ADE2-694226C020DB");
        capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("app", appIOS.getAbsolutePath());
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("locationServicesAuthorized", true);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("useNewWDA", true);

        //ios realdevice
//		capabilities.setCapability("platformName", "ios");
//		capabilities.setCapability("platformVersion", "9.3.1");
//		capabilities.setCapability("bundleId", "com.gotokeep.keep");
//		capabilities.setCapability("udid", "d89aed18c7d9c74d975845e1c9dfe76a0671906f");
//		capabilities.setCapability("deviceName", "iphone 5s");
//		capabilities.setCapability("bowserName", "");
//		capabilities.setCapability("automationName", "XCUITest");
//		capabilities.setCapability("autoAcceptAlerts", true);
//		capabilities.setCapability("locationServicesAuthorized", true);
//		capabilities.setCapability("noReset", true);
//		capabilities.setCapability("realDeviceLogger", "idevicesyslog");
//		capabilities.setCapability("showXcodeLog", true);


        //设置键盘在测试完成后自动回复原始状态，unicodekeyboard可以输入unicode字符
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

        driver = new IOSDriver(new URL(url), capabilities);
    }
}
