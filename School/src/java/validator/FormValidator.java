/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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

}
