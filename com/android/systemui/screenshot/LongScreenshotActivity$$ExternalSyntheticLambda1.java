package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LongScreenshotActivity$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ LongScreenshotActivity f$0;
    public final /* synthetic */ LongScreenshotActivity.PendingAction f$1;
    public final /* synthetic */ ListenableFuture f$2;

    public /* synthetic */ LongScreenshotActivity$$ExternalSyntheticLambda1(LongScreenshotActivity longScreenshotActivity, LongScreenshotActivity.PendingAction pendingAction, CallbackToFutureAdapter.SafeFuture safeFuture) {
        this.f$0 = longScreenshotActivity;
        this.f$1 = pendingAction;
        this.f$2 = safeFuture;
    }

    public final void run() {
        LongScreenshotActivity longScreenshotActivity = this.f$0;
        LongScreenshotActivity.PendingAction pendingAction = this.f$1;
        ListenableFuture listenableFuture = this.f$2;
        int i = LongScreenshotActivity.$r8$clinit;
        Objects.requireNonNull(longScreenshotActivity);
        longScreenshotActivity.setButtonsEnabled(true);
        try {
            ImageExporter.Result result = (ImageExporter.Result) listenableFuture.get();
            int ordinal = pendingAction.ordinal();
            if (ordinal == 0) {
                Uri uri = result.uri;
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/png");
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.addFlags(268468225);
                longScreenshotActivity.startActivityAsUser(Intent.createChooser(intent, (CharSequence) null).addFlags(1), UserHandle.CURRENT);
            } else if (ordinal == 1) {
                Uri uri2 = result.uri;
                String string = longScreenshotActivity.getString(C1777R.string.config_screenshotEditor);
                Intent intent2 = new Intent("android.intent.action.EDIT");
                if (!TextUtils.isEmpty(string)) {
                    intent2.setComponent(ComponentName.unflattenFromString(string));
                }
                intent2.setDataAndType(uri2, "image/png");
                intent2.addFlags(3);
                longScreenshotActivity.mTransitionView.setImageBitmap(longScreenshotActivity.mOutputBitmap);
                longScreenshotActivity.mTransitionView.setVisibility(0);
                longScreenshotActivity.mTransitionView.setTransitionName("screenshot_preview_image");
                longScreenshotActivity.mTransitionStarted = true;
                longScreenshotActivity.startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(longScreenshotActivity, longScreenshotActivity.mTransitionView, "screenshot_preview_image").toBundle());
            } else if (ordinal == 2) {
                longScreenshotActivity.finishAndRemoveTask();
            }
        } catch (InterruptedException | CancellationException | ExecutionException e) {
            Log.e("Screenshot", "failed to export", e);
        }
    }
}
