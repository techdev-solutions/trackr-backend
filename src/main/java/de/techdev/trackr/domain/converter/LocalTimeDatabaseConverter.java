package de.techdev.trackr.domain.converter;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalTimeDatabaseConverter implements AttributeConverter<LocalTime, java.sql.Time> {

	@Override
	public Time convertToDatabaseColumn(LocalTime attribute) {
		return attribute == null ? null: Time.valueOf(attribute);
	}

	@Override
	public LocalTime convertToEntityAttribute(Time dbData) {
		return dbData == null ? null :  dbData.toLocalTime();
	}

}