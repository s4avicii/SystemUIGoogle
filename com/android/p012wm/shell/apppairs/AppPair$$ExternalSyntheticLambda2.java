package com.android.p012wm.shell.apppairs;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.split.SplitDecorManager;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.splitscreen.StageTaskListener;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$$ExternalSyntheticLambda2 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda2(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        switch (this.$r8$classId) {
            case 0:
                AppPair appPair = (AppPair) this.f$0;
                Objects.requireNonNull(appPair);
                ((SplitLayout) this.f$1).applySurfaceChanges(transaction, appPair.mTaskLeash1, appPair.mTaskLeash2, appPair.mDimLayer1, appPair.mDimLayer2);
                return;
            default:
                StageTaskListener stageTaskListener = (StageTaskListener) this.f$0;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.f$1;
                Objects.requireNonNull(stageTaskListener);
                if (runningTaskInfo.isVisible) {
                    SplitDecorManager splitDecorManager = stageTaskListener.mSplitDecorManager;
                    Context context = stageTaskListener.mContext;
                    SurfaceControl surfaceControl = stageTaskListener.mRootLeash;
                    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    Objects.requireNonNull(splitDecorManager);
                    if (splitDecorManager.mIconLeash == null || splitDecorManager.mViewHost == null) {
                        Context createWindowContext = context.createWindowContext(context.getDisplay(), 2038, (Bundle) null);
                        splitDecorManager.mHostLeash = surfaceControl;
                        splitDecorManager.mViewHost = new SurfaceControlViewHost(createWindowContext, createWindowContext.getDisplay(), splitDecorManager);
                        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(createWindowContext).inflate(C1777R.layout.split_decor, (ViewGroup) null);
                        splitDecorManager.mResizingIconView = (ImageView) frameLayout.findViewById(C1777R.C1779id.split_resizing_icon);
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(0, 0, 2038, 24, -3);
                        layoutParams.width = bounds.width();
                        layoutParams.height = bounds.height();
                        layoutParams.token = new Binder();
                        layoutParams.setTitle("SplitDecorManager");
                        layoutParams.privateFlags |= 536870976;
                        splitDecorManager.mViewHost.setView(frameLayout, layoutParams);
                        return;
                    }
                    return;
                }
                stageTaskListener.mSplitDecorManager.release(transaction);
                return;
        }
    }
}
