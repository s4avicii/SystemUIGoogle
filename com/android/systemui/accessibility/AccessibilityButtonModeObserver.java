package com.android.systemui.accessibility;

import android.content.Context;
import android.util.Log;

public final class AccessibilityButtonModeObserver extends SecureSettingsContentObserver<ModeChangedListener> {

    public interface ModeChangedListener {
        void onAccessibilityButtonModeChanged(int i);
    }

    public AccessibilityButtonModeObserver(Context context) {
        super(context, "accessibility_button_mode");
    }

    public final void onValueChanged(Object obj, String str) {
        ((ModeChangedListener) obj).onAccessibilityButtonModeChanged(parseAccessibilityButtonMode(str));
    }

    public static int parseAccessibilityButtonMode(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            return 0;
        }
    }
}
