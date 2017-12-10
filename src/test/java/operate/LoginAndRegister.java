package operate;

import io.appium.java_client.AppiumDriver;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import runcase.Main;
import tools.Elements;
import tools.Sleep;
import tools.WaitElement;

import java.io.*;

/**
 * Created by sunyingying on 2017/3/14.
 */
public class LoginAndRegister {

    private OperateCommon operateCommon;
    private Sleep sleep;
    private AppiumDriver driver;

    public LoginAndRegister(){
        operateCommon = new OperateCommon();
        sleep = new Sleep();
        driver = Main.driver;
    }

    public void login(String username, String password){

        Reporter.log("Start 登录测试 <br>");
        System.out.println("登录测试开始");
        operateCommon.screenShot();
        operateCommon.skipSpread();
        try{
            WaitElement.wait(driver, 10, Elements.closeOrBack);
//            LogReport.log("关闭记录上次登录的账号页面");
        }catch (Exception e){
//            LogReport.log("没有记录上次登录的账号页面");
        }finally {
            WaitElement.wait(driver, 10, Elements.loginInSplash);
            //可是打印在控制台上
            System.out.println("============" + driver.getPageSource());
            //???为什么不可以输出在log中
//            Reporter.log(driver.getPageSource());
            String formatXML = null;
            try {
                formatXML = format(driver.getPageSource());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //需要试试保存为.dom文件
            writeDom(formatXML);

            WaitElement.wait(driver, 10, Elements.loginBtn);
            WebElement userinput = driver.findElementById(Elements.editPhone);
            userinput.sendKeys(username);
            WebElement passwordinput = driver.findElementById(Elements.editPassword);
            passwordinput.sendKeys(password);
            operateCommon.screenShot();
            driver.findElementById(Elements.loginBtn).click();
//            LogReport.log("登录成功");
        }
//        LogReport.log("Edn 登录测试");
    }

    public void writeDom(String pageSource){
        //创建目录
        File dir = new File("Dom");
        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir + "/" + "test.dom");
        FileWriter writer;
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new FileWriter(file);
            writer.write(pageSource);
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * 将Dom 文档格式化并输出
     * @param xmlDom
     * @return
     */
    public String format(String xmlDom) throws Exception {
        Document document = null;
        document = DocumentHelper.parseText(xmlDom);
        // 格式化输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("gb2312");
        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        // 将document写入到输出流
        xmlWriter.write(document);
        xmlWriter.close();

        return writer.toString();
    }
}
