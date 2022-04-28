package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class AnimatableTextPropertiesParser {
    public static JsonReader.Options ANIMATABLE_PROPERTIES_NAMES = JsonReader.Options.m22of("fc", "sc", "sw", "t");
    public static JsonReader.Options PROPERTIES_NAMES = JsonReader.Options.m22of("a");
}
