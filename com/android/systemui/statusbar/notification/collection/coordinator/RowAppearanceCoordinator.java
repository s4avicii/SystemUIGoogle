package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.SectionClassifier;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import java.util.Objects;

/* compiled from: RowAppearanceCoordinator.kt */
public final class RowAppearanceCoordinator implements Coordinator {
    public NotificationEntry entryToExpand;
    public final boolean mAlwaysExpandNonGroupedNotification;
    public AssistantFeedbackController mAssistantFeedbackController;
    public SectionClassifier mSectionClassifier;

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new RowAppearanceCoordinator$attach$1(this));
        RowAppearanceCoordinator$attach$2 rowAppearanceCoordinator$attach$2 = new RowAppearanceCoordinator$attach$2(this);
        RenderStageManager renderStageManager = notifPipeline.mRenderStageManager;
        Objects.requireNonNull(renderStageManager);
        renderStageManager.onAfterRenderEntryListeners.add(rowAppearanceCoordinator$attach$2);
    }

    public RowAppearanceCoordinator(Context context, AssistantFeedbackController assistantFeedbackController, SectionClassifier sectionClassifier) {
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mSectionClassifier = sectionClassifier;
        this.mAlwaysExpandNonGroupedNotification = context.getResources().getBoolean(C1777R.bool.config_alwaysExpandNonGroupedNotifications);
    }
}
