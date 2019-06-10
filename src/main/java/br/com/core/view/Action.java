package br.com.core.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * @author Rubens Lobo
 * @since 13/05/2018
 */
public class Action {


    /**
     * Returns an existing element from the screen
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @param driver  driver of the application
     * @return Returns an existing element from the screen
     * @author Rubens Lobo
     */
    public static WebElement getExistingElement(WebDriver driver, By by, int seconds) {
        return new WebDriverWait(driver, seconds).until(ExpectedConditions.presenceOfElementLocated(by));
    }


    /**
     * Returns a visible element from the screen
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @param driver  driver of the application
     * @return Returns an visible element from the screen
     * @author Rubens Lobo
     */
    public static WebElement getVisibleElement(WebDriver driver, By by, int seconds) {
        return new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Returns a clickable element from the screen
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @param driver  driver of the application
     * @return Returns a clickable element from the screen
     * @author Rubens Lobo
     */
    public static WebElement getClickableElement(WebDriver driver, By by, int seconds) {
        return new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(by));
    }


    /**
     * Clears the Text Field of a WebElement
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @param driver  driver of the application
     * @author Rubens Lobo
     */
    public static void clearField(WebDriver driver, By by, int seconds) {
        WebElement element = getClickableElement(driver, by, seconds);
        element.clear();
    }

    /**
     * Enters text in a WebElement Text Field
     *
     * @param driver  driver of the application
     * @param by      Type of "By"
     * @param text    set a text to web element
     * @param seconds Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void setText(WebDriver driver, By by, String text, int seconds) {
        WebElement element = getClickableElement(driver, by, seconds);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets text from a WebElement Text Field
     *
     * @param driver  driver of the application
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static String getText(WebDriver driver, By by, int seconds) {

        String textExtracted = null;
        WebElement element = Action.getVisibleElement(driver, by, seconds);
        if (element != null) {
            textExtracted = element.getText().trim();
        } else {
            Assert.fail("Failed to retrieve the value from the web element.");
        }
        return textExtracted;
    }


    /**
     * Gets text by attribute in a WebElement Text Field
     *
     * @param driver    driver of the application
     * @param by        Type of "By"
     * @param seconds   Waits for the defined time set as parameter
     * @param attribute get text value thought of the tag name
     * @author Rubens Lobo
     */
    public static String getTextAttribute(WebDriver driver, By by, String attribute, int seconds) {

        String textExtracted = null;
        WebElement element = Action.getVisibleElement(driver, by, seconds);
        if (element != null) {
            textExtracted = element.getAttribute(attribute).trim();
        } else {
            Assert.fail("Failed to retrieve the value from the web element.");
        }
        return textExtracted;
    }


    /**
     * Clicks in the web element
     *
     * @param driver  driver of the application
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void clickOnElement(WebDriver driver, By by, int seconds) {
        //if the object is not enabled or visible, this line finalizes the test, but if the object exists the method returns a AppWeb Element object
        WebElement element = getClickableElement(driver, by, seconds);
        //Clicks at the element requested
        element.click();
    }

}
