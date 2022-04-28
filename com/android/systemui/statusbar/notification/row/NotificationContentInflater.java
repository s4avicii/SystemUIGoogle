package com.android.systemui.statusbar.notification.row;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.internal.widget.MessagingMessage;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda5;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda8;
import com.android.systemui.media.MediaFeatureFlag;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda34;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.util.Assert;
import com.android.systemui.util.Utils;
import com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda1;
import com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
public class NotificationContentInflater implements NotificationRowContentBinder {
    public final Executor mBgExecutor;
    public final ConversationNotificationProcessor mConversationProcessor;
    public boolean mInflateSynchronously = false;
    public final boolean mIsMediaInQS;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final NotifRemoteViewCache mRemoteViewCache;
    public final SmartReplyStateInflater mSmartReplyStateInflater;

    @VisibleForTesting
    public static abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    public static class AsyncInflationTask extends AsyncTask<Void, Void, InflationProgress> implements NotificationRowContentBinder.InflationCallback, InflationTask {
        public final Executor mBgExecutor;
        public final NotificationRowContentBinder.InflationCallback mCallback;
        public CancellationSignal mCancellationSignal;
        public final Context mContext;
        public final ConversationNotificationProcessor mConversationProcessor;
        public final NotificationEntry mEntry;
        public Exception mError;
        public final boolean mInflateSynchronously;
        public final boolean mIsLowPriority;
        public final int mReInflateFlags;
        public final NotifRemoteViewCache mRemoteViewCache;
        public RemoteViews.InteractionHandler mRemoteViewClickHandler;
        public ExpandableNotificationRow mRow;
        public final SmartReplyStateInflater mSmartRepliesInflater;
        public final boolean mUsesIncreasedHeadsUpHeight;
        public final boolean mUsesIncreasedHeight;

        public final void abort() {
            cancel(true);
            CancellationSignal cancellationSignal = this.mCancellationSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
            }
        }

