package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.leanback.R$raw;
import com.android.p012wm.shell.C1777R;
import java.text.DecimalFormat;

/* renamed from: com.android.systemui.qs.tiles.DataUsageDetailView */
public class DataUsageDetailView extends LinearLayout {
    public DataUsageDetailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new DecimalFormat("#.##");
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        R$raw.updateFontSize(this, 16908310, C1777R.dimen.qs_data_usage_text_size);
        R$raw.updateFontSize(this, C1777R.C1779id.usage_text, C1777R.dimen.qs_data_usage_usage_text_size);
        R$raw.updateFontSize(this, C1777R.C1779id.usage_carrier_text, C1777R.dimen.qs_data_usage_text_size);
        R$raw.updateFontSize(this, C1777R.C1779id.usage_info_top_text, C1777R.dimen.qs_data_usage_text_size);
        R$raw.updateFontSize(this, C1777R.C1779id.usage_period_text, C1777R.dimen.qs_data_usage_text_size);
        R$raw.updateFontSize(this, C1777R.C1779id.usage_info_bottom_text, C1777R.dimen.qs_data_usage_text_size);
    }
}
