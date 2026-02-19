package RealGrocary.Groc.com;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeClass
    public void startReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("WikiAutomationReport.html");
        reporter.config().setReportName("Wikipedia Automation Project");
        reporter.config().setDocumentTitle("Wiki Automation Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Ammu");
        extent.setSystemInfo("Device", "Redmi / Android 16");
    }

    // Capture screenshot method
    public String captureScreenshot(org.openqa.selenium.WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + timestamp + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots"));
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
        }
        return path;
    }

    @AfterClass
    public void flushReport() {
        extent.flush();
    }
}
