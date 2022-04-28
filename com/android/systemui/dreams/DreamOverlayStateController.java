package com.android.systemui.dreams;

import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda5;
import com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.p006qs.tiles.InternetTile$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda20;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public final class DreamOverlayStateController implements CallbackController<Callback> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAvailableComplicationTypes = 0;
    public final ArrayList<Callback> mCallbacks = new ArrayList<>();
    public final HashSet mComplications = new HashSet();
    public final Executor mExecutor;
    public boolean mShouldShowComplications = true;
    public int mState;

    public interface Callback {
        void onAvailableComplicationTypesChanged() {
        }

        void onComplicationsChanged() {
        }

        void onStateChanged() {
        }
    }

    public final void setOverlayActive(boolean z) {
        boolean z2;
        if (z) {
            z2 = true;
        } else {
            z2 = true;
        }
        int i = this.mState;
        if (z2) {
            this.mState = i & -2;
        } else if (z2) {
            this.mState = i | 1;
        }
        if (i != this.mState) {
            this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(this));
        }
    }

    static {
        Log.isLoggable("DreamOverlayStateCtlr", 3);
    }

    public final void addCallback(Object obj) {
        this.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda20(this, (Callback) obj, 2));
    }

    public final void addComplication(Complication complication) {
        this.mExecutor.execute(new InternetTile$$ExternalSyntheticLambda0(this, complication, 1));
    }

    public final Collection<Complication> getComplications() {
        return Collections.unmodifiableCollection((Collection) this.mComplications.stream().filter(new BubbleData$$ExternalSyntheticLambda5(this, 2)).collect(Collectors.toCollection(DreamOverlayStateController$$ExternalSyntheticLambda9.INSTANCE)));
    }

    public final void removeCallback(Object obj) {
        this.mExecutor.execute(new SystemUIApplication$$ExternalSyntheticLambda1(this, (Callback) obj, 1));
    }

    @VisibleForTesting
    public DreamOverlayStateController(Executor executor) {
        this.mExecutor = executor;
    }
}
