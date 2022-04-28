package com.android.systemui.animation;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SurfaceControl;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import java.util.Objects;

/* renamed from: com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1$startAnimation$animationFinishedCallback$1 */
/* compiled from: RemoteTransitionAdapter.kt */
public final class C0669x6b47aed implements IRemoteAnimationFinishedCallback {
    public final /* synthetic */ DrawerLayout.C01322 $counterLauncher;
    public final /* synthetic */ DrawerLayout.C01322 $counterWallpaper;
    public final /* synthetic */ IRemoteTransitionFinishedCallback $finishCallback;
    public final /* synthetic */ TransitionInfo $info;
    public final /* synthetic */ ArrayMap<SurfaceControl, SurfaceControl> $leashMap;

    public final IBinder asBinder() {
        return null;
    }

    public C0669x6b47aed(DrawerLayout.C01322 r1, DrawerLayout.C01322 r2, TransitionInfo transitionInfo, ArrayMap<SurfaceControl, SurfaceControl> arrayMap, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
        this.$counterLauncher = r1;
        this.$counterWallpaper = r2;
        this.$info = transitionInfo;
        this.$leashMap = arrayMap;
        this.$finishCallback = iRemoteTransitionFinishedCallback;
    }

    public final void onAnimationFinished() {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        Objects.requireNonNull(this.$counterLauncher);
        Objects.requireNonNull(this.$counterWallpaper);
        int size = this.$info.getChanges().size() - 1;
        if (size >= 0) {
            while (true) {
                int i = size - 1;
                ((TransitionInfo.Change) this.$info.getChanges().get(size)).getLeash().release();
                if (i < 0) {
                    break;
                }
                size = i;
            }
        }
        int size2 = this.$leashMap.size() - 1;
        if (size2 >= 0) {
            while (true) {
                int i2 = size2 - 1;
                this.$leashMap.valueAt(size2).release();
                if (i2 >= 0) {
                    size2 = i2;
                }
            }
            this.$finishCallback.onTransitionFinished((WindowContainerTransaction) null, transaction);
        }
        try {
            this.$finishCallback.onTransitionFinished((WindowContainerTransaction) null, transaction);
        } catch (RemoteException e) {
            Log.e("ActivityOptionsCompat", "Failed to call app controlled animation finished callback", e);
        }
    }
}
