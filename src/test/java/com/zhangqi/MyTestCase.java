package com.zhangqi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


public class MyTestCase {
    public WebDriver webDriver;
    @BeforeClass
    public void setUpEnv(){
        System.setProperty("webdriver.chrome.driver","E:\\springboot\\test-selenium\\src\\drivers\\chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        option.setExperimentalOption("useAutomationExtension", false);
        option.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        //创建浏览器实例
        webDriver = new ChromeDriver(option);
        webDriver.manage().window().maximize();
        webDriver.get("https://juhe.cn");

    }
    @Test
    public void testTimeOut(){
        //显式等待是针对某一元素等待时间
        WebDriverWait wait=new WebDriverWait(webDriver,Duration.ofSeconds(15));
        WebElement loginFrame=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("layui-layer-iframe1")));
        webDriver.switchTo().frame(loginFrame);
        webDriver.findElement(By.id("username")).sendKeys("qeqeqwe");
    }
    @Test
    public void testImplicity(){
        //设置最长等待时间为15秒  隐式等待 全局等待时间，就算找到不到，也会等待15秒
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        WebElement loginFrame=webDriver.findElement(By.id("layui-layer-iframe1"));
        webDriver.switchTo().frame(loginFrame);
        webDriver.findElement(By.id("username")).sendKeys("qeqeqwe");
    }

    @Test
    public void testId() throws InterruptedException {

        webDriver.findElement(By.id("kw")).sendKeys("HelloWord");
        webDriver.findElement(By.id("su")).click();
        Thread.sleep(3_000);
    }
    @Test
    public void testName() throws InterruptedException {
        webDriver.findElement(By.name("wd")).sendKeys("qi");
        webDriver.findElement(By.className("s_btn")).click();
        Thread.sleep(3000);
    }
    @Test
    public void testXpath() throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@class=\"nav-search-input\"]")).sendKeys("appium");
        webDriver.findElement(By.xpath("//*[@class=\"nav-search-btn\"]")).click();
        Thread.sleep(3000);
    }
    @Test
    public void testAction(){
        Actions actions=new Actions(webDriver);
        WebElement moreBtn= webDriver.findElement(By.linkText("更多"));
        actions.moveToElement(moreBtn);
        WebElement mp3link=webDriver.findElement(By.xpath("//a[@name=\"tj_mp3\"]"));
        actions.moveToElement(mp3link).click().perform();

    }
    @Test
    public void testDragandDrop(){
        //webDriver.findElement(By.linkText("地图")).click();
        WebElement mask=webDriver.findElement(By.id("mask"));
        Actions actions=new Actions(webDriver);
        actions.moveToElement(mask).contextClick().pause(3000);
        WebElement start=webDriver.findElement(By.id("cmitem_start"));
        actions.moveToElement(start).click();
        actions.clickAndHold();
        actions.moveByOffset(200,100);
        actions.release();
        actions.contextClick().pause(3000);
        WebElement end=webDriver.findElement(By.id("cmitem_end"));
        actions.moveToElement(end).click();
        actions.perform();//最后才执行这个
    }
    @Test
    public void teatSwitch(){
        webDriver.findElement(By.linkText("图片")).click();
        String currentWindow=webDriver.getWindowHandle();
        Set<String> allWindows=webDriver.getWindowHandles();
        for(String window:allWindows){
            if(!Objects.equals(window, currentWindow)){
                webDriver.switchTo().window(window);
            }
        }
        //图片页面上的自动化
        webDriver.findElement(By.id("kw")).sendKeys("helloword");
        webDriver.switchTo().window(currentWindow);
        webDriver.findElement(By.id("kw")).sendKeys("hahahah");
    }
    @Test
    public void testSwitchFrame(){
        webDriver.get("https://login.anjuke.com/login/form");
        WebElement iframe=webDriver.findElement(By.id("iframeLoginIfm"));
        webDriver.switchTo().frame(iframe);
        webDriver.findElement(By.id("phoneIpt")).sendKeys("13212341234");
    }



    @AfterClass
    public void tearDownEnv() throws InterruptedException {
        Thread.sleep(3000);
        //webDriver.quit();
    }
}
