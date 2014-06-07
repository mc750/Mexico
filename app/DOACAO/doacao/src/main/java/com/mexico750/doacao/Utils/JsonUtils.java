package com.mexico750.doacao.Utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by root on 29/05/14.
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public static String getJson(Object o){
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            Log.e("JSON PROCESSING", "Error during JSON writing");
            return "{}";
        }
    }

    public static <T> T getObject(String value, Class<T> clazz){
        try {
            return OBJECT_MAPPER.readValue(value, clazz);
        } catch (IOException e) {
            Log.e("JSON PROCESSING", "Error during JSON reading");
            return null;
        }
    }
}
