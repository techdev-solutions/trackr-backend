package de.techdev.trackr.core.web.converters;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.Version;

public class TimePropertiesToJson extends JsonModuleSupport {

	@Autowired
	LocalDateConverter localDateConverter;
	
	/**
	 * auto generated
	 */
	private static final long serialVersionUID = 1L;
	

	public TimePropertiesToJson() {
		super("TimeSerialisation", new Version(1, 0, 0, "SNAPSHOT",
				"de.techdev", "trackr"));

		addLocalDateSupport();
		addLocalTimeSupport();
		addInstantSupport();
	}

	private void addLocalDateSupport() {
		addSerializer(LocalDate.class, (value, jgen, provider) -> {
			jgen.writeString(value.toString());
		});
		addDeserializer(LocalDate.class, (jp, ctxt) -> {
			String dateAsString = jp.readValueAs(String.class);
			return localDateConverter.convert(dateAsString);
		});
	}

	private void addLocalTimeSupport() {
		addSerializer(LocalTime.class, (v, jgen, provider) -> {

			String formatted = v.format(ISO_LOCAL_TIME);
			jgen.writeString(formatted);
		});
		addDeserializer(LocalTime.class, (jp, ctxt) -> {
			String dateAsString = jp.readValueAs(String.class);
				return ISO_LOCAL_TIME.parse(dateAsString,
						LocalTime::from);
		});
	}

	private void addInstantSupport() {
		addSerializer(Instant.class, (v, jgen, provider) -> {

			String formatted = ISO_INSTANT.format(v);
			jgen.writeString(formatted);
		});
		addDeserializer(Instant.class, (jp, ctxt) -> {
			String dateAsString = jp.readValueAs(String.class);
				return ISO_INSTANT.parse(dateAsString,Instant::from);
		});
		
	}

}
