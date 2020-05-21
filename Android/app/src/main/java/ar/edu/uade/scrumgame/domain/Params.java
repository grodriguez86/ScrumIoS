package ar.edu.uade.scrumgame.domain;

import java.util.HashMap;
import java.util.Map;

public class Params {
    public static final Params EMPTY = Params.build();
    private Map<String, Object> parameters = new HashMap<>();

    public static Params build() {
        return new Params();
    }

    public void putString(String key, String value) {
        if (value != null) {
            this.parameters.put(key, value);
        }
    }

    public String getString(String key, String defaultValue) {
        try {
            Object value = this.parameters.get(key);
            if (value == null) {
                return defaultValue;
            }
            return (String) value;
        } catch (ClassCastException cce) {
            return defaultValue;
        }
    }

    public Map<String, String> toStringMap() {
        if (this.parameters == null) {
            return new HashMap<>();
        }
        Map<String, String> convertedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : this.parameters.entrySet()) {
            if (entry.getValue() instanceof String) {
                convertedMap.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return convertedMap;
    }
}
