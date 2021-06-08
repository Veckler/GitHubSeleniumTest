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

        List<WebElement> issueXpaths = new ArrayList<>();
        List<String> issueUrls = new ArrayList<>();

        String username = "";
        String password = "";

        String currentIssue = "";

        try {
            File credentialsFile = new File("credentials.txt");
            Scanner myReader = new Scanner(credentialsFile);

            username = myReader.nextLine();
            password = myReader.nextLine();

            myReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GitHubSeleniumTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        driver.get("https://github.com/Veckler/SeleniumTestDocs/blob/main/testcase.md");
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();

        driver.findElement(By.xpath("//button[@class='btn-octicon tooltipped tooltipped-nw']")).click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        issueXpaths = driver.findElements(By.xpath("//div[@role='presentation']//span[contains(text(), 'BROKEN')]//following-sibling::span[contains(text(), 'https')]"));
        for (int i = 0; i < issueXpaths.size(); i++) {
            issueUrls.add(issueXpaths.get(i).getText().substring(1, issueXpaths.get(i).getText().length() - 1));
        }

//        System.out.println("done");
        WebElement status;
        for (int i = 0; i < issueUrls.size(); i++) {
            driver.get(issueUrls.get(i));
            if (driver.findElements(By.xpath("//span[text() = 'Close issue']")).isEmpty()) {
                System.out.println("This is fixed, needs updating");
            } else {
                System.out.println("This is not fixed, no update needed");
            }
        }

//        driver.quit();
        System.exit(0);
    }
}
