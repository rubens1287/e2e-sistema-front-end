package br.com.core.dates;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DatePicker {

    private DatePicker() {
        throw new IllegalStateException("Get some types of the date and time");
    }

    /**
     * @since 16/05/2018
     */
    private static DateFormat df = null;
    private static String typeOfFormat = "dd/MM/yyyy HH:mm:ss";

    /**
     * get current date and time full
     */
    public static String getDateTime() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime());
    }

    /**
     * get current day
     */
    public static String getDay() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[0].split("/")[0];
    }

    /**
     * get current month
     */
    public static String getMonth() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[0].split("/")[1];
    }

    /**
     * get current year
     */
    public static String getYear() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[0].split("/")[2];
    }

    /**
     * get current hour
     */
    public static String getHour() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[1].split(":")[0];
    }

    /**
     * get current minute
     */
    public static String getMinute() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[1].split(":")[1];
    }

    /**
     * get current second
     */
    public static String getSecond() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[1].split(":")[2];
    }

    /**
     * get current time
     */
    public static String getCurrentTime() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[1];
    }

    /**
     * get current date
     */
    public static String getCurrentDate() {
        df = new SimpleDateFormat(typeOfFormat);
        return df.format(Calendar.getInstance().getTime()).split(" ")[0];
    }

    /**
     * get date Up or Down, you can pass parameter int day as negative or positive
     *
     * @param day set day to get date to up or down, can be positive or negative
     */
    public static String getDateUpOrDown(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }
}
