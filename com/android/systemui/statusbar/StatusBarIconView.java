package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.core.graphics.ColorUtils;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda4;
import com.android.systemui.R$array;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.notification.NotificationDozeHelper;
import com.android.systemui.statusbar.notification.NotificationDozeHelper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.NotificationIconDozeHelper;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class StatusBarIconView extends AnimatedImageView implements StatusIconDisplayable {
    public static final C11842 DOT_APPEAR_AMOUNT = new FloatProperty<StatusBarIconView>() {
        public final Object get(Object obj) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) obj;
            Objects.requireNonNull(statusBarIconView);
            return Float.valueOf(statusBarIconView.mDotAppearAmount);
        }

        public final void setValue(Object obj, float f) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) obj;
            Objects.requireNonNull(statusBarIconView);
            if (statusBarIconView.mDotAppearAmount != f) {
                statusBarIconView.mDotAppearAmount = f;
                statusBarIconView.invalidate();
            }
        }
    };
    public static final C11831 ICON_APPEAR_AMOUNT = new FloatProperty<StatusBarIconView>() {
        public final Object get(Object obj) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) obj;
            Objects.requireNonNull(statusBarIconView);
            return Float.valueOf(statusBarIconView.mIconAppearAmount);
        }

        public final void setValue(Object obj, float f) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) obj;
            Objects.requireNonNull(statusBarIconView);
            if (statusBarIconView.mIconAppearAmount != f) {
                statusBarIconView.mIconAppearAmount = f;
                statusBarIconView.invalidate();
            }
        }
    };
    public boolean mAlwaysScaleIcon;
    public int mAnimationStartColor;
    public final boolean mBlocked;
    public int mCachedContrastBackgroundColor;
    public ValueAnimator mColorAnimator;
    public final ValueAnimator.AnimatorUpdateListener mColorUpdater;
    public int mContrastedDrawableColor;
    public int mCurrentSetColor;
    public int mDecorColor;
    public int mDensity;
    public ObjectAnimator mDotAnimator;
    public float mDotAppearAmount;
    public final Paint mDotPaint;
    public float mDotRadius;
    public float mDozeAmount;
    public final NotificationIconDozeHelper mDozer;
    public int mDrawableColor;
    public StatusBarIcon mIcon;
    public float mIconAppearAmount;
    public ObjectAnimator mIconAppearAnimator;
    public int mIconColor;
    public float mIconScale;
    public boolean mIncreasedSize;
    public Runnable mLayoutRunnable;
    public float[] mMatrix;
    public ColorMatrixColorFilter mMatrixColorFilter;
    public boolean mNightMode;
    public StatusBarNotification mNotification;
    public Drawable mNumberBackground;
    public Paint mNumberPain;
    public String mNumberText;
    public int mNumberX;
    public int mNumberY;
    public Runnable mOnDismissListener;
    public boolean mShowsConversation;
    @ViewDebug.ExportedProperty
    public String mSlot;
    public int mStaticDotRadius;
    public int mStatusBarIconDrawingSize;
    public int mStatusBarIconDrawingSizeIncreased;
    public int mStatusBarIconSize;
    public float mSystemIconDefaultScale;
    public float mSystemIconDesiredHeight;
    public float mSystemIconIntrinsicHeight;
    public int mVisibleState;

    public StatusBarIconView(Context context, String str, StatusBarNotification statusBarNotification) {
        this(context, str, statusBarNotification, false);
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void setVisibleState(int i) {
        setVisibleState(i, true, (PipTaskOrganizer$$ExternalSyntheticLambda4) null, 0);
    }

    public StatusBarIconView(Context context, String str, StatusBarNotification statusBarNotification, boolean z) {
        super(context);
        this.mSystemIconDesiredHeight = 15.0f;
        this.mSystemIconIntrinsicHeight = 17.0f;
        this.mSystemIconDefaultScale = 0.88235295f;
        boolean z2 = true;
        this.mStatusBarIconDrawingSizeIncreased = 1;
        this.mStatusBarIconDrawingSize = 1;
        this.mStatusBarIconSize = 1;
        this.mIconScale = 1.0f;
        this.mDotPaint = new Paint(1);
        this.mVisibleState = 0;
        this.mIconAppearAmount = 1.0f;
        this.mCurrentSetColor = 0;
        this.mAnimationStartColor = 0;
        this.mColorUpdater = new StatusBarIconView$$ExternalSyntheticLambda0(this, 0);
        this.mCachedContrastBackgroundColor = 0;
        this.mDozer = new NotificationIconDozeHelper(context);
        this.mBlocked = z;
        this.mSlot = str;
        Paint paint = new Paint();
        this.mNumberPain = paint;
        paint.setTextAlign(Paint.Align.CENTER);
        this.mNumberPain.setColor(context.getColor(C1777R.C1778drawable.notification_number_text_color));
        this.mNumberPain.setAntiAlias(true);
        setNotification(statusBarNotification);
        setScaleType(ImageView.ScaleType.CENTER);
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.mNightMode = (context.getResources().getConfiguration().uiMode & 48) != 32 ? false : z2;
        if (this.mNotification != null) {
            setDecorColor(getContext().getColor(this.mNightMode ? 17170975 : 17170976));
        }
        reloadDimens();
        maybeUpdateIconScaleDimens();
    }

    public static String contentDescForNotification(Context context, Notification notification) {
        CharSequence charSequence;
        CharSequence charSequence2 = "";
        try {
            charSequence = Notification.Builder.recoverBuilder(context, notification).loadHeaderAppName();
        } catch (RuntimeException e) {
            Log.e("StatusBarIconView", "Unable to recover builder", e);
            Parcelable parcelable = notification.extras.getParcelable("android.appInfo");
            if (parcelable instanceof ApplicationInfo) {
                charSequence = String.valueOf(((ApplicationInfo) parcelable).loadLabel(context.getPackageManager()));
            } else {
                charSequence = charSequence2;
            }
        }
        CharSequence charSequence3 = notification.extras.getCharSequence("android.title");
        CharSequence charSequence4 = notification.extras.getCharSequence("android.text");
        CharSequence charSequence5 = notification.tickerText;
        if (TextUtils.equals(charSequence3, charSequence)) {
            charSequence3 = charSequence4;
        }
        if (!TextUtils.isEmpty(charSequence3)) {
            charSequence2 = charSequence3;
        } else if (!TextUtils.isEmpty(charSequence5)) {
            charSequence2 = charSequence5;
        }
        return context.getString(C1777R.string.accessibility_desc_notification_icon, new Object[]{charSequence, charSequence2});
    }

    public final boolean isIconVisible() {
        StatusBarIcon statusBarIcon = this.mIcon;
        if (statusBarIcon == null || !statusBarIcon.visible) {
            return false;
        }
        return true;
    }

    public final void maybeUpdateIconScaleDimens() {
        int i;
        float f;
        if (this.mNotification != null || this.mAlwaysScaleIcon) {
            if (this.mIncreasedSize) {
                i = this.mStatusBarIconDrawingSizeIncreased;
            } else {
                i = this.mStatusBarIconDrawingSize;
            }
            this.mIconScale = ((float) i) / ((float) this.mStatusBarIconSize);
            updatePivot();
            return;
        }
        if (getDrawable() != null) {
            f = (float) getDrawable().getIntrinsicHeight();
        } else {
            f = this.mSystemIconIntrinsicHeight;
        }
        if (f != 0.0f) {
            this.mIconScale = this.mSystemIconDesiredHeight / f;
        } else {
            this.mIconScale = this.mSystemIconDefaultScale;
        }
    }

    public final void onDraw(Canvas canvas) {
        float f;
        if (this.mIconAppearAmount > 0.0f) {
            canvas.save();
            float f2 = this.mIconScale;
            float f3 = this.mIconAppearAmount;
            canvas.scale(f2 * f3, f2 * f3, (float) (getWidth() / 2), (float) (getHeight() / 2));
            super.onDraw(canvas);
            canvas.restore();
        }
        Drawable drawable = this.mNumberBackground;
        if (drawable != null) {
            drawable.draw(canvas);
            canvas.drawText(this.mNumberText, (float) this.mNumberX, (float) this.mNumberY, this.mNumberPain);
        }
        if (this.mDotAppearAmount != 0.0f) {
            float alpha = ((float) Color.alpha(this.mDecorColor)) / 255.0f;
            float f4 = this.mDotAppearAmount;
            if (f4 <= 1.0f) {
                f = this.mDotRadius * f4;
            } else {
                float f5 = f4 - 1.0f;
                alpha *= 1.0f - f5;
                f = R$array.interpolate(this.mDotRadius, (float) (getWidth() / 4), f5);
            }
            this.mDotPaint.setAlpha((int) (alpha * 255.0f));
            canvas.drawCircle((float) (this.mStatusBarIconSize / 2), (float) (getHeight() / 2), f, this.mDotPaint);
        }
    }

    public final void reloadDimens() {
        boolean z;
        if (this.mDotRadius == ((float) this.mStaticDotRadius)) {
            z = true;
        } else {
            z = false;
        }
        Resources resources = getResources();
        this.mStaticDotRadius = resources.getDimensionPixelSize(C1777R.dimen.overflow_dot_radius);
        this.mStatusBarIconSize = resources.getDimensionPixelSize(C1777R.dimen.status_bar_icon_size);
        this.mStatusBarIconDrawingSizeIncreased = resources.getDimensionPixelSize(C1777R.dimen.status_bar_icon_drawing_size_dark);
        this.mStatusBarIconDrawingSize = resources.getDimensionPixelSize(C1777R.dimen.status_bar_icon_drawing_size);
        if (z) {
            this.mDotRadius = (float) this.mStaticDotRadius;
        }
        this.mSystemIconDesiredHeight = resources.getDimension(17105556);
        float dimension = resources.getDimension(17105555);
        this.mSystemIconIntrinsicHeight = dimension;
        this.mSystemIconDefaultScale = this.mSystemIconDesiredHeight / dimension;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0049, code lost:
        if (r0.getResId() == r3.getResId()) goto L_0x004b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00d6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean set(com.android.internal.statusbar.StatusBarIcon r8) {
        /*
            r7 = this;
            com.android.internal.statusbar.StatusBarIcon r0 = r7.mIcon
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0052
            android.graphics.drawable.Icon r0 = r0.icon
            android.graphics.drawable.Icon r3 = r8.icon
            if (r0 != r3) goto L_0x000d
            goto L_0x004b
        L_0x000d:
            int r4 = r0.getType()
            int r5 = r3.getType()
            if (r4 == r5) goto L_0x0018
            goto L_0x004d
        L_0x0018:
            int r4 = r0.getType()
            r5 = 2
            if (r4 == r5) goto L_0x0033
            r5 = 4
            if (r4 == r5) goto L_0x0026
            r5 = 6
            if (r4 == r5) goto L_0x0026
            goto L_0x004d
        L_0x0026:
            java.lang.String r0 = r0.getUriString()
            java.lang.String r3 = r3.getUriString()
            boolean r0 = r0.equals(r3)
            goto L_0x004e
        L_0x0033:
            java.lang.String r4 = r0.getResPackage()
            java.lang.String r5 = r3.getResPackage()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x004d
            int r0 = r0.getResId()
            int r3 = r3.getResId()
            if (r0 != r3) goto L_0x004d
        L_0x004b:
            r0 = r1
            goto L_0x004e
        L_0x004d:
            r0 = r2
        L_0x004e:
            if (r0 == 0) goto L_0x0052
            r0 = r1
            goto L_0x0053
        L_0x0052:
            r0 = r2
        L_0x0053:
            if (r0 == 0) goto L_0x005f
            com.android.internal.statusbar.StatusBarIcon r3 = r7.mIcon
            int r3 = r3.iconLevel
            int r4 = r8.iconLevel
            if (r3 != r4) goto L_0x005f
            r3 = r1
            goto L_0x0060
        L_0x005f:
            r3 = r2
        L_0x0060:
            com.android.internal.statusbar.StatusBarIcon r4 = r7.mIcon
            if (r4 == 0) goto L_0x006c
            boolean r5 = r4.visible
            boolean r6 = r8.visible
            if (r5 != r6) goto L_0x006c
            r5 = r1
            goto L_0x006d
        L_0x006c:
            r5 = r2
        L_0x006d:
            if (r4 == 0) goto L_0x0077
            int r4 = r4.number
            int r6 = r8.number
            if (r4 != r6) goto L_0x0077
            r4 = r1
            goto L_0x0078
        L_0x0077:
            r4 = r2
        L_0x0078:
            com.android.internal.statusbar.StatusBarIcon r6 = r8.clone()
            r7.mIcon = r6
            java.lang.CharSequence r6 = r8.contentDescription
            r7.setContentDescription(r6)
            r6 = 0
            if (r0 != 0) goto L_0x0096
            boolean r0 = r7.updateDrawable(r2)
            if (r0 != 0) goto L_0x008d
            return r2
        L_0x008d:
            r0 = 2131428106(0x7f0b030a, float:1.8477847E38)
            r7.setTag(r0, r6)
            r7.maybeUpdateIconScaleDimens()
        L_0x0096:
            if (r3 != 0) goto L_0x009d
            int r0 = r8.iconLevel
            r7.setImageLevel(r0)
        L_0x009d:
            if (r4 != 0) goto L_0x00d4
            int r0 = r8.number
            if (r0 <= 0) goto L_0x00cd
            android.content.Context r0 = r7.getContext()
            android.content.res.Resources r0 = r0.getResources()
            r3 = 2131034177(0x7f050041, float:1.7678864E38)
            boolean r0 = r0.getBoolean(r3)
            if (r0 == 0) goto L_0x00cd
            android.graphics.drawable.Drawable r0 = r7.mNumberBackground
            if (r0 != 0) goto L_0x00c9
            android.content.Context r0 = r7.getContext()
            android.content.res.Resources r0 = r0.getResources()
            r3 = 2131232203(0x7f0805cb, float:1.8080509E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r3)
            r7.mNumberBackground = r0
        L_0x00c9:
            r7.placeNumber()
            goto L_0x00d1
        L_0x00cd:
            r7.mNumberBackground = r6
            r7.mNumberText = r6
        L_0x00d1:
            r7.invalidate()
        L_0x00d4:
            if (r5 != 0) goto L_0x00e4
            boolean r8 = r8.visible
            if (r8 == 0) goto L_0x00df
            boolean r8 = r7.mBlocked
            if (r8 != 0) goto L_0x00df
            goto L_0x00e1
        L_0x00df:
            r2 = 8
        L_0x00e1:
            r7.setVisibility(r2)
        L_0x00e4:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.StatusBarIconView.set(com.android.internal.statusbar.StatusBarIcon):boolean");
    }

    public final void setDecorColor(int i) {
        this.mDecorColor = i;
        updateDecorColor();
    }

    public final void setDozing(boolean z, boolean z2) {
        float f;
        NotificationIconDozeHelper notificationIconDozeHelper = this.mDozer;
        ShellCommandHandlerImpl$$ExternalSyntheticLambda2 shellCommandHandlerImpl$$ExternalSyntheticLambda2 = new ShellCommandHandlerImpl$$ExternalSyntheticLambda2(this, 1);
        Objects.requireNonNull(notificationIconDozeHelper);
        float f2 = 1.0f;
        if (z2) {
            NotificationDozeHelper$$ExternalSyntheticLambda0 notificationDozeHelper$$ExternalSyntheticLambda0 = new NotificationDozeHelper$$ExternalSyntheticLambda0(shellCommandHandlerImpl$$ExternalSyntheticLambda2);
            NotificationDozeHelper.C12343 r1 = new AnimatorListenerAdapter(this) {
                public final /* synthetic */ View val$view;

                {
                    this.val$view = r1;
                }

                public final void onAnimationEnd(Animator animator) {
                    this.val$view.setTag(C1777R.C1779id.doze_intensity_tag, (Object) null);
                }

                public final void onAnimationStart(Animator animator) {
                    this.val$view.setTag(C1777R.C1779id.doze_intensity_tag, animator);
                }
            };
            if (z) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            if (!z) {
                f2 = 0.0f;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, f2});
            ofFloat.addUpdateListener(notificationDozeHelper$$ExternalSyntheticLambda0);
            ofFloat.setDuration(500);
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat.setStartDelay(0);
            ofFloat.addListener(r1);
            ofFloat.start();
            return;
        }
        Animator animator = (Animator) getTag(C1777R.C1779id.doze_intensity_tag);
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            f2 = 0.0f;
        }
        shellCommandHandlerImpl$$ExternalSyntheticLambda2.accept(Float.valueOf(f2));
    }

    public final void setIconColor(int i, boolean z) {
        if (this.mIconColor != i) {
            this.mIconColor = i;
            ValueAnimator valueAnimator = this.mColorAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            int i2 = this.mCurrentSetColor;
            if (i2 != i) {
                if (!z || i2 == 0) {
                    this.mCurrentSetColor = i;
                    updateIconColor();
                    return;
                }
                this.mAnimationStartColor = i2;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                this.mColorAnimator = ofFloat;
                ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                this.mColorAnimator.setDuration(100);
                this.mColorAnimator.addUpdateListener(this.mColorUpdater);
                this.mColorAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        StatusBarIconView statusBarIconView = StatusBarIconView.this;
                        statusBarIconView.mColorAnimator = null;
                        statusBarIconView.mAnimationStartColor = 0;
                    }
                });
                this.mColorAnimator.start();
            }
        }
    }

    public final void setNotification(StatusBarNotification statusBarNotification) {
        Notification notification;
        this.mNotification = statusBarNotification;
        if (!(statusBarNotification == null || (notification = statusBarNotification.getNotification()) == null)) {
            String contentDescForNotification = contentDescForNotification(this.mContext, notification);
            if (!TextUtils.isEmpty(contentDescForNotification)) {
                setContentDescription(contentDescForNotification);
            }
        }
        maybeUpdateIconScaleDimens();
    }

    public final void setStaticDrawableColor(int i) {
        this.mDrawableColor = i;
        this.mCurrentSetColor = i;
        updateIconColor();
        updateContrastedStaticColor();
        this.mIconColor = i;
        Objects.requireNonNull(this.mDozer);
    }

    public final void setVisibleState(int i, boolean z) {
        setVisibleState(i, z, (PipTaskOrganizer$$ExternalSyntheticLambda4) null, 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("StatusBarIconView(slot=");
        m.append(this.mSlot);
        m.append(" icon=");
        m.append(this.mIcon);
        m.append(" notification=");
        m.append(this.mNotification);
        m.append(")");
        return m.toString();
    }

    public final void updateContrastedStaticColor() {
        if (Color.alpha(this.mCachedContrastBackgroundColor) != 255) {
            this.mContrastedDrawableColor = this.mDrawableColor;
            return;
        }
        int i = this.mDrawableColor;
        if (!ContrastColorUtil.satisfiesTextContrast(this.mCachedContrastBackgroundColor, i)) {
            float[] fArr = new float[3];
            int i2 = this.mDrawableColor;
            ThreadLocal<double[]> threadLocal = ColorUtils.TEMP_ARRAY;
            ColorUtils.RGBToHSL(Color.red(i2), Color.green(i2), Color.blue(i2), fArr);
            if (fArr[1] < 0.2f) {
                i = 0;
            }
            i = ContrastColorUtil.resolveContrastColor(this.mContext, i, this.mCachedContrastBackgroundColor, !ContrastColorUtil.isColorLight(this.mCachedContrastBackgroundColor));
        }
        this.mContrastedDrawableColor = i;
    }

    public final void updateDecorColor() {
        int interpolateColors = R$array.interpolateColors(this.mDecorColor, -1, this.mDozeAmount);
        if (this.mDotPaint.getColor() != interpolateColors) {
            this.mDotPaint.setColor(interpolateColors);
            if (this.mDotAppearAmount != 0.0f) {
                invalidate();
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:15|16|17|18) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0042, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        android.util.Log.w("StatusBarIconView", "OOM while inflating " + r3.mIcon.icon + " for slot " + r3.mSlot);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
        throw r3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0044 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean updateDrawable(boolean r4) {
        /*
            r3 = this;
            java.lang.String r0 = "StatusBarIconView"
            com.android.internal.statusbar.StatusBarIcon r1 = r3.mIcon
            r2 = 0
            if (r1 != 0) goto L_0x0008
            return r2
        L_0x0008:
            java.lang.String r1 = "StatusBarIconView#updateDrawable()"
            android.os.Trace.beginSection(r1)     // Catch:{ OutOfMemoryError -> 0x0044 }
            com.android.internal.statusbar.StatusBarIcon r1 = r3.mIcon     // Catch:{ OutOfMemoryError -> 0x0044 }
            android.graphics.drawable.Drawable r1 = r3.getIcon(r1)     // Catch:{ OutOfMemoryError -> 0x0044 }
            android.os.Trace.endSection()
            if (r1 != 0) goto L_0x0037
            java.lang.String r4 = "No icon for slot "
            java.lang.StringBuilder r4 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r4)
            java.lang.String r1 = r3.mSlot
            r4.append(r1)
            java.lang.String r1 = "; "
            r4.append(r1)
            com.android.internal.statusbar.StatusBarIcon r3 = r3.mIcon
            android.graphics.drawable.Icon r3 = r3.icon
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Log.w(r0, r3)
            return r2
        L_0x0037:
            if (r4 == 0) goto L_0x003d
            r4 = 0
            r3.setImageDrawable(r4)
        L_0x003d:
            r3.setImageDrawable(r1)
            r3 = 1
            return r3
        L_0x0042:
            r3 = move-exception
            goto L_0x006a
        L_0x0044:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0042 }
            r4.<init>()     // Catch:{ all -> 0x0042 }
            java.lang.String r1 = "OOM while inflating "
            r4.append(r1)     // Catch:{ all -> 0x0042 }
            com.android.internal.statusbar.StatusBarIcon r1 = r3.mIcon     // Catch:{ all -> 0x0042 }
            android.graphics.drawable.Icon r1 = r1.icon     // Catch:{ all -> 0x0042 }
            r4.append(r1)     // Catch:{ all -> 0x0042 }
            java.lang.String r1 = " for slot "
            r4.append(r1)     // Catch:{ all -> 0x0042 }
            java.lang.String r3 = r3.mSlot     // Catch:{ all -> 0x0042 }
            r4.append(r3)     // Catch:{ all -> 0x0042 }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0042 }
            android.util.Log.w(r0, r3)     // Catch:{ all -> 0x0042 }
            android.os.Trace.endSection()
            return r2
        L_0x006a:
            android.os.Trace.endSection()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.StatusBarIconView.updateDrawable(boolean):boolean");
    }

    public final void updateIconColor() {
        if (this.mShowsConversation) {
            setColorFilter((ColorFilter) null);
        } else if (this.mCurrentSetColor != 0) {
            if (this.mMatrixColorFilter == null) {
                this.mMatrix = new float[20];
                this.mMatrixColorFilter = new ColorMatrixColorFilter(this.mMatrix);
            }
            int interpolateColors = R$array.interpolateColors(this.mCurrentSetColor, -1, this.mDozeAmount);
            float[] fArr = this.mMatrix;
            Arrays.fill(fArr, 0.0f);
            fArr[4] = (float) Color.red(interpolateColors);
            fArr[9] = (float) Color.green(interpolateColors);
            fArr[14] = (float) Color.blue(interpolateColors);
            fArr[18] = (((float) Color.alpha(interpolateColors)) / 255.0f) + (this.mDozeAmount * 0.67f);
            this.mMatrixColorFilter.setColorMatrixArray(this.mMatrix);
            setColorFilter((ColorFilter) null);
            setColorFilter(this.mMatrixColorFilter);
        } else {
            NotificationIconDozeHelper notificationIconDozeHelper = this.mDozer;
            float f = this.mDozeAmount;
            if (f > 0.0f) {
                notificationIconDozeHelper.mGrayscaleColorMatrix.setSaturation(1.0f - f);
                setColorFilter(new ColorMatrixColorFilter(notificationIconDozeHelper.mGrayscaleColorMatrix));
                return;
            }
            Objects.requireNonNull(notificationIconDozeHelper);
            setColorFilter((ColorFilter) null);
        }
    }

    public final void debug(int i) {
        super.debug(i);
        Log.d("View", ImageView.debugIndent(i) + "slot=" + this.mSlot);
        Log.d("View", ImageView.debugIndent(i) + "icon=" + this.mIcon);
    }

    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        float translationX = getTranslationX();
        float translationY = getTranslationY();
        rect.left = (int) (((float) rect.left) + translationX);
        rect.right = (int) (((float) rect.right) + translationX);
        rect.top = (int) (((float) rect.top) + translationY);
        rect.bottom = (int) (((float) rect.bottom) + translationY);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x007b A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0080 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0088 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008c A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008f A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0090 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00a2 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a7 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ba A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f0 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x010f A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0113 A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0115 A[Catch:{ all -> 0x01dd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.drawable.Drawable getIcon(com.android.internal.statusbar.StatusBarIcon r14) {
        /*
            r13 = this;
            android.content.Context r0 = r13.getContext()
            android.service.notification.StatusBarNotification r1 = r13.mNotification
            if (r1 == 0) goto L_0x0010
            android.content.Context r0 = r13.getContext()
            android.content.Context r0 = r1.getPackageContext(r0)
        L_0x0010:
            android.content.Context r1 = r13.getContext()
            if (r0 == 0) goto L_0x0017
            goto L_0x001b
        L_0x0017:
            android.content.Context r0 = r13.getContext()
        L_0x001b:
            android.os.UserHandle r13 = r14.user
            int r13 = r13.getIdentifier()
            r2 = -1
            r3 = 0
            if (r13 != r2) goto L_0x0026
            r13 = r3
        L_0x0026:
            android.graphics.drawable.Icon r14 = r14.icon
            android.graphics.drawable.Drawable r13 = r14.loadDrawableAsUser(r0, r13)
            android.util.TypedValue r14 = new android.util.TypedValue
            r14.<init>()
            android.content.res.Resources r0 = r1.getResources()
            r2 = 2131167066(0x7f07075a, float:1.7948395E38)
            r4 = 1
            r0.getValue(r2, r14, r4)
            float r14 = r14.getFloat()
            if (r13 == 0) goto L_0x01e2
            boolean r0 = android.app.ActivityManager.isLowRamDeviceStatic()
            android.content.res.Resources r1 = r1.getResources()
            if (r0 == 0) goto L_0x0050
            r0 = 17105436(0x105021c, float:2.4429755E-38)
            goto L_0x0053
        L_0x0050:
            r0 = 17105435(0x105021b, float:2.4429753E-38)
        L_0x0053:
            int r0 = r1.getDimensionPixelSize(r0)
            java.lang.String r2 = "DrawableSize#downscaleToSize"
            android.os.Trace.beginSection(r2)
            boolean r2 = r13 instanceof android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x01dd }
            r5 = 0
            if (r2 == 0) goto L_0x0065
            r2 = r13
            android.graphics.drawable.BitmapDrawable r2 = (android.graphics.drawable.BitmapDrawable) r2     // Catch:{ all -> 0x01dd }
            goto L_0x0066
        L_0x0065:
            r2 = r5
        L_0x0066:
            if (r2 != 0) goto L_0x0069
            goto L_0x006f
        L_0x0069:
            android.graphics.Bitmap r2 = r2.getBitmap()     // Catch:{ all -> 0x01dd }
            if (r2 != 0) goto L_0x0071
        L_0x006f:
            r2 = r5
            goto L_0x0079
        L_0x0071:
            int r2 = r2.getWidth()     // Catch:{ all -> 0x01dd }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x01dd }
        L_0x0079:
            if (r2 != 0) goto L_0x0080
            int r2 = r13.getIntrinsicWidth()     // Catch:{ all -> 0x01dd }
            goto L_0x0084
        L_0x0080:
            int r2 = r2.intValue()     // Catch:{ all -> 0x01dd }
        L_0x0084:
            boolean r6 = r13 instanceof android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x01dd }
            if (r6 == 0) goto L_0x008c
            r6 = r13
            android.graphics.drawable.BitmapDrawable r6 = (android.graphics.drawable.BitmapDrawable) r6     // Catch:{ all -> 0x01dd }
            goto L_0x008d
        L_0x008c:
            r6 = r5
        L_0x008d:
            if (r6 != 0) goto L_0x0090
            goto L_0x0096
        L_0x0090:
            android.graphics.Bitmap r6 = r6.getBitmap()     // Catch:{ all -> 0x01dd }
            if (r6 != 0) goto L_0x0098
        L_0x0096:
            r6 = r5
            goto L_0x00a0
        L_0x0098:
            int r6 = r6.getHeight()     // Catch:{ all -> 0x01dd }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x01dd }
        L_0x00a0:
            if (r6 != 0) goto L_0x00a7
            int r6 = r13.getIntrinsicHeight()     // Catch:{ all -> 0x01dd }
            goto L_0x00ab
        L_0x00a7:
            int r6 = r6.intValue()     // Catch:{ all -> 0x01dd }
        L_0x00ab:
            if (r2 <= 0) goto L_0x01d9
            if (r6 > 0) goto L_0x00b1
            goto L_0x01d9
        L_0x00b1:
            java.lang.String r7 = " to "
            r8 = 3
            java.lang.String r9 = "SysUiDrawableSize"
            java.lang.String r10 = " x "
            if (r2 >= r0) goto L_0x00ea
            if (r6 >= r0) goto L_0x00ea
            boolean r1 = android.util.Log.isLoggable(r9, r8)     // Catch:{ all -> 0x01dd }
            if (r1 == 0) goto L_0x01d9
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dd }
            r1.<init>()     // Catch:{ all -> 0x01dd }
            java.lang.String r3 = "Not resizing "
            r1.append(r3)     // Catch:{ all -> 0x01dd }
            r1.append(r2)     // Catch:{ all -> 0x01dd }
            r1.append(r10)     // Catch:{ all -> 0x01dd }
            r1.append(r6)     // Catch:{ all -> 0x01dd }
            r1.append(r7)     // Catch:{ all -> 0x01dd }
            r1.append(r0)     // Catch:{ all -> 0x01dd }
            r1.append(r10)     // Catch:{ all -> 0x01dd }
            r1.append(r0)     // Catch:{ all -> 0x01dd }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x01dd }
            android.util.Log.d(r9, r0)     // Catch:{ all -> 0x01dd }
            goto L_0x01d9
        L_0x00ea:
            boolean r11 = r13.isStateful()     // Catch:{ all -> 0x01dd }
            if (r11 != 0) goto L_0x0110
            boolean r11 = r13 instanceof android.graphics.drawable.Animatable     // Catch:{ all -> 0x01dd }
            if (r11 != 0) goto L_0x010c
            boolean r11 = r13 instanceof android.graphics.drawable.Animatable2     // Catch:{ all -> 0x01dd }
            if (r11 == 0) goto L_0x00f9
            goto L_0x010c
        L_0x00f9:
            boolean r11 = r13 instanceof android.graphics.drawable.AnimatedImageDrawable     // Catch:{ all -> 0x01dd }
            if (r11 != 0) goto L_0x010c
            boolean r11 = r13 instanceof android.graphics.drawable.AnimatedRotateDrawable     // Catch:{ all -> 0x01dd }
            if (r11 != 0) goto L_0x010c
            boolean r11 = r13 instanceof android.graphics.drawable.AnimatedStateListDrawable     // Catch:{ all -> 0x01dd }
            if (r11 != 0) goto L_0x010c
            boolean r11 = r13 instanceof android.graphics.drawable.AnimatedVectorDrawable     // Catch:{ all -> 0x01dd }
            if (r11 == 0) goto L_0x010a
            goto L_0x010c
        L_0x010a:
            r11 = r3
            goto L_0x010d
        L_0x010c:
            r11 = r4
        L_0x010d:
            if (r11 != 0) goto L_0x0110
            goto L_0x0111
        L_0x0110:
            r4 = r3
        L_0x0111:
            if (r4 != 0) goto L_0x0115
            goto L_0x01d9
        L_0x0115:
            float r0 = (float) r0     // Catch:{ all -> 0x01dd }
            float r4 = (float) r2     // Catch:{ all -> 0x01dd }
            float r11 = r0 / r4
            float r12 = (float) r6     // Catch:{ all -> 0x01dd }
            float r0 = r0 / r12
            float r0 = java.lang.Math.min(r0, r11)     // Catch:{ all -> 0x01dd }
            float r4 = r4 * r0
            int r4 = (int) r4     // Catch:{ all -> 0x01dd }
            float r12 = r12 * r0
            int r0 = (int) r12     // Catch:{ all -> 0x01dd }
            if (r4 <= 0) goto L_0x019c
            if (r0 > 0) goto L_0x0129
            goto L_0x019c
        L_0x0129:
            boolean r8 = android.util.Log.isLoggable(r9, r8)     // Catch:{ all -> 0x01dd }
            if (r8 == 0) goto L_0x0165
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dd }
            r8.<init>()     // Catch:{ all -> 0x01dd }
            java.lang.String r11 = "Resizing large drawable ("
            r8.append(r11)     // Catch:{ all -> 0x01dd }
            java.lang.Class r11 = r13.getClass()     // Catch:{ all -> 0x01dd }
            java.lang.String r11 = r11.getSimpleName()     // Catch:{ all -> 0x01dd }
            r8.append(r11)     // Catch:{ all -> 0x01dd }
            java.lang.String r11 = ") from "
            r8.append(r11)     // Catch:{ all -> 0x01dd }
            r8.append(r2)     // Catch:{ all -> 0x01dd }
            r8.append(r10)     // Catch:{ all -> 0x01dd }
            r8.append(r6)     // Catch:{ all -> 0x01dd }
            r8.append(r7)     // Catch:{ all -> 0x01dd }
            r8.append(r4)     // Catch:{ all -> 0x01dd }
            r8.append(r10)     // Catch:{ all -> 0x01dd }
            r8.append(r0)     // Catch:{ all -> 0x01dd }
            java.lang.String r2 = r8.toString()     // Catch:{ all -> 0x01dd }
            android.util.Log.d(r9, r2)     // Catch:{ all -> 0x01dd }
        L_0x0165:
            boolean r2 = r13 instanceof android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x01dd }
            if (r2 == 0) goto L_0x016d
            r2 = r13
            android.graphics.drawable.BitmapDrawable r2 = (android.graphics.drawable.BitmapDrawable) r2     // Catch:{ all -> 0x01dd }
            goto L_0x016e
        L_0x016d:
            r2 = r5
        L_0x016e:
            if (r2 != 0) goto L_0x0171
            goto L_0x017c
        L_0x0171:
            android.graphics.Bitmap r2 = r2.getBitmap()     // Catch:{ all -> 0x01dd }
            if (r2 != 0) goto L_0x0178
            goto L_0x017c
        L_0x0178:
            android.graphics.Bitmap$Config r5 = r2.getConfig()     // Catch:{ all -> 0x01dd }
        L_0x017c:
            if (r5 != 0) goto L_0x0180
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ all -> 0x01dd }
        L_0x0180:
            android.graphics.Bitmap r2 = android.graphics.Bitmap.createBitmap(r4, r0, r5)     // Catch:{ all -> 0x01dd }
            android.graphics.Canvas r5 = new android.graphics.Canvas     // Catch:{ all -> 0x01dd }
            r5.<init>(r2)     // Catch:{ all -> 0x01dd }
            android.graphics.Rect r6 = r13.getBounds()     // Catch:{ all -> 0x01dd }
            r13.setBounds(r3, r3, r4, r0)     // Catch:{ all -> 0x01dd }
            r13.draw(r5)     // Catch:{ all -> 0x01dd }
            r13.setBounds(r6)     // Catch:{ all -> 0x01dd }
            android.graphics.drawable.BitmapDrawable r13 = new android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x01dd }
            r13.<init>(r1, r2)     // Catch:{ all -> 0x01dd }
            goto L_0x01d9
        L_0x019c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dd }
            r1.<init>()     // Catch:{ all -> 0x01dd }
            java.lang.String r3 = "Attempted to resize "
            r1.append(r3)     // Catch:{ all -> 0x01dd }
            java.lang.Class r3 = r13.getClass()     // Catch:{ all -> 0x01dd }
            java.lang.String r3 = r3.getSimpleName()     // Catch:{ all -> 0x01dd }
            r1.append(r3)     // Catch:{ all -> 0x01dd }
            java.lang.String r3 = " from "
            r1.append(r3)     // Catch:{ all -> 0x01dd }
            r1.append(r2)     // Catch:{ all -> 0x01dd }
            r1.append(r10)     // Catch:{ all -> 0x01dd }
            r1.append(r6)     // Catch:{ all -> 0x01dd }
            java.lang.String r2 = " to invalid "
            r1.append(r2)     // Catch:{ all -> 0x01dd }
            r1.append(r4)     // Catch:{ all -> 0x01dd }
            r1.append(r10)     // Catch:{ all -> 0x01dd }
            r1.append(r0)     // Catch:{ all -> 0x01dd }
            r0 = 46
            r1.append(r0)     // Catch:{ all -> 0x01dd }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x01dd }
            android.util.Log.w(r9, r0)     // Catch:{ all -> 0x01dd }
        L_0x01d9:
            android.os.Trace.endSection()
            goto L_0x01e2
        L_0x01dd:
            r13 = move-exception
            android.os.Trace.endSection()
            throw r13
        L_0x01e2:
            r0 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x01e9
            goto L_0x01ef
        L_0x01e9:
            com.android.systemui.statusbar.ScalingDrawableWrapper r0 = new com.android.systemui.statusbar.ScalingDrawableWrapper
            r0.<init>(r13, r14)
            r13 = r0
        L_0x01ef:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.StatusBarIconView.getIcon(com.android.internal.statusbar.StatusBarIcon):android.graphics.drawable.Drawable");
    }

    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        super.onConfigurationChanged(configuration);
        int i2 = configuration.densityDpi;
        boolean z = true;
        if (i2 != this.mDensity) {
            this.mDensity = i2;
            reloadDimens();
            updateDrawable(true);
            maybeUpdateIconScaleDimens();
        }
        if ((configuration.uiMode & 48) != 32) {
            z = false;
        }
        if (z != this.mNightMode) {
            this.mNightMode = z;
            if (this.mNotification != null) {
                Context context = getContext();
                if (this.mNightMode) {
                    i = 17170975;
                } else {
                    i = 17170976;
                }
                setDecorColor(context.getColor(i));
            }
        }
    }

    public final void onDarkChanged(ArrayList<Rect> arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        setImageTintList(ColorStateList.valueOf(tint));
        setDecorColor(tint);
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        StatusBarNotification statusBarNotification = this.mNotification;
        if (statusBarNotification != null) {
            accessibilityEvent.setParcelableData(statusBarNotification.getNotification());
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Runnable runnable = this.mLayoutRunnable;
        if (runnable != null) {
            runnable.run();
            this.mLayoutRunnable = null;
        }
        updatePivot();
    }

    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateDrawable(true);
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mNumberBackground != null) {
            placeNumber();
        }
    }

    public final void placeNumber() {
        String str;
        if (this.mIcon.number > getContext().getResources().getInteger(17694723)) {
            str = getContext().getResources().getString(17039383);
        } else {
            str = NumberFormat.getIntegerInstance().format((long) this.mIcon.number);
        }
        this.mNumberText = str;
        int width = getWidth();
        int height = getHeight();
        Rect rect = new Rect();
        this.mNumberPain.getTextBounds(str, 0, str.length(), rect);
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        this.mNumberBackground.getPadding(rect);
        int i3 = rect.left + i + rect.right;
        if (i3 < this.mNumberBackground.getMinimumWidth()) {
            i3 = this.mNumberBackground.getMinimumWidth();
        }
        int i4 = rect.right;
        this.mNumberX = (width - i4) - (((i3 - i4) - rect.left) / 2);
        int i5 = rect.top + i2 + rect.bottom;
        if (i5 < this.mNumberBackground.getMinimumWidth()) {
            i5 = this.mNumberBackground.getMinimumWidth();
        }
        int i6 = rect.bottom;
        this.mNumberY = (height - i6) - ((((i5 - rect.top) - i2) - i6) / 2);
        this.mNumberBackground.setBounds(width - i3, height - i5, width, height);
    }

    public final void setVisibleState(int i, boolean z, PipTaskOrganizer$$ExternalSyntheticLambda4 pipTaskOrganizer$$ExternalSyntheticLambda4, long j) {
        float f;
        PathInterpolator pathInterpolator;
        boolean z2;
        int i2 = i;
        final PipTaskOrganizer$$ExternalSyntheticLambda4 pipTaskOrganizer$$ExternalSyntheticLambda42 = pipTaskOrganizer$$ExternalSyntheticLambda4;
        boolean z3 = true;
        if (i2 != this.mVisibleState) {
            this.mVisibleState = i2;
            ObjectAnimator objectAnimator = this.mIconAppearAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            ObjectAnimator objectAnimator2 = this.mDotAnimator;
            if (objectAnimator2 != null) {
                objectAnimator2.cancel();
            }
            float f2 = 1.0f;
            if (z) {
                PathInterpolator pathInterpolator2 = Interpolators.FAST_OUT_LINEAR_IN;
                if (i2 == 0) {
                    pathInterpolator = Interpolators.LINEAR_OUT_SLOW_IN;
                    f = 1.0f;
                } else {
                    pathInterpolator = pathInterpolator2;
                    f = 0.0f;
                }
                float f3 = this.mIconAppearAmount;
                long j2 = 100;
                if (f != f3) {
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ICON_APPEAR_AMOUNT, new float[]{f3, f});
                    this.mIconAppearAnimator = ofFloat;
                    ofFloat.setInterpolator(pathInterpolator);
                    this.mIconAppearAnimator.setDuration(j == 0 ? 100 : j);
                    this.mIconAppearAnimator.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            StatusBarIconView.this.mIconAppearAnimator = null;
                            Runnable runnable = pipTaskOrganizer$$ExternalSyntheticLambda42;
                            if (runnable != null) {
                                runnable.run();
                            }
                        }
                    });
                    this.mIconAppearAnimator.start();
                    z2 = true;
                } else {
                    z2 = false;
                }
                float f4 = i2 == 0 ? 2.0f : 0.0f;
                if (i2 == 1) {
                    pathInterpolator2 = Interpolators.LINEAR_OUT_SLOW_IN;
                } else {
                    f2 = f4;
                }
                float f5 = this.mDotAppearAmount;
                if (f2 != f5) {
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, DOT_APPEAR_AMOUNT, new float[]{f5, f2});
                    this.mDotAnimator = ofFloat2;
                    ofFloat2.setInterpolator(pathInterpolator2);
                    ObjectAnimator objectAnimator3 = this.mDotAnimator;
                    if (j != 0) {
                        j2 = j;
                    }
                    objectAnimator3.setDuration(j2);
                    final boolean z4 = !z2;
                    this.mDotAnimator.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            Runnable runnable;
                            StatusBarIconView.this.mDotAnimator = null;
                            if (z4 && (runnable = pipTaskOrganizer$$ExternalSyntheticLambda42) != null) {
                                runnable.run();
                            }
                        }
                    });
                    this.mDotAnimator.start();
                } else {
                    z3 = z2;
                }
                if (!z3 && pipTaskOrganizer$$ExternalSyntheticLambda42 != null) {
                    pipTaskOrganizer$$ExternalSyntheticLambda4.run();
                    return;
                }
            }
            float f6 = i2 == 0 ? 1.0f : 0.0f;
            if (this.mIconAppearAmount != f6) {
                this.mIconAppearAmount = f6;
                invalidate();
            }
            float f7 = i2 == 1 ? 1.0f : i2 == 0 ? 2.0f : 0.0f;
            if (this.mDotAppearAmount != f7) {
                this.mDotAppearAmount = f7;
                invalidate();
            }
        }
        z3 = false;
        if (!z3) {
        }
    }

    public final void updatePivot() {
        if (isLayoutRtl()) {
            setPivotX(((this.mIconScale + 1.0f) / 2.0f) * ((float) getWidth()));
        } else {
            setPivotX(((1.0f - this.mIconScale) / 2.0f) * ((float) getWidth()));
        }
        setPivotY((((float) getHeight()) - (this.mIconScale * ((float) getWidth()))) / 2.0f);
    }

    public StatusBarIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSystemIconDesiredHeight = 15.0f;
        this.mSystemIconIntrinsicHeight = 17.0f;
        this.mSystemIconDefaultScale = 15.0f / 17.0f;
        this.mStatusBarIconDrawingSizeIncreased = 1;
        this.mStatusBarIconDrawingSize = 1;
        this.mStatusBarIconSize = 1;
        this.mIconScale = 1.0f;
        this.mDotPaint = new Paint(1);
        this.mVisibleState = 0;
        this.mIconAppearAmount = 1.0f;
        this.mCurrentSetColor = 0;
        this.mAnimationStartColor = 0;
        this.mColorUpdater = new StatusBarIconView$$ExternalSyntheticLambda1(this);
        this.mCachedContrastBackgroundColor = 0;
        this.mDozer = new NotificationIconDozeHelper(context);
        this.mBlocked = false;
        this.mAlwaysScaleIcon = true;
        reloadDimens();
        maybeUpdateIconScaleDimens();
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
    }

    public final void setVisibility(int i) {
        super.setVisibility(i);
    }

    public final String getSlot() {
        return this.mSlot;
    }

    public final int getVisibleState() {
        return this.mVisibleState;
    }

    public final boolean isIconBlocked() {
        return this.mBlocked;
    }
}
