package com.google.android.systemui.gamedashboard;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import com.android.internal.util.ScreenshotHelper;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ ShortcutBarView f$0;
    public final /* synthetic */ ShortcutBarController f$1;
    public final /* synthetic */ ScreenshotHelper f$2;
    public final /* synthetic */ Handler f$3;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda5(ShortcutBarView shortcutBarView, ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler) {
        this.f$0 = shortcutBarView;
        this.f$1 = shortcutBarController;
        this.f$2 = screenshotHelper;
        this.f$3 = handler;
    }

    public final void accept(Object obj) {
        ShortcutBarView shortcutBarView = this.f$0;
        ShortcutBarController shortcutBarController = this.f$1;
        ScreenshotHelper screenshotHelper = this.f$2;
        Handler handler = this.f$3;
        int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
        Objects.requireNonNull(shortcutBarView);
        Objects.requireNonNull(shortcutBarController);
        EntryPointController entryPointController = shortcutBarController.mEntryPointController;
        Objects.requireNonNull(entryPointController);
        ActivityManager.RunningTaskInfo runningTaskInfo = entryPointController.mGameTaskInfo;
        DisplayMetrics displayMetrics = shortcutBarView.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        ((TaskSurfaceHelper) obj).screenshotTask(runningTaskInfo, rect, shortcutBarView.getContext().getMainExecutor(), new ShortcutBarView$$ExternalSyntheticLambda4(shortcutBarView, screenshotHelper, rect, runningTaskInfo, handler));
    }
}
