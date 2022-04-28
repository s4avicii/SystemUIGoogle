package com.airbnb.lottie.value;

import com.airbnb.lottie.SimpleColorFilter;
import java.util.Objects;

public class LottieValueCallback<T> {
    public final LottieFrameInfo<T> frameInfo;
    public T value;

    public LottieValueCallback() {
        this.frameInfo = new LottieFrameInfo<>();
        this.value = null;
    }

    public final Object getValueInternal(Object obj, Object obj2) {
        LottieFrameInfo<T> lottieFrameInfo = this.frameInfo;
        Objects.requireNonNull(lottieFrameInfo);
        lottieFrameInfo.startValue = obj;
        lottieFrameInfo.endValue = obj2;
        return getValue(lottieFrameInfo);
    }

    public LottieValueCallback(SimpleColorFilter simpleColorFilter) {
        this.frameInfo = new LottieFrameInfo<>();
        this.value = simpleColorFilter;
    }

    public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
        return this.value;
    }
}
