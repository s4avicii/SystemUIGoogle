package com.google.android.material.resources;

import android.graphics.Typeface;
import androidx.fragment.app.FragmentContainer;

public final class CancelableFontCallback extends FragmentContainer {
    public final ApplyFont applyFont;
    public boolean cancelled;
    public final Typeface fallbackFont;

    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public final void onFontRetrievalFailed(int i) {
        Typeface typeface = this.fallbackFont;
        if (!this.cancelled) {
            this.applyFont.apply(typeface);
        }
    }

    public final void onFontRetrieved(Typeface typeface, boolean z) {
        if (!this.cancelled) {
            this.applyFont.apply(typeface);
        }
    }

    public CancelableFontCallback(ApplyFont applyFont2, Typeface typeface) {
        this.fallbackFont = typeface;
        this.applyFont = applyFont2;
    }
}