        public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            Void[] voidArr = (Void[]) objArr;
            return doInBackground();
        }

        public static class RtlEnabledContext extends ContextWrapper {
            public final ApplicationInfo getApplicationInfo() {
                ApplicationInfo applicationInfo = super.getApplicationInfo();
                applicationInfo.flags |= 4194304;
                return applicationInfo;
            }

            public RtlEnabledContext(Context context) {
                super(context);
            }
        }

        public final InflationProgress doInBackground() {
            try {
                NotificationEntry notificationEntry = this.mEntry;
                Objects.requireNonNull(notificationEntry);
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                try {
                    Notification.addFieldsFromContext(this.mContext.getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
                } catch (PackageManager.NameNotFoundException unused) {
                }
                Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.mContext, statusBarNotification.getNotification());
                Context packageContext = statusBarNotification.getPackageContext(this.mContext);
                if (recoverBuilder.usesTemplate()) {
                    packageContext = new RtlEnabledContext(packageContext);
                }
                NotificationEntry notificationEntry2 = this.mEntry;
                Objects.requireNonNull(notificationEntry2);
                if (notificationEntry2.mRanking.isConversation()) {
                    this.mConversationProcessor.processNotification(this.mEntry, recoverBuilder);
                }
                InflationProgress createRemoteViews = NotificationContentInflater.createRemoteViews(this.mReInflateFlags, recoverBuilder, this.mIsLowPriority, this.mUsesIncreasedHeight, this.mUsesIncreasedHeadsUpHeight, packageContext);
                ExpandableNotificationRow expandableNotificationRow = this.mRow;
                Objects.requireNonNull(expandableNotificationRow);
                NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                Objects.requireNonNull(notificationContentView);
                InflatedSmartReplyState inflatedSmartReplyState = notificationContentView.mCurrentSmartReplyState;
                NotificationContentInflater.inflateSmartReplyViews(createRemoteViews, this.mReInflateFlags, this.mEntry, this.mContext, packageContext, inflatedSmartReplyState, this.mSmartRepliesInflater);
                return createRemoteViews;
            } catch (Exception e) {
                this.mError = e;
                return null;
            }
        }

        public final void handleError(Exception exc) {
            NotificationEntry notificationEntry = this.mEntry;
            Objects.requireNonNull(notificationEntry);
            notificationEntry.mRunningTask = null;
            NotificationEntry notificationEntry2 = this.mEntry;
            Objects.requireNonNull(notificationEntry2);
            StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
            Log.e("StatusBar", "couldn't inflate view for notification " + (statusBarNotification.getPackageName() + "/0x" + Integer.toHexString(statusBarNotification.getId())), exc);
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                ExpandableNotificationRow expandableNotificationRow = this.mRow;
                Objects.requireNonNull(expandableNotificationRow);
                inflationCallback.handleInflationException(expandableNotificationRow.mEntry, new InflationException("Couldn't inflate contentViews" + exc));
            }
        }

        public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            boolean z;
            NotificationEntry notificationEntry2 = this.mEntry;
            Objects.requireNonNull(notificationEntry2);
            notificationEntry2.mRunningTask = null;
            this.mRow.onNotificationUpdated();
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(this.mEntry);
            }
            ExpandableNotificationRow expandableNotificationRow = this.mRow;
            Objects.requireNonNull(expandableNotificationRow);
            NotificationInlineImageResolver notificationInlineImageResolver = expandableNotificationRow.mImageResolver;
            Objects.requireNonNull(notificationInlineImageResolver);
            if (notificationInlineImageResolver.mImageCache == null || ActivityManager.isLowRamDeviceStatic()) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver.mImageCache;
                Objects.requireNonNull(notificationInlineImageCache);
                NotificationInlineImageResolver notificationInlineImageResolver2 = notificationInlineImageCache.mResolver;
                Objects.requireNonNull(notificationInlineImageResolver2);
                notificationInlineImageCache.mCache.entrySet().removeIf(new NotificationInlineImageCache$$ExternalSyntheticLambda0(notificationInlineImageResolver2.mWantedUriSet));
            }
        }

        public final void onPostExecute(InflationProgress inflationProgress) {
            Exception exc = this.mError;
            if (exc == null) {
                this.mCancellationSignal = NotificationContentInflater.apply(this.mBgExecutor, this.mInflateSynchronously, inflationProgress, this.mReInflateFlags, this.mRemoteViewCache, this.mEntry, this.mRow, this.mRemoteViewClickHandler, this);
                return;
            }
            handleError(exc);
        }

        public AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, boolean z2, boolean z3, boolean z4, RowContentBindStage.C13331 r11, NotificationRemoteInputManager.C11691 r12, SmartReplyStateInflater smartReplyStateInflater) {
            this.mEntry = notificationEntry;
            this.mRow = expandableNotificationRow;
            this.mBgExecutor = executor;
            this.mInflateSynchronously = z;
            this.mReInflateFlags = i;
            this.mRemoteViewCache = notifRemoteViewCache;
            this.mSmartRepliesInflater = smartReplyStateInflater;
            this.mContext = expandableNotificationRow.getContext();
            this.mIsLowPriority = z2;
            this.mUsesIncreasedHeight = z3;
            this.mUsesIncreasedHeadsUpHeight = z4;
            this.mRemoteViewClickHandler = r12;
            this.mCallback = r11;
            this.mConversationProcessor = conversationNotificationProcessor;
            notificationEntry.abortTask();
            notificationEntry.mRunningTask = this;
        }

        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            handleError(exc);
        }

        @VisibleForTesting
        public int getReInflateFlags() {
            return this.mReInflateFlags;
        }
    }

    @VisibleForTesting
    public static class InflationProgress {
        public InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        public InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        public CharSequence headsUpStatusBarText;
        public CharSequence headsUpStatusBarTextPublic;
        public View inflatedContentView;
        public View inflatedExpandedView;
        public View inflatedHeadsUpView;
        public View inflatedPublicView;
        public InflatedSmartReplyState inflatedSmartReplyState;
        public RemoteViews newContentView;
        public RemoteViews newExpandedView;
        public RemoteViews newHeadsUpView;
        public RemoteViews newPublicView;
        @VisibleForTesting
        public Context packageContext;
    }

    @VisibleForTesting
    public static boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (remoteViews == null && remoteViews2 == null) {
            return true;
        }
        return (remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !remoteViews.getPackage().equals(remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1)) ? false : true;
    }

    public static InflationProgress inflateSmartReplyViews(InflationProgress inflationProgress, int i, NotificationEntry notificationEntry, Context context, Context context2, InflatedSmartReplyState inflatedSmartReplyState, SmartReplyStateInflater smartReplyStateInflater) {
        boolean z;
        boolean z2;
        InflationProgress inflationProgress2 = inflationProgress;
        boolean z3 = true;
        if ((i & 1) == 0 || inflationProgress2.newContentView == null) {
            z = false;
        } else {
            z = true;
        }
        if ((i & 2) == 0 || inflationProgress2.newExpandedView == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        if ((i & 4) == 0 || inflationProgress2.newHeadsUpView == null) {
            z3 = false;
        }
        if (z || z2 || z3) {
            NotificationEntry notificationEntry2 = notificationEntry;
            inflationProgress2.inflatedSmartReplyState = smartReplyStateInflater.inflateSmartReplyState(notificationEntry);
        } else {
            NotificationEntry notificationEntry3 = notificationEntry;
            SmartReplyStateInflater smartReplyStateInflater2 = smartReplyStateInflater;
        }
        if (z2) {
            inflationProgress2.expandedInflatedSmartReplies = smartReplyStateInflater.inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress2.inflatedSmartReplyState);
        }
        if (z3) {
            inflationProgress2.headsUpInflatedSmartReplies = smartReplyStateInflater.inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress2.inflatedSmartReplyState);
        }
        return inflationProgress2;
    }

    @VisibleForTesting
    public InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater) {
        NotificationRowContentBinder.BindParams bindParams2 = bindParams;
        InflationProgress createRemoteViews = createRemoteViews(i, builder, bindParams2.isLowPriority, bindParams2.usesIncreasedHeight, bindParams2.usesIncreasedHeadsUpHeight, context);
        Context context2 = expandableNotificationRow.getContext();
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        Objects.requireNonNull(notificationContentView);
        inflateSmartReplyViews(createRemoteViews, i, notificationEntry, context2, context, notificationContentView.mCurrentSmartReplyState, smartReplyStateInflater);
        Executor executor = this.mBgExecutor;
        NotifRemoteViewCache notifRemoteViewCache = this.mRemoteViewCache;
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        Objects.requireNonNull(notificationRemoteInputManager);
        apply(executor, z, createRemoteViews, i, notifRemoteViewCache, notificationEntry, expandableNotificationRow, notificationRemoteInputManager.mInteractionHandler, (NotificationRowContentBinder.InflationCallback) null);
        return createRemoteViews;
    }

    public final void unbindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i) {
        int i2 = 1;
        while (i != 0) {
            if ((i & i2) != 0) {
                if (i2 == 1) {
                    Objects.requireNonNull(expandableNotificationRow);
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(0, new AsyncSensorManager$$ExternalSyntheticLambda1(this, expandableNotificationRow, notificationEntry, 1));
                } else if (i2 == 2) {
                    Objects.requireNonNull(expandableNotificationRow);
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(1, new AsyncSensorManager$$ExternalSyntheticLambda2(this, expandableNotificationRow, notificationEntry, 1));
                } else if (i2 == 4) {
                    Objects.requireNonNull(expandableNotificationRow);
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(2, new NotificationContentInflater$$ExternalSyntheticLambda1(this, expandableNotificationRow, notificationEntry));
                } else if (i2 == 8) {
                    Objects.requireNonNull(expandableNotificationRow);
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(0, new PipTaskOrganizer$$ExternalSyntheticLambda5(this, expandableNotificationRow, notificationEntry, 4));
                }
            }
            i &= ~i2;
            i2 <<= 1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x00be, code lost:
        r15 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x006a, code lost:
        r14 = r25;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.CancellationSignal apply(java.util.concurrent.Executor r23, boolean r24, com.android.systemui.statusbar.notification.row.NotificationContentInflater.InflationProgress r25, int r26, com.android.systemui.statusbar.notification.row.NotifRemoteViewCache r27, com.android.systemui.statusbar.notification.collection.NotificationEntry r28, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r29, android.widget.RemoteViews.InteractionHandler r30, com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback r31) {
        /*
            r15 = r25
            r14 = r27
            r13 = r28
            r12 = r29
            java.util.Objects.requireNonNull(r29)
            com.android.systemui.statusbar.notification.row.NotificationContentView r11 = r12.mPrivateLayout
            com.android.systemui.statusbar.notification.row.NotificationContentView r10 = r12.mPublicLayout
            java.util.HashMap r9 = new java.util.HashMap
            r9.<init>()
            r0 = r26 & 1
            r8 = 0
            r7 = 1
            if (r0 == 0) goto L_0x005f
            android.widget.RemoteViews r0 = r15.newContentView
            android.widget.RemoteViews r1 = r14.getCachedView(r13, r7)
            boolean r0 = canReapplyRemoteView(r0, r1)
            r16 = r0 ^ 1
            com.android.systemui.statusbar.notification.row.NotificationContentInflater$1 r6 = new com.android.systemui.statusbar.notification.row.NotificationContentInflater$1
            r6.<init>()
            java.util.Objects.requireNonNull(r11)
            android.view.View r5 = r11.mContractedChild
            com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper r17 = r11.getVisibleWrapper(r8)
            r4 = 1
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            r18 = r5
            r5 = r27
            r19 = r6
            r6 = r28
            r7 = r29
            r8 = r16
            r16 = r9
            r9 = r30
            r21 = r10
            r10 = r31
            r22 = r11
            r12 = r18
            r13 = r17
            r14 = r16
            r15 = r19
            applyRemoteView(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x0065
        L_0x005f:
            r16 = r9
            r21 = r10
            r22 = r11
        L_0x0065:
            r0 = r26 & 2
            r15 = 2
            if (r0 == 0) goto L_0x00b8
            r14 = r25
            android.widget.RemoteViews r0 = r14.newExpandedView
            if (r0 == 0) goto L_0x00b8
            r13 = r27
            r12 = r28
            android.widget.RemoteViews r1 = r13.getCachedView(r12, r15)
            boolean r0 = canReapplyRemoteView(r0, r1)
            r11 = 1
            r8 = r0 ^ 1
            com.android.systemui.statusbar.notification.row.NotificationContentInflater$2 r10 = new com.android.systemui.statusbar.notification.row.NotificationContentInflater$2
            r10.<init>()
            java.util.Objects.requireNonNull(r22)
            r9 = r22
            android.view.View r7 = r9.mExpandedChild
            com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper r17 = r9.getVisibleWrapper(r11)
            r4 = 2
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            r5 = r27
            r6 = r28
            r18 = r7
            r7 = r29
            r22 = r9
            r9 = r30
            r19 = r10
            r10 = r31
            r20 = r11
            r11 = r22
            r12 = r18
            r13 = r17
            r14 = r16
            r15 = r19
            applyRemoteView(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x00ba
        L_0x00b8:
            r20 = 1
        L_0x00ba:
            r0 = r26 & 4
            if (r0 == 0) goto L_0x0106
            r15 = r25
            android.widget.RemoteViews r0 = r15.newHeadsUpView
            if (r0 == 0) goto L_0x0106
            r1 = 4
            r14 = r27
            r13 = r28
            android.widget.RemoteViews r1 = r14.getCachedView(r13, r1)
            boolean r0 = canReapplyRemoteView(r0, r1)
            r8 = r0 ^ 1
            com.android.systemui.statusbar.notification.row.NotificationContentInflater$3 r12 = new com.android.systemui.statusbar.notification.row.NotificationContentInflater$3
            r12.<init>()
            java.util.Objects.requireNonNull(r22)
            r11 = r22
            android.view.View r10 = r11.mHeadsUpChild
            r0 = 2
            com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper r17 = r11.getVisibleWrapper(r0)
            r4 = 4
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            r5 = r27
            r6 = r28
            r7 = r29
            r9 = r30
            r18 = r10
            r10 = r31
            r19 = r12
            r12 = r18
            r13 = r17
            r14 = r16
            r15 = r19
            applyRemoteView(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
        L_0x0106:
            r0 = r26 & 8
            if (r0 == 0) goto L_0x0152
            r15 = r25
            android.widget.RemoteViews r0 = r15.newPublicView
            r1 = 8
            r14 = r27
            r13 = r28
            android.widget.RemoteViews r1 = r14.getCachedView(r13, r1)
            boolean r0 = canReapplyRemoteView(r0, r1)
            r8 = r0 ^ 1
            com.android.systemui.statusbar.notification.row.NotificationContentInflater$4 r12 = new com.android.systemui.statusbar.notification.row.NotificationContentInflater$4
            r12.<init>()
            java.util.Objects.requireNonNull(r21)
            r11 = r21
            android.view.View r10 = r11.mContractedChild
            r0 = 0
            com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper r17 = r11.getVisibleWrapper(r0)
            r4 = 8
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            r5 = r27
            r6 = r28
            r7 = r29
            r9 = r30
            r18 = r10
            r10 = r31
            r19 = r12
            r12 = r18
            r13 = r17
            r14 = r16
            r15 = r19
            applyRemoteView(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
        L_0x0152:
            r0 = r25
            r1 = r26
            r2 = r27
            r3 = r16
            r4 = r31
            r5 = r28
            r6 = r29
            finishIfDone(r0, r1, r2, r3, r4, r5, r6)
            android.os.CancellationSignal r0 = new android.os.CancellationSignal
            r0.<init>()
            com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda0 r1 = new com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda0
            r2 = r16
            r1.<init>(r2)
            r0.setOnCancelListener(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationContentInflater.apply(java.util.concurrent.Executor, boolean, com.android.systemui.statusbar.notification.row.NotificationContentInflater$InflationProgress, int, com.android.systemui.statusbar.notification.row.NotifRemoteViewCache, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, android.widget.RemoteViews$InteractionHandler, com.android.systemui.statusbar.notification.row.NotificationRowContentBinder$InflationCallback):android.os.CancellationSignal");
    }

    @VisibleForTesting
    public static void applyRemoteView(Executor executor, boolean z, InflationProgress inflationProgress, int i, int i2, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, boolean z2, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationContentView notificationContentView, View view, NotificationViewWrapper notificationViewWrapper, HashMap<Integer, CancellationSignal> hashMap, ApplyCallback applyCallback) {
        CancellationSignal cancellationSignal;
        InflationProgress inflationProgress2 = inflationProgress;
        RemoteViews.InteractionHandler interactionHandler2 = interactionHandler;
        NotificationRowContentBinder.InflationCallback inflationCallback2 = inflationCallback;
        HashMap<Integer, CancellationSignal> hashMap2 = hashMap;
        RemoteViews remoteView = applyCallback.getRemoteView();
        if (!z) {
            final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
            NotificationContentView notificationContentView2 = notificationContentView;
            View view2 = view;
            final ApplyCallback applyCallback2 = applyCallback;
            final boolean z3 = z2;
            final NotificationViewWrapper notificationViewWrapper2 = notificationViewWrapper;
            final HashMap<Integer, CancellationSignal> hashMap3 = hashMap;
            final int i3 = i2;
            final InflationProgress inflationProgress3 = inflationProgress;
            final int i4 = i;
            final NotifRemoteViewCache notifRemoteViewCache2 = notifRemoteViewCache;
            final NotificationRowContentBinder.InflationCallback inflationCallback3 = inflationCallback;
            final NotificationEntry notificationEntry2 = notificationEntry;
            RemoteViews remoteViews = remoteView;
            final View view3 = view;
            final RemoteViews remoteViews2 = remoteViews;
            final NotificationContentView notificationContentView3 = notificationContentView;
            final RemoteViews.InteractionHandler interactionHandler3 = interactionHandler;
            C13235 r1 = new RemoteViews.OnViewAppliedListener() {
                public final void onError(Exception exc) {
                    try {
                        View view = view3;
                        if (z3) {
                            view = remoteViews2.apply(inflationProgress3.packageContext, notificationContentView3, interactionHandler3);
                        } else {
                            remoteViews2.reapply(inflationProgress3.packageContext, view, interactionHandler3);
                        }
                        Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                        onViewApplied(view);
                    } catch (Exception unused) {
                        hashMap3.remove(Integer.valueOf(i3));
                        HashMap hashMap = hashMap3;
                        ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
                        Objects.requireNonNull(expandableNotificationRow);
                        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                        NotificationRowContentBinder.InflationCallback inflationCallback = inflationCallback3;
                        Assert.isMainThread();
                        hashMap.values().forEach(DreamOverlayStateController$$ExternalSyntheticLambda8.INSTANCE$1);
                        if (inflationCallback != null) {
                            inflationCallback.handleInflationException(notificationEntry, exc);
                        }
                    }
                }

                public final void onViewApplied(View view) {
                    if (z3) {
                        view.setIsRootNamespace(true);
                        applyCallback2.setResultView(view);
                    } else {
                        NotificationViewWrapper notificationViewWrapper = notificationViewWrapper2;
                        if (notificationViewWrapper != null) {
                            notificationViewWrapper.onReinflated();
                        }
                    }
                    hashMap3.remove(Integer.valueOf(i3));
                    NotificationContentInflater.finishIfDone(inflationProgress3, i4, notifRemoteViewCache2, hashMap3, inflationCallback3, notificationEntry2, ExpandableNotificationRow.this);
                }

                public final void onViewInflated(View view) {
                    if (view instanceof ImageMessageConsumer) {
                        ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
                        Objects.requireNonNull(expandableNotificationRow);
                        ((ImageMessageConsumer) view).setImageResolver(expandableNotificationRow.mImageResolver);
                    }
                }
            };
            if (z2) {
                cancellationSignal = remoteViews.applyAsync(inflationProgress2.packageContext, notificationContentView, executor, r1, interactionHandler);
            } else {
                cancellationSignal = remoteViews.reapplyAsync(inflationProgress2.packageContext, view, executor, r1, interactionHandler);
            }
            hashMap.put(Integer.valueOf(i2), cancellationSignal);
        } else if (z2) {
            try {
                View apply = remoteView.apply(inflationProgress2.packageContext, notificationContentView, interactionHandler2);
                apply.setIsRootNamespace(true);
                applyCallback.setResultView(apply);
            } catch (Exception e) {
                Objects.requireNonNull(expandableNotificationRow);
                NotificationEntry notificationEntry3 = expandableNotificationRow.mEntry;
                Assert.isMainThread();
                hashMap.values().forEach(DreamOverlayStateController$$ExternalSyntheticLambda8.INSTANCE$1);
                if (inflationCallback2 != null) {
                    inflationCallback2.handleInflationException(notificationEntry3, e);
                }
                hashMap2.put(Integer.valueOf(i2), new CancellationSignal());
            }
        } else {
            remoteView.reapply(inflationProgress2.packageContext, view, interactionHandler2);
            notificationViewWrapper.onReinflated();
        }
    }

    public static InflationProgress createRemoteViews(int i, Notification.Builder builder, boolean z, boolean z2, boolean z3, Context context) {
        RemoteViews remoteViews;
        InflationProgress inflationProgress = new InflationProgress();
        if ((i & 1) != 0) {
            if (z) {
                remoteViews = builder.makeLowPriorityContentView(false);
            } else {
                remoteViews = builder.createContentView(z2);
            }
            inflationProgress.newContentView = remoteViews;
        }
        if ((i & 2) != 0) {
            RemoteViews createBigContentView = builder.createBigContentView();
            if (createBigContentView == null) {
                if (z) {
                    createBigContentView = builder.createContentView();
                    Notification.Builder.makeHeaderExpanded(createBigContentView);
                } else {
                    createBigContentView = null;
                }
            }
            inflationProgress.newExpandedView = createBigContentView;
        }
        if ((i & 4) != 0) {
            inflationProgress.newHeadsUpView = builder.createHeadsUpContentView(z3);
        }
        if ((i & 8) != 0) {
            inflationProgress.newPublicView = builder.makePublicContentView(z);
        }
        inflationProgress.packageContext = context;
        inflationProgress.headsUpStatusBarText = builder.getHeadsUpStatusBarText(false);
        inflationProgress.headsUpStatusBarTextPublic = builder.getHeadsUpStatusBarText(true);
        return inflationProgress;
    }

    public final void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, RowContentBindStage.C13331 r23) {
        boolean z2;
        List<Notification.MessagingStyle.Message> list;
        NotificationEntry notificationEntry2 = notificationEntry;
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
        NotificationRowContentBinder.BindParams bindParams2 = bindParams;
        Objects.requireNonNull(expandableNotificationRow);
        if (!expandableNotificationRow2.mRemoved) {
            StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
            NotificationInlineImageResolver notificationInlineImageResolver = expandableNotificationRow2.mImageResolver;
            Notification notification = statusBarNotification.getNotification();
            Objects.requireNonNull(notificationInlineImageResolver);
            if (notificationInlineImageResolver.mImageCache == null || ActivityManager.isLowRamDeviceStatic()) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                HashSet hashSet = new HashSet();
                Bundle bundle = notification.extras;
                if (bundle != null) {
                    Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
                    List<Notification.MessagingStyle.Message> list2 = null;
                    if (parcelableArray == null) {
                        list = null;
                    } else {
                        list = Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
                    }
                    if (list != null) {
                        for (Notification.MessagingStyle.Message message : list) {
                            if (MessagingMessage.hasImage(message)) {
                                hashSet.add(message.getDataUri());
                            }
                        }
                    }
                    Parcelable[] parcelableArray2 = bundle.getParcelableArray("android.messages.historic");
                    if (parcelableArray2 != null) {
                        list2 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray2);
                    }
                    if (list2 != null) {
                        for (Notification.MessagingStyle.Message message2 : list2) {
                            if (MessagingMessage.hasImage(message2)) {
                                hashSet.add(message2.getDataUri());
                            }
                        }
                    }
                    notificationInlineImageResolver.mWantedUriSet = hashSet;
                }
                notificationInlineImageResolver.mWantedUriSet.forEach(new StatusBar$$ExternalSyntheticLambda34(notificationInlineImageResolver, 1));
            }
            if (z) {
                this.mRemoteViewCache.clearCache(notificationEntry2);
            }
            if ((i & 1) != 0) {
                expandableNotificationRow2.mPrivateLayout.removeContentInactiveRunnable(0);
            }
            if ((i & 2) != 0) {
                expandableNotificationRow2.mPrivateLayout.removeContentInactiveRunnable(1);
            }
            if ((i & 4) != 0) {
                expandableNotificationRow2.mPrivateLayout.removeContentInactiveRunnable(2);
            }
            if ((i & 8) != 0) {
                expandableNotificationRow2.mPublicLayout.removeContentInactiveRunnable(0);
            }
            Executor executor = this.mBgExecutor;
            boolean z3 = this.mInflateSynchronously;
            NotifRemoteViewCache notifRemoteViewCache = this.mRemoteViewCache;
            ConversationNotificationProcessor conversationNotificationProcessor = this.mConversationProcessor;
            boolean z4 = bindParams2.isLowPriority;
            boolean z5 = bindParams2.usesIncreasedHeight;
            boolean z6 = bindParams2.usesIncreasedHeadsUpHeight;
            NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
            Objects.requireNonNull(notificationRemoteInputManager);
            AsyncInflationTask asyncInflationTask = r1;
            AsyncInflationTask asyncInflationTask2 = new AsyncInflationTask(executor, z3, i, notifRemoteViewCache, notificationEntry, conversationNotificationProcessor, expandableNotificationRow, z4, z5, z6, r23, notificationRemoteInputManager.mInteractionHandler, this.mSmartReplyStateInflater);
            if (this.mInflateSynchronously) {
                asyncInflationTask.onPostExecute(asyncInflationTask.doInBackground());
                return;
            }
            asyncInflationTask.executeOnExecutor(this.mBgExecutor, new Void[0]);
        }
    }

    public NotificationContentInflater(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflater smartReplyStateInflater) {
        this.mRemoteViewCache = notifRemoteViewCache;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mConversationProcessor = conversationNotificationProcessor;
        Objects.requireNonNull(mediaFeatureFlag);
        this.mIsMediaInQS = Utils.useQsMediaPlayer(mediaFeatureFlag.context);
        this.mBgExecutor = executor;
        this.mSmartReplyStateInflater = smartReplyStateInflater;
    }

    public static boolean finishIfDone(InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, HashMap<Integer, CancellationSignal> hashMap, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        Assert.isMainThread();
        Objects.requireNonNull(expandableNotificationRow);
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        NotificationContentView notificationContentView2 = expandableNotificationRow.mPublicLayout;
        if (!hashMap.isEmpty()) {
            return false;
        }
        if ((i & 1) != 0) {
            View view = inflationProgress.inflatedContentView;
            if (view != null) {
                notificationContentView.setContractedChild(view);
                notifRemoteViewCache.putCachedView(notificationEntry, 1, inflationProgress.newContentView);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 1)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 1, inflationProgress.newContentView);
            }
        }
        if ((i & 2) != 0) {
            View view2 = inflationProgress.inflatedExpandedView;
            if (view2 != null) {
                notificationContentView.setExpandedChild(view2);
                notifRemoteViewCache.putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
            } else if (inflationProgress.newExpandedView == null) {
                notificationContentView.setExpandedChild((View) null);
                notifRemoteViewCache.removeCachedView(notificationEntry, 2);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 2)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
            }
            if (inflationProgress.newExpandedView != null) {
                InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = inflationProgress.expandedInflatedSmartReplies;
                Objects.requireNonNull(notificationContentView);
                notificationContentView.mExpandedInflatedSmartReplies = inflatedSmartReplyViewHolder;
                if (inflatedSmartReplyViewHolder == null) {
                    notificationContentView.mExpandedSmartReplyView = null;
                }
            } else {
                Objects.requireNonNull(notificationContentView);
                notificationContentView.mExpandedInflatedSmartReplies = null;
                notificationContentView.mExpandedSmartReplyView = null;
            }
            if (inflationProgress.newExpandedView != null) {
                z = true;
            } else {
                z = false;
            }
            expandableNotificationRow.mExpandable = z;
            NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
            boolean isExpandable = expandableNotificationRow.isExpandable();
            Objects.requireNonNull(notificationContentView3);
            notificationContentView3.updateExpandButtonsDuringLayout(isExpandable, false);
        }
        if ((i & 4) != 0) {
            View view3 = inflationProgress.inflatedHeadsUpView;
            if (view3 != null) {
                notificationContentView.setHeadsUpChild(view3);
                notifRemoteViewCache.putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
            } else if (inflationProgress.newHeadsUpView == null) {
                notificationContentView.setHeadsUpChild((View) null);
                notifRemoteViewCache.removeCachedView(notificationEntry, 4);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 4)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
            }
            if (inflationProgress.newHeadsUpView != null) {
                InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder2 = inflationProgress.headsUpInflatedSmartReplies;
                Objects.requireNonNull(notificationContentView);
                notificationContentView.mHeadsUpInflatedSmartReplies = inflatedSmartReplyViewHolder2;
                if (inflatedSmartReplyViewHolder2 == null) {
                    notificationContentView.mHeadsUpSmartReplyView = null;
                }
            } else {
                Objects.requireNonNull(notificationContentView);
                notificationContentView.mHeadsUpInflatedSmartReplies = null;
                notificationContentView.mHeadsUpSmartReplyView = null;
            }
        }
        InflatedSmartReplyState inflatedSmartReplyState = inflationProgress.inflatedSmartReplyState;
        Objects.requireNonNull(notificationContentView);
        notificationContentView.mCurrentSmartReplyState = inflatedSmartReplyState;
        if ((i & 8) != 0) {
            View view4 = inflationProgress.inflatedPublicView;
            if (view4 != null) {
                notificationContentView2.setContractedChild(view4);
                notifRemoteViewCache.putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 8)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
            }
        }
        notificationEntry.headsUpStatusBarText = inflationProgress.headsUpStatusBarText;
        notificationEntry.headsUpStatusBarTextPublic = inflationProgress.headsUpStatusBarTextPublic;
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry);
        }
        return true;
    }

    public final void cancelBind(NotificationEntry notificationEntry) {
        notificationEntry.abortTask();
    }

    @VisibleForTesting
    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }
}
