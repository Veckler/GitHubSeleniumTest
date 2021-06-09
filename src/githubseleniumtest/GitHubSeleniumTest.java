package githubseleniumtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GitHubSeleniumTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        String testCaseUrl = "https://github.com/Veckler/SeleniumTestDocs/blob/main/testcase.md";
//        String testCaseUrl = "https://github.com/livecode/lcfm-native-solutions/blob/master/Sample%20Content%20Management/test-cases/desktopfinal.md";

        List<WebElement> issueXpaths = new ArrayList<>();
        List<String> issueUrls = new ArrayList<>();

        String username = "";
        String password = "";

        try {
            File credentialsFile = new File("credentials.txt");
//            File credentialsFile = new File("lccredentials.txt");
            Scanner myReader = new Scanner(credentialsFile);

            username = myReader.nextLine();
            password = myReader.nextLine();

            myReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GitHubSeleniumTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Logging in, getting to the test case url
//        driver.get(privateUrl);
        driver.get(testCaseUrl);
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Make a list of WebElements out of the BROKEN issue numbers
        issueXpaths = driver.findElements(By.xpath("//div[@id = 'readme']//*[contains(text(), 'BROKEN')]//a"));
        for (int i = 0; i < issueXpaths.size(); i++) {
            issueUrls.add(issueXpaths.get(i).getAttribute("href"));
        }
        System.out.println(issueUrls.toString());

        //Get urls of issues labeled BROKEN and sort out which one is fixed, remove others
        for (int i = 0; i < issueUrls.size(); i++) {
            driver.get(issueUrls.get(i));
            if (driver.findElements(By.xpath("//span[text() = 'Close issue']")).isEmpty()) {
                System.out.println(issueUrls.get(i) + " is fixed, needs updating");
            } else {
                issueUrls.remove(i);
                i--;
            }
        }

        //Return to TestCase page and start editing
        driver.get(testCaseUrl);
        driver.findElement(By.xpath("//button[@class='btn-octicon tooltipped tooltipped-nw']")).click();

        String currentIssueToEdit;
        for (int i = 0; i < issueUrls.size(); i++) {
            currentIssueToEdit = issueUrls.get(i).replaceAll(".*issues/+", "#");
            System.out.println(currentIssueToEdit);
//            driver.findElement(By.xpath());
            ////span[contains(text(), '#3909')]//preceding-sibling::span//text()
        }
        driver.quit();
        System.exit(0);
    }
}
