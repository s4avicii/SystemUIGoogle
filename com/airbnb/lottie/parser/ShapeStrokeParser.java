package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class ShapeStrokeParser {
    public static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.m22of("n", "v");
    public static JsonReader.Options NAMES = JsonReader.Options.m22of("nm", "c", "w", "o", "lc", "lj", "ml", "hd", "d");
}
