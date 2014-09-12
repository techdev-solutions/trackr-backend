package de.techdev.trackr.core.web.converters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class JsonModuleSupport extends SimpleModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface SerializeInterface<T> {
		public void serialize(T value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException,
				JsonGenerationException;
	}

	public interface DeserializerInterface<T> {
		public T deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException;
	}

	public JsonModuleSupport() {
		super();
	}

	public JsonModuleSupport(String name) {
		super(name);
	}

	public JsonModuleSupport(Version version) {
		super(version);
	}

	public JsonModuleSupport(String name, Version version) {
		super(name, version);
	}

	public JsonModuleSupport(String name, Version version,
			Map<Class<?>, JsonDeserializer<?>> deserializers) {
		super(name, version, deserializers);
	}

	public JsonModuleSupport(String name, Version version,
			List<JsonSerializer<?>> serializers) {
		super(name, version, serializers);
	}

	public JsonModuleSupport(String name, Version version,
			Map<Class<?>, JsonDeserializer<?>> deserializers,
			List<JsonSerializer<?>> serializers) {
		super(name, version, deserializers, serializers);
	}

	public <T> void addSerializer(Class<T> type, SerializeInterface<T> serializer) {
		addSerializer(translateToSerializer(type, serializer));
	}

	public <T> void addDeserializer(Class<T> type, DeserializerInterface<T> deserializer) {
		addDeserializer(type, translateToDeserializer(type, deserializer));
	}

	private <T> JsonSerializer<T> translateToSerializer(Class<T> type, SerializeInterface<T> serializer) {
		return new StdSerializer<T>(type) {
	
			@Override
			public void serialize(T value, JsonGenerator jgen,
					SerializerProvider provider) throws IOException,
					JsonGenerationException {
				serializer.serialize(value, jgen, provider);
	
			}
		};
	}

	private <T> JsonDeserializer<T> translateToDeserializer(Class<T> type, DeserializerInterface<T> deserializer) {
		return new StdDeserializer<T>(type) {
	
			/**
			 * auto generated
			 */
			private static final long serialVersionUID = 1L;
	
			@Override
			public T deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				return deserializer.deserialize(jp, ctxt);
			}
	
		};
	}

}