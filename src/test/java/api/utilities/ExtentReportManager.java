package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    String repName;

    public void onStart(ITestContext context){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//Time Stamp
        repName = "Test-Report-"+timeStamp+".html";

        sparkReporter = new ExtentSparkReporter(".//reports//"+repName);//Specify location of the report

        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");//Title of the report
        sparkReporter.config().setReportName("Pet Store Users API");//Name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();

        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application","Pet Store Users API");
        extentReports.setSystemInfo("Operating System",System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name",System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment","QA");
        extentReports.setSystemInfo("User","VPSR");

    }
    public void onTestSuccess(ITestResult result){
        extentTest = extentReports.createTest(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.PASS,"Test Passed");
    }
    public void onTestFailure(ITestResult result){
        extentTest = extentReports.createTest(result.getName());
        extentTest.createNode(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.log(Status.FAIL,"Test Failed");
        extentTest.log(Status.FAIL,result.getThrowable().getMessage());

    }
    public void onTestSkipped(ITestResult result){
        extentTest = extentReports.createTest(result.getName());
        extentTest.createNode(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.log(Status.SKIP,"Test Skipped");
        extentTest.log(Status.SKIP,result.getThrowable().getMessage());

    }
    public void onFinish(ITestContext context){
        extentReports.flush();
    }
}