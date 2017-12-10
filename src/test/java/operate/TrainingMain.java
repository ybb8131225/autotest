package operate;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import tools.*;
import runcase.Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunyingying on 2016/12/8.
 */
public class TrainingMain {
//    private Main main = Main.getInstance();
    private static AppiumDriver driver;
    private Sleep sleep;
    private Swipe swipe;
    private Elements elements;
    private WaitElement waitElement;
    private OperateCommon operateCommon;

    //所有训练中选择的训练名字
    private String train_name_all;
    //选择后训练页训练名字
    private String train_name_only;
    private int width, height;
    //训练次数
    private String trainTimesOnly;
    //当前训练的动作名称
    private String work_out_name;
    private Guide guide;
    //当前系统时间
    private String month;
    private String day;

    public TrainingMain(){
        driver = Main.driver;
        sleep = new Sleep();
        swipe = new Swipe();
        elements = new Elements();
        waitElement = new WaitElement();
        operateCommon = new OperateCommon();
        guide = new Guide();
        width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    }

    /*
    训练完后发布动态
     */
    public void publish(){
        Reporter.log("Start 发布动态<br>");
        System.out.println("发布动态测试开始");
        sleep.sleep(3000);
        try{
            if(driver instanceof AndroidDriver){
                if(driver.findElementByXPath(Elements.trainingCongratulationsAndroid) != null){
                    operateCommon.screenShot();
                    driver.pinch(width * 1 / 2, height * 1 / 8);
                    Reporter.log("有恭喜你的页面提示，点击其他地方 <br>");
                    System.out.println("有恭喜你的页面提示，点击其他地方");
                }
            }
            else{
                if(driver.findElementByXPath(Elements.trainingCongratulationsIOS) != null){
                    operateCommon.screenShot();
                    driver.pinch(width * 1 / 2, height * 1 / 8);
                    Reporter.log("有恭喜你的页面提示，点击其他地方 <br>");
                    System.out.println("有恭喜你的页面提示，点击其他地方");
                }
            }
        }catch (Exception e){
            operateCommon.screenShot();
            Reporter.log("没有恭喜你的页面提示 <br>");
            System.out.println("没有恭喜你的页面提示");
        }
        WaitElement.wait(driver, 10, Elements.timeline_input);
        operateCommon.screenShot();
        Reporter.log("点击输入框，打开发布动态界面 <br>");
        System.out.println("点击输入框，打开发布动态界面");
        sleep.sleep(3000);
        //点击允许获取地理位置
        try{
            if(driver instanceof AndroidDriver){
                if(driver.findElementByXPath(Elements.allowAndroid)!=null){
                    driver.findElementByXPath(Elements.allowAndroid).click();
                }
            }else{
                if(driver.findElementByXPath(Elements.allowIOS)!=null){
                    driver.findElementByXPath(Elements.allowIOS).click();
                }
            }
            Reporter.log("允许获取地理位置 <br>");
            System.out.println("允许获取地理位置");
            WaitElement.waitElement(driver, 10, Elements.feed_input);
            WebElement feed_input = driver.findElementById(Elements.feed_input);
            //输入发动态内容
            feed_input.sendKeys("good");
            operateCommon.screenShot();

        }catch (Exception e){
            Reporter.log("没有地理位置请求权限");
            System.out.println("没有地理位置请求权限");
            WaitElement.waitElement(driver, 10, Elements.feed_input);
            WebElement feed_input = driver.findElementById(Elements.feed_input);
            //输入发动态内容
            feed_input.sendKeys("good");
            operateCommon.screenShot();
        }
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

    /*
    训练历史
     */
    public void trainHistory(){
        Reporter.log("Start 历史训练 <br>");
        System.out.println("历史训练测试开始");
        WaitElement.wait(driver, 10, Elements.tablin1);
        System.out.println("点击训练tab");
        swipe.swipeOwn(driver, width * 1 / 2, height * 1 / 5, width * 1 / 2, height * 4 / 5, 1000);
        operateCommon.screenShot();
        //点击首页总共训练时间
        WaitElement.wait(driver, 10, Elements.finish_times);
        sleep.sleep(3000);
        operateCommon.screenShot();
        if(driver instanceof AndroidDriver){
            try{
                WaitElement.waitElement(driver,10, Elements.timesInHistory);
                Reporter.log("没有训练历史分类引导 <br>");
                System.out.println("没有训练历史分类引导");

            }catch (Exception e){
                //有时候提示An unknown server-side error occurred
                swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 3, 1000);
                Reporter.log("关闭训练历史分类引导 <br>");
                System.out.println("关闭训练历史分类引导");
            }
        }
            //获取当前系统时间月日，历史中是01月，动态中是1月～～～～
            //历史中ios是1月，android是01月
//            getSystemDate();
            if(driver instanceof AndroidDriver){
                SimpleDateFormat monthdf = new SimpleDateFormat("MM");
                month = monthdf.format(new Date());
                SimpleDateFormat daydf = new SimpleDateFormat("dd");
                day = daydf.format(new Date());
            }else {
                SimpleDateFormat monthdf = new SimpleDateFormat("M");
                month = monthdf.format(new Date());
                SimpleDateFormat daydf = new SimpleDateFormat("d");
                day = daydf.format(new Date());
            }
            String date = month+"月"+day+"日";
            Reporter.log("系统时间：" + date + "<br>");
            System.out.println("系统时间：" + date);
            String trainDate = driver.findElementById(Elements.day).getText();
            Reporter.log("个人训练历史中最新训练时间：" + trainDate + "<br>");
            System.out.println("个人训练历史中最新训练时间：" + trainDate);
            org.testng.Assert.assertEquals(date, trainDate);
            Reporter.log("当前最新训练日期与当前系统时间一致 <br>");
            System.out.println("当前最新训练日期与当前系统时间一致");
            if(driver instanceof AndroidDriver){
                String finishTrainName = driver.findElementById(Elements.person_train_name).getText();
                Reporter.log("个人训练历史中最新训练名：" + finishTrainName + "<br>");
                System.out.println("个人训练历史中最新训练名：" + finishTrainName);
                org.testng.Assert.assertEquals(work_out_name.trim(), finishTrainName.trim());
                Reporter.log("当前最新训练名字是上次训练的名字 <br>");
                System.out.println("当前最新训练名字是上次训练的名字");
                String finishTrainTimesAll = driver.findElementById(Elements.person_train_times).getText();
                String[] finishTrainTimesArr = finishTrainTimesAll.split(" ");
                String finishTrainTimes = finishTrainTimesArr[1];
                Reporter.log("个人训练历史中最新训练次数：" + finishTrainTimes + "<br>");
                System.out.println("个人训练历史中最新训练次数：" + finishTrainTimes);
                org.testng.Assert.assertEquals(trainTimesOnly, finishTrainTimes);
                Reporter.log("当前最新训练次数是上次训练次数 <br>");
                System.out.println("当前最新训练次数是上次训练次数");
            }else{
                String finishTrainInfo = driver.findElementById(Elements.person_train_name).getText();
                System.out.println(finishTrainInfo);
                String[] finishTrainInfoArr = finishTrainInfo.split(" ");
                String finishTrainName = finishTrainInfoArr[0];
                Reporter.log("个人训练历史中最新训练名：" + finishTrainName + "<br>");
                System.out.println("个人训练历史中最新训练名：" + finishTrainName);
                org.testng.Assert.assertEquals(work_out_name.trim(), finishTrainName.trim());
                Reporter.log("当前最新训练名字是上次训练的名字 <br>");
                System.out.println("当前最新训练名字是上次训练的名字");
                String finishTrainTimesAll = finishTrainInfoArr[1];
                finishTrainTimesAll = finishTrainTimesAll.split("第")[1];
                String finishTrainTimes = finishTrainTimesAll.split("次")[0];
                Reporter.log("个人训练历史中最新训练次数："+finishTrainTimes + "<br>");
                System.out.println("个人训练历史中最新训练次数：" + finishTrainTimes);
                org.testng.Assert.assertEquals(trainTimesOnly, finishTrainTimes);
                Reporter.log("当前最新训练次数是上次训练次数 <br> ");
                System.out.println("当前最新训练次数是上次训练次数");
            }

        driver.findElementById(Elements.left_back).click();
        //最后操作是返回到首页
        operateCommon.screenShot();
        Reporter.log("End 历史训练");
    }

