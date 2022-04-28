package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.p012wm.shell.C1777R;

/* compiled from: PrivacyItem.kt */
public enum PrivacyType {
    TYPE_CAMERA(C1777R.string.privacy_type_camera, 17303150, "android.permission-group.CAMERA", "camera"),
    TYPE_MICROPHONE(C1777R.string.privacy_type_microphone, 17303155, "android.permission-group.MICROPHONE", "microphone"),
    TYPE_LOCATION(C1777R.string.privacy_type_location, 17303154, "android.permission-group.LOCATION", "location");
    
    private final int iconId;
    private final String logName;
    private final int nameId;
    private final String permGroupName;

    /* access modifiers changed from: public */
    PrivacyType(int i, int i2, String str, String str2) {
        this.nameId = i;
        this.iconId = i2;
        this.permGroupName = str;
        this.logName = str2;
    }

    public final Drawable getIcon(Context context) {
        return context.getResources().getDrawable(this.iconId, context.getTheme());
    }

    public final String getName(Context context) {
        return context.getResources().getString(this.nameId);
    }

    public final int getIconId() {
        return this.iconId;
    }

    public final String getLogName() {
        return this.logName;
    }

    public final int getNameId() {
        return this.nameId;
    }

    public final String getPermGroupName() {
        return this.permGroupName;
    }
}
