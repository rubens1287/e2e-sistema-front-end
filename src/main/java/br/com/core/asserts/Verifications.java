package br.com.core.asserts;

import br.com.core.report.ExtentReports;
import br.com.core.view.Action;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class Verifications {


    private Verifications() {
        throw new IllegalStateException();
    }

    /**
     * Highlights the requested part of a component on screen
     *
     * @author Rubens Lobo
     */
    public static void highlightElement(WebDriver driver, WebElement element) {
        try {
            if (!driver.toString().contains("Windows") && !driver.toString().contains("appPackage")
                    && !driver.toString().contains("WiniumDriver") && driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='2px dashed red'", element);
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='1,5'", element);
                wait(1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
            }
        } catch (Exception e) {
            fail("The element is not visible for Highlight: " + e);
        }
    }

    /**
     * Waits for a defined period
     *
     * @param seconds the number of seconds the function will wait for
     * @author Rubens Lobo
     */
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            ExtentReports.appendToReport(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Verifies an existing element on the screen
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyElementExists(WebDriver driver, By by, int seconds) {
        WebElement element = Action.getExistingElement(driver, by, seconds);
        highlightElement(driver, element);
    }

    /**
     * Verifies if the text requested is part of an attribute of an element on screen
     *
     * @param by           Type of "By"
     * @param attribute    set attribute of the web element
     * @param expectedText is the text expected to be matched on screen
     * @param seconds      Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyElementAttributeContains(WebDriver driver, By by, int seconds, String attribute, String expectedText) {
        WebElement element = Action.getClickableElement(driver, by, seconds);
        assertTrue(element.getAttribute(attribute).contains(expectedText));
        highlightElement(driver, element);
    }

    /**
     * Verifies if a clickable element contains the expected text on screen
     *
     * @param by           Type of "By"
     * @param expectedText is the text expected to be matched on screen
     * @param seconds      Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyTextsElementClickable(WebDriver driver, By by, String expectedText, int seconds) {
        WebElement element = Action.getClickableElement(driver, by, seconds);
        int timeout = 0;
        while (!(element.getText().trim().equals(expectedText)) && (timeout <= seconds)) {
            Verifications.wait(1);
            if (timeout == seconds) {
                fail("Element " + by.toString() + " not found on current page!");
            }
            timeout++;
        }
        highlightElement(driver, element);
    }


    /**
     * Verifies if an existing element contains the expected text on screen. It can be a disabled element
     *
     * @param by           Type of "By"
     * @param expectedText is the text to be entered expected to be matched on screen
     * @param seconds      Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyTextsExistingElement(WebDriver driver, By by, String expectedText, int seconds) {

        WebElement element = Action.getExistingElement(driver, by, seconds);
        int timeout = 0;
        while (!(element.getText().trim().equals(expectedText)) && (timeout <= seconds)) {
            Verifications.wait(1);
            if (timeout == seconds) {
                fail("Element " + by.toString() + " not found on current page!");
            }
            timeout++;
        }
        highlightElement(driver, element);
    }

    /**
     * Verifies if an element is visible and Clickable
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @return checking an element is visible and can be clicked
     * @author Rubens Lobo
     */
    public static void verifyElementIsClickable(WebDriver driver, By by, int seconds) {
        WebElement element = Action.getClickableElement(driver, by, seconds);
        highlightElement(driver, element);
    }

    /**
     * Verifies if the given element is visible.
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyElementIsVisible(WebDriver driver, By by, int seconds) {
        WebElement element = Action.getVisibleElement(driver, by, seconds);
        highlightElement(driver, element);
    }

    /**
     * Verifies if an element is not on screen
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyElementDoesNotExist(WebDriver driver, By by, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        ExpectedCondition elementIsDisplayed = (ExpectedCondition<Boolean>) webDriver -> {
            try {
                driver.findElement(by).isDisplayed();
                return false;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return true;
            }
        };
        wait.until(elementIsDisplayed);
    }
}
