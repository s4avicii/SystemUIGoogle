package com.android.systemui.hdmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2;
import com.android.systemui.p008tv.TvBottomSheetActivity;
import java.util.Locale;
import java.util.Objects;

public class HdmiCecSetMenuLanguageActivity extends TvBottomSheetActivity implements View.OnClickListener {
    public final HdmiCecSetMenuLanguageHelper mHdmiCecSetMenuLanguageHelper;

    public HdmiCecSetMenuLanguageActivity(HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper) {
        this.mHdmiCecSetMenuLanguageHelper = hdmiCecSetMenuLanguageHelper;
    }

    public final void onClick(View view) {
        if (view.getId() == C1777R.C1779id.bottom_sheet_positive_button) {
            HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper = this.mHdmiCecSetMenuLanguageHelper;
            Objects.requireNonNull(hdmiCecSetMenuLanguageHelper);
            hdmiCecSetMenuLanguageHelper.mBackgroundExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda2(hdmiCecSetMenuLanguageHelper, 2));
        } else {
            HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper2 = this.mHdmiCecSetMenuLanguageHelper;
            Objects.requireNonNull(hdmiCecSetMenuLanguageHelper2);
            hdmiCecSetMenuLanguageHelper2.mDenylist.add(hdmiCecSetMenuLanguageHelper2.mLocale.toLanguageTag());
            hdmiCecSetMenuLanguageHelper2.mSecureSettings.putString("hdmi_cec_set_menu_language_denylist", String.join(",", hdmiCecSetMenuLanguageHelper2.mDenylist));
        }
        finish();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(524288);
        String stringExtra = getIntent().getStringExtra("android.hardware.hdmi.extra.LOCALE");
        HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper = this.mHdmiCecSetMenuLanguageHelper;
        Objects.requireNonNull(hdmiCecSetMenuLanguageHelper);
        hdmiCecSetMenuLanguageHelper.mLocale = Locale.forLanguageTag(stringExtra);
        HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper2 = this.mHdmiCecSetMenuLanguageHelper;
        Objects.requireNonNull(hdmiCecSetMenuLanguageHelper2);
        if (hdmiCecSetMenuLanguageHelper2.mDenylist.contains(hdmiCecSetMenuLanguageHelper2.mLocale.toLanguageTag())) {
            finish();
        }
    }

    public final void onResume() {
        super.onResume();
        HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper = this.mHdmiCecSetMenuLanguageHelper;
        Objects.requireNonNull(hdmiCecSetMenuLanguageHelper);
        String string = getString(C1777R.string.hdmi_cec_set_menu_language_title, new Object[]{hdmiCecSetMenuLanguageHelper.mLocale.getDisplayLanguage()});
        String string2 = getString(C1777R.string.hdmi_cec_set_menu_language_description);
        Button button = (Button) findViewById(C1777R.C1779id.bottom_sheet_positive_button);
        Button button2 = (Button) findViewById(C1777R.C1779id.bottom_sheet_negative_button);
        ((TextView) findViewById(C1777R.C1779id.bottom_sheet_title)).setText(string);
        ((TextView) findViewById(C1777R.C1779id.bottom_sheet_body)).setText(string2);
        ((ImageView) findViewById(C1777R.C1779id.bottom_sheet_icon)).setImageResource(17302842);
        ((ImageView) findViewById(C1777R.C1779id.bottom_sheet_second_icon)).setVisibility(8);
        button.setText(C1777R.string.hdmi_cec_set_menu_language_accept);
        button.setOnClickListener(this);
        button2.setText(C1777R.string.hdmi_cec_set_menu_language_decline);
        button2.setOnClickListener(this);
        button2.requestFocus();
    }

    static {
        Class<HdmiCecSetMenuLanguageActivity> cls = HdmiCecSetMenuLanguageActivity.class;
    }
}
