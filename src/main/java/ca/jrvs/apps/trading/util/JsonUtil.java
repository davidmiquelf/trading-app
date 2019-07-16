package ca.jrvs.apps.trading.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class JsonUtil {


  /**
   * Convert a java object to JSON string
   *
   * @param object input object
   * @return JSON String
   */
  public static String toJsonFromObject(
      Object object, boolean prettyJson,
      boolean includeNullValues) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    //Options.
    if (prettyJson) {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    } else {
      mapper.disable(SerializationFeature.INDENT_OUTPUT);
    }

    if (includeNullValues) {
      mapper.setSerializationInclusion(Include.ALWAYS);
    } else {
      mapper.setSerializationInclusion(Include.NON_NULL);
    }

    return mapper.writeValueAsString(object);
  }

  /**
   * Parse JSON string to a object
   *
   * @param json JSON str
   * @param clazz object class
   * @param <T> Type
   * @return Object
   */
  public static <T> T toObjectFromJson(
      String json, Class clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return (T) mapper.readValue(json, clazz);
  }

  public static <T> List<T> toObjectsFromJsonByField(
      String json, String fieldName, Class clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    JsonNode rootNode = mapper.readTree(json);
    List<JsonNode> nodes = rootNode.findValues(fieldName);
    List<T> l = new ArrayList<>();
    for (JsonNode node : nodes){
      l.add((T) mapper.readValue(node.toString(), clazz));
    }
    return l;
  }

}
