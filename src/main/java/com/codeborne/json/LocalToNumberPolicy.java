package com.codeborne.json;

import com.google.gson.JsonParseException;
import com.google.gson.ToNumberStrategy;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.math.BigDecimal;

public enum LocalToNumberPolicy implements ToNumberStrategy {
    INT_OR_DOUBLE {
        @Override public Number readNumber(JsonReader in) throws IOException, com.google.gson.JsonParseException {
            String value = in.nextString();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException intE) {
                try {
                    return Long.parseLong(value);
                } catch (NumberFormatException longE) {
                    try {
//                        return new BigDecimal(value);
                        Double d = Double.valueOf(value);
                        if ((d.isInfinite() || d.isNaN()) && !in.isLenient()) {
                            throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + in.getPreviousPath());
                        }
                        return d > 10 ? new BigDecimal(value) : d;
                    } catch (NumberFormatException doubleE) {
                        throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), doubleE);
                    }
                }
            }
        }
    },
}
