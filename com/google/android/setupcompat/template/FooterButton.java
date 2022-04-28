package com.google.android.setupcompat.template;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.systemui.R$id;
import com.android.systemui.plugins.FalsingManager;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import java.util.HashMap;

public final class FooterButton implements View.OnClickListener {
    public OnButtonEventListener buttonListener;
    public final int buttonType;
    public int clickCount = 0;
    public boolean enabled = true;
    public View.OnClickListener onClickListener;
    public CharSequence text;
    public int theme;

    public interface OnButtonEventListener {
    }

    public FooterButton(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$id.SucFooterButton);
        this.text = obtainStyledAttributes.getString(1);
        this.onClickListener = null;
        int i = obtainStyledAttributes.getInt(2, 0);
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("Not a ButtonType");
        }
        this.buttonType = i;
        this.theme = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
    }

    @TargetApi(29)
    public final PersistableBundle getMetrics(String str) {
        String str2;
        PersistableBundle persistableBundle = new PersistableBundle();
        String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, "_text");
        String charSequence = this.text.toString();
        if (charSequence.length() > 50) {
            charSequence = String.format("%s…", new Object[]{charSequence.substring(0, 49)});
        }
        persistableBundle.putString(m, charSequence);
        String str3 = str + "_type";
        switch (this.buttonType) {
            case 1:
                str2 = "ADD_ANOTHER";
                break;
            case 2:
                str2 = "CANCEL";
                break;
            case 3:
                str2 = "CLEAR";
                break;
            case 4:
                str2 = "DONE";
                break;
            case 5:
                str2 = "NEXT";
                break;
            case FalsingManager.VERSION:
                str2 = "OPT_IN";
                break;
            case 7:
                str2 = "SKIP";
                break;
            case 8:
                str2 = "STOP";
                break;
            default:
                str2 = "OTHER";
                break;
        }
        persistableBundle.putString(str3, str2);
        persistableBundle.putInt(str + "_onClickCount", this.clickCount);
        return persistableBundle;
    }

    public final void onClick(View view) {
        View.OnClickListener onClickListener2 = this.onClickListener;
        if (onClickListener2 != null) {
            this.clickCount++;
            onClickListener2.onClick(view);
        }
    }

    public final void setEnabled(boolean z) {
        Button button;
        PartnerConfig partnerConfig;
        PartnerConfig partnerConfig2;
        this.enabled = z;
        OnButtonEventListener onButtonEventListener = this.buttonListener;
        if (onButtonEventListener != null) {
            FooterBarMixin.C21491 r3 = (FooterBarMixin.C21491) onButtonEventListener;
            LinearLayout linearLayout = FooterBarMixin.this.buttonContainer;
            if (linearLayout != null && (button = (Button) linearLayout.findViewById(id)) != null) {
                button.setEnabled(z);
                FooterBarMixin footerBarMixin = FooterBarMixin.this;
                if (footerBarMixin.applyPartnerResources && !footerBarMixin.applyDynamicColor) {
                    int i = id;
                    int i2 = footerBarMixin.primaryButtonId;
                    if (i == i2 || footerBarMixin.isSecondaryButtonInPrimaryStyle) {
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR;
                    } else {
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR;
                    }
                    if (i == i2 || footerBarMixin.isSecondaryButtonInPrimaryStyle) {
                        partnerConfig2 = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR;
                    } else {
                        partnerConfig2 = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR;
                    }
                    if (button.isEnabled()) {
                        Context context = footerBarMixin.context;
                        HashMap<Integer, ColorStateList> hashMap = FooterButtonStyleUtils.defaultTextColor;
                        int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig);
                        if (color != 0) {
                            button.setTextColor(ColorStateList.valueOf(color));
                            return;
                        }
                        return;
                    }
                    FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(footerBarMixin.context, button, partnerConfig2);
                }
            }
        }
    }

    public FooterButton(String str, View.OnClickListener onClickListener2) {
        this.text = str;
        this.onClickListener = onClickListener2;
        this.buttonType = 0;
        this.theme = 0;
    }
}
