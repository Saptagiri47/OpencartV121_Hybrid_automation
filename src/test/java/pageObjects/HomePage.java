package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
//must define explicit constructor

	public HomePage(WebDriver driver) {
		// must explicitly invoke another constructor
		/*
		 * inheritance concept we can invoke immediate parent variable method
		 * constructor using super keyword
		 */
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement lnkMyaccount;

	@FindBy(linkText = "Register") // span[normalize-space()='Register']
	WebElement lnkRegister;

	@FindBy(linkText = "Login") // login link added in steps
	WebElement lnkLogin;

//future login element code

	public void clickMyAccount() {
		lnkMyaccount.click();
	}

	public void clickRegister() {
		lnkRegister.click();
	}

	public void clickLogin() {
		lnkLogin.click();
	}

}
