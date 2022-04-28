package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifPanelEventSource;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public final class VisualStabilityCoordinator implements Coordinator, Dumpable, NotifPanelEventSource.Callbacks {
    public static final long ALLOW_SECTION_CHANGE_TIMEOUT = 500;
    public final DelayableExecutor mDelayableExecutor;
    public HashMap mEntriesThatCanChangeSection = new HashMap();
    public final HashSet mEntriesWithSuppressedSectionChange = new HashSet();
    public final HeadsUpManager mHeadsUpManager;
    public boolean mIsSuppressingEntryReorder = false;
    public boolean mIsSuppressingGroupChange = false;
    public boolean mIsSuppressingPipelineRun = false;
    public boolean mNotifPanelCollapsing;
    public final NotifPanelEventSource mNotifPanelEventSource;
    public boolean mNotifPanelLaunchingActivity;
    public final C12811 mNotifStabilityManager = new NotifStabilityManager() {
        public final boolean isEntryReorderingAllowed() {
            return VisualStabilityCoordinator.this.mReorderingAllowed;
        }

        public final boolean isEveryChangeAllowed() {
            return VisualStabilityCoordinator.this.mReorderingAllowed;
        }

        public final boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
            boolean z;
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            if (!visualStabilityCoordinator.mReorderingAllowed) {
                HeadsUpManager headsUpManager = visualStabilityCoordinator.mHeadsUpManager;
                Objects.requireNonNull(notificationEntry);
                if (!headsUpManager.isAlerting(notificationEntry.mKey)) {
                    z = false;
                    VisualStabilityCoordinator.this.mIsSuppressingGroupChange |= !z;
                    return z;
                }
            }
            z = true;
            VisualStabilityCoordinator.this.mIsSuppressingGroupChange |= !z;
            return z;
        }

        public final boolean isPipelineRunAllowed() {
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            boolean z = visualStabilityCoordinator.mIsSuppressingPipelineRun;
            boolean z2 = visualStabilityCoordinator.mPipelineRunAllowed;
            visualStabilityCoordinator.mIsSuppressingPipelineRun = z | (!z2);
            return z2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:9:0x0025  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean isSectionChangeAllowed(com.android.systemui.statusbar.notification.collection.NotificationEntry r3) {
            /*
                r2 = this;
                com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator r0 = com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.this
                boolean r1 = r0.mReorderingAllowed
                if (r1 != 0) goto L_0x0022
                com.android.systemui.statusbar.policy.HeadsUpManager r0 = r0.mHeadsUpManager
                java.util.Objects.requireNonNull(r3)
                java.lang.String r1 = r3.mKey
                boolean r0 = r0.isAlerting(r1)
                if (r0 != 0) goto L_0x0022
                com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator r0 = com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.this
                java.util.HashMap r0 = r0.mEntriesThatCanChangeSection
                java.lang.String r1 = r3.mKey
                boolean r0 = r0.containsKey(r1)
                if (r0 == 0) goto L_0x0020
                goto L_0x0022
            L_0x0020:
                r0 = 0
                goto L_0x0023
            L_0x0022:
                r0 = 1
            L_0x0023:
                if (r0 != 0) goto L_0x0031
                com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator r2 = com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.this
                java.util.HashSet r2 = r2.mEntriesWithSuppressedSectionChange
                java.util.Objects.requireNonNull(r3)
                java.lang.String r3 = r3.mKey
                r2.add(r3)
            L_0x0031:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.C12811.isSectionChangeAllowed(com.android.systemui.statusbar.notification.collection.NotificationEntry):boolean");
        }

        public final void onBeginRun() {
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            visualStabilityCoordinator.mIsSuppressingPipelineRun = false;
            visualStabilityCoordinator.mIsSuppressingGroupChange = false;
            visualStabilityCoordinator.mEntriesWithSuppressedSectionChange.clear();
            VisualStabilityCoordinator.this.mIsSuppressingEntryReorder = false;
        }

        public final void onEntryReorderSuppressed() {
            VisualStabilityCoordinator.this.mIsSuppressingEntryReorder = true;
        }
    };
    public boolean mPanelExpanded;
    public boolean mPipelineRunAllowed;
    public boolean mPulsing;
    public boolean mReorderingAllowed;
    public boolean mScreenOn;
    public final StatusBarStateController mStatusBarStateController;
    public final C12822 mStatusBarStateControllerListener = new StatusBarStateController.StateListener() {
        public final void onExpandedChanged(boolean z) {
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            visualStabilityCoordinator.mPanelExpanded = z;
            visualStabilityCoordinator.updateAllowedStates();
        }

        public final void onPulsingChanged(boolean z) {
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            visualStabilityCoordinator.mPulsing = z;
            visualStabilityCoordinator.updateAllowedStates();
        }
    };
    public final VisualStabilityProvider mVisualStabilityProvider;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final C12833 mWakefulnessObserver = new WakefulnessLifecycle.Observer() {
        public final void onFinishedGoingToSleep() {
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            visualStabilityCoordinator.mScreenOn = false;
            visualStabilityCoordinator.updateAllowedStates();
        }

        public final void onStartedWakingUp() {
            VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
            visualStabilityCoordinator.mScreenOn = true;
            visualStabilityCoordinator.updateAllowedStates();
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle);
        boolean z = true;
        if (wakefulnessLifecycle.mWakefulness != 2) {
            WakefulnessLifecycle wakefulnessLifecycle2 = this.mWakefulnessLifecycle;
            Objects.requireNonNull(wakefulnessLifecycle2);
            if (wakefulnessLifecycle2.mWakefulness != 1) {
                z = false;
            }
        }
        this.mScreenOn = z;
        this.mStatusBarStateController.addCallback(this.mStatusBarStateControllerListener);
        this.mPulsing = this.mStatusBarStateController.isPulsing();
        this.mNotifPanelEventSource.registerCallbacks(this);
        C12811 r3 = this.mNotifStabilityManager;
        Objects.requireNonNull(notifPipeline);
        ShadeListBuilder shadeListBuilder = notifPipeline.mShadeListBuilder;
        Objects.requireNonNull(shadeListBuilder);
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        if (shadeListBuilder.mNotifStabilityManager == null) {
            shadeListBuilder.mNotifStabilityManager = r3;
            r3.mListener = new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(shadeListBuilder);
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Attempting to set the NotifStabilityManager more than once. There should only be one visual stability manager. Manager is being set by ");
        NotifStabilityManager notifStabilityManager = shadeListBuilder.mNotifStabilityManager;
        Objects.requireNonNull(notifStabilityManager);
        m.append(notifStabilityManager.mName);
        m.append(" and ");
        m.append(r3.mName);
        throw new IllegalStateException(m.toString());
    }

    public final void onPanelCollapsingChanged(boolean z) {
        this.mNotifPanelCollapsing = z;
        updateAllowedStates();
    }

    public final void updateAllowedStates() {
        boolean z;
        boolean z2 = false;
        if (this.mNotifPanelCollapsing || this.mNotifPanelLaunchingActivity) {
            z = true;
        } else {
            z = false;
        }
        boolean z3 = !z;
        this.mPipelineRunAllowed = z3;
        if ((!this.mScreenOn || !this.mPanelExpanded) && !this.mPulsing) {
            z2 = true;
        }
        this.mReorderingAllowed = z2;
        if ((z3 && this.mIsSuppressingPipelineRun) || (z2 && (this.mIsSuppressingGroupChange || (!this.mEntriesWithSuppressedSectionChange.isEmpty()) || this.mIsSuppressingEntryReorder))) {
            this.mNotifStabilityManager.invalidateList();
        }
        this.mVisualStabilityProvider.setReorderingAllowed(this.mReorderingAllowed);
    }

    public VisualStabilityCoordinator(DelayableExecutor delayableExecutor, DumpManager dumpManager, HeadsUpManager headsUpManager, NotifPanelEventSource notifPanelEventSource, StatusBarStateController statusBarStateController, VisualStabilityProvider visualStabilityProvider, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mHeadsUpManager = headsUpManager;
        this.mVisualStabilityProvider = visualStabilityProvider;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = statusBarStateController;
        this.mDelayableExecutor = delayableExecutor;
        this.mNotifPanelEventSource = notifPanelEventSource;
        dumpManager.registerDumpable(this);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("reorderingAllowed: "), this.mReorderingAllowed, printWriter, "  screenOn: "), this.mScreenOn, printWriter, "  panelExpanded: "), this.mPanelExpanded, printWriter, "  pulsing: "), this.mPulsing, printWriter, "isSuppressingGroupChange: "), this.mIsSuppressingGroupChange, printWriter, "isSuppressingEntryReorder: "), this.mIsSuppressingEntryReorder, printWriter, "entriesWithSuppressedSectionChange: ");
        m.append(this.mEntriesWithSuppressedSectionChange.size());
        printWriter.println(m.toString());
        Iterator it = this.mEntriesWithSuppressedSectionChange.iterator();
        while (it.hasNext()) {
            printWriter.println("  " + ((String) it.next()));
        }
        StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("entriesThatCanChangeSection: ");
        m2.append(this.mEntriesThatCanChangeSection.size());
        printWriter.println(m2.toString());
        for (String str : this.mEntriesThatCanChangeSection.keySet()) {
            printWriter.println("  " + str);
        }
    }
}
