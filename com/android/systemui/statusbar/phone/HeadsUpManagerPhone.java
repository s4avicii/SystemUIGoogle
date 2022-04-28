package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Region;
import android.os.SystemClock;
import android.util.Pools;
import androidx.collection.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.SystemBarUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.AlertingNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.google.android.systemui.dreamliner.DockObserver$$ExternalSyntheticLambda0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public final class HeadsUpManagerPhone extends HeadsUpManager implements Dumpable, OnHeadsUpChangedListener {
    public AnimationStateHandler mAnimationStateHandler;
    public final KeyguardBypassController mBypassController;
    public final HashSet<NotificationEntry> mEntriesToRemoveAfterExpand = new HashSet<>();
    public final ArraySet<NotificationEntry> mEntriesToRemoveWhenReorderingAllowed = new ArraySet<>(0);
    public final C14181 mEntryPool = new Pools.Pool<HeadsUpEntryPhone>() {
        public Stack<HeadsUpEntryPhone> mPoolObjects = new Stack<>();

        public final Object acquire() {
            if (!this.mPoolObjects.isEmpty()) {
                return this.mPoolObjects.pop();
            }
            return new HeadsUpEntryPhone();
        }

        public final boolean release(Object obj) {
            this.mPoolObjects.push((HeadsUpEntryPhone) obj);
            return true;
        }
    };
    @VisibleForTesting
    public final int mExtensionTime;
    public final GroupMembershipManager mGroupMembershipManager;
    public boolean mHeadsUpGoingAway;
    public int mHeadsUpInset;
    public final ArrayList mHeadsUpPhoneListeners = new ArrayList();
    public boolean mIsExpanded;
    public final HeadsUpManagerPhone$$ExternalSyntheticLambda0 mOnReorderingAllowedListener = new HeadsUpManagerPhone$$ExternalSyntheticLambda0(this);
    public boolean mReleaseOnExpandFinish;
    public int mStatusBarState;
    public final C14203 mStatusBarStateListener;
    public final HashSet<String> mSwipedOutKeys = new HashSet<>();
    public final Region mTouchableRegion = new Region();
    public boolean mTrackingHeadsUp;
    public final VisualStabilityProvider mVisualStabilityProvider;

    public interface AnimationStateHandler {
    }

    public class HeadsUpEntryPhone extends HeadsUpManager.HeadsUpEntry {
        public boolean extended;
        public boolean mMenuShownPinned;

        public HeadsUpEntryPhone() {
            super();
        }

        public final void setEntry(NotificationEntry notificationEntry) {
            DockObserver$$ExternalSyntheticLambda0 dockObserver$$ExternalSyntheticLambda0 = new DockObserver$$ExternalSyntheticLambda0(this, notificationEntry, 2);
            this.mEntry = notificationEntry;
            this.mRemoveAlertRunnable = dockObserver$$ExternalSyntheticLambda0;
            this.mPostTime = calculatePostTime();
            updateEntry(true);
        }

        public final void setExpanded(boolean z) {
            if (this.expanded != z) {
                this.expanded = z;
                if (z) {
                    removeAutoRemovalCallbacks();
                } else {
                    updateEntry(false);
                }
            }
        }

        public final long calculateFinishTime() {
            int i;
            long calculateFinishTime = super.calculateFinishTime();
            if (this.extended) {
                i = HeadsUpManagerPhone.this.mExtensionTime;
            } else {
                i = 0;
            }
            return calculateFinishTime + ((long) i);
        }

        public final boolean isSticky() {
            if (super.isSticky() || this.mMenuShownPinned) {
                return true;
            }
            return false;
        }

        public final void reset() {
            super.reset();
            this.mMenuShownPinned = false;
            this.extended = false;
        }

        public final void updateEntry(boolean z) {
            super.updateEntry(z);
            if (HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.contains(this.mEntry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.remove(this.mEntry);
            }
            if (HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.contains(this.mEntry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.remove(this.mEntry);
            }
        }
    }

    public interface OnHeadsUpPhoneListenerChange {
        void onHeadsUpGoingAwayStateChanged(boolean z);
    }

    public final boolean canRemoveImmediately(String str) {
        if (this.mSwipedOutKeys.contains(str)) {
            this.mSwipedOutKeys.remove(str);
            return true;
        }
        HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpEntryPhone) this.mAlertEntries.get(str);
        HeadsUpEntryPhone headsUpEntryPhone2 = (HeadsUpEntryPhone) getTopHeadsUpEntry();
        if (headsUpEntryPhone == null || headsUpEntryPhone != headsUpEntryPhone2 || super.canRemoveImmediately(str)) {
            return true;
        }
        return false;
    }

    public final HeadsUpManager.HeadsUpEntry createAlertEntry() {
        return (HeadsUpManager.HeadsUpEntry) this.mEntryPool.acquire();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("HeadsUpManagerPhone state:");
        printWriter.print("  mTouchAcceptanceDelay=");
        printWriter.println(this.mTouchAcceptanceDelay);
        printWriter.print("  mSnoozeLengthMs=");
        printWriter.println(this.mSnoozeLengthMs);
        printWriter.print("  now=");
        Objects.requireNonNull(this.mClock);
        printWriter.println(SystemClock.elapsedRealtime());
        printWriter.print("  mUser=");
        printWriter.println(this.mUser);
        for (AlertingNotificationManager.AlertEntry alertEntry : this.mAlertEntries.values()) {
            printWriter.print("  HeadsUpEntry=");
            printWriter.println(alertEntry.mEntry);
        }
        int size = this.mSnoozedPackages.size();
        printWriter.println("  snoozed packages: " + size);
        for (int i = 0; i < size; i++) {
            printWriter.print("    ");
            printWriter.print(this.mSnoozedPackages.valueAt(i));
            printWriter.print(", ");
            printWriter.println(this.mSnoozedPackages.keyAt(i));
        }
        printWriter.print("  mBarState=");
        printWriter.println(this.mStatusBarState);
        printWriter.print("  mTouchableRegion=");
        printWriter.println(this.mTouchableRegion);
    }

    public final void setHeadsUpGoingAway(boolean z) {
        if (z != this.mHeadsUpGoingAway) {
            this.mHeadsUpGoingAway = z;
            Iterator it = this.mHeadsUpPhoneListeners.iterator();
            while (it.hasNext()) {
                ((OnHeadsUpPhoneListenerChange) it.next()).onHeadsUpGoingAwayStateChanged(z);
            }
        }
    }

    public final boolean shouldExtendLifetime(NotificationEntry notificationEntry) {
        VisualStabilityProvider visualStabilityProvider = this.mVisualStabilityProvider;
        Objects.requireNonNull(visualStabilityProvider);
        if (!visualStabilityProvider.isReorderingAllowed || !(!canRemoveImmediately(notificationEntry.mKey))) {
            return false;
        }
        return true;
    }

    public final boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry) {
        boolean z;
        boolean z2;
        if (this.mStatusBarState != 0 || this.mIsExpanded) {
            z = false;
        } else {
            z = true;
        }
        if (this.mBypassController.getBypassEnabled()) {
            if (this.mStatusBarState == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            z |= z2;
        }
        if (z || HeadsUpManager.hasFullScreenIntent(notificationEntry)) {
            return true;
        }
        return false;
    }

    public final void updateResources() {
        Resources resources = this.mContext.getResources();
        this.mHeadsUpInset = resources.getDimensionPixelSize(C1777R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(this.mContext);
    }

    public HeadsUpManagerPhone(Context context, HeadsUpManagerLogger headsUpManagerLogger, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController) {
        super(context, headsUpManagerLogger);
        C14203 r2 = new StatusBarStateController.StateListener() {
            public final void onDozingChanged(boolean z) {
                if (!z) {
                    for (AlertingNotificationManager.AlertEntry updateEntry : HeadsUpManagerPhone.this.mAlertEntries.values()) {
                        updateEntry.updateEntry(true);
                    }
                }
            }

            public final void onStateChanged(int i) {
                boolean z;
                HeadsUpManagerPhone headsUpManagerPhone = HeadsUpManagerPhone.this;
                boolean z2 = false;
                if (headsUpManagerPhone.mStatusBarState == 1) {
                    z = true;
                } else {
                    z = false;
                }
                if (i == 1) {
                    z2 = true;
                }
                headsUpManagerPhone.mStatusBarState = i;
                if (z && !z2 && headsUpManagerPhone.mBypassController.getBypassEnabled()) {
                    ArrayList arrayList = new ArrayList();
                    for (AlertingNotificationManager.AlertEntry next : HeadsUpManagerPhone.this.mAlertEntries.values()) {
                        NotificationEntry notificationEntry = next.mEntry;
                        if (notificationEntry != null && notificationEntry.isBubble() && !next.isSticky()) {
                            NotificationEntry notificationEntry2 = next.mEntry;
                            Objects.requireNonNull(notificationEntry2);
                            arrayList.add(notificationEntry2.mKey);
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        HeadsUpManagerPhone.this.removeAlertEntry((String) it.next());
                    }
                }
            }
        };
        this.mStatusBarStateListener = r2;
        this.mExtensionTime = this.mContext.getResources().getInteger(C1777R.integer.ambient_notification_extension_time);
        statusBarStateController.addCallback(r2);
        this.mBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mVisualStabilityProvider = visualStabilityProvider;
        updateResources();
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() {
            public final void onDensityOrFontScaleChanged() {
                HeadsUpManagerPhone.this.updateResources();
            }

            public final void onThemeChanged() {
                HeadsUpManagerPhone.this.updateResources();
            }
        });
    }

    public final void onAlertEntryRemoved(AlertingNotificationManager.AlertEntry alertEntry) {
        super.onAlertEntryRemoved(alertEntry);
        this.mEntryPool.release((HeadsUpEntryPhone) alertEntry);
    }

    public final void setMenuShown(NotificationEntry notificationEntry, boolean z) {
        Objects.requireNonNull(notificationEntry);
        HeadsUpManager.HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.mKey);
        if ((headsUpEntry instanceof HeadsUpEntryPhone) && notificationEntry.isRowPinned()) {
            HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpEntryPhone) headsUpEntry;
            Objects.requireNonNull(headsUpEntryPhone);
            if (headsUpEntryPhone.mMenuShownPinned != z) {
                headsUpEntryPhone.mMenuShownPinned = z;
                if (z) {
                    headsUpEntryPhone.removeAutoRemovalCallbacks();
                } else {
                    headsUpEntryPhone.updateEntry(false);
                }
            }
        }
    }

    public final void snooze() {
        super.snooze();
        this.mReleaseOnExpandFinish = true;
    }

    public final boolean isTrackingHeadsUp() {
        return this.mTrackingHeadsUp;
    }
}
