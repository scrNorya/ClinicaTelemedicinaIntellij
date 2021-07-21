package org.example.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {
	private static ObjectMapper objectMapper = getDefaultObjectMapper();
	
	/*
	 * Instancia o ObjectMapper;
	 * Colocado dentro de um metodo pois pode ser necessario 
	 * adicionar configuracoes no objectMapper ao decorrer do projeto;
	 */
	private static ObjectMapper getDefaultObjectMapper() {
		ObjectMapper defaultObjectMapper = new ObjectMapper();
		return defaultObjectMapper;
	}
	
	/*
	 * Transforma string com conte�do Json em JsonNode (tipo JSON da lib)
	 */
	public static JsonNode parse(String src) throws IOException { 
		return objectMapper.readTree(src);
	}
	
	/*
	 * Transforma conte�do Json em objeto
	 */
	public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
		return objectMapper.treeToValue(node, clazz);
	}
	
	/*
	 * Transforma objeto em conte�do Json
	 */
	public static JsonNode toJson(Object a) {
		return objectMapper.valueToTree(a);
	}
	
	/*
	 * Transforma conte�do Json em string
	 */
	public static String Stringify(JsonNode node) throws JsonProcessingException {
		return generateString(node, false);
	}
	
	/*
	 *  Transforma conte�do Json em string, formatado
	 */
	public static String prettyPrint(JsonNode node) throws JsonProcessingException {
		return generateString(node, true);
	}
	
	/*
	 * 
	 */
	private static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException {
		ObjectWriter objectWritter = objectMapper.writer();
		if(pretty) {
			objectWritter = objectWritter.with(SerializationFeature.INDENT_OUTPUT);
		}
		return objectWritter.writeValueAsString(node);
	}
}
