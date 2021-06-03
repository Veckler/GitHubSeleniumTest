package githubseleniumtest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GitHubSeleniumTest {
    public static void main(String[] args) {
       System.setProperty("webdriver.gecko.driver","geckodriver.exe");
       WebDriver driver = new FirefoxDriver();
       
        String baseUrl = "http://www.facebook.com";
        String tagName = "";

        driver.get(baseUrl);
        
        tagName = driver.findElement(By.id("email")).getTagName();
        System.out.println(tagName);
       
        driver.close();
        System.exit(0);
    }
    
}
