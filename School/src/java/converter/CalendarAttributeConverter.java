/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.sql.Time;
import java.util.Calendar;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Rafa
 */
@Converter(autoApply = false)
public class CalendarAttributeConverter implements AttributeConverter<Calendar, Time> {

    
    @Override
    public Time convertToDatabaseColumn(Calendar attribute) {
        Time time = new Time(attribute.getTimeInMillis());
        return time;
    }

    @Override
    public Calendar convertToEntityAttribute(Time dbData) {
        Calendar calendar = Calendar.getInstance();
        if(dbData == null)
            return null;
        calendar.setTime(dbData);
        return calendar;
    }

    
}
