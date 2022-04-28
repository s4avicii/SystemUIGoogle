package com.android.p012wm.shell.bubbles;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseSetArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda7;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.bubbles.BubbleData;
import com.android.p012wm.shell.bubbles.BubbleLogger;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.ShellExecutor$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.pip.PinnedStackListenerForwarder;
import com.android.p012wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda1;
import com.android.systemui.keyguard.KeyguardViewMediator$9$$ExternalSyntheticLambda0;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda2;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda1;
import com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda4;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda7;
import com.android.systemui.wmshell.BubblesManager$8$$ExternalSyntheticLambda0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.bubbles.BubbleController */
public final class BubbleController {
    public boolean mAddedToWindowManager = false;
    public final IStatusBarService mBarService;
    public BubbleBadgeIconFactory mBubbleBadgeIconFactory;
    public BubbleData mBubbleData;
    public final C17955 mBubbleDataListener = new BubbleData.Listener() {
        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public final void applyUpdate(com.android.p012wm.shell.bubbles.BubbleData.Update r17) {
            /*
                r16 = this;
                r0 = r16
                r1 = r17
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                r2.ensureStackViewCreated()
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                java.util.Objects.requireNonNull(r2)
                boolean r3 = r2.mOverflowDataLoadNeeded
                r4 = 3
                r5 = 0
                r6 = 0
                if (r3 != 0) goto L_0x0016
                goto L_0x002e
            L_0x0016:
                r2.mOverflowDataLoadNeeded = r5
                com.android.wm.shell.bubbles.BubbleDataRepository r3 = r2.mDataRepository
                int r7 = r2.mCurrentUserId
                com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda12 r8 = new com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda12
                r8.<init>(r2)
                java.util.Objects.requireNonNull(r3)
                kotlinx.coroutines.internal.ContextScope r2 = r3.ioScope
                com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1 r9 = new com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1
                r9.<init>(r3, r7, r8, r6)
                kotlinx.coroutines.BuildersKt.launch$default(r2, r6, r6, r9, r4)
            L_0x002e:
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r2 = r2.mStackView
                java.util.Objects.requireNonNull(r2)
                com.android.wm.shell.bubbles.BubbleData r3 = r2.mBubbleData
                java.util.List r3 = r3.getOverflowBubbles()
                java.util.Iterator r3 = r3.iterator()
            L_0x003f:
                boolean r7 = r3.hasNext()
                r8 = 1
                if (r7 == 0) goto L_0x0062
                java.lang.Object r7 = r3.next()
                com.android.wm.shell.bubbles.Bubble r7 = (com.android.p012wm.shell.bubbles.Bubble) r7
                boolean r7 = r7.showDot()
                if (r7 == 0) goto L_0x003f
                com.android.wm.shell.bubbles.BubbleOverflow r2 = r2.mBubbleOverflow
                java.util.Objects.requireNonNull(r2)
                r2.showDot = r8
                com.android.wm.shell.bubbles.BadgedImageView r2 = r2.overflowBtn
                if (r2 != 0) goto L_0x005e
                goto L_0x0071
            L_0x005e:
                r2.updateDotVisibility(r8)
                goto L_0x0071
            L_0x0062:
                com.android.wm.shell.bubbles.BubbleOverflow r2 = r2.mBubbleOverflow
                java.util.Objects.requireNonNull(r2)
                r2.showDot = r5
                com.android.wm.shell.bubbles.BadgedImageView r2 = r2.overflowBtn
                if (r2 != 0) goto L_0x006e
                goto L_0x0071
            L_0x006e:
                r2.updateDotVisibility(r8)
            L_0x0071:
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleData$Listener r2 = r2.mOverflowListener
                if (r2 == 0) goto L_0x007a
                r2.applyUpdate(r1)
            L_0x007a:
                boolean r2 = r1.expandedChanged
                if (r2 == 0) goto L_0x009c
                boolean r2 = r1.expanded
                if (r2 != 0) goto L_0x009c
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r2 = r2.mStackView
                r2.setExpanded(r5)
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r2 = r2.mSysuiProxy
                com.android.systemui.wmshell.BubblesManager$5 r2 = (com.android.systemui.wmshell.BubblesManager.C17525) r2
                java.util.Objects.requireNonNull(r2)
                java.util.concurrent.Executor r3 = r5
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda8 r7 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda8
                r7.<init>(r2, r5)
                r3.execute(r7)
            L_0x009c:
                java.util.ArrayList r2 = new java.util.ArrayList
                java.util.ArrayList r3 = r1.removedBubbles
                r2.<init>(r3)
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                java.util.Iterator r2 = r2.iterator()
            L_0x00ac:
                boolean r7 = r2.hasNext()
                if (r7 == 0) goto L_0x0219
                java.lang.Object r7 = r2.next()
                android.util.Pair r7 = (android.util.Pair) r7
                java.lang.Object r9 = r7.first
                com.android.wm.shell.bubbles.Bubble r9 = (com.android.p012wm.shell.bubbles.Bubble) r9
                java.lang.Object r7 = r7.second
                java.lang.Integer r7 = (java.lang.Integer) r7
                int r7 = r7.intValue()
                com.android.wm.shell.bubbles.BubbleController r10 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r10 = r10.mStackView
                r11 = 5
                r12 = 8
                if (r10 == 0) goto L_0x0177
                r13 = r5
            L_0x00ce:
                int r14 = r10.getBubbleCount()
                if (r13 >= r14) goto L_0x013c
                com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout r14 = r10.mBubbleContainer
                android.view.View r14 = r14.getChildAt(r13)
                boolean r15 = r14 instanceof com.android.p012wm.shell.bubbles.BadgedImageView
                if (r15 == 0) goto L_0x0139
                com.android.wm.shell.bubbles.BadgedImageView r14 = (com.android.p012wm.shell.bubbles.BadgedImageView) r14
                java.util.Objects.requireNonNull(r14)
                com.android.wm.shell.bubbles.BubbleViewProvider r14 = r14.mBubble
                if (r14 == 0) goto L_0x00ec
                java.lang.String r14 = r14.getKey()
                goto L_0x00ed
            L_0x00ec:
                r14 = r6
            L_0x00ed:
                java.util.Objects.requireNonNull(r9)
                java.lang.String r15 = r9.mKey
                boolean r14 = r14.equals(r15)
                if (r14 == 0) goto L_0x0139
                com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout r14 = r10.mBubbleContainer
                r14.removeViewAt(r13)
                com.android.wm.shell.bubbles.BubbleData r13 = r10.mBubbleData
                java.lang.String r14 = r9.mKey
                boolean r13 = r13.hasOverflowBubbleWithKey(r14)
                if (r13 == 0) goto L_0x011c
                com.android.wm.shell.bubbles.BubbleExpandedView r13 = r9.mExpandedView
                if (r13 == 0) goto L_0x0110
                r13.cleanUpExpandedState()
                r9.mExpandedView = r6
            L_0x0110:
                android.app.PendingIntent r13 = r9.mIntent
                if (r13 == 0) goto L_0x0119
                com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda0 r14 = r9.mIntentCancelListener
                r13.unregisterCancelListener(r14)
            L_0x0119:
                r9.mIntentActive = r5
                goto L_0x0132
            L_0x011c:
                com.android.wm.shell.bubbles.BubbleExpandedView r13 = r9.mExpandedView
                if (r13 == 0) goto L_0x0125
                r13.cleanUpExpandedState()
                r9.mExpandedView = r6
            L_0x0125:
                android.app.PendingIntent r13 = r9.mIntent
                if (r13 == 0) goto L_0x012e
                com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda0 r14 = r9.mIntentCancelListener
                r13.unregisterCancelListener(r14)
            L_0x012e:
                r9.mIntentActive = r5
                r9.mIconView = r6
            L_0x0132:
                r10.updateExpandedView()
                r10.logBubbleEvent(r9, r11)
                goto L_0x0177
            L_0x0139:
                int r13 = r13 + 1
                goto L_0x00ce
            L_0x013c:
                java.util.Objects.requireNonNull(r9)
                int r10 = r9.mFlags
                r10 = r10 & r12
                if (r10 == 0) goto L_0x0146
                r10 = r8
                goto L_0x0147
            L_0x0146:
                r10 = r5
            L_0x0147:
                if (r10 == 0) goto L_0x0160
                com.android.wm.shell.bubbles.BubbleExpandedView r10 = r9.mExpandedView
                if (r10 == 0) goto L_0x0152
                r10.cleanUpExpandedState()
                r9.mExpandedView = r6
            L_0x0152:
                android.app.PendingIntent r10 = r9.mIntent
                if (r10 == 0) goto L_0x015b
                com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda0 r13 = r9.mIntentCancelListener
                r10.unregisterCancelListener(r13)
            L_0x015b:
                r9.mIntentActive = r5
                r9.mIconView = r6
                goto L_0x0177
            L_0x0160:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r13 = "was asked to remove Bubble, but didn't find the view! "
                r10.append(r13)
                r10.append(r9)
                java.lang.String r10 = r10.toString()
                java.lang.String r13 = "Bubbles"
                android.util.Log.d(r13, r10)
            L_0x0177:
                if (r7 == r12) goto L_0x00ac
                r10 = 14
                if (r7 != r10) goto L_0x017f
                goto L_0x00ac
            L_0x017f:
                if (r7 == r11) goto L_0x0185
                r10 = 12
                if (r7 != r10) goto L_0x0188
            L_0x0185:
                r3.add(r9)
            L_0x0188:
                com.android.wm.shell.bubbles.BubbleController r10 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleData r10 = r10.mBubbleData
                java.util.Objects.requireNonNull(r9)
                java.lang.String r12 = r9.mKey
                boolean r10 = r10.hasBubbleInStackWithKey(r12)
                if (r10 != 0) goto L_0x01fd
                com.android.wm.shell.bubbles.BubbleController r10 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleData r10 = r10.mBubbleData
                java.lang.String r12 = r9.mKey
                boolean r10 = r10.hasOverflowBubbleWithKey(r12)
                if (r10 != 0) goto L_0x01c5
                boolean r10 = r9.showInShade()
                if (r10 == 0) goto L_0x01af
                if (r7 == r11) goto L_0x01af
                r10 = 9
                if (r7 != r10) goto L_0x01c5
            L_0x01af:
                com.android.wm.shell.bubbles.BubbleController r7 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r7 = r7.mSysuiProxy
                java.lang.String r10 = r9.mKey
                com.android.systemui.wmshell.BubblesManager$5 r7 = (com.android.systemui.wmshell.BubblesManager.C17525) r7
                java.util.Objects.requireNonNull(r7)
                java.util.concurrent.Executor r11 = r5
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda6 r12 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda6
                r12.<init>(r7, r10)
                r11.execute(r12)
                goto L_0x01fd
            L_0x01c5:
                boolean r7 = r9.mIsBubble
                if (r7 == 0) goto L_0x01e8
                com.android.wm.shell.bubbles.BubbleController r7 = com.android.p012wm.shell.bubbles.BubbleController.this
                java.util.Objects.requireNonNull(r7)
                r9.mIsBubble = r5
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r10 = r7.mSysuiProxy
                java.lang.String r11 = r9.mKey
                com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda11 r12 = new com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda11
                r12.<init>(r7, r5, r9)
                com.android.systemui.wmshell.BubblesManager$5 r10 = (com.android.systemui.wmshell.BubblesManager.C17525) r10
                java.util.Objects.requireNonNull(r10)
                java.util.concurrent.Executor r7 = r5
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda7 r13 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda7
                r13.<init>(r10, r11, r12)
                r7.execute(r13)
            L_0x01e8:
                com.android.wm.shell.bubbles.BubbleController r7 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r7 = r7.mSysuiProxy
                java.lang.String r10 = r9.mKey
                com.android.systemui.wmshell.BubblesManager$5 r7 = (com.android.systemui.wmshell.BubblesManager.C17525) r7
                java.util.Objects.requireNonNull(r7)
                java.util.concurrent.Executor r11 = r5
                com.android.wm.shell.TaskView$$ExternalSyntheticLambda7 r12 = new com.android.wm.shell.TaskView$$ExternalSyntheticLambda7
                r12.<init>(r7, r10, r4)
                r11.execute(r12)
            L_0x01fd:
                com.android.wm.shell.bubbles.BubbleController r7 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r7 = r7.mSysuiProxy
                java.lang.String r10 = r9.mKey
                com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda2 r11 = new com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda2
                r11.<init>(r0, r9, r8)
                com.android.systemui.wmshell.BubblesManager$5 r7 = (com.android.systemui.wmshell.BubblesManager.C17525) r7
                java.util.Objects.requireNonNull(r7)
                java.util.concurrent.Executor r9 = r5
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda7 r12 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda7
                r12.<init>(r7, r10, r11)
                r9.execute(r12)
                goto L_0x00ac
            L_0x0219:
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleDataRepository r7 = r2.mDataRepository
                int r2 = r2.mCurrentUserId
                java.util.Objects.requireNonNull(r7)
                java.util.ArrayList r3 = com.android.p012wm.shell.bubbles.BubbleDataRepository.transform(r3)
                com.android.wm.shell.bubbles.storage.BubbleVolatileRepository r10 = r7.volatileRepository
                java.util.Objects.requireNonNull(r10)
                monitor-enter(r10)
                java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ all -> 0x053a }
                r11.<init>()     // Catch:{ all -> 0x053a }
                java.util.Iterator r12 = r3.iterator()     // Catch:{ all -> 0x053a }
            L_0x0235:
                boolean r13 = r12.hasNext()     // Catch:{ all -> 0x053a }
                if (r13 == 0) goto L_0x026a
                java.lang.Object r13 = r12.next()     // Catch:{ all -> 0x053a }
                r14 = r13
                com.android.wm.shell.bubbles.storage.BubbleEntity r14 = (com.android.p012wm.shell.bubbles.storage.BubbleEntity) r14     // Catch:{ all -> 0x053a }
                monitor-enter(r10)     // Catch:{ all -> 0x053a }
                android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r15 = r10.entitiesByUser     // Catch:{ all -> 0x0267 }
                java.lang.Object r15 = r15.get(r2)     // Catch:{ all -> 0x0267 }
                java.util.List r15 = (java.util.List) r15     // Catch:{ all -> 0x0267 }
                if (r15 != 0) goto L_0x0257
                java.util.ArrayList r15 = new java.util.ArrayList     // Catch:{ all -> 0x0267 }
                r15.<init>()     // Catch:{ all -> 0x0267 }
                android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r9 = r10.entitiesByUser     // Catch:{ all -> 0x0267 }
                r9.put(r2, r15)     // Catch:{ all -> 0x0267 }
            L_0x0257:
                monitor-exit(r10)     // Catch:{ all -> 0x053a }
                com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$removeBubbles$1$1 r9 = new com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$removeBubbles$1$1     // Catch:{ all -> 0x053a }
                r9.<init>(r14)     // Catch:{ all -> 0x053a }
                boolean r9 = r15.removeIf(r9)     // Catch:{ all -> 0x053a }
                if (r9 == 0) goto L_0x0235
                r11.add(r13)     // Catch:{ all -> 0x053a }
                goto L_0x0235
            L_0x0267:
                r0 = move-exception
                monitor-exit(r10)     // Catch:{ all -> 0x053a }
                throw r0     // Catch:{ all -> 0x053a }
            L_0x026a:
                r10.uncache(r11)     // Catch:{ all -> 0x053a }
                monitor-exit(r10)
                boolean r2 = r3.isEmpty()
                r2 = r2 ^ r8
                if (r2 == 0) goto L_0x0284
                kotlinx.coroutines.StandaloneCoroutine r2 = r7.job
                kotlinx.coroutines.internal.ContextScope r3 = r7.ioScope
                com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1 r9 = new com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1
                r9.<init>(r2, r7, r6)
                kotlinx.coroutines.StandaloneCoroutine r2 = kotlinx.coroutines.BuildersKt.launch$default(r3, r6, r6, r9, r4)
                r7.job = r2
            L_0x0284:
                com.android.wm.shell.bubbles.Bubble r2 = r1.addedBubble
                if (r2 == 0) goto L_0x0333
                com.android.wm.shell.bubbles.BubbleController r3 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r7 = r3.mStackView
                if (r7 == 0) goto L_0x0333
                com.android.wm.shell.bubbles.BubbleDataRepository r7 = r3.mDataRepository
                int r3 = r3.mCurrentUserId
                java.util.Objects.requireNonNull(r7)
                java.util.List r2 = java.util.Collections.singletonList(r2)
                java.util.ArrayList r2 = com.android.p012wm.shell.bubbles.BubbleDataRepository.transform(r2)
                com.android.wm.shell.bubbles.storage.BubbleVolatileRepository r9 = r7.volatileRepository
                r9.addBubbles(r3, r2)
                boolean r2 = r2.isEmpty()
                r2 = r2 ^ r8
                if (r2 == 0) goto L_0x02b8
                kotlinx.coroutines.StandaloneCoroutine r2 = r7.job
                kotlinx.coroutines.internal.ContextScope r3 = r7.ioScope
                com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1 r9 = new com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1
                r9.<init>(r2, r7, r6)
                kotlinx.coroutines.StandaloneCoroutine r2 = kotlinx.coroutines.BuildersKt.launch$default(r3, r6, r6, r9, r4)
                r7.job = r2
            L_0x02b8:
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r2 = r2.mStackView
                com.android.wm.shell.bubbles.Bubble r3 = r1.addedBubble
                java.util.Objects.requireNonNull(r2)
                int r7 = r2.getBubbleCount()
                if (r7 != 0) goto L_0x02d8
                boolean r7 = r2.shouldShowStackEdu()
                if (r7 == 0) goto L_0x02d8
                com.android.wm.shell.bubbles.animation.StackAnimationController r7 = r2.mStackAnimationController
                com.android.wm.shell.bubbles.BubblePositioner r9 = r2.mPositioner
                android.graphics.PointF r9 = r9.getDefaultStartPosition()
                r7.setStackPosition(r9)
            L_0x02d8:
                java.util.Objects.requireNonNull(r3)
                com.android.wm.shell.bubbles.BadgedImageView r7 = r3.mIconView
                if (r7 != 0) goto L_0x02e0
                goto L_0x0333
            L_0x02e0:
                com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout r9 = r2.mBubbleContainer
                android.widget.FrameLayout$LayoutParams r10 = new android.widget.FrameLayout$LayoutParams
                com.android.wm.shell.bubbles.BubblePositioner r11 = r2.mPositioner
                java.util.Objects.requireNonNull(r11)
                int r11 = r11.mBubbleSize
                com.android.wm.shell.bubbles.BubblePositioner r12 = r2.mPositioner
                java.util.Objects.requireNonNull(r12)
                int r12 = r12.mBubbleSize
                r10.<init>(r11, r12)
                java.util.Objects.requireNonNull(r9)
                r9.addViewInternal(r7, r5, r10, r5)
                int r7 = r2.getBubbleCount()
                if (r7 != 0) goto L_0x0309
                com.android.wm.shell.bubbles.animation.StackAnimationController r7 = r2.mStackAnimationController
                boolean r7 = r7.isStackOnLeftSide()
                r2.mStackOnLeftOrWillBe = r7
            L_0x0309:
                com.android.wm.shell.bubbles.BadgedImageView r7 = r3.mIconView
                boolean r9 = r2.mStackOnLeftOrWillBe
                r9 = r9 ^ r8
                java.util.Objects.requireNonNull(r7)
                r7.mOnLeft = r9
                r7.invalidate()
                r7.showBadge()
                com.android.wm.shell.bubbles.BadgedImageView r7 = r3.mIconView
                com.android.wm.shell.bubbles.BubbleStackView$6 r9 = r2.mBubbleClickListener
                r7.setOnClickListener(r9)
                com.android.wm.shell.bubbles.BadgedImageView r7 = r3.mIconView
                com.android.wm.shell.bubbles.BubbleStackView$7 r9 = r2.mBubbleTouchListener
                r7.setOnTouchListener(r9)
                r2.updateBubbleShadows(r5)
                r2.animateInFlyoutForBubble(r3)
                r2.requestUpdate()
                r2.logBubbleEvent(r3, r8)
            L_0x0333:
                com.android.wm.shell.bubbles.Bubble r2 = r1.updatedBubble
                if (r2 == 0) goto L_0x0347
                com.android.wm.shell.bubbles.BubbleController r3 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r3 = r3.mStackView
                if (r3 == 0) goto L_0x0347
                r3.animateInFlyoutForBubble(r2)
                r3.requestUpdate()
                r7 = 2
                r3.logBubbleEvent(r2, r7)
            L_0x0347:
                com.android.wm.shell.bubbles.Bubble r2 = r1.suppressedBubble
                if (r2 == 0) goto L_0x0354
                com.android.wm.shell.bubbles.BubbleController r3 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r3 = r3.mStackView
                if (r3 == 0) goto L_0x0354
                r3.setBubbleSuppressed(r2, r8)
            L_0x0354:
                com.android.wm.shell.bubbles.Bubble r2 = r1.unsuppressedBubble
                if (r2 == 0) goto L_0x0361
                com.android.wm.shell.bubbles.BubbleController r3 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r3 = r3.mStackView
                if (r3 == 0) goto L_0x0361
                r3.setBubbleSuppressed(r2, r5)
            L_0x0361:
                boolean r2 = r1.orderChanged
                if (r2 == 0) goto L_0x0427
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r3 = r2.mStackView
                if (r3 == 0) goto L_0x0427
                com.android.wm.shell.bubbles.BubbleDataRepository r3 = r2.mDataRepository
                int r2 = r2.mCurrentUserId
                java.util.List<com.android.wm.shell.bubbles.Bubble> r7 = r1.bubbles
                java.util.Objects.requireNonNull(r3)
                java.util.ArrayList r7 = com.android.p012wm.shell.bubbles.BubbleDataRepository.transform(r7)
                com.android.wm.shell.bubbles.storage.BubbleVolatileRepository r9 = r3.volatileRepository
                r9.addBubbles(r2, r7)
                boolean r2 = r7.isEmpty()
                r2 = r2 ^ r8
                if (r2 == 0) goto L_0x0393
                kotlinx.coroutines.StandaloneCoroutine r2 = r3.job
                kotlinx.coroutines.internal.ContextScope r7 = r3.ioScope
                com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1 r9 = new com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1
                r9.<init>(r2, r3, r6)
                kotlinx.coroutines.StandaloneCoroutine r2 = kotlinx.coroutines.BuildersKt.launch$default(r7, r6, r6, r9, r4)
                r3.job = r2
            L_0x0393:
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r2 = r2.mStackView
                java.util.List<com.android.wm.shell.bubbles.Bubble> r3 = r1.bubbles
                java.util.Objects.requireNonNull(r2)
                com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda20 r7 = new com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda20
                r7.<init>(r2, r3, r5)
                boolean r9 = r2.mIsExpanded
                if (r9 != 0) goto L_0x041b
                boolean r9 = r2.mIsExpansionAnimating
                if (r9 == 0) goto L_0x03ab
                goto L_0x041b
            L_0x03ab:
                if (r9 != 0) goto L_0x0424
                java.util.stream.Stream r3 = r3.stream()
                com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda29 r9 = com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda29.INSTANCE
                java.util.stream.Stream r3 = r3.map(r9)
                java.util.stream.Collector r9 = java.util.stream.Collectors.toList()
                java.lang.Object r3 = r3.collect(r9)
                java.util.List r3 = (java.util.List) r3
                com.android.wm.shell.bubbles.animation.StackAnimationController r9 = r2.mStackAnimationController
                java.util.Objects.requireNonNull(r9)
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2 r10 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2
                r10.<init>(r9, r3, r4)
                r4 = r5
                r11 = r4
            L_0x03cd:
                int r12 = r3.size()
                if (r4 >= r12) goto L_0x0415
                java.lang.Object r12 = r3.get(r4)
                android.view.View r12 = (android.view.View) r12
                com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout r13 = r9.mLayout
                int r13 = r13.indexOfChild(r12)
                if (r4 != r13) goto L_0x03e6
                r9.moveToFinalIndex(r12, r4, r7)
                r12 = r5
                goto L_0x0411
            L_0x03e6:
                if (r4 != 0) goto L_0x040d
                android.view.ViewPropertyAnimator r13 = r12.animate()
                android.graphics.PointF r14 = r9.mStackPosition
                float r14 = r14.y
                float r15 = r9.mSwapAnimationOffset
                float r14 = r14 - r15
                android.view.ViewPropertyAnimator r13 = r13.translationY(r14)
                r14 = 300(0x12c, double:1.48E-321)
                android.view.ViewPropertyAnimator r13 = r13.setDuration(r14)
                com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda3 r14 = new com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda3
                r14.<init>(r9, r10, r12, r7)
                android.view.ViewPropertyAnimator r13 = r13.withEndAction(r14)
                r14 = 2131428691(0x7f0b0553, float:1.8479034E38)
                r12.setTag(r14, r13)
                goto L_0x0410
            L_0x040d:
                r9.moveToFinalIndex(r12, r4, r7)
            L_0x0410:
                r12 = r8
            L_0x0411:
                r11 = r11 | r12
                int r4 = r4 + 1
                goto L_0x03cd
            L_0x0415:
                if (r11 != 0) goto L_0x0424
                r10.run()
                goto L_0x0424
            L_0x041b:
                r7.run()
                r2.updateBadges(r5)
                r2.updateZOrder()
            L_0x0424:
                r2.updatePointerPosition(r5)
            L_0x0427:
                boolean r2 = r1.selectionChanged
                if (r2 == 0) goto L_0x0452
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r2 = r2.mStackView
                if (r2 == 0) goto L_0x0452
                com.android.wm.shell.bubbles.BubbleViewProvider r3 = r1.selectedBubble
                r2.setSelectedBubble(r3)
                com.android.wm.shell.bubbles.BubbleViewProvider r2 = r1.selectedBubble
                if (r2 == 0) goto L_0x0452
                com.android.wm.shell.bubbles.BubbleController r3 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r3 = r3.mSysuiProxy
                java.lang.String r2 = r2.getKey()
                com.android.systemui.wmshell.BubblesManager$5 r3 = (com.android.systemui.wmshell.BubblesManager.C17525) r3
                java.util.Objects.requireNonNull(r3)
                java.util.concurrent.Executor r4 = r5
                com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda0 r7 = new com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda0
                r9 = 2
                r7.<init>(r3, r2, r9)
                r4.execute(r7)
            L_0x0452:
                boolean r2 = r1.expandedChanged
                if (r2 == 0) goto L_0x0476
                boolean r2 = r1.expanded
                if (r2 == 0) goto L_0x0476
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r2 = r2.mStackView
                if (r2 == 0) goto L_0x0476
                r2.setExpanded(r8)
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r2 = r2.mSysuiProxy
                com.android.systemui.wmshell.BubblesManager$5 r2 = (com.android.systemui.wmshell.BubblesManager.C17525) r2
                java.util.Objects.requireNonNull(r2)
                java.util.concurrent.Executor r3 = r5
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda8 r4 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda8
                r4.<init>(r2, r8)
                r3.execute(r4)
            L_0x0476:
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.Bubbles$SysuiProxy r2 = r2.mSysuiProxy
                java.lang.String r3 = "BubbleData.Listener.applyUpdate"
                com.android.systemui.wmshell.BubblesManager$5 r2 = (com.android.systemui.wmshell.BubblesManager.C17525) r2
                java.util.Objects.requireNonNull(r2)
                java.util.concurrent.Executor r4 = r5
                com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3 r7 = new com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3
                r7.<init>(r2, r3, r5)
                r4.execute(r7)
                com.android.wm.shell.bubbles.BubbleController r2 = com.android.p012wm.shell.bubbles.BubbleController.this
                r2.updateStack()
                com.android.wm.shell.bubbles.BubbleController r0 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleController$BubblesImpl r0 = r0.mImpl
                com.android.wm.shell.bubbles.BubbleController$BubblesImpl$CachedState r2 = r0.mCachedState
                java.util.Objects.requireNonNull(r2)
                monitor-enter(r2)
                boolean r0 = r1.selectionChanged     // Catch:{ all -> 0x0537 }
                if (r0 == 0) goto L_0x04a8
                com.android.wm.shell.bubbles.BubbleViewProvider r0 = r1.selectedBubble     // Catch:{ all -> 0x0537 }
                if (r0 == 0) goto L_0x04a6
                java.lang.String r6 = r0.getKey()     // Catch:{ all -> 0x0537 }
            L_0x04a6:
                r2.mSelectedBubbleKey = r6     // Catch:{ all -> 0x0537 }
            L_0x04a8:
                boolean r0 = r1.expandedChanged     // Catch:{ all -> 0x0537 }
                if (r0 == 0) goto L_0x04b0
                boolean r0 = r1.expanded     // Catch:{ all -> 0x0537 }
                r2.mIsStackExpanded = r0     // Catch:{ all -> 0x0537 }
            L_0x04b0:
                boolean r0 = r1.suppressedSummaryChanged     // Catch:{ all -> 0x0537 }
                if (r0 == 0) goto L_0x04d8
                com.android.wm.shell.bubbles.BubbleController$BubblesImpl r0 = com.android.p012wm.shell.bubbles.BubbleController.BubblesImpl.this     // Catch:{ all -> 0x0537 }
                com.android.wm.shell.bubbles.BubbleController r0 = com.android.p012wm.shell.bubbles.BubbleController.this     // Catch:{ all -> 0x0537 }
                com.android.wm.shell.bubbles.BubbleData r0 = r0.mBubbleData     // Catch:{ all -> 0x0537 }
                java.lang.String r3 = r1.suppressedSummaryGroup     // Catch:{ all -> 0x0537 }
                java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0537 }
                java.util.HashMap<java.lang.String, java.lang.String> r0 = r0.mSuppressedGroupKeys     // Catch:{ all -> 0x0537 }
                java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x0537 }
                java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0537 }
                if (r0 == 0) goto L_0x04d1
                java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.mSuppressedGroupToNotifKeys     // Catch:{ all -> 0x0537 }
                java.lang.String r4 = r1.suppressedSummaryGroup     // Catch:{ all -> 0x0537 }
                r3.put(r4, r0)     // Catch:{ all -> 0x0537 }
                goto L_0x04d8
            L_0x04d1:
                java.util.HashMap<java.lang.String, java.lang.String> r0 = r2.mSuppressedGroupToNotifKeys     // Catch:{ all -> 0x0537 }
                java.lang.String r3 = r1.suppressedSummaryGroup     // Catch:{ all -> 0x0537 }
                r0.remove(r3)     // Catch:{ all -> 0x0537 }
            L_0x04d8:
                java.util.ArrayList<com.android.wm.shell.bubbles.Bubble> r0 = r2.mTmpBubbles     // Catch:{ all -> 0x0537 }
                r0.clear()     // Catch:{ all -> 0x0537 }
                java.util.ArrayList<com.android.wm.shell.bubbles.Bubble> r0 = r2.mTmpBubbles     // Catch:{ all -> 0x0537 }
                java.util.List<com.android.wm.shell.bubbles.Bubble> r3 = r1.bubbles     // Catch:{ all -> 0x0537 }
                r0.addAll(r3)     // Catch:{ all -> 0x0537 }
                java.util.ArrayList<com.android.wm.shell.bubbles.Bubble> r0 = r2.mTmpBubbles     // Catch:{ all -> 0x0537 }
                java.util.List<com.android.wm.shell.bubbles.Bubble> r1 = r1.overflowBubbles     // Catch:{ all -> 0x0537 }
                r0.addAll(r1)     // Catch:{ all -> 0x0537 }
                java.util.HashSet<java.lang.String> r0 = r2.mSuppressedBubbleKeys     // Catch:{ all -> 0x0537 }
                r0.clear()     // Catch:{ all -> 0x0537 }
                java.util.HashMap<java.lang.String, com.android.wm.shell.bubbles.Bubble> r0 = r2.mShortcutIdToBubble     // Catch:{ all -> 0x0537 }
                r0.clear()     // Catch:{ all -> 0x0537 }
                java.util.ArrayList<com.android.wm.shell.bubbles.Bubble> r0 = r2.mTmpBubbles     // Catch:{ all -> 0x0537 }
                java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0537 }
            L_0x04fb:
                boolean r1 = r0.hasNext()     // Catch:{ all -> 0x0537 }
                if (r1 == 0) goto L_0x0535
                java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x0537 }
                com.android.wm.shell.bubbles.Bubble r1 = (com.android.p012wm.shell.bubbles.Bubble) r1     // Catch:{ all -> 0x0537 }
                java.util.HashMap<java.lang.String, com.android.wm.shell.bubbles.Bubble> r3 = r2.mShortcutIdToBubble     // Catch:{ all -> 0x0537 }
                java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x0537 }
                android.content.pm.ShortcutInfo r4 = r1.mShortcutInfo     // Catch:{ all -> 0x0537 }
                if (r4 == 0) goto L_0x0515
                java.lang.String r4 = r4.getId()     // Catch:{ all -> 0x0537 }
                goto L_0x0517
            L_0x0515:
                java.lang.String r4 = r1.mMetadataShortcutId     // Catch:{ all -> 0x0537 }
            L_0x0517:
                r3.put(r4, r1)     // Catch:{ all -> 0x0537 }
                monitor-enter(r2)     // Catch:{ all -> 0x0537 }
                boolean r3 = r1.showInShade()     // Catch:{ all -> 0x0532 }
                if (r3 != 0) goto L_0x0529
                java.util.HashSet<java.lang.String> r3 = r2.mSuppressedBubbleKeys     // Catch:{ all -> 0x0532 }
                java.lang.String r1 = r1.mKey     // Catch:{ all -> 0x0532 }
                r3.add(r1)     // Catch:{ all -> 0x0532 }
                goto L_0x0530
            L_0x0529:
                java.util.HashSet<java.lang.String> r3 = r2.mSuppressedBubbleKeys     // Catch:{ all -> 0x0532 }
                java.lang.String r1 = r1.mKey     // Catch:{ all -> 0x0532 }
                r3.remove(r1)     // Catch:{ all -> 0x0532 }
            L_0x0530:
                monitor-exit(r2)     // Catch:{ all -> 0x0537 }
                goto L_0x04fb
            L_0x0532:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0537 }
                throw r0     // Catch:{ all -> 0x0537 }
            L_0x0535:
                monitor-exit(r2)
                return
            L_0x0537:
                r0 = move-exception
                monitor-exit(r2)
                throw r0
            L_0x053a:
                r0 = move-exception
                monitor-exit(r10)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleController.C17955.applyUpdate(com.android.wm.shell.bubbles.BubbleData$Update):void");
        }
    };
    public BubbleIconFactory mBubbleIconFactory;
    public BubblePositioner mBubblePositioner;
    public final Context mContext;
    public SparseArray<UserInfo> mCurrentProfiles;
    public int mCurrentUserId;
    public final BubbleDataRepository mDataRepository;
    public int mDensityDpi = 0;
    public final DisplayController mDisplayController;
    public BubbleController$$ExternalSyntheticLambda4 mExpandListener;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public float mFontScale = 0.0f;
    public final BubblesImpl mImpl = new BubblesImpl();
    public boolean mInflateSynchronously;
    public boolean mIsStatusBarShade = true;
    public final LauncherApps mLauncherApps;
    public int mLayoutDirection = -1;
    public BubbleLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public BubbleEntry mNotifEntryToExpandOnShadeUnlock;
    public Optional<OneHandedController> mOneHandedOptional;
    public boolean mOverflowDataLoadNeeded = true;
    public BubbleData.Listener mOverflowListener = null;
    public final SparseSetArray<String> mSavedBubbleKeysPerUser;
    public Rect mScreenBounds = new Rect();
    public BubbleStackView mStackView;
    public BubbleStackView.SurfaceSynchronizer mSurfaceSynchronizer;
    public final SyncTransactionQueue mSyncQueue;
    public Bubbles.SysuiProxy mSysuiProxy;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskStackListenerImpl mTaskStackListener;
    public final TaskViewTransitions mTaskViewTransitions;
    public NotificationListenerService.Ranking mTmpRanking;
    public WindowInsets mWindowInsets;
    public final WindowManager mWindowManager;
    public final WindowManagerShellWrapper mWindowManagerShellWrapper;
    public WindowManager.LayoutParams mWmLayoutParams;

    /* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImeListener */
    public class BubblesImeListener extends PinnedStackListenerForwarder.PinnedTaskListener {
        public BubblesImeListener() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:27:0x0066  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x0083  */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x0099  */
        /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onImeVisibilityChanged(boolean r9, int r10) {
            /*
                r8 = this;
                com.android.wm.shell.bubbles.BubbleController r0 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubblePositioner r0 = r0.mBubblePositioner
                java.util.Objects.requireNonNull(r0)
                r0.mImeVisible = r9
                r0.mImeHeight = r10
                com.android.wm.shell.bubbles.BubbleController r8 = com.android.p012wm.shell.bubbles.BubbleController.this
                com.android.wm.shell.bubbles.BubbleStackView r8 = r8.mStackView
                if (r8 == 0) goto L_0x011d
                boolean r10 = r8.mIsExpansionAnimating
                if (r10 != 0) goto L_0x0019
                boolean r10 = r8.mIsBubbleSwitchAnimating
                if (r10 == 0) goto L_0x002b
            L_0x0019:
                boolean r10 = r8.mIsExpanded
                if (r10 == 0) goto L_0x002b
                com.android.wm.shell.bubbles.animation.ExpandedAnimationController r9 = r8.mExpandedAnimationController
                com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2 r10 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2
                r0 = 8
                r10.<init>(r8, r0)
                r9.expandFromStack(r10)
                goto L_0x011d
            L_0x002b:
                boolean r10 = r8.mIsExpanded
                r0 = 0
                if (r10 != 0) goto L_0x00b4
                int r10 = r8.getBubbleCount()
                if (r10 <= 0) goto L_0x00b4
                com.android.wm.shell.bubbles.animation.StackAnimationController r10 = r8.mStackAnimationController
                java.util.Objects.requireNonNull(r10)
                android.graphics.RectF r1 = r10.getAllowableStackPositionRegion()
                float r1 = r1.bottom
                r2 = -2147483647(0xffffffff80000001, float:-1.4E-45)
                if (r9 == 0) goto L_0x0057
                android.graphics.PointF r9 = r10.mStackPosition
                float r9 = r9.y
                int r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
                if (r3 <= 0) goto L_0x0061
                float r3 = r10.mPreImeY
                int r3 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
                if (r3 != 0) goto L_0x0061
                r10.mPreImeY = r9
                goto L_0x005f
            L_0x0057:
                float r1 = r10.mPreImeY
                int r9 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r9 == 0) goto L_0x0061
                r10.mPreImeY = r2
            L_0x005f:
                r9 = r1
                goto L_0x0062
            L_0x0061:
                r9 = r2
            L_0x0062:
                int r7 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
                if (r7 == 0) goto L_0x0080
                androidx.dynamicanimation.animation.DynamicAnimation$2 r2 = androidx.dynamicanimation.animation.DynamicAnimation.TRANSLATION_Y
                androidx.dynamicanimation.animation.SpringForce r3 = r10.getSpringForce()
                r1 = 1128792064(0x43480000, float:200.0)
                r3.setStiffness(r1)
                r4 = 0
                java.lang.Runnable[] r6 = new java.lang.Runnable[r0]
                r1 = r10
                r5 = r9
                r1.springFirstBubbleWithStackFollowing(r2, r3, r4, r5, r6)
                android.graphics.PointF r0 = r10.mStackPosition
                float r0 = r0.x
                r10.notifyFloatingCoordinatorStackAnimatingTo(r0, r9)
            L_0x0080:
                if (r7 == 0) goto L_0x0083
                goto L_0x0087
            L_0x0083:
                android.graphics.PointF r9 = r10.mStackPosition
                float r9 = r9.y
            L_0x0087:
                com.android.wm.shell.bubbles.animation.StackAnimationController r10 = r8.mStackAnimationController
                java.util.Objects.requireNonNull(r10)
                android.graphics.PointF r10 = r10.mStackPosition
                float r10 = r10.y
                float r9 = r9 - r10
                com.android.wm.shell.bubbles.BubbleFlyoutView r10 = r8.mFlyout
                int r10 = r10.getVisibility()
                if (r10 != 0) goto L_0x011d
                com.android.wm.shell.bubbles.BubbleFlyoutView r10 = r8.mFlyout
                kotlin.jvm.functions.Function1<java.lang.Object, ? extends com.android.wm.shell.animation.PhysicsAnimator<?>> r0 = com.android.p012wm.shell.animation.PhysicsAnimator.instanceConstructor
                com.android.wm.shell.animation.PhysicsAnimator r10 = com.android.p012wm.shell.animation.PhysicsAnimator.Companion.getInstance(r10)
                androidx.dynamicanimation.animation.DynamicAnimation$2 r0 = androidx.dynamicanimation.animation.DynamicAnimation.TRANSLATION_Y
                com.android.wm.shell.bubbles.BubbleFlyoutView r8 = r8.mFlyout
                float r8 = r8.getTranslationY()
                float r8 = r8 + r9
                com.android.wm.shell.animation.PhysicsAnimator$SpringConfig r9 = com.android.p012wm.shell.bubbles.BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG
                r1 = 0
                r10.spring(r0, r8, r1, r9)
                r10.start()
                goto L_0x011d
            L_0x00b4:
                com.android.wm.shell.bubbles.BubblePositioner r10 = r8.mPositioner
                boolean r10 = r10.showBubblesVertically()
                if (r10 == 0) goto L_0x011d
                boolean r10 = r8.mIsExpanded
                if (r10 == 0) goto L_0x011d
                com.android.wm.shell.bubbles.BubbleViewProvider r10 = r8.mExpandedBubble
                if (r10 == 0) goto L_0x011d
                com.android.wm.shell.bubbles.BubbleExpandedView r10 = r10.getExpandedView()
                if (r10 == 0) goto L_0x011d
                com.android.wm.shell.bubbles.BubbleViewProvider r10 = r8.mExpandedBubble
                com.android.wm.shell.bubbles.BubbleExpandedView r10 = r10.getExpandedView()
                java.util.Objects.requireNonNull(r10)
                r10.mImeVisible = r9
                if (r9 != 0) goto L_0x00de
                boolean r9 = r10.mNeedsNewHeight
                if (r9 == 0) goto L_0x00de
                r10.updateHeight()
            L_0x00de:
                java.util.ArrayList r9 = new java.util.ArrayList
                r9.<init>()
                r10 = r0
            L_0x00e4:
                com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout r1 = r8.mBubbleContainer
                int r1 = r1.getChildCount()
                r2 = 1
                if (r10 >= r1) goto L_0x010f
                com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout r1 = r8.mBubbleContainer
                android.view.View r1 = r1.getChildAt(r10)
                com.android.wm.shell.bubbles.BubblePositioner r3 = r8.mPositioner
                com.android.wm.shell.bubbles.BubbleStackView$StackViewState r4 = r8.getState()
                android.graphics.PointF r3 = r3.getExpandedBubbleXY(r10, r4)
                float r3 = r3.y
                android.util.Property r4 = android.widget.FrameLayout.TRANSLATION_Y
                float[] r2 = new float[r2]
                r2[r0] = r3
                android.animation.ObjectAnimator r1 = android.animation.ObjectAnimator.ofFloat(r1, r4, r2)
                r9.add(r1)
                int r10 = r10 + 1
                goto L_0x00e4
            L_0x010f:
                r8.updatePointerPosition(r2)
                android.animation.AnimatorSet r8 = new android.animation.AnimatorSet
                r8.<init>()
                r8.playTogether(r9)
                r8.start()
            L_0x011d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleController.BubblesImeListener.onImeVisibilityChanged(boolean, int):void");
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl */
    public class BubblesImpl implements Bubbles {
        public CachedState mCachedState = new CachedState();

        @VisibleForTesting
        /* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$CachedState */
        public class CachedState {
            public boolean mIsStackExpanded;
            public String mSelectedBubbleKey;
            public HashMap<String, Bubble> mShortcutIdToBubble = new HashMap<>();
            public HashSet<String> mSuppressedBubbleKeys = new HashSet<>();
            public HashMap<String, String> mSuppressedGroupToNotifKeys = new HashMap<>();
            public ArrayList<Bubble> mTmpBubbles = new ArrayList<>();

            public CachedState() {
            }
        }

        public final void expandStackAndSelectBubble(BubbleEntry bubbleEntry) {
            BubbleController.this.mMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda3(this, bubbleEntry, 1));
        }

        public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            if (i == 2 || i == 3) {
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda4(this, str, userHandle, notificationChannel, i));
            }
        }

        public BubblesImpl() {
        }

        public final void collapseStack() {
            BubbleController.this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(this, 8));
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            try {
                BubbleController.this.mMainExecutor.executeBlocking$1(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(this, fileDescriptor, printWriter, strArr));
            } catch (InterruptedException unused) {
                Slog.e("Bubbles", "Failed to dump BubbleController in 2s");
            }
        }

        public final void expandStackAndSelectBubble(Bubble bubble) {
            BubbleController.this.mMainExecutor.execute(new TaskView$$ExternalSyntheticLambda7(this, bubble, 4));
        }

        public final Bubble getBubbleWithShortcutId(String str) {
            Bubble bubble;
            CachedState cachedState = this.mCachedState;
            Objects.requireNonNull(cachedState);
            synchronized (cachedState) {
                bubble = cachedState.mShortcutIdToBubble.get(str);
            }
            return bubble;
        }

        public final boolean handleDismissalInterception(BubbleEntry bubbleEntry, ArrayList arrayList, BubblesManager$$ExternalSyntheticLambda1 bubblesManager$$ExternalSyntheticLambda1, Executor executor) {
            return ((Boolean) BubbleController.this.mMainExecutor.executeBlockingForResult(new BubbleController$BubblesImpl$$ExternalSyntheticLambda11(this, bubbleEntry, arrayList, new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(executor, bubblesManager$$ExternalSyntheticLambda1)))).booleanValue();
        }

        public final boolean isBubbleExpanded(String str) {
            boolean z;
            CachedState cachedState = this.mCachedState;
            Objects.requireNonNull(cachedState);
            synchronized (cachedState) {
                if (!cachedState.mIsStackExpanded || !str.equals(cachedState.mSelectedBubbleKey)) {
                    z = false;
                } else {
                    z = true;
                }
            }
            return z;
        }

        public final boolean isBubbleNotificationSuppressedFromShade(String str, String str2) {
            boolean z;
            CachedState cachedState = this.mCachedState;
            Objects.requireNonNull(cachedState);
            synchronized (cachedState) {
                if (cachedState.mSuppressedBubbleKeys.contains(str) || (cachedState.mSuppressedGroupToNotifKeys.containsKey(str2) && str.equals(cachedState.mSuppressedGroupToNotifKeys.get(str2)))) {
                    z = true;
                } else {
                    z = false;
                }
            }
            return z;
        }

        public final boolean isStackExpanded() {
            boolean z;
            CachedState cachedState = this.mCachedState;
            Objects.requireNonNull(cachedState);
            synchronized (cachedState) {
                z = cachedState.mIsStackExpanded;
            }
            return z;
        }

        public final void onConfigChanged(Configuration configuration) {
            BubbleController.this.mMainExecutor.execute(new ScrimView$$ExternalSyntheticLambda1(this, configuration, 4));
        }

        public final void onCurrentProfilesChanged(SparseArray<UserInfo> sparseArray) {
            BubbleController.this.mMainExecutor.execute(new ShellExecutor$$ExternalSyntheticLambda0(this, sparseArray, 4));
        }

        public final void onEntryAdded(BubbleEntry bubbleEntry) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda0(this, bubbleEntry, 0));
        }

        public final void onEntryRemoved(BubbleEntry bubbleEntry) {
            BubbleController.this.mMainExecutor.execute(new KeyguardViewMediator$9$$ExternalSyntheticLambda0(this, bubbleEntry, 2));
        }

        public final void onEntryUpdated(BubbleEntry bubbleEntry, boolean z) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda3(this, bubbleEntry, z));
        }

        public final void onRankingUpdated(NotificationListenerService.RankingMap rankingMap, HashMap<String, Pair<BubbleEntry, Boolean>> hashMap) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda1(this, rankingMap, hashMap, 0));
        }

        public final void onStatusBarStateChanged(boolean z) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(this, z));
        }

        public final void onStatusBarVisibilityChanged(boolean z) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda7(this, z));
        }

        public final void onUserChanged(int i) {
            BubbleController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda1(this, i, 1));
        }

        public final void onZenStateChanged() {
            BubbleController.this.mMainExecutor.execute(new CreateUserActivity$$ExternalSyntheticLambda1(this, 7));
        }

        public final void removeSuppressedSummaryIfNecessary(String str, BubblesManager$8$$ExternalSyntheticLambda0 bubblesManager$8$$ExternalSyntheticLambda0, Executor executor) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(this, bubblesManager$8$$ExternalSyntheticLambda0, executor, str));
        }

        public final void setExpandListener(StatusBar$$ExternalSyntheticLambda2 statusBar$$ExternalSyntheticLambda2) {
            BubbleController.this.mMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda2(this, statusBar$$ExternalSyntheticLambda2, 2));
        }

        public final void setSysuiProxy(BubblesManager.C17525 r4) {
            BubbleController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, r4, 2));
        }

        public final void updateForThemeChanges() {
            BubbleController.this.mMainExecutor.execute(new ScrimView$$ExternalSyntheticLambda0(this, 4));
        }
    }

    @VisibleForTesting
    public BubbleController(Context context, BubbleData bubbleData, BubbleStackView.SurfaceSynchronizer surfaceSynchronizer, FloatingContentCoordinator floatingContentCoordinator, BubbleDataRepository bubbleDataRepository, IStatusBarService iStatusBarService, WindowManager windowManager, WindowManagerShellWrapper windowManagerShellWrapper, LauncherApps launcherApps, BubbleLogger bubbleLogger, TaskStackListenerImpl taskStackListenerImpl, ShellTaskOrganizer shellTaskOrganizer, BubblePositioner bubblePositioner, DisplayController displayController, Optional<OneHandedController> optional, ShellExecutor shellExecutor, Handler handler, TaskViewTransitions taskViewTransitions, SyncTransactionQueue syncTransactionQueue) {
        IStatusBarService iStatusBarService2;
        this.mContext = context;
        this.mLauncherApps = launcherApps;
        if (iStatusBarService == null) {
            iStatusBarService2 = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        } else {
            iStatusBarService2 = iStatusBarService;
        }
        this.mBarService = iStatusBarService2;
        this.mWindowManager = windowManager;
        this.mWindowManagerShellWrapper = windowManagerShellWrapper;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        this.mDataRepository = bubbleDataRepository;
        this.mLogger = bubbleLogger;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mSurfaceSynchronizer = surfaceSynchronizer;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mBubblePositioner = bubblePositioner;
        this.mBubbleData = bubbleData;
        this.mSavedBubbleKeysPerUser = new SparseSetArray<>();
        this.mBubbleIconFactory = new BubbleIconFactory(context);
        this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(context);
        this.mDisplayController = displayController;
        this.mTaskViewTransitions = taskViewTransitions;
        this.mOneHandedOptional = optional;
        this.mSyncQueue = syncTransactionQueue;
    }

    @VisibleForTesting
    public void updateBubble(BubbleEntry bubbleEntry) {
        updateBubble(bubbleEntry, false, true);
    }

    public static PackageManager getPackageManagerForUser(Context context, int i) {
        if (i >= 0) {
            try {
                context = context.createPackageContextAsUser(context.getPackageName(), 4, new UserHandle(i));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return context.getPackageManager();
    }

    @VisibleForTesting
    public void collapseStack() {
        this.mBubbleData.setExpanded(false);
    }

    public final void ensureStackViewCreated() {
        if (this.mStackView == null) {
            BubbleStackView bubbleStackView = new BubbleStackView(this.mContext, this, this.mBubbleData, this.mSurfaceSynchronizer, this.mFloatingContentCoordinator, this.mMainExecutor);
            this.mStackView = bubbleStackView;
            bubbleStackView.mRelativeStackPositionBeforeRotation = new BubbleStackView.RelativeStackPosition(bubbleStackView.mPositioner.getRestingPosition(), bubbleStackView.mStackAnimationController.getAllowableStackPositionRegion());
            bubbleStackView.addOnLayoutChangeListener(bubbleStackView.mOrientationChangedListener);
            bubbleStackView.hideFlyoutImmediate();
            BubbleController$$ExternalSyntheticLambda4 bubbleController$$ExternalSyntheticLambda4 = this.mExpandListener;
            if (bubbleController$$ExternalSyntheticLambda4 != null) {
                BubbleStackView bubbleStackView2 = this.mStackView;
                Objects.requireNonNull(bubbleStackView2);
                bubbleStackView2.mExpandListener = bubbleController$$ExternalSyntheticLambda4;
            }
            BubbleStackView bubbleStackView3 = this.mStackView;
            Bubbles.SysuiProxy sysuiProxy = this.mSysuiProxy;
            Objects.requireNonNull(sysuiProxy);
            BubbleController$$ExternalSyntheticLambda10 bubbleController$$ExternalSyntheticLambda10 = new BubbleController$$ExternalSyntheticLambda10(sysuiProxy, 0);
            Objects.requireNonNull(bubbleStackView3);
            bubbleStackView3.mUnbubbleConversationCallback = bubbleController$$ExternalSyntheticLambda10;
        }
        if (this.mStackView != null && !this.mAddedToWindowManager) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 16777256, -3);
            this.mWmLayoutParams = layoutParams;
            layoutParams.setTrustedOverlay();
            this.mWmLayoutParams.setFitInsetsTypes(0);
            WindowManager.LayoutParams layoutParams2 = this.mWmLayoutParams;
            layoutParams2.softInputMode = 16;
            layoutParams2.token = new Binder();
            this.mWmLayoutParams.setTitle("Bubbles!");
            this.mWmLayoutParams.packageName = this.mContext.getPackageName();
            WindowManager.LayoutParams layoutParams3 = this.mWmLayoutParams;
            layoutParams3.layoutInDisplayCutoutMode = 3;
            layoutParams3.privateFlags = 16 | layoutParams3.privateFlags;
            try {
                this.mAddedToWindowManager = true;
                BubbleData bubbleData = this.mBubbleData;
                Objects.requireNonNull(bubbleData);
                BubbleOverflow bubbleOverflow = bubbleData.mOverflow;
                Objects.requireNonNull(bubbleOverflow);
                View inflate = bubbleOverflow.inflater.inflate(C1777R.layout.bubble_expanded_view, (ViewGroup) null, false);
                Objects.requireNonNull(inflate, "null cannot be cast to non-null type com.android.wm.shell.bubbles.BubbleExpandedView");
                BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) inflate;
                bubbleOverflow.expandedView = bubbleExpandedView;
                bubbleExpandedView.applyThemeAttrs();
                bubbleOverflow.updateResources();
                BubbleExpandedView bubbleExpandedView2 = bubbleOverflow.expandedView;
                if (bubbleExpandedView2 != null) {
                    bubbleExpandedView2.initialize(this, getStackView(), true);
                }
                this.mWindowManager.addView(this.mStackView, this.mWmLayoutParams);
                this.mStackView.setOnApplyWindowInsetsListener(new BubbleController$$ExternalSyntheticLambda0(this, 0));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public final void expandStackAndSelectBubble(BubbleEntry bubbleEntry) {
        if (this.mIsStatusBarShade) {
            this.mNotifEntryToExpandOnShadeUnlock = null;
            String key = bubbleEntry.getKey();
            Bubble bubbleInStackWithKey = this.mBubbleData.getBubbleInStackWithKey(key);
            if (bubbleInStackWithKey != null) {
                this.mBubbleData.setSelectedBubble(bubbleInStackWithKey);
                this.mBubbleData.setExpanded(true);
                return;
            }
            Bubble overflowBubbleWithKey = this.mBubbleData.getOverflowBubbleWithKey(key);
            if (overflowBubbleWithKey != null) {
                promoteBubbleFromOverflow(overflowBubbleWithKey);
            } else if (bubbleEntry.mRanking.canBubble()) {
                bubbleEntry.setFlagBubble(true);
                try {
                    this.mBarService.onNotificationBubbleChanged(bubbleEntry.getKey(), true, 3);
                } catch (RemoteException unused) {
                }
            }
        } else {
            this.mNotifEntryToExpandOnShadeUnlock = bubbleEntry;
        }
    }

    public final ArrayList<Bubble> getBubblesInGroup(String str) {
        ArrayList<Bubble> arrayList = new ArrayList<>();
        if (str == null) {
            return arrayList;
        }
        BubbleData bubbleData = this.mBubbleData;
        Objects.requireNonNull(bubbleData);
        for (Bubble bubble : Collections.unmodifiableList(bubbleData.mBubbles)) {
            Objects.requireNonNull(bubble);
            String str2 = bubble.mGroupKey;
            if (str2 != null && str.equals(str2)) {
                arrayList.add(bubble);
            }
        }
        return arrayList;
    }

    @VisibleForTesting
    public BubblesImpl.CachedState getImplCachedState() {
        return this.mImpl.mCachedState;
    }

    @VisibleForTesting
    public boolean hasBubbles() {
        if (this.mStackView == null) {
            return false;
        }
        BubbleData bubbleData = this.mBubbleData;
        Objects.requireNonNull(bubbleData);
        if ((!bubbleData.mBubbles.isEmpty()) || this.mBubbleData.isShowingOverflow()) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean isBubbleNotificationSuppressedFromShade(String str, String str2) {
        boolean z;
        if (!this.mBubbleData.hasAnyBubbleWithKey(str) || this.mBubbleData.getAnyBubbleWithkey(str).showInShade()) {
            z = false;
        } else {
            z = true;
        }
        boolean isSummarySuppressed = this.mBubbleData.isSummarySuppressed(str2);
        BubbleData bubbleData = this.mBubbleData;
        Objects.requireNonNull(bubbleData);
        if ((!str.equals(bubbleData.mSuppressedGroupKeys.get(str2)) || !isSummarySuppressed) && !z) {
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public boolean isStackExpanded() {
        BubbleData bubbleData = this.mBubbleData;
        Objects.requireNonNull(bubbleData);
        return bubbleData.mExpanded;
    }

    @VisibleForTesting
    public void onBubbleNotificationSuppressionChanged(Bubble bubble) {
        boolean z;
        try {
            IStatusBarService iStatusBarService = this.mBarService;
            Objects.requireNonNull(bubble);
            String str = bubble.mKey;
            boolean z2 = true;
            if (!bubble.showInShade()) {
                z = true;
            } else {
                z = false;
            }
            if ((bubble.mFlags & 8) == 0) {
                z2 = false;
            }
            iStatusBarService.onBubbleNotificationSuppressionChanged(str, z, z2);
        } catch (RemoteException unused) {
        }
        BubblesImpl.CachedState cachedState = this.mImpl.mCachedState;
        Objects.requireNonNull(cachedState);
        synchronized (cachedState) {
            if (!bubble.showInShade()) {
                cachedState.mSuppressedBubbleKeys.add(bubble.mKey);
            } else {
                cachedState.mSuppressedBubbleKeys.remove(bubble.mKey);
            }
        }
    }

    public final void onEntryUpdated(BubbleEntry bubbleEntry, boolean z) {
        boolean z2;
        if (!z || !canLaunchInTaskView(this.mContext, bubbleEntry)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2 && this.mBubbleData.hasAnyBubbleWithKey(bubbleEntry.getKey())) {
            removeBubble(bubbleEntry.getKey(), 7);
        } else if (z2 && bubbleEntry.isBubble()) {
            updateBubble(bubbleEntry);
        }
    }

    @VisibleForTesting
    public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        String str2;
        ArrayList arrayList = new ArrayList(this.mBubbleData.getOverflowBubbles());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Bubble bubble = (Bubble) arrayList.get(i2);
            Objects.requireNonNull(bubble);
            ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
            if (shortcutInfo != null) {
                str2 = shortcutInfo.getId();
            } else {
                str2 = bubble.mMetadataShortcutId;
            }
            if (Objects.equals(str2, notificationChannel.getConversationId()) && bubble.mPackageName.equals(str) && bubble.mUser.getIdentifier() == userHandle.getIdentifier() && (!notificationChannel.canBubble() || notificationChannel.isDeleted())) {
                this.mBubbleData.dismissBubbleWithKey(bubble.mKey, 7);
            }
        }
    }

    @VisibleForTesting
    public void onUserChanged(int i) {
        int i2 = this.mCurrentUserId;
        this.mSavedBubbleKeysPerUser.remove(i2);
        for (Bubble next : this.mBubbleData.getBubbles()) {
            SparseSetArray<String> sparseSetArray = this.mSavedBubbleKeysPerUser;
            Objects.requireNonNull(next);
            sparseSetArray.add(i2, next.mKey);
        }
        this.mCurrentUserId = i;
        this.mBubbleData.dismissAll(8);
        BubbleData bubbleData = this.mBubbleData;
        Objects.requireNonNull(bubbleData);
        while (!bubbleData.mOverflowBubbles.isEmpty()) {
            Bubble bubble = (Bubble) bubbleData.mOverflowBubbles.get(0);
            Objects.requireNonNull(bubble);
            bubbleData.doRemove(bubble.mKey, 8);
        }
        bubbleData.dispatchPendingChanges();
        this.mOverflowDataLoadNeeded = true;
        ArraySet arraySet = this.mSavedBubbleKeysPerUser.get(i);
        if (arraySet != null) {
            Bubbles.SysuiProxy sysuiProxy = this.mSysuiProxy;
            BubbleController$$ExternalSyntheticLambda9 bubbleController$$ExternalSyntheticLambda9 = new BubbleController$$ExternalSyntheticLambda9(this, 0);
            BubblesManager.C17525 r1 = (BubblesManager.C17525) sysuiProxy;
            Objects.requireNonNull(r1);
            executor2.execute(new BubblesManager$5$$ExternalSyntheticLambda4(r1, arraySet, bubbleController$$ExternalSyntheticLambda9));
            this.mSavedBubbleKeysPerUser.remove(i);
        }
        BubbleData bubbleData2 = this.mBubbleData;
        Objects.requireNonNull(bubbleData2);
        bubbleData2.mCurrentUserId = i;
    }

    public final void promoteBubbleFromOverflow(Bubble bubble) {
        this.mLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK);
        bubble.setInflateSynchronously(this.mInflateSynchronously);
        bubble.mFlags |= 1;
        bubble.mLastAccessed = System.currentTimeMillis();
        bubble.setSuppressNotification(true);
        bubble.setShowDot(false);
        bubble.mIsBubble = true;
        Bubbles.SysuiProxy sysuiProxy = this.mSysuiProxy;
        String str = bubble.mKey;
        BubbleController$$ExternalSyntheticLambda11 bubbleController$$ExternalSyntheticLambda11 = new BubbleController$$ExternalSyntheticLambda11(this, true, bubble);
        BubblesManager.C17525 r0 = (BubblesManager.C17525) sysuiProxy;
        Objects.requireNonNull(r0);
        executor2.execute(new BubblesManager$5$$ExternalSyntheticLambda7(r0, str, bubbleController$$ExternalSyntheticLambda11));
    }

    @VisibleForTesting
    public void removeBubble(String str, int i) {
        if (this.mBubbleData.hasAnyBubbleWithKey(str)) {
            this.mBubbleData.dismissBubbleWithKey(str, i);
        }
    }

    @VisibleForTesting
    public void setExpandListener(Bubbles.BubbleExpandListener bubbleExpandListener) {
        BubbleController$$ExternalSyntheticLambda4 bubbleController$$ExternalSyntheticLambda4 = new BubbleController$$ExternalSyntheticLambda4(bubbleExpandListener);
        this.mExpandListener = bubbleController$$ExternalSyntheticLambda4;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            bubbleStackView.mExpandListener = bubbleController$$ExternalSyntheticLambda4;
        }
    }

    @VisibleForTesting
    public void updateBubble(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        Bubbles.SysuiProxy sysuiProxy = this.mSysuiProxy;
        String key = bubbleEntry.getKey();
        BubblesManager.C17525 r0 = (BubblesManager.C17525) sysuiProxy;
        Objects.requireNonNull(r0);
        executor2.execute(new Monitor$$ExternalSyntheticLambda0(r0, key, 3));
        if (bubbleEntry.mRanking.isTextChanged() || bubbleEntry.getBubbleMetadata() == null || bubbleEntry.getBubbleMetadata().getAutoExpandBubble() || !this.mBubbleData.hasOverflowBubbleWithKey(bubbleEntry.getKey())) {
            BubbleData bubbleData = this.mBubbleData;
            LocusId locusId = bubbleEntry.mSbn.getNotification().getLocusId();
            Objects.requireNonNull(bubbleData);
            if (bubbleData.mSuppressedBubbles.get(locusId) != null) {
                Bubble suppressedBubbleWithKey = this.mBubbleData.getSuppressedBubbleWithKey(bubbleEntry.getKey());
                if (suppressedBubbleWithKey != null) {
                    suppressedBubbleWithKey.setEntry(bubbleEntry);
                    return;
                }
                return;
            }
            Bubble orCreateBubble = this.mBubbleData.getOrCreateBubble(bubbleEntry, (Bubble) null);
            ensureStackViewCreated();
            orCreateBubble.setInflateSynchronously(this.mInflateSynchronously);
            orCreateBubble.inflate(new BubbleController$$ExternalSyntheticLambda3(this, z, z2), this.mContext, this, this.mStackView, this.mBubbleIconFactory, this.mBubbleBadgeIconFactory, false);
            return;
        }
        this.mBubbleData.getOverflowBubbleWithKey(bubbleEntry.getKey()).setEntry(bubbleEntry);
    }

    public final void updateStack() {
        BadgedImageView badgedImageView;
        Bubble bubble;
        int i;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            int i2 = 0;
            if (!this.mIsStatusBarShade) {
                bubbleStackView.setVisibility(4);
            } else if (hasBubbles()) {
                this.mStackView.setVisibility(0);
            }
            BubbleStackView bubbleStackView2 = this.mStackView;
            Objects.requireNonNull(bubbleStackView2);
            if (!bubbleStackView2.mBubbleData.getBubbles().isEmpty()) {
                for (int i3 = 0; i3 < bubbleStackView2.mBubbleData.getBubbles().size(); i3++) {
                    Bubble bubble2 = bubbleStackView2.mBubbleData.getBubbles().get(i3);
                    Objects.requireNonNull(bubble2);
                    String str = bubble2.mAppName;
                    String str2 = bubble2.mTitle;
                    if (str2 == null) {
                        str2 = bubbleStackView2.getResources().getString(C1777R.string.notification_bubble_title);
                    }
                    BadgedImageView badgedImageView2 = bubble2.mIconView;
                    if (badgedImageView2 != null) {
                        if (bubbleStackView2.mIsExpanded || i3 > 0) {
                            badgedImageView2.setContentDescription(bubbleStackView2.getResources().getString(C1777R.string.bubble_content_description_single, new Object[]{str2, str}));
                        } else {
                            bubble2.mIconView.setContentDescription(bubbleStackView2.getResources().getString(C1777R.string.bubble_content_description_stack, new Object[]{str2, str, Integer.valueOf(bubbleStackView2.mBubbleContainer.getChildCount() - 1)}));
                        }
                    }
                }
            }
            BubbleStackView bubbleStackView3 = this.mStackView;
            Objects.requireNonNull(bubbleStackView3);
            while (true) {
                badgedImageView = null;
                if (i2 >= bubbleStackView3.mBubbleData.getBubbles().size()) {
                    break;
                }
                if (i2 > 0) {
                    bubble = bubbleStackView3.mBubbleData.getBubbles().get(i2 - 1);
                } else {
                    bubble = null;
                }
                Bubble bubble3 = bubbleStackView3.mBubbleData.getBubbles().get(i2);
                Objects.requireNonNull(bubble3);
                BadgedImageView badgedImageView3 = bubble3.mIconView;
                if (badgedImageView3 != null) {
                    if (bubbleStackView3.mIsExpanded) {
                        badgedImageView3.setImportantForAccessibility(1);
                        if (bubble != null) {
                            badgedImageView = bubble.mIconView;
                        }
                        if (badgedImageView != null) {
                            badgedImageView3.setAccessibilityDelegate(new View.AccessibilityDelegate(badgedImageView) {
                                public final /* synthetic */ View val$prevBubbleIconView;

                                {
                                    this.val$prevBubbleIconView = r1;
                                }

                                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                                    accessibilityNodeInfo.setTraversalAfter(this.val$prevBubbleIconView);
                                }
                            });
                        }
                    } else {
                        if (i2 == 0) {
                            i = 1;
                        } else {
                            i = 2;
                        }
                        badgedImageView3.setImportantForAccessibility(i);
                    }
                }
                i2++;
            }
            if (bubbleStackView3.mIsExpanded) {
                BubbleOverflow bubbleOverflow = bubbleStackView3.mBubbleOverflow;
                if (bubbleOverflow != null) {
                    badgedImageView = bubbleOverflow.getIconView$1();
                }
                if (badgedImageView != null && !bubbleStackView3.mBubbleData.getBubbles().isEmpty()) {
                    Bubble bubble4 = bubbleStackView3.mBubbleData.getBubbles().get(bubbleStackView3.mBubbleData.getBubbles().size() - 1);
                    Objects.requireNonNull(bubble4);
                    BadgedImageView badgedImageView4 = bubble4.mIconView;
                    if (badgedImageView4 != null) {
                        badgedImageView.setAccessibilityDelegate(new View.AccessibilityDelegate(badgedImageView4) {
                            public final /* synthetic */ View val$lastBubbleIconView;

                            {
                                this.val$lastBubbleIconView = r1;
                            }

                            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                                accessibilityNodeInfo.setTraversalAfter(this.val$lastBubbleIconView);
                            }
                        });
                    }
                }
            }
        }
    }

    public final void updateWindowFlagsForBackpress(boolean z) {
        int i;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null && this.mAddedToWindowManager) {
            WindowManager.LayoutParams layoutParams = this.mWmLayoutParams;
            if (z) {
                i = 0;
            } else {
                i = 40;
            }
            layoutParams.flags = i | 16777216;
            this.mWindowManager.updateViewLayout(bubbleStackView, layoutParams);
        }
    }

    public static boolean canLaunchInTaskView(Context context, BubbleEntry bubbleEntry) {
        PendingIntent pendingIntent;
        if (bubbleEntry.getBubbleMetadata() != null) {
            pendingIntent = bubbleEntry.getBubbleMetadata().getIntent();
        } else {
            pendingIntent = null;
        }
        if (bubbleEntry.getBubbleMetadata() != null && bubbleEntry.getBubbleMetadata().getShortcutId() != null) {
            return true;
        }
        if (pendingIntent == null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to create bubble -- no intent: ");
            m.append(bubbleEntry.getKey());
            Log.w("Bubbles", m.toString());
            return false;
        }
        ActivityInfo resolveActivityInfo = pendingIntent.getIntent().resolveActivityInfo(getPackageManagerForUser(context, bubbleEntry.mSbn.getUser().getIdentifier()), 0);
        if (resolveActivityInfo == null) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to send as bubble, ");
            m2.append(bubbleEntry.getKey());
            m2.append(" couldn't find activity info for intent: ");
            m2.append(pendingIntent);
            Log.w("Bubbles", m2.toString());
            return false;
        } else if (ActivityInfo.isResizeableMode(resolveActivityInfo.resizeMode)) {
            return true;
        } else {
            StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to send as bubble, ");
            m3.append(bubbleEntry.getKey());
            m3.append(" activity is not resizable for intent: ");
            m3.append(pendingIntent);
            Log.w("Bubbles", m3.toString());
            return false;
        }
    }

    public final boolean isSummaryOfBubbles(BubbleEntry bubbleEntry) {
        boolean z;
        Objects.requireNonNull(bubbleEntry);
        String groupKey = bubbleEntry.mSbn.getGroupKey();
        ArrayList<Bubble> bubblesInGroup = getBubblesInGroup(groupKey);
        if (this.mBubbleData.isSummarySuppressed(groupKey)) {
            BubbleData bubbleData = this.mBubbleData;
            Objects.requireNonNull(bubbleData);
            if (bubbleData.mSuppressedGroupKeys.get(groupKey).equals(bubbleEntry.getKey())) {
                z = true;
                boolean isGroupSummary = bubbleEntry.mSbn.getNotification().isGroupSummary();
                if ((!z || isGroupSummary) && !bubblesInGroup.isEmpty()) {
                    return true;
                }
                return false;
            }
        }
        z = false;
        boolean isGroupSummary2 = bubbleEntry.mSbn.getNotification().isGroupSummary();
        if (!z) {
        }
        return true;
    }

    @VisibleForTesting
    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }

    @VisibleForTesting
    public Bubbles asBubbles() {
        return this.mImpl;
    }

    @VisibleForTesting
    public BubblePositioner getPositioner() {
        return this.mBubblePositioner;
    }

    @VisibleForTesting
    public BubbleStackView getStackView() {
        return this.mStackView;
    }
}
