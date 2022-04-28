package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.leanback.R$color;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.navigationbar.NavigationModeController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.elmyra.actions.Action$$ExternalSyntheticLambda0;
import dagger.Lazy;

public final class TouchInsideHandler implements NgaMessageHandler.ConfigInfoListener, View.OnClickListener, View.OnTouchListener {
    public final AssistLogger mAssistLogger;
    public Runnable mFallback;
    public boolean mGuardLocked;
    public boolean mGuarded;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public boolean mInGesturalMode;
    public PendingIntent mTouchInside;

    public final void onConfigInfo(NgaMessageHandler.ConfigInfo configInfo) {
        this.mTouchInside = configInfo.onTouchInside;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mInGesturalMode) {
            if (motionEvent.getAction() == 0) {
                onTouchInside();
            }
        } else if (this.mGuarded && !this.mGuardLocked && motionEvent.getAction() == 0) {
            this.mGuarded = false;
        } else if (!this.mGuarded && motionEvent.getAction() == 1) {
            onTouchInside();
        }
        return true;
    }

    public final void onTouchInside() {
        PendingIntent pendingIntent = this.mTouchInside;
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException unused) {
                Log.w("TouchInsideHandler", "Touch outside PendingIntent canceled");
                this.mFallback.run();
            }
        } else {
            this.mFallback.run();
        }
        this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_USER_DISMISS);
        MetricsLogger.action(new LogMaker(1716).setType(5).setSubtype(2));
    }

    public TouchInsideHandler(Lazy<AssistManager> lazy, NavigationModeController navigationModeController, AssistLogger assistLogger) {
        this.mAssistLogger = assistLogger;
        this.mFallback = new Action$$ExternalSyntheticLambda0(lazy, 6);
        boolean isGesturalMode = R$color.isGesturalMode(navigationModeController.addListener(new TouchInsideHandler$$ExternalSyntheticLambda0(this)));
        this.mInGesturalMode = isGesturalMode;
        if (isGesturalMode) {
            this.mGuardLocked = false;
            this.mGuarded = false;
        }
    }

    public final void onClick(View view) {
        onTouchInside();
    }
}
