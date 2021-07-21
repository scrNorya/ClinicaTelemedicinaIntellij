package org.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonUtils {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    /*
     * Instancia o ObjectMapper; Colocado dentro de um metodo pois pode ser
     * necessario adicionar configuracoes no objectMapper ao decorrer do projeto;
     */
    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    /*
     * Transforma string com conteúdo Json em JsonNode (tipo JSON da lib)
     */
    public static JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(src);
    }

    /*
     * Transforma conteúdo Json em objeto
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(node, clazz);
    }

    /*
     * Transforma objeto em conteúdo Json
     */
    public static JsonNode toJson(Object a) {
        return objectMapper.valueToTree(a);
    }

    /*
     * Transforma conteúdo Json em string
     */
    public static String Stringify(JsonNode node) throws JsonProcessingException {
        return generateString(node, false);
    }

    /*
     * Transforma conteúdo Json em string, formatado
     */
    public static String prettyPrint(JsonNode node) throws JsonProcessingException {
        return generateString(node, true);
    }

    /*
     *
     */
    private static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWritter = objectMapper.writer();
        if (pretty) {
            objectWritter = objectWritter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWritter.writeValueAsString(node);
    }

    public static Map<String, Object> readValues(String jsonFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(jsonFile));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> nodes = mapper.readValue(br, Map.class);;
            return nodes;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		try {
//			map = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {
//			});
//
//			int teste = 1;
//		} catch (StreamReadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DatabindException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        return null;
    }

    public static void appendToArray(File jsonFile, Object value) throws IOException {

        Objects.requireNonNull(jsonFile);
        Objects.requireNonNull(value);
        if (jsonFile.isDirectory()) {
            throw new IllegalArgumentException("File can not be a directory!");
        }

        JsonNode node;
        try {
            node = readArrayOrCreateNew(jsonFile);

            if (node.isArray()) {
                ArrayNode array = (ArrayNode) node;
                array.addPOJO(value);
            } else {
                ArrayNode rootArray = objectMapper.createArrayNode();
                rootArray.add(node);
                rootArray.addPOJO(value);
                node = rootArray;
            }
            objectMapper.writeValue(jsonFile, node);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static JsonNode readArrayOrCreateNew(File jsonFile) throws IOException {
        if (jsonFile.exists() && jsonFile.length() > 0) {
            return objectMapper.readTree(jsonFile);
        }

        return objectMapper.createArrayNode();
    }
}
