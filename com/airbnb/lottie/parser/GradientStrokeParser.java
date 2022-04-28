package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class GradientStrokeParser {
    public static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.m22of("n", "v");
    public static final JsonReader.Options GRADIENT_NAMES = JsonReader.Options.m22of("p", "k");
    public static JsonReader.Options NAMES = JsonReader.Options.m22of("nm", "g", "o", "t", "s", "e", "w", "lc", "lj", "ml", "hd", "d");
}
