package com.android.systemui.dreams.complication;

import android.content.Context;
import android.database.ContentObserver;
import android.os.UserHandle;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda19;
import com.android.settingslib.dream.DreamBackend;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.Executor;

public class ComplicationTypesUpdater extends CoreStartable {
    public final DreamBackend mDreamBackend;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Executor mExecutor;
    public final SecureSettings mSecureSettings;

    public final void start() {
        C07901 r0 = new ContentObserver() {
            public static final /* synthetic */ int $r8$clinit = 0;

            public final void onChange(boolean z) {
                ComplicationTypesUpdater.this.mExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda19(this, 1));
            }
        };
        this.mSecureSettings.registerContentObserverForUser("screensaver_enabled_complications", (ContentObserver) r0, UserHandle.myUserId());
        r0.onChange(false);
    }

    public ComplicationTypesUpdater(Context context, DreamBackend dreamBackend, Executor executor, SecureSettings secureSettings, DreamOverlayStateController dreamOverlayStateController) {
        super(context);
        this.mDreamBackend = dreamBackend;
        this.mExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mDreamOverlayStateController = dreamOverlayStateController;
    }
}
