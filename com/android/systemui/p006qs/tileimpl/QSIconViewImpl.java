package com.android.systemui.p006qs.tileimpl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.p006qs.AlphaControlledSignalTileView;
import com.android.systemui.p006qs.SlashDrawable;
import com.android.systemui.plugins.p005qs.QSIconView;
import com.android.systemui.plugins.p005qs.QSTile;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tileimpl.QSIconViewImpl */
public class QSIconViewImpl extends QSIconView {
    public boolean mAnimationEnabled = true;
    public final View mIcon;
    public int mIconSizePx;
    public QSTile.Icon mLastIcon;
    public int mState = -1;
    public int mTint;

    public final void disableAnimation() {
        this.mAnimationEnabled = false;
    }

    public int getIconMeasureMode() {
        return 1073741824;
    }

    public void setIcon(QSTile.State state, boolean z) {
        setIcon((ImageView) this.mIcon, state, z);
    }

    public View createIcon() {
        SlashImageView slashImageView = new SlashImageView(this.mContext);
        slashImageView.setId(16908294);
        slashImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return slashImageView;
    }

    public final void setIcon(ImageView imageView, QSTile.State state, boolean z) {
        if (state.disabledByPolicy) {
            imageView.setColorFilter(getContext().getColor(C1777R.color.qs_tile_disabled_color));
        } else {
            imageView.clearColorFilter();
        }
        int i = state.state;
        if (i != this.mState) {
            int iconColorForState = getIconColorForState(getContext(), i);
            this.mState = state.state;
            if (this.mTint != 0 && z) {
                if (this.mAnimationEnabled && imageView.isShown() && imageView.getDrawable() != null) {
                    int i2 = this.mTint;
                    final QSIconViewImpl$$ExternalSyntheticLambda1 qSIconViewImpl$$ExternalSyntheticLambda1 = new QSIconViewImpl$$ExternalSyntheticLambda1(this, imageView, state, z);
                    if (imageView instanceof AlphaControlledSignalTileView.AlphaControlledSlashImageView) {
                        AlphaControlledSignalTileView.AlphaControlledSlashImageView alphaControlledSlashImageView = (AlphaControlledSignalTileView.AlphaControlledSlashImageView) imageView;
                        ColorStateList valueOf = ColorStateList.valueOf(iconColorForState);
                        Objects.requireNonNull(alphaControlledSlashImageView);
                        alphaControlledSlashImageView.setImageTintList(valueOf);
                        SlashDrawable slashDrawable = alphaControlledSlashImageView.mSlash;
                        if (slashDrawable != null) {
                            ((AlphaControlledSignalTileView.AlphaControlledSlashDrawable) slashDrawable).setFinalTintList(valueOf);
                        }
                    }
                    if (!this.mAnimationEnabled || !ValueAnimator.areAnimatorsEnabled()) {
                        imageView.setImageTintList(ColorStateList.valueOf(iconColorForState));
                        qSIconViewImpl$$ExternalSyntheticLambda1.run();
                    } else {
                        float alpha = (float) Color.alpha(i2);
                        float alpha2 = (float) Color.alpha(iconColorForState);
                        float red = (float) Color.red(i2);
                        float red2 = (float) Color.red(iconColorForState);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                        ofFloat.setDuration(350);
                        ofFloat.addUpdateListener(new QSIconViewImpl$$ExternalSyntheticLambda0(alpha, alpha2, red, red2, imageView));
                        ofFloat.addListener(new AnimatorListenerAdapter() {
                            public final void onAnimationEnd(Animator animator) {
                                qSIconViewImpl$$ExternalSyntheticLambda1.run();
                            }
                        });
                        ofFloat.start();
                    }
                    this.mTint = iconColorForState;
                    return;
                }
            }
            if (imageView instanceof AlphaControlledSignalTileView.AlphaControlledSlashImageView) {
                AlphaControlledSignalTileView.AlphaControlledSlashImageView alphaControlledSlashImageView2 = (AlphaControlledSignalTileView.AlphaControlledSlashImageView) imageView;
                ColorStateList valueOf2 = ColorStateList.valueOf(iconColorForState);
                Objects.requireNonNull(alphaControlledSlashImageView2);
                alphaControlledSlashImageView2.setImageTintList(valueOf2);
                SlashDrawable slashDrawable2 = alphaControlledSlashImageView2.mSlash;
                if (slashDrawable2 != null) {
                    ((AlphaControlledSignalTileView.AlphaControlledSlashDrawable) slashDrawable2).setFinalTintList(valueOf2);
                }
            } else {
                imageView.setImageTintList(ColorStateList.valueOf(iconColorForState));
            }
            this.mTint = iconColorForState;
            updateIcon(imageView, state, z);
            return;
        }
        updateIcon(imageView, state, z);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("state=" + this.mState);
        sb.append(", tint=" + this.mTint);
        if (this.mLastIcon != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(", lastIcon=");
            m.append(this.mLastIcon.toString());
            sb.append(m.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0040, code lost:
        if (r11 != false) goto L_0x0044;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateIcon(android.widget.ImageView r9, com.android.systemui.plugins.p005qs.QSTile.State r10, boolean r11) {
        /*
            r8 = this;
            java.util.function.Supplier<com.android.systemui.plugins.qs.QSTile$Icon> r0 = r10.iconSupplier
            if (r0 == 0) goto L_0x000b
            java.lang.Object r0 = r0.get()
            com.android.systemui.plugins.qs.QSTile$Icon r0 = (com.android.systemui.plugins.p005qs.QSTile.Icon) r0
            goto L_0x000d
        L_0x000b:
            com.android.systemui.plugins.qs.QSTile$Icon r0 = r10.icon
        L_0x000d:
            r1 = 2131428654(0x7f0b052e, float:1.8478959E38)
            java.lang.Object r2 = r9.getTag(r1)
            boolean r2 = java.util.Objects.equals(r0, r2)
            r3 = 2131428658(0x7f0b0532, float:1.8478967E38)
            if (r2 == 0) goto L_0x0029
            com.android.systemui.plugins.qs.QSTile$SlashState r2 = r10.slash
            java.lang.Object r4 = r9.getTag(r3)
            boolean r2 = java.util.Objects.equals(r2, r4)
            if (r2 != 0) goto L_0x00ae
        L_0x0029:
            r2 = 1
            r4 = 0
            if (r11 == 0) goto L_0x0043
            boolean r11 = r8.mAnimationEnabled
            if (r11 == 0) goto L_0x003f
            boolean r11 = r9.isShown()
            if (r11 == 0) goto L_0x003f
            android.graphics.drawable.Drawable r11 = r9.getDrawable()
            if (r11 == 0) goto L_0x003f
            r11 = r2
            goto L_0x0040
        L_0x003f:
            r11 = r4
        L_0x0040:
            if (r11 == 0) goto L_0x0043
            goto L_0x0044
        L_0x0043:
            r2 = r4
        L_0x0044:
            r8.mLastIcon = r0
            r11 = 0
            if (r0 == 0) goto L_0x0059
            if (r2 == 0) goto L_0x0052
            android.content.Context r5 = r8.mContext
            android.graphics.drawable.Drawable r5 = r0.getDrawable(r5)
            goto L_0x005a
        L_0x0052:
            android.content.Context r5 = r8.mContext
            android.graphics.drawable.Drawable r5 = r0.getInvisibleDrawable(r5)
            goto L_0x005a
        L_0x0059:
            r5 = r11
        L_0x005a:
            if (r0 == 0) goto L_0x0061
            int r6 = r0.getPadding()
            goto L_0x0062
        L_0x0061:
            r6 = r4
        L_0x0062:
            if (r5 == 0) goto L_0x007c
            android.graphics.drawable.Drawable$ConstantState r7 = r5.getConstantState()
            if (r7 == 0) goto L_0x0072
            android.graphics.drawable.Drawable$ConstantState r5 = r5.getConstantState()
            android.graphics.drawable.Drawable r5 = r5.newDrawable()
        L_0x0072:
            r5.setAutoMirrored(r4)
            int r8 = r8.getLayoutDirection()
            r5.setLayoutDirection(r8)
        L_0x007c:
            boolean r8 = r9 instanceof com.android.systemui.p006qs.tileimpl.SlashImageView
            if (r8 == 0) goto L_0x008b
            r8 = r9
            com.android.systemui.qs.tileimpl.SlashImageView r8 = (com.android.systemui.p006qs.tileimpl.SlashImageView) r8
            r8.mAnimationEnabled = r2
            r8.mSlash = r11
            r8.setImageDrawable(r5)
            goto L_0x008e
        L_0x008b:
            r9.setImageDrawable(r5)
        L_0x008e:
            r9.setTag(r1, r0)
            com.android.systemui.plugins.qs.QSTile$SlashState r8 = r10.slash
            r9.setTag(r3, r8)
            r9.setPadding(r4, r6, r4, r6)
            boolean r8 = r5 instanceof android.graphics.drawable.Animatable2
            if (r8 == 0) goto L_0x00ae
            android.graphics.drawable.Animatable2 r5 = (android.graphics.drawable.Animatable2) r5
            r5.start()
            boolean r8 = r10.isTransient
            if (r8 == 0) goto L_0x00ae
            com.android.systemui.qs.tileimpl.QSIconViewImpl$1 r8 = new com.android.systemui.qs.tileimpl.QSIconViewImpl$1
            r8.<init>(r5)
            r5.registerAnimationCallback(r8)
        L_0x00ae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tileimpl.QSIconViewImpl.updateIcon(android.widget.ImageView, com.android.systemui.plugins.qs.QSTile$State, boolean):void");
    }

    public QSIconViewImpl(Context context) {
        super(context);
        this.mIconSizePx = context.getResources().getDimensionPixelSize(C1777R.dimen.qs_icon_size);
        View createIcon = createIcon();
        this.mIcon = createIcon;
        addView(createIcon);
    }

    public static int getIconColorForState(Context context, int i) {
        if (i == 0) {
            return Utils.applyAlpha(Utils.getColorAttrDefaultColor(context, 16842806));
        }
        if (i == 1) {
            return Utils.getColorAttrDefaultColor(context, 16842806);
        }
        if (i == 2) {
            return Utils.getColorAttrDefaultColor(context, 17957103);
        }
        KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Invalid state ", i, "QSIconView");
        return 0;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIconSizePx = getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_icon_size);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2;
        View view = this.mIcon;
        view.layout(measuredWidth, 0, view.getMeasuredWidth() + measuredWidth, view.getMeasuredHeight() + 0);
    }

    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(size, getIconMeasureMode()), View.MeasureSpec.makeMeasureSpec(this.mIconSizePx, 1073741824));
        setMeasuredDimension(size, this.mIcon.getMeasuredHeight());
    }

    public final View getIconView() {
        return this.mIcon;
    }
}
