package tools;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author sunyingying
 * 等待某个元素出现
 *
 */
public class WaitElement {

	//等待某个element出现并且点击，根据id获取元素
	public static void wait(AppiumDriver driver,long timeout, final String id) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id(id));
			}
		}).click();
	}

	//等待某个element出现不点击，根据xpath获取元素
	public static void waitByXpath(final AppiumDriver driver,long timeout, final String id) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(new ExpectedCondition<WebElement>() {
			//			@Override
//			public WebElement apply(WebDriver d) {
//				if(d.findElement(By.xpath(idOne)) != null){
//					System.out.println("找第一个id");
//					return d.findElement(By.xpath(idOne));
//				}
//				else if(d.findElement(By.xpath(idTwo)) != null){
//					System.out.println("找第二个id");
//					return d.findElement(By.xpath(idTwo));
//				}
//				return null;
//			}
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath(id));
			}

		});
	}

	//等待某个element出现返回控件
	public static WebElement waitElement(AppiumDriver driver,long timeout, final String id) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id(id));
			}
		});
	}

}
