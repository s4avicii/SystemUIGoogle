package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.pm.UserInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import androidx.fragment.R$id;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda1;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.NotificationLifetimeExtender;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class NotificationRemoteInputManager implements Dumpable {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public static boolean FORCE_REMOTE_INPUT_HISTORY = SystemProperties.getBoolean("debug.force_remoteinput_history", true);
    public IStatusBarService mBarService;
    public Callback mCallback;
    public final NotificationClickNotifier mClickNotifier;
    public final ArrayList mControllerCallbacks = new ArrayList();
    public final NotificationEntryManager mEntryManager;
    public final C11691 mInteractionHandler = new RemoteViews.InteractionHandler() {
        public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
            NotificationEntry notificationEntry;
            boolean z;
            int i;
            String str;
            RemoteInput[] remoteInputArr;
            String str2;
            String str3;
            NotificationListenerService.Ranking ranking;
            NotificationChannel channel;
            View view2 = view;
            PendingIntent pendingIntent2 = pendingIntent;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationRemoteInputManager.this.mStatusBarOptionalLazy.get().ifPresent(new PipMotionHelper$$ExternalSyntheticLambda1(view2, 2));
            ViewParent parent = view.getParent();
            while (true) {
                if (parent == null) {
                    notificationEntry = null;
                    break;
                } else if (parent instanceof ExpandableNotificationRow) {
                    notificationEntry = ((ExpandableNotificationRow) parent).mEntry;
                    break;
                } else {
                    parent = parent.getParent();
                }
            }
            ActionClickLogger actionClickLogger = NotificationRemoteInputManager.this.mLogger;
            Objects.requireNonNull(actionClickLogger);
            LogBuffer logBuffer = actionClickLogger.buffer;
            ActionClickLogger$logInitialClick$2 actionClickLogger$logInitialClick$2 = ActionClickLogger$logInitialClick$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$logInitialClick$2);
                if (notificationEntry == null) {
                    str2 = null;
                } else {
                    str2 = notificationEntry.mKey;
                }
                obtain.str1 = str2;
                if (notificationEntry == null || (ranking = notificationEntry.mRanking) == null || (channel = ranking.getChannel()) == null) {
                    str3 = null;
                } else {
                    str3 = channel.getId();
                }
                obtain.str2 = str3;
                obtain.str3 = pendingIntent.getIntent().toString();
                logBuffer.push(obtain);
            }
            boolean z2 = false;
            boolean z3 = true;
            if (NotificationRemoteInputManager.this.mCallback.shouldHandleRemoteInput()) {
                z = true;
            } else {
                Object tag = view2.getTag(16909390);
                if (tag instanceof RemoteInput[]) {
                    remoteInputArr = (RemoteInput[]) tag;
                } else {
                    remoteInputArr = null;
                }
                if (remoteInputArr != null) {
                    RemoteInput remoteInput = null;
                    for (RemoteInput remoteInput2 : remoteInputArr) {
                        if (remoteInput2.getAllowFreeFormInput()) {
                            remoteInput = remoteInput2;
                        }
                    }
                    if (remoteInput != null) {
                        NotificationRemoteInputManager notificationRemoteInputManager = NotificationRemoteInputManager.this;
                        Objects.requireNonNull(notificationRemoteInputManager);
                        z = true;
                        z3 = notificationRemoteInputManager.activateRemoteInput(view, remoteInputArr, remoteInput, pendingIntent, (NotificationEntry.EditedSuggestionInfo) null, (String) null, (AuthBypassPredicate) null);
                    }
                }
                z = true;
                z3 = false;
            }
            if (z3) {
                ActionClickLogger actionClickLogger2 = NotificationRemoteInputManager.this.mLogger;
                Objects.requireNonNull(actionClickLogger2);
                LogBuffer logBuffer2 = actionClickLogger2.buffer;
                ActionClickLogger$logRemoteInputWasHandled$2 actionClickLogger$logRemoteInputWasHandled$2 = ActionClickLogger$logRemoteInputWasHandled$2.INSTANCE;
                Objects.requireNonNull(logBuffer2);
                if (!logBuffer2.frozen) {
                    LogMessageImpl obtain2 = logBuffer2.obtain("ActionClickLogger", logLevel, actionClickLogger$logRemoteInputWasHandled$2);
                    if (notificationEntry == null) {
                        str = null;
                    } else {
                        str = notificationEntry.mKey;
                    }
                    obtain2.str1 = str;
                    logBuffer2.push(obtain2);
                }
                return z;
            }
            Notification.Action actionFromView = getActionFromView(view2, notificationEntry, pendingIntent2);
            if (actionFromView != null) {
                ViewParent parent2 = view.getParent();
                Objects.requireNonNull(notificationEntry);
                String key = notificationEntry.mSbn.getKey();
                if (view.getId() != 16908716 || parent2 == null || !(parent2 instanceof ViewGroup)) {
                    i = -1;
                } else {
                    i = ((ViewGroup) parent2).indexOfChild(view2);
                }
                NotificationVisibility obtain3 = NotificationRemoteInputManager.this.mVisibilityProvider.obtain(notificationEntry, z);
                NotificationClickNotifier notificationClickNotifier = NotificationRemoteInputManager.this.mClickNotifier;
                Objects.requireNonNull(notificationClickNotifier);
                try {
                    notificationClickNotifier.barService.onNotificationActionClick(key, i, actionFromView, obtain3, false);
                } catch (RemoteException unused) {
                }
                notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$1(notificationClickNotifier, key));
            }
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException unused2) {
            }
            Notification.Action actionFromView2 = getActionFromView(view2, notificationEntry, pendingIntent2);
            Callback callback = NotificationRemoteInputManager.this.mCallback;
            if (actionFromView2 != null) {
                z2 = actionFromView2.isAuthenticationRequired();
            }
            return callback.handleRemoteViewClick(pendingIntent2, z2, new NotificationRemoteInputManager$1$$ExternalSyntheticLambda0(this, remoteResponse, view, notificationEntry, pendingIntent));
        }

        public static Notification.Action getActionFromView(View view, NotificationEntry notificationEntry, PendingIntent pendingIntent) {
            Integer num = (Integer) view.getTag(16909269);
            if (num == null) {
                return null;
            }
            if (notificationEntry == null) {
                Log.w("NotifRemoteInputManager", "Couldn't determine notification for click.");
                return null;
            }
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Notification.Action[] actionArr = statusBarNotification.getNotification().actions;
            if (actionArr == null || num.intValue() >= actionArr.length) {
                Log.w("NotifRemoteInputManager", "statusBarNotification.getNotification().actions is null or invalid");
                return null;
            }
            Notification.Action action = statusBarNotification.getNotification().actions[num.intValue()];
            if (Objects.equals(action.actionIntent, pendingIntent)) {
                return action;
            }
            Log.w("NotifRemoteInputManager", "actionIntent does not match");
            return null;
        }
    };
    public final KeyguardManager mKeyguardManager;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final ActionClickLogger mLogger;
    public final Handler mMainHandler;
    public final NotifPipelineFlags mNotifPipelineFlags;
    public final RemoteInputNotificationRebuilder mRebuilder;
    public RemoteInputController mRemoteInputController;
    public RemoteInputListener mRemoteInputListener;
    public final RemoteInputUriController mRemoteInputUriController;
    public final SmartReplyController mSmartReplyController;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final StatusBarStateController mStatusBarStateController;
    public final UserManager mUserManager;
    public final NotificationVisibilityProvider mVisibilityProvider;

    public interface AuthBypassPredicate {
        boolean canSendRemoteInputWithoutBouncer();
    }

    public interface BouncerChecker {
    }

    public interface Callback {
        boolean handleRemoteViewClick(PendingIntent pendingIntent, boolean z, NotificationRemoteInputManager$1$$ExternalSyntheticLambda0 notificationRemoteInputManager$1$$ExternalSyntheticLambda0);

        void onLockedRemoteInput(ExpandableNotificationRow expandableNotificationRow, View view);

        void onLockedWorkRemoteInput(int i, View view);

        void onMakeExpandedVisibleForRemoteInput(ExpandableNotificationRow expandableNotificationRow, View view, boolean z, NotificationRemoteInputManager$$ExternalSyntheticLambda2 notificationRemoteInputManager$$ExternalSyntheticLambda2);

        boolean shouldHandleRemoteInput();
    }

    public interface ClickHandler {
    }

    @VisibleForTesting
    public class LegacyRemoteInputLifetimeExtender implements RemoteInputListener, Dumpable {
        public final ArraySet<NotificationEntry> mEntriesKeptForRemoteInputActive = new ArraySet<>();
        public final ArraySet<String> mKeysKeptForRemoteInputHistory = new ArraySet<>();
        public final ArrayList<NotificationLifetimeExtender> mLifetimeExtenders;
        public NotificationLifetimeExtender.NotificationSafeToRemoveCallback mNotificationLifetimeFinishedCallback;
        public RemoteInputController mRemoteInputController;

        public class RemoteInputActiveExtender extends RemoteInputExtender {
            public RemoteInputActiveExtender() {
                super();
            }

            public final void setShouldManageLifetime(NotificationEntry notificationEntry, boolean z) {
                if (z) {
                    if (Log.isLoggable("NotifRemoteInputManager", 3)) {
                        ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("Keeping notification around while remote input active "), notificationEntry.mKey, "NotifRemoteInputManager");
                    }
                    LegacyRemoteInputLifetimeExtender.this.mEntriesKeptForRemoteInputActive.add(notificationEntry);
                    return;
                }
                LegacyRemoteInputLifetimeExtender.this.mEntriesKeptForRemoteInputActive.remove(notificationEntry);
            }

            public final boolean shouldExtendLifetime(NotificationEntry notificationEntry) {
                return NotificationRemoteInputManager.this.isRemoteInputActive(notificationEntry);
            }
        }

        public abstract class RemoteInputExtender implements NotificationLifetimeExtender {
            public RemoteInputExtender() {
            }

            public final void setCallback(ScreenshotController$$ExternalSyntheticLambda3 screenshotController$$ExternalSyntheticLambda3) {
                LegacyRemoteInputLifetimeExtender legacyRemoteInputLifetimeExtender = LegacyRemoteInputLifetimeExtender.this;
                if (legacyRemoteInputLifetimeExtender.mNotificationLifetimeFinishedCallback == null) {
                    legacyRemoteInputLifetimeExtender.mNotificationLifetimeFinishedCallback = screenshotController$$ExternalSyntheticLambda3;
                }
            }
        }

        public class RemoteInputHistoryExtender extends RemoteInputExtender {
            public RemoteInputHistoryExtender() {
                super();
            }

            public final void setShouldManageLifetime(NotificationEntry notificationEntry, boolean z) {
                boolean z2;
                if (z) {
                    RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = NotificationRemoteInputManager.this.mRebuilder;
                    Objects.requireNonNull(remoteInputNotificationRebuilder);
                    CharSequence charSequence = notificationEntry.remoteInputText;
                    if (TextUtils.isEmpty(charSequence)) {
                        charSequence = notificationEntry.remoteInputTextWhenReset;
                    }
                    StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
                    notificationEntry.lastRemoteInputSent = -2000;
                    notificationEntry.remoteInputTextWhenReset = null;
                    if (rebuildWithRemoteInputInserted != null) {
                        NotificationEntryManager notificationEntryManager = NotificationRemoteInputManager.this.mEntryManager;
                        Objects.requireNonNull(notificationEntryManager);
                        try {
                            notificationEntryManager.updateNotificationInternal(rebuildWithRemoteInputInserted, (NotificationListenerService.RankingMap) null);
                        } catch (InflationException e) {
                            notificationEntryManager.handleInflationException(rebuildWithRemoteInputInserted, e);
                        }
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        if (expandableNotificationRow == null || expandableNotificationRow.mRemoved) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            if (Log.isLoggable("NotifRemoteInputManager", 3)) {
                                ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("Keeping notification around after sending remote input "), notificationEntry.mKey, "NotifRemoteInputManager");
                            }
                            LegacyRemoteInputLifetimeExtender.this.mKeysKeptForRemoteInputHistory.add(notificationEntry.mKey);
                            return;
                        }
                        return;
                    }
                    return;
                }
                LegacyRemoteInputLifetimeExtender.this.mKeysKeptForRemoteInputHistory.remove(notificationEntry.mKey);
            }

            public final boolean shouldExtendLifetime(NotificationEntry notificationEntry) {
                return NotificationRemoteInputManager.this.shouldKeepForRemoteInputHistory(notificationEntry);
            }
        }

        public class SmartReplyHistoryExtender extends RemoteInputExtender {
            public SmartReplyHistoryExtender() {
                super();
            }

            public final void setShouldManageLifetime(NotificationEntry notificationEntry, boolean z) {
                boolean z2;
                if (z) {
                    RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = NotificationRemoteInputManager.this.mRebuilder;
                    Objects.requireNonNull(remoteInputNotificationRebuilder);
                    StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, (CharSequence) null, false, (String) null, (Uri) null);
                    if (rebuildWithRemoteInputInserted != null) {
                        NotificationEntryManager notificationEntryManager = NotificationRemoteInputManager.this.mEntryManager;
                        Objects.requireNonNull(notificationEntryManager);
                        try {
                            notificationEntryManager.updateNotificationInternal(rebuildWithRemoteInputInserted, (NotificationListenerService.RankingMap) null);
                        } catch (InflationException e) {
                            notificationEntryManager.handleInflationException(rebuildWithRemoteInputInserted, e);
                        }
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        if (expandableNotificationRow == null || expandableNotificationRow.mRemoved) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            if (Log.isLoggable("NotifRemoteInputManager", 3)) {
                                ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("Keeping notification around after sending smart reply "), notificationEntry.mKey, "NotifRemoteInputManager");
                            }
                            LegacyRemoteInputLifetimeExtender.this.mKeysKeptForRemoteInputHistory.add(notificationEntry.mKey);
                            return;
                        }
                        return;
                    }
                    return;
                }
                LegacyRemoteInputLifetimeExtender.this.mKeysKeptForRemoteInputHistory.remove(notificationEntry.mKey);
                NotificationRemoteInputManager.this.mSmartReplyController.stopSending(notificationEntry);
            }

            public final boolean shouldExtendLifetime(NotificationEntry notificationEntry) {
                return NotificationRemoteInputManager.this.shouldKeepForSmartReplyHistory(notificationEntry);
            }
        }

        public final void onPanelCollapsed() {
            for (int i = 0; i < this.mEntriesKeptForRemoteInputActive.size(); i++) {
                NotificationEntry valueAt = this.mEntriesKeptForRemoteInputActive.valueAt(i);
                RemoteInputController remoteInputController = this.mRemoteInputController;
                if (remoteInputController != null) {
                    remoteInputController.removeRemoteInput(valueAt, (Object) null);
                }
                NotificationLifetimeExtender.NotificationSafeToRemoveCallback notificationSafeToRemoveCallback = this.mNotificationLifetimeFinishedCallback;
                if (notificationSafeToRemoveCallback != null) {
                    Objects.requireNonNull(valueAt);
                    ((ScreenshotController$$ExternalSyntheticLambda3) notificationSafeToRemoveCallback).onSafeToRemove(valueAt.mKey);
                }
            }
            this.mEntriesKeptForRemoteInputActive.clear();
        }

        public LegacyRemoteInputLifetimeExtender() {
            ArrayList<NotificationLifetimeExtender> arrayList = new ArrayList<>();
            this.mLifetimeExtenders = arrayList;
            arrayList.add(new RemoteInputHistoryExtender());
            arrayList.add(new SmartReplyHistoryExtender());
            arrayList.add(new RemoteInputActiveExtender());
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.println("LegacyRemoteInputLifetimeExtender:");
            printWriter.print("  mKeysKeptForRemoteInputHistory: ");
            printWriter.println(this.mKeysKeptForRemoteInputHistory);
            printWriter.print("  mEntriesKeptForRemoteInputActive: ");
            printWriter.println(this.mEntriesKeptForRemoteInputActive);
        }

        public final boolean isNotificationKeptForRemoteInputHistory(String str) {
            return this.mKeysKeptForRemoteInputHistory.contains(str);
        }

        public final void onRemoteInputSent(NotificationEntry notificationEntry) {
            if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                Objects.requireNonNull(notificationEntry);
                if (isNotificationKeptForRemoteInputHistory(notificationEntry.mKey)) {
                    ((ScreenshotController$$ExternalSyntheticLambda3) this.mNotificationLifetimeFinishedCallback).onSafeToRemove(notificationEntry.mKey);
                    return;
                }
            }
            if (this.mEntriesKeptForRemoteInputActive.contains(notificationEntry)) {
                NotificationRemoteInputManager.this.mMainHandler.postDelayed(new BiometricUnlockController$$ExternalSyntheticLambda0(this, notificationEntry, 1), 200);
            }
        }

        public final void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry) {
            String str = notificationEntry.mKey;
            if (isNotificationKeptForRemoteInputHistory(str)) {
                NotificationRemoteInputManager.this.mMainHandler.postDelayed(new PipTaskOrganizer$$ExternalSyntheticLambda4(this, str, 2), 200);
            }
        }

        public final void setRemoteInputController(RemoteInputController remoteInputController) {
            this.mRemoteInputController = remoteInputController;
        }

        @VisibleForTesting
        public Set<NotificationEntry> getEntriesKeptForRemoteInputActive() {
            return this.mEntriesKeptForRemoteInputActive;
        }
    }

    public interface RemoteInputListener {
        boolean isNotificationKeptForRemoteInputHistory(String str);

        void onPanelCollapsed();

        void onRemoteInputSent(NotificationEntry notificationEntry);

        void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry);

        void setRemoteInputController(RemoteInputController remoteInputController);
    }

    public final boolean isRemoteInputActive() {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isRemoteInputActive();
    }

    /* JADX WARNING: type inference failed for: r0v11, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean activateRemoteInput(android.view.View r17, android.app.RemoteInput[] r18, android.app.RemoteInput r19, android.app.PendingIntent r20, com.android.systemui.statusbar.notification.collection.NotificationEntry.EditedSuggestionInfo r21, java.lang.String r22, com.android.systemui.statusbar.NotificationRemoteInputManager.AuthBypassPredicate r23) {
        /*
            r16 = this;
            r1 = r16
            r9 = r17
            r3 = r18
            r4 = r19
            r5 = r20
            r7 = r22
            android.view.ViewParent r0 = r17.getParent()
        L_0x0010:
            r2 = 0
            if (r0 == 0) goto L_0x0038
            boolean r6 = r0 instanceof android.view.View
            if (r6 == 0) goto L_0x0033
            r6 = r0
            android.view.View r6 = (android.view.View) r6
            boolean r8 = r6.isRootNamespace()
            if (r8 == 0) goto L_0x0033
            java.lang.Object r0 = com.android.systemui.statusbar.policy.RemoteInputView.VIEW_TAG
            android.view.View r0 = r6.findViewWithTag(r0)
            com.android.systemui.statusbar.policy.RemoteInputView r0 = (com.android.systemui.statusbar.policy.RemoteInputView) r0
            r8 = 2131428725(0x7f0b0575, float:1.8479103E38)
            java.lang.Object r6 = r6.getTag(r8)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
            r10 = r6
            goto L_0x003a
        L_0x0033:
            android.view.ViewParent r0 = r0.getParent()
            goto L_0x0010
        L_0x0038:
            r0 = r2
            r10 = r0
        L_0x003a:
            r6 = 0
            if (r10 != 0) goto L_0x003e
            return r6
        L_0x003e:
            r11 = 1
            r10.setUserExpanded(r11, r6)
            if (r23 == 0) goto L_0x0046
            r12 = r11
            goto L_0x0047
        L_0x0046:
            r12 = r6
        L_0x0047:
            if (r12 != 0) goto L_0x0050
            boolean r8 = r1.showBouncerForRemoteInput(r9, r5, r10)
            if (r8 == 0) goto L_0x0050
            return r11
        L_0x0050:
            if (r0 == 0) goto L_0x0059
            boolean r8 = r0.isAttachedToWindow()
            if (r8 != 0) goto L_0x0059
            r0 = r2
        L_0x0059:
            if (r0 != 0) goto L_0x0072
            com.android.systemui.statusbar.notification.row.NotificationContentView r0 = r10.mPrivateLayout
            java.util.Objects.requireNonNull(r0)
            android.view.View r0 = r0.mExpandedChild
            if (r0 != 0) goto L_0x0065
            goto L_0x006e
        L_0x0065:
            java.lang.Object r2 = com.android.systemui.statusbar.policy.RemoteInputView.VIEW_TAG
            android.view.View r0 = r0.findViewWithTag(r2)
            r2 = r0
            com.android.systemui.statusbar.policy.RemoteInputView r2 = (com.android.systemui.statusbar.policy.RemoteInputView) r2
        L_0x006e:
            if (r2 != 0) goto L_0x0071
            return r6
        L_0x0071:
            r0 = r2
        L_0x0072:
            com.android.systemui.statusbar.notification.row.NotificationContentView r2 = r10.mPrivateLayout
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.policy.RemoteInputView r2 = r2.mExpandedRemoteInput
            if (r0 != r2) goto L_0x00a4
            com.android.systemui.statusbar.notification.row.NotificationContentView r2 = r10.mPrivateLayout
            java.util.Objects.requireNonNull(r2)
            android.view.View r2 = r2.mExpandedChild
            boolean r2 = r2.isShown()
            if (r2 != 0) goto L_0x00a4
            com.android.systemui.statusbar.NotificationRemoteInputManager$Callback r13 = r1.mCallback
            com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda2 r14 = new com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda2
            r0 = r14
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            r7 = r22
            r8 = r23
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r13.onMakeExpandedVisibleForRemoteInput(r10, r9, r12, r14)
            return r11
        L_0x00a4:
            boolean r2 = r0.isAttachedToWindow()
            if (r2 != 0) goto L_0x00ab
            return r6
        L_0x00ab:
            int r2 = r17.getWidth()
            boolean r8 = r9 instanceof android.widget.TextView
            if (r8 == 0) goto L_0x00d3
            r8 = r9
            android.widget.TextView r8 = (android.widget.TextView) r8
            android.text.Layout r13 = r8.getLayout()
            if (r13 == 0) goto L_0x00d3
            android.text.Layout r13 = r8.getLayout()
            float r6 = r13.getLineWidth(r6)
            int r6 = (int) r6
            int r13 = r8.getCompoundPaddingLeft()
            int r8 = r8.getCompoundPaddingRight()
            int r8 = r8 + r13
            int r8 = r8 + r6
            int r2 = java.lang.Math.min(r2, r8)
        L_0x00d3:
            int r6 = r17.getLeft()
            int r2 = r2 / 2
            int r2 = r2 + r6
            int r6 = r17.getTop()
            int r8 = r17.getHeight()
            int r8 = r8 / 2
            int r8 = r8 + r6
            int r6 = r0.getWidth()
            int r13 = r0.getHeight()
            int r14 = r2 + r8
            int r13 = r13 - r8
            int r15 = r2 + r13
            int r14 = java.lang.Math.max(r14, r15)
            int r6 = r6 - r2
            int r15 = r6 + r8
            int r6 = r6 + r13
            int r6 = java.lang.Math.max(r15, r6)
            int r6 = java.lang.Math.max(r14, r6)
            r0.mRevealCx = r2
            r0.mRevealCy = r8
            r0.mRevealR = r6
            r0.mPendingIntent = r5
            com.android.systemui.statusbar.policy.RemoteInputViewController r2 = r0.mViewController
            r2.setPendingIntent(r5)
            r2 = r21
            r0.setRemoteInput(r3, r4, r2)
            com.android.systemui.statusbar.policy.RemoteInputViewController r2 = r0.mViewController
            r2.setRemoteInput(r4)
            com.android.systemui.statusbar.policy.RemoteInputViewController r2 = r0.mViewController
            r2.setRemoteInputs(r3)
            int r2 = r0.getVisibility()
            if (r2 == 0) goto L_0x013d
            int r2 = r0.mRevealCx
            int r3 = r0.mRevealCy
            r4 = 0
            int r6 = r0.mRevealR
            float r6 = (float) r6
            android.animation.Animator r2 = android.view.ViewAnimationUtils.createCircularReveal(r0, r2, r3, r4, r6)
            r3 = 360(0x168, double:1.78E-321)
            r2.setDuration(r3)
            android.view.animation.PathInterpolator r3 = com.android.p012wm.shell.animation.Interpolators.LINEAR_OUT_SLOW_IN
            r2.setInterpolator(r3)
            r2.start()
        L_0x013d:
            r0.focus()
            if (r7 == 0) goto L_0x0147
            com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText r2 = r0.mEditText
            r2.setText(r7)
        L_0x0147:
            if (r12 == 0) goto L_0x015d
            com.android.systemui.statusbar.policy.RemoteInputViewController r6 = r0.mViewController
            com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0 r7 = new com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0
            r0 = r7
            r1 = r16
            r2 = r23
            r3 = r17
            r4 = r20
            r5 = r10
            r0.<init>(r1, r2, r3, r4, r5)
            r6.setBouncerChecker(r7)
        L_0x015d:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationRemoteInputManager.activateRemoteInput(android.view.View, android.app.RemoteInput[], android.app.RemoteInput, android.app.PendingIntent, com.android.systemui.statusbar.notification.collection.NotificationEntry$EditedSuggestionInfo, java.lang.String, com.android.systemui.statusbar.NotificationRemoteInputManager$AuthBypassPredicate):boolean");
    }

    public final void addControllerCallback(RemoteInputController.Callback callback) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            Objects.requireNonNull(callback);
            remoteInputController.mCallbacks.add(callback);
            return;
        }
        this.mControllerCallbacks.add(callback);
    }

    public final void closeRemoteInputs() {
        ExpandableNotificationRow expandableNotificationRow;
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            Objects.requireNonNull(remoteInputController);
            if (remoteInputController.mOpen.size() != 0) {
                ArrayList arrayList = new ArrayList(remoteInputController.mOpen.size());
                int size = remoteInputController.mOpen.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) remoteInputController.mOpen.get(size).first).get();
                    if (notificationEntry != null && notificationEntry.rowExists()) {
                        arrayList.add(notificationEntry);
                    }
                }
                int size2 = arrayList.size();
                while (true) {
                    size2--;
                    if (size2 >= 0) {
                        NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(size2);
                        if (notificationEntry2.rowExists() && (expandableNotificationRow = notificationEntry2.row) != null) {
                            expandableNotificationRow.closeRemoteInput();
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    @VisibleForTesting
    public LegacyRemoteInputLifetimeExtender createLegacyRemoteInputLifetimeExtender(Handler handler, NotificationEntryManager notificationEntryManager, SmartReplyController smartReplyController) {
        return new LegacyRemoteInputLifetimeExtender();
    }

    public final boolean isRemoteInputActive(NotificationEntry notificationEntry) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            Objects.requireNonNull(remoteInputController);
            if (remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, (NotificationEntry) null, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSpinning(String str) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            Objects.requireNonNull(remoteInputController);
            if (remoteInputController.mSpinning.containsKey(str)) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    public void onPerformRemoveNotification(NotificationEntry notificationEntry, String str) {
        ((LegacyRemoteInputLifetimeExtender) this.mRemoteInputListener).mKeysKeptForRemoteInputHistory.remove(str);
        if (isRemoteInputActive(notificationEntry)) {
            notificationEntry.mRemoteEditImeVisible = false;
            this.mRemoteInputController.removeRemoteInput(notificationEntry, (Object) null);
        }
    }

    public final boolean shouldKeepForRemoteInputHistory(NotificationEntry notificationEntry) {
        boolean z;
        if (!FORCE_REMOTE_INPUT_HISTORY) {
            return false;
        }
        Objects.requireNonNull(notificationEntry);
        if (!isSpinning(notificationEntry.mKey)) {
            if (SystemClock.elapsedRealtime() < notificationEntry.lastRemoteInputSent + 500) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public final boolean shouldKeepForSmartReplyHistory(NotificationEntry notificationEntry) {
        if (!FORCE_REMOTE_INPUT_HISTORY) {
            return false;
        }
        SmartReplyController smartReplyController = this.mSmartReplyController;
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        Objects.requireNonNull(smartReplyController);
        return smartReplyController.mSendingKeys.contains(str);
    }

    public final boolean showBouncerForRemoteInput(View view, PendingIntent pendingIntent, ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        boolean z2;
        UserInfo profileParent;
        if (this.mLockscreenUserManager.shouldAllowLockscreenRemoteInput()) {
            return false;
        }
        int identifier = pendingIntent.getCreatorUserHandle().getIdentifier();
        if (!this.mUserManager.getUserInfo(identifier).isManagedProfile() || !this.mKeyguardManager.isDeviceLocked(identifier)) {
            z = false;
        } else {
            z = true;
        }
        if (!z || (profileParent = this.mUserManager.getProfileParent(identifier)) == null || !this.mKeyguardManager.isDeviceLocked(profileParent.id)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (this.mLockscreenUserManager.isLockscreenPublicMode(identifier) || this.mStatusBarStateController.getState() == 1) {
            if (!z || z2) {
                this.mCallback.onLockedRemoteInput(expandableNotificationRow, view);
            } else {
                this.mCallback.onLockedWorkRemoteInput(identifier, view);
            }
            return true;
        } else if (!z) {
            return false;
        } else {
            this.mCallback.onLockedWorkRemoteInput(identifier, view);
            return true;
        }
    }

    public NotificationRemoteInputManager(Context context, NotifPipelineFlags notifPipelineFlags, NotificationLockscreenUserManager notificationLockscreenUserManager, SmartReplyController smartReplyController, NotificationVisibilityProvider notificationVisibilityProvider, NotificationEntryManager notificationEntryManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, Lazy<Optional<StatusBar>> lazy, StatusBarStateController statusBarStateController, Handler handler, RemoteInputUriController remoteInputUriController, NotificationClickNotifier notificationClickNotifier, ActionClickLogger actionClickLogger, DumpManager dumpManager) {
        this.mNotifPipelineFlags = notifPipelineFlags;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mSmartReplyController = smartReplyController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mEntryManager = notificationEntryManager;
        this.mStatusBarOptionalLazy = lazy;
        this.mMainHandler = handler;
        this.mLogger = actionClickLogger;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mRebuilder = remoteInputNotificationRebuilder;
        if (!notifPipelineFlags.isNewPipelineEnabled()) {
            this.mRemoteInputListener = createLegacyRemoteInputLifetimeExtender(handler, notificationEntryManager, smartReplyController);
        }
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mStatusBarStateController = statusBarStateController;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mClickNotifier = notificationClickNotifier;
        dumpManager.registerDumpable(this);
        notificationEntryManager.addNotificationEntryListener(new NotificationEntryListener() {
            public final void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
                NotificationRemoteInputManager.this.mSmartReplyController.stopSending(notificationEntry);
                if (z) {
                    NotificationRemoteInputManager.this.onPerformRemoveNotification(notificationEntry, notificationEntry.mKey);
                }
            }

            public final void onPreEntryUpdated(NotificationEntry notificationEntry) {
                NotificationRemoteInputManager.this.mSmartReplyController.stopSending(notificationEntry);
            }
        });
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = R$id.asIndenting(printWriter);
        if (this.mRemoteInputController != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mRemoteInputController: ");
            m.append(this.mRemoteInputController);
            asIndenting.println(m.toString());
            asIndenting.increaseIndent();
            RemoteInputController remoteInputController = this.mRemoteInputController;
            Objects.requireNonNull(remoteInputController);
            asIndenting.print("isRemoteInputActive: ");
            asIndenting.println(remoteInputController.isRemoteInputActive());
            asIndenting.println("mOpen: " + remoteInputController.mOpen.size());
            R$id.withIncreasedIndent(asIndenting, new CarrierTextManager$$ExternalSyntheticLambda2(remoteInputController, asIndenting, 2));
            asIndenting.println("mSpinning: " + remoteInputController.mSpinning.size());
            R$id.withIncreasedIndent(asIndenting, new CarrierTextManager$$ExternalSyntheticLambda1(remoteInputController, asIndenting, 3));
            asIndenting.println(remoteInputController.mSpinning);
            asIndenting.print("mDelegate: ");
            asIndenting.println(remoteInputController.mDelegate);
            asIndenting.decreaseIndent();
        }
        if (this.mRemoteInputListener instanceof Dumpable) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mRemoteInputListener: ");
            m2.append(this.mRemoteInputListener.getClass().getSimpleName());
            asIndenting.println(m2.toString());
            asIndenting.increaseIndent();
            ((Dumpable) this.mRemoteInputListener).dump(fileDescriptor, asIndenting, strArr);
            asIndenting.decreaseIndent();
        }
    }
}
