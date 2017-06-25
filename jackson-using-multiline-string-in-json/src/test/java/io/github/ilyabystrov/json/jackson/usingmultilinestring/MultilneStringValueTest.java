package io.github.ilyabystrov.json.jackson.usingmultilinestring;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class MultilneStringValueTest {
    
    @Test
    public void test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        Config config = mapper.readValue(
                new BufferedReader( new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("config.json.txt"))),
                Config.class);

        System.out.println("config.name = " + config.getName());
        System.out.println("config.sql = " + config.getSql());

        TypeReference<LinkedHashMap<String,String>> typeRef 
            = new TypeReference<LinkedHashMap<String,String>>() {};
        LinkedHashMap<String,String> map = mapper.readValue(
                new BufferedReader( new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("config.json.txt"))),
                typeRef);
        System.out.println("map = " + map);
    }
}
