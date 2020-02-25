package ru.zvv.json;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonWriter {
    public static String write(String name, String value) {
        return new JsonWriter().convert(Collections.singletonMap(name, value));
    }

    public static String write(Map<String, ?> map) {
        return new JsonWriter().convert(map);
    }

    private String convert(Map<String, ?> map) {
        StringBuilder sb = new StringBuilder();
        appendMap(sb, map);
        return sb.toString();
    }

    private void appendMap(StringBuilder sb, Map<?, ?> map) {
        sb.append("{");
        boolean needSeparator = false;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getKey() != null) {
                if (needSeparator)
                    sb.append(",");
                appendString(sb, entry.getKey().toString());
                sb.append(":");
                appendAny(sb, entry.getValue());
                needSeparator = true;
            }
        }
        sb.append("}");
    }

    private void appendAny(StringBuilder sb, Object value) {
        if (value instanceof Number)
            sb.append(value);
        else if (value instanceof List<?>)
            appendList(sb, (List<?>) value);
        else if (value instanceof Map)
            appendMap(sb, (Map<?,?>) value);
        else
            appendString(sb, value.toString());
    }

    private void appendList(StringBuilder sb, List<?> values) {
        sb.append("[");
        boolean needSeparator = false;
        for (Object value : values) {
            if (value != null) {
                if (needSeparator)
                    sb.append(",");
                appendAny(sb, value);
                needSeparator = true;
            }
        }
        sb.append("]");
    }

    private void appendString(StringBuilder sb, String key) {
        sb.append('"').append(key.replace("\"", "\\\"")).append('"');
    }
}
