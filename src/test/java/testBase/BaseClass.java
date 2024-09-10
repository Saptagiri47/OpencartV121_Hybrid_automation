package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {
	public static WebDriver driver; // static is to prevent new driver object create creation while calling the instance in other class 
	public Logger logger; // log4j
	public Properties p;

	@BeforeClass(groups= {"Sanity", "Regression", "Master", "DataDriven"})
	@Parameters({"os", "browser"})
	public void setup(@Optional("windows") String os, String br) throws IOException {

		// loading config.properties file
		FileReader fr = new FileReader("C:\\Users\\Sapth\\git\\opencark\\OpencarkV121_Hybrid_automation\\src\\test\\resources\\config.properties");
		p = new Properties();
		p.load(fr); // load(reader reader)

		logger = LogManager.getLogger(this.getClass());

		//only at runtime we are able to see in http://localhost:4444
		//for remote in config.properties file to run on grid
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//capabilities.setPlatform(Platform.WIN11); this is not correct coz we are getting OS from xml file
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.WIN11);
			} else {
				System.out.println("No matching OS.");
				return;
			}
			//browser
			switch(br.toLowerCase()) {
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("edge"); break;
			default : System.out.println("No matching browser"); return;
			}
			
			driver = new RemoteWebDriver(new URL("gridURL"), capabilities);
		}
		// for remote in config.properties file to run on grid
		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome": driver = new ChromeDriver(); break;
			case "edge": driver = new EdgeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name.."); return;
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// driver.get("https://localhost/opencart/upload/index.php");
		// driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL2")); // reading url from properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups= {"Sanity", "Regression", "Master", "DataDriven"})
	public void tearDown() {
		driver.quit();
	}

	// for email
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}

	// for telephone
	public String randomNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}

	// for password
	public String randomAlphaNumeric() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		return (generatedString + "@" + generatedNumber);
		// no method for characters
		// multiple calling creates multiple Strings so we store in a variable and reuse
		// the generated one
	}
	//used in ExtentReportManagerClass
	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile); //source file is copied to target file
		return targetFilePath; // to make it part of the report we use return. without return we get data into folder but not display on to the report
	}//whenever TC fails we execute above method
	
}
