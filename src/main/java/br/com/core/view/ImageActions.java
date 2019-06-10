package br.com.core.view;

import br.com.core.asserts.Verifications;
import br.com.core.files.FileManager;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.core.takescreenshot.TakeScreenShot;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageActions extends DriverManager {

    Tesseract instance;
    FileManager fileManager = new FileManager();

    public ImageActions() {
        instance = new Tesseract();
        instance.setDatapath("./src/core/resources/");
        instance.setLanguage("por");
        instance.setLanguage("eng");
    }

    /**
     * click on the image
     *
     * @param pathImage path of the image to set a click
     * @param seconds   Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void clickOnImage(String pathImage, int seconds) {
        try {
            Screen screen = new Screen();
            screen.wait(pathImage, seconds);
            screen.click(pathImage);
            ExtentReports.appendToReport("Image [{" + pathImage + "}] <br/> clicked successfully.");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Unable to locate image element [{" + pathImage + "}] on screen! " + findFailed.getMessage());
        }
    }

    /**
     * double click on the image
     *
     * @param pathImage path of the image to set a click
     * @param seconds   Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void doubleClickOnImage(String pathImage, int seconds) {
        try {
            Screen screen = new Screen();
            screen.wait(pathImage, seconds);
            screen.doubleClick(pathImage);
            ExtentReports.appendToReport("Image [{" + pathImage + "}] <br/> clicked successfully.");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Unable to locate image element [{" + pathImage + "}] on screen! " + findFailed.getMessage());
        }
    }

    /**
     * Set text on the image
     *
     * @param pathImage path of the image to set a click
     * @param text      Set text
     * @param seconds   Waits for the defined time set as parameter
     * @author Rubens Lobo
     */
    public static void setTextOnImage(String pathImage, String text, int seconds) {
        try {
            Screen screen = new Screen();
            screen.wait(pathImage, seconds);
            screen.type(pathImage, text, 0);
            ExtentReports.appendToReport("Image [{" + pathImage + "}] <br/> received text [{" + text + "}] successfully.");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Unable to locate image element [{" + pathImage + "}] on screen! " + findFailed.getMessage());
        }
    }

    /**
     * get text of the web element by image
     *
     * @param by      Type of "By"
     * @param seconds Waits for the defined time set as parameter
     * @return Returns text of the web element for image
     * @author Rubens Lobo
     */
    public String getTextElementImage(WebDriver driver, By by, int seconds) {
        String text = null;
        BufferedImage eleScreenshot;
        WebElement element = Action.getVisibleElement(driver, by, seconds);
        Verifications.highlightElement(driver, element);
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        String pathImage = TakeScreenShot.imageFile(driver, null);
        Point point = element.getLocation();
        BufferedImage fullImg;
        try {
            fullImg = ImageIO.read(new File(pathImage));
            eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "PNG", new File(pathImage));
        } catch (IOException e) {
            ExtentReports.appendToReport("[getTextElementImage] - Failed to read or override image! " + e.getMessage());
        }
        try {
            text = instance.doOCR(new File(pathImage));
            fileManager.deleteAllFile(pathImage);
        } catch (TesseractException e) {
            ExtentReports.appendToReport("[getTextElementImage] - Failed to capture text from image! " + e.getMessage());
        }
        return text;
    }

    /**
     * get text of the web element by image
     *
     * @param x      the X coordinate of the left to right corner of the specified region
     * @param y      the Y coordinate of the up to down corner of the specified region
     * @param width  the width of the specified rectangular region
     * @param height the height of the specified rectangular region
     * @return Returns text of the web element for image
     * @author Rubens Lobo
     */
    public String getTextLocationImage(WebDriver driver, int x, int y, int width, int height) {
        String text = null;
        BufferedImage screenshot;
        String pathImage = TakeScreenShot.imageFile(driver, null);
        BufferedImage fullImg;
        try {
            fullImg = ImageIO.read(new File(pathImage));
            screenshot = fullImg.getSubimage(x, y, width, height);
            ImageIO.write(screenshot, "PNG", new File(pathImage));
        } catch (IOException e) {

            ExtentReports.appendToReport("[getTextElementImage] - Failed to read or override image! " + e.getMessage());
        }
        try {
            text = instance.doOCR(new File(pathImage));
            fileManager.deleteAllFile(pathImage);
        } catch (TesseractException e) {
            ExtentReports.appendToReport("[getTextElementImage] - Failed to capture text from image! " + e.getMessage());
        }

        return text;
    }

    /**
     * get all text of the page by image
     *
     * @return Returns text of the page by image
     * @author Rubens Lobo
     */
    public String getTextAllImage(WebDriver driver) {
        String text = null;
        try {
            String pathImage = TakeScreenShot.imageFile(driver, null);
            text = instance.doOCR(new File(pathImage));
            fileManager.deleteAllFile(pathImage);
        } catch (TesseractException e) {
            Assert.fail("Failed to retrieve text from image! " + e.getMessage());
        }
        return text;
    }
}
