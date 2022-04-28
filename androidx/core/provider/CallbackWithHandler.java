package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontRequestWorker;
import androidx.transition.ViewUtilsBase;
import java.util.Objects;

public final class CallbackWithHandler {
    public final ViewUtilsBase mCallback;
    public final Handler mCallbackHandler;

    public CallbackWithHandler(TypefaceCompat.ResourcesCallbackAdapter resourcesCallbackAdapter, Handler handler) {
        this.mCallback = resourcesCallbackAdapter;
        this.mCallbackHandler = handler;
    }

    public final void onTypefaceResult(FontRequestWorker.TypefaceResult typefaceResult) {
        boolean z;
        Objects.requireNonNull(typefaceResult);
        final int i = typefaceResult.mResult;
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            final Typeface typeface = typefaceResult.mTypeface;
            final ViewUtilsBase viewUtilsBase = this.mCallback;
            this.mCallbackHandler.post(new Runnable() {
                public final void run() {
                    ViewUtilsBase viewUtilsBase = ViewUtilsBase.this;
                    Typeface typeface = typeface;
                    TypefaceCompat.ResourcesCallbackAdapter resourcesCallbackAdapter = (TypefaceCompat.ResourcesCallbackAdapter) viewUtilsBase;
                    Objects.requireNonNull(resourcesCallbackAdapter);
                    ResourcesCompat.FontCallback fontCallback = resourcesCallbackAdapter.mFontCallback;
                    if (fontCallback != null) {
                        fontCallback.onFontRetrieved(typeface);
                    }
                }
            });
            return;
        }
        final ViewUtilsBase viewUtilsBase2 = this.mCallback;
        this.mCallbackHandler.post(new Runnable() {
            public final void run() {
                ViewUtilsBase viewUtilsBase = ViewUtilsBase.this;
                int i = i;
                TypefaceCompat.ResourcesCallbackAdapter resourcesCallbackAdapter = (TypefaceCompat.ResourcesCallbackAdapter) viewUtilsBase;
                Objects.requireNonNull(resourcesCallbackAdapter);
                ResourcesCompat.FontCallback fontCallback = resourcesCallbackAdapter.mFontCallback;
                if (fontCallback != null) {
                    fontCallback.onFontRetrievalFailed(i);
                }
            }
        });
    }
}
