package edu.popovd.converter;

import edu.popovd.entity.Birthday;
import jakarta.persistence.AttributeConverter;
import org.hibernate.annotations.ConverterRegistration;

import java.sql.Date;

@ConverterRegistration(converter = BirthdayConverter.class)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        if (attribute == null) {
            return null;
        }
        return Date.valueOf(attribute.birthDate());
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        if (dbData == null) {
            return null;
        }
        return new Birthday(dbData.toLocalDate());
    }
}
