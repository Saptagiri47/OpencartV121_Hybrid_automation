package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//homePage, LoginPage, MyAccountPage three require to automate login test
public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[text()='My Account']") // my account heading
	WebElement msgHeading;

	@FindBy(xpath = "//div[@class='list-group']//a[text()='Logout']") // my account heading
	WebElement lnkLogout;  ////div[@class-'list-group']//a[text()='Logout'] rose to error

	// below method is not validation method but just checking
	public boolean isMyAccountPageExists() {
		try {
			return (msgHeading.isDisplayed());
		} catch (Exception e) {
			return false;
		}
	}

	public void clickLogout() {
		lnkLogout.click();
	}
}
