package com.android.systemui.p006qs;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.systemui.statusbar.phone.MultiUserSwitch;
import com.android.systemui.statusbar.phone.SettingsButton;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.FooterActionsView */
/* compiled from: FooterActionsView.kt */
public final class FooterActionsView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageView multiUserAvatar;
    public MultiUserSwitch multiUserSwitch;
    public boolean qsDisabled;
    public SettingsButton settingsButton;
    public View settingsContainer;

    public final void onUserInfoChanged(Drawable drawable, boolean z) {
        if (drawable != null && z && !(drawable instanceof UserIconDrawable)) {
            drawable = drawable.getConstantState().newDrawable(getResources()).mutate();
            drawable.setColorFilter(Utils.getColorAttrDefaultColor(this.mContext, 16842800), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView = this.multiUserAvatar;
        if (imageView == null) {
            imageView = null;
        }
        imageView.setImageDrawable(drawable);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.settingsButton = (SettingsButton) findViewById(C1777R.C1779id.settings_button);
        this.settingsContainer = findViewById(C1777R.C1779id.settings_button_container);
        MultiUserSwitch multiUserSwitch2 = (MultiUserSwitch) findViewById(C1777R.C1779id.multi_user_switch);
        this.multiUserSwitch = multiUserSwitch2;
        this.multiUserAvatar = (ImageView) multiUserSwitch2.findViewById(C1777R.C1779id.multi_user_avatar);
        SettingsButton settingsButton2 = this.settingsButton;
        SettingsButton settingsButton3 = null;
        if (settingsButton2 == null) {
            settingsButton2 = null;
        }
        if (settingsButton2.getBackground() instanceof RippleDrawable) {
            SettingsButton settingsButton4 = this.settingsButton;
            if (settingsButton4 != null) {
                settingsButton3 = settingsButton4;
            }
            Drawable background = settingsButton3.getBackground();
            Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.RippleDrawable");
            ((RippleDrawable) background).setForceSoftware(true);
        }
        setImportantForAccessibility(1);
    }

    public FooterActionsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
