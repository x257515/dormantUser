package com.telus.manage;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.files.interaction.ReadJSON;

import io.github.bonigarcia.wdm.WebDriverManager;


public class dormantUser {
	WebDriver driver;
	String filepath="//TestData//dormantUser.json";
	JSONObject readJson = new JSONObject(ReadJSON.parse(filepath));

	@Test(dataProvider = "getData")
	
	public void active_user(String username, String password) throws InterruptedException {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
	//	options.addArguments("--headless");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(options);
		String url=readJson.getString("Url");
		driver.manage().window().maximize();
		driver.navigate().to(url);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("idtoken1"))));
		driver.findElement(By.id("idtoken1")).sendKeys(username);
		driver.findElement(By.id("idtoken2")).sendKeys(password);
		
		driver.findElement(By.id("login-btn")).click();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait2.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//div[@class = 'block__title'])[1]"))));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//img[@alt = 'user avatar']/.."))).click().build().perform();
		Thread.sleep(3000);
		actions.moveToElement(driver.findElement(By.xpath("//div[@class = 'site-header__util__footer']/a"))).click().build().perform();
		
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait3.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("idtoken1"))));
        
	}

	
	  @AfterMethod 
	  public void tearDown() {
		  driver.quit(); 
		  }
	 
	
	@DataProvider
	public Iterator<Object[]> getData() {
		ArrayList<Object[]> testData = ExcelUtils.getDataFromExcel();
		return testData.iterator();
	}
}
