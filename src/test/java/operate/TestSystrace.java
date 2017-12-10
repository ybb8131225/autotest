package operate;

import io.appium.java_client.AppiumDriver;
import runcase.Main;
import tools.Sleep;
import tools.Swipe;

/**
 * Created by sunyingying on 2017/3/14.
 */
public class TestSystrace {

    private OperateCommon operateCommon;
    private Sleep sleep;
    private AppiumDriver driver;
    private Swipe swipe;

    int width, height;

    public TestSystrace(){
        operateCommon = new OperateCommon();
        sleep = new Sleep();
        driver = Main.driver;
        swipe = new Swipe();
        width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    }

    public void swipeInHome(){

        sleep.sleep(10000);
        int i = 0;

        while(i <= 10){
            swipe.swipeOwn(driver, width * 1/2, height * 3/4, width * 1/2, height * 1/4 , 1000);
            i++;
        }

    }
}

