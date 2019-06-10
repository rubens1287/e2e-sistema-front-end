package br.com.core.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static br.com.core.view.Action.*;

public class CheckBoxActions {


	private CheckBoxActions() {
		throw new IllegalStateException("Utility class");
	}
	/**
	 * select check box by index
	 *
	 * @param driver driver of the application
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 * @return Returns an existing element from the screen
	 */
	public static void selectCheckBoxOptionBy(WebDriver driver, By by, int seconds) {

		WebElement checkbox = getClickableElement(driver, by, seconds);
		if(!checkbox.isSelected()) {
			checkbox.click();
		}
	}
}
