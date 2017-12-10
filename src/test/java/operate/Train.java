package operate;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import runcase.Main;
import tools.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyingying on 2017/3/15.
 */
public class Train {

    private AppiumDriver driver;
    private Sleep sleep;
    private OperateCommon operateCommon;
    private Swipe swipe;

    int width, height;

    String trainingName, workOutName, trainingTimes;

    public Train(){
        driver = Main.driver;
        sleep = new Sleep();
        operateCommon = new OperateCommon();
        swipe = new Swipe();
        width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    }

    /*
    退出训练
     */
    public void outTrain(){
        operateCommon.screenShot();
        operateCommon.skipSpread();
        OperateCommon.logReport("Start 退出训练");
        operateCommon.screenShot();
        operateCommon.skipGuid();
        operateCommon.swipeUpToFind(Elements.home_train);
        OperateCommon.logReport("点击我的训练下第一条训练");
        operateCommon.screenShot();
        try{
            WaitElement.wait(driver, 30, Elements.trainingTip);
            OperateCommon.logReport("有动作要点讲解提示");

        }catch (Exception e){
            OperateCommon.logReport("没有动作要点讲解提示");
        }
        WaitElement.wait(driver, 10, Elements.right_set);
        if(driver instanceof AndroidDriver){
            driver.findElementByXPath(Elements.out_train_android).click();
            sleep.sleep(1000);
            driver.findElementByXPath(Elements.out_train_android).click();
            OperateCommon.logReport("退出训练成功");
        }
        else{
            driver.findElementByXPath(Elements.out_train_ios).click();
            OperateCommon.logReport("退出训练成功");
        }
        operateCommon.screenShot();
        OperateCommon.logReport("End 退出训练");
        //最后回到首页
        WaitElement.wait(driver, 10, Elements.left_back);
    }

