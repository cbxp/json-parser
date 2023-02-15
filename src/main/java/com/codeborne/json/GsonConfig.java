package com.codeborne.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonConfig {
    public static Gson getGson() {
        return new GsonBuilder()
                .setObjectToNumberStrategy(LocalToNumberPolicy.INT_OR_DOUBLE)
                .create();
    }
}
