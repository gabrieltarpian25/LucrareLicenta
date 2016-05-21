import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Payment {

	
	public static void payOrange()
	{
	WebDriverWait wait;
	WebDriver driver;
	
	File pathToBinary = new File("/Applications/Firefox.app/Contents/MacOS/firefox"); 
	FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary); 
	
	// firefox profile and additional preferences
	FirefoxProfile firefoxProfile = new FirefoxProfile(); 
	
	driver = new FirefoxDriver(ffBinary, firefoxProfile);
	// driver = new FirefoxDriver();
	
	wait = new WebDriverWait(driver, 20);
	driver.get("https://sso.orange.ro/wp/oro?jspname=login.jsp&action=LOGINPAGE_SSO&full_page=true");
	
	String username = null  ;
	String password = null ;
	
	BufferedReader br = null;

	try {

		String sCurrentLine;
		br = new BufferedReader(new FileReader("./Details/loginOrange.txt"));
		//user name
		sCurrentLine = br.readLine();
		username = sCurrentLine;
		
		//password
		sCurrentLine = br.readLine();
		password = sCurrentLine;
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (br != null)br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// type search query
	driver.findElement(By.id("login")).sendKeys(new String[] { username });
	driver.findElement(By.name("password")).sendKeys(new String[] { password });
	driver.findElement(By.id("autent")).click();
	driver.findElement(By.id("invoice_menu")).click();
	driver.get("https://www.orange.ro/myaccount/invoice/payment-step-one");
	
	// scroll a little bit
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	driver.findElement(By.id("initializePayment")).click();
	
	String cardNumber = null  ;
	String expirationDate = null ;
	String CIV = null;
	String cardName = null;
	
	br = null;

	try {

		String sCurrentLine;
		br = new BufferedReader(new FileReader("./Details/detaliiCard.txt"));
		//card number
		sCurrentLine = br.readLine();
		cardNumber = sCurrentLine;
		
		//expiration date
		sCurrentLine = br.readLine();
		expirationDate = sCurrentLine;
		
		// CIV
		sCurrentLine = br.readLine();
		CIV = sCurrentLine;
		
		// card name
		sCurrentLine = br.readLine();
		cardName = sCurrentLine;
		
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (br != null)br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// wait until element is clickable
	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className("card-number-input")));
	
	//cardNumber = cardNumber.substring(0,4)+" "+cardNumber.substring(4,8)+" "+cardNumber.substring(8,12)+" "+cardNumber.substring(12,16);
	System.out.println("Card number: "+cardNumber);
	
	// send card number
	driver.findElement(By.name("ccnumber")).click();
	driver.findElement(By.name("ccnumber")).sendKeys(new String[] { cardNumber.substring(0,4) });
	driver.findElement(By.name("ccnumber")).sendKeys(new String[] { cardNumber.substring(4,8) });
	driver.findElement(By.name("ccnumber")).sendKeys(new String[] { cardNumber.substring(8,12) });
	driver.findElement(By.name("ccnumber")).sendKeys(new String[] { cardNumber.substring(12,16) });
	
	// send expiration date
	element = wait.until(ExpectedConditions.elementToBeClickable(By.name("ccexp")));
	driver.findElement(By.name("ccexp")).sendKeys(new String[] { expirationDate.substring(0,2) });
	driver.findElement(By.name("ccexp")).sendKeys(new String[] { expirationDate.substring(3,7) });
	
	// send CIV code
	element = wait.until(ExpectedConditions.elementToBeClickable(By.name("cardCvv")));
	driver.findElement(By.name("cardCvv")).sendKeys(new String[] { CIV });
	
	// click to pay the bill
	element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[class^='pay-button dropShadow']")));
	driver.findElement(By.cssSelector("*[class^='pay-button dropShadow']")).click();
	
	//driver.findElement(By.className("bt_oro")).click();
	//WebElement element = driver.findElement(By.id("initializePayment"));
    //JavascriptExecutor js =(JavascriptExecutor)driver;
    //js.executeScript("window.scrollTo(0,"+element.getLocation().y+")");
    //element.click();
	
	
	}
}
