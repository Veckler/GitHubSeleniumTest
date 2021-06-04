package githubseleniumtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GitHubSeleniumTest {

    public static void main(String[] args) {

        String username = "";
        String password = "";

        try {
            File credentialsFile = new File("credentials.txt");
            Scanner myReader = new Scanner(credentialsFile);

            username = myReader.nextLine();
            password = myReader.nextLine();

            myReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GitHubSeleniumTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        driver.get("https://github.com/Veckler/SeleniumTestDocs/blob/main/testcase.md");
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();
//        driver.quit();
        System.exit(0);
    }
}
