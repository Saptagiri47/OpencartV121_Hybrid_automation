package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkerReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) { // testContext is nothing but which method we are executing
		/*
		 * SimpleDateFormat df =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt =
		 * new Date(); String currentdatetimestamp = df.format(df); instead of all these
		 * lines, below single line covers everything
		 */

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // we get current timestamp
																							// in java

		repName = "Test-Report-" + timeStamp + ".html";
		sparkerReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report

		sparkerReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkerReporter.config().setReportName("Opencart Functional Testing");
		sparkerReporter.config().setTheme(Theme.DARK); // STANDARD

		extent = new ExtentReports();
		extent.attachReporter(sparkerReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("os"); // we capture this os dynamically from xml file
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser); // we capture this browser dynamically from xml file

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
			// if groups are mentioned in xml file then all groups are stored in
			// includedGroups and displayed in reports
		}
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName() + " got successfully executed");

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); // new entry is created from result using classname
		// generally method name is printed but for class name printing we use
		// getTestClass().getName()
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		// we are assigning the result using method and groups

		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try { // this for screenshot attachment
			String imgPath = new BaseClass().captureScreen(result.getName()); // from baseclass we are calling
																				// capturescreen method by passing name
																				// of test
			test.addScreenCaptureFromPath(imgPath); // attaching data to report
		} catch (IOException e1) { // mention this IOException in BaseClass for captureScreen method
			e1.printStackTrace();
		} // due to new instance of BaseClass two drivers are created. to resolve the
			// conflict we just make variable in baseclass as static
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report

		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext testContext) {
		extent.flush(); // consolidates the report and prints

		// to open report automatically without our intervention below code
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// to send email automatically //URL java.net.url
		/*
		 * try { URL url =new
		 * URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		 * 
		 * //create the email message for below add java email dependency ImageHtmlEmail
		 * email =new ImageHtmlEmail(); email.setDataSourceResolver(new
		 * DataSourceUrlResolver(url)); email.setHostName("smpt.googlemail.com"); // for
		 * gmail email.setSmtpPort(465); email.setAuthenticator(new
		 * DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
		 * email.setSSLOnConnect(true); email.setFrom("pavanoltraining@gmail.com");
		 * //sender email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report..");
		 * email.addTo("prahladhasaptagiri@gmail.com"); //DL = distribution list. common
		 * email for all receivers email.attach(url, "extent report",
		 * "please check report.."); email.send(); // send the mail } catch (Exception
		 * e) { e.printStackTrace(); }
		 */
	}

}
