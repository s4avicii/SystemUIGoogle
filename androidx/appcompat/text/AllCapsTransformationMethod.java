package androidx.appcompat.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

public final class AllCapsTransformationMethod implements TransformationMethod {
    public Locale mLocale;

    public final void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
    }

    public final CharSequence getTransformation(CharSequence charSequence, View view) {
        if (charSequence != null) {
            return charSequence.toString().toUpperCase(this.mLocale);
        }
        return null;
    }

    public AllCapsTransformationMethod(Context context) {
        this.mLocale = context.getResources().getConfiguration().locale;
    }
}
