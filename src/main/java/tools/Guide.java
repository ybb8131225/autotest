package tools;

import io.appium.java_client.AppiumDriver;

/**
 * Created by sunyingying on 2016/12/13.
 */
public class Guide {

    public void skipGuid(AppiumDriver driver, String described){
            driver.findElementByAccessibilityId(described).click();
            System.out.println("跳过引导");

    }
}