    /*
    只有一天训练的课程表
     */
    public void scheduledOnlyOneDay(){
//        sleep.sleep(10000);
        WaitElement.waitElement(driver, 10, Elements.finish_times);
        Reporter.log("Start 添加只有一天的课程表 <br>");
        System.out.println("添加只有一天的课程表测试开始");
        swipe.swipeOwn(driver, width * 1 / 2, height * 4 / 5, width * 1 / 2, height * 1 / 7, 1000);
        System.out.println("滑动到屏幕最底部");
        operateCommon.screenShot();
        //android 添加训练和添加课程表是一个控件，ios不是一个控件
        if(driver instanceof AndroidDriver){
            try{
                WaitElement.wait(driver, 180, Elements.join_train);
            }catch (Exception e){
                swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 5, 1000);
                WaitElement.wait(driver, 180, Elements.join_train);
            }
        }
        else{
            try{
                WaitElement.wait(driver, 180, Elements.join_train);
            }catch (Exception e){
                swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 5, 1000);
                WaitElement.wait(driver, 180, Elements.join_train);
            }
        }
        Reporter.log("点击添加课程表 <br>");
        System.out.println("点击添加课程表");
        operateCommon.screenShot();
        driver.findElementById(Elements.getSchedule).click();
        Reporter.log("选择训练课程表 <br>");
        System.out.println("选择训练课程表");
        if(driver instanceof AndroidDriver){
            driver.findElementById(Elements.selectScheduleType).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第一个获取Keep课程表 <br>");
            System.out.println("选择第一个获取Keep课程表");
            driver.findElementById(Elements.selectScheduleCreate).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextInCreate).click();
            Reporter.log("选择第一个减脂 <br>");
            System.out.println("选择第一个减脂");
            driver.findElementById(Elements.selectScheduleCreate).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextInCreate).click();
            Reporter.log("选择K1难度 <br>");
            System.out.println("选择K1难度");
            driver.findElementById(Elements.selectScheduleCreate).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextInCreate).click();
            Reporter.log("选择第一个兴趣 <br>");
            System.out.println("选择第一个兴趣");
        }
        else{
            waitElement.wait(driver, 10, elements.keepSchedule);
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第一个获取Keep课程表 <br>");
            System.out.println("选择第一个获取Keep课程表");
            operateCommon.screenShot();
            List<WebElement> selectCreate;
            WaitElement.waitElement(driver, 10, Elements.selectScheduleCreate);
            selectCreate = driver.findElementsById(Elements.selectScheduleCreate);
            selectCreate.get(0).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第一个减脂 <br>");
            System.out.println("选择第一个减脂");
            WaitElement.waitElement(driver, 10, Elements.selectScheduleCreate);
            selectCreate = driver.findElementsById(Elements.selectScheduleCreate);
            selectCreate.get(0).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择K1难度 <br>");
            System.out.println("选择K1难度");
            WaitElement.waitElement(driver, 10, Elements.selectScheduleCreate);
            selectCreate = driver.findElementsById(Elements.selectScheduleCreate);
            selectCreate.get(0).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第一个兴趣 <br>");
            System.out.println("选择第一个兴趣");
        }

        //选择开始日期，完成定制，校验开始时间是不是系统时间
        keepScheduleAssert();
        //获取训练名字，更改workout的值
        String scheduleName = driver.findElementById(Elements.scheduleName).getText();
        Reporter.log("课程表中动作名称" + scheduleName + "<br>");
        System.out.println("课程表中动作名称" + scheduleName);
        work_out_name = scheduleName;
        driver.findElementById(Elements.scheduleName).click();
        //开始训练，目前来看课程表中的训练都是但节训练
        startTraining();
        operateCommon.screenShot();
        WaitElement.wait(driver, 10, Elements.finish);
        Reporter.log("关闭当前页面 <br>");
        System.out.println("关闭当前页面");
        WaitElement.wait(driver, 10, Elements.left_back);
        //查看历史训练
        trainHistory();
        Reporter.log("End 添加只有一天的课程表");
        operateCommon.screenShot();
    }

    /*
    制定下一个课程表，前提是当前课程表已完成
     */
    public void nextSchedule(){
        Reporter.log("Start 制定下一课程表 <br>");
        System.out.println("制定下一课程表测试开始");
        operateCommon.screenShot();
        try{
            WaitElement.wait(driver, 10, Elements.mySchedule);
        }catch (Exception e){
            swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 3, 1000);
            WaitElement.wait(driver, 10, Elements.mySchedule);
        }
        operateCommon.screenShot();
        WaitElement.wait(driver, 10, Elements.nextSchedule);
        driver.findElementById(Elements.otherDifficulty).click();
        Reporter.log("选择其他难度的课程表 <br>");
        System.out.println("选择其他难度的课程表");
        if(driver instanceof AndroidDriver){
            driver.findElementById(Elements.selectScheduleCreate).findElement(By.xpath("//android.widget.RelativeLayout[2]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextInCreate).click();
            Reporter.log("选择第二个塑形 <br>");
            System.out.println("选择第二个塑形");
            driver.findElementById(Elements.selectScheduleCreate).findElement(By.xpath("//android.widget.RelativeLayout[2]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextInCreate).click();
            Reporter.log("选择K2难度 <br>");
            System.out.println("选择K2难度");
            driver.findElementById(Elements.selectScheduleCreate).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextInCreate).click();
            Reporter.log("选择第一个 <br>");
            System.out.println("选择第一个");

        }
        else{
            List<WebElement> selectCreate;
            selectCreate = driver.findElementsById(Elements.selectScheduleCreate);
            selectCreate.get(1).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第二个塑形 <br>");
            System.out.println("选择第二个塑形");
            selectCreate = driver.findElementsById(Elements.selectScheduleCreate);
            selectCreate.get(1).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择K2难度 <br>");
            System.out.println("选择K2难度");
            selectCreate = driver.findElementsById(Elements.selectScheduleCreate);
            selectCreate.get(0).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第一个 <br>");
            System.out.println("选择第一个");

        }
        //选择开始日期，完成定制，校验开始时间是不是系统时间
        keepScheduleAssert();
        Reporter.log("End 制定下一课程表");
        operateCommon.screenShot();
    }

    /*
    退出课程表
     */
    public void outSchedule(){
        sleep.sleep(10000);
        Reporter.log("Start 退出课程表");
        System.out.println("退出课程表测试开始");
        driver.findElementById(Elements.right_set).click();
        operateCommon.screenShot();
        if(driver instanceof AndroidDriver){
            driver.findElementByXPath(Elements.outScheduleAndroid).click();
            driver.findElementByXPath(Elements.outScheduleAndroid).click();
        }else{
            driver.findElementByXPath(Elements.outScheduleIOS).click();
            driver.findElementByXPath("//*[@name='退出']").click();
        }
        swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 2 / 4, 1000);
        try{
            WaitElement.wait(driver, 10, Elements.mySchedule);
        }catch (Exception e){
            Reporter.log("没有我的课程表了 <br>");
            System.out.println("没有我的课程表了");
        }
        Reporter.log("End 退出课程表");
        operateCommon.screenShot();
    }

    /*
    创建自定义课程表
     */
    public void definedSchedule(){
        Reporter.log("Start 自定义课程表 <br>");
        System.out.println("自定义课程表测试开始");
//        WaitElement.waitElement(driver, 10, Elements.finish_times);
        swipe.swipeOwn(driver, width * 1 / 2, height * 4 / 5, width * 1 / 2, height * 1 / 6, 1000);
        System.out.println("滑动到屏幕最底部");
        operateCommon.screenShot();
        if(driver instanceof AndroidDriver){
            WaitElement.wait(driver, 10, Elements.join_train);
            Reporter.log("点击添加课程表 <br>");
            System.out.println("点击添加课程表");
            driver.findElementById(Elements.getSchedule).click();
            driver.findElementById(Elements.selectScheduleType).findElement(By.xpath("//android.widget.RelativeLayout[2]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第二个自定义课程表 <br>");
            System.out.println("选择第二个自定义课程表");
            //不能选择日期，直接下一步
            driver.findElementById(Elements.definedScheduleNext).click();
            //选择日期
            selectDefinedScheduleDay();
            operateCommon.screenShot();
            driver.findElementById(Elements.definedScheduleNext).click();
            //选择目标和难度
            driver.findElementById(Elements.definedScheduleSlection).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.definedScheduleNext).click();
            Reporter.log("选择第一个减脂 <br>");
            System.out.println("选择第一个减脂");
            driver.findElementById(Elements.definedScheduleSlection).findElement(By.xpath("//android.widget.RelativeLayout[1]")).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.definedScheduleNext).click();
            Reporter.log("选择K1难度 <br>");
            System.out.println("选择K1难度");
            //训练部位
            String upper = driver.findElementById(Elements.upper).findElement(By.xpath("//android.widget.TextView[2]")).getText();
            driver.findElementById(Elements.upper).findElement(By.xpath("//android.widget.TextView[2]")).click();
            Reporter.log("选择的上肢训练部位是" + upper + "<br>");
            System.out.println("选择的上肢训练部位是" + upper);
            String core = driver.findElementById(Elements.core).findElement(By.xpath("//android.widget.TextView[2]")).getText();
            driver.findElementById(Elements.core).findElement(By.xpath("//android.widget.TextView[2]")).click();
            Reporter.log("选择的核心训练部位是" + core + "<br>");
            System.out.println("选择的核心训练部位是" + core);
            String down = driver.findElementById(Elements.down).findElement(By.xpath("//android.widget.TextView[2]")).getText();
            driver.findElementById(Elements.down).findElement(By.xpath("//android.widget.TextView[2]")).click();
            Reporter.log("选择的下肢训练部位是" + down);
            System.out.println("选择的下肢训练部位是" + down);
            operateCommon.screenShot();
            driver.findElementById(Elements.definedScheduleNext).click();
        }
        else{
            WaitElement.wait(driver, 10, Elements.join_train);
            Reporter.log("点击添加课程表 <br>");
            System.out.println("点击添加课程表");
            driver.findElementById(Elements.getSchedule).click();
            driver.findElementById(Elements.customSchedule).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第二个自定义课程表 <br>");
            System.out.println("选择第二个自定义课程表");
            //不能选择日期，直接下一步
            operateCommon.screenShot();
            driver.findElementById(Elements.definedScheduleNext).click();
            //选择日期
            selectDefinedScheduleDayIOS();
            driver.findElementById(Elements.nextSlection).click();
            driver.findElementById(Elements.fatTragetDefined).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择第一个减脂 <br>");
            System.out.println("选择第一个减脂");
            driver.findElementById(Elements.difficultyDefined).click();
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();
            Reporter.log("选择K1难度 <br>");
            System.out.println("选择K1难度");
            WebElement upper = driver.findElementById(Elements.upper);
            upper.findElements(By.id(Elements.trainPoint)).get(0).click();
            Reporter.log("选择胸部 <br>");
            System.out.println("选择胸部");
            WebElement core = driver.findElementById(Elements.core);
            core.findElements(By.id(Elements.trainPoint)).get(0).click();
            Reporter.log("选择腹部 <br>");
            System.out.println("选择腹部");
            WebElement down = driver.findElementById(Elements.down);
            down.findElements(By.id(Elements.trainPoint)).get(0).click();
            Reporter.log("选择臀部 <br>");
            System.out.println("选择臀部");
            operateCommon.screenShot();
            driver.findElementById(Elements.nextSlection).click();

        }
        WaitElement.wait(driver, 10, Elements.addTrainInSchedule);
        Reporter.log("选择第一天训练日，添加训练 <br>");
        System.out.println("选择第一天训练日，添加训练");
        operateCommon.screenShot();
        WaitElement.waitElement(driver, 10, Elements.addScheduleTrain);
        driver.findElementById(Elements.addScheduleTrain).click();
        Reporter.log("选择推荐的第一个训练 <br>");
        System.out.println("选择推荐的第一个训练");
        operateCommon.screenShot();
        String selectionScheduleTrainName = driver.findElementById(Elements.selectionScheduleTrainName).getText();
        Reporter.log("选择的训练名字是" + selectionScheduleTrainName + "<br>");
        System.out.println("选择的训练名字是" + selectionScheduleTrainName);
        operateCommon.screenShot();
        driver.findElementById(Elements.right_set).click();
        Reporter.log("选择完训练，点击完成 <br>");
        System.out.println("选择完训练，点击完成");
        if(driver instanceof AndroidDriver){
            driver.findElementByXPath(".//*[@text='"+selectionScheduleTrainName+"']");
        }
        operateCommon.screenShot();
        Reporter.log("训练安排中添加上了选择的训练 <br>");
        System.out.println("训练安排中添加上了选择的训练");
        driver.findElementById(Elements.right_set).click();
        System.out.println("完成");
        //判断课程表开始时间是不是当前系统时间
        //获取当前系统时间月日
        getSystemDate();
//        String scheduleMonth = driver.findElementById(elements.monthInSchedule).findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView")).getText();
//        org.testng.Assert.assertEquals(month + "月", scheduleMonth);
//        String sechduleDay = driver.findElementById(Elements.dayInScheduleCreate).getText();
//        org.testng.Assert.assertEquals(day, sechduleDay);
//        System.out.println("课程表第一天是当前系统日期");
//        driver.findElementById(Elements.scheduleBeforJoin).click();
//        WaitElement.wait(driver, 5000, Elements.scheduleProgress);
//        String scheduleProgress = driver.findElementById(Elements.scheduleProgress).getText();
//        //判断刚开始是否是完成0%
//        org.testng.Assert.assertEquals("完成0%", scheduleProgress);
//        System.out.println("课程表初始完成进度是0");
        if(driver instanceof AndroidDriver){
            String scheduleMonth = driver.findElementById(Elements.monthInSchedule).findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView")).getText();
            org.testng.Assert.assertEquals(month + "月", scheduleMonth);
            Reporter.log("课程表第一天是当前系统日期月 <br>");
            System.out.println("课程表第一天是当前系统日期月");
            String scheduleDay = driver.findElementById(Elements.dayInScheduleCreate).getText();
            org.testng.Assert.assertEquals(day, scheduleDay);
            Reporter.log("课程表第一天是当前系统日期日 <br>");
            System.out.println("课程表第一天是当前系统日期日");
        }
        else{
            String scheduleMonth = driver.findElementById(Elements.monthInSchedule).getText();
            org.testng.Assert.assertEquals(month + "月", scheduleMonth);
            Reporter.log("课程表第一天是当前系统日期月 <br>");
            System.out.println("课程表第一天是当前系统日期月");
            String scheduleDay = driver.findElementById(Elements.dayInScheduleCreateSelected).getText();
            org.testng.Assert.assertEquals(day, scheduleDay);
            Reporter.log("课程表第一天是当前系统日期日 <br>");
            System.out.println("课程表第一天是当前系统日期日");
        }
        operateCommon.screenShot();
        driver.findElementById(Elements.scheduleBeforJoin).click();
        WaitElement.wait(driver, 10, Elements.scheduleProgress);
        String scheduleProgress = driver.findElementById(Elements.scheduleProgress).getText();
        //判断刚开始是否是完成0%
        org.testng.Assert.assertEquals("完成0%", scheduleProgress.replace(" ",""));
        Reporter.log("课程表初始完成进度是0 <br>");
        System.out.println("课程表初始完成进度是0");
        driver.findElementById(Elements.left_back).click();
        swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 4, 1000);
        WaitElement.waitElement(driver, 10, Elements.mySchedule);
        Reporter.log("首页中有课程表 <br>");
        System.out.println("首页中有课程表");
        operateCommon.screenShot();
        Reporter.log("End 添加自定义课程表");
    }

    public void selectDefinedScheduleDayIOS(){
        List<WebElement> selectedDay = driver.findElementsById(Elements.dayInScheduleCreateSelected);
        int selectedDayCount = selectedDay.size();
        Reporter.log("被选择上的天数" + selectedDayCount + "<br>");
        System.out.println("被选择上的天数" + selectedDayCount);
        List<WebElement> notSelectedDay = driver.findElementsById(Elements.dayInScheduleCreate);
        notSelectedDay.get(0).click();
        selectedDayCount++;
        Reporter.log("选择后的天数" + selectedDayCount + "<br>");
        System.out.println("选择后的天数" + selectedDayCount);
        String totalDayTip = driver.findElementById(Elements.totalDayTip).getText();
        totalDayTip = totalDayTip.substring(3, 4);
        Reporter.log("一共选中的天数提示是" + totalDayTip + "<br>");
        System.out.println("一共选中的天数提示是" + totalDayTip);
        String totalDaySelectString = selectedDayCount+"";
//        System.out.println("totalDaySelectString   "  +  totalDaySelectString + "totalDayTip" + totalDayTip);
        org.testng.Assert.assertEquals(totalDaySelectString, totalDayTip);
    }

    public void selectDefinedScheduleDay(){
        WebElement monthCalandar = driver.findElementById(Elements.monthCalandar);
        List<WebElement> weeks = monthCalandar.findElements(By.className("android.widget.LinearLayout"));
        System.out.println("一共有" + weeks.size() + "周");
        int totalDay = 0;
        List<String> dayList = new ArrayList<String>();
        Boolean selected = false;
        for(int i = 0 ; i < weeks.size() ; i++){
            List<WebElement> days = weeks.get(i).findElements(By.className("android.widget.RelativeLayout"));
            System.out.println("当前周有" + days.size() + "天");
            for(int j  = 0 ; j < days.size() ; j++){
                try{
                    if(days.get(j).findElement(By.id(Elements.selectedScheduleDayImg)) != null){
                        System.out.println("是被选中的日期");
                        //因为days.size()获取的天数不对，所以要做去重处理
                        //不知道为什么获取的天数不对
                        String selectedScheduleDay = days.get(j).findElement(By.id(Elements.selectedScheduleDay)).getText();
                        if(!dayList.contains(selectedScheduleDay)){
                            dayList.add(selectedScheduleDay);
                            System.out.println("选中的日期是" + selectedScheduleDay);
                            totalDay++;
                        }
                    }
                }catch (Exception e){
                    System.out.println("不是被选中的日期");
                    if(!selected){
                        days.get(j).findElement(By.id(Elements.selectedScheduleDay)).click();
                        selected = true;
                        totalDay++;
                    }
                }

            }
        }
        Reporter.log("一共选中了" + totalDay + "<br>");
        System.out.println("一共选中了" + totalDay);
        String totalDayTip = driver.findElementById(Elements.totalDayTip).getText();
        totalDayTip = totalDayTip.substring(4, 5);
        Reporter.log("一共选中的天数提示是" + totalDayTip + "<br>");
        System.out.println("一共选中的天数提示是" + totalDayTip);
        String totalDaySelectString = totalDay+"";
//        System.out.println("totalDaySelectString======" + totalDaySelectString);
        org.testng.Assert.assertEquals( totalDayTip , totalDaySelectString);
    }

    public void startTraining(){
        //获取训练次数
        WebElement trainName = WaitElement.waitElement(driver, 10, Elements.train_times);
        String trainTimesAll = trainName.getText();
        String[] trainTimes1 = trainTimesAll.split("第");
        String[] trainTimes2 = trainTimes1[1].split("次");
        trainTimesOnly = trainTimes2[0].replace(" ","");
        Reporter.log("训练开始前训练次数：" + trainTimesOnly + "<br>");
        System.out.println("训练开始前训练次数：" + trainTimesOnly);
        //开始训练
        driver.findElementById(Elements.start_train).click();
        operateCommon.screenShot();

        //下载全部
        try{
            if(driver instanceof AndroidDriver){
                if(driver.findElementByXPath(Elements.downloadAndroid)!=null){
                    driver.findElementByXPath(Elements.downloadAndroid).click();
                    Reporter.log("下载全部 <br>");
                    System.out.println("下载全部");
                    downloading();
                }
            }else{
                if(driver.findElementByXPath(Elements.downloadIOS)!=null){
                    driver.findElementByXPath(Elements.downloadIOS).click();
                    Reporter.log("下载全部 <br>");
                    System.out.println("下载全部");
                    downloading();
                }
            }
        }catch (Exception e){
            Reporter.log("没有下载全部，直接下载 <br>");
            System.out.println("没有下载全部，直接下载");
            downloading();
        }
        try{
            operateCommon.screenShot();
            System.out.println("开始找表情icon");
            //最后一个下一步后等待训练完成出现表情icon
            WaitElement.waitElement(driver, 500, Elements.emojiNormal);
            List<WebElement> emoji = driver.findElementsById(elements.emojiNormal);
            emoji.get(2).click();
            operateCommon.screenShot();
            Reporter.log("点击表情 <br>");
            System.out.println("点击传数据");
            operateCommon.screenShot();
//            waitElement.wait(driver, 10, elements.centerSliderTag);
//            driver.findElementById(elements.sureButton).click();
//            Reporter.log("选择训练强度 <br>");
//            System.out.println("选择训练强度");
//            operateCommon.screenShot();
//            waitElement.wait(driver, 10, elements.feedbackCardCell);
//            Reporter.log("选择跳过动作原因<br>");
//            System.out.println("选择跳过动作原因");

        }catch (Exception e){
            Reporter.log("训练出错 <br>");
            System.out.println("训练出错");
            operateCommon.screenShot();
        }
    }