    /*
    我的训练中没有训练，第一次参加训练
     */
    public void train(){
        OperateCommon.logReport("Start 参加训练");
        WaitElement.waitElement(driver, 10, Elements.tablin1);
        operateCommon.screenShot();
        operateCommon.swipeUpToFind(Elements.join_train);
        OperateCommon.logReport("点击添加训练");
        operateCommon.screenShot();
        WaitElement.waitElement(driver, 10, Elements.switchFunctionInFound);
        List<WebElement> switchFunciton = driver.findElementsById(Elements.switchFunctionInFound);
        switchFunciton.get(3).click();
        OperateCommon.logReport("点击全部训练");
        sleep.sleep(1000);
        WaitElement.wait(driver, 10, Elements.rightSetSecondButton);
        OperateCommon.logReport("点击搜索课程按钮");
        operateCommon.screenShot();
        WaitElement.waitElement(driver, 10, Elements.editSearch);
        WebElement editSearch = driver.findElementById(Elements.editSearch);
        editSearch.sendKeys("Tabata4分钟强化燃脂");
        OperateCommon.logReport("输入查找的训练名");
        operateCommon.screenShot();
        WaitElement.waitElement(driver, 10, Elements.searchItemGeneralLabel);
        List<WebElement> train = driver.findElementsById(Elements.searchItemGeneralLabel);
        train.get(0).click();
        OperateCommon.logReport("在查找出来的训练中选择第一个");
        operateCommon.screenShot();
        //等待训练页中，训练名称view出现
        WaitElement.waitElement(driver, 10, Elements.train_title_only);
        operateCommon.screenShot();
        trainingName = driver.findElementById(Elements.train_title_only).getText();
        OperateCommon.logReport("训练页中的训练名字: " + trainingName);
        //第一次参加训练，获得训练的名字（训练后发布动态的名字是这个）只有一个训练的获取方式
        //若果是多个workout，先获取第一个workout的名字
        try{
            WaitElement.waitElement(driver, 10, Elements.workout_info);
            workOutName = driver.findElementById(Elements.workout_info).getText();
            OperateCommon.logReport("workout的名字: " + workOutName);
        }catch (Exception e){
            swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 4, 1000);
            WaitElement.waitElement(driver, 10, Elements.workout_info);
            workOutName = driver.findElementById(Elements.workout_info).getText();
            operateCommon.screenShot();
            OperateCommon.logReport("workout的名字: " + workOutName);
            swipe.swipeOwn(driver, width * 1 / 2, height * 1 / 4, width * 1 / 2, height * 3 / 4, 1000);
        }
        //参加训练
        driver.findElementById(Elements.join_train_only).click();
        //获取多个workout的训练，当前训练的名字
        try{
            WebElement workout;
            WaitElement.waitElement(driver, 10, Elements.workoutName);
            if(driver instanceof AndroidDriver){
                //ios不行
                workout = driver.findElementById(Elements.workoutName).findElement(By.className(Elements.workoutClassName));
            }else{
                workout = driver.findElementById(Elements.workoutName);
            }
            operateCommon.screenShot();
            workOutName = workout.getText();
            OperateCommon.logReport("有多个workout的当前训练名字：" + workOutName);
        }catch (Exception e){
            OperateCommon.logReport("不是多个workout训练");
        }
        startTraining();
        OperateCommon.logReport("训练结束，上传数据");
        WaitElement.waitElement(driver, 10, Elements.finish);
        operateCommon.screenShot();
        OperateCommon.logReport("End 参加训练");
    }

    public void startTraining(){
        //获取训练次数
        WebElement trainName = WaitElement.waitElement(driver, 10, Elements.train_times);
        String trainTimesAll = trainName.getText();
        String[] trainTimes1 = trainTimesAll.split("第");
        String[] trainTimes2 = trainTimes1[1].split("次");
        trainingTimes = trainTimes2[0].replace(" ","");
        OperateCommon.logReport("训练开始前训练次数：" + trainingTimes);
        //开始训练
        driver.findElementById(Elements.start_train).click();
        operateCommon.screenShot();
        //下载全部
        try{
            if(driver instanceof AndroidDriver){
                driver.findElementByXPath(Elements.downloadAndroid).click();
                OperateCommon.logReport("下载全部");
                downloading();
            }else{
                driver.findElementByXPath(Elements.downloadIOS).click();
                OperateCommon.logReport("下载全部");
                downloading();
            }
        }catch (Exception e){
            OperateCommon.logReport("没有下载全部，直接下载");
            downloading();
        }
        try{
            operateCommon.screenShot();
            System.out.println("等待训练结束");
            WaitElement.waitElement(driver, 500, Elements.finishTrainingInSend);
            finishFeedback();

        }catch (Exception e){
            OperateCommon.logReport("训练出错");
            operateCommon.screenShot();
        }
    }

    public void finishFeedback(){

        List<String> sendList = new ArrayList<String>();
        sendList.add(Elements.emojiNormal);
        sendList.add(Elements.centerSliderTag);
        sendList.add(Elements.feedbackCardCell);

        String sendStr;
        boolean found = true;

        for(int i = 3 ; i>0; i--){
            int j = 0;
            while(found || j < 3){
                try{
                    WaitElement.waitElement(driver, 5, sendList.get(j));
                    sendStr = sendList.get(j);
                    if(sendStr.equals(Elements.emojiNormal)){
                        List<WebElement> emoji = driver.findElementsById(Elements.emojiNormal);
                        emoji.get(2).click();
                        OperateCommon.logReport("点击表情");
                    }else if(sendStr.equals(Elements.centerSliderTag)){
                        driver.findElementById(Elements.centerSliderTag).click();
                        OperateCommon.logReport("选择训练强度");
                    }else if(sendStr.equals(Elements.feedbackCardCell)){
                        driver.findElementById(Elements.feedbackCardCell).click();
                        OperateCommon.logReport("选择跳过动作原因");
                    }
                    found = false;
                }catch (Exception e){
                    sendList.remove(j);
                    j++;
                }
            }
        }
    }

    public void downloading(){
        try {
            if (driver.findElementById(Elements.downloading) != null){
                OperateCommon.logReport("正在下载");
                operateCommon.screenShot();
                WaitElement.wait(driver, 300, Elements.next_in_train);
                OperateCommon.logReport("下载完开始训练");
                nextTrain();
            }
        }catch (Exception e){
            nextTrain();
        }
    }

    public void nextTrain(){
        System.out.println("开始下一步");
        WebElement next = driver.findElementById(Elements.next_in_train);
        boolean next_enable = next.isEnabled();
        while(next_enable) {
            next.click();
            next_enable = next.isEnabled();
            System.out.println("下一步");
        }
        operateCommon.screenShot();
    }

}
