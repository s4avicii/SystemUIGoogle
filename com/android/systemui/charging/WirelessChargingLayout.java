package com.android.systemui.charging;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.charging.ChargingRippleView;
import com.android.systemui.statusbar.charging.RippleShader;
import java.text.NumberFormat;
import java.util.Objects;

public class WirelessChargingLayout extends FrameLayout {
    public ChargingRippleView mRippleView;

    public WirelessChargingLayout(Context context, int i, int i2) {
        super(context);
        init(context, i, i2);
    }

    public final void init(Context context, int i, int i2) {
        boolean z;
        float f;
        int i3 = i;
        int i4 = i2;
        if (i3 != -1) {
            z = true;
        } else {
            z = false;
        }
        View.inflate(new ContextThemeWrapper(context, 2132017467), C1777R.layout.wireless_charging_layout, this);
        TextView textView = (TextView) findViewById(C1777R.C1779id.wireless_charging_percentage);
        if (i4 != -1) {
            textView.setText(NumberFormat.getPercentInstance().format((double) (((float) i4) / 100.0f)));
            textView.setAlpha(0.0f);
        }
        long integer = (long) context.getResources().getInteger(C1777R.integer.wireless_charging_fade_offset);
        long integer2 = (long) context.getResources().getInteger(C1777R.integer.wireless_charging_fade_duration);
        float f2 = context.getResources().getFloat(C1777R.dimen.wireless_charging_anim_battery_level_text_size_start);
        float f3 = context.getResources().getFloat(C1777R.dimen.wireless_charging_anim_battery_level_text_size_end);
        if (z) {
            f = 0.75f;
        } else {
            f = 1.0f;
        }
        float f4 = f3 * f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "textSize", new float[]{f2, f4});
        ofFloat.setInterpolator(new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        ofFloat.setDuration((long) context.getResources().getInteger(C1777R.integer.wireless_charging_battery_level_text_scale_animation_duration));
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.0f, 1.0f});
        LinearInterpolator linearInterpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(linearInterpolator);
        String str = "textSize";
        float f5 = f4;
        ofFloat2.setDuration((long) context.getResources().getInteger(C1777R.integer.wireless_charging_battery_level_text_opacity_duration));
        boolean z2 = z;
        ofFloat2.setStartDelay((long) context.getResources().getInteger(C1777R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{1.0f, 0.0f});
        ofFloat3.setDuration(integer2);
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setStartDelay(integer);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3});
        ObjectAnimator ofArgb = ObjectAnimator.ofArgb(this, "backgroundColor", new int[]{0, 1275068416});
        ofArgb.setDuration(300);
        ofArgb.setInterpolator(linearInterpolator);
        ObjectAnimator ofArgb2 = ObjectAnimator.ofArgb(this, "backgroundColor", new int[]{1275068416, 0});
        ofArgb2.setDuration(300);
        ofArgb2.setInterpolator(linearInterpolator);
        ofArgb2.setStartDelay(1200);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(new Animator[]{ofArgb, ofArgb2});
        animatorSet2.start();
        ChargingRippleView chargingRippleView = (ChargingRippleView) findViewById(C1777R.C1779id.wireless_charging_ripple);
        this.mRippleView = chargingRippleView;
        chargingRippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public final void onViewDetachedFromWindow(View view) {
            }

            public final void onViewAttachedToWindow(View view) {
                ChargingRippleView chargingRippleView = WirelessChargingLayout.this.mRippleView;
                Objects.requireNonNull(chargingRippleView);
                chargingRippleView.duration = 1500;
                ChargingRippleView chargingRippleView2 = WirelessChargingLayout.this.mRippleView;
                Objects.requireNonNull(chargingRippleView2);
                chargingRippleView2.startRipple((Runnable) null);
                WirelessChargingLayout.this.mRippleView.removeOnAttachStateChangeListener(this);
            }
        });
        if (!z2) {
            animatorSet.start();
            return;
        }
        TextView textView2 = (TextView) findViewById(C1777R.C1779id.reverse_wireless_charging_percentage);
        textView2.setVisibility(0);
        textView2.setText(NumberFormat.getPercentInstance().format((double) (((float) i3) / 100.0f)));
        textView2.setAlpha(0.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(textView2, str, new float[]{f2, f5});
        ofFloat4.setInterpolator(new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        ofFloat4.setDuration((long) context.getResources().getInteger(C1777R.integer.wireless_charging_battery_level_text_scale_animation_duration));
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(textView2, "alpha", new float[]{0.0f, 1.0f});
        ofFloat5.setInterpolator(linearInterpolator);
        ofFloat5.setDuration((long) context.getResources().getInteger(C1777R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat5.setStartDelay((long) context.getResources().getInteger(C1777R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(textView2, "alpha", new float[]{1.0f, 0.0f});
        ofFloat6.setDuration(integer2);
        ofFloat6.setInterpolator(linearInterpolator);
        ofFloat6.setStartDelay(integer);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(new Animator[]{ofFloat4, ofFloat5, ofFloat6});
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.reverse_wireless_charging_icon);
        imageView.setVisibility(0);
        int round = Math.round(TypedValue.applyDimension(1, f5, getResources().getDisplayMetrics()));
        imageView.setPadding(round, 0, round, 0);
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(imageView, "alpha", new float[]{0.0f, 1.0f});
        ofFloat7.setInterpolator(linearInterpolator);
        ofFloat7.setDuration((long) context.getResources().getInteger(C1777R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat7.setStartDelay((long) context.getResources().getInteger(C1777R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(imageView, "alpha", new float[]{1.0f, 0.0f});
        ofFloat8.setDuration(integer2);
        ofFloat8.setInterpolator(linearInterpolator);
        ofFloat8.setStartDelay(integer);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(new Animator[]{ofFloat7, ofFloat8});
        animatorSet.start();
        animatorSet3.start();
        animatorSet4.start();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mRippleView != null) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            ChargingRippleView chargingRippleView = this.mRippleView;
            chargingRippleView.rippleShader.setColor(Utils.getColorAttr(chargingRippleView.getContext(), 16843829).getDefaultColor());
            ChargingRippleView chargingRippleView2 = this.mRippleView;
            PointF pointF = new PointF((float) (measuredWidth / 2), (float) (measuredHeight / 2));
            Objects.requireNonNull(chargingRippleView2);
            RippleShader rippleShader = chargingRippleView2.rippleShader;
            Objects.requireNonNull(rippleShader);
            rippleShader.setFloatUniform("in_origin", pointF.x, pointF.y);
            chargingRippleView2.origin = pointF;
            ChargingRippleView chargingRippleView3 = this.mRippleView;
            float max = ((float) Math.max(measuredWidth, measuredHeight)) * 0.5f;
            Objects.requireNonNull(chargingRippleView3);
            RippleShader rippleShader2 = chargingRippleView3.rippleShader;
            Objects.requireNonNull(rippleShader2);
            rippleShader2.radius = max;
            rippleShader2.setFloatUniform("in_maxRadius", max);
            chargingRippleView3.radius = max;
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    public WirelessChargingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, -1, -1);
    }
}
