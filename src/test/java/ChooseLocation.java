import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class ChooseLocation {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds wait


    @BeforeClass
    public void setUp(){
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://www.uhaul.com/Storage/");

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    private void selectFromMenu (String topMenuItem, String subMenuItem){
        WebElement mainMenu = driver.findElement(By.linkText(topMenuItem));
        Actions actions = new Actions(driver);
        actions.moveToElement(mainMenu).perform();
        WebElement subMenu = driver.findElement(By.linkText(subMenuItem));
        subMenu.click();
    }


    @Test (priority = 1)
    public void chooseLocationZipCode(){
        selectFromMenu("Storage", "Move-In Online Today!");
        WebElement txtField = driver.findElement(By.id("movingFromInput"));
        txtField.clear();
        WebElement dropDownLocation = driver.findElement(By.xpath("//*[@id=\"movingFromInput\"]"));
        WebElement fndStorageButton =  driver.findElement(By.xpath("//*[@id=\"locationSearchForm\"]/fieldset/div/div/div[4]/button"));
        dropDownLocation.click();
        dropDownLocation.sendKeys("9810");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-menu-item-wrapper")));
        //locate the autocomplete option
        WebElement autoOption = driver.findElement(By.className("ui-menu-item-wrapper"));
        autoOption.click();
        fndStorageButton.click();
        String actualmessage = driver.findElement(By.xpath("//*[@id=\"mainRow\"]/div[1]/div/div/h1")).getText();
        Assert.assertTrue(actualmessage.contains("Find a Storage Unit Near You in 98101"),"\n message does not contain: Find a Storage Unit Near You in ");
    }
    @Test (priority = 2)
    public void chooseLocationCity(){
        selectFromMenu("Storage", "Move-In Online Today!");
        WebElement txtField = driver.findElement(By.id("movingFromInput"));
        txtField.clear();
        WebElement dropDownLocation = driver.findElement(By.xpath("//*[@id=\"movingFromInput\"]"));
        WebElement fndStorageButton = driver.findElement(By.xpath("//*[@id=\"locationSearchForm\"]/fieldset/div/div/div[4]/button"));
        dropDownLocation.click();
        dropDownLocation.sendKeys("New York");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-menu-item-wrapper")));
        //locate the autocomplete option
        WebElement autoOption = driver.findElement(By.className("ui-menu-item-wrapper"));
        autoOption.click();
        fndStorageButton.click();
        String actualmessage = driver.findElement(By.xpath("//*[@id=\"mainRow\"]/div[1]/div/div/h1")).getText();
        Assert.assertTrue(actualmessage.contains("Find a Storage Unit Near You in New York"),"\n message does not contain to: Find a Storage Unit Near You in New York");
    }

    @Test (priority = 3)
    public void chooseLocationState(){
        selectFromMenu("Storage", "Move-In Online Today!");
        WebElement txtField = driver.findElement(By.id("movingFromInput"));
        txtField.clear();
        WebElement dropDownLocation = driver.findElement(By.xpath("//*[@id=\"movingFromInput\"]"));
        WebElement fndStorageButton = driver.findElement(By.xpath("//*[@id=\"locationSearchForm\"]/fieldset/div/div/div[4]/button"));
        dropDownLocation.click();
        dropDownLocation.sendKeys("California");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-menu-item-wrapper")));
        //locate the autocomplete option
        WebElement autoOption = driver.findElement(By.className("ui-menu-item-wrapper"));
        autoOption.click();
        fndStorageButton.click();
        String actualmessage = driver.findElement(By.xpath("//*[@id=\"mainRow\"]/div[1]/div/div/h1")).getText();
        Assert.assertTrue(actualmessage.contains("Find a Storage Unit Near You in California"),"\n message does not contain to: Find a Storage Unit Near You in New York");
    }


    //This test is to test that when the user sends an invalid value the page will throw an error message
    // in this case I purposely put "test" to make it fail the Test case
    @Test (priority = 4)
    public void chooseBadLocation() throws InterruptedException {
        selectFromMenu("Storage", "Move-In Online Today!");
        WebElement txtField = driver.findElement(By.id("movingFromInput"));
        txtField.clear();

        WebElement dropDownLocation = driver.findElement(By.xpath("//*[@id='movingFromInput']"));
        WebElement fndStorageButton = driver.findElement(By.xpath("//*[@id=\"locationSearchForm\"]/fieldset/div/div/div[4]/button"));

        //WebElement findStorageButton = driver.findElement(By.xpath("//*[@id='locationSearchForm']/fieldset/div/div/div[4]/button"));
        dropDownLocation.click();
        dropDownLocation.sendKeys("*(&$(*@)@");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[1]/div/div/form/fieldset/div/div[1]/ul/li/text()")));
        fndStorageButton.click();
        Thread.sleep(2000);
        String actualError = driver.findElement(By.className("validation-summary-errors")).getText();
        Assert.assertTrue(actualError.equals("test"),"\n Message is not equal to 'We could not find your location. Please enter your zip/postal code, city or address again.'");
    }


}