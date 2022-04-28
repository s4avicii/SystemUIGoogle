package com.airbnb.lottie.model;

public final class Marker {
    public final float durationFrames;
    public final String name;
    public final float startFrame;

    public Marker(String str, float f, float f2) {
        this.name = str;
        this.durationFrames = f2;
        this.startFrame = f;
    }
}
