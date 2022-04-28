package com.android.systemui.statusbar.notification.row;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.BindStage;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.Objects;

public final class NotifBindPipeline {
    public final ArrayMap mBindEntries = new ArrayMap();
    public final C13171 mCollectionListener;
    public final NotifBindPipelineLogger mLogger;
    public final NotifBindPipelineHandler mMainHandler;
    public final ArrayList mScratchCallbacksList = new ArrayList();
    public BindStage mStage;

    public interface BindCallback {
        void onBindFinished(NotificationEntry notificationEntry);
    }

    public class BindEntry {
        public final ArraySet callbacks = new ArraySet();
        public boolean invalidated;
        public ExpandableNotificationRow row;
    }

    public class NotifBindPipelineHandler extends Handler {
        public NotifBindPipelineHandler(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (message.what == 1) {
                NotificationEntry notificationEntry = (NotificationEntry) message.obj;
                NotifBindPipeline notifBindPipeline = NotifBindPipeline.this;
                Objects.requireNonNull(notifBindPipeline);
                NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
                Objects.requireNonNull(notificationEntry);
                String str = notificationEntry.mKey;
                Objects.requireNonNull(notifBindPipelineLogger);
                LogBuffer logBuffer = notifBindPipelineLogger.buffer;
                LogLevel logLevel = LogLevel.INFO;
                NotifBindPipelineLogger$logStartPipeline$2 notifBindPipelineLogger$logStartPipeline$2 = NotifBindPipelineLogger$logStartPipeline$2.INSTANCE;
                Objects.requireNonNull(logBuffer);
                if (!logBuffer.frozen) {
                    LogMessageImpl obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logStartPipeline$2);
                    obtain.str1 = str;
                    logBuffer.push(obtain);
                }
                if (notifBindPipeline.mStage != null) {
                    ExpandableNotificationRow expandableNotificationRow = ((BindEntry) notifBindPipeline.mBindEntries.get(notificationEntry)).row;
                    BindStage bindStage = notifBindPipeline.mStage;
                    NgaUiController$$ExternalSyntheticLambda3 ngaUiController$$ExternalSyntheticLambda3 = new NgaUiController$$ExternalSyntheticLambda3(notifBindPipeline);
                    RowContentBindStage rowContentBindStage = (RowContentBindStage) bindStage;
                    Objects.requireNonNull(rowContentBindStage);
                    RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
                    RowContentBindStageLogger rowContentBindStageLogger = rowContentBindStage.mLogger;
                    String str2 = notificationEntry.mKey;
                    String rowContentBindParams2 = rowContentBindParams.toString();
                    Objects.requireNonNull(rowContentBindStageLogger);
                    LogBuffer logBuffer2 = rowContentBindStageLogger.buffer;
                    RowContentBindStageLogger$logStageParams$2 rowContentBindStageLogger$logStageParams$2 = RowContentBindStageLogger$logStageParams$2.INSTANCE;
                    Objects.requireNonNull(logBuffer2);
                    if (!logBuffer2.frozen) {
                        LogMessageImpl obtain2 = logBuffer2.obtain("RowContentBindStage", logLevel, rowContentBindStageLogger$logStageParams$2);
                        obtain2.str1 = str2;
                        obtain2.str2 = rowContentBindParams2;
                        logBuffer2.push(obtain2);
                    }
                    int i = rowContentBindParams.mContentViews;
                    int i2 = rowContentBindParams.mDirtyContentViews & i;
                    rowContentBindStage.mBinder.unbindContent(notificationEntry, expandableNotificationRow, i ^ 15);
                    NotificationRowContentBinder.BindParams bindParams = new NotificationRowContentBinder.BindParams();
                    bindParams.isLowPriority = rowContentBindParams.mUseLowPriority;
                    bindParams.usesIncreasedHeight = rowContentBindParams.mUseIncreasedHeight;
                    bindParams.usesIncreasedHeadsUpHeight = rowContentBindParams.mUseIncreasedHeadsUpHeight;
                    boolean z = rowContentBindParams.mViewsNeedReinflation;
                    RowContentBindStage.C13331 r6 = new NotificationRowContentBinder.InflationCallback(ngaUiController$$ExternalSyntheticLambda3) {
                        public final /* synthetic */ BindStage.StageCallback val$callback;

                        {
                            this.val$callback = r2;
                        }

                        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
                            RowContentBindStage.this.mNotifInflationErrorManager.setInflationError(notificationEntry, exc);
                        }

                        public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
                            RowContentBindStage.this.mNotifInflationErrorManager.clearInflationError(notificationEntry);
                            ((RowContentBindParams) RowContentBindStage.this.getStageParams(notificationEntry)).mDirtyContentViews = 0;
                            NgaUiController$$ExternalSyntheticLambda3 ngaUiController$$ExternalSyntheticLambda3 = (NgaUiController$$ExternalSyntheticLambda3) this.val$callback;
                            Objects.requireNonNull(ngaUiController$$ExternalSyntheticLambda3);
                            NotifBindPipeline.$r8$lambda$QRo7GExQEDf4mZLfcJ0VhAf4hBg((NotifBindPipeline) ngaUiController$$ExternalSyntheticLambda3.f$0, notificationEntry);
                        }
                    };
                    rowContentBindStage.mBinder.cancelBind(notificationEntry);
                    rowContentBindStage.mBinder.bindContent(notificationEntry, expandableNotificationRow, i2, bindParams, z, r6);
                    return;
                }
                throw new IllegalStateException("No stage was ever set on the pipeline");
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown message type: ");
            m.append(message.what);
            throw new IllegalArgumentException(m.toString());
        }
    }

    public final void manageRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        NotifBindPipelineLogger notifBindPipelineLogger = this.mLogger;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(notifBindPipelineLogger);
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$logManagedRow$2 notifBindPipelineLogger$logManagedRow$2 = NotifBindPipelineLogger$logManagedRow$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logManagedRow$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        BindEntry bindEntry = (BindEntry) this.mBindEntries.get(notificationEntry);
        if (bindEntry != null) {
            bindEntry.row = expandableNotificationRow;
            if (bindEntry.invalidated) {
                requestPipelineRun(notificationEntry);
            }
        }
    }

    public final void requestPipelineRun(NotificationEntry notificationEntry) {
        NotifBindPipelineLogger notifBindPipelineLogger = this.mLogger;
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        Objects.requireNonNull(notifBindPipelineLogger);
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$logRequestPipelineRun$2 notifBindPipelineLogger$logRequestPipelineRun$2 = NotifBindPipelineLogger$logRequestPipelineRun$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logRequestPipelineRun$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        if (((BindEntry) this.mBindEntries.get(notificationEntry)).row == null) {
            NotifBindPipelineLogger notifBindPipelineLogger2 = this.mLogger;
            String str2 = notificationEntry.mKey;
            Objects.requireNonNull(notifBindPipelineLogger2);
            LogBuffer logBuffer2 = notifBindPipelineLogger2.buffer;
            NotifBindPipelineLogger$logRequestPipelineRowNotSet$2 notifBindPipelineLogger$logRequestPipelineRowNotSet$2 = NotifBindPipelineLogger$logRequestPipelineRowNotSet$2.INSTANCE;
            Objects.requireNonNull(logBuffer2);
            if (!logBuffer2.frozen) {
                LogMessageImpl obtain2 = logBuffer2.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logRequestPipelineRowNotSet$2);
                obtain2.str1 = str2;
                logBuffer2.push(obtain2);
                return;
            }
            return;
        }
        RowContentBindStage rowContentBindStage = (RowContentBindStage) this.mStage;
        Objects.requireNonNull(rowContentBindStage);
        rowContentBindStage.mBinder.cancelBind(notificationEntry);
        if (!this.mMainHandler.hasMessages(1, notificationEntry)) {
            this.mMainHandler.sendMessage(Message.obtain(this.mMainHandler, 1, notificationEntry));
        }
    }

    public static void $r8$lambda$QRo7GExQEDf4mZLfcJ0VhAf4hBg(NotifBindPipeline notifBindPipeline, NotificationEntry notificationEntry) {
        Objects.requireNonNull(notifBindPipeline);
        BindEntry bindEntry = (BindEntry) notifBindPipeline.mBindEntries.get(notificationEntry);
        ArraySet arraySet = bindEntry.callbacks;
        NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        int size = arraySet.size();
        Objects.requireNonNull(notifBindPipelineLogger);
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$logFinishedPipeline$2 notifBindPipelineLogger$logFinishedPipeline$2 = NotifBindPipelineLogger$logFinishedPipeline$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logFinishedPipeline$2);
            obtain.str1 = str;
            obtain.int1 = size;
            logBuffer.push(obtain);
        }
        bindEntry.invalidated = false;
        notifBindPipeline.mScratchCallbacksList.addAll(arraySet);
        arraySet.clear();
        for (int i = 0; i < notifBindPipeline.mScratchCallbacksList.size(); i++) {
            ((BindCallback) notifBindPipeline.mScratchCallbacksList.get(i)).onBindFinished(notificationEntry);
        }
        notifBindPipeline.mScratchCallbacksList.clear();
    }

    public NotifBindPipeline(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, Looper looper) {
        C13171 r0 = new NotifCollectionListener() {
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                if (((BindEntry) NotifBindPipeline.this.mBindEntries.remove(notificationEntry)).row != null) {
                    RowContentBindStage rowContentBindStage = (RowContentBindStage) NotifBindPipeline.this.mStage;
                    Objects.requireNonNull(rowContentBindStage);
                    rowContentBindStage.mBinder.cancelBind(notificationEntry);
                }
                BindStage bindStage = NotifBindPipeline.this.mStage;
                Objects.requireNonNull(bindStage);
                bindStage.mContentParams.remove(notificationEntry);
                NotifBindPipeline.this.mMainHandler.removeMessages(1, notificationEntry);
            }

            public final void onEntryInit(NotificationEntry notificationEntry) {
                NotifBindPipeline.this.mBindEntries.put(notificationEntry, new BindEntry());
                BindStage bindStage = NotifBindPipeline.this.mStage;
                Objects.requireNonNull(bindStage);
                bindStage.mContentParams.put(notificationEntry, new RowContentBindParams());
            }
        };
        this.mCollectionListener = r0;
        commonNotifCollection.addCollectionListener(r0);
        this.mLogger = notifBindPipelineLogger;
        this.mMainHandler = new NotifBindPipelineHandler(looper);
    }
}
