package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.IActivityManager;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController implements CallbackController<OngoingCallListener>, Dumpable {
    public final ActivityStarter activityStarter;
    public CallNotificationInfo callNotificationInfo;
    public View chipView;
    public final IActivityManager iActivityManager;
    public boolean isCallAppVisible;
    public boolean isFullscreen;
    public final OngoingCallLogger logger;
    public final ArrayList mListeners = new ArrayList();
    public final Executor mainExecutor;
    public final CommonNotifCollection notifCollection;
    public final OngoingCallController$notifListener$1 notifListener = new OngoingCallController$notifListener$1(this);
    public final OngoingCallFlags ongoingCallFlags;
    public final StatusBarStateController statusBarStateController;
    public final OngoingCallController$statusBarStateListener$1 statusBarStateListener = new OngoingCallController$statusBarStateListener$1(this);
    public final Optional<StatusBarWindowController> statusBarWindowController;
    public final Optional<SwipeStatusBarAwayGestureHandler> swipeStatusBarAwayGestureHandler;
    public final SystemClock systemClock;
    public OngoingCallController$setUpUidObserver$1 uidObserver;

    /* compiled from: OngoingCallController.kt */
    public static final class CallNotificationInfo {
        public final long callStartTime;
        public final Intent intent;
        public final boolean isOngoing;
        public final String key;
        public final boolean statusBarSwipedAway;
        public final int uid;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CallNotificationInfo)) {
                return false;
            }
            CallNotificationInfo callNotificationInfo = (CallNotificationInfo) obj;
            return Intrinsics.areEqual(this.key, callNotificationInfo.key) && this.callStartTime == callNotificationInfo.callStartTime && Intrinsics.areEqual(this.intent, callNotificationInfo.intent) && this.uid == callNotificationInfo.uid && this.isOngoing == callNotificationInfo.isOngoing && this.statusBarSwipedAway == callNotificationInfo.statusBarSwipedAway;
        }

        public final int hashCode() {
            int i;
            int hashCode = (Long.hashCode(this.callStartTime) + (this.key.hashCode() * 31)) * 31;
            Intent intent2 = this.intent;
            if (intent2 == null) {
                i = 0;
            } else {
                i = intent2.hashCode();
            }
            int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.uid, (hashCode + i) * 31, 31);
            boolean z = this.isOngoing;
            boolean z2 = true;
            if (z) {
                z = true;
            }
            int i2 = (m + (z ? 1 : 0)) * 31;
            boolean z3 = this.statusBarSwipedAway;
            if (!z3) {
                z2 = z3;
            }
            return i2 + (z2 ? 1 : 0);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CallNotificationInfo(key=");
            m.append(this.key);
            m.append(", callStartTime=");
            m.append(this.callStartTime);
            m.append(", intent=");
            m.append(this.intent);
            m.append(", uid=");
            m.append(this.uid);
            m.append(", isOngoing=");
            m.append(this.isOngoing);
            m.append(", statusBarSwipedAway=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.statusBarSwipedAway, ')');
        }

        public CallNotificationInfo(String str, long j, Intent intent2, int i, boolean z, boolean z2) {
            this.key = str;
            this.callStartTime = j;
            this.intent = intent2;
            this.uid = i;
            this.isOngoing = z;
            this.statusBarSwipedAway = z2;
        }
    }

    public final void addCallback(OngoingCallListener ongoingCallListener) {
        synchronized (this.mListeners) {
            if (!this.mListeners.contains(ongoingCallListener)) {
                this.mListeners.add(ongoingCallListener);
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("Active call notification: ", this.callNotificationInfo));
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.isCallAppVisible, "Call app visible: ", printWriter);
    }

    public final boolean hasOngoingCall() {
        boolean z;
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        if (callNotificationInfo2 != null && callNotificationInfo2.isOngoing) {
            z = true;
        } else {
            z = false;
        }
        if (!z || this.isCallAppVisible) {
            return false;
        }
        return true;
    }

    public final void removeCallback(Object obj) {
        OngoingCallListener ongoingCallListener = (OngoingCallListener) obj;
        synchronized (this.mListeners) {
            this.mListeners.remove(ongoingCallListener);
        }
    }

    public final Unit tearDownChipView() {
        OngoingCallChronometer ongoingCallChronometer;
        View view = this.chipView;
        if (view == null || (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(C1777R.C1779id.ongoing_call_chip_time)) == null) {
            return null;
        }
        ongoingCallChronometer.stop();
        return Unit.INSTANCE;
    }

    public final void updateChip() {
        OngoingCallChronometer ongoingCallChronometer;
        boolean z;
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        if (callNotificationInfo2 != null) {
            View view = this.chipView;
            if (view == null) {
                ongoingCallChronometer = null;
            } else {
                ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(C1777R.C1779id.ongoing_call_chip_time);
            }
            if (view == null || ongoingCallChronometer == null) {
                this.callNotificationInfo = null;
                if (OngoingCallControllerKt.DEBUG) {
                    Log.w("OngoingCallController", "Ongoing call chip view could not be found; Not displaying chip in status bar");
                    return;
                }
                return;
            }
            boolean z2 = false;
            if (callNotificationInfo2.callStartTime > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                ongoingCallChronometer.shouldHideText = false;
                ongoingCallChronometer.requestLayout();
                ongoingCallChronometer.setBase(this.systemClock.elapsedRealtime() + (callNotificationInfo2.callStartTime - this.systemClock.currentTimeMillis()));
                ongoingCallChronometer.start();
            } else {
                ongoingCallChronometer.shouldHideText = true;
                ongoingCallChronometer.requestLayout();
                ongoingCallChronometer.stop();
            }
            updateChipClickListener();
            if (this.iActivityManager.getUidProcessState(callNotificationInfo2.uid, (String) null) <= 2) {
                z2 = true;
            }
            this.isCallAppVisible = z2;
            OngoingCallController$setUpUidObserver$1 ongoingCallController$setUpUidObserver$1 = this.uidObserver;
            if (ongoingCallController$setUpUidObserver$1 != null) {
                this.iActivityManager.unregisterUidObserver(ongoingCallController$setUpUidObserver$1);
            }
            OngoingCallController$setUpUidObserver$1 ongoingCallController$setUpUidObserver$12 = new OngoingCallController$setUpUidObserver$1(callNotificationInfo2, this);
            this.uidObserver = ongoingCallController$setUpUidObserver$12;
            this.iActivityManager.registerUidObserver(ongoingCallController$setUpUidObserver$12, 1, -1, (String) null);
            if (!callNotificationInfo2.statusBarSwipedAway) {
                this.statusBarWindowController.ifPresent(OngoingCallController$updateChip$1.INSTANCE);
            }
            updateGestureListening();
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
            }
        }
    }

    public final void updateChipClickListener() {
        View view;
        boolean z;
        if (this.callNotificationInfo != null) {
            Intent intent = null;
            if (this.isFullscreen) {
                OngoingCallFlags ongoingCallFlags2 = this.ongoingCallFlags;
                Objects.requireNonNull(ongoingCallFlags2);
                boolean z2 = true;
                if (!ongoingCallFlags2.featureFlags.isEnabled(Flags.ONGOING_CALL_STATUS_BAR_CHIP) || !ongoingCallFlags2.featureFlags.isEnabled(Flags.ONGOING_CALL_IN_IMMERSIVE)) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z || !ongoingCallFlags2.featureFlags.isEnabled(Flags.ONGOING_CALL_IN_IMMERSIVE_CHIP_TAP)) {
                    z2 = false;
                }
                if (!z2) {
                    View view2 = this.chipView;
                    if (view2 != null) {
                        view2.setOnClickListener((View.OnClickListener) null);
                        return;
                    }
                    return;
                }
            }
            View view3 = this.chipView;
            if (view3 == null) {
                view = null;
            } else {
                view = view3.findViewById(C1777R.C1779id.ongoing_call_chip_background);
            }
            CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
            if (callNotificationInfo2 != null) {
                intent = callNotificationInfo2.intent;
            }
            if (view3 != null && view != null && intent != null) {
                view3.setOnClickListener(new OngoingCallController$updateChipClickListener$1(this, intent, view));
            }
        }
    }

    public final void updateGestureListening() {
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        if (callNotificationInfo2 != null) {
            boolean z = false;
            if (callNotificationInfo2 != null && callNotificationInfo2.statusBarSwipedAway) {
                z = true;
            }
            if (!z && this.isFullscreen) {
                this.swipeStatusBarAwayGestureHandler.ifPresent(new OngoingCallController$updateGestureListening$2(this));
                return;
            }
        }
        this.swipeStatusBarAwayGestureHandler.ifPresent(OngoingCallController$updateGestureListening$1.INSTANCE);
    }

    public OngoingCallController(CommonNotifCollection commonNotifCollection, OngoingCallFlags ongoingCallFlags2, SystemClock systemClock2, ActivityStarter activityStarter2, Executor executor, IActivityManager iActivityManager2, OngoingCallLogger ongoingCallLogger, DumpManager dumpManager, Optional<StatusBarWindowController> optional, Optional<SwipeStatusBarAwayGestureHandler> optional2, StatusBarStateController statusBarStateController2) {
        this.notifCollection = commonNotifCollection;
        this.ongoingCallFlags = ongoingCallFlags2;
        this.systemClock = systemClock2;
        this.activityStarter = activityStarter2;
        this.mainExecutor = executor;
        this.iActivityManager = iActivityManager2;
        this.logger = ongoingCallLogger;
        this.statusBarWindowController = optional;
        this.swipeStatusBarAwayGestureHandler = optional2;
        this.statusBarStateController = statusBarStateController2;
    }

    public static final void access$removeChip(OngoingCallController ongoingCallController) {
        Objects.requireNonNull(ongoingCallController);
        ongoingCallController.callNotificationInfo = null;
        ongoingCallController.tearDownChipView();
        ongoingCallController.statusBarWindowController.ifPresent(OngoingCallController$removeChip$1.INSTANCE);
        ongoingCallController.swipeStatusBarAwayGestureHandler.ifPresent(OngoingCallController$removeChip$2.INSTANCE);
        Iterator it = ongoingCallController.mListeners.iterator();
        while (it.hasNext()) {
            ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
        }
        OngoingCallController$setUpUidObserver$1 ongoingCallController$setUpUidObserver$1 = ongoingCallController.uidObserver;
        if (ongoingCallController$setUpUidObserver$1 != null) {
            ongoingCallController.iActivityManager.unregisterUidObserver(ongoingCallController$setUpUidObserver$1);
        }
    }
}
