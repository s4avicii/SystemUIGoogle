package com.android.systemui.p006qs.tiles.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda1;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.DozeParameters$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda12;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda8;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog */
public final class InternetDialog extends SystemUIDialog implements InternetDialogController.InternetDialogCallback {
    public static final boolean DEBUG = Log.isLoggable("InternetDialog", 3);
    public InternetAdapter mAdapter;
    public Button mAirplaneModeButton;
    public TextView mAirplaneModeSummaryText;
    public AlertDialog mAlertDialog;
    public final Executor mBackgroundExecutor;
    public Drawable mBackgroundOff = null;
    public Drawable mBackgroundOn;
    public boolean mCanConfigMobileData;
    public boolean mCanConfigWifi;
    public LinearLayout mConnectedWifListLayout;
    public WifiEntry mConnectedWifiEntry;
    public ImageView mConnectedWifiIcon;
    public TextView mConnectedWifiSummaryText;
    public TextView mConnectedWifiTitleText;
    public Context mContext;
    public int mDefaultDataSubId = -1;
    public View mDialogView;
    public View mDivider;
    public Button mDoneButton;
    public LinearLayout mEthernetLayout;
    public final Handler mHandler;
    public boolean mHasMoreWifiEntries;
    public final DozeUi$$ExternalSyntheticLambda1 mHideProgressBarRunnable = new DozeUi$$ExternalSyntheticLambda1(this, 5);
    public ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 mHideSearchingRunnable = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 4);
    public InternetDialogController mInternetDialogController;
    public InternetDialogFactory mInternetDialogFactory;
    public TextView mInternetDialogSubTitle;
    public TextView mInternetDialogTitle;
    public boolean mIsProgressBarVisible;
    public boolean mIsSearchingHidden;
    public KeyguardStateController mKeyguard;
    public Switch mMobileDataToggle;
    public LinearLayout mMobileNetworkLayout;
    public TextView mMobileSummaryText;
    public TextView mMobileTitleText;
    public View mMobileToggleDivider;
    public ProgressBar mProgressBar;
    public LinearLayout mSeeAllLayout;
    public ImageView mSignalIcon;
    public TelephonyManager mTelephonyManager;
    public LinearLayout mTurnWifiOnLayout;
    public UiEventLogger mUiEventLogger;
    public Switch mWiFiToggle;
    public int mWifiEntriesCount;
    public WifiManager mWifiManager;
    public int mWifiNetworkHeight;
    public RecyclerView mWifiRecyclerView;
    public LinearLayout mWifiScanNotifyLayout;
    public TextView mWifiScanNotifyText;
    public ImageView mWifiSettingsIcon;
    public TextView mWifiToggleTitleText;

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$InternetDialogEvent */
    public enum InternetDialogEvent implements UiEventLogger.UiEventEnum {
        ;
        
        private final int mId;

        /* access modifiers changed from: public */
        InternetDialogEvent() {
            this.mId = 843;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public void hideWifiViews() {
        setProgressBarVisible(false);
        this.mTurnWifiOnLayout.setVisibility(8);
        this.mConnectedWifListLayout.setVisibility(8);
        this.mWifiRecyclerView.setVisibility(8);
        this.mSeeAllLayout.setVisibility(8);
    }

    public final CharSequence getDialogTitleText() {
        InternetDialogController internetDialogController = this.mInternetDialogController;
        Objects.requireNonNull(internetDialogController);
        if (internetDialogController.isAirplaneModeEnabled()) {
            return internetDialogController.mContext.getText(C1777R.string.airplane_mode);
        }
        return internetDialogController.mContext.getText(C1777R.string.quick_settings_internet_label);
    }

    public final CharSequence getMobileNetworkTitle() {
        InternetDialogController internetDialogController = this.mInternetDialogController;
        Objects.requireNonNull(internetDialogController);
        int i = internetDialogController.mDefaultDataSubId;
        Context context = internetDialogController.mContext;
        InternetDialogController$$ExternalSyntheticLambda6 internetDialogController$$ExternalSyntheticLambda6 = new InternetDialogController$$ExternalSyntheticLambda6(internetDialogController);
        HashSet hashSet = new HashSet();
        Set set = (Set) ((Stream) internetDialogController$$ExternalSyntheticLambda6.get()).filter(new InternetDialogController$$ExternalSyntheticLambda4(hashSet)).map(WifiPickerTracker$$ExternalSyntheticLambda8.INSTANCE$1).collect(Collectors.toSet());
        hashSet.clear();
        return (CharSequence) ((Map) ((Stream) internetDialogController$$ExternalSyntheticLambda6.get()).map(new InternetDialogController$$ExternalSyntheticLambda2(set, context)).map(new InternetDialogController$$ExternalSyntheticLambda1((Set) ((Stream) internetDialogController$$ExternalSyntheticLambda6.get()).map(new InternetDialogController$$ExternalSyntheticLambda2(set, context)).filter(new WifiPickerTracker$$ExternalSyntheticLambda12(hashSet, 2)).map(InternetDialogController$$ExternalSyntheticLambda3.INSTANCE).collect(Collectors.toSet()))).collect(Collectors.toMap(DozeParameters$$ExternalSyntheticLambda0.INSTANCE$2, PeopleSpaceUtils$$ExternalSyntheticLambda4.INSTANCE$1))).getOrDefault(Integer.valueOf(i), "");
    }

    public final CharSequence getSubtitleText() {
        boolean z;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        if (!this.mIsProgressBarVisible || this.mIsSearchingHidden) {
            z = false;
        } else {
            z = true;
        }
        Objects.requireNonNull(internetDialogController);
        if (internetDialogController.mCanConfigWifi && !internetDialogController.mWifiManager.isWifiEnabled()) {
            if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "Wi-Fi off.");
            }
            return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_WIFI_IS_OFF);
        } else if (!internetDialogController.mKeyguardStateController.isUnlocked()) {
            if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "The device is locked.");
            }
            return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS);
        } else {
            if (internetDialogController.mHasWifiEntries) {
                if (internetDialogController.mCanConfigWifi) {
                    return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT);
                }
            } else if (internetDialogController.mCanConfigWifi && z) {
                return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS);
            } else {
                if (internetDialogController.isCarrierNetworkActive()) {
                    return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
                }
                boolean z2 = InternetDialogController.DEBUG;
                if (z2) {
                    Log.d("InternetDialogController", "No Wi-Fi item.");
                }
                if (!internetDialogController.hasActiveSubId() || (!internetDialogController.isVoiceStateInService() && !internetDialogController.isDataStateInService())) {
                    if (z2) {
                        Log.d("InternetDialogController", "No carrier or service is out of service.");
                    }
                    return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE);
                } else if (internetDialogController.mCanConfigWifi && !internetDialogController.isMobileDataEnabled()) {
                    if (z2) {
                        Log.d("InternetDialogController", "Mobile data off");
                    }
                    return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
                } else if (!internetDialogController.activeNetworkIsCellular()) {
                    if (z2) {
                        Log.d("InternetDialogController", "No carrier data.");
                    }
                    return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE);
                } else if (internetDialogController.mCanConfigWifi) {
                    return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
                }
            }
            return null;
        }
    }

    public int getWifiListMaxCount() {
        int i;
        int i2 = 3;
        if (this.mEthernetLayout.getVisibility() == 0) {
            i = 3;
        } else {
            i = 4;
        }
        if (this.mMobileNetworkLayout.getVisibility() == 0) {
            i--;
        }
        if (i <= 3) {
            i2 = i;
        }
        if (this.mConnectedWifListLayout.getVisibility() == 0) {
            return i2 - 1;
        }
        return i2;
    }

    public final void setProgressBarVisible(boolean z) {
        int i;
        if (this.mIsProgressBarVisible != z) {
            this.mIsProgressBarVisible = z;
            ProgressBar progressBar = this.mProgressBar;
            int i2 = 0;
            if (z) {
                i = 0;
            } else {
                i = 8;
            }
            progressBar.setVisibility(i);
            this.mProgressBar.setIndeterminate(z);
            View view = this.mDivider;
            if (z) {
                i2 = 8;
            }
            view.setVisibility(i2);
            this.mInternetDialogSubTitle.setText(getSubtitleText());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:112:0x024d  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0268  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0286  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0289  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x02f5 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x034b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x014c  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01a4  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01ad  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01b0  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01d2  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01e2  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01f0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateDialog(boolean r14) {
        /*
            r13 = this;
            boolean r0 = DEBUG
            java.lang.String r1 = "InternetDialog"
            if (r0 == 0) goto L_0x000c
            java.lang.String r2 = "updateDialog"
            android.util.Log.d(r1, r2)
        L_0x000c:
            android.widget.TextView r2 = r13.mInternetDialogTitle
            java.lang.CharSequence r3 = r13.getDialogTitleText()
            r2.setText(r3)
            android.widget.TextView r2 = r13.mInternetDialogSubTitle
            java.lang.CharSequence r3 = r13.getSubtitleText()
            r2.setText(r3)
            android.widget.Button r2 = r13.mAirplaneModeButton
            com.android.systemui.qs.tiles.dialog.InternetDialogController r3 = r13.mInternetDialogController
            boolean r3 = r3.isAirplaneModeEnabled()
            r4 = 8
            r5 = 0
            if (r3 == 0) goto L_0x002d
            r3 = r5
            goto L_0x002e
        L_0x002d:
            r3 = r4
        L_0x002e:
            r2.setVisibility(r3)
            android.widget.LinearLayout r2 = r13.mEthernetLayout
            com.android.systemui.qs.tiles.dialog.InternetDialogController r3 = r13.mInternetDialogController
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mHasEthernet
            if (r3 == 0) goto L_0x003e
            r3 = r5
            goto L_0x003f
        L_0x003e:
            r3 = r4
        L_0x003f:
            r2.setVisibility(r3)
            r2 = 4
            r3 = 2132017913(0x7f1402f9, float:1.9674118E38)
            r6 = 2132017912(0x7f1402f8, float:1.9674116E38)
            r7 = 1
            if (r14 == 0) goto L_0x01f4
            com.android.systemui.qs.tiles.dialog.InternetDialogController r14 = r13.mInternetDialogController
            boolean r14 = r14.activeNetworkIsCellular()
            com.android.systemui.qs.tiles.dialog.InternetDialogController r8 = r13.mInternetDialogController
            boolean r8 = r8.isCarrierNetworkActive()
            if (r14 != 0) goto L_0x005f
            if (r8 == 0) goto L_0x005d
            goto L_0x005f
        L_0x005d:
            r14 = r5
            goto L_0x0060
        L_0x005f:
            r14 = r7
        L_0x0060:
            if (r0 == 0) goto L_0x0068
            java.lang.String r0 = "setMobileDataLayout, isCarrierNetworkActive = "
            androidx.core.view.ViewCompat$$ExternalSyntheticLambda0.m12m(r0, r8, r1)
        L_0x0068:
            android.net.wifi.WifiManager r0 = r13.mWifiManager
            if (r0 == 0) goto L_0x0074
            boolean r0 = r0.isWifiEnabled()
            if (r0 == 0) goto L_0x0074
            r0 = r7
            goto L_0x0075
        L_0x0074:
            r0 = r5
        L_0x0075:
            com.android.systemui.qs.tiles.dialog.InternetDialogController r1 = r13.mInternetDialogController
            boolean r1 = r1.hasActiveSubId()
            if (r1 != 0) goto L_0x0088
            if (r0 == 0) goto L_0x0081
            if (r8 != 0) goto L_0x0088
        L_0x0081:
            android.widget.LinearLayout r14 = r13.mMobileNetworkLayout
            r14.setVisibility(r4)
            goto L_0x01f4
        L_0x0088:
            android.widget.LinearLayout r0 = r13.mMobileNetworkLayout
            r0.setVisibility(r5)
            android.widget.Switch r0 = r13.mMobileDataToggle
            com.android.systemui.qs.tiles.dialog.InternetDialogController r1 = r13.mInternetDialogController
            boolean r1 = r1.isMobileDataEnabled()
            r0.setChecked(r1)
            android.widget.TextView r0 = r13.mMobileTitleText
            java.lang.CharSequence r1 = r13.getMobileNetworkTitle()
            r0.setText(r1)
            com.android.systemui.qs.tiles.dialog.InternetDialogController r0 = r13.mInternetDialogController
            java.util.Objects.requireNonNull(r0)
            android.content.Context r1 = r0.mContext
            com.android.settingslib.mobile.MobileMappings$Config r8 = r0.mConfig
            android.telephony.TelephonyDisplayInfo r9 = r0.mTelephonyDisplayInfo
            int r10 = r0.mDefaultDataSubId
            int r11 = r9.getOverrideNetworkType()
            if (r11 != 0) goto L_0x00bd
            int r9 = r9.getNetworkType()
            java.lang.String r9 = java.lang.Integer.toString(r9)
            goto L_0x00c5
        L_0x00bd:
            int r9 = r9.getOverrideNetworkType()
            java.lang.String r9 = com.android.settingslib.mobile.MobileMappings.toDisplayIconKey(r9)
        L_0x00c5:
            com.android.settingslib.mobile.MobileMappings.mapIconSets(r8)
            java.util.HashMap r11 = com.android.settingslib.mobile.MobileMappings.mapIconSets(r8)
            java.lang.Object r11 = r11.get(r9)
            if (r11 != 0) goto L_0x00de
            boolean r1 = com.android.systemui.p006qs.tiles.dialog.InternetDialogController.DEBUG
            if (r1 == 0) goto L_0x0103
            java.lang.String r1 = "InternetDialogController"
            java.lang.String r8 = "The description of network type is empty."
            android.util.Log.d(r1, r8)
            goto L_0x0103
        L_0x00de:
            java.util.HashMap r8 = com.android.settingslib.mobile.MobileMappings.mapIconSets(r8)
            java.lang.Object r8 = r8.get(r9)
            com.android.settingslib.SignalIcon$MobileIconGroup r8 = (com.android.settingslib.SignalIcon$MobileIconGroup) r8
            java.util.Objects.requireNonNull(r8)
            int r8 = r8.dataContentDescription
            boolean r9 = r0.isCarrierNetworkActive()
            if (r9 == 0) goto L_0x00f8
            com.android.settingslib.SignalIcon$MobileIconGroup r8 = com.android.settingslib.mobile.TelephonyIcons.CARRIER_NETWORK_CHANGE
            r8 = 2131952229(0x7f130265, float:1.9540895E38)
        L_0x00f8:
            if (r8 == 0) goto L_0x0103
            android.content.res.Resources r1 = android.telephony.SubscriptionManager.getResourcesForSubId(r1, r10)
            java.lang.String r1 = r1.getString(r8)
            goto L_0x0105
        L_0x0103:
            java.lang.String r1 = ""
        L_0x0105:
            android.content.Context r8 = r0.mContext
            boolean r9 = r0.isMobileDataEnabled()
            if (r9 != 0) goto L_0x0115
            r0 = 2131952772(0x7f130484, float:1.9541996E38)
            java.lang.String r0 = r8.getString(r0)
            goto L_0x0146
        L_0x0115:
            boolean r9 = r0.activeNetworkIsCellular()
            if (r9 != 0) goto L_0x0130
            boolean r9 = r0.isCarrierNetworkActive()
            if (r9 == 0) goto L_0x0122
            goto L_0x0130
        L_0x0122:
            boolean r0 = r0.isDataStateInService()
            if (r0 != 0) goto L_0x0145
            r0 = 2131952771(0x7f130483, float:1.9541994E38)
            java.lang.String r1 = r8.getString(r0)
            goto L_0x0145
        L_0x0130:
            r0 = 2131953021(0x7f13057d, float:1.9542501E38)
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r10 = 2131952767(0x7f13047f, float:1.9541986E38)
            java.lang.String r10 = r8.getString(r10)
            r9[r5] = r10
            r9[r7] = r1
            java.lang.String r1 = r8.getString(r0, r9)
        L_0x0145:
            r0 = r1
        L_0x0146:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x015b
            android.widget.TextView r1 = r13.mMobileSummaryText
            android.text.Spanned r0 = android.text.Html.fromHtml(r0, r5)
            r1.setText(r0)
            android.widget.TextView r0 = r13.mMobileSummaryText
            r0.setVisibility(r5)
            goto L_0x0160
        L_0x015b:
            android.widget.TextView r0 = r13.mMobileSummaryText
            r0.setVisibility(r4)
        L_0x0160:
            java.util.concurrent.Executor r0 = r13.mBackgroundExecutor
            com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2 r1 = new com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2
            r8 = 3
            r1.<init>(r13, r8)
            r0.execute(r1)
            android.widget.TextView r0 = r13.mMobileTitleText
            if (r14 == 0) goto L_0x0171
            r1 = r3
            goto L_0x0172
        L_0x0171:
            r1 = r6
        L_0x0172:
            r0.setTextAppearance(r1)
            if (r14 == 0) goto L_0x017b
            r0 = 2132017915(0x7f1402fb, float:1.9674122E38)
            goto L_0x017e
        L_0x017b:
            r0 = 2132017914(0x7f1402fa, float:1.967412E38)
        L_0x017e:
            android.widget.TextView r1 = r13.mMobileSummaryText
            r1.setTextAppearance(r0)
            com.android.systemui.qs.tiles.dialog.InternetDialogController r1 = r13.mInternetDialogController
            boolean r1 = r1.isAirplaneModeEnabled()
            if (r1 == 0) goto L_0x01a4
            android.widget.TextView r1 = r13.mAirplaneModeSummaryText
            r1.setVisibility(r5)
            android.widget.TextView r1 = r13.mAirplaneModeSummaryText
            android.content.Context r8 = r13.mContext
            r9 = 2131951873(0x7f130101, float:1.9540173E38)
            java.lang.CharSequence r8 = r8.getText(r9)
            r1.setText(r8)
            android.widget.TextView r1 = r13.mAirplaneModeSummaryText
            r1.setTextAppearance(r0)
            goto L_0x01a9
        L_0x01a4:
            android.widget.TextView r0 = r13.mAirplaneModeSummaryText
            r0.setVisibility(r4)
        L_0x01a9:
            android.widget.LinearLayout r0 = r13.mMobileNetworkLayout
            if (r14 == 0) goto L_0x01b0
            android.graphics.drawable.Drawable r1 = r13.mBackgroundOn
            goto L_0x01b2
        L_0x01b0:
            android.graphics.drawable.Drawable r1 = r13.mBackgroundOff
        L_0x01b2:
            r0.setBackground(r1)
            android.content.Context r0 = r13.mContext
            r1 = 2132017502(0x7f14015e, float:1.9673284E38)
            int[] r8 = new int[r7]
            r9 = 16842964(0x10100d4, float:2.3694152E-38)
            r8[r5] = r9
            android.content.res.TypedArray r0 = r0.obtainStyledAttributes(r1, r8)
            android.content.Context r1 = r13.mContext
            r8 = 16842808(0x1010038, float:2.3693715E-38)
            int r1 = com.android.settingslib.Utils.getColorAttrDefaultColor(r1, r8)
            android.view.View r8 = r13.mMobileToggleDivider
            if (r14 == 0) goto L_0x01d6
            int r1 = r0.getColor(r5, r1)
        L_0x01d6:
            r8.setBackgroundColor(r1)
            r0.recycle()
            android.widget.Switch r14 = r13.mMobileDataToggle
            boolean r0 = r13.mCanConfigMobileData
            if (r0 == 0) goto L_0x01e4
            r0 = r5
            goto L_0x01e5
        L_0x01e4:
            r0 = r2
        L_0x01e5:
            r14.setVisibility(r0)
            android.view.View r14 = r13.mMobileToggleDivider
            boolean r0 = r13.mCanConfigMobileData
            if (r0 == 0) goto L_0x01f0
            r0 = r5
            goto L_0x01f1
        L_0x01f0:
            r0 = r2
        L_0x01f1:
            r14.setVisibility(r0)
        L_0x01f4:
            boolean r14 = r13.mCanConfigWifi
            if (r14 != 0) goto L_0x01f9
            return
        L_0x01f9:
            android.net.wifi.WifiManager r14 = r13.mWifiManager
            if (r14 == 0) goto L_0x0234
            boolean r14 = r14.isWifiEnabled()
            if (r14 == 0) goto L_0x0234
            com.android.systemui.qs.tiles.dialog.InternetDialogController r14 = r13.mInternetDialogController
            java.util.Objects.requireNonNull(r14)
            com.android.systemui.statusbar.policy.KeyguardStateController r14 = r14.mKeyguardStateController
            boolean r14 = r14.isUnlocked()
            r14 = r14 ^ r7
            if (r14 == 0) goto L_0x0212
            goto L_0x0234
        L_0x0212:
            r13.setProgressBarVisible(r7)
            com.android.wifitrackerlib.WifiEntry r14 = r13.mConnectedWifiEntry
            r0 = 1500(0x5dc, double:7.41E-321)
            if (r14 != 0) goto L_0x022c
            int r14 = r13.mWifiEntriesCount
            if (r14 <= 0) goto L_0x0220
            goto L_0x022c
        L_0x0220:
            boolean r14 = r13.mIsSearchingHidden
            if (r14 != 0) goto L_0x0237
            android.os.Handler r14 = r13.mHandler
            com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 r8 = r13.mHideSearchingRunnable
            r14.postDelayed(r8, r0)
            goto L_0x0237
        L_0x022c:
            android.os.Handler r14 = r13.mHandler
            com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1 r8 = r13.mHideProgressBarRunnable
            r14.postDelayed(r8, r0)
            goto L_0x0237
        L_0x0234:
            r13.setProgressBarVisible(r5)
        L_0x0237:
            com.android.systemui.qs.tiles.dialog.InternetDialogController r14 = r13.mInternetDialogController
            java.util.Objects.requireNonNull(r14)
            com.android.systemui.statusbar.policy.KeyguardStateController r14 = r14.mKeyguardStateController
            boolean r14 = r14.isUnlocked()
            r14 = r14 ^ r7
            android.net.wifi.WifiManager r0 = r13.mWifiManager
            if (r0 == 0) goto L_0x024f
            boolean r0 = r0.isWifiEnabled()
            if (r0 == 0) goto L_0x024f
            r0 = r7
            goto L_0x0250
        L_0x024f:
            r0 = r5
        L_0x0250:
            com.android.systemui.qs.tiles.dialog.InternetDialogController r1 = r13.mInternetDialogController
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.policy.LocationController r8 = r1.mLocationController
            boolean r8 = r8.isLocationEnabled()
            if (r8 != 0) goto L_0x025e
            goto L_0x026a
        L_0x025e:
            android.net.wifi.WifiManager r1 = r1.mWifiManager
            if (r1 == 0) goto L_0x026a
            boolean r1 = r1.isScanAlwaysAvailable()
            if (r1 == 0) goto L_0x026a
            r1 = r7
            goto L_0x026b
        L_0x026a:
            r1 = r5
        L_0x026b:
            android.widget.Switch r8 = r13.mWiFiToggle
            r8.setChecked(r0)
            if (r14 == 0) goto L_0x027d
            android.widget.TextView r8 = r13.mWifiToggleTitleText
            com.android.wifitrackerlib.WifiEntry r9 = r13.mConnectedWifiEntry
            if (r9 == 0) goto L_0x0279
            goto L_0x027a
        L_0x0279:
            r3 = r6
        L_0x027a:
            r8.setTextAppearance(r3)
        L_0x027d:
            android.widget.LinearLayout r3 = r13.mTurnWifiOnLayout
            r6 = 0
            if (r14 == 0) goto L_0x0289
            com.android.wifitrackerlib.WifiEntry r8 = r13.mConnectedWifiEntry
            if (r8 == 0) goto L_0x0289
            android.graphics.drawable.Drawable r8 = r13.mBackgroundOn
            goto L_0x028a
        L_0x0289:
            r8 = r6
        L_0x028a:
            r3.setBackground(r8)
            if (r0 == 0) goto L_0x02ee
            com.android.wifitrackerlib.WifiEntry r3 = r13.mConnectedWifiEntry
            if (r3 == 0) goto L_0x02ee
            if (r14 == 0) goto L_0x0296
            goto L_0x02ee
        L_0x0296:
            android.widget.LinearLayout r3 = r13.mConnectedWifListLayout
            r3.setVisibility(r5)
            android.widget.TextView r3 = r13.mConnectedWifiTitleText
            com.android.wifitrackerlib.WifiEntry r8 = r13.mConnectedWifiEntry
            java.lang.String r8 = r8.getTitle()
            r3.setText(r8)
            android.widget.TextView r3 = r13.mConnectedWifiSummaryText
            com.android.wifitrackerlib.WifiEntry r8 = r13.mConnectedWifiEntry
            java.lang.String r8 = r8.getSummary(r5)
            r3.setText(r8)
            android.widget.ImageView r3 = r13.mConnectedWifiIcon
            com.android.systemui.qs.tiles.dialog.InternetDialogController r8 = r13.mInternetDialogController
            com.android.wifitrackerlib.WifiEntry r9 = r13.mConnectedWifiEntry
            java.util.Objects.requireNonNull(r8)
            java.util.Objects.requireNonNull(r9)
            int r10 = r9.mLevel
            r11 = -1
            r12 = 2131099775(0x7f06007f, float:1.7811913E38)
            if (r10 != r11) goto L_0x02c6
            goto L_0x02df
        L_0x02c6:
            com.android.settingslib.wifi.WifiUtils$InternetIconInjector r10 = r8.mWifiIconInjector
            boolean r11 = r9.shouldShowXLevelIcon()
            int r9 = r9.mLevel
            android.graphics.drawable.Drawable r9 = r10.getIcon(r11, r9)
            if (r9 != 0) goto L_0x02d5
            goto L_0x02df
        L_0x02d5:
            android.content.Context r6 = r8.mContext
            int r6 = r6.getColor(r12)
            r9.setTint(r6)
            r6 = r9
        L_0x02df:
            r3.setImageDrawable(r6)
            android.widget.ImageView r3 = r13.mWifiSettingsIcon
            android.content.Context r6 = r13.mContext
            int r6 = r6.getColor(r12)
            r3.setColorFilter(r6)
            goto L_0x02f3
        L_0x02ee:
            android.widget.LinearLayout r3 = r13.mConnectedWifListLayout
            r3.setVisibility(r4)
        L_0x02f3:
            if (r0 == 0) goto L_0x033f
            if (r14 == 0) goto L_0x02f8
            goto L_0x033f
        L_0x02f8:
            int r3 = r13.getWifiListMaxCount()
            com.android.systemui.qs.tiles.dialog.InternetAdapter r6 = r13.mAdapter
            java.util.Objects.requireNonNull(r6)
            int r6 = r6.mWifiEntriesCount
            if (r6 <= r3) goto L_0x0307
            r13.mHasMoreWifiEntries = r7
        L_0x0307:
            com.android.systemui.qs.tiles.dialog.InternetAdapter r6 = r13.mAdapter
            if (r3 < 0) goto L_0x031c
            int r8 = r6.mMaxEntriesCount
            if (r8 != r3) goto L_0x0310
            goto L_0x031f
        L_0x0310:
            r6.mMaxEntriesCount = r3
            int r8 = r6.mWifiEntriesCount
            if (r8 <= r3) goto L_0x031f
            r6.mWifiEntriesCount = r3
            r6.notifyDataSetChanged()
            goto L_0x031f
        L_0x031c:
            java.util.Objects.requireNonNull(r6)
        L_0x031f:
            int r6 = r13.mWifiNetworkHeight
            int r6 = r6 * r3
            androidx.recyclerview.widget.RecyclerView r3 = r13.mWifiRecyclerView
            int r3 = r3.getMinimumHeight()
            if (r3 == r6) goto L_0x032f
            androidx.recyclerview.widget.RecyclerView r3 = r13.mWifiRecyclerView
            r3.setMinimumHeight(r6)
        L_0x032f:
            androidx.recyclerview.widget.RecyclerView r3 = r13.mWifiRecyclerView
            r3.setVisibility(r5)
            android.widget.LinearLayout r3 = r13.mSeeAllLayout
            boolean r6 = r13.mHasMoreWifiEntries
            if (r6 == 0) goto L_0x033b
            r2 = r5
        L_0x033b:
            r3.setVisibility(r2)
            goto L_0x0349
        L_0x033f:
            androidx.recyclerview.widget.RecyclerView r2 = r13.mWifiRecyclerView
            r2.setVisibility(r4)
            android.widget.LinearLayout r2 = r13.mSeeAllLayout
            r2.setVisibility(r4)
        L_0x0349:
            if (r0 != 0) goto L_0x0392
            if (r1 == 0) goto L_0x0392
            if (r14 == 0) goto L_0x0350
            goto L_0x0392
        L_0x0350:
            android.widget.TextView r14 = r13.mWifiScanNotifyText
            java.lang.CharSequence r14 = r14.getText()
            boolean r14 = android.text.TextUtils.isEmpty(r14)
            if (r14 == 0) goto L_0x038c
            com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$LinkInfo r14 = new com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$LinkInfo
            com.android.systemui.qs.tiles.dialog.InternetDialogController r0 = r13.mInternetDialogController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3 r1 = new com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3
            r1.<init>(r0, r5)
            r14.<init>(r1)
            android.widget.TextView r0 = r13.mWifiScanNotifyText
            android.content.Context r1 = r13.getContext()
            r2 = 2131953571(0x7f1307a3, float:1.9543617E38)
            java.lang.CharSequence r1 = r1.getText(r2)
            com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$LinkInfo[] r2 = new com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan.LinkInfo[r7]
            r2[r5] = r14
            android.text.SpannableStringBuilder r14 = com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan.linkify(r1, r2)
            r0.setText(r14)
            android.widget.TextView r14 = r13.mWifiScanNotifyText
            android.text.method.MovementMethod r0 = android.text.method.LinkMovementMethod.getInstance()
            r14.setMovementMethod(r0)
        L_0x038c:
            android.widget.LinearLayout r13 = r13.mWifiScanNotifyLayout
            r13.setVisibility(r5)
            goto L_0x0397
        L_0x0392:
            android.widget.LinearLayout r13 = r13.mWifiScanNotifyLayout
            r13.setVisibility(r4)
        L_0x0397:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.dialog.InternetDialog.updateDialog(boolean):void");
    }

    public InternetDialog(Context context, InternetDialogFactory internetDialogFactory, InternetDialogController internetDialogController, boolean z, boolean z2, UiEventLogger uiEventLogger, Handler handler, Executor executor, KeyguardStateController keyguardStateController) {
        super(context);
        if (DEBUG) {
            Log.d("InternetDialog", "Init InternetDialog");
        }
        this.mContext = getContext();
        this.mHandler = handler;
        this.mBackgroundExecutor = executor;
        this.mInternetDialogFactory = internetDialogFactory;
        this.mInternetDialogController = internetDialogController;
        Objects.requireNonNull(internetDialogController);
        this.mDefaultDataSubId = this.mInternetDialogController.getDefaultDataSubscriptionId();
        InternetDialogController internetDialogController2 = this.mInternetDialogController;
        Objects.requireNonNull(internetDialogController2);
        this.mTelephonyManager = internetDialogController2.mTelephonyManager;
        InternetDialogController internetDialogController3 = this.mInternetDialogController;
        Objects.requireNonNull(internetDialogController3);
        this.mWifiManager = internetDialogController3.mWifiManager;
        this.mCanConfigMobileData = z;
        this.mCanConfigWifi = z2;
        this.mKeyguard = keyguardStateController;
        this.mUiEventLogger = uiEventLogger;
        this.mAdapter = new InternetAdapter(this.mInternetDialogController);
    }

    /* JADX INFO: finally extract failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (DEBUG) {
            Log.d("InternetDialog", "onCreate");
        }
        this.mUiEventLogger.log(InternetDialogEvent.INTERNET_DIALOG_SHOW);
        this.mDialogView = LayoutInflater.from(this.mContext).inflate(C1777R.layout.internet_connectivity_dialog, (ViewGroup) null);
        Window window = getWindow();
        window.setContentView(this.mDialogView);
        window.setWindowAnimations(2132017162);
        this.mWifiNetworkHeight = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.internet_dialog_wifi_network_height);
        LinearLayout linearLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.internet_connectivity_dialog);
        this.mInternetDialogTitle = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.internet_dialog_title);
        this.mInternetDialogSubTitle = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.internet_dialog_subtitle);
        this.mDivider = this.mDialogView.requireViewById(C1777R.C1779id.divider);
        this.mProgressBar = (ProgressBar) this.mDialogView.requireViewById(C1777R.C1779id.wifi_searching_progress);
        this.mEthernetLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.ethernet_layout);
        this.mMobileNetworkLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.mobile_network_layout);
        this.mTurnWifiOnLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.turn_on_wifi_layout);
        this.mWifiToggleTitleText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_toggle_title);
        this.mWifiScanNotifyLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.wifi_scan_notify_layout);
        this.mWifiScanNotifyText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_scan_notify_text);
        this.mConnectedWifListLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.wifi_connected_layout);
        this.mConnectedWifiIcon = (ImageView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_connected_icon);
        this.mConnectedWifiTitleText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_connected_title);
        this.mConnectedWifiSummaryText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_connected_summary);
        this.mWifiSettingsIcon = (ImageView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_settings_icon);
        this.mWifiRecyclerView = (RecyclerView) this.mDialogView.requireViewById(C1777R.C1779id.wifi_list_layout);
        this.mSeeAllLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.see_all_layout);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(C1777R.C1779id.done_button);
        this.mAirplaneModeButton = (Button) this.mDialogView.requireViewById(C1777R.C1779id.apm_button);
        this.mSignalIcon = (ImageView) this.mDialogView.requireViewById(C1777R.C1779id.signal_icon);
        this.mMobileTitleText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.mobile_title);
        this.mMobileSummaryText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.mobile_summary);
        this.mAirplaneModeSummaryText = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.airplane_mode_summary);
        this.mMobileToggleDivider = this.mDialogView.requireViewById(C1777R.C1779id.mobile_toggle_divider);
        this.mMobileDataToggle = (Switch) this.mDialogView.requireViewById(C1777R.C1779id.mobile_toggle);
        this.mWiFiToggle = (Switch) this.mDialogView.requireViewById(C1777R.C1779id.wifi_toggle);
        this.mBackgroundOn = this.mContext.getDrawable(C1777R.C1778drawable.settingslib_switch_bar_bg_on);
        this.mInternetDialogTitle.setText(getDialogTitleText());
        this.mInternetDialogTitle.setGravity(8388627);
        int i = 0;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16843534});
        try {
            this.mBackgroundOff = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            this.mMobileNetworkLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda7(this));
            this.mMobileDataToggle.setOnCheckedChangeListener(new InternetDialog$$ExternalSyntheticLambda9(this));
            this.mConnectedWifListLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda6(this));
            this.mSeeAllLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda5(this, 0));
            this.mWiFiToggle.setOnCheckedChangeListener(new InternetDialog$$ExternalSyntheticLambda8(this));
            this.mDoneButton.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda1(this, 1));
            this.mAirplaneModeButton.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda4(this, 0));
            this.mTurnWifiOnLayout.setBackground((Drawable) null);
            Button button = this.mAirplaneModeButton;
            if (!this.mInternetDialogController.isAirplaneModeEnabled()) {
                i = 8;
            }
            button.setVisibility(i);
            this.mWifiRecyclerView.setLayoutManager(new LinearLayoutManager(1));
            this.mWifiRecyclerView.setAdapter(this.mAdapter);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void onStart() {
        super.onStart();
        if (DEBUG) {
            Log.d("InternetDialog", "onStart");
        }
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean z = this.mCanConfigWifi;
        boolean z2 = InternetDialogController.DEBUG;
        if (z2) {
            Objects.requireNonNull(internetDialogController);
            Log.d("InternetDialogController", "onStart");
        }
        internetDialogController.mCallback = this;
        internetDialogController.mKeyguardUpdateMonitor.registerCallback(internetDialogController.mKeyguardUpdateCallback);
        internetDialogController.mAccessPointController.addAccessPointCallback(internetDialogController);
        BroadcastDispatcher broadcastDispatcher = internetDialogController.mBroadcastDispatcher;
        InternetDialogController.C10532 r6 = internetDialogController.mConnectionStateReceiver;
        IntentFilter intentFilter = internetDialogController.mConnectionStateFilter;
        Executor executor = internetDialogController.mExecutor;
        Objects.requireNonNull(broadcastDispatcher);
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r6, intentFilter, executor, (UserHandle) null, 24);
        InternetDialogController.InternetOnSubscriptionChangedListener internetOnSubscriptionChangedListener = new InternetDialogController.InternetOnSubscriptionChangedListener();
        internetDialogController.mOnSubscriptionsChangedListener = internetOnSubscriptionChangedListener;
        internetDialogController.mSubscriptionManager.addOnSubscriptionsChangedListener(internetDialogController.mExecutor, internetOnSubscriptionChangedListener);
        internetDialogController.mDefaultDataSubId = internetDialogController.getDefaultDataSubscriptionId();
        if (z2) {
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("Init, SubId: "), internetDialogController.mDefaultDataSubId, "InternetDialogController");
        }
        internetDialogController.mConfig = MobileMappings.Config.readConfig(internetDialogController.mContext);
        internetDialogController.mTelephonyManager = internetDialogController.mTelephonyManager.createForSubscriptionId(internetDialogController.mDefaultDataSubId);
        InternetDialogController.InternetTelephonyCallback internetTelephonyCallback = new InternetDialogController.InternetTelephonyCallback();
        internetDialogController.mInternetTelephonyCallback = internetTelephonyCallback;
        internetDialogController.mTelephonyManager.registerTelephonyCallback(internetDialogController.mExecutor, internetTelephonyCallback);
        internetDialogController.mConnectivityManager.registerDefaultNetworkCallback(internetDialogController.mConnectivityManagerNetworkCallback);
        internetDialogController.mCanConfigWifi = z;
        if (z) {
            internetDialogController.mAccessPointController.scanForAccessPoints();
        }
        if (!this.mCanConfigWifi) {
            hideWifiViews();
        }
    }

    public final void onStop() {
        super.onStop();
        if (DEBUG) {
            Log.d("InternetDialog", "onStop");
        }
        this.mHandler.removeCallbacks(this.mHideProgressBarRunnable);
        this.mHandler.removeCallbacks(this.mHideSearchingRunnable);
        this.mMobileNetworkLayout.setOnClickListener((View.OnClickListener) null);
        this.mMobileDataToggle.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        this.mConnectedWifListLayout.setOnClickListener((View.OnClickListener) null);
        this.mSeeAllLayout.setOnClickListener((View.OnClickListener) null);
        this.mWiFiToggle.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        this.mDoneButton.setOnClickListener((View.OnClickListener) null);
        this.mAirplaneModeButton.setOnClickListener((View.OnClickListener) null);
        InternetDialogController internetDialogController = this.mInternetDialogController;
        if (InternetDialogController.DEBUG) {
            Objects.requireNonNull(internetDialogController);
            Log.d("InternetDialogController", "onStop");
        }
        internetDialogController.mBroadcastDispatcher.unregisterReceiver(internetDialogController.mConnectionStateReceiver);
        internetDialogController.mTelephonyManager.unregisterTelephonyCallback(internetDialogController.mInternetTelephonyCallback);
        internetDialogController.mSubscriptionManager.removeOnSubscriptionsChangedListener(internetDialogController.mOnSubscriptionsChangedListener);
        internetDialogController.mAccessPointController.removeAccessPointCallback(internetDialogController);
        internetDialogController.mKeyguardUpdateMonitor.removeCallback(internetDialogController.mKeyguardUpdateCallback);
        internetDialogController.mConnectivityManager.unregisterNetworkCallback(internetDialogController.mConnectivityManagerNetworkCallback);
        InternetDialogController.ConnectedWifiInternetMonitor connectedWifiInternetMonitor = internetDialogController.mConnectedWifiInternetMonitor;
        Objects.requireNonNull(connectedWifiInternetMonitor);
        WifiEntry wifiEntry = connectedWifiInternetMonitor.mWifiEntry;
        if (wifiEntry != null) {
            synchronized (wifiEntry) {
                wifiEntry.mListener = null;
            }
            connectedWifiInternetMonitor.mWifiEntry = null;
        }
        Objects.requireNonNull(this.mInternetDialogFactory);
        if (InternetDialogFactoryKt.DEBUG) {
            Log.d("InternetDialogFactory", "destroyDialog");
        }
        InternetDialogFactory.internetDialog = null;
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null && !alertDialog.isShowing() && !z && isShowing()) {
            dismiss();
        }
    }
}
