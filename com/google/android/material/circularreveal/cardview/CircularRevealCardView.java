package com.google.android.material.circularreveal.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import java.util.Objects;

public class CircularRevealCardView extends MaterialCardView implements CircularRevealWidget {
    public final CircularRevealHelper helper = new CircularRevealHelper(this);

    public final void buildCircularRevealCache() {
        Objects.requireNonNull(this.helper);
    }

    public final void destroyCircularRevealCache() {
        Objects.requireNonNull(this.helper);
    }

    public final void draw(Canvas canvas) {
        CircularRevealHelper circularRevealHelper = this.helper;
        if (circularRevealHelper != null) {
            circularRevealHelper.draw(canvas);
        } else {
            super.draw(canvas);
        }
    }

    public final int getCircularRevealScrimColor() {
        return this.helper.getCircularRevealScrimColor();
    }

    public final CircularRevealWidget.RevealInfo getRevealInfo() {
        return this.helper.getRevealInfo();
    }

    public final boolean isOpaque() {
        CircularRevealHelper circularRevealHelper = this.helper;
        if (circularRevealHelper != null) {
            return circularRevealHelper.isOpaque();
        }
        return super.isOpaque();
    }

    public final void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.helper.setCircularRevealOverlayDrawable(drawable);
    }

    public final void setCircularRevealScrimColor(int i) {
        this.helper.setCircularRevealScrimColor(i);
    }

    public final void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo) {
        this.helper.setRevealInfo(revealInfo);
    }

    public CircularRevealCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean actualIsOpaque() {
        return super.isOpaque();
    }

    public final void actualDraw(Canvas canvas) {
        super.draw(canvas);
    }
}
