/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Calendar;

/**
 *
 * @author Rafa
 */
public class FormValidator {

    public static LocalDate validateDate(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        try {
            LocalDate myDate = LocalDate.parse(data);
            return myDate;
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer validateInteger(String ocena) {
        try {
            return Integer.parseInt(ocena);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BigDecimal validateMoney(String kwota) {
        if (kwota == null || kwota.equals("")) {
            return null;
        }
        BigDecimal bd;
        try {
            bd = new BigDecimal(kwota);
        } catch (Exception e) {
            return null;
        }
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd;
    }

    public static boolean validateString(String nazwa) {
        return !(nazwa == null || nazwa.equals(""));
    }

    public static Calendar validateTime(String timeString) {
        Calendar calendar= Calendar.getInstance();

        if(timeString == null)
            return null;
        
        if (timeString.length() != 5) {
            return null;
        }
        if (!timeString.substring(2, 3).equals(":")) {
            return null;
        }
        Integer hour = validateInteger(timeString.substring(0, 2));
        Integer minute = validateInteger(timeString.substring(3));
        if (hour == null || hour < 0 || hour >= 24) {
            return null;
        }
        if (minute == null || minute < 0 || minute >= 60) {
            return null;
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        
        return calendar;
    }

}
