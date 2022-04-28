package com.android.systemui.statusbar.notification.row.wrapper;

import android.view.View;
import com.android.internal.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public final class NotificationCustomViewWrapper extends NotificationViewWrapper {
    public boolean mIsLegacy;
    public int mLegacyColor;

    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        if (needsInversion(this.mBackgroundColor, this.mView)) {
            NotificationViewWrapper.invertViewLuminosity(this.mView);
            float[] fArr = {0.0f, 0.0f, 0.0f};
            ColorUtils.colorToHSL(this.mBackgroundColor, fArr);
            if (this.mBackgroundColor != 0 && ((double) fArr[2]) > 0.5d) {
                fArr[2] = 1.0f - fArr[2];
                this.mBackgroundColor = ColorUtils.HSLToColor(fArr);
            }
        }
    }

    public NotificationCustomViewWrapper(View view, ExpandableNotificationRow expandableNotificationRow) {
        super(view, expandableNotificationRow);
        this.mLegacyColor = expandableNotificationRow.getContext().getColor(C1777R.color.notification_legacy_background_color);
    }

    public final int getCustomBackgroundColor() {
        int customBackgroundColor = super.getCustomBackgroundColor();
        if (customBackgroundColor != 0 || !this.mIsLegacy) {
            return customBackgroundColor;
        }
        return this.mLegacyColor;
    }

    public final void setNotificationFaded(boolean z) {
        super.setNotificationFaded(z);
        NotificationFadeAware.setLayerTypeForFaded(this.mView, z);
    }

    public final void setVisible(boolean z) {
        float f;
        super.setVisible(z);
        View view = this.mView;
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        view.setAlpha(f);
    }

    public final void setLegacy(boolean z) {
        this.mIsLegacy = z;
    }
}
