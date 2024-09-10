package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

/*
 * we need xaamp apachePoi and mysql to execute
 */

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration() {
		
		logger.info("*** Starting TC001_AccountRegistrationTest ***");
		try {
		//take help of pageObjects
		//to access methods from HomePage and AccountRegistrationPage we need to create object for them in this method
		
		HomePage hp = new HomePage(driver);  // driver from above in this class
		hp.clickMyAccount();
		logger.info("*** Clicked on My Account link ***");
		hp.clickRegister();
		logger.info("*** Clicked on Register link ***");
		
		AccountRegistrationPage reg = new AccountRegistrationPage(driver);
		logger.info("*** Providing customer details ***");
		/*reg.setFirstName("Vishal");
		reg.setLastName("Darpally");
		reg.setEmail("vishaldarpally123@gmail.com"); // we have static data and randomly generated data
		reg.setTelephone("123234544");*/
		
		reg.setFirstName(randomString().toUpperCase());
		reg.setLastName(randomString().toUpperCase());
		reg.setEmail(randomString()+"@gmail.com"); // we have static data and randomly generated data
		reg.setTelephone(randomNumber());
		
		String  password = randomAlphaNumeric();
		
		/*reg.setPassword("xyz12334");
		reg.setConfirmPassword("xyz12334");*/
		
		reg.setPassword(password);
		reg.setConfirmPassword(password);
		
		reg.setPrivacyPolicy();
		reg.clickContinue();
//		reg.afterclickContinue();
		
		logger.info("*** Validating expected message ***");
		String confmsg = reg.getConfirmationMsg();
		if (confmsg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		} else {
			logger.error("*** Test failed ***");
			logger.debug("*** Test failed ***");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		} catch(Exception e) {
			Assert.fail();
		}
		
		logger.info("*** Finished TC001_AccountRegistrationTest ***");
	}
}
