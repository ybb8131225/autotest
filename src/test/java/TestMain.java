/**
 * Created by sunyingying on 2017/2/7.
 */

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;

public class TestMain {

    public static String packageName = "com.gotokeep.keep";
    public static String activity = ".activity.SplashActivity";
    public static AppiumDriver driver;

    @BeforeClass
    public static void setUpClass() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File classPathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classPathRoot,"apps");
        File app = new File(appDir,"keep.apk");

        capabilities.setCapability("deviceName", "android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", packageName);
        capabilities.setCapability("appActivity", activity);
        capabilities.setCapability("noReset", true);

        //设置键盘在测试完成后自动回复原始状态，unicodekeyboard可以输入unicode字符
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

        driver = new AndroidDriver<AndroidElement>(new URL("http://10.2.0.215:1234/wd/hub"),capabilities);
    }

    @Test
    public void test() throws IOException {
        driver.findElementById("com.gotokeep.keep:id/tablin2").click();
    }

    @AfterClass
    public static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
}
