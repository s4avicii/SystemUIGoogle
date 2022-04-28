package com.android.systemui.statusbar.notification.collection.legacy;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import androidx.collection.ArraySet;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda4;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public final class VisualStabilityManager implements OnHeadsUpChangedListener, Dumpable {
    public ArraySet<View> mAddedChildren = new ArraySet<>(0);
    public ArraySet<View> mAllowedReorderViews = new ArraySet<>(0);
    public boolean mGroupChangedAllowed;
    public final ArrayList<Callback> mGroupChangesAllowedCallbacks = new ArrayList<>();
    public final Handler mHandler;
    public boolean mIsTemporaryReorderingAllowed;
    public ArraySet<NotificationEntry> mLowPriorityReorderingViews = new ArraySet<>(0);
    public final TaskView$$ExternalSyntheticLambda4 mOnTemporaryReorderingExpired = new TaskView$$ExternalSyntheticLambda4(this, 8);
    public boolean mPanelExpanded;
    public final ArraySet<Callback> mPersistentGroupCallbacks = new ArraySet<>(0);
    public final ArraySet<Callback> mPersistentReorderingCallbacks = new ArraySet<>(0);
    public boolean mPulsing;
    public boolean mReorderingAllowed;
    public final ArrayList<Callback> mReorderingAllowedCallbacks = new ArrayList<>();
    public boolean mScreenOn;
    public long mTemporaryReorderingStart;
    public VisibilityLocationProvider mVisibilityLocationProvider;
    public final VisualStabilityProvider mVisualStabilityProvider;
    public final C12873 mWakefulnessObserver;

    public interface Callback {
        void onChangeAllowed();
    }

    public static void notifyChangeAllowed(ArrayList arrayList, ArraySet arraySet) {
        int i = 0;
        while (i < arrayList.size()) {
            Callback callback = (Callback) arrayList.get(i);
            callback.onChangeAllowed();
            if (!arraySet.contains(callback)) {
                arrayList.remove(callback);
                i--;
            }
            i++;
        }
    }

    public final boolean canReorderNotification(ExpandableNotificationRow expandableNotificationRow) {
        if (this.mReorderingAllowed || this.mAddedChildren.contains(expandableNotificationRow)) {
            return true;
        }
        ArraySet<NotificationEntry> arraySet = this.mLowPriorityReorderingViews;
        Objects.requireNonNull(expandableNotificationRow);
        if (arraySet.contains(expandableNotificationRow.mEntry)) {
            return true;
        }
        if (!this.mAllowedReorderViews.contains(expandableNotificationRow) || this.mVisibilityLocationProvider.isInVisibleLocation(expandableNotificationRow.mEntry)) {
            return false;
        }
        return true;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        long j;
        printWriter.println("VisualStabilityManager state:");
        printWriter.print("  mIsTemporaryReorderingAllowed=");
        printWriter.println(this.mIsTemporaryReorderingAllowed);
        printWriter.print("  mTemporaryReorderingStart=");
        printWriter.println(this.mTemporaryReorderingStart);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        printWriter.print("    Temporary reordering window has been open for ");
        if (this.mIsTemporaryReorderingAllowed) {
            j = this.mTemporaryReorderingStart;
        } else {
            j = elapsedRealtime;
        }
        printWriter.print(elapsedRealtime - j);
        printWriter.println("ms");
        printWriter.println();
    }

    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        if (z) {
            this.mAllowedReorderViews.add(notificationEntry.row);
        }
    }

    public final void updateAllowedStates() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if ((!this.mScreenOn || !this.mPanelExpanded || this.mIsTemporaryReorderingAllowed) && !this.mPulsing) {
            z = true;
        } else {
            z = false;
        }
        if (!z || this.mReorderingAllowed) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mReorderingAllowed = z;
        if (z2) {
            notifyChangeAllowed(this.mReorderingAllowedCallbacks, this.mPersistentReorderingCallbacks);
        }
        this.mVisualStabilityProvider.setReorderingAllowed(z);
        if ((!this.mScreenOn || !this.mPanelExpanded) && !this.mPulsing) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (!z3 || this.mGroupChangedAllowed) {
            z4 = false;
        }
        this.mGroupChangedAllowed = z3;
        if (z4) {
            notifyChangeAllowed(this.mGroupChangesAllowedCallbacks, this.mPersistentGroupCallbacks);
        }
    }

    public VisualStabilityManager(NotificationEntryManager notificationEntryManager, VisualStabilityProvider visualStabilityProvider, Handler handler, StatusBarStateController statusBarStateController, WakefulnessLifecycle wakefulnessLifecycle, DumpManager dumpManager) {
        C12873 r0 = new WakefulnessLifecycle.Observer() {
            public final void onFinishedGoingToSleep() {
                VisualStabilityManager visualStabilityManager = VisualStabilityManager.this;
                Objects.requireNonNull(visualStabilityManager);
                visualStabilityManager.mScreenOn = false;
                visualStabilityManager.updateAllowedStates();
            }

            public final void onStartedWakingUp() {
                VisualStabilityManager visualStabilityManager = VisualStabilityManager.this;
                Objects.requireNonNull(visualStabilityManager);
                visualStabilityManager.mScreenOn = true;
                visualStabilityManager.updateAllowedStates();
            }
        };
        this.mWakefulnessObserver = r0;
        this.mVisualStabilityProvider = visualStabilityProvider;
        this.mHandler = handler;
        dumpManager.registerDumpable(this);
        if (notificationEntryManager != null) {
            notificationEntryManager.addNotificationEntryListener(new NotificationEntryListener() {
                public final void onPreEntryUpdated(NotificationEntry notificationEntry) {
                    boolean z;
                    boolean isAmbient = notificationEntry.isAmbient();
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    Objects.requireNonNull(expandableNotificationRow);
                    if (isAmbient != expandableNotificationRow.mIsLowPriority) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        VisualStabilityManager.this.mLowPriorityReorderingViews.add(notificationEntry);
                    }
                }
            });
        }
        if (statusBarStateController != null) {
            boolean isPulsing = statusBarStateController.isPulsing();
            if (this.mPulsing != isPulsing) {
                this.mPulsing = isPulsing;
                updateAllowedStates();
            }
            statusBarStateController.addCallback(new StatusBarStateController.StateListener() {
                public final void onExpandedChanged(boolean z) {
                    VisualStabilityManager visualStabilityManager = VisualStabilityManager.this;
                    Objects.requireNonNull(visualStabilityManager);
                    visualStabilityManager.mPanelExpanded = z;
                    visualStabilityManager.updateAllowedStates();
                }

                public final void onPulsingChanged(boolean z) {
                    VisualStabilityManager visualStabilityManager = VisualStabilityManager.this;
                    Objects.requireNonNull(visualStabilityManager);
                    if (visualStabilityManager.mPulsing != z) {
                        visualStabilityManager.mPulsing = z;
                        visualStabilityManager.updateAllowedStates();
                    }
                }
            });
        }
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.mObservers.add(r0);
        }
    }
}
