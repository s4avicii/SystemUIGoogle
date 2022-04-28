package com.android.p012wm.shell.compatui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUILayout */
class CompatUILayout extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CompatUIWindowManager mWindowManager;

    public CompatUILayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void updateCameraTreatmentButton(int i) {
        int i2;
        int i3;
        if (i == 1) {
            i2 = C1777R.C1778drawable.camera_compat_treatment_suggested_ripple;
        } else {
            i2 = C1777R.C1778drawable.camera_compat_treatment_applied_ripple;
        }
        if (i == 1) {
            i3 = C1777R.string.camera_compat_treatment_suggested_button_description;
        } else {
            i3 = C1777R.string.camera_compat_treatment_applied_button_description;
        }
        ImageButton imageButton = (ImageButton) findViewById(C1777R.C1779id.camera_compat_treatment_button);
        imageButton.setImageResource(i2);
        imageButton.setContentDescription(getResources().getString(i3));
        ((TextView) ((LinearLayout) findViewById(C1777R.C1779id.camera_compat_hint)).findViewById(C1777R.C1779id.compat_mode_hint_text)).setText(i3);
    }

    public CompatUILayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompatUILayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        ImageButton imageButton = (ImageButton) findViewById(C1777R.C1779id.size_compat_restart_button);
        imageButton.setOnClickListener(new CompatUILayout$$ExternalSyntheticLambda2(this));
        imageButton.setOnLongClickListener(new CompatUILayout$$ExternalSyntheticLambda5(this));
        LinearLayout linearLayout = (LinearLayout) findViewById(C1777R.C1779id.size_compat_hint);
        ((TextView) linearLayout.findViewById(C1777R.C1779id.compat_mode_hint_text)).setText(C1777R.string.restart_button_description);
        linearLayout.setOnClickListener(new CompatUILayout$$ExternalSyntheticLambda0(this));
        ImageButton imageButton2 = (ImageButton) findViewById(C1777R.C1779id.camera_compat_treatment_button);
        imageButton2.setOnClickListener(new CompatUILayout$$ExternalSyntheticLambda4(this));
        imageButton2.setOnLongClickListener(new CompatUILayout$$ExternalSyntheticLambda6(this));
        ImageButton imageButton3 = (ImageButton) findViewById(C1777R.C1779id.camera_compat_dismiss_button);
        imageButton3.setOnClickListener(new CompatUILayout$$ExternalSyntheticLambda3(this));
        imageButton3.setOnLongClickListener(new CompatUILayout$$ExternalSyntheticLambda7(this));
        ((LinearLayout) findViewById(C1777R.C1779id.camera_compat_hint)).setOnClickListener(new CompatUILayout$$ExternalSyntheticLambda1(this));
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        CompatUIWindowManager compatUIWindowManager = this.mWindowManager;
        Objects.requireNonNull(compatUIWindowManager);
        WindowManager.LayoutParams windowLayoutParams = compatUIWindowManager.getWindowLayoutParams();
        SurfaceControlViewHost surfaceControlViewHost = compatUIWindowManager.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.relayout(windowLayoutParams);
            compatUIWindowManager.updateSurfacePosition();
        }
    }

    public final void setViewVisibility(int i, boolean z) {
        int i2;
        View findViewById = findViewById(i);
        if (z) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        if (findViewById.getVisibility() != i2) {
            findViewById.setVisibility(i2);
        }
    }

    public CompatUILayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
