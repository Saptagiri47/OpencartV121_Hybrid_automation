package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTestCase_DDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="DataDriven")
    public void verify_loginDDT(String email, String pwd, String exp) {
        logger.info("*** Starting TC_003_LoginDDT ***");

        try {
            // Navigate to login page
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            // Perform login
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin();

            // Verify login
            MyAccountPage mac = new MyAccountPage(driver);
            boolean targetPageExists = mac.isMyAccountPageExists();

            if (exp.equalsIgnoreCase("Valid")) {
                // Expected valid data
                if (targetPageExists) {
                    mac.clickLogout();
                    logger.info("Login successful with valid data.");
                    Assert.assertTrue(true, "Login should be successful with valid data.");
                } else {
                    Assert.fail("Login failed with valid data, but target page does not exist.");
                }
            } else if (exp.equalsIgnoreCase("Invalid")) {
                // Expected invalid data
                if (targetPageExists) {
                    mac.clickLogout();
                    Assert.fail("Login succeeded with invalid data.");
                } else {
                    Assert.assertTrue(true, "Login failed as expected with invalid data.");
                }
            } else {
                Assert.fail("Invalid data type provided for 'exp'.");
            }
        } catch (Exception e) {
            logger.error("Exception encountered during login: ", e);
            Assert.fail("Exception encountered during login: " + e.getMessage());
        }

        logger.info("*** Finished TC_003_LoginDDT ***");
    }
}
