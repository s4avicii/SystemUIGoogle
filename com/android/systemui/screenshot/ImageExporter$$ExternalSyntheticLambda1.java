package com.android.systemui.screenshot;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleExpandedView$1$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageExporter$$ExternalSyntheticLambda1 implements CallbackToFutureAdapter.Resolver, ScreenshotController.ActionsReadyListener {
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ImageExporter$$ExternalSyntheticLambda1(Object obj, Object obj2) {
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ImageExporter.Task task = (ImageExporter.Task) this.f$1;
        ((Executor) this.f$0).execute(new NavBarTuner$$ExternalSyntheticLambda6(completer, task, 1));
        return task;
    }

    public final void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
        ScreenshotController screenshotController = (ScreenshotController) this.f$0;
        ScreenshotController.C10731 r1 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
        Objects.requireNonNull(screenshotController);
        ((Consumer) this.f$1).accept(savedImageData.uri);
        if (savedImageData.uri == null) {
            screenshotController.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, screenshotController.mPackageName);
            screenshotController.mNotificationsController.notifyScreenshotError(C1777R.string.screenshot_failed_to_save_text);
            return;
        }
        screenshotController.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, screenshotController.mPackageName);
        screenshotController.mScreenshotHandler.post(new BubbleExpandedView$1$$ExternalSyntheticLambda0(screenshotController, 4));
    }
}
