import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Payment {

	
	public static void payOrange()
	{
	WebDriverWait wait;
	WebDriver driver;
	
	File pathToBinary = new File("/Applications/Firefox.app/Contents/MacOS/firefox"); 
	FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary); FirefoxProfile firefoxProfile = new FirefoxProfile(); driver = new FirefoxDriver(ffBinary, firefoxProfile);
	// driver = new FirefoxDriver();
	
	wait = new WebDriverWait(driver, 60); 
	driver.get("https://sso.orange.ro/wp/oro?jspname=login.jsp&action=LOGINPAGE_SSO&full_page=true");
	
	String username = "gabystelistu2007";
	String password = "flavius";
	
	// type search query
	driver.findElement(By.id("login")).sendKeys(new String[] { username });
	driver.findElement(By.name("password")).sendKeys(new String[] { password });
	driver.findElement(By.id("autent")).click();
	driver.get("https://www.orange.ro/myaccount/invoice/download/lastInvoice");
	
	
	
	}
}
