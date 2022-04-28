package com.android.p012wm.shell;

import android.app.ActivityManager;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda8;
import com.android.p012wm.shell.bubbles.BubbleData;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.common.DisplayChangeController;
import com.android.p012wm.shell.common.TaskStackListenerCallback;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellInitImpl$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$$ExternalSyntheticLambda5 implements Consumer {
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda5 INSTANCE = new ShellInitImpl$$ExternalSyntheticLambda5(0);
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda5 INSTANCE$1 = new ShellInitImpl$$ExternalSyntheticLambda5(1);
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda5 INSTANCE$2 = new ShellInitImpl$$ExternalSyntheticLambda5(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) obj;
                Objects.requireNonNull(bubbleController);
                BubbleData bubbleData = bubbleController.mBubbleData;
                BubbleController.C17955 r0 = bubbleController.mBubbleDataListener;
                Objects.requireNonNull(bubbleData);
                bubbleData.mListener = r0;
                BubbleData bubbleData2 = bubbleController.mBubbleData;
                PipController$$ExternalSyntheticLambda1 pipController$$ExternalSyntheticLambda1 = new PipController$$ExternalSyntheticLambda1(bubbleController);
                Objects.requireNonNull(bubbleData2);
                bubbleData2.mSuppressionListener = pipController$$ExternalSyntheticLambda1;
                BubbleData bubbleData3 = bubbleController.mBubbleData;
                ImageLoader$$ExternalSyntheticLambda0 imageLoader$$ExternalSyntheticLambda0 = new ImageLoader$$ExternalSyntheticLambda0(bubbleController);
                Objects.requireNonNull(bubbleData3);
                bubbleData3.mCancelledListener = imageLoader$$ExternalSyntheticLambda0;
                try {
                    bubbleController.mWindowManagerShellWrapper.addPinnedStackListener(new BubbleController.BubblesImeListener());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                BubbleData bubbleData4 = bubbleController.mBubbleData;
                int i = bubbleController.mCurrentUserId;
                Objects.requireNonNull(bubbleData4);
                bubbleData4.mCurrentUserId = i;
                ShellTaskOrganizer shellTaskOrganizer = bubbleController.mTaskOrganizer;
                BubbleController$$ExternalSyntheticLambda1 bubbleController$$ExternalSyntheticLambda1 = new BubbleController$$ExternalSyntheticLambda1(bubbleController);
                Objects.requireNonNull(shellTaskOrganizer);
                synchronized (shellTaskOrganizer.mLock) {
                    shellTaskOrganizer.mLocusIdListeners.add(bubbleController$$ExternalSyntheticLambda1);
                    for (int i2 = 0; i2 < shellTaskOrganizer.mVisibleTasksWithLocusId.size(); i2++) {
                        bubbleController$$ExternalSyntheticLambda1.onVisibilityChanged(shellTaskOrganizer.mVisibleTasksWithLocusId.keyAt(i2), shellTaskOrganizer.mVisibleTasksWithLocusId.valueAt(i2), true);
                    }
                }
                bubbleController.mLauncherApps.registerCallback(new LauncherApps.Callback() {
                    public final void onPackageAdded(String str, UserHandle userHandle) {
                    }

                    public final void onPackageChanged(String str, UserHandle userHandle) {
                    }

                    public final void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z) {
                    }

                    public final void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z) {
                        for (String bubbleData$$ExternalSyntheticLambda7 : strArr) {
                            BubbleData bubbleData = BubbleController.this.mBubbleData;
                            Objects.requireNonNull(bubbleData);
                            BubbleData$$ExternalSyntheticLambda7 bubbleData$$ExternalSyntheticLambda72 = new BubbleData$$ExternalSyntheticLambda7(bubbleData$$ExternalSyntheticLambda7, 0);
                            BubbleData$$ExternalSyntheticLambda2 bubbleData$$ExternalSyntheticLambda2 = new BubbleData$$ExternalSyntheticLambda2(bubbleData);
                            BubbleData.performActionOnBubblesMatching(bubbleData.getBubbles(), bubbleData$$ExternalSyntheticLambda72, bubbleData$$ExternalSyntheticLambda2);
                            BubbleData.performActionOnBubblesMatching(bubbleData.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda72, bubbleData$$ExternalSyntheticLambda2);
                        }
                    }

                    public final void onPackageRemoved(String str, UserHandle userHandle) {
                        BubbleData bubbleData = BubbleController.this.mBubbleData;
                        Objects.requireNonNull(bubbleData);
                        BubbleData$$ExternalSyntheticLambda7 bubbleData$$ExternalSyntheticLambda7 = new BubbleData$$ExternalSyntheticLambda7(str, 0);
                        BubbleData$$ExternalSyntheticLambda2 bubbleData$$ExternalSyntheticLambda2 = new BubbleData$$ExternalSyntheticLambda2(bubbleData);
                        BubbleData.performActionOnBubblesMatching(bubbleData.getBubbles(), bubbleData$$ExternalSyntheticLambda7, bubbleData$$ExternalSyntheticLambda2);
                        BubbleData.performActionOnBubblesMatching(bubbleData.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda7, bubbleData$$ExternalSyntheticLambda2);
                    }

                    public final void onShortcutsChanged(String str, List<ShortcutInfo> list, UserHandle userHandle) {
                        super.onShortcutsChanged(str, list, userHandle);
                        BubbleData bubbleData = BubbleController.this.mBubbleData;
                        Objects.requireNonNull(bubbleData);
                        HashSet hashSet = new HashSet();
                        for (ShortcutInfo id : list) {
                            hashSet.add(id.getId());
                        }
                        BubbleData$$ExternalSyntheticLambda8 bubbleData$$ExternalSyntheticLambda8 = new BubbleData$$ExternalSyntheticLambda8(str, hashSet);
                        BubbleData$$ExternalSyntheticLambda1 bubbleData$$ExternalSyntheticLambda1 = new BubbleData$$ExternalSyntheticLambda1(bubbleData);
                        BubbleData.performActionOnBubblesMatching(bubbleData.getBubbles(), bubbleData$$ExternalSyntheticLambda8, bubbleData$$ExternalSyntheticLambda1);
                        BubbleData.performActionOnBubblesMatching(bubbleData.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda8, bubbleData$$ExternalSyntheticLambda1);
                    }
                }, bubbleController.mMainHandler);
                bubbleController.mTaskStackListener.addListener(new TaskStackListenerCallback() {
                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                        for (Bubble next : BubbleController.this.mBubbleData.getBubbles()) {
                            if (runningTaskInfo.taskId == next.getTaskId()) {
                                BubbleController.this.mBubbleData.setSelectedBubble(next);
                                BubbleController.this.mBubbleData.setExpanded(true);
                                return;
                            }
                        }
                        for (Bubble next2 : BubbleController.this.mBubbleData.getOverflowBubbles()) {
                            if (runningTaskInfo.taskId == next2.getTaskId()) {
                                BubbleController.this.promoteBubbleFromOverflow(next2);
                                BubbleController.this.mBubbleData.setExpanded(true);
                                return;
                            }
                        }
                    }

                    public final void onTaskMovedToFront(int i) {
                        Bubbles.SysuiProxy sysuiProxy = BubbleController.this.mSysuiProxy;
                        if (sysuiProxy != null) {
                            BubbleController$3$$ExternalSyntheticLambda1 bubbleController$3$$ExternalSyntheticLambda1 = new BubbleController$3$$ExternalSyntheticLambda1(this, i);
                            BubblesManager.C17525 r0 = (BubblesManager.C17525) sysuiProxy;
                            Objects.requireNonNull(r0);
                            executor2.execute(new BubblesManager$5$$ExternalSyntheticLambda2(r0, bubbleController$3$$ExternalSyntheticLambda1, 0));
                        }
                    }
                });
                bubbleController.mDisplayController.addDisplayChangingController(new DisplayChangeController.OnDisplayChangingListener() {
                    public final void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
                        BubbleStackView bubbleStackView;
                        if (i2 != i3 && (bubbleStackView = BubbleController.this.mStackView) != null) {
                            bubbleStackView.mRelativeStackPositionBeforeRotation = new BubbleStackView.RelativeStackPosition(bubbleStackView.mPositioner.getRestingPosition(), bubbleStackView.mStackAnimationController.getAllowableStackPositionRegion());
                            bubbleStackView.addOnLayoutChangeListener(bubbleStackView.mOrientationChangedListener);
                            bubbleStackView.hideFlyoutImmediate();
                        }
                    }
                });
                bubbleController.mOneHandedOptional.ifPresent(new BubbleController$$ExternalSyntheticLambda8(bubbleController, 0));
                return;
            case 1:
                int i3 = ZenModeControllerImpl.$r8$clinit;
                Objects.requireNonNull((ZenModeController.Callback) obj);
                return;
            default:
                StartingWindowController startingWindowController = (StartingWindowController) obj;
                int i4 = StartingWindowController.IStartingWindowImpl.$r8$clinit;
                Objects.requireNonNull(startingWindowController);
                startingWindowController.mTaskLaunchingCallback = null;
                return;
        }
    }
}
