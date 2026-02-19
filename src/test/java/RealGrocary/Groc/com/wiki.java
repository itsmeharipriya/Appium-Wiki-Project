package RealGrocary.Groc.com;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.net.URL;
import java.time.Duration;

public class wiki extends BaseTest {

    AndroidDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName("KVWKKJ95Z9NR4TVO");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("org.wikipedia");
        options.setAppActivity("org.wikipedia.page.PageActivity");
        options.setNoReset(true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        test = extent.createTest("App Launch");
        test.info("App Launched Successfully ✅");
    }

    @Test(priority = 1)
    public void conentInWikipedia() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement contents = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia:id/page_contents"))
            );
            contents.click();
            test.pass("Clicked on Contents successfully ✅");
        } catch (Exception e) {
            String screenshot = captureScreenshot(driver, "ContentsFail");
            test.fail("Failed to click Contents: " + e.getMessage())
                .addScreenCaptureFromPath(screenshot);
        }
    }

    @Test(priority = 2)
    public void searchInWikipedia1() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement search = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/page_toolbar_button_search"))
            );
            search.click();
            test.pass("Clicked Search box ✅");

            WebElement searchText = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia:id/search_src_text"))
            );
            searchText.sendKeys("India");
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));
            test.pass("Entered search text ✅");

            WebElement firstResult = wait.until(
                    ExpectedConditions.elementToBeClickable(By.className("android.view.View"))
            );
            firstResult.click();
            test.pass("Clicked first search result ✅");

        } catch (Exception e) {
            String screenshot = captureScreenshot(driver, "SearchFail");
            test.fail("Search test failed: " + e.getMessage())
                .addScreenCaptureFromPath(screenshot);
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
        test.info("Driver Closed ✅");
        extent.flush();
    }
}