//    public void getPublishTrainInfo(){
//        WebElement publishTrainNameElement = WaitElement.waitElement(driver, 10, Elements.publish_train_name);
//        operateCommon.screenShot();
//        //获取发布后训练名字
//        String publishTrainNameAll = publishTrainNameElement.getText();
//        String[] publishTrainNameList = publishTrainNameAll.split(" ");
//        String publishTrainName = publishTrainNameList[1];
//        Reporter.log("发布动态训练的名字：" + publishTrainName + "<br>");
//        System.out.println("发布动态训练的名字：" + publishTrainName);
//        org.testng.Assert.assertEquals(publishTrainName, work_out_name);
//        Reporter.log("训练的动作名称和动态中的动作名称一样 <br>");
//        System.out.println("训练的动作名称和动态中的动作名称一样");
//        //获取发布后训练次数
//        String publishTrainTimesAll = driver.findElementById(Elements.publish_train_times).getText();
////        System.out.println(publishTrainTimesAll+"publishTrainTimesAll.replace后：" +publishTrainTimesAll.replace("  ","").replace(" ",""));
//        String[] publishTrainTimes1 = publishTrainTimesAll.replace(" ","").replace(" ", "").split("第");
////        System.out.println(publishTrainTimes1[1]);
//        String[] publishTrainTimes2 = publishTrainTimes1[1].split("次");
//        String publishTrainTimes = publishTrainTimes2[0];
//        Reporter.log("发布训练动态中训练次数：" + publishTrainTimes + "<br>");
//        System.out.println("发布训练动态中训练次数：" + publishTrainTimes);
//        org.testng.Assert.assertEquals(trainTimesOnly, publishTrainTimes.trim());
//        Reporter.log("训练动作的训练次数和发布动态中的训练次数一样 <br>");
//        System.out.println("训练动作的训练次数和发布动态中的训练次数一样");
//    }

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
        org.testng.Assert.assertEquals(work_out_name, publishTrainName);
        //获取发布后训练次数
        String[] publishTrainTimes2 = NameAndTimesArr[1].split("次");
        String publishTrainTimes = publishTrainTimes2[0];
        Reporter.log("发布训练动态中训练次数：" + publishTrainTimes + "<br>");
        System.out.println("发布训练动态中训练次数：" + publishTrainTimes);
        org.testng.Assert.assertEquals(trainTimesOnly, publishTrainTimes);
    }

    public void downloading(){
        try {
            if (driver.findElementById(Elements.downloading) != null){
                Reporter.log("正在下载 <br>");
                System.out.println("正在下载");
                operateCommon.screenShot();
                WaitElement.wait(driver, 300, Elements.next_in_train);
                Reporter.log("下载完开始训练 <br>");
                System.out.println("下载完开始训练");
                nextTrain();
            }
        }catch (Exception e){
            nextTrain();
        }
    }

    public void overturn(){
        try{
            if(driver.findElementByXPath(Elements.overturn)!=null){
                driver.pinch(width * 1 / 2, height * 1 / 8);
                System.out.println("点击屏幕不横屏");
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

    public void getSystemDate(){
        SimpleDateFormat monthdf = new SimpleDateFormat("M");
        month = monthdf.format(new Date());
        SimpleDateFormat daydf = new SimpleDateFormat("d");
        day = daydf.format(new Date());
    }

    /*
    从选择开始日期，到完成制定课程表的校验
     */
    public void keepScheduleAssert(){
        //还不知道怎么选日志，先直接下一步
        driver.findElementById(Elements.viewSchedule).click();
        Reporter.log("查看课程表 <br>");
        System.out.println("查看课程表");
        operateCommon.screenShot();
        //判断课程表开始时间是不是当前系统时间
        //获取当前系统时间月日
        getSystemDate();
        waitElement.waitElement(driver, 10, elements.monthInSchedule);
        if(driver instanceof AndroidDriver){
            String scheduleMonth = driver.findElementById(Elements.monthInSchedule).findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView")).getText();
            org.testng.Assert.assertEquals(month + "月", scheduleMonth);
            Reporter.log("课程表第一天是当前系统日期月 <br>");
            System.out.println("课程表第一天是当前系统日期月");
            String scheduleDay = driver.findElementById(Elements.dayInScheduleCreate).getText();
            org.testng.Assert.assertEquals(day, scheduleDay);
            Reporter.log("课程表第一天是当前系统日期日 <br>");
            System.out.println("课程表第一天是当前系统日期日");
        }
        else{
            String scheduleMonth = driver.findElementById(Elements.monthInSchedule).getText();
            org.testng.Assert.assertEquals(month+"月", scheduleMonth);
            Reporter.log("课程表第一天是当前系统日期月 <br>");
            System.out.println("课程表第一天是当前系统日期月");
            String scheduleDay = driver.findElementById(Elements.dayInScheduleCreateSelected).getText();
            org.testng.Assert.assertEquals(day, scheduleDay);
            Reporter.log("课程表第一天是当前系统日期日 <br>");
            System.out.println("课程表第一天是当前系统日期日");
        }
        driver.findElementById(Elements.scheduleBeforJoin).click();
        WaitElement.wait(driver, 10, Elements.scheduleProgress);
        operateCommon.screenShot();
        String scheduleProgress = driver.findElementById(Elements.scheduleProgress).getText();
        //判断刚开始是否是完成0%
        org.testng.Assert.assertEquals("完成0%", scheduleProgress.replace(" ",""));
        Reporter.log("课程表初始完成进度是0 <br>");
        System.out.println("课程表初始完成进度是0");
    }

    /*
    测试方法
     */
    public void testPraise(){
        sleep.sleep(5000);
        width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
        swipe.swipeOwn(driver, width * 1 / 2, height * 3 / 4, width * 1 / 2, height * 1 / 3, 1000);
        sleep.sleep(1000);
        driver.findElementById(Elements.home_train).click();
        driver.findElementById(Elements.start_train).click();
        //下载全部
        try{
            if(driver.findElementByXPath(Elements.downloadAndroid)!=null){
                driver.findElementByXPath(Elements.downloadAndroid).click();
                nextTrain();
            }
        }catch (Exception e){
            nextTrain();
        }
        sleep.sleep(1000);
        WaitElement.wait(driver, 60000, Elements.emojiNormal);
    }

    public void testWait(){
        System.out.println("正在等待");
        WaitElement.wait(driver, 10, Elements.emojiNormal);
        System.out.println("等待结束");
    }
}
