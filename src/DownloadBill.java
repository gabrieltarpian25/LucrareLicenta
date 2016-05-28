import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;


public class DownloadBill {
	
	// **************************************************************************************************** ORANGE
	public static void downloadOrange() throws AWTException {
		WebDriverWait wait;
		WebDriver driver;

		File pathToBinary = new File("/Applications/Firefox.app/Contents/MacOS/firefox");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		
		// firefox profile preferences
		firefoxProfile.setPreference("browser.download.folderList",2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
		firefoxProfile.setPreference("browser.download.dir","./Facturi");
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/pdf,application/x-pdf");
		
		driver = new FirefoxDriver(ffBinary, firefoxProfile);
		// driver = new FirefoxDriver();

		wait = new WebDriverWait(driver, 60);
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
		driver.get("https://www.orange.ro/myaccount/invoice/download/lastInvoice");
	}
	
	// **************************************************************************************************** E-ON
	public static void downloadEon() throws AWTException, FindFailed {
		WebDriverWait wait;
		WebDriver driver;
		
		File pathToBinary = new File("/Applications/Firefox.app/Contents/MacOS/firefox");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		
		// firefox profile preferences
		firefoxProfile.setPreference("browser.download.folderList",2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
		firefoxProfile.setPreference("browser.download.dir","./Facturi");
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/pdf,application/x-pdf");
		
		// Use this to disable Acrobat plugin for previewing PDFs in Firefox (if you have Adobe reader installed on your computer)
		firefoxProfile.setPreference("plugin.scan.Acrobat", "99.0");
		firefoxProfile.setPreference("plugin.scan.plid.all", false);
		
		driver = new FirefoxDriver(ffBinary, firefoxProfile);
		// driver = new FirefoxDriver();

		wait = new WebDriverWait(driver, 60);
		driver.get("https://myline-eon.ro");
		
		String username = null  ;
		String password = null ;
		
		BufferedReader br = null;

		try {

			String sCurrentLine;
			br = new BufferedReader(new FileReader("./Details/loginEon.txt"));
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

		System.out.println("Download bill: "+username + " "+password);
		
		
		// type search query
		driver.findElement(By.id("username")).sendKeys(new String[] { username });
		driver.findElement(By.id("password")).sendKeys(new String[] { password });
		driver.findElement(By.cssSelector("*[class^='btn margin-right-10']")).click();
		driver.get("https://myline-eon.ro/facturile-mele");
		driver.findElement(By.cssSelector("*[class^='sprite inline-block ir pdf']")).click();
		
		// wait until download button is enabled
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("download")));
		driver.findElement(By.id("download")).click();
	}

}
