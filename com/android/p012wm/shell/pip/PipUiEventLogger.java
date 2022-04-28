package com.android.p012wm.shell.pip;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.android.internal.logging.UiEventLogger;

/* renamed from: com.android.wm.shell.pip.PipUiEventLogger */
public final class PipUiEventLogger {
    public final PackageManager mPackageManager;
    public String mPackageName;
    public int mPackageUid = -1;
    public final UiEventLogger mUiEventLogger;

    public final void setTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ComponentName componentName;
        int i = -1;
        if (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) {
            this.mPackageName = null;
            this.mPackageUid = -1;
            return;
        }
        String packageName = componentName.getPackageName();
        this.mPackageName = packageName;
        try {
            i = this.mPackageManager.getApplicationInfoAsUser(packageName, 0, runningTaskInfo.userId).uid;
        } catch (PackageManager.NameNotFoundException unused) {
        }
        this.mPackageUid = i;
    }

    /* renamed from: com.android.wm.shell.pip.PipUiEventLogger$PipUiEventEnum */
    public enum PipUiEventEnum implements UiEventLogger.UiEventEnum {
        PICTURE_IN_PICTURE_ENTER(603),
        PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN(604),
        PICTURE_IN_PICTURE_TAP_TO_REMOVE(605),
        PICTURE_IN_PICTURE_DRAG_TO_REMOVE(606),
        PICTURE_IN_PICTURE_SHOW_MENU(607),
        PICTURE_IN_PICTURE_HIDE_MENU(608),
        PICTURE_IN_PICTURE_RESIZE(610),
        PICTURE_IN_PICTURE_STASH_UNSTASHED(709),
        PICTURE_IN_PICTURE_STASH_LEFT(710),
        PICTURE_IN_PICTURE_STASH_RIGHT(711),
        PICTURE_IN_PICTURE_SHOW_SETTINGS(933);
        
        private final int mId;

        /* access modifiers changed from: public */
        PipUiEventEnum(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void log(PipUiEventEnum pipUiEventEnum) {
        int i;
        String str = this.mPackageName;
        if (str != null && (i = this.mPackageUid) != -1) {
            this.mUiEventLogger.log(pipUiEventEnum, i, str);
        }
    }

    public PipUiEventLogger(UiEventLogger uiEventLogger, PackageManager packageManager) {
        this.mUiEventLogger = uiEventLogger;
        this.mPackageManager = packageManager;
    }
}
