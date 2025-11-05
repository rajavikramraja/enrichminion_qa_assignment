package com.enrichminion.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

public class UI_Automation_PositiveFlow {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://enrichminion.vercel.app/");
		System.out.println("Browser launched and navigated to application.");
	}

	@Test(priority = 1)
	public void loginTest() throws InterruptedException {
		System.out.println("Current URL before login: " + driver.getCurrentUrl());
		driver.findElement(By.id("email")).sendKeys("vikramraja161196@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Vikram@123");
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

		Thread.sleep(4000);
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL after login: " + currentUrl);

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement userTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".w-full.whitespace-nowrap")));
		Assert.assertTrue(userTitle.getText().contains("Vikramraja"), "❌ Login failed or wrong redirect!");
	}

//	@Test(priority = 2, dependsOnMethods = "loginTest")
//	public void uploadFileTest() throws InterruptedException {
//		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		Thread.sleep(3000);
//		System.out.println("Starting file upload...");
//
//		Actions action = new Actions(driver);
//		WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='Enrichment']")));
//		action.moveToElement(menu).perform();
//
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Phone Finder']"))).click();
//
//
//		WebElement uploadInput = driver.findElement(By.xpath("//input[@type='file']"));
//		uploadInput.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\Test_File.csv");
//
//		WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
//				By.xpath("//button[normalize-space()='Continue to map columns']")));
//		continueBtn.click();
//		
//		WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(
//		        By.xpath("//div[@role='dialog' and .//h2[contains(text(),'Map Columns')]]")
//		    ));
//		WebElement emaildropdownBtn = wait.until(ExpectedConditions.elementToBeClickable(
//				By.xpath("(//button[@role='combobox'])[1]")));
//		emaildropdownBtn.click();
//
//		// Select 'email' option
//		WebElement emailOption = wait.until(ExpectedConditions.elementToBeClickable(
//				By.xpath("//*[normalize-space(text())='email']")
//				));
//		emailOption.click();
//
//		WebElement linkdropdownBtn = wait.until(ExpectedConditions.elementToBeClickable(
//				By.xpath("(//button[@role='combobox'])[2]")));
//		linkdropdownBtn.click();
//
//		// Select 'email' option
//		WebElement linkOption = wait.until(ExpectedConditions.elementToBeClickable(
//				By.xpath("//*[normalize-space(text())='linkedin_url']")
//				));
//		linkOption.click();
//		WebElement NextBtn = wait.until(ExpectedConditions.elementToBeClickable(
//				By.xpath("//*[normalize-space(text())='Next']")
//				));
//		NextBtn.click();
//		
//		String buyCredit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Buy Credits']"))).getText();
//		Assert.assertTrue(buyCredit.contains("Buy"), "Low Credit Value");
//		Thread.sleep(3000);
//
//	}

	@Test(priority = 2, dependsOnMethods = "loginTest")
	public void uploadFileTest() throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    System.out.println("📁 Starting file upload...");

	    // --- Open Enrichment → Phone Finder ---
	    WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Enrichment']")));
	    actions.moveToElement(menu).perform();
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//p[normalize-space()='Phone Finder']"))).click();

	    // --- Upload CSV File ---
	    WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//input[@type='file']")));
	    uploadInput.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\Test_File.csv");

	    // --- Continue to Map Columns ---
	    WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Continue to map columns']")));
	    continueBtn.click();

	    // --- Wait for dialog ---
	    WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@role='dialog' and .//h2[contains(text(),'Map Columns')]]")));
	    System.out.println("✅ 'Map Columns' dialog appeared.");

	    // ==== Map "Email" ====
	    WebElement emailDropdown = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//button[@role='combobox'])[1]")));
	    js.executeScript("arguments[0].scrollIntoView(true);", emailDropdown);
	    actions.moveToElement(emailDropdown).pause(Duration.ofMillis(200)).click().perform();

	    WebElement emailOption = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[@role='option' or @data-radix-collection-item]//*[normalize-space(text())='email']")));
	    emailOption.click();
	    System.out.println("✅ Email column mapped to 'email'.");

	    Thread.sleep(500);

	    // ==== Map "LinkedinId" ====
	    WebElement linkedinDropdown = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//button[@role='combobox'])[2]")));
	    js.executeScript("arguments[0].scrollIntoView(true);", linkedinDropdown);
	    actions.moveToElement(linkedinDropdown).pause(Duration.ofMillis(200)).click().perform();

	    WebElement linkedinOption = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[@role='option' or @data-radix-collection-item]//*[normalize-space(text())='linkedin_url']")));
	    linkedinOption.click();
	    System.out.println("✅ LinkedinId column mapped to 'linkedin_url'.");

	    Thread.sleep(500);

	    // ==== Click Next ====
	    WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Next']")));

	    // Wait for overlay to vanish
//	    try {
//	        wait.until(ExpectedConditions.invisibilityOfElementLocated(
//	                By.xpath("//div[contains(@class,'bg-black') and contains(@class,'inset-0') and @data-state='open']")));
//	    } catch (Exception ignored) {}
//
//	    js.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
//	    Thread.sleep(300);
	    try {
	        nextBtn.click();
	    } catch (ElementClickInterceptedException e) {
	        js.executeScript("arguments[0].click();", nextBtn);
	    }

	    System.out.println("➡️ Clicked Next — waiting for dialog to close...");

	    // --- Wait for dialog to close ---
	    //wait.until(ExpectedConditions.invisibilityOf(dialog));

	    // --- Verify Buy Credits ---
	    WebElement buyCredit = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//button[normalize-space()='Buy Credits']")));
	    Assert.assertTrue(buyCredit.getText().contains("Buy"), "❌ Low Credit Value or missing Buy Credits button!");
	    System.out.println("✅ Buy Credits button visible — mapping flow completed.");
	    WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
	    	    By.xpath("//div[@role='dialog']//button[contains(@class,'absolute') and contains(@class,'right-4')]")
	    	));
	    	closeBtn.click();
	    	}


	@Test(priority = 3, dependsOnMethods = "uploadFileTest")
	public void logoutTest() throws InterruptedException {
		System.out.println("Initiating logout...");
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

	    // ✅ Use XPath to find profile icon safely
	    WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//span[contains(@class,'bg-[#333333]')]")));
	    profileIcon.click();

	    // Click "Sign Out"
	    WebElement signOutBtn = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[normalize-space()='Sign Out']")));
	    signOutBtn.click();

	    // Wait for confirmation dialog
	    WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//div[@role='alertdialog']//button[normalize-space()='Yes']")));
	    yesButton.click();

	    // Wait for login screen
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//h2[contains(translate(text(),'LOGIN','login'),'login')]")));

	    System.out.println("✅ Logout successful, login page visible.");}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			System.out.println("Browser closed and session ended.");
		}
	}
}
