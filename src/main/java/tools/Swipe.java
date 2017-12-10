package tools;

import io.appium.java_client.AppiumDriver;

/**
 * Created by sunyingying on 2016/12/8.
 */
public class Swipe {
    Sleep sleep = new Sleep();

    public void swipeOwn(AppiumDriver driver, int startx, int starty, int endx, int endy, int duration){
        try {
            driver.swipe(startx, starty, endx, endy, duration);
            sleep.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
