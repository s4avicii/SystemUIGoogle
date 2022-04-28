package com.android.systemui.p006qs.tiles.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.wifitrackerlib.WifiEntry;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetAdapter */
public final class InternetAdapter extends RecyclerView.Adapter<InternetViewHolder> {
    public View mHolderView;
    public final InternetDialogController mInternetDialogController;
    @VisibleForTesting
    public int mMaxEntriesCount = 3;
    public List<WifiEntry> mWifiEntries;
    @VisibleForTesting
    public int mWifiEntriesCount;

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder */
    public static class InternetViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final InternetDialogController mInternetDialogController;
        public final ImageView mWifiEndIcon;
        public final ImageView mWifiIcon;
        @VisibleForTesting
        public WifiUtils.InternetIconInjector mWifiIconInjector;
        public final LinearLayout mWifiListLayout;
        public final TextView mWifiSummaryText;
        public final TextView mWifiTitleText;

        public InternetViewHolder(View view, InternetDialogController internetDialogController) {
            super(view);
            this.mContext = view.getContext();
            this.mInternetDialogController = internetDialogController;
            LinearLayout linearLayout = (LinearLayout) view.requireViewById(C1777R.C1779id.internet_container);
            this.mWifiListLayout = (LinearLayout) view.requireViewById(C1777R.C1779id.wifi_list);
            LinearLayout linearLayout2 = (LinearLayout) view.requireViewById(C1777R.C1779id.wifi_network_layout);
            this.mWifiIcon = (ImageView) view.requireViewById(C1777R.C1779id.wifi_icon);
            this.mWifiTitleText = (TextView) view.requireViewById(C1777R.C1779id.wifi_title);
            this.mWifiSummaryText = (TextView) view.requireViewById(C1777R.C1779id.wifi_summary);
            this.mWifiEndIcon = (ImageView) view.requireViewById(C1777R.C1779id.wifi_end_icon);
            Objects.requireNonNull(internetDialogController);
            this.mWifiIconInjector = internetDialogController.mWifiIconInjector;
        }
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Drawable drawable;
        Drawable icon;
        InternetViewHolder internetViewHolder = (InternetViewHolder) viewHolder;
        List<WifiEntry> list = this.mWifiEntries;
        if (list != null && i < this.mWifiEntriesCount) {
            WifiEntry wifiEntry = list.get(i);
            ImageView imageView = internetViewHolder.mWifiIcon;
            Objects.requireNonNull(wifiEntry);
            int i2 = wifiEntry.mLevel;
            boolean shouldShowXLevelIcon = wifiEntry.shouldShowXLevelIcon();
            Drawable drawable2 = null;
            if (i2 == -1 || (icon = internetViewHolder.mWifiIconInjector.getIcon(shouldShowXLevelIcon, i2)) == null) {
                drawable = null;
            } else {
                icon.setTint(Utils.getColorAttrDefaultColor(internetViewHolder.mContext, 16843282));
                AtomicReference atomicReference = new AtomicReference();
                atomicReference.set(icon);
                drawable = (Drawable) atomicReference.get();
            }
            imageView.setImageDrawable(drawable);
            String title = wifiEntry.getTitle();
            Spanned fromHtml = Html.fromHtml(wifiEntry.getSummary(false), 0);
            internetViewHolder.mWifiTitleText.setText(title);
            if (TextUtils.isEmpty(fromHtml)) {
                internetViewHolder.mWifiSummaryText.setVisibility(8);
            } else {
                internetViewHolder.mWifiSummaryText.setVisibility(0);
                internetViewHolder.mWifiSummaryText.setText(fromHtml);
            }
            int connectedState = wifiEntry.getConnectedState();
            char c = 3;
            switch (com.android.wifitrackerlib.Utils.getSingleSecurityTypeFromMultipleSecurityTypes(wifiEntry.getSecurityTypes())) {
                case 1:
                    c = 1;
                    break;
                case 2:
                    c = 2;
                    break;
                case 3:
                case QSTileImpl.C1034H.STALE:
                case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                    break;
                case 4:
                    c = 5;
                    break;
                case 5:
                    c = 6;
                    break;
                case FalsingManager.VERSION:
                    c = 4;
                    break;
                case 9:
                    c = 7;
                    break;
                default:
                    c = 0;
                    break;
            }
            if (connectedState != 0) {
                drawable2 = internetViewHolder.mContext.getDrawable(C1777R.C1778drawable.ic_settings_24dp);
            } else if (!(c == 0 || c == 4)) {
                drawable2 = internetViewHolder.mContext.getDrawable(C1777R.C1778drawable.ic_friction_lock_closed);
            }
            if (drawable2 == null) {
                internetViewHolder.mWifiEndIcon.setVisibility(8);
            } else {
                internetViewHolder.mWifiEndIcon.setVisibility(0);
                internetViewHolder.mWifiEndIcon.setImageDrawable(drawable2);
            }
            if (connectedState != 0) {
                internetViewHolder.mWifiListLayout.setOnClickListener(new InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0(internetViewHolder, wifiEntry));
            } else {
                internetViewHolder.mWifiListLayout.setOnClickListener(new InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda1(internetViewHolder, wifiEntry));
            }
        }
    }

    public InternetAdapter(InternetDialogController internetDialogController) {
        this.mInternetDialogController = internetDialogController;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        this.mHolderView = LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.internet_list_item, recyclerView, false);
        return new InternetViewHolder(this.mHolderView, this.mInternetDialogController);
    }

    public final int getItemCount() {
        return this.mWifiEntriesCount;
    }
}
