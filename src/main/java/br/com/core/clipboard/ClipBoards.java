package br.com.core.clipboard;

import br.com.core.report.ExtentReports;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * @author Rubens Lobo
 * @since 28/05/2018
 */
public class ClipBoards {


    /**
     * Get Value from ClipBoard and paste it where the cursor is positioned
     *
     * @author Rubens Lobo
     * @since 28/05/2018
     */
    public void pasteValue() {
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e) {
            ExtentReports.appendToReport(e.getMessage());
        }

    }

    /**
     * Set text value to ClipBoards
     *
     * @param value - receive value and pass to clipboard
     * @author Rubens Lobo
     * @since 28/05/2018
     */
    public void setValueToClipBoard(String value) {

        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * Get text value to clipboard
     *
     * @return String
     * @since 28/05/2018
     */
    public String getValueFromClipboard() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null && clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                ret = (String) clipTf
                        .getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                ExtentReports.appendToReport(e.getMessage());
            }
        }
        return ret;
    }


}
