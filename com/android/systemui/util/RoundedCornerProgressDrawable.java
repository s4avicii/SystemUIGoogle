package com.android.systemui.util;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import java.util.Objects;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RoundedCornerProgressDrawable.kt */
public final class RoundedCornerProgressDrawable extends InsetDrawable {

    /* compiled from: RoundedCornerProgressDrawable.kt */
    public static final class RoundedCornerState extends Drawable.ConstantState {
        public final Drawable.ConstantState wrappedState;

        public final boolean canApplyTheme() {
            return true;
        }

        public final Drawable newDrawable() {
            return newDrawable((Resources) null, (Resources.Theme) null);
        }

        public final int getChangingConfigurations() {
            return this.wrappedState.getChangingConfigurations();
        }

        public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
            Drawable newDrawable = this.wrappedState.newDrawable(resources, theme);
            Objects.requireNonNull(newDrawable, "null cannot be cast to non-null type android.graphics.drawable.DrawableWrapper");
            return new RoundedCornerProgressDrawable(((DrawableWrapper) newDrawable).getDrawable());
        }

        public RoundedCornerState(Drawable.ConstantState constantState) {
            this.wrappedState = constantState;
        }
    }

    public RoundedCornerProgressDrawable() {
        this((Drawable) null, 1, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ RoundedCornerProgressDrawable(Drawable drawable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : drawable);
    }

    public RoundedCornerProgressDrawable(Drawable drawable) {
        super(drawable, 0);
    }

    public final Drawable.ConstantState getConstantState() {
        Drawable.ConstantState constantState = super.getConstantState();
        Intrinsics.checkNotNull(constantState);
        return new RoundedCornerState(constantState);
    }

    public final boolean canApplyTheme() {
        boolean z;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            z = false;
        } else {
            z = drawable.canApplyTheme();
        }
        if (z || super.canApplyTheme()) {
            return true;
        }
        return false;
    }

    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | 4096;
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        onLevelChange(getLevel());
    }

    public final boolean onLayoutDirectionChanged(int i) {
        onLevelChange(getLevel());
        return super.onLayoutDirectionChanged(i);
    }

    public final boolean onLevelChange(int i) {
        Rect rect;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            rect = null;
        } else {
            rect = drawable.getBounds();
        }
        Intrinsics.checkNotNull(rect);
        int width = (((getBounds().width() - getBounds().height()) * i) / 10000) + getBounds().height();
        Drawable drawable2 = getDrawable();
        if (drawable2 != null) {
            drawable2.setBounds(getBounds().left, rect.top, getBounds().left + width, rect.bottom);
        }
        return super.onLevelChange(i);
    }
}
