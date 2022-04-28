package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.internal.widget.MessagingImageMessage;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$array;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.TransformState;

public final class MessagingImageTransformState extends ImageTransformState {
    public static final int START_ACTUAL_HEIGHT = C1777R.C1779id.transformation_start_actual_height;
    public static final int START_ACTUAL_WIDTH = C1777R.C1779id.transformation_start_actual_width;
    public static Pools.SimplePool<MessagingImageTransformState> sInstancePool = new Pools.SimplePool<>(40);
    public MessagingImageMessage mImageMessage;

    public final boolean transformScale(TransformState transformState) {
        return false;
    }

    public final void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        this.mImageMessage = (MessagingImageMessage) view;
    }

    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    public final void reset() {
        super.reset();
        this.mImageMessage = null;
    }

    public final void resetTransformedView() {
        super.resetTransformedView();
        MessagingImageMessage messagingImageMessage = this.mImageMessage;
        messagingImageMessage.setActualWidth(messagingImageMessage.getWidth());
        MessagingImageMessage messagingImageMessage2 = this.mImageMessage;
        messagingImageMessage2.setActualHeight(messagingImageMessage2.getHeight());
    }

    public final boolean sameAs(TransformState transformState) {
        if (super.sameAs(transformState)) {
            return true;
        }
        if (transformState instanceof MessagingImageTransformState) {
            return this.mImageMessage.sameAs(((MessagingImageTransformState) transformState).mImageMessage);
        }
        return false;
    }

    public final void transformViewFrom(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        int i2;
        super.transformViewFrom(transformState, i, customTransformation, f);
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        if ((transformState instanceof MessagingImageTransformState) && sameAs(transformState)) {
            MessagingImageMessage messagingImageMessage = ((MessagingImageTransformState) transformState).mImageMessage;
            if (f == 0.0f) {
                this.mTransformedView.setTag(START_ACTUAL_WIDTH, Integer.valueOf(messagingImageMessage.getActualWidth()));
                this.mTransformedView.setTag(START_ACTUAL_HEIGHT, Integer.valueOf(messagingImageMessage.getActualHeight()));
            }
            Object tag = this.mTransformedView.getTag(START_ACTUAL_WIDTH);
            int i3 = -1;
            if (tag == null) {
                i2 = -1;
            } else {
                i2 = ((Integer) tag).intValue();
            }
            MessagingImageMessage messagingImageMessage2 = this.mImageMessage;
            messagingImageMessage2.setActualWidth((int) R$array.interpolate((float) i2, (float) messagingImageMessage2.getWidth(), interpolation));
            Object tag2 = this.mTransformedView.getTag(START_ACTUAL_HEIGHT);
            if (tag2 != null) {
                i3 = ((Integer) tag2).intValue();
            }
            MessagingImageMessage messagingImageMessage3 = this.mImageMessage;
            messagingImageMessage3.setActualHeight((int) R$array.interpolate((float) i3, (float) messagingImageMessage3.getHeight(), interpolation));
        }
    }
}
