package operate;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import runcase.Main;
import tools.Elements;
import tools.Sleep;
import tools.WaitElement;

/**
 * Created by sunyingying on 2017/3/17.
 */
public class SUPublish {

    private AppiumDriver driver;
    private Sleep sleep;
    private OperateCommon operateCommon;

    int width, height;

    public SUPublish(){
        driver = Main.driver;
        sleep = new Sleep();
        operateCommon = new OperateCommon();

        width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    }

    /*
    训练完后发布动态
     */
    public void trainingPublish(){
        OperateCommon.logReport("Start 发布动态");
        sleep.sleep(3000);
        try{
            if(driver instanceof AndroidDriver){
                driver.findElementByXPath(Elements.trainingCongratulationsAndroid);
            }
            else{
                driver.findElementByXPath(Elements.trainingCongratulationsIOS);
            }
            operateCommon.screenShot();
            driver.pinch(width * 1 / 2, height * 1 / 8);
            OperateCommon.logReport("有恭喜你的页面提示，点击其他地方");
        }catch (Exception e){
            operateCommon.screenShot();
            OperateCommon.logReport("没有恭喜你的页面提示");
        }
        WaitElement.wait(driver, 10, Elements.timeline_input);
        operateCommon.screenShot();
        OperateCommon.logReport("点击输入框，打开发布动态界面");
        sleep.sleep(3000);
        //点击允许获取地理位置
        try{
            if(driver instanceof AndroidDriver){
                driver.findElementByXPath(Elements.allowAndroid).click();
            }else{
                driver.findElementByXPath(Elements.allowIOS).click();
            }
            OperateCommon.logReport("允许获取地理位置");
        }catch (Exception e){
            OperateCommon.logReport("没有地理位置请求权限");
        }
        WaitElement.waitElement(driver, 10, Elements.feed_input);
        WebElement feed_input = driver.findElementById(Elements.feed_input);
        feed_input.sendKeys("good");
        operateCommon.screenShot();
        //添加图片

        driver.findElementById(Elements.feed_publish).click();
        WaitElement.wait(driver, 20, Elements.publish_close);
        sleep.sleep(3000);
        //随机点赞
        try {
            if(driver.findElementById(Elements.praise_confirm)!=null){
                driver.pinch(width * 1 / 2, height * 1 / 8);
//                if(driver instanceof AndroidDriver){
//                    getPublishTrainInfo();
//                }else{
//                    getPublishTrainInfoIos();
//                }
                getPublishTrainInfo();
            }

        }catch (Exception e){
//            if(driver instanceof AndroidDriver){
//                getPublishTrainInfo();
//            }else{
//                getPublishTrainInfoIos();
//            }
            getPublishTrainInfo();
        }
        Reporter.log("校验完发布动态后的训练名称和次数 <br>");
        System.out.println("校验完发布动态后的训练名称和次数");
        operateCommon.screenShot();
    }

    public void getPublishTrainInfo(){

        WebElement publishTrainNameElement;
        if(driver instanceof AndroidDriver){
            publishTrainNameElement = WaitElement.waitElement(driver, 10, Elements.publish_train_times);
        } else{
            publishTrainNameElement = WaitElement.waitElement(driver, 10, Elements.publish_train_name);
        }
        operateCommon.screenShot();
        //获取发布后训练名字
        String publishTrainInfo = publishTrainNameElement.getText();
        String pulishTrainNameAndTimes = publishTrainInfo.substring(2);
        System.out.println(pulishTrainNameAndTimes);
        String[] NameAndTimesArr = pulishTrainNameAndTimes.split("第");
        String publishTrainName = NameAndTimesArr[0];
        Reporter.log("发布动态训练的名字：" + publishTrainName + "<br>");
        System.out.println("发布动态训练的名字：" + publishTrainName);
//        org.testng.Assert.assertEquals(work_out_name, publishTrainName);
        //获取发布后训练次数
        String[] publishTrainTimes2 = NameAndTimesArr[1].split("次");
        String publishTrainTimes = publishTrainTimes2[0];
        Reporter.log("发布训练动态中训练次数：" + publishTrainTimes + "<br>");
        System.out.println("发布训练动态中训练次数：" + publishTrainTimes);
//        org.testng.Assert.assertEquals(trainTimesOnly, publishTrainTimes);
    }
}
