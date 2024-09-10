package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

@FindBy(xpath="//input[@id='input-firstname']")
WebElement txtFirstname;

@FindBy(xpath="//input[@id='input-lastname']")
WebElement txtLastname;

@FindBy(xpath="//input[@id='input-email']")
WebElement txtEmail;

@FindBy(xpath="//input[@id='input-telephone']")
WebElement txtTelephone;

@FindBy(xpath="//input[@id='input-password']")
WebElement txtPassword;

@FindBy(xpath="//input[@id='input-confirm']")
WebElement txtConfirmPassword;

//checkbox
@FindBy(xpath="//input[@name='agree']")
WebElement chkPolicy;

@FindBy(xpath="//input[@value='Continue']")
WebElement btnContinue;

@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
WebElement msgConfirmation;


public void setFirstName(String fname) {
	txtFirstname.sendKeys(fname);
}

public void setLastName(String lname) {
	txtLastname.sendKeys(lname);
}

public void setEmail(String email) {
	txtEmail.sendKeys(email);
}

public void setTelephone(String tel) {
	txtTelephone.sendKeys(tel);
}

public void setPassword(String pwd) {
	txtPassword.sendKeys(pwd);
}

public void setConfirmPassword(String pwd) {
	txtConfirmPassword.sendKeys(pwd);
}

public void setPrivacyPolicy() {
	chkPolicy.click();
}

public void clickContinue() {
	//sol-1 below every method is working if performed individually
//	btnContinue.click(); //working
	//sol-2
//	btnContinue.submit(); //working
	//sol-3
//	Actions act = new Actions(driver); //working
//	act.moveToElement(btnContinue).click().perform();
	//sol-4
//	JavascriptExecutor js = (JavascriptExecutor)driver; //working
//	js.executeScript("arguments[0].click();", btnContinue);
	//sol-5
//	btnContinue.sendKeys(Keys.RETURN); //working
	//sol-6
	WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));  //working
	myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
}

//validations always in TC but we just returning below
public String getConfirmationMsg() {
	try {
		return (msgConfirmation.getText());
	} catch(Exception e) {
		return (e.getMessage());
	}

}

}
