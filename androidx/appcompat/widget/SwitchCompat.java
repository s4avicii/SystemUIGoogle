package androidx.appcompat.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.VelocityTracker;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.TextViewCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.viewsintegration.EmojiTextViewHelper;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;

public class SwitchCompat extends CompoundButton {
    public static final int[] CHECKED_STATE_SET = {16842912};
    public static final C00841 THUMB_POS = new Property<SwitchCompat, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            return Float.valueOf(((SwitchCompat) obj).mThumbPosition);
        }

        public final void set(Object obj, Object obj2) {
            SwitchCompat switchCompat = (SwitchCompat) obj;
            float floatValue = ((Float) obj2).floatValue();
            Objects.requireNonNull(switchCompat);
            switchCompat.mThumbPosition = floatValue;
            switchCompat.invalidate();
        }
    };
    public AppCompatEmojiTextHelper mAppCompatEmojiTextHelper;
    public EmojiCompatInitCallback mEmojiCompatInitCallback;
    public boolean mHasThumbTint;
    public boolean mHasThumbTintMode;
    public boolean mHasTrackTint;
    public boolean mHasTrackTintMode;
    public int mMinFlingVelocity;
    public StaticLayout mOffLayout;
    public StaticLayout mOnLayout;
    public ObjectAnimator mPositionAnimator;
    public boolean mShowText;
    public boolean mSplitTrack;
    public int mSwitchBottom;
    public int mSwitchHeight;
    public int mSwitchLeft;
    public int mSwitchMinWidth;
    public int mSwitchPadding;
    public int mSwitchRight;
    public int mSwitchTop;
    public AllCapsTransformationMethod mSwitchTransformationMethod;
    public int mSwitchWidth;
    public final Rect mTempRect;
    public ColorStateList mTextColors;
    public CharSequence mTextOff;
    public CharSequence mTextOffTransformed;
    public CharSequence mTextOn;
    public CharSequence mTextOnTransformed;
    public final TextPaint mTextPaint;
    public Drawable mThumbDrawable;
    public float mThumbPosition;
    public int mThumbTextPadding;
    public ColorStateList mThumbTintList;
    public PorterDuff.Mode mThumbTintMode;
    public int mThumbWidth;
    public int mTouchMode;
    public int mTouchSlop;
    public float mTouchX;
    public float mTouchY;
    public Drawable mTrackDrawable;
    public ColorStateList mTrackTintList;
    public PorterDuff.Mode mTrackTintMode;
    public VelocityTracker mVelocityTracker;

    public static class EmojiCompatInitCallback extends EmojiCompat.InitCallback {
        public final WeakReference mOuterWeakRef;

        public final void onFailed() {
            SwitchCompat switchCompat = (SwitchCompat) this.mOuterWeakRef.get();
            if (switchCompat != null) {
                switchCompat.setTextOnInternal(switchCompat.mTextOn);
                switchCompat.setTextOffInternal(switchCompat.mTextOff);
                switchCompat.requestLayout();
            }
        }

        public final void onInitialized() {
            SwitchCompat switchCompat = (SwitchCompat) this.mOuterWeakRef.get();
            if (switchCompat != null) {
                switchCompat.setTextOnInternal(switchCompat.mTextOn);
                switchCompat.setTextOffInternal(switchCompat.mTextOff);
                switchCompat.requestLayout();
            }
        }

        public EmojiCompatInitCallback(SwitchCompat switchCompat) {
            this.mOuterWeakRef = new WeakReference(switchCompat);
        }
    }

    public SwitchCompat(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.switchStyle);
    }

    public final void applyThumbTint() {
        Drawable drawable = this.mThumbDrawable;
        if (drawable == null) {
            return;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            Drawable mutate = drawable.mutate();
            this.mThumbDrawable = mutate;
            if (this.mHasThumbTint) {
                mutate.setTintList(this.mThumbTintList);
            }
            if (this.mHasThumbTintMode) {
                this.mThumbDrawable.setTintMode(this.mThumbTintMode);
            }
            if (this.mThumbDrawable.isStateful()) {
                this.mThumbDrawable.setState(getDrawableState());
            }
        }
    }

    public final void applyTrackTint() {
        Drawable drawable = this.mTrackDrawable;
        if (drawable == null) {
            return;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            Drawable mutate = drawable.mutate();
            this.mTrackDrawable = mutate;
            if (this.mHasTrackTint) {
                mutate.setTintList(this.mTrackTintList);
            }
            if (this.mHasTrackTintMode) {
                this.mTrackDrawable.setTintMode(this.mTrackTintMode);
            }
            if (this.mTrackDrawable.isStateful()) {
                this.mTrackDrawable.setState(getDrawableState());
            }
        }
    }

    public final void draw(Canvas canvas) {
        float f;
        Rect rect;
        int i;
        int i2;
        Rect rect2 = this.mTempRect;
        int i3 = this.mSwitchLeft;
        int i4 = this.mSwitchTop;
        int i5 = this.mSwitchRight;
        int i6 = this.mSwitchBottom;
        if (ViewUtils.isLayoutRtl(this)) {
            f = 1.0f - this.mThumbPosition;
        } else {
            f = this.mThumbPosition;
        }
        int thumbScrollRange = ((int) ((f * ((float) getThumbScrollRange())) + 0.5f)) + i3;
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            rect = DrawableUtils.getOpticalBounds(drawable);
        } else {
            rect = DrawableUtils.INSETS_NONE;
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect2);
            int i7 = rect2.left;
            thumbScrollRange += i7;
            if (rect != null) {
                int i8 = rect.left;
                if (i8 > i7) {
                    i3 += i8 - i7;
                }
                int i9 = rect.top;
                int i10 = rect2.top;
                if (i9 > i10) {
                    i = (i9 - i10) + i4;
                } else {
                    i = i4;
                }
                int i11 = rect.right;
                int i12 = rect2.right;
                if (i11 > i12) {
                    i5 -= i11 - i12;
                }
                int i13 = rect.bottom;
                int i14 = rect2.bottom;
                if (i13 > i14) {
                    i2 = i6 - (i13 - i14);
                    this.mTrackDrawable.setBounds(i3, i, i5, i2);
                }
            } else {
                i = i4;
            }
            i2 = i6;
            this.mTrackDrawable.setBounds(i3, i, i5, i2);
        }
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            drawable3.getPadding(rect2);
            int i15 = thumbScrollRange - rect2.left;
            int i16 = thumbScrollRange + this.mThumbWidth + rect2.right;
            this.mThumbDrawable.setBounds(i15, i4, i16, i6);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(i15, i4, i16, i6);
            }
        }
        super.draw(canvas);
    }

    public final AppCompatEmojiTextHelper getEmojiTextViewHelper() {
        if (this.mAppCompatEmojiTextHelper == null) {
            this.mAppCompatEmojiTextHelper = new AppCompatEmojiTextHelper(this);
        }
        return this.mAppCompatEmojiTextHelper;
    }

    public final int getThumbScrollRange() {
        Rect rect;
        Drawable drawable = this.mTrackDrawable;
        if (drawable == null) {
            return 0;
        }
        Rect rect2 = this.mTempRect;
        drawable.getPadding(rect2);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            rect = DrawableUtils.getOpticalBounds(drawable2);
        } else {
            rect = DrawableUtils.INSETS_NONE;
        }
        return ((((this.mSwitchWidth - this.mThumbWidth) - rect2.left) - rect2.right) - rect.left) - rect.right;
    }

    public final StaticLayout makeLayout(CharSequence charSequence) {
        int i;
        TextPaint textPaint = this.mTextPaint;
        if (charSequence != null) {
            i = (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, textPaint));
        } else {
            i = 0;
        }
        return new StaticLayout(charSequence, textPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOnTransformed);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOffTransformed);
            }
        }
        Rect rect = this.mTempRect;
        Drawable drawable = this.mThumbDrawable;
        int i6 = 0;
        if (drawable != null) {
            drawable.getPadding(rect);
            i4 = (this.mThumbDrawable.getIntrinsicWidth() - rect.left) - rect.right;
            i3 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            i4 = 0;
            i3 = 0;
        }
        if (this.mShowText) {
            i5 = (this.mThumbTextPadding * 2) + Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth());
        } else {
            i5 = 0;
        }
        this.mThumbWidth = Math.max(i5, i4);
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            i6 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i7 = rect.left;
        int i8 = rect.right;
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable3);
            i7 = Math.max(i7, opticalBounds.left);
            i8 = Math.max(i8, opticalBounds.right);
        }
        int max = Math.max(this.mSwitchMinWidth, (this.mThumbWidth * 2) + i7 + i8);
        int max2 = Math.max(i6, i3);
        this.mSwitchWidth = max;
        this.mSwitchHeight = max2;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < max2) {
            setMeasuredDimension(getMeasuredWidthAndState(), max2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0018, code lost:
        if (r0 != 3) goto L_0x0165;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            r10 = this;
            android.view.VelocityTracker r0 = r10.mVelocityTracker
            r0.addMovement(r11)
            int r0 = r11.getActionMasked()
            r1 = 1056964608(0x3f000000, float:0.5)
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L_0x0102
            r5 = 3
            r6 = 0
            r7 = 2
            if (r0 == r3) goto L_0x0093
            if (r0 == r7) goto L_0x001c
            if (r0 == r5) goto L_0x0093
            goto L_0x0165
        L_0x001c:
            int r0 = r10.mTouchMode
            if (r0 == r3) goto L_0x005f
            if (r0 == r7) goto L_0x0024
            goto L_0x0165
        L_0x0024:
            float r11 = r11.getX()
            int r0 = r10.getThumbScrollRange()
            float r1 = r10.mTouchX
            float r1 = r11 - r1
            if (r0 == 0) goto L_0x0035
            float r0 = (float) r0
            float r1 = r1 / r0
            goto L_0x003d
        L_0x0035:
            int r0 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x003b
            r1 = r2
            goto L_0x003d
        L_0x003b:
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
        L_0x003d:
            boolean r0 = androidx.appcompat.widget.ViewUtils.isLayoutRtl(r10)
            if (r0 == 0) goto L_0x0044
            float r1 = -r1
        L_0x0044:
            float r0 = r10.mThumbPosition
            float r1 = r1 + r0
            int r4 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x004d
            r2 = r6
            goto L_0x0053
        L_0x004d:
            int r4 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            r2 = r1
        L_0x0053:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x005e
            r10.mTouchX = r11
            r10.mThumbPosition = r2
            r10.invalidate()
        L_0x005e:
            return r3
        L_0x005f:
            float r0 = r11.getX()
            float r1 = r11.getY()
            float r2 = r10.mTouchX
            float r2 = r0 - r2
            float r2 = java.lang.Math.abs(r2)
            int r4 = r10.mTouchSlop
            float r4 = (float) r4
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x0085
            float r2 = r10.mTouchY
            float r2 = r1 - r2
            float r2 = java.lang.Math.abs(r2)
            int r4 = r10.mTouchSlop
            float r4 = (float) r4
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0165
        L_0x0085:
            r10.mTouchMode = r7
            android.view.ViewParent r11 = r10.getParent()
            r11.requestDisallowInterceptTouchEvent(r3)
            r10.mTouchX = r0
            r10.mTouchY = r1
            return r3
        L_0x0093:
            int r0 = r10.mTouchMode
            if (r0 != r7) goto L_0x00fa
            r10.mTouchMode = r4
            int r0 = r11.getAction()
            if (r0 != r3) goto L_0x00a7
            boolean r0 = r10.isEnabled()
            if (r0 == 0) goto L_0x00a7
            r0 = r3
            goto L_0x00a8
        L_0x00a7:
            r0 = r4
        L_0x00a8:
            boolean r2 = r10.isChecked()
            if (r0 == 0) goto L_0x00e0
            android.view.VelocityTracker r0 = r10.mVelocityTracker
            r7 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r7)
            android.view.VelocityTracker r0 = r10.mVelocityTracker
            float r0 = r0.getXVelocity()
            float r7 = java.lang.Math.abs(r0)
            int r8 = r10.mMinFlingVelocity
            float r8 = (float) r8
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x00d9
            boolean r1 = androidx.appcompat.widget.ViewUtils.isLayoutRtl(r10)
            if (r1 == 0) goto L_0x00d1
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 >= 0) goto L_0x00d7
            goto L_0x00d5
        L_0x00d1:
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x00d7
        L_0x00d5:
            r0 = r3
            goto L_0x00e1
        L_0x00d7:
            r0 = r4
            goto L_0x00e1
        L_0x00d9:
            float r0 = r10.mThumbPosition
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x00d7
            goto L_0x00d5
        L_0x00e0:
            r0 = r2
        L_0x00e1:
            if (r0 == r2) goto L_0x00e6
            r10.playSoundEffect(r4)
        L_0x00e6:
            r10.setChecked(r0)
            android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r11)
            r0.setAction(r5)
            super.onTouchEvent(r0)
            r0.recycle()
            super.onTouchEvent(r11)
            return r3
        L_0x00fa:
            r10.mTouchMode = r4
            android.view.VelocityTracker r0 = r10.mVelocityTracker
            r0.clear()
            goto L_0x0165
        L_0x0102:
            float r0 = r11.getX()
            float r5 = r11.getY()
            boolean r6 = r10.isEnabled()
            if (r6 == 0) goto L_0x0165
            android.graphics.drawable.Drawable r6 = r10.mThumbDrawable
            if (r6 != 0) goto L_0x0115
            goto L_0x015d
        L_0x0115:
            boolean r6 = androidx.appcompat.widget.ViewUtils.isLayoutRtl(r10)
            if (r6 == 0) goto L_0x011f
            float r6 = r10.mThumbPosition
            float r2 = r2 - r6
            goto L_0x0121
        L_0x011f:
            float r2 = r10.mThumbPosition
        L_0x0121:
            int r6 = r10.getThumbScrollRange()
            float r6 = (float) r6
            float r2 = r2 * r6
            float r2 = r2 + r1
            int r1 = (int) r2
            android.graphics.drawable.Drawable r2 = r10.mThumbDrawable
            android.graphics.Rect r6 = r10.mTempRect
            r2.getPadding(r6)
            int r2 = r10.mSwitchTop
            int r6 = r10.mTouchSlop
            int r2 = r2 - r6
            int r7 = r10.mSwitchLeft
            int r7 = r7 + r1
            int r7 = r7 - r6
            int r1 = r10.mThumbWidth
            int r1 = r1 + r7
            android.graphics.Rect r8 = r10.mTempRect
            int r9 = r8.left
            int r1 = r1 + r9
            int r8 = r8.right
            int r1 = r1 + r8
            int r1 = r1 + r6
            int r8 = r10.mSwitchBottom
            int r8 = r8 + r6
            float r6 = (float) r7
            int r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x015d
            float r1 = (float) r1
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 >= 0) goto L_0x015d
            float r1 = (float) r2
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x015d
            float r1 = (float) r8
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 >= 0) goto L_0x015d
            r4 = r3
        L_0x015d:
            if (r4 == 0) goto L_0x0165
            r10.mTouchMode = r3
            r10.mTouchX = r0
            r10.mTouchY = r5
        L_0x0165:
            boolean r10 = super.onTouchEvent(r11)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SwitchCompat.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void setSwitchTypeface(Typeface typeface) {
        if ((this.mTextPaint.getTypeface() != null && !this.mTextPaint.getTypeface().equals(typeface)) || (this.mTextPaint.getTypeface() == null && typeface != null)) {
            this.mTextPaint.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    public final void setTextOffInternal(CharSequence charSequence) {
        this.mTextOff = charSequence;
        AppCompatEmojiTextHelper emojiTextViewHelper = getEmojiTextViewHelper();
        AllCapsTransformationMethod allCapsTransformationMethod = this.mSwitchTransformationMethod;
        Objects.requireNonNull(emojiTextViewHelper);
        EmojiTextViewHelper emojiTextViewHelper2 = emojiTextViewHelper.mEmojiTextViewHelper;
        Objects.requireNonNull(emojiTextViewHelper2);
        TransformationMethod wrapTransformationMethod = emojiTextViewHelper2.mHelper.wrapTransformationMethod(allCapsTransformationMethod);
        if (wrapTransformationMethod != null) {
            charSequence = wrapTransformationMethod.getTransformation(charSequence, this);
        }
        this.mTextOffTransformed = charSequence;
        this.mOffLayout = null;
        if (this.mShowText) {
            setupEmojiCompatLoadCallback();
        }
    }

    public final void setTextOnInternal(CharSequence charSequence) {
        this.mTextOn = charSequence;
        AppCompatEmojiTextHelper emojiTextViewHelper = getEmojiTextViewHelper();
        AllCapsTransformationMethod allCapsTransformationMethod = this.mSwitchTransformationMethod;
        Objects.requireNonNull(emojiTextViewHelper);
        EmojiTextViewHelper emojiTextViewHelper2 = emojiTextViewHelper.mEmojiTextViewHelper;
        Objects.requireNonNull(emojiTextViewHelper2);
        TransformationMethod wrapTransformationMethod = emojiTextViewHelper2.mHelper.wrapTransformationMethod(allCapsTransformationMethod);
        if (wrapTransformationMethod != null) {
            charSequence = wrapTransformationMethod.getTransformation(charSequence, this);
        }
        this.mTextOnTransformed = charSequence;
        this.mOnLayout = null;
        if (this.mShowText) {
            setupEmojiCompatLoadCallback();
        }
    }

    public final void setupEmojiCompatLoadCallback() {
        boolean z;
        if (this.mEmojiCompatInitCallback == null) {
            AppCompatEmojiTextHelper appCompatEmojiTextHelper = this.mAppCompatEmojiTextHelper;
            Objects.requireNonNull(appCompatEmojiTextHelper);
            EmojiTextViewHelper emojiTextViewHelper = appCompatEmojiTextHelper.mEmojiTextViewHelper;
            Objects.requireNonNull(emojiTextViewHelper);
            if (emojiTextViewHelper.mHelper.isEnabled()) {
                if (EmojiCompat.sInstance != null) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    EmojiCompat emojiCompat = EmojiCompat.get();
                    int loadState = emojiCompat.getLoadState();
                    if (loadState == 3 || loadState == 0) {
                        EmojiCompatInitCallback emojiCompatInitCallback = new EmojiCompatInitCallback(this);
                        this.mEmojiCompatInitCallback = emojiCompatInitCallback;
                        emojiCompat.registerInitCallback(emojiCompatInitCallback);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0109, code lost:
        r9 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r13, (r9 = r7.getResourceId(3, 0)));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SwitchCompat(android.content.Context r13, android.util.AttributeSet r14, int r15) {
        /*
            r12 = this;
            r12.<init>(r13, r14, r15)
            r0 = 0
            r12.mThumbTintList = r0
            r12.mThumbTintMode = r0
            r1 = 0
            r12.mHasThumbTint = r1
            r12.mHasThumbTintMode = r1
            r12.mTrackTintList = r0
            r12.mTrackTintMode = r0
            r12.mHasTrackTint = r1
            r12.mHasTrackTintMode = r1
            android.view.VelocityTracker r2 = android.view.VelocityTracker.obtain()
            r12.mVelocityTracker = r2
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r12.mTempRect = r2
            android.content.Context r2 = r12.getContext()
            androidx.appcompat.widget.ThemeUtils.checkAppCompatTheme(r12, r2)
            android.text.TextPaint r2 = new android.text.TextPaint
            r3 = 1
            r2.<init>(r3)
            r12.mTextPaint = r2
            android.content.res.Resources r4 = r12.getResources()
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
            float r4 = r4.density
            r2.density = r4
            int[] r7 = androidx.appcompat.R$styleable.SwitchCompat
            androidx.appcompat.widget.TintTypedArray r4 = new androidx.appcompat.widget.TintTypedArray
            android.content.res.TypedArray r9 = r13.obtainStyledAttributes(r14, r7, r15, r1)
            r4.<init>(r13, r9)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r5 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            r11 = 0
            r5 = r12
            r6 = r13
            r8 = r14
            r10 = r15
            androidx.core.view.ViewCompat.Api29Impl.saveAttributeDataForStyleable(r5, r6, r7, r8, r9, r10, r11)
            r5 = 2
            android.graphics.drawable.Drawable r6 = r4.getDrawable(r5)
            r12.mThumbDrawable = r6
            if (r6 == 0) goto L_0x005e
            r6.setCallback(r12)
        L_0x005e:
            r6 = 11
            android.graphics.drawable.Drawable r6 = r4.getDrawable(r6)
            r12.mTrackDrawable = r6
            if (r6 == 0) goto L_0x006b
            r6.setCallback(r12)
        L_0x006b:
            java.lang.CharSequence r6 = r4.getText(r1)
            r12.setTextOnInternal(r6)
            java.lang.CharSequence r6 = r4.getText(r3)
            r12.setTextOffInternal(r6)
            r6 = 3
            boolean r7 = r4.getBoolean(r6, r3)
            r12.mShowText = r7
            r7 = 8
            int r7 = r4.getDimensionPixelSize(r7, r1)
            r12.mThumbTextPadding = r7
            r7 = 5
            int r7 = r4.getDimensionPixelSize(r7, r1)
            r12.mSwitchMinWidth = r7
            r7 = 6
            int r7 = r4.getDimensionPixelSize(r7, r1)
            r12.mSwitchPadding = r7
            r7 = 4
            boolean r7 = r4.getBoolean(r7, r1)
            r12.mSplitTrack = r7
            r7 = 9
            android.content.res.ColorStateList r7 = r4.getColorStateList(r7)
            if (r7 == 0) goto L_0x00a9
            r12.mThumbTintList = r7
            r12.mHasThumbTint = r3
        L_0x00a9:
            r7 = 10
            r8 = -1
            int r7 = r4.getInt(r7, r8)
            android.graphics.PorterDuff$Mode r7 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r7, r0)
            android.graphics.PorterDuff$Mode r9 = r12.mThumbTintMode
            if (r9 == r7) goto L_0x00bc
            r12.mThumbTintMode = r7
            r12.mHasThumbTintMode = r3
        L_0x00bc:
            boolean r7 = r12.mHasThumbTint
            if (r7 != 0) goto L_0x00c4
            boolean r7 = r12.mHasThumbTintMode
            if (r7 == 0) goto L_0x00c7
        L_0x00c4:
            r12.applyThumbTint()
        L_0x00c7:
            r7 = 12
            android.content.res.ColorStateList r7 = r4.getColorStateList(r7)
            if (r7 == 0) goto L_0x00d3
            r12.mTrackTintList = r7
            r12.mHasTrackTint = r3
        L_0x00d3:
            r7 = 13
            int r7 = r4.getInt(r7, r8)
            android.graphics.PorterDuff$Mode r7 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r7, r0)
            android.graphics.PorterDuff$Mode r9 = r12.mTrackTintMode
            if (r9 == r7) goto L_0x00e5
            r12.mTrackTintMode = r7
            r12.mHasTrackTintMode = r3
        L_0x00e5:
            boolean r7 = r12.mHasTrackTint
            if (r7 != 0) goto L_0x00ed
            boolean r7 = r12.mHasTrackTintMode
            if (r7 == 0) goto L_0x00f0
        L_0x00ed:
            r12.applyTrackTint()
        L_0x00f0:
            r7 = 7
            int r7 = r4.getResourceId(r7, r1)
            if (r7 == 0) goto L_0x01a6
            int[] r9 = androidx.appcompat.R$styleable.TextAppearance
            android.content.res.TypedArray r7 = r13.obtainStyledAttributes(r7, r9)
            boolean r9 = r7.hasValue(r6)
            if (r9 == 0) goto L_0x0110
            int r9 = r7.getResourceId(r6, r1)
            if (r9 == 0) goto L_0x0110
            android.content.res.ColorStateList r9 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r13, r9)
            if (r9 == 0) goto L_0x0110
            goto L_0x0114
        L_0x0110:
            android.content.res.ColorStateList r9 = r7.getColorStateList(r6)
        L_0x0114:
            if (r9 == 0) goto L_0x0119
            r12.mTextColors = r9
            goto L_0x011f
        L_0x0119:
            android.content.res.ColorStateList r9 = r12.getTextColors()
            r12.mTextColors = r9
        L_0x011f:
            int r9 = r7.getDimensionPixelSize(r1, r1)
            if (r9 == 0) goto L_0x0134
            float r9 = (float) r9
            float r10 = r2.getTextSize()
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 == 0) goto L_0x0134
            r2.setTextSize(r9)
            r12.requestLayout()
        L_0x0134:
            int r9 = r7.getInt(r3, r8)
            int r8 = r7.getInt(r5, r8)
            if (r9 == r3) goto L_0x014a
            if (r9 == r5) goto L_0x0147
            if (r9 == r6) goto L_0x0144
            r6 = r0
            goto L_0x014c
        L_0x0144:
            android.graphics.Typeface r6 = android.graphics.Typeface.MONOSPACE
            goto L_0x014c
        L_0x0147:
            android.graphics.Typeface r6 = android.graphics.Typeface.SERIF
            goto L_0x014c
        L_0x014a:
            android.graphics.Typeface r6 = android.graphics.Typeface.SANS_SERIF
        L_0x014c:
            r9 = 0
            if (r8 <= 0) goto L_0x017a
            if (r6 != 0) goto L_0x0156
            android.graphics.Typeface r6 = android.graphics.Typeface.defaultFromStyle(r8)
            goto L_0x015a
        L_0x0156:
            android.graphics.Typeface r6 = android.graphics.Typeface.create(r6, r8)
        L_0x015a:
            r12.setSwitchTypeface(r6)
            if (r6 == 0) goto L_0x0164
            int r6 = r6.getStyle()
            goto L_0x0165
        L_0x0164:
            r6 = r1
        L_0x0165:
            int r6 = ~r6
            r6 = r6 & r8
            r8 = r6 & 1
            if (r8 == 0) goto L_0x016c
            goto L_0x016d
        L_0x016c:
            r3 = r1
        L_0x016d:
            r2.setFakeBoldText(r3)
            r3 = r6 & 2
            if (r3 == 0) goto L_0x0176
            r9 = -1098907648(0xffffffffbe800000, float:-0.25)
        L_0x0176:
            r2.setTextSkewX(r9)
            goto L_0x0183
        L_0x017a:
            r2.setFakeBoldText(r1)
            r2.setTextSkewX(r9)
            r12.setSwitchTypeface(r6)
        L_0x0183:
            r2 = 14
            boolean r1 = r7.getBoolean(r2, r1)
            if (r1 == 0) goto L_0x0197
            androidx.appcompat.text.AllCapsTransformationMethod r0 = new androidx.appcompat.text.AllCapsTransformationMethod
            android.content.Context r1 = r12.getContext()
            r0.<init>(r1)
            r12.mSwitchTransformationMethod = r0
            goto L_0x0199
        L_0x0197:
            r12.mSwitchTransformationMethod = r0
        L_0x0199:
            java.lang.CharSequence r0 = r12.mTextOn
            r12.setTextOnInternal(r0)
            java.lang.CharSequence r0 = r12.mTextOff
            r12.setTextOffInternal(r0)
            r7.recycle()
        L_0x01a6:
            androidx.appcompat.widget.AppCompatTextHelper r0 = new androidx.appcompat.widget.AppCompatTextHelper
            r0.<init>(r12)
            r0.loadFromAttributes(r14, r15)
            r4.recycle()
            android.view.ViewConfiguration r13 = android.view.ViewConfiguration.get(r13)
            int r0 = r13.getScaledTouchSlop()
            r12.mTouchSlop = r0
            int r13 = r13.getScaledMinimumFlingVelocity()
            r12.mMinFlingVelocity = r13
            androidx.appcompat.widget.AppCompatEmojiTextHelper r13 = r12.getEmojiTextViewHelper()
            r13.loadFromAttributes(r14, r15)
            r12.refreshDrawableState()
            boolean r13 = r12.isChecked()
            r12.setChecked(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SwitchCompat.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public final void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mThumbDrawable;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    public final int getCompoundPaddingLeft() {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (!TextUtils.isEmpty(getText())) {
            return compoundPaddingLeft + this.mSwitchPadding;
        }
        return compoundPaddingLeft;
    }

    public final int getCompoundPaddingRight() {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (!TextUtils.isEmpty(getText())) {
            return compoundPaddingRight + this.mSwitchPadding;
        }
        return compoundPaddingRight;
    }

    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return TextViewCompat.unwrapCustomSelectionActionModeCallback(super.getCustomSelectionActionModeCallback());
    }

    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.mPositionAnimator;
        if (objectAnimator != null && objectAnimator.isStarted()) {
            this.mPositionAnimator.end();
            this.mPositionAnimator = null;
        }
    }

    public final void onDraw(Canvas canvas) {
        boolean z;
        StaticLayout staticLayout;
        int i;
        super.onDraw(canvas);
        Rect rect = this.mTempRect;
        Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i2 = this.mSwitchTop;
        int i3 = this.mSwitchBottom;
        int i4 = i2 + rect.top;
        int i5 = i3 - rect.bottom;
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable != null) {
            if (!this.mSplitTrack || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable2);
                drawable2.copyBounds(rect);
                rect.left += opticalBounds.left;
                rect.right -= opticalBounds.right;
                int save = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        if (this.mThumbPosition > 0.5f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            staticLayout = this.mOnLayout;
        } else {
            staticLayout = this.mOffLayout;
        }
        if (staticLayout != null) {
            int[] drawableState = getDrawableState();
            ColorStateList colorStateList = this.mTextColors;
            if (colorStateList != null) {
                this.mTextPaint.setColor(colorStateList.getColorForState(drawableState, 0));
            }
            this.mTextPaint.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                i = bounds.left + bounds.right;
            } else {
                i = getWidth();
            }
            canvas.translate((float) ((i / 2) - (staticLayout.getWidth() / 2)), (float) (((i4 + i5) / 2) - (staticLayout.getHeight() / 2)));
            staticLayout.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        super.onLayout(z, i, i2, i3, i4);
        int i10 = 0;
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect;
            Drawable drawable = this.mTrackDrawable;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            i5 = Math.max(0, opticalBounds.left - rect.left);
            i10 = Math.max(0, opticalBounds.right - rect.right);
        } else {
            i5 = 0;
        }
        if (ViewUtils.isLayoutRtl(this)) {
            i7 = getPaddingLeft() + i5;
            i6 = ((this.mSwitchWidth + i7) - i5) - i10;
        } else {
            i6 = (getWidth() - getPaddingRight()) - i10;
            i7 = (i6 - this.mSwitchWidth) + i5 + i10;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            int paddingTop = getPaddingTop();
            int i11 = this.mSwitchHeight;
            int height = (((getHeight() + paddingTop) - getPaddingBottom()) / 2) - (i11 / 2);
            int i12 = height;
            i8 = i11 + height;
            i9 = i12;
        } else if (gravity != 80) {
            i9 = getPaddingTop();
            i8 = this.mSwitchHeight + i9;
        } else {
            i8 = getHeight() - getPaddingBottom();
            i9 = i8 - this.mSwitchHeight;
        }
        this.mSwitchLeft = i7;
        this.mSwitchTop = i9;
        this.mSwitchBottom = i8;
        this.mSwitchRight = i6;
    }

    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        CharSequence charSequence;
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        if (isChecked()) {
            charSequence = this.mTextOn;
        } else {
            charSequence = this.mTextOff;
        }
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        getEmojiTextViewHelper().setAllCaps(z);
    }

    public final void setChecked(boolean z) {
        super.setChecked(z);
        boolean isChecked = isChecked();
        if (isChecked) {
            Object obj = this.mTextOn;
            if (obj == null) {
                obj = getResources().getString(C1777R.string.abc_capital_on);
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            new ViewCompat.AccessibilityViewProperty<CharSequence>(CharSequence.class) {
                public final void frameworkSet(View view, Object obj) {
                    Api30Impl.setStateDescription(view, (CharSequence) obj);
                }

                public final boolean shouldUpdate(Object obj, Object obj2) {
                    return !TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
                }

                public final Object frameworkGet(View view) {
                    return Api30Impl.getStateDescription(view);
                }
            }.set(this, obj);
        } else {
            Object obj2 = this.mTextOff;
            if (obj2 == null) {
                obj2 = getResources().getString(C1777R.string.abc_capital_off);
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            new ViewCompat.AccessibilityViewProperty<CharSequence>(CharSequence.class) {
                public final void frameworkSet(View view, Object obj) {
                    Api30Impl.setStateDescription(view, (CharSequence) obj);
                }

                public final boolean shouldUpdate(Object obj, Object obj2) {
                    return !TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
                }

                public final Object frameworkGet(View view) {
                    return Api30Impl.getStateDescription(view);
                }
            }.set(this, obj2);
        }
        float f = 1.0f;
        if (getWindowToken() == null || !ViewCompat.Api19Impl.isLaidOut(this)) {
            ObjectAnimator objectAnimator = this.mPositionAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            if (!isChecked) {
                f = 0.0f;
            }
            this.mThumbPosition = f;
            invalidate();
            return;
        }
        if (!isChecked) {
            f = 0.0f;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, THUMB_POS, new float[]{f});
        this.mPositionAnimator = ofFloat;
        ofFloat.setDuration(250);
        this.mPositionAnimator.setAutoCancel(true);
        this.mPositionAnimator.start();
    }

    public final void setFilters(InputFilter[] inputFilterArr) {
        super.setFilters(getEmojiTextViewHelper().getFilters(inputFilterArr));
    }

    public final void toggle() {
        setChecked(!isChecked());
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable) {
            return true;
        }
        return false;
    }

    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }
}
