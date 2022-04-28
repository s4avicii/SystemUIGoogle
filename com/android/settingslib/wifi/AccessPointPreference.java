package com.android.settingslib.wifi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.wifi.WifiConfiguration;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class AccessPointPreference extends Preference {
    public static final int[] STATE_METERED = {C1777R.attr.state_metered};
    public static final int[] STATE_SECURED = {C1777R.attr.state_encrypted};
    public static final int[] WIFI_CONNECTION_STRENGTH = {C1777R.string.accessibility_no_wifi, C1777R.string.accessibility_wifi_one_bar, C1777R.string.accessibility_wifi_two_bars, C1777R.string.accessibility_wifi_three_bars, C1777R.string.accessibility_wifi_signal_full};
    public AccessPoint mAccessPoint;
    public final int mBadgePadding;
    public final StateListDrawable mFrictionSld;
    public int mLevel;
    public final C06151 mNotifyChanged;
    public TextView mTitleView;

    public static class IconInjector {
    }

    public static class UserBadgeCache {
    }

    public AccessPointPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNotifyChanged = new Runnable() {
            public final void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mFrictionSld = null;
        this.mBadgePadding = 0;
    }

    public static CharSequence buildContentDescription(Context context, Preference preference, AccessPoint accessPoint) {
        String str;
        Objects.requireNonNull(preference);
        CharSequence charSequence = preference.mTitle;
        CharSequence summary = preference.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            charSequence = TextUtils.concat(new CharSequence[]{charSequence, ",", summary});
        }
        int level = accessPoint.getLevel();
        if (level >= 0) {
            int[] iArr = WIFI_CONNECTION_STRENGTH;
            if (level < 5) {
                charSequence = TextUtils.concat(new CharSequence[]{charSequence, ",", context.getString(iArr[level])});
            }
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = charSequence;
        charSequenceArr[1] = ",";
        if (accessPoint.security == 0) {
            str = context.getString(C1777R.string.accessibility_wifi_security_type_none);
        } else {
            str = context.getString(C1777R.string.accessibility_wifi_security_type_secured);
        }
        charSequenceArr[2] = str;
        return TextUtils.concat(charSequenceArr);
    }

    public static void setTitle(AccessPointPreference accessPointPreference, AccessPoint accessPoint) {
        accessPointPreference.setTitle((CharSequence) accessPoint.getTitle());
    }

    public final void notifyChanged() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.post(this.mNotifyChanged);
                return;
            }
            return;
        }
        super.notifyChanged();
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        boolean z;
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mAccessPoint != null) {
            Drawable icon = getIcon();
            if (icon != null) {
                icon.setLevel(this.mLevel);
            }
            TextView textView = (TextView) preferenceViewHolder.findViewById(16908310);
            this.mTitleView = textView;
            if (textView != null) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.mTitleView.setCompoundDrawablePadding(this.mBadgePadding);
            }
            preferenceViewHolder.itemView.setContentDescription((CharSequence) null);
            ImageView imageView = (ImageView) preferenceViewHolder.findViewById(C1777R.C1779id.friction_icon);
            if (!(imageView == null || this.mFrictionSld == null)) {
                AccessPoint accessPoint = this.mAccessPoint;
                Objects.requireNonNull(accessPoint);
                if (accessPoint.security != 0) {
                    AccessPoint accessPoint2 = this.mAccessPoint;
                    Objects.requireNonNull(accessPoint2);
                    if (accessPoint2.security != 4) {
                        this.mFrictionSld.setState(STATE_SECURED);
                        imageView.setImageDrawable(this.mFrictionSld.getCurrent());
                    }
                }
                AccessPoint accessPoint3 = this.mAccessPoint;
                Objects.requireNonNull(accessPoint3);
                if (accessPoint3.mIsScoredNetworkMetered || WifiConfiguration.isMetered(accessPoint3.mConfig, accessPoint3.mInfo)) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    this.mFrictionSld.setState(STATE_METERED);
                }
                imageView.setImageDrawable(this.mFrictionSld.getCurrent());
            }
            preferenceViewHolder.findViewById(C1777R.C1779id.two_target_divider).setVisibility(4);
        }
    }

    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, int i, boolean z, StateListDrawable stateListDrawable, int i2, IconInjector iconInjector) {
        super(context);
        this.mNotifyChanged = new Runnable() {
            public final void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mLayoutResId = C1777R.layout.preference_access_point;
        this.mWidgetLayoutResId = C1777R.layout.access_point_friction_widget;
        this.mAccessPoint = accessPoint;
        Objects.requireNonNull(accessPoint);
        this.mLevel = i2;
        this.mFrictionSld = stateListDrawable;
        this.mBadgePadding = context.getResources().getDimensionPixelSize(C1777R.dimen.wifi_preference_badge_padding);
    }
}
