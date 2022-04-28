package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class FontCharacterParser {
    public static final JsonReader.Options DATA_NAMES = JsonReader.Options.m22of("shapes");
    public static final JsonReader.Options NAMES = JsonReader.Options.m22of("ch", "size", "w", "style", "fFamily", "data");
}
