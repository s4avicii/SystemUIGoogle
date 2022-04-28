package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.airbnb.lottie.LottieAnimationView;
import com.android.p012wm.shell.C1777R;

public class IllustrationPreference extends Preference {
    public final C06111 mAnimationCallback = new Animatable2.AnimationCallback() {
        public final void onAnimationEnd(Drawable drawable) {
            ((Animatable) drawable).start();
        }
    };
    public final C06122 mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback() {
        public final void onAnimationEnd(Drawable drawable) {
            ((Animatable) drawable).start();
        }
    };
    public int mImageResId;
    public int mMaxHeight = -1;

    public IllustrationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutResId = C1777R.layout.illustration_preference;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, 0, 0);
            this.mImageResId = obtainStyledAttributes.getResourceId(9, 0);
            obtainStyledAttributes.recycle();
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(C1777R.C1779id.background_view);
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.findViewById(C1777R.C1779id.middleground_layout);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) preferenceViewHolder.findViewById(C1777R.C1779id.lottie_view);
        int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
        int i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        FrameLayout frameLayout2 = (FrameLayout) preferenceViewHolder.findViewById(C1777R.C1779id.illustration_frame);
        ViewGroup.LayoutParams layoutParams = frameLayout2.getLayoutParams();
        if (i >= i2) {
            i = i2;
        }
        layoutParams.width = i;
        frameLayout2.setLayoutParams(layoutParams);
        if (this.mImageResId > 0) {
            Drawable drawable = lottieAnimationView.getDrawable();
            if (drawable instanceof Animatable) {
                if (drawable instanceof Animatable2) {
                    ((Animatable2) drawable).clearAnimationCallbacks();
                } else if (drawable instanceof Animatable2Compat) {
                    ((Animatable2Compat) drawable).clearAnimationCallbacks();
                }
                ((Animatable) drawable).stop();
            }
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setImageResource(this.mImageResId);
            Drawable drawable2 = lottieAnimationView.getDrawable();
            if (drawable2 == null) {
                int i3 = this.mImageResId;
                lottieAnimationView.setFailureListener(new IllustrationPreference$$ExternalSyntheticLambda0(i3));
                lottieAnimationView.setAnimation(i3);
                lottieAnimationView.setRepeatCount(-1);
                lottieAnimationView.playAnimation();
            } else if (drawable2 instanceof Animatable) {
                if (drawable2 instanceof Animatable2) {
                    ((Animatable2) drawable2).registerAnimationCallback(this.mAnimationCallback);
                } else if (drawable2 instanceof Animatable2Compat) {
                    ((Animatable2Compat) drawable2).registerAnimationCallback(this.mAnimationCallbackCompat);
                } else if (drawable2 instanceof AnimationDrawable) {
                    ((AnimationDrawable) drawable2).setOneShot(false);
                }
                ((Animatable) drawable2).start();
            }
        }
        if (this.mMaxHeight != -1) {
            Resources resources = imageView.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.settingslib_illustration_width);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(C1777R.dimen.settingslib_illustration_height);
            int min = Math.min(this.mMaxHeight, dimensionPixelSize2);
            imageView.setMaxHeight(min);
            lottieAnimationView.setMaxHeight(min);
            lottieAnimationView.setMaxWidth((int) (((float) min) * (((float) dimensionPixelSize) / ((float) dimensionPixelSize2))));
        }
        frameLayout.removeAllViews();
        frameLayout.setVisibility(8);
    }
}
