package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

public class DrawableWrapper extends Drawable implements Drawable.Callback {
    public Drawable mDrawable;

    public void draw(Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    public final int getChangingConfigurations() {
        return this.mDrawable.getChangingConfigurations();
    }

    public final Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public final int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public final int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public final int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public final int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public final int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public final boolean getPadding(Rect rect) {
        return this.mDrawable.getPadding(rect);
    }

    public final int[] getState() {
        return this.mDrawable.getState();
    }

    public final Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    public final boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    public final boolean isStateful() {
        return this.mDrawable.isStateful();
    }

    public final void jumpToCurrentState() {
        this.mDrawable.jumpToCurrentState();
    }

    public final void onBoundsChange(Rect rect) {
        this.mDrawable.setBounds(rect);
    }

    public final boolean onLevelChange(int i) {
        return this.mDrawable.setLevel(i);
    }

    public final void setAlpha(int i) {
        this.mDrawable.setAlpha(i);
    }

    public final void setAutoMirrored(boolean z) {
        this.mDrawable.setAutoMirrored(z);
    }

    public final void setChangingConfigurations(int i) {
        this.mDrawable.setChangingConfigurations(i);
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
    }

    public final void setDither(boolean z) {
        this.mDrawable.setDither(z);
    }

    public final void setFilterBitmap(boolean z) {
        this.mDrawable.setFilterBitmap(z);
    }

    public void setHotspot(float f, float f2) {
        this.mDrawable.setHotspot(f, f2);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.mDrawable.setHotspotBounds(i, i2, i3, i4);
    }

    public boolean setState(int[] iArr) {
        return this.mDrawable.setState(iArr);
    }

    public final void setTint(int i) {
        this.mDrawable.setTint(i);
    }

    public final void setTintList(ColorStateList colorStateList) {
        this.mDrawable.setTintList(colorStateList);
    }

    public final void setTintMode(PorterDuff.Mode mode) {
        this.mDrawable.setTintMode(mode);
    }

    public DrawableWrapper(Drawable drawable) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        if (super.setVisible(z, z2) || this.mDrawable.setVisible(z, z2)) {
            return true;
        }
        return false;
    }

    public final void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }
}
