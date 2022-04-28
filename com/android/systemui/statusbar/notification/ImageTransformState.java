package com.android.systemui.statusbar.notification;

import android.graphics.drawable.Icon;
import android.util.Pools;
import android.view.View;
import android.widget.ImageView;
import androidx.leanback.R$raw;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;

public class ImageTransformState extends TransformState {
    public static Pools.SimplePool<ImageTransformState> sInstancePool = new Pools.SimplePool<>(40);
    public Icon mIcon;

    public final void appear(float f, TransformableView transformableView) {
        if (transformableView instanceof HybridNotificationView) {
            if (f == 0.0f) {
                this.mTransformedView.setPivotY(0.0f);
                View view = this.mTransformedView;
                view.setPivotX((float) (view.getWidth() / 2));
                resetTransformedView();
            }
            float max = Math.max(Math.min(((f * 360.0f) - 150.0f) / 210.0f, 1.0f), 0.0f);
            R$raw.fadeIn(this.mTransformedView, max, false);
            float interpolation = Interpolators.LINEAR_OUT_SLOW_IN.getInterpolation(max);
            this.mTransformedView.setScaleX(interpolation);
            this.mTransformedView.setScaleY(interpolation);
            return;
        }
        super.appear(f, transformableView);
    }

    public final void disappear(float f, TransformableView transformableView) {
        if (transformableView instanceof HybridNotificationView) {
            if (f == 0.0f) {
                this.mTransformedView.setPivotY(0.0f);
                View view = this.mTransformedView;
                view.setPivotX((float) (view.getWidth() / 2));
            }
            float max = Math.max(Math.min((((1.0f - f) * 360.0f) - 150.0f) / 210.0f, 1.0f), 0.0f);
            R$raw.fadeOut(this.mTransformedView, 1.0f - max, false);
            float interpolation = Interpolators.LINEAR_OUT_SLOW_IN.getInterpolation(max);
            this.mTransformedView.setScaleX(interpolation);
            this.mTransformedView.setScaleY(interpolation);
            return;
        }
        super.disappear(f, transformableView);
    }

    public boolean sameAs(TransformState transformState) {
        if (this.mSameAsAny) {
            return true;
        }
        if (!(transformState instanceof ImageTransformState)) {
            return false;
        }
        Icon icon = this.mIcon;
        if (icon == null || !icon.sameAs(((ImageTransformState) transformState).mIcon)) {
            return false;
        }
        return true;
    }

    public void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        if (view instanceof ImageView) {
            this.mIcon = (Icon) view.getTag(C1777R.C1779id.image_icon_tag);
        }
    }

    public void recycle() {
        super.recycle();
        if (getClass() == ImageTransformState.class) {
            sInstancePool.release(this);
        }
    }

    public void reset() {
        super.reset();
        this.mIcon = null;
    }
}
