package com.google.android.systemui.communal.dock.callbacks;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.util.Log;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda0;
import com.android.systemui.util.condition.Monitor;
import com.google.android.systemui.communal.dreams.SetupDreamComplication;
import dagger.Lazy;
import java.util.Objects;
import javax.inject.Provider;

public final class NudgeToSetupDreamCallback implements Monitor.Callback {
    public static final boolean DEBUG = Log.isLoggable("NudgeToSetupDream", 3);
    public final SetupDreamComplication mComplication;
    public boolean mDocked;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Provider<Boolean> mDreamSelectedProvider;
    public boolean mDreamSet;
    public final int mNotificationId;
    public final Lazy<Notification> mNotificationLazy;
    public final NotificationManager mNotificationManager;
    public final C22011 mSettingObserver;

    public final void onConditionsChanged(boolean z) {
        if (DEBUG) {
            ViewCompat$$ExternalSyntheticLambda0.m12m("onConditionsChanged:", z, "NudgeToSetupDream");
        }
        this.mDocked = z;
        this.mDreamSet = this.mDreamSelectedProvider.get().booleanValue();
        updatePresentation();
    }

    public final void updatePresentation() {
        if (this.mDreamSet || !this.mDocked) {
            this.mNotificationManager.cancel("NudgeToSetupDream", this.mNotificationId);
            DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
            SetupDreamComplication setupDreamComplication = this.mComplication;
            Objects.requireNonNull(dreamOverlayStateController);
            dreamOverlayStateController.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda0(dreamOverlayStateController, setupDreamComplication, 0));
            return;
        }
        this.mNotificationManager.notify("NudgeToSetupDream", this.mNotificationId, this.mNotificationLazy.get());
        this.mDreamOverlayStateController.addComplication(this.mComplication);
    }

    public NudgeToSetupDreamCallback(SetupDreamComplication setupDreamComplication, DreamOverlayStateController dreamOverlayStateController, Provider<Boolean> provider, NotificationManager notificationManager, Lazy<Notification> lazy, ContentResolver contentResolver, Uri uri, int i) {
        C22011 r0 = new ContentObserver() {
            public final void onChange(boolean z) {
                if (NudgeToSetupDreamCallback.DEBUG) {
                    Log.d("NudgeToSetupDream", "onChange");
                }
                NudgeToSetupDreamCallback nudgeToSetupDreamCallback = NudgeToSetupDreamCallback.this;
                nudgeToSetupDreamCallback.mDreamSet = nudgeToSetupDreamCallback.mDreamSelectedProvider.get().booleanValue();
                NudgeToSetupDreamCallback.this.updatePresentation();
            }
        };
        this.mSettingObserver = r0;
        this.mNotificationManager = notificationManager;
        this.mComplication = setupDreamComplication;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mDreamSelectedProvider = provider;
        this.mNotificationLazy = lazy;
        this.mNotificationId = i;
        contentResolver.registerContentObserver(uri, true, r0);
    }
}
