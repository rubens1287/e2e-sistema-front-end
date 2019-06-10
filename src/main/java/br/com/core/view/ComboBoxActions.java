/**
 * 
 */
package br.com.core.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author rubens.lobo
 *
 */
public class ComboBoxActions {


	private ComboBoxActions() {
		throw new IllegalStateException("Utility class");
	}
	/**
	 * Selects Combo Box Option by: Text
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void selectComboOptionByText(WebDriver driver, By by,
											   String optionText, int seconds) {
		WebElement element = Action.getClickableElement(driver, by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.selectByVisibleText(optionText);
	}

	/**
	 * Selects Combo Box Option by: Index
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void selectComboOptionByIndex(WebDriver driver,By by, int index, int seconds) {
		WebElement element = Action.getClickableElement(driver, by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.selectByIndex(index);
	}

	/**
	 * Selects Combo Box Option by: Value
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void selectComboOptionByValue(WebDriver driver, By by, String value,
			int seconds) {
		WebElement element = Action.getClickableElement(driver, by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.selectByValue(value);
	}
	
	/**
	 * Deselects Combo Box Option by: Text
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void deselectComboOptionByText(WebDriver driver, By by,
			String optionText, int seconds) {
		WebElement element = Action.getClickableElement(driver,by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.deselectByVisibleText(optionText);
	}

	/**
	 * Deselects Combo Box Option by: Index
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void deselectComboOptionByIndex(WebDriver driver, By by, int index, int seconds) {
		WebElement element = Action.getClickableElement(driver, by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.deselectByIndex(index);
	}

	/**
	 * Selects Combo Box Option by: Value
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void deselectComboOptionByValue(WebDriver driver, By by, String value,
			int seconds) {
		WebElement element = Action.getClickableElement(driver, by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.deselectByValue(value);
	}
	
	/**
	 * Deselects all selected options
	 * 
	 * @author Rubens Lobo 
	 * @param by Type of "By"
	 * @param seconds Waits for the defined time set as parameter
	 */
	public static void deselectAll(WebDriver driver, By by, int seconds) {
		WebElement element = Action.getClickableElement(driver, by, seconds);
		Select selectCombo = new Select(element);
		selectCombo.deselectAll();
	}

}
