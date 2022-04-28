package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;

public class MultiUserSwitch extends FrameLayout {
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(Button.class.getName());
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    public final void refreshContentDescription(String str) {
        String str2;
        if (!TextUtils.isEmpty(str)) {
            str2 = this.mContext.getString(C1777R.string.accessibility_quick_settings_user, new Object[]{str});
        } else {
            str2 = null;
        }
        if (!TextUtils.equals(getContentDescription(), str2)) {
            setContentDescription(str2);
        }
    }

    public MultiUserSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
