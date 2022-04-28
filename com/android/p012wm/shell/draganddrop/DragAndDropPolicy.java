package com.android.p012wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.WindowConfiguration;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.ResolveInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.logging.InstanceId;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.splitscreen.SplitscreenEventLogger;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.draganddrop.DragAndDropPolicy */
public final class DragAndDropPolicy {
    public final ActivityTaskManager mActivityTaskManager;
    public final Context mContext;
    public InstanceId mLoggerSessionId;
    public DragSession mSession;
    public final SplitScreenController mSplitScreen;
    public final Starter mStarter;
    public final ArrayList<Target> mTargets = new ArrayList<>();

    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropPolicy$DefaultStarter */
    public static class DefaultStarter implements Starter {
        public final Context mContext;

        public final void startIntent(PendingIntent pendingIntent, Intent intent, int i, Bundle bundle) {
            try {
                pendingIntent.send(this.mContext, 0, intent, (PendingIntent.OnFinished) null, (Handler) null, (String) null, bundle);
            } catch (PendingIntent.CanceledException e) {
                Slog.e("DragAndDropPolicy", "Failed to launch activity", e);
            }
        }

        public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
            try {
                ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, (Rect) null, bundle, userHandle);
            } catch (ActivityNotFoundException e) {
                Slog.e("DragAndDropPolicy", "Failed to launch shortcut", e);
            }
        }

        public DefaultStarter(Context context) {
            this.mContext = context;
        }

        public final void startTask(int i, int i2, Bundle bundle) {
            try {
                ActivityTaskManager.getService().startActivityFromRecents(i, bundle);
            } catch (RemoteException e) {
                Slog.e("DragAndDropPolicy", "Failed to launch task", e);
            }
        }
    }

    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropPolicy$Starter */
    public interface Starter {
        void startIntent(PendingIntent pendingIntent, Intent intent, int i, Bundle bundle);

        void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle);

        void startTask(int i, int i2, Bundle bundle);
    }

    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropPolicy$Target */
    public static class Target {
        public final Rect drawRegion;
        public final Rect hitRegion;
        public final int type;

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Target {hit=");
            m.append(this.hitRegion);
            m.append(" draw=");
            m.append(this.drawRegion);
            m.append("}");
            return m.toString();
        }

        public Target(int i, Rect rect, Rect rect2) {
            this.type = i;
            this.hitRegion = rect;
            this.drawRegion = rect2;
        }
    }

    public Intent getStartIntentFillInIntent(PendingIntent pendingIntent, int i) {
        ComponentName componentName;
        boolean z;
        ComponentName componentName2;
        List queryIntentComponents = pendingIntent.queryIntentComponents(0);
        if (!queryIntentComponents.isEmpty()) {
            componentName = ((ResolveInfo) queryIntentComponents.get(0)).activityInfo.getComponentName();
        } else {
            componentName = null;
        }
        SplitScreenController splitScreenController = this.mSplitScreen;
        int i2 = 1;
        if (splitScreenController == null || !splitScreenController.isSplitScreenVisible()) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mSession.runningTaskInfo;
            if (runningTaskInfo != null) {
                componentName2 = runningTaskInfo.baseActivity;
            } else {
                componentName2 = null;
            }
        } else {
            if (i != 0) {
                i2 = 0;
            }
            componentName2 = this.mSplitScreen.getTaskInfo(i2).baseActivity;
        }
        if (!componentName2.equals(componentName)) {
            return null;
        }
        Intent intent = new Intent();
        intent.addFlags(134217728);
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -32505692, 0, (String) null, (Object[]) null);
        }
        return intent;
    }

    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession */
    public static class DragSession {
        public final DisplayLayout displayLayout;
        public Intent dragData;
        @WindowConfiguration.ActivityType
        public int runningTaskActType = 1;
        public ActivityManager.RunningTaskInfo runningTaskInfo;
        @WindowConfiguration.WindowingMode
        public int runningTaskWinMode = 0;

        public DragSession(ActivityTaskManager activityTaskManager, DisplayLayout displayLayout2, ClipData clipData) {
            this.displayLayout = displayLayout2;
        }
    }

    public void handleDrop(Target target, ClipData clipData) {
        int i;
        int i2;
        Bundle bundle;
        SplitScreenController splitScreenController;
        if (target != null && this.mTargets.contains(target)) {
            int i3 = target.type;
            if (i3 == 2 || i3 == 1) {
                i = 1;
            } else {
                i = 0;
            }
            if (i3 == 0 || (splitScreenController = this.mSplitScreen) == null) {
                i2 = -1;
            } else {
                int i4 = i ^ 1;
                InstanceId instanceId = this.mLoggerSessionId;
                Objects.requireNonNull(splitScreenController);
                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                Objects.requireNonNull(stageCoordinator);
                SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
                Objects.requireNonNull(splitscreenEventLogger);
                splitscreenEventLogger.mDragEnterPosition = i4;
                splitscreenEventLogger.mDragEnterSessionId = instanceId;
                i2 = i4;
            }
            ClipDescription description = clipData.getDescription();
            Intent intent = this.mSession.dragData;
            boolean hasMimeType = description.hasMimeType("application/vnd.android.task");
            boolean hasMimeType2 = description.hasMimeType("application/vnd.android.shortcut");
            if (intent.hasExtra("android.intent.extra.ACTIVITY_OPTIONS")) {
                bundle = intent.getBundleExtra("android.intent.extra.ACTIVITY_OPTIONS");
            } else {
                bundle = new Bundle();
            }
            Bundle bundle2 = bundle;
            if (hasMimeType) {
                this.mStarter.startTask(intent.getIntExtra("android.intent.extra.TASK_ID", -1), i2, bundle2);
            } else if (hasMimeType2) {
                this.mStarter.startShortcut(intent.getStringExtra("android.intent.extra.PACKAGE_NAME"), intent.getStringExtra("android.intent.extra.shortcut.ID"), i2, bundle2, (UserHandle) intent.getParcelableExtra("android.intent.extra.USER"));
            } else {
                PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("android.intent.extra.PENDING_INTENT");
                this.mStarter.startIntent(pendingIntent, getStartIntentFillInIntent(pendingIntent, i2), i2, bundle2);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [com.android.wm.shell.draganddrop.DragAndDropPolicy$Starter] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DragAndDropPolicy(android.content.Context r2, android.app.ActivityTaskManager r3, com.android.p012wm.shell.splitscreen.SplitScreenController r4, com.android.p012wm.shell.draganddrop.DragAndDropPolicy.Starter r5) {
        /*
            r1 = this;
            r1.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1.mTargets = r0
            r1.mContext = r2
            r1.mActivityTaskManager = r3
            r1.mSplitScreen = r4
            if (r4 == 0) goto L_0x0013
            goto L_0x0014
        L_0x0013:
            r4 = r5
        L_0x0014:
            r1.mStarter = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.draganddrop.DragAndDropPolicy.<init>(android.content.Context, android.app.ActivityTaskManager, com.android.wm.shell.splitscreen.SplitScreenController, com.android.wm.shell.draganddrop.DragAndDropPolicy$Starter):void");
    }
}
