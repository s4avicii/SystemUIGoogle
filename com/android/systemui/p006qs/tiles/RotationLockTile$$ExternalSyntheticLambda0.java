package com.android.systemui.p006qs.tiles;

import android.hardware.SensorPrivacyManager;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.RotationLockTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RotationLockTile$$ExternalSyntheticLambda0 implements SensorPrivacyManager.OnSensorPrivacyChangedListener {
    public final /* synthetic */ RotationLockTile f$0;

    public /* synthetic */ RotationLockTile$$ExternalSyntheticLambda0(RotationLockTile rotationLockTile) {
        this.f$0 = rotationLockTile;
    }

    public final void onSensorPrivacyChanged(int i, boolean z) {
        RotationLockTile rotationLockTile = this.f$0;
        Objects.requireNonNull(rotationLockTile);
        rotationLockTile.refreshState((Object) null);
    }
}
