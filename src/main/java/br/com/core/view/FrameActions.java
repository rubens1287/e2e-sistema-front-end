package br.com.core.view;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import br.com.core.asserts.Verifications;

public class FrameActions  {
	/**
	 * Switches from one frame to other
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	 public static void switchToFrame(WebDriver driver, By by, int seconds) {
		 Verifications.wait(2);
		 WebElement iFrame = Action.getClickableElement(driver, by, seconds);
		 driver.switchTo().frame(iFrame);
	 }
	 
	 public static void swtichToPreviousFrame(WebDriver driver) {
		 String firstFrame = driver.getWindowHandle();
		 driver.switchTo().window(firstFrame);
	 }

	public static boolean swichToWindows(WindowsDriver desktop, String janela, int timeOut){

		int timeOutInternal = 0;
		while(timeOutInternal <= timeOut){

			for (String winHandle : desktop.getWindowHandles()) {
				desktop.switchTo().window(winHandle);
				if(desktop.getTitle().contains(janela)){
					return true;
				}
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			timeOutInternal ++;
		}
		return false;
	}




}
