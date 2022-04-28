package com.android.systemui.p006qs;

import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserManager;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecurityController;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.android.systemui.qs.QSSecurityFooter */
public final class QSSecurityFooter implements View.OnClickListener, DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter mActivityStarter;
    public final Callback mCallback = new Callback();
    public final Context mContext;
    public SystemUIDialog mDialog;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final DevicePolicyManager mDpm;
    public int mFooterIconId;
    public final TextView mFooterText;
    public String mFooterTextContent = null;
    public C1001H mHandler;
    public boolean mIsMovable = true;
    public boolean mIsVisible;
    public final Handler mMainHandler;
    public final ImageView mPrimaryFooterIcon;
    public Drawable mPrimaryFooterIconDrawable;
    public final View mRootView;
    public final SecurityController mSecurityController;
    public final AtomicBoolean mShouldUseSettingsButton = new AtomicBoolean(false);
    public final C10002 mUpdateDisplayState = new Runnable() {
        public final void run() {
            int i;
            QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
            String str = qSSecurityFooter.mFooterTextContent;
            if (str != null) {
                qSSecurityFooter.mFooterText.setText(str);
            }
            QSSecurityFooter qSSecurityFooter2 = QSSecurityFooter.this;
            View view = qSSecurityFooter2.mRootView;
            if (!qSSecurityFooter2.mIsVisible) {
                i = 8;
            } else {
                i = 0;
            }
            view.setVisibility(i);
            QSSecurityFooter qSSecurityFooter3 = QSSecurityFooter.this;
            VisibilityChangedDispatcher$OnVisibilityChangedListener visibilityChangedDispatcher$OnVisibilityChangedListener = qSSecurityFooter3.mVisibilityChangedListener;
            if (visibilityChangedDispatcher$OnVisibilityChangedListener != null) {
                visibilityChangedDispatcher$OnVisibilityChangedListener.onVisibilityChanged(qSSecurityFooter3.mRootView.getVisibility());
            }
        }
    };
    public final C09991 mUpdatePrimaryIcon = new Runnable() {
        public final void run() {
            QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
            Drawable drawable = qSSecurityFooter.mPrimaryFooterIconDrawable;
            if (drawable != null) {
                qSSecurityFooter.mPrimaryFooterIcon.setImageDrawable(drawable);
            } else {
                qSSecurityFooter.mPrimaryFooterIcon.setImageResource(qSSecurityFooter.mFooterIconId);
            }
        }
    };
    public final UserTracker mUserTracker;
    public VisibilityChangedDispatcher$OnVisibilityChangedListener mVisibilityChangedListener;

    /* renamed from: com.android.systemui.qs.QSSecurityFooter$Callback */
    public class Callback implements SecurityController.SecurityControllerCallback {
        public Callback() {
        }

        public final void onStateChanged() {
            QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
            Objects.requireNonNull(qSSecurityFooter);
            qSSecurityFooter.mHandler.sendEmptyMessage(1);
        }
    }

    /* renamed from: com.android.systemui.qs.QSSecurityFooter$H */
    public class C1001H extends Handler {
        public final void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 1) {
                    QSSecurityFooter.m213$$Nest$mhandleRefreshState(QSSecurityFooter.this);
                } else if (i == 0) {
                    QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
                    Objects.requireNonNull(qSSecurityFooter);
                    qSSecurityFooter.mShouldUseSettingsButton.set(false);
                    qSSecurityFooter.mMainHandler.post(new KeyguardPatternView$$ExternalSyntheticLambda0(qSSecurityFooter, qSSecurityFooter.createDialogView(), 2));
                    DevicePolicyEventLogger.createEvent(57).write();
                }
            } catch (Throwable th) {
                Log.w("QSSecurityFooter", "Error in " + null, th);
            }
        }

        public C1001H(Looper looper) {
            super(looper);
        }
    }

    /* renamed from: com.android.systemui.qs.QSSecurityFooter$VpnSpan */
    public class VpnSpan extends ClickableSpan {
        public final int hashCode() {
            return 314159257;
        }

        public VpnSpan() {
        }

        public final void onClick(View view) {
            Intent intent = new Intent("android.settings.VPN_SETTINGS");
            QSSecurityFooter.this.mDialog.dismiss();
            QSSecurityFooter.this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }

        public final boolean equals(Object obj) {
            return obj instanceof VpnSpan;
        }
    }

    public final void onClick(View view) {
        if (this.mRootView.getVisibility() != 8) {
            this.mHandler.sendEmptyMessage(0);
        }
    }

    /* renamed from: -$$Nest$mhandleRefreshState  reason: not valid java name */
    public static void m213$$Nest$mhandleRefreshState(QSSecurityFooter qSSecurityFooter) {
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        String str2;
        QSSecurityFooter qSSecurityFooter2 = qSSecurityFooter;
        Objects.requireNonNull(qSSecurityFooter);
        boolean isDeviceManaged = qSSecurityFooter2.mSecurityController.isDeviceManaged();
        UserInfo userInfo = qSSecurityFooter2.mUserTracker.getUserInfo();
        if (!UserManager.isDeviceInDemoMode(qSSecurityFooter2.mContext) || userInfo == null || !userInfo.isDemo()) {
            z = false;
        } else {
            z = true;
        }
        boolean hasWorkProfile = qSSecurityFooter2.mSecurityController.hasWorkProfile();
        boolean hasCACertInCurrentUser = qSSecurityFooter2.mSecurityController.hasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = qSSecurityFooter2.mSecurityController.hasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = qSSecurityFooter2.mSecurityController.isNetworkLoggingEnabled();
        String primaryVpnName = qSSecurityFooter2.mSecurityController.getPrimaryVpnName();
        String workProfileVpnName = qSSecurityFooter2.mSecurityController.getWorkProfileVpnName();
        CharSequence deviceOwnerOrganizationName = qSSecurityFooter2.mSecurityController.getDeviceOwnerOrganizationName();
        CharSequence workProfileOrganizationName = qSSecurityFooter2.mSecurityController.getWorkProfileOrganizationName();
        boolean isProfileOwnerOfOrganizationOwnedDevice = qSSecurityFooter2.mSecurityController.isProfileOwnerOfOrganizationOwnedDevice();
        boolean isParentalControlsEnabled = qSSecurityFooter2.mSecurityController.isParentalControlsEnabled();
        boolean isWorkProfileOn = qSSecurityFooter2.mSecurityController.isWorkProfileOn();
        if (hasCACertInWorkProfile || workProfileVpnName != null || (hasWorkProfile && isNetworkLoggingEnabled)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((!isDeviceManaged || z) && !hasCACertInCurrentUser && primaryVpnName == null && !isProfileOwnerOfOrganizationOwnedDevice && !isParentalControlsEnabled && (!z2 || !isWorkProfileOn)) {
            z3 = false;
        } else {
            z3 = true;
        }
        qSSecurityFooter2.mIsVisible = z3;
        if (!z3 || !isProfileOwnerOfOrganizationOwnedDevice || (z2 && isWorkProfileOn)) {
            qSSecurityFooter2.mRootView.setClickable(true);
            qSSecurityFooter2.mRootView.findViewById(C1777R.C1779id.footer_icon).setVisibility(0);
        } else {
            qSSecurityFooter2.mRootView.setClickable(false);
            qSSecurityFooter2.mRootView.findViewById(C1777R.C1779id.footer_icon).setVisibility(8);
        }
        if (isParentalControlsEnabled) {
            str = qSSecurityFooter2.mContext.getString(C1777R.string.quick_settings_disclosure_parental_controls);
        } else if (!isDeviceManaged) {
            if (hasCACertInCurrentUser || (hasCACertInWorkProfile && isWorkProfileOn)) {
                if (!hasCACertInWorkProfile || !isWorkProfileOn) {
                    if (hasCACertInCurrentUser) {
                        str = qSSecurityFooter2.mContext.getString(C1777R.string.quick_settings_disclosure_monitoring);
                    }
                } else if (workProfileOrganizationName == null) {
                    str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_WORK_PROFILE_MONITORING", new QSSecurityFooter$$ExternalSyntheticLambda4(qSSecurityFooter2));
                } else {
                    str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_NAMED_WORK_PROFILE_MONITORING", new QSSecurityFooter$$ExternalSyntheticLambda15(qSSecurityFooter2, workProfileOrganizationName), new Object[]{workProfileOrganizationName});
                }
            } else if (primaryVpnName != null || (workProfileVpnName != null && isWorkProfileOn)) {
                if (primaryVpnName != null && workProfileVpnName != null) {
                    str = qSSecurityFooter2.mContext.getString(C1777R.string.quick_settings_disclosure_vpns);
                } else if (workProfileVpnName != null && isWorkProfileOn) {
                    str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_WORK_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda19(qSSecurityFooter2, workProfileVpnName), new Object[]{workProfileVpnName});
                } else if (primaryVpnName != null) {
                    if (hasWorkProfile) {
                        str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_PERSONAL_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda20(qSSecurityFooter2, primaryVpnName), new Object[]{primaryVpnName});
                    } else {
                        str = qSSecurityFooter2.mContext.getString(C1777R.string.quick_settings_disclosure_named_vpn, new Object[]{primaryVpnName});
                    }
                }
            } else if (hasWorkProfile && isNetworkLoggingEnabled && isWorkProfileOn) {
                str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_WORK_PROFILE_NETWORK", new QSSecurityFooter$$ExternalSyntheticLambda5(qSSecurityFooter2));
            } else if (isProfileOwnerOfOrganizationOwnedDevice) {
                str = qSSecurityFooter2.getMangedDeviceGeneralText(workProfileOrganizationName);
            }
            str = null;
        } else if (hasCACertInCurrentUser || hasCACertInWorkProfile || isNetworkLoggingEnabled) {
            if (deviceOwnerOrganizationName == null) {
                str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_MANAGEMENT_MONITORING", new QSSecurityFooter$$ExternalSyntheticLambda1(qSSecurityFooter2));
            } else {
                str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MONITORING", new QSSecurityFooter$$ExternalSyntheticLambda12(qSSecurityFooter2, deviceOwnerOrganizationName), new Object[]{deviceOwnerOrganizationName});
            }
        } else if (primaryVpnName == null && workProfileVpnName == null) {
            str = qSSecurityFooter2.getMangedDeviceGeneralText(deviceOwnerOrganizationName);
        } else if (primaryVpnName == null || workProfileVpnName == null) {
            if (primaryVpnName != null) {
                str2 = primaryVpnName;
            } else {
                str2 = workProfileVpnName;
            }
            if (deviceOwnerOrganizationName == null) {
                str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_MANAGEMENT_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda18(qSSecurityFooter2, str2), new Object[]{str2});
            } else {
                str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(qSSecurityFooter2, deviceOwnerOrganizationName, str2), new Object[]{deviceOwnerOrganizationName, str2});
            }
        } else if (deviceOwnerOrganizationName == null) {
            str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_MANAGEMENT_MULTIPLE_VPNS", new QSSecurityFooter$$ExternalSyntheticLambda2(qSSecurityFooter2));
        } else {
            str = qSSecurityFooter2.mDpm.getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MULTIPLE_VPNS", new QSSecurityFooter$$ExternalSyntheticLambda13(qSSecurityFooter2, deviceOwnerOrganizationName), new Object[]{deviceOwnerOrganizationName});
        }
        qSSecurityFooter2.mFooterTextContent = str;
        int i = C1777R.C1778drawable.ic_info_outline;
        if (!(primaryVpnName == null && workProfileVpnName == null)) {
            if (qSSecurityFooter2.mSecurityController.isVpnBranded()) {
                i = C1777R.C1778drawable.stat_sys_branded_vpn;
            } else {
                i = C1777R.C1778drawable.stat_sys_vpn_ic;
            }
        }
        if (qSSecurityFooter2.mFooterIconId != i) {
            qSSecurityFooter2.mFooterIconId = i;
        }
        if (!isParentalControlsEnabled) {
            qSSecurityFooter2.mPrimaryFooterIconDrawable = null;
        } else if (qSSecurityFooter2.mPrimaryFooterIconDrawable == null) {
            qSSecurityFooter2.mPrimaryFooterIconDrawable = qSSecurityFooter2.mSecurityController.getIcon(qSSecurityFooter2.mSecurityController.getDeviceAdminInfo());
        }
        qSSecurityFooter2.mMainHandler.post(qSSecurityFooter2.mUpdatePrimaryIcon);
        qSSecurityFooter2.mMainHandler.post(qSSecurityFooter2.mUpdateDisplayState);
    }

    static {
        Log.isLoggable("QSSecurityFooter", 3);
    }

    public View createDialogView() {
        String str;
        String str2;
        SpannableStringBuilder spannableStringBuilder;
        boolean z;
        int i;
        boolean z2;
        int i2;
        boolean z3 = false;
        String str3 = null;
        if (this.mSecurityController.isParentalControlsEnabled()) {
            View inflate = LayoutInflater.from(this.mContext).inflate(C1777R.layout.quick_settings_footer_dialog_parental_controls, (ViewGroup) null, false);
            DeviceAdminInfo deviceAdminInfo = this.mSecurityController.getDeviceAdminInfo();
            Drawable icon = this.mSecurityController.getIcon(deviceAdminInfo);
            if (icon != null) {
                ((ImageView) inflate.findViewById(C1777R.C1779id.parental_controls_icon)).setImageDrawable(icon);
            }
            ((TextView) inflate.findViewById(C1777R.C1779id.parental_controls_title)).setText(this.mSecurityController.getLabel(deviceAdminInfo));
            return inflate;
        }
        boolean isDeviceManaged = this.mSecurityController.isDeviceManaged();
        boolean hasWorkProfile = this.mSecurityController.hasWorkProfile();
        CharSequence deviceOwnerOrganizationName = this.mSecurityController.getDeviceOwnerOrganizationName();
        boolean hasCACertInCurrentUser = this.mSecurityController.hasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = this.mSecurityController.hasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = this.mSecurityController.isNetworkLoggingEnabled();
        String primaryVpnName = this.mSecurityController.getPrimaryVpnName();
        String workProfileVpnName = this.mSecurityController.getWorkProfileVpnName();
        View inflate2 = LayoutInflater.from(this.mContext).inflate(C1777R.layout.quick_settings_footer_dialog, (ViewGroup) null, false);
        ((TextView) inflate2.findViewById(C1777R.C1779id.device_management_subtitle)).setText(getManagementTitle(deviceOwnerOrganizationName));
        if (isDeviceManaged) {
            if (deviceOwnerOrganizationName == null) {
                str3 = this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT", new QSSecurityFooter$$ExternalSyntheticLambda7(this));
            } else if (isFinancedDevice()) {
                str3 = this.mContext.getString(C1777R.string.monitoring_financed_description_named_management, new Object[]{deviceOwnerOrganizationName, deviceOwnerOrganizationName});
            } else {
                str3 = this.mDpm.getString("SystemUi.QS_DIALOG_NAMED_MANAGEMENT", new QSSecurityFooter$$ExternalSyntheticLambda16(this, deviceOwnerOrganizationName), new Object[]{deviceOwnerOrganizationName});
            }
        }
        if (str3 == null) {
            inflate2.findViewById(C1777R.C1779id.device_management_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(C1777R.C1779id.device_management_disclosures).setVisibility(0);
            ((TextView) inflate2.findViewById(C1777R.C1779id.device_management_warning)).setText(str3);
            this.mShouldUseSettingsButton.set(true);
        }
        if (!hasCACertInCurrentUser && !hasCACertInWorkProfile) {
            str = null;
        } else if (isDeviceManaged) {
            str = this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT_CA_CERT", new QSSecurityFooter$$ExternalSyntheticLambda0(this, 0));
        } else if (hasCACertInWorkProfile) {
            str = this.mDpm.getString("SystemUi.QS_DIALOG_WORK_PROFILE_CA_CERT", new QSSecurityFooter$$ExternalSyntheticLambda8(this));
        } else {
            str = this.mContext.getString(C1777R.string.monitoring_description_ca_certificate);
        }
        if (str == null) {
            inflate2.findViewById(C1777R.C1779id.ca_certs_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(C1777R.C1779id.ca_certs_disclosures).setVisibility(0);
            TextView textView = (TextView) inflate2.findViewById(C1777R.C1779id.ca_certs_warning);
            textView.setText(str);
            textView.setMovementMethod(new LinkMovementMethod());
        }
        if (!isNetworkLoggingEnabled) {
            str2 = null;
        } else if (isDeviceManaged) {
            str2 = this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT_NETWORK", new QSSecurityFooter$$ExternalSyntheticLambda9(this));
        } else {
            str2 = this.mDpm.getString("SystemUi.QS_DIALOG_WORK_PROFILE_NETWORK", new QSSecurityFooter$$ExternalSyntheticLambda10(this));
        }
        if (str2 == null) {
            inflate2.findViewById(C1777R.C1779id.network_logging_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(C1777R.C1779id.network_logging_disclosures).setVisibility(0);
            ((TextView) inflate2.findViewById(C1777R.C1779id.network_logging_warning)).setText(str2);
        }
        if (primaryVpnName == null && workProfileVpnName == null) {
            spannableStringBuilder = null;
        } else {
            spannableStringBuilder = new SpannableStringBuilder();
            if (isDeviceManaged) {
                if (primaryVpnName == null || workProfileVpnName == null) {
                    if (primaryVpnName == null) {
                        primaryVpnName = workProfileVpnName;
                    }
                    spannableStringBuilder.append(this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda21(this, primaryVpnName), new Object[]{primaryVpnName}));
                } else {
                    spannableStringBuilder.append(this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda24(this, primaryVpnName, workProfileVpnName), new Object[]{primaryVpnName, workProfileVpnName}));
                }
            } else if (primaryVpnName != null && workProfileVpnName != null) {
                spannableStringBuilder.append(this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda25(this, primaryVpnName, workProfileVpnName), new Object[]{primaryVpnName, workProfileVpnName}));
            } else if (workProfileVpnName != null) {
                spannableStringBuilder.append(this.mDpm.getString("SystemUi.QS_DIALOG_WORK_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda22(this, workProfileVpnName), new Object[]{workProfileVpnName}));
            } else if (hasWorkProfile) {
                spannableStringBuilder.append(this.mDpm.getString("SystemUi.QS_DIALOG_PERSONAL_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda23(this, primaryVpnName), new Object[]{primaryVpnName}));
            } else {
                spannableStringBuilder.append(this.mContext.getString(C1777R.string.monitoring_description_named_vpn, new Object[]{primaryVpnName}));
            }
            spannableStringBuilder.append(this.mContext.getString(C1777R.string.monitoring_description_vpn_settings_separator));
            spannableStringBuilder.append(this.mContext.getString(C1777R.string.monitoring_description_vpn_settings), new VpnSpan(), 0);
        }
        if (spannableStringBuilder == null) {
            inflate2.findViewById(C1777R.C1779id.vpn_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(C1777R.C1779id.vpn_disclosures).setVisibility(0);
            TextView textView2 = (TextView) inflate2.findViewById(C1777R.C1779id.vpn_warning);
            textView2.setText(spannableStringBuilder);
            textView2.setMovementMethod(new LinkMovementMethod());
        }
        if (str3 != null) {
            z = true;
        } else {
            z = false;
        }
        if (str != null) {
            i = 1;
        } else {
            i = 0;
        }
        if (str2 != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (spannableStringBuilder != null) {
            z3 = true;
        }
        if (!z) {
            if (z2) {
                i2 = i + 1;
            } else {
                i2 = i;
            }
            if (z3) {
                i2++;
            }
            if (i2 == 1) {
                if (i != 0) {
                    inflate2.findViewById(C1777R.C1779id.ca_certs_subtitle).setVisibility(8);
                }
                if (z2) {
                    inflate2.findViewById(C1777R.C1779id.network_logging_subtitle).setVisibility(8);
                }
                if (z3) {
                    inflate2.findViewById(C1777R.C1779id.vpn_subtitle).setVisibility(8);
                }
            }
        }
        return inflate2;
    }

    public CharSequence getManagementTitle(CharSequence charSequence) {
        if (charSequence == null || !isFinancedDevice()) {
            return this.mDpm.getString("SystemUi.QS_DIALOG_MANAGEMENT_TITLE", new QSSecurityFooter$$ExternalSyntheticLambda11(this));
        }
        return this.mContext.getString(C1777R.string.monitoring_title_financed_device, new Object[]{charSequence});
    }

    public final String getMangedDeviceGeneralText(CharSequence charSequence) {
        if (charSequence == null) {
            return this.mDpm.getString("SystemUi.QS_MSG_MANAGEMENT", new QSSecurityFooter$$ExternalSyntheticLambda3(this));
        }
        if (isFinancedDevice()) {
            return this.mContext.getString(C1777R.string.quick_settings_financed_disclosure_named_management, new Object[]{charSequence});
        }
        return this.mDpm.getString("SystemUi.QS_MSG_NAMED_MANAGEMENT", new QSSecurityFooter$$ExternalSyntheticLambda14(this, charSequence), new Object[]{charSequence});
    }

    public String getSettingsButton() {
        return this.mDpm.getString("SystemUi.QS_DIALOG_VIEW_POLICIES", new QSSecurityFooter$$ExternalSyntheticLambda6(this));
    }

    public final boolean isFinancedDevice() {
        if (this.mSecurityController.isDeviceManaged()) {
            SecurityController securityController = this.mSecurityController;
            if (securityController.getDeviceOwnerType(securityController.getDeviceOwnerComponentOnAnyUser()) == 1) {
                return true;
            }
        }
        return false;
    }

    public QSSecurityFooter(View view, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, DialogLaunchAnimator dialogLaunchAnimator, Looper looper) {
        this.mRootView = view;
        view.setOnClickListener(this);
        this.mFooterText = (TextView) view.findViewById(C1777R.C1779id.footer_text);
        this.mPrimaryFooterIcon = (ImageView) view.findViewById(C1777R.C1779id.primary_footer_icon);
        this.mFooterIconId = C1777R.C1778drawable.ic_info_outline;
        this.mContext = view.getContext();
        this.mDpm = (DevicePolicyManager) view.getContext().getSystemService(DevicePolicyManager.class);
        this.mMainHandler = handler;
        this.mActivityStarter = activityStarter;
        this.mSecurityController = securityController;
        this.mHandler = new C1001H(looper);
        this.mUserTracker = userTracker;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            Intent intent = new Intent("android.settings.ENTERPRISE_PRIVACY_SETTINGS");
            dialogInterface.dismiss();
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }
    }
}
