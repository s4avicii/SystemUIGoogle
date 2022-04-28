package com.android.systemui.statusbar.charging;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.preference.R$id;
import java.util.Objects;

/* compiled from: WiredChargingRippleController.kt */
public final class WiredChargingRippleController$startRipple$1 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ WiredChargingRippleController this$0;

    public final void onViewDetachedFromWindow(View view) {
    }

    public WiredChargingRippleController$startRipple$1(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
    }

    public final void onViewAttachedToWindow(View view) {
        PointF pointF;
        WiredChargingRippleController wiredChargingRippleController = this.this$0;
        Objects.requireNonNull(wiredChargingRippleController);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wiredChargingRippleController.context.getDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        ChargingRippleView chargingRippleView = wiredChargingRippleController.rippleView;
        float max = (float) Integer.max(i, i2);
        Objects.requireNonNull(chargingRippleView);
        RippleShader rippleShader = chargingRippleView.rippleShader;
        Objects.requireNonNull(rippleShader);
        rippleShader.radius = max;
        rippleShader.setFloatUniform("in_maxRadius", max);
        chargingRippleView.radius = max;
        ChargingRippleView chargingRippleView2 = wiredChargingRippleController.rippleView;
        int rotation = R$id.getRotation(wiredChargingRippleController.context);
        if (rotation == 1) {
            pointF = new PointF(((float) i) * wiredChargingRippleController.normalizedPortPosY, (((float) 1) - wiredChargingRippleController.normalizedPortPosX) * ((float) i2));
        } else if (rotation == 2) {
            float f = (float) 1;
            pointF = new PointF((f - wiredChargingRippleController.normalizedPortPosX) * ((float) i), (f - wiredChargingRippleController.normalizedPortPosY) * ((float) i2));
        } else if (rotation != 3) {
            pointF = new PointF(((float) i) * wiredChargingRippleController.normalizedPortPosX, ((float) i2) * wiredChargingRippleController.normalizedPortPosY);
        } else {
            pointF = new PointF((((float) 1) - wiredChargingRippleController.normalizedPortPosY) * ((float) i), ((float) i2) * wiredChargingRippleController.normalizedPortPosX);
        }
        Objects.requireNonNull(chargingRippleView2);
        RippleShader rippleShader2 = chargingRippleView2.rippleShader;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_origin", pointF.x, pointF.y);
        chargingRippleView2.origin = pointF;
        WiredChargingRippleController wiredChargingRippleController2 = this.this$0;
        Objects.requireNonNull(wiredChargingRippleController2);
        wiredChargingRippleController2.rippleView.startRipple(new C1195xfe18c88d(this.this$0));
        WiredChargingRippleController wiredChargingRippleController3 = this.this$0;
        Objects.requireNonNull(wiredChargingRippleController3);
        wiredChargingRippleController3.rippleView.removeOnAttachStateChangeListener(this);
    }
}
