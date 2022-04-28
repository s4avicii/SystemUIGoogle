package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.WeakHashMap;

public final class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    public WeakReference<View> anchorViewRef;
    public final Rect badgeBounds = new Rect();
    public float badgeCenterX;
    public float badgeCenterY;
    public float badgeRadius;
    public float badgeWidePadding;
    public float badgeWithTextRadius;
    public final WeakReference<Context> contextRef;
    public float cornerRadius;
    public WeakReference<FrameLayout> customBadgeParentRef;
    public float halfBadgeHeight;
    public float halfBadgeWidth;
    public int maxBadgeNumber;
    public final SavedState savedState;
    public final MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable();
    public final TextDrawableHelper textDrawableHelper;

    public static final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int additionalHorizontalOffset;
        public int additionalVerticalOffset;
        public int alpha = 255;
        public int backgroundColor;
        public int badgeGravity;
        public int badgeTextColor;
        public int contentDescriptionExceedsMaxBadgeNumberRes;
        public String contentDescriptionNumberless;
        public int contentDescriptionQuantityStrings;
        public int horizontalOffsetWithText;
        public int horizontalOffsetWithoutText;
        public boolean isVisible;
        public int maxCharacterCount;
        public int number = -1;
        public int verticalOffsetWithText;
        public int verticalOffsetWithoutText;

        public SavedState(Context context) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(2132017962, R$styleable.TextAppearance);
            obtainStyledAttributes.getDimension(0, 0.0f);
            ColorStateList colorStateList = MaterialResources.getColorStateList(context, obtainStyledAttributes, 3);
            MaterialResources.getColorStateList(context, obtainStyledAttributes, 4);
            MaterialResources.getColorStateList(context, obtainStyledAttributes, 5);
            obtainStyledAttributes.getInt(2, 0);
            obtainStyledAttributes.getInt(1, 1);
            int i = !obtainStyledAttributes.hasValue(12) ? 10 : 12;
            obtainStyledAttributes.getResourceId(i, 0);
            obtainStyledAttributes.getString(i);
            obtainStyledAttributes.getBoolean(14, false);
            MaterialResources.getColorStateList(context, obtainStyledAttributes, 6);
            obtainStyledAttributes.getFloat(7, 0.0f);
            obtainStyledAttributes.getFloat(8, 0.0f);
            obtainStyledAttributes.getFloat(9, 0.0f);
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(2132017962, R$styleable.MaterialTextAppearance);
            obtainStyledAttributes2.hasValue(0);
            obtainStyledAttributes2.getFloat(0, 0.0f);
            obtainStyledAttributes2.recycle();
            this.badgeTextColor = colorStateList.getDefaultColor();
            this.contentDescriptionNumberless = context.getString(C1777R.string.mtrl_badge_numberless_content_description);
            this.contentDescriptionQuantityStrings = C1777R.plurals.mtrl_badge_content_description;
            this.contentDescriptionExceedsMaxBadgeNumberRes = C1777R.string.mtrl_exceed_max_badge_number_content_description;
            this.isVisible = true;
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.backgroundColor);
            parcel.writeInt(this.badgeTextColor);
            parcel.writeInt(this.alpha);
            parcel.writeInt(this.number);
            parcel.writeInt(this.maxCharacterCount);
            parcel.writeString(this.contentDescriptionNumberless.toString());
            parcel.writeInt(this.contentDescriptionQuantityStrings);
            parcel.writeInt(this.badgeGravity);
            parcel.writeInt(this.horizontalOffsetWithoutText);
            parcel.writeInt(this.verticalOffsetWithoutText);
            parcel.writeInt(this.horizontalOffsetWithText);
            parcel.writeInt(this.verticalOffsetWithText);
            parcel.writeInt(this.additionalHorizontalOffset);
            parcel.writeInt(this.additionalVerticalOffset);
            parcel.writeInt(this.isVisible ? 1 : 0);
        }

        public SavedState(Parcel parcel) {
            this.backgroundColor = parcel.readInt();
            this.badgeTextColor = parcel.readInt();
            this.alpha = parcel.readInt();
            this.number = parcel.readInt();
            this.maxCharacterCount = parcel.readInt();
            this.contentDescriptionNumberless = parcel.readString();
            this.contentDescriptionQuantityStrings = parcel.readInt();
            this.badgeGravity = parcel.readInt();
            this.horizontalOffsetWithoutText = parcel.readInt();
            this.verticalOffsetWithoutText = parcel.readInt();
            this.horizontalOffsetWithText = parcel.readInt();
            this.verticalOffsetWithText = parcel.readInt();
            this.additionalHorizontalOffset = parcel.readInt();
            this.additionalVerticalOffset = parcel.readInt();
            this.isVisible = parcel.readInt() != 0;
        }
    }

    public final int getOpacity() {
        return -3;
    }

    public final boolean isStateful() {
        return false;
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }

    public final int getAlpha() {
        return this.savedState.alpha;
    }

    public final FrameLayout getCustomBadgeParent() {
        WeakReference<FrameLayout> weakReference = this.customBadgeParentRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public final int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    public final int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    public final boolean hasNumber() {
        if (this.savedState.number != -1) {
            return true;
        }
        return false;
    }

    public final void setAlpha(int i) {
        this.savedState.alpha = i;
        TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
        Objects.requireNonNull(textDrawableHelper2);
        textDrawableHelper2.textPaint.setAlpha(i);
        invalidateSelf();
    }

    public final void updateBadgeCoordinates(View view, FrameLayout frameLayout) {
        this.anchorViewRef = new WeakReference<>(view);
        this.customBadgeParentRef = new WeakReference<>(frameLayout);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        updateCenterAndBounds();
        invalidateSelf();
    }

    public final void updateCenterAndBounds() {
        View view;
        int i;
        int i2;
        int i3;
        float f;
        float f2;
        float f3;
        Context context = this.contextRef.get();
        WeakReference<View> weakReference = this.anchorViewRef;
        ViewGroup viewGroup = null;
        if (weakReference != null) {
            view = weakReference.get();
        } else {
            view = null;
        }
        if (context != null && view != null) {
            Rect rect = new Rect();
            rect.set(this.badgeBounds);
            Rect rect2 = new Rect();
            view.getDrawingRect(rect2);
            WeakReference<FrameLayout> weakReference2 = this.customBadgeParentRef;
            if (weakReference2 != null) {
                viewGroup = weakReference2.get();
            }
            if (viewGroup != null) {
                if (viewGroup == null) {
                    viewGroup = (ViewGroup) view.getParent();
                }
                viewGroup.offsetDescendantRectToMyCoords(view, rect2);
            }
            if (hasNumber()) {
                i = this.savedState.verticalOffsetWithText;
            } else {
                i = this.savedState.verticalOffsetWithoutText;
            }
            SavedState savedState2 = this.savedState;
            int i4 = i + savedState2.additionalVerticalOffset;
            int i5 = savedState2.badgeGravity;
            if (i5 == 8388691 || i5 == 8388693) {
                this.badgeCenterY = (float) (rect2.bottom - i4);
            } else {
                this.badgeCenterY = (float) (rect2.top + i4);
            }
            if (getNumber() <= 9) {
                if (!hasNumber()) {
                    f3 = this.badgeRadius;
                } else {
                    f3 = this.badgeWithTextRadius;
                }
                this.cornerRadius = f3;
                this.halfBadgeHeight = f3;
                this.halfBadgeWidth = f3;
            } else {
                float f4 = this.badgeWithTextRadius;
                this.cornerRadius = f4;
                this.halfBadgeHeight = f4;
                this.halfBadgeWidth = (this.textDrawableHelper.getTextWidth(getBadgeText()) / 2.0f) + this.badgeWidePadding;
            }
            Resources resources = context.getResources();
            if (hasNumber()) {
                i2 = C1777R.dimen.mtrl_badge_text_horizontal_edge_offset;
            } else {
                i2 = C1777R.dimen.mtrl_badge_horizontal_edge_offset;
            }
            int dimensionPixelSize = resources.getDimensionPixelSize(i2);
            if (hasNumber()) {
                i3 = this.savedState.horizontalOffsetWithText;
            } else {
                i3 = this.savedState.horizontalOffsetWithoutText;
            }
            SavedState savedState3 = this.savedState;
            int i6 = i3 + savedState3.additionalHorizontalOffset;
            int i7 = savedState3.badgeGravity;
            if (i7 == 8388659 || i7 == 8388691) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api17Impl.getLayoutDirection(view) == 0) {
                    f = (((float) rect2.left) - this.halfBadgeWidth) + ((float) dimensionPixelSize) + ((float) i6);
                } else {
                    f = ((((float) rect2.right) + this.halfBadgeWidth) - ((float) dimensionPixelSize)) - ((float) i6);
                }
                this.badgeCenterX = f;
            } else {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api17Impl.getLayoutDirection(view) == 0) {
                    f2 = ((((float) rect2.right) + this.halfBadgeWidth) - ((float) dimensionPixelSize)) - ((float) i6);
                } else {
                    f2 = (((float) rect2.left) - this.halfBadgeWidth) + ((float) dimensionPixelSize) + ((float) i6);
                }
                this.badgeCenterX = f2;
            }
            Rect rect3 = this.badgeBounds;
            float f5 = this.badgeCenterX;
            float f6 = this.badgeCenterY;
            float f7 = this.halfBadgeWidth;
            float f8 = this.halfBadgeHeight;
            rect3.set((int) (f5 - f7), (int) (f6 - f8), (int) (f5 + f7), (int) (f6 + f8));
            MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
            float f9 = this.cornerRadius;
            Objects.requireNonNull(materialShapeDrawable);
            materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.drawableState.shapeAppearanceModel.withCornerSize(f9));
            if (!rect.equals(this.badgeBounds)) {
                this.shapeDrawable.setBounds(this.badgeBounds);
            }
        }
    }

    public BadgeDrawable(Context context) {
        Context context2;
        WeakReference<Context> weakReference = new WeakReference<>(context);
        this.contextRef = weakReference;
        ThemeEnforcement.checkTheme(context, ThemeEnforcement.MATERIAL_CHECK_ATTRS, "Theme.MaterialComponents");
        Resources resources = context.getResources();
        this.badgeRadius = (float) resources.getDimensionPixelSize(C1777R.dimen.mtrl_badge_radius);
        this.badgeWidePadding = (float) resources.getDimensionPixelSize(C1777R.dimen.mtrl_badge_long_text_horizontal_padding);
        this.badgeWithTextRadius = (float) resources.getDimensionPixelSize(C1777R.dimen.mtrl_badge_with_text_radius);
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        Objects.requireNonNull(textDrawableHelper2);
        textDrawableHelper2.textPaint.setTextAlign(Paint.Align.CENTER);
        this.savedState = new SavedState(context);
        Context context3 = weakReference.get();
        if (context3 != null) {
            TextAppearance textAppearance = new TextAppearance(context3, 2132017962);
            Objects.requireNonNull(textDrawableHelper2);
            if (textDrawableHelper2.textAppearance != textAppearance && (context2 = weakReference.get()) != null) {
                textDrawableHelper2.setTextAppearance(textAppearance, context2);
                updateCenterAndBounds();
            }
        }
    }

    public final void draw(Canvas canvas) {
        if (!getBounds().isEmpty() && this.savedState.alpha != 0 && isVisible()) {
            this.shapeDrawable.draw(canvas);
            if (hasNumber()) {
                Rect rect = new Rect();
                String badgeText = getBadgeText();
                TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper2);
                textDrawableHelper2.textPaint.getTextBounds(badgeText, 0, badgeText.length(), rect);
                TextDrawableHelper textDrawableHelper3 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper3);
                canvas.drawText(badgeText, this.badgeCenterX, this.badgeCenterY + ((float) (rect.height() / 2)), textDrawableHelper3.textPaint);
            }
        }
    }

    public final String getBadgeText() {
        if (getNumber() <= this.maxBadgeNumber) {
            return NumberFormat.getInstance().format((long) getNumber());
        }
        Context context = this.contextRef.get();
        if (context == null) {
            return "";
        }
        return context.getString(C1777R.string.mtrl_exceed_max_badge_number_suffix, new Object[]{Integer.valueOf(this.maxBadgeNumber), "+"});
    }

    public final int getNumber() {
        if (!hasNumber()) {
            return 0;
        }
        return this.savedState.number;
    }

    public final boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    public final void onTextSizeChange() {
        invalidateSelf();
    }
}
