package com.fca.calidad.funcionales;

import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestEditar {
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();
	  JavascriptExecutor js;
	  
	  @Before
	  public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--headless");
	    options.addArguments("--no-sandbox");
	    options.addArguments("--disable-dev-shm-usage");

	    driver = new ChromeDriver();
	    baseUrl = "https://www.google.com/";
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	    js = (JavascriptExecutor) driver;
	  }


	@Test
	  public void testEdit() throws Exception {
		  driver.get("https://mern-crud-mpfr.onrender.com/");
		    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")).click();
		    Thread.sleep(1000); // Wait for 4 seconds
		    driver.findElement(By.name("name")).click();
		    driver.findElement(By.name("name")).click();
		    driver.findElement(By.name("name")).click();
		    driver.findElement(By.name("name")).clear();
		    driver.findElement(By.name("name")).sendKeys("Editar");
		    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
		    driver.findElement(By.xpath("//i")).click();
		    driver.navigate().refresh();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div[2]/table/tbody/tr[1]/td[1]")));
	    assertEquals("Editar", confirmationMessage.getText());
	    
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	}



