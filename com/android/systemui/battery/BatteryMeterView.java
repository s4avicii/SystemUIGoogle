package com.android.systemui.battery;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.graph.ThemedBatteryDrawable;
import com.android.systemui.DejankUtils;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R$styleable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.BatteryController;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class BatteryMeterView extends LinearLayout implements DarkIconDispatcher.DarkReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BatteryEstimateFetcher mBatteryEstimateFetcher;
    public final ImageView mBatteryIconView;
    public TextView mBatteryPercentView;
    public boolean mBatteryStateUnknown;
    public boolean mCharging;
    public final ThemedBatteryDrawable mDrawable;
    public DualToneHandler mDualToneHandler;
    public int mLevel;
    public int mNonAdaptedForegroundColor;
    public int mNonAdaptedSingleToneColor;
    public final int mPercentageStyleId;
    public boolean mShowPercentAvailable;
    public int mShowPercentMode;
    public int mTextColor;
    public Drawable mUnknownStateDrawable;

    public interface BatteryEstimateFetcher {
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowPercentMode = 0;
        setOrientation(0);
        setGravity(8388627);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BatteryMeterView, i, 0);
        int color = obtainStyledAttributes.getColor(0, context.getColor(C1777R.color.meter_background_color));
        this.mPercentageStyleId = obtainStyledAttributes.getResourceId(1, 0);
        ThemedBatteryDrawable themedBatteryDrawable = new ThemedBatteryDrawable(context, color);
        this.mDrawable = themedBatteryDrawable;
        obtainStyledAttributes.recycle();
        this.mShowPercentAvailable = context.getResources().getBoolean(17891383);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(200);
        layoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{0.0f, 1.0f}));
        layoutTransition.setInterpolator(2, Interpolators.ALPHA_IN);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{1.0f, 0.0f});
        layoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
        layoutTransition.setAnimator(3, ofFloat);
        layoutTransition.setAnimator(0, (Animator) null);
        layoutTransition.setAnimator(1, (Animator) null);
        layoutTransition.setAnimator(4, (Animator) null);
        setLayoutTransition(layoutTransition);
        ImageView imageView = new ImageView(context);
        this.mBatteryIconView = imageView;
        imageView.setImageDrawable(themedBatteryDrawable);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(getResources().getDimensionPixelSize(C1777R.dimen.status_bar_battery_icon_width), getResources().getDimensionPixelSize(C1777R.dimen.status_bar_battery_icon_height));
        marginLayoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(C1777R.dimen.battery_margin_bottom));
        addView(imageView, marginLayoutParams);
        updateShowPercent();
        this.mDualToneHandler = new DualToneHandler(context);
        onDarkChanged(new ArrayList(), 0.0f, -1);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public CharSequence getBatteryPercentViewText() {
        return this.mBatteryPercentView.getText();
    }

    public final void onBatteryUnknownStateChanged(boolean z) {
        if (this.mBatteryStateUnknown != z) {
            this.mBatteryStateUnknown = z;
            if (z) {
                ImageView imageView = this.mBatteryIconView;
                if (this.mUnknownStateDrawable == null) {
                    Drawable drawable = this.mContext.getDrawable(C1777R.C1778drawable.ic_battery_unknown);
                    this.mUnknownStateDrawable = drawable;
                    drawable.setTint(this.mTextColor);
                }
                imageView.setImageDrawable(this.mUnknownStateDrawable);
            } else {
                this.mBatteryIconView.setImageDrawable(this.mDrawable);
            }
            updateShowPercent();
        }
    }

    public final void setPercentShowMode(int i) {
        if (i != this.mShowPercentMode) {
            this.mShowPercentMode = i;
            updateShowPercent();
        }
    }

    public final void setPercentTextAtCurrentLevel() {
        int i;
        TextView textView = this.mBatteryPercentView;
        if (textView != null) {
            textView.setText(NumberFormat.getPercentInstance().format((double) (((float) this.mLevel) / 100.0f)));
            Context context = getContext();
            if (this.mCharging) {
                i = C1777R.string.accessibility_battery_level_charging;
            } else {
                i = C1777R.string.accessibility_battery_level;
            }
            setContentDescription(context.getString(i, new Object[]{Integer.valueOf(this.mLevel)}));
        }
    }

    public final void updateColors(int i, int i2, int i3) {
        ThemedBatteryDrawable themedBatteryDrawable = this.mDrawable;
        Objects.requireNonNull(themedBatteryDrawable);
        if (!themedBatteryDrawable.dualTone) {
            i = i3;
        }
        themedBatteryDrawable.fillColor = i;
        themedBatteryDrawable.fillPaint.setColor(i);
        themedBatteryDrawable.fillColorStrokePaint.setColor(themedBatteryDrawable.fillColor);
        themedBatteryDrawable.dualToneBackgroundFill.setColor(i2);
        themedBatteryDrawable.levelColor = themedBatteryDrawable.batteryColorForLevel(themedBatteryDrawable.batteryLevel);
        themedBatteryDrawable.invalidateSelf();
        this.mTextColor = i3;
        TextView textView = this.mBatteryPercentView;
        if (textView != null) {
            textView.setTextColor(i3);
        }
        Drawable drawable = this.mUnknownStateDrawable;
        if (drawable != null) {
            drawable.setTint(i3);
        }
    }

    public final void updatePercentText() {
        int i;
        if (this.mBatteryStateUnknown) {
            setContentDescription(getContext().getString(C1777R.string.accessibility_battery_unknown));
            return;
        }
        BatteryEstimateFetcher batteryEstimateFetcher = this.mBatteryEstimateFetcher;
        if (batteryEstimateFetcher != null) {
            if (this.mBatteryPercentView == null) {
                Context context = getContext();
                if (this.mCharging) {
                    i = C1777R.string.accessibility_battery_level_charging;
                } else {
                    i = C1777R.string.accessibility_battery_level;
                }
                setContentDescription(context.getString(i, new Object[]{Integer.valueOf(this.mLevel)}));
            } else if (this.mShowPercentMode != 3 || this.mCharging) {
                setPercentTextAtCurrentLevel();
            } else {
                ((BatteryController) ((BatteryMeterViewController$$ExternalSyntheticLambda0) batteryEstimateFetcher).f$0).getEstimatedTimeRemainingString(new BatteryMeterView$$ExternalSyntheticLambda0(this));
            }
        }
    }

    public final void updateShowPercent() {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        boolean z4 = false;
        if (this.mBatteryPercentView != null) {
            z = true;
        } else {
            z = false;
        }
        if (((Integer) DejankUtils.whitelistIpcs(new BatteryMeterView$$ExternalSyntheticLambda1(this))).intValue() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((this.mShowPercentAvailable && z2 && this.mShowPercentMode != 2) || (i = this.mShowPercentMode) == 1 || i == 3) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 && !this.mBatteryStateUnknown) {
            z4 = true;
        }
        if (z4) {
            if (!z) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(C1777R.layout.battery_percentage_view, (ViewGroup) null);
                this.mBatteryPercentView = textView;
                int i2 = this.mPercentageStyleId;
                if (i2 != 0) {
                    textView.setTextAppearance(i2);
                }
                int i3 = this.mTextColor;
                if (i3 != 0) {
                    this.mBatteryPercentView.setTextColor(i3);
                }
                updatePercentText();
                addView(this.mBatteryPercentView, new ViewGroup.LayoutParams(-2, -1));
            }
        } else if (z) {
            removeView(this.mBatteryPercentView);
            this.mBatteryPercentView = null;
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TextView textView = this.mBatteryPercentView;
        if (textView != null) {
            removeView(textView);
            this.mBatteryPercentView = null;
        }
        updateShowPercent();
    }

    public final void onDarkChanged(ArrayList<Rect> arrayList, float f, int i) {
        if (!DarkIconDispatcher.isInAreas(arrayList, this)) {
            f = 0.0f;
        }
        this.mNonAdaptedSingleToneColor = this.mDualToneHandler.getSingleColor(f);
        DualToneHandler dualToneHandler = this.mDualToneHandler;
        Objects.requireNonNull(dualToneHandler);
        DualToneHandler.Color color = dualToneHandler.lightColor;
        DualToneHandler.Color color2 = null;
        if (color == null) {
            color = null;
        }
        Objects.requireNonNull(color);
        int i2 = color.fill;
        DualToneHandler.Color color3 = dualToneHandler.darkColor;
        if (color3 == null) {
            color3 = null;
        }
        Objects.requireNonNull(color3);
        this.mNonAdaptedForegroundColor = DualToneHandler.getColorForDarkIntensity(f, i2, color3.fill);
        DualToneHandler dualToneHandler2 = this.mDualToneHandler;
        Objects.requireNonNull(dualToneHandler2);
        DualToneHandler.Color color4 = dualToneHandler2.lightColor;
        if (color4 == null) {
            color4 = null;
        }
        Objects.requireNonNull(color4);
        int i3 = color4.background;
        DualToneHandler.Color color5 = dualToneHandler2.darkColor;
        if (color5 != null) {
            color2 = color5;
        }
        Objects.requireNonNull(color2);
        updateColors(this.mNonAdaptedForegroundColor, DualToneHandler.getColorForDarkIntensity(f, i3, color2.background), this.mNonAdaptedSingleToneColor);
    }
}
