package androidx.leanback.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import androidx.leanback.R$styleable;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;

public class BaseCardView extends FrameLayout {
    public static final int[] LB_PRESSED_STATE_SET = {16842919};
    public final int mActivatedAnimDuration;
    public AnimationBase mAnim;
    public final C02171 mAnimationTrigger;
    public int mCardType;
    public boolean mDelaySelectedAnim;
    public ArrayList<View> mExtraViewList;
    public float mInfoAlpha;
    public float mInfoOffset;
    public ArrayList<View> mInfoViewList;
    public float mInfoVisFraction;
    public int mInfoVisibility;
    public ArrayList<View> mMainViewList;
    public int mMeasuredHeight;
    public int mMeasuredWidth;
    public final int mSelectedAnimDuration;
    public int mSelectedAnimationDelay;

    public class AnimationBase extends Animation {
        public AnimationBase() {
        }

        public final void mockEnd() {
            applyTransformation(1.0f, (Transformation) null);
            BaseCardView.this.cancelAnimations();
        }

        public final void mockStart() {
            getTransformation(0, (Transformation) null);
        }
    }

    public final class InfoAlphaAnimation extends AnimationBase {
        public float mDelta;
        public float mStartValue;

        public InfoAlphaAnimation(float f, float f2) {
            super();
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView.this.mInfoAlpha = (f * this.mDelta) + this.mStartValue;
            for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                BaseCardView.this.mInfoViewList.get(i).setAlpha(BaseCardView.this.mInfoAlpha);
            }
        }
    }

    public final class InfoHeightAnimation extends AnimationBase {
        public float mDelta;
        public float mStartValue;

        public InfoHeightAnimation(float f, float f2) {
            super();
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoVisFraction = (f * this.mDelta) + this.mStartValue;
            baseCardView.requestLayout();
        }
    }

    public final class InfoOffsetAnimation extends AnimationBase {
        public float mDelta;
        public float mStartValue;

        public InfoOffsetAnimation(float f, float f2) {
            super();
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoOffset = (f * this.mDelta) + this.mStartValue;
            baseCardView.requestLayout();
        }
    }

    public BaseCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.baseCardViewStyle);
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        float paddingTop = (float) getPaddingTop();
        for (int i5 = 0; i5 < this.mMainViewList.size(); i5++) {
            View view = this.mMainViewList.get(i5);
            if (view.getVisibility() != 8) {
                view.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) (((float) view.getMeasuredHeight()) + paddingTop));
                paddingTop += (float) view.getMeasuredHeight();
            }
        }
        boolean z3 = true;
        if (this.mCardType != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            float f = 0.0f;
            for (int i6 = 0; i6 < this.mInfoViewList.size(); i6++) {
                f += (float) this.mInfoViewList.get(i6).getMeasuredHeight();
            }
            int i7 = this.mCardType;
            if (i7 == 1) {
                paddingTop -= f;
                if (paddingTop < 0.0f) {
                    paddingTop = 0.0f;
                }
            } else if (i7 != 2) {
                paddingTop -= this.mInfoOffset;
            } else if (this.mInfoVisibility == 2) {
                f *= this.mInfoVisFraction;
            }
            for (int i8 = 0; i8 < this.mInfoViewList.size(); i8++) {
                View view2 = this.mInfoViewList.get(i8);
                if (view2.getVisibility() != 8) {
                    int measuredHeight = view2.getMeasuredHeight();
                    if (((float) measuredHeight) > f) {
                        measuredHeight = (int) f;
                    }
                    float f2 = (float) measuredHeight;
                    paddingTop += f2;
                    view2.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) paddingTop);
                    f -= f2;
                    if (f <= 0.0f) {
                        break;
                    }
                }
            }
            if (this.mCardType != 3) {
                z3 = false;
            }
            if (z3) {
                for (int i9 = 0; i9 < this.mExtraViewList.size(); i9++) {
                    View view3 = this.mExtraViewList.get(i9);
                    if (view3.getVisibility() != 8) {
                        view3.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) (((float) view3.getMeasuredHeight()) + paddingTop));
                        paddingTop += (float) view3.getMeasuredHeight();
                    }
                }
            }
        }
        onSizeChanged(0, 0, i3 - i, i4 - i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0034, code lost:
        if (r0.mInfoVisFraction > 0.0f) goto L_0x0043;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0162  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x009c A[EDGE_INSN: B:99:0x009c->B:47:0x009c ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r16, int r17) {
        /*
            r15 = this;
            r0 = r15
            r1 = 0
            r0.mMeasuredWidth = r1
            r0.mMeasuredHeight = r1
            java.util.ArrayList<android.view.View> r2 = r0.mMainViewList
            r2.clear()
            java.util.ArrayList<android.view.View> r2 = r0.mInfoViewList
            r2.clear()
            java.util.ArrayList<android.view.View> r2 = r0.mExtraViewList
            r2.clear()
            int r2 = r15.getChildCount()
            int r3 = r0.mCardType
            r4 = 1
            if (r3 == 0) goto L_0x0020
            r5 = r4
            goto L_0x0021
        L_0x0020:
            r5 = r1
        L_0x0021:
            r6 = 0
            r7 = 2
            if (r5 == 0) goto L_0x0048
            int r5 = r0.mInfoVisibility
            if (r5 == 0) goto L_0x0043
            if (r5 == r4) goto L_0x003e
            if (r5 == r7) goto L_0x002e
            goto L_0x0037
        L_0x002e:
            if (r3 != r7) goto L_0x0039
            float r3 = r0.mInfoVisFraction
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x0037
            goto L_0x0043
        L_0x0037:
            r3 = r1
            goto L_0x0044
        L_0x0039:
            boolean r3 = r15.isSelected()
            goto L_0x0044
        L_0x003e:
            boolean r3 = r15.isActivated()
            goto L_0x0044
        L_0x0043:
            r3 = r4
        L_0x0044:
            if (r3 == 0) goto L_0x0048
            r3 = r4
            goto L_0x0049
        L_0x0048:
            r3 = r1
        L_0x0049:
            int r5 = r0.mCardType
            r8 = 3
            if (r5 != r8) goto L_0x0050
            r5 = r4
            goto L_0x0051
        L_0x0050:
            r5 = r1
        L_0x0051:
            if (r5 == 0) goto L_0x005b
            float r5 = r0.mInfoOffset
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x005b
            r5 = r4
            goto L_0x005c
        L_0x005b:
            r5 = r1
        L_0x005c:
            r9 = r1
        L_0x005d:
            r10 = 8
            if (r9 >= r2) goto L_0x009c
            android.view.View r11 = r15.getChildAt(r9)
            if (r11 != 0) goto L_0x0068
            goto L_0x0099
        L_0x0068:
            android.view.ViewGroup$LayoutParams r12 = r11.getLayoutParams()
            androidx.leanback.widget.BaseCardView$LayoutParams r12 = (androidx.leanback.widget.BaseCardView.LayoutParams) r12
            int r12 = r12.viewType
            if (r12 != r4) goto L_0x0083
            float r12 = r0.mInfoAlpha
            r11.setAlpha(r12)
            java.util.ArrayList<android.view.View> r12 = r0.mInfoViewList
            r12.add(r11)
            if (r3 == 0) goto L_0x007f
            r10 = r1
        L_0x007f:
            r11.setVisibility(r10)
            goto L_0x0099
        L_0x0083:
            if (r12 != r7) goto L_0x0091
            java.util.ArrayList<android.view.View> r12 = r0.mExtraViewList
            r12.add(r11)
            if (r5 == 0) goto L_0x008d
            r10 = r1
        L_0x008d:
            r11.setVisibility(r10)
            goto L_0x0099
        L_0x0091:
            java.util.ArrayList<android.view.View> r10 = r0.mMainViewList
            r10.add(r11)
            r11.setVisibility(r1)
        L_0x0099:
            int r9 = r9 + 1
            goto L_0x005d
        L_0x009c:
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r1)
            r3 = r1
            r5 = r3
            r9 = r5
        L_0x00a3:
            java.util.ArrayList<android.view.View> r11 = r0.mMainViewList
            int r11 = r11.size()
            if (r3 >= r11) goto L_0x00d8
            java.util.ArrayList<android.view.View> r11 = r0.mMainViewList
            java.lang.Object r11 = r11.get(r3)
            android.view.View r11 = (android.view.View) r11
            int r12 = r11.getVisibility()
            if (r12 == r10) goto L_0x00d5
            r15.measureChild(r11, r2, r2)
            int r12 = r0.mMeasuredWidth
            int r13 = r11.getMeasuredWidth()
            int r12 = java.lang.Math.max(r12, r13)
            r0.mMeasuredWidth = r12
            int r12 = r11.getMeasuredHeight()
            int r5 = r5 + r12
            int r11 = r11.getMeasuredState()
            int r9 = android.view.View.combineMeasuredStates(r9, r11)
        L_0x00d5:
            int r3 = r3 + 1
            goto L_0x00a3
        L_0x00d8:
            int r3 = r0.mMeasuredWidth
            int r3 = r3 / r7
            float r3 = (float) r3
            r15.setPivotX(r3)
            int r3 = r5 / 2
            float r3 = (float) r3
            r15.setPivotY(r3)
            int r3 = r0.mMeasuredWidth
            r11 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r11)
            int r11 = r0.mCardType
            if (r11 == 0) goto L_0x00f3
            r11 = r4
            goto L_0x00f4
        L_0x00f3:
            r11 = r1
        L_0x00f4:
            if (r11 == 0) goto L_0x015c
            r11 = r1
            r12 = r11
        L_0x00f8:
            java.util.ArrayList<android.view.View> r13 = r0.mInfoViewList
            int r13 = r13.size()
            if (r11 >= r13) goto L_0x0126
            java.util.ArrayList<android.view.View> r13 = r0.mInfoViewList
            java.lang.Object r13 = r13.get(r11)
            android.view.View r13 = (android.view.View) r13
            int r14 = r13.getVisibility()
            if (r14 == r10) goto L_0x0123
            r15.measureChild(r13, r3, r2)
            int r14 = r0.mCardType
            if (r14 == r4) goto L_0x011b
            int r14 = r13.getMeasuredHeight()
            int r14 = r14 + r12
            r12 = r14
        L_0x011b:
            int r13 = r13.getMeasuredState()
            int r9 = android.view.View.combineMeasuredStates(r9, r13)
        L_0x0123:
            int r11 = r11 + 1
            goto L_0x00f8
        L_0x0126:
            int r11 = r0.mCardType
            if (r11 != r8) goto L_0x012c
            r8 = r4
            goto L_0x012d
        L_0x012c:
            r8 = r1
        L_0x012d:
            if (r8 == 0) goto L_0x015a
            r8 = r1
            r11 = r8
        L_0x0131:
            java.util.ArrayList<android.view.View> r13 = r0.mExtraViewList
            int r13 = r13.size()
            if (r8 >= r13) goto L_0x015e
            java.util.ArrayList<android.view.View> r13 = r0.mExtraViewList
            java.lang.Object r13 = r13.get(r8)
            android.view.View r13 = (android.view.View) r13
            int r14 = r13.getVisibility()
            if (r14 == r10) goto L_0x0157
            r15.measureChild(r13, r3, r2)
            int r14 = r13.getMeasuredHeight()
            int r11 = r11 + r14
            int r13 = r13.getMeasuredState()
            int r9 = android.view.View.combineMeasuredStates(r9, r13)
        L_0x0157:
            int r8 = r8 + 1
            goto L_0x0131
        L_0x015a:
            r11 = r1
            goto L_0x015e
        L_0x015c:
            r11 = r1
            r12 = r11
        L_0x015e:
            int r2 = r0.mCardType
            if (r2 == 0) goto L_0x0164
            r2 = r4
            goto L_0x0165
        L_0x0164:
            r2 = r1
        L_0x0165:
            if (r2 == 0) goto L_0x016c
            int r2 = r0.mInfoVisibility
            if (r2 != r7) goto L_0x016c
            r1 = r4
        L_0x016c:
            float r2 = (float) r5
            float r3 = (float) r12
            if (r1 == 0) goto L_0x0173
            float r4 = r0.mInfoVisFraction
            float r3 = r3 * r4
        L_0x0173:
            float r2 = r2 + r3
            float r3 = (float) r11
            float r2 = r2 + r3
            if (r1 == 0) goto L_0x0179
            goto L_0x017b
        L_0x0179:
            float r6 = r0.mInfoOffset
        L_0x017b:
            float r2 = r2 - r6
            int r1 = (int) r2
            r0.mMeasuredHeight = r1
            int r1 = r0.mMeasuredWidth
            int r2 = r15.getPaddingLeft()
            int r2 = r2 + r1
            int r1 = r15.getPaddingRight()
            int r1 = r1 + r2
            r2 = r16
            int r1 = android.view.View.resolveSizeAndState(r1, r2, r9)
            int r2 = r0.mMeasuredHeight
            int r3 = r15.getPaddingTop()
            int r3 = r3 + r2
            int r2 = r15.getPaddingBottom()
            int r2 = r2 + r3
            int r3 = r9 << 16
            r4 = r17
            int r2 = android.view.View.resolveSizeAndState(r2, r4, r3)
            r15.setMeasuredDimension(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.BaseCardView.onMeasure(int, int):void");
    }

    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    /* JADX INFO: finally extract failed */
    @SuppressLint({"CustomViewStyleable"})
    public BaseCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimationTrigger = new Runnable() {
            public final void run() {
                BaseCardView.this.animateInfoOffset(true);
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbBaseCardView, i, 0);
        try {
            this.mCardType = obtainStyledAttributes.getInteger(3, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(2);
            if (drawable != null) {
                setForeground(drawable);
            }
            Drawable drawable2 = obtainStyledAttributes.getDrawable(1);
            if (drawable2 != null) {
                setBackground(drawable2);
            }
            this.mInfoVisibility = obtainStyledAttributes.getInteger(5, 1);
            int integer = obtainStyledAttributes.getInteger(4, 2);
            int i2 = this.mInfoVisibility;
            this.mSelectedAnimationDelay = obtainStyledAttributes.getInteger(6, getResources().getInteger(C1777R.integer.lb_card_selected_animation_delay));
            this.mSelectedAnimDuration = obtainStyledAttributes.getInteger(7, getResources().getInteger(C1777R.integer.lb_card_selected_animation_duration));
            this.mActivatedAnimDuration = obtainStyledAttributes.getInteger(0, getResources().getInteger(C1777R.integer.lb_card_activated_animation_duration));
            obtainStyledAttributes.recycle();
            this.mDelaySelectedAnim = true;
            this.mMainViewList = new ArrayList<>();
            this.mInfoViewList = new ArrayList<>();
            this.mExtraViewList = new ArrayList<>();
            float f = 0.0f;
            this.mInfoOffset = 0.0f;
            this.mInfoVisFraction = (this.mCardType == 2 && this.mInfoVisibility == 2 && !isSelected()) ? 0.0f : 1.0f;
            this.mInfoAlpha = (this.mCardType == 1 && this.mInfoVisibility == 2 && !isSelected()) ? f : 1.0f;
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void cancelAnimations() {
        AnimationBase animationBase = this.mAnim;
        if (animationBase != null) {
            animationBase.cancel();
            this.mAnim = null;
            clearAnimation();
        }
    }

    /* renamed from: generateDefaultLayoutParams  reason: collision with other method in class */
    public final FrameLayout.LayoutParams m153generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* renamed from: generateLayoutParams  reason: collision with other method in class */
    public final FrameLayout.LayoutParams m154generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void setInfoViewVisibility(boolean z) {
        float f;
        int i;
        int i2 = this.mCardType;
        float f2 = 0.0f;
        if (i2 == 3) {
            if (z) {
                for (int i3 = 0; i3 < this.mInfoViewList.size(); i3++) {
                    this.mInfoViewList.get(i3).setVisibility(0);
                }
                return;
            }
            for (int i4 = 0; i4 < this.mInfoViewList.size(); i4++) {
                this.mInfoViewList.get(i4).setVisibility(8);
            }
            for (int i5 = 0; i5 < this.mExtraViewList.size(); i5++) {
                this.mExtraViewList.get(i5).setVisibility(8);
            }
            this.mInfoOffset = 0.0f;
        } else if (i2 == 2) {
            if (this.mInfoVisibility == 2) {
                cancelAnimations();
                if (z) {
                    for (int i6 = 0; i6 < this.mInfoViewList.size(); i6++) {
                        this.mInfoViewList.get(i6).setVisibility(0);
                    }
                }
                if (z) {
                    f2 = 1.0f;
                }
                if (this.mInfoVisFraction != f2) {
                    InfoHeightAnimation infoHeightAnimation = new InfoHeightAnimation(this.mInfoVisFraction, f2);
                    this.mAnim = infoHeightAnimation;
                    infoHeightAnimation.setDuration((long) this.mSelectedAnimDuration);
                    this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                    this.mAnim.setAnimationListener(new Animation.AnimationListener() {
                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                        }

                        public final void onAnimationEnd(Animation animation) {
                            if (BaseCardView.this.mInfoVisFraction == 0.0f) {
                                for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                                    BaseCardView.this.mInfoViewList.get(i).setVisibility(8);
                                }
                            }
                        }
                    });
                    startAnimation(this.mAnim);
                    return;
                }
                return;
            }
            for (int i7 = 0; i7 < this.mInfoViewList.size(); i7++) {
                View view = this.mInfoViewList.get(i7);
                if (z) {
                    i = 0;
                } else {
                    i = 8;
                }
                view.setVisibility(i);
            }
        } else if (i2 == 1) {
            cancelAnimations();
            if (z) {
                for (int i8 = 0; i8 < this.mInfoViewList.size(); i8++) {
                    this.mInfoViewList.get(i8).setVisibility(0);
                }
            }
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            if (f != this.mInfoAlpha) {
                float f3 = this.mInfoAlpha;
                if (z) {
                    f2 = 1.0f;
                }
                InfoAlphaAnimation infoAlphaAnimation = new InfoAlphaAnimation(f3, f2);
                this.mAnim = infoAlphaAnimation;
                infoAlphaAnimation.setDuration((long) this.mActivatedAnimDuration);
                this.mAnim.setInterpolator(new DecelerateInterpolator());
                this.mAnim.setAnimationListener(new Animation.AnimationListener() {
                    public final void onAnimationRepeat(Animation animation) {
                    }

                    public final void onAnimationStart(Animation animation) {
                    }

                    public final void onAnimationEnd(Animation animation) {
                        if (((double) BaseCardView.this.mInfoAlpha) == 0.0d) {
                            for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                                BaseCardView.this.mInfoViewList.get(i).setVisibility(8);
                            }
                        }
                    }
                });
                startAnimation(this.mAnim);
            }
        }
    }

    public final void animateInfoOffset(boolean z) {
        float f;
        cancelAnimations();
        int i = 0;
        if (z) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, 1073741824);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int i2 = 0;
            for (int i3 = 0; i3 < this.mExtraViewList.size(); i3++) {
                View view = this.mExtraViewList.get(i3);
                view.setVisibility(0);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i2 = Math.max(i2, view.getMeasuredHeight());
            }
            i = i2;
        }
        float f2 = this.mInfoOffset;
        if (z) {
            f = (float) i;
        } else {
            f = 0.0f;
        }
        InfoOffsetAnimation infoOffsetAnimation = new InfoOffsetAnimation(f2, f);
        this.mAnim = infoOffsetAnimation;
        infoOffsetAnimation.setDuration((long) this.mSelectedAnimDuration);
        this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoOffset == 0.0f) {
                    for (int i = 0; i < BaseCardView.this.mExtraViewList.size(); i++) {
                        BaseCardView.this.mExtraViewList.get(i).setVisibility(8);
                    }
                }
            }
        });
        startAnimation(this.mAnim);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i);
        int length = onCreateDrawableState.length;
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            if (onCreateDrawableState[i2] == 16842919) {
                z = true;
            }
            if (onCreateDrawableState[i2] == 16842910) {
                z2 = true;
            }
        }
        if (z && z2) {
            return View.PRESSED_ENABLED_STATE_SET;
        }
        if (z) {
            return LB_PRESSED_STATE_SET;
        }
        if (z2) {
            return View.ENABLED_STATE_SET;
        }
        return View.EMPTY_STATE_SET;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mAnimationTrigger);
        cancelAnimations();
    }

    public final void setActivated(boolean z) {
        boolean z2;
        int i;
        if (z != isActivated()) {
            super.setActivated(z);
            boolean z3 = true;
            if (this.mCardType != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 && (i = this.mInfoVisibility) == 1) {
                if (i != 0) {
                    if (i == 1) {
                        z3 = isActivated();
                    } else if (i != 2) {
                        z3 = false;
                    } else {
                        z3 = isSelected();
                    }
                }
                setInfoViewVisibility(z3);
            }
        }
    }

    public final void setSelected(boolean z) {
        if (z != isSelected()) {
            super.setSelected(z);
            boolean isSelected = isSelected();
            removeCallbacks(this.mAnimationTrigger);
            if (this.mCardType == 3) {
                if (!isSelected) {
                    animateInfoOffset(false);
                } else if (!this.mDelaySelectedAnim) {
                    post(this.mAnimationTrigger);
                    this.mDelaySelectedAnim = true;
                } else {
                    postDelayed(this.mAnimationTrigger, (long) this.mSelectedAnimationDelay);
                }
            } else if (this.mInfoVisibility == 2) {
                setInfoViewVisibility(isSelected);
            }
        }
    }

    public final String toString() {
        return super.toString();
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        @ViewDebug.ExportedProperty(category = "layout", mapping = {@ViewDebug.IntToString(from = 0, to = "MAIN"), @ViewDebug.IntToString(from = 1, to = "INFO"), @ViewDebug.IntToString(from = 2, to = "EXTRA")})
        public int viewType = 0;

        @SuppressLint({"CustomViewStyleable"})
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbBaseCardView_Layout);
            this.viewType = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams() {
            super(-2, -2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.viewType = layoutParams.viewType;
        }
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }
}
