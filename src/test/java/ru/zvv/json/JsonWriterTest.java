package ru.zvv.json;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonWriterTest {

    @Test
    void writeString() {
        assertEquals("{\"name\":\"value\"}",JsonWriter.write("name", "value"));
    }


    @Test
    void writeNumber() {
        assertEquals("{\"name\":5}",JsonWriter.write(Collections.singletonMap("name", 5)));
    }

    @Test
    void writeMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("var1", 5);
        map.put("var2", "test");
        assertEquals("{\"var1\":5,\"var2\":\"test\"}",JsonWriter.write(map));
    }

    @Test
    void writeList() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("var1", 5);
        map.put("varArr", Arrays.asList(1,2,3));
        assertEquals("{\"var1\":5,\"varArr\":[1,2,3]}",JsonWriter.write(map));
    }

    @Test
    void writeInnerMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("var1", Collections.singletonMap("var2", "value"));
        assertEquals("{\"var1\":{\"var2\":\"value\"}}",JsonWriter.write(map));
    }
}