package com.ctsi.springboot.server.util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String bean2Json(Object obj) throws IOException {
		StringWriter sw = new StringWriter();
		try (
				JsonGenerator gen = new JsonFactory().createGenerator(sw);
				) {
			mapper.writeValue(gen, obj);
			
			return sw.toString();
		}
		
	}

	public static <T> T json2Bean(String jsonStr, Class<T> objClass)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(jsonStr, objClass);
	}
	
}