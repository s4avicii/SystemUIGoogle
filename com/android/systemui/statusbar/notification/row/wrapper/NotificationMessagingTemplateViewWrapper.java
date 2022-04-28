package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$array;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import java.util.Objects;

public final class NotificationMessagingTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ViewGroup mImageMessageContainer;
    public MessagingLayout mMessagingLayout;
    public MessagingLinearLayout mMessagingLinearLayout;
    public final int mMinHeightWithActions;
    public final View mTitle = this.mView.findViewById(16908310);
    public final View mTitleInHeader = this.mView.findViewById(16909073);

    public final int getMinLayoutHeight() {
        View view = this.mActionsContainer;
        if (view == null || view.getVisibility() == 8) {
            return 0;
        }
        return this.mMinHeightWithActions;
    }

    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        this.mMessagingLinearLayout = this.mMessagingLayout.getMessagingLinearLayout();
        this.mImageMessageContainer = this.mMessagingLayout.getImageMessageContainer();
        super.onContentUpdated(expandableNotificationRow);
    }

    public final void setRemoteInputVisible(boolean z) {
        this.mMessagingLayout.showHistoricMessages(z);
    }

    public NotificationMessagingTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mMessagingLayout = (MessagingLayout) view;
        this.mMinHeightWithActions = R$array.getFontScaledHeight(context, C1777R.dimen.notification_messaging_actions_min_height);
    }

    public final void updateTransformedTypes() {
        View view;
        super.updateTransformedTypes();
        MessagingLinearLayout messagingLinearLayout = this.mMessagingLinearLayout;
        if (messagingLinearLayout != null) {
            this.mTransformationHelper.addTransformedView(messagingLinearLayout);
        }
        if (this.mTitle == null && (view = this.mTitleInHeader) != null) {
            this.mTransformationHelper.addTransformedView(1, view);
        }
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        ViewGroup viewGroup = this.mImageMessageContainer;
        if (viewGroup != null) {
            C13381 r1 = new ViewTransformationHelper.CustomTransformation() {
                public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                    if (transformableView instanceof HybridNotificationView) {
                        return false;
                    }
                    transformState.ensureVisible();
                    return true;
                }

                public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                    return transformTo(transformState, transformableView, f);
                }
            };
            int id = viewGroup.getId();
            Objects.requireNonNull(viewTransformationHelper);
            viewTransformationHelper.mCustomTransformations.put(Integer.valueOf(id), r1);
        }
    }
}
