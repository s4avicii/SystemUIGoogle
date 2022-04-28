package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UdfpsFpmOtherView.kt */
public final class UdfpsFpmOtherView extends UdfpsAnimationView {
    public final UdfpsFpDrawable fingerprintDrawable;

    public UdfpsFpmOtherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fingerprintDrawable = new UdfpsFpDrawable(context);
    }

    public final void onFinishInflate() {
        View findViewById = findViewById(C1777R.C1779id.udfps_fpm_other_fp_view);
        Intrinsics.checkNotNull(findViewById);
        ((ImageView) findViewById).setImageDrawable(this.fingerprintDrawable);
    }

    public final UdfpsDrawable getDrawable() {
        return this.fingerprintDrawable;
    }
}
