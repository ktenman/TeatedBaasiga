package ee.tlu;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.runners.MethodSorters;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UITests {

    static WebDriver driver;
    static String URL = "http://estimol.eu:51234/";

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
    }

    @Test
    public void firstTest() throws InterruptedException {
        driver.get(URL);
        int kogus = driver.findElements(By.className("kustutaTeade")).size();
        Hoidla hoidla = new Hoidla();
        assertEquals(hoidla.getHoida().size(),kogus);
        oota();
    }
    
    @Test
    public void secondTest() throws InterruptedException {
        driver.get(URL);
        driver.findElement(By.xpath("//*[@name=\"teade\"]")).sendKeys("suvaline tekst siia");
        driver.findElement(By.id("submit")).click();
        assertTrue(driver.getPageSource().contains("Teade salvestatud!"));
        oota();
    }
    
    @Test
    public void thirdTest() throws IOException, InterruptedException {
        driver.get(URL);
        List<WebElement> buttons = driver.findElements(By.className("kustutaTeade"));
        WebElement webElement = buttons.get(buttons.size()-1);
        webElement.click();
        assertTrue(driver.getPageSource().contains("Teade kustutatud!"));
        oota();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
    
    void oota() throws InterruptedException{
    	System.out.println("---------------------------------------");
    	for (int i = 3; i > 0; i--) {
        	TimeUnit.SECONDS.sleep(1);
        	System.out.println(i);
		}
    }
}