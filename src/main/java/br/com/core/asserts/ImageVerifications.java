package br.com.core.asserts;

import br.com.core.report.ExtentReports;
import br.com.core.view.ImageActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;

public class ImageVerifications {

    private ImageVerifications() {
        throw new IllegalStateException();
    }

    /**
     * Verify if the image exist on screen
     *
     * @param pathImage path of the image + file
     * @param seconds   Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyImageExists(WebDriver driver, String pathImage, int seconds) {

        try {
            Screen screen = new Screen();
            screen.wait(pathImage, seconds);
            ExtentReports.appendToReport(driver, "The image [{" + pathImage + "}] <br/> is identified successfully.");
        } catch (FindFailed findFailed) {
            ExtentReports.appendToReport(findFailed.getMessage());
            Assert.fail("Unable to validate the image element [{" + pathImage + "}] on screen! " + findFailed.getMessage());
        }

    }

    /**
     * Verify if the text exist on screen
     *
     * @param pathImage    path of the image + file
     * @param expectedText is the text to be entered expected to be matched on screen
     * @param seconds      Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyTextImage(WebDriver driver, String pathImage, String expectedText, int seconds) {
        try {
            Screen screen = new Screen();
            screen.wait(pathImage, seconds);
            ImageActions imageActions = new ImageActions();
            Assert.assertTrue(imageActions.getTextAllImage(driver).contains(expectedText),
                    "The text retrieved from the image is different from the expected.");
            ExtentReports.appendToReport(driver, "The text retrieved from the image is validated successfully.");
        } catch (FindFailed findFailed) {
            ExtentReports.appendToReport(findFailed.getMessage());
            Assert.fail("Unable to validate the image element [{" + pathImage + "}] on screen! " + findFailed.getMessage());
        }
    }

    /**
     * verify text of the image by locate Selenium (By by)
     *
     * @param by           Type of "By"
     * @param expectedText set a text to web element
     * @param seconds      Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void verifyTextImageByLocate(WebDriver driver, By by, String expectedText, int seconds) {
        ImageActions imageActions = new ImageActions();
        Assert.assertTrue(imageActions.getTextElementImage(driver, by, seconds).contains(expectedText),
                "The text retrieved from the image is different from the expected.");
        ExtentReports.appendToReport(driver, "The text retrieved from the image is validated successfully.");
    }

    /**
     * verify text of the image using location
     *
     * @param x            the X coordinate of the left to right corner of the specified region ( -| )
     * @param y            the Y coordinate of the up to down corner of the specified region ( _|_ )
     * @param width        the width of the specified rectangular region
     * @param height       the height of the specified rectangular region
     * @param pathImage    path of the image + file
     * @param expectedText is the text to be entered expected to be matched on screen
     * @param seconds      Waits for the defined time set as parameter
     * @return Returns text of the web element for image
     * @author Rubens Lobo
     */
    public static void verifyTextImageLocation(WebDriver driver, int x, int y, int width, int height, String pathImage, String expectedText, int seconds) {
        try {
            ImageActions imageActions = new ImageActions();
            Screen screen = new Screen();
            screen.wait(pathImage, seconds);
            Assert.assertTrue(imageActions.getTextLocationImage(driver, x, y, width, height).contains(expectedText),
                    "The text retrieved from the image is different from the expected.");
            ExtentReports.appendToReport(driver, "The text retrieved from the image is validated successfully.");
        } catch (FindFailed findFailed) {
            ExtentReports.appendToReport(findFailed.getMessage());
            Assert.fail("Unable to validate the image element [{" + pathImage + "}] on screen! " + findFailed.getMessage());
        }

    }
}
