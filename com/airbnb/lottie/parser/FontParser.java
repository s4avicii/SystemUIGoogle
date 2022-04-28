package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class FontParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.m22of("fFamily", "fName", "fStyle", "ascent");
}
