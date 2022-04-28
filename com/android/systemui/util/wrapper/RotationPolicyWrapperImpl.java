package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import com.android.internal.view.RotationPolicy;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: RotationPolicyWrapper.kt */
public final class RotationPolicyWrapperImpl implements RotationPolicyWrapper {
    public final Context context;
    public final SecureSettings secureSettings;

    public final int getRotationLockOrientation() {
        return RotationPolicy.getRotationLockOrientation(this.context);
    }

    public final boolean isCameraRotationEnabled() {
        if (this.secureSettings.getInt("camera_autorotate", 0) == 1) {
            return true;
        }
        return false;
    }

    public final boolean isRotationLockToggleVisible() {
        return RotationPolicy.isRotationLockToggleVisible(this.context);
    }

    public final boolean isRotationLocked() {
        return RotationPolicy.isRotationLocked(this.context);
    }

    public final void registerRotationPolicyListener(RotationPolicy.RotationPolicyListener rotationPolicyListener) {
        RotationPolicy.registerRotationPolicyListener(this.context, rotationPolicyListener, -1);
    }

    public final void setRotationLock(boolean z) {
        Trace.beginSection("RotationPolicyWrapperImpl#setRotationLock");
        try {
            RotationPolicy.setRotationLock(this.context, z);
        } finally {
            Trace.endSection();
        }
    }

    public RotationPolicyWrapperImpl(Context context2, SecureSettings secureSettings2) {
        this.context = context2;
        this.secureSettings = secureSettings2;
    }
}
