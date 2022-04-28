package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CameraManager;
import androidx.leanback.R$drawable;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: CameraAvailabilityListener.kt */
public final class CameraAvailabilityListener {
    public final CameraAvailabilityListener$availabilityCallback$1 availabilityCallback = new CameraAvailabilityListener$availabilityCallback$1(this);
    public final CameraManager cameraManager;
    public Rect cutoutBounds = new Rect();
    public final Path cutoutProtectionPath;
    public final Set<String> excludedPackageIds;
    public final Executor executor;
    public final ArrayList listeners = new ArrayList();
    public final String targetCameraId;

    /* compiled from: CameraAvailabilityListener.kt */
    public interface CameraTransitionCallback {
        void onApplyCameraProtection(Path path, Rect rect);

        void onHideCameraProtection();
    }

    public CameraAvailabilityListener(CameraManager cameraManager2, Path path, String str, String str2, DelayableExecutor delayableExecutor) {
        this.cameraManager = cameraManager2;
        this.cutoutProtectionPath = path;
        this.targetCameraId = str;
        this.executor = delayableExecutor;
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        this.cutoutBounds.set(R$drawable.roundToInt(rectF.left), R$drawable.roundToInt(rectF.top), R$drawable.roundToInt(rectF.right), R$drawable.roundToInt(rectF.bottom));
        this.excludedPackageIds = CollectionsKt___CollectionsKt.toSet(StringsKt__StringsKt.split$default(str2, new String[]{","}));
    }
}
