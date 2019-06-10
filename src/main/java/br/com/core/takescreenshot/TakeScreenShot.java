package br.com.core.takescreenshot;


import br.com.core.files.FileManager;
import br.com.core.properties.PropertiesManager;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import sun.misc.BASE64Encoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TakeScreenShot extends DriverManager {

    static PropertiesManager setupProperties = new PropertiesManager("Setup.properties");

    /**
     * Create a file with a screenshot from the browser (Webdriver object)
     * @return The path to the file of the screenshot
     */
    public static String imageFile(WebDriver driver, String path){
        if(path == null) {
            path = System.getProperty("user.dir") + "\\target\\image\\";
        }
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            path += new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime()) + ".png";
            FileUtils.copyFile(srcFile, new File(path));
        } catch (IOException e) {
            ExtentReports.appendToReport(e.getMessage());
        }
        return path;
    }



    public synchronized static byte[] getImageBytes(WebDriver driver){
        return  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
