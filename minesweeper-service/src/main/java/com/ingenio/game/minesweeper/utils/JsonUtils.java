package com.ingenio.game.minesweeper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Converts a json to a java object.
     */
    @SneakyThrows
    public <R> R convert(final String json, final Class<R> conversionType) {
        return MAPPER.readValue(json, conversionType);
    }

    /**
     * Converts a java object to a string.
     */
    @SneakyThrows
    public String convertToString(final Object value) {
        return MAPPER.writeValueAsString(value);
    }
}
