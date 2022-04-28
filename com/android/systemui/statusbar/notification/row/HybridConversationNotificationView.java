package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.ConversationLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.NotificationFadeAware;

public class HybridConversationNotificationView extends HybridNotificationView {
    public View mConversationFacePile;
    public ImageView mConversationIconView;
    public TextView mConversationSenderName;
    public int mFacePileAvatarSize;
    public int mFacePileProtectionWidth;
    public int mFacePileSize;
    public int mSingleAvatarSize;

    public HybridConversationNotificationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void bind(CharSequence charSequence, CharSequence charSequence2, View view) {
        if (!(view instanceof ConversationLayout)) {
            super.bind(charSequence, charSequence2, view);
            return;
        }
        ConversationLayout conversationLayout = (ConversationLayout) view;
        Icon conversationIcon = conversationLayout.getConversationIcon();
        if (conversationIcon != null) {
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.setVisibility(0);
            this.mConversationIconView.setImageIcon(conversationIcon);
            setSize(this.mConversationIconView, this.mSingleAvatarSize);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            View requireViewById = requireViewById(16908916);
            this.mConversationFacePile = requireViewById;
            ImageView imageView = (ImageView) requireViewById.requireViewById(16908918);
            ImageView imageView2 = (ImageView) this.mConversationFacePile.requireViewById(16908917);
            ImageView imageView3 = (ImageView) this.mConversationFacePile.requireViewById(16908919);
            conversationLayout.bindFacePile(imageView, imageView2, imageView3);
            setSize(this.mConversationFacePile, this.mFacePileSize);
            setSize(imageView2, this.mFacePileAvatarSize);
            setSize(imageView3, this.mFacePileAvatarSize);
            setSize(imageView, (this.mFacePileProtectionWidth * 2) + this.mFacePileAvatarSize);
            this.mTransformationHelper.addViewTransformingToSimilar(imageView3);
            this.mTransformationHelper.addViewTransformingToSimilar(imageView2);
            this.mTransformationHelper.addViewTransformingToSimilar(imageView);
        }
        CharSequence conversationTitle = conversationLayout.getConversationTitle();
        if (!TextUtils.isEmpty(conversationTitle)) {
            charSequence = conversationTitle;
        }
        if (conversationLayout.isOneToOne()) {
            this.mConversationSenderName.setVisibility(8);
        } else {
            this.mConversationSenderName.setVisibility(0);
            this.mConversationSenderName.setText(conversationLayout.getConversationSenderName());
        }
        CharSequence conversationText = conversationLayout.getConversationText();
        if (!TextUtils.isEmpty(conversationText)) {
            charSequence2 = conversationText;
        }
        super.bind(charSequence, charSequence2, conversationLayout);
    }

    public final void setNotificationFaded(boolean z) {
        NotificationFadeAware.setLayerTypeForFaded(this.mConversationFacePile, z);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public static void setSize(View view, int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mConversationIconView = (ImageView) requireViewById(16908921);
        this.mConversationFacePile = requireViewById(16908916);
        this.mConversationSenderName = (TextView) requireViewById(C1777R.C1779id.conversation_notification_sender);
        this.mFacePileSize = getResources().getDimensionPixelSize(C1777R.dimen.conversation_single_line_face_pile_size);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(C1777R.dimen.conversation_single_line_face_pile_avatar_size);
        this.mSingleAvatarSize = getResources().getDimensionPixelSize(C1777R.dimen.conversation_single_line_avatar_size);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(C1777R.dimen.conversation_single_line_face_pile_protection_width);
        this.mTransformationHelper.addViewTransformingToSimilar(this.mConversationIconView);
        this.mTransformationHelper.addTransformedView(this.mConversationSenderName);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
