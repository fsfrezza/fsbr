package com.fsbr.utils;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public final class JsonUtils {
    public static <T> Map<String, T> readMap(String json) throws Exception {
        if (json == null)
            return null;

        ObjectReader reader = new ObjectMapper().readerFor(Map.class);
        MappingIterator<Map<String, T>> it = reader.readValues(json);

        if (it.hasNextValue()) {
            Map<String, T> res = it.next();
            return res.isEmpty() ? Collections.emptyMap() : res;
        }

        return Collections.emptyMap();
    }
}