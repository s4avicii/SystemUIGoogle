package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.leanback.R$raw;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.TransformState;
import java.util.Objects;

public class HybridNotificationView extends AlphaOptimizedLinearLayout implements TransformableView, NotificationFadeAware {
    public TextView mTextView;
    public TextView mTitleView;
    public final ViewTransformationHelper mTransformationHelper;

    public HybridNotificationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public void setNotificationFaded(boolean z) {
    }

    public final void transformFrom(TransformableView transformableView) {
        this.mTransformationHelper.transformFrom(transformableView);
    }

    public final void transformTo(TransformableView transformableView, Runnable runnable) {
        this.mTransformationHelper.transformTo(transformableView, runnable);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void bind(CharSequence charSequence, CharSequence charSequence2, View view) {
        int i;
        this.mTitleView.setText(charSequence);
        TextView textView = this.mTitleView;
        if (TextUtils.isEmpty(charSequence)) {
            i = 8;
        } else {
            i = 0;
        }
        textView.setVisibility(i);
        if (TextUtils.isEmpty(charSequence2)) {
            this.mTextView.setVisibility(8);
            this.mTextView.setText((CharSequence) null);
        } else {
            this.mTextView.setVisibility(0);
            this.mTextView.setText(charSequence2.toString());
        }
        requestLayout();
    }

    public final TransformState getCurrentState(int i) {
        return this.mTransformationHelper.getCurrentState(i);
    }

    public final void setVisible(boolean z) {
        int i;
        if (z) {
            i = 0;
        } else {
            i = 4;
        }
        setVisibility(i);
        this.mTransformationHelper.setVisible(z);
    }

    public final void transformFrom(TransformableView transformableView, float f) {
        this.mTransformationHelper.transformFrom(transformableView, f);
    }

    public final void transformTo(TransformableView transformableView, float f) {
        this.mTransformationHelper.transformTo(transformableView, f);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(C1777R.C1779id.notification_title);
        this.mTextView = (TextView) findViewById(C1777R.C1779id.notification_text);
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        C13161 r1 = new ViewTransformationHelper.CustomTransformation() {
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(1);
                R$raw.fadeIn((View) HybridNotificationView.this.mTextView, f, true);
                if (currentState != null) {
                    transformState.transformViewFrom(currentState, 16, (ViewTransformationHelper.CustomTransformation) null, f);
                    currentState.recycle();
                }
                return true;
            }

            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(1);
                R$raw.fadeOut((View) HybridNotificationView.this.mTextView, f, true);
                if (currentState != null) {
                    transformState.transformViewTo(currentState, 16, (ViewTransformationHelper.CustomTransformation) null, f);
                    currentState.recycle();
                }
                return true;
            }
        };
        Objects.requireNonNull(viewTransformationHelper);
        viewTransformationHelper.mCustomTransformations.put(2, r1);
        this.mTransformationHelper.addTransformedView(1, this.mTitleView);
        this.mTransformationHelper.addTransformedView(2, this.mTextView);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTransformationHelper = new ViewTransformationHelper();
    }
}
