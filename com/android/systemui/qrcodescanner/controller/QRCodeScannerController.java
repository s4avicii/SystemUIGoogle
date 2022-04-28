package com.android.systemui.qrcodescanner.controller;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.provider.DeviceConfig;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda3;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda4;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.LocationControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.settings.SecureSettings;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public final class QRCodeScannerController implements CallbackController<Callback> {
    public final ArrayList<Callback> mCallbacks = new ArrayList<>();
    public final boolean mConfigEnableLockScreenButton;
    public final Context mContext;
    public AtomicInteger mDefaultQRCodeScannerChangeEvents = new AtomicInteger(0);
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final Executor mExecutor;
    public Intent mIntent = null;
    public Boolean mIsCameraAvailable = null;
    public LocationControllerImpl$$ExternalSyntheticLambda0 mOnDefaultQRCodeScannerChangedListener = null;
    public String mQRCodeScannerActivity = null;
    public boolean mQRCodeScannerEnabled;
    public AtomicInteger mQRCodeScannerPreferenceChangeEvents = new AtomicInteger(0);
    public HashMap<Integer, ContentObserver> mQRCodeScannerPreferenceObserver = new HashMap<>();
    public final SecureSettings mSecureSettings;
    public C09772 mUserChangedListener = null;
    public final UserTracker mUserTracker;

    public interface Callback {
        void onQRCodeScannerActivityChanged() {
        }
    }

    public final void addCallback(Object obj) {
        Callback callback = (Callback) obj;
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.add(callback);
            }
        }
    }

    public final boolean isCameraAvailable() {
        if (this.mIsCameraAvailable == null) {
            this.mIsCameraAvailable = Boolean.valueOf(this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera"));
        }
        return this.mIsCameraAvailable.booleanValue();
    }

    public final void registerQRCodePreferenceObserver() {
        if (this.mConfigEnableLockScreenButton) {
            int userId = this.mUserTracker.getUserId();
            if (this.mQRCodeScannerPreferenceObserver.getOrDefault(Integer.valueOf(userId), (Object) null) == null) {
                this.mExecutor.execute(new AccessPoint$$ExternalSyntheticLambda0(this, 4));
                this.mQRCodeScannerPreferenceObserver.put(Integer.valueOf(userId), new ContentObserver() {
                    public static final /* synthetic */ int $r8$clinit = 0;

                    public final void onChange(boolean z) {
                        QRCodeScannerController.this.mExecutor.execute(new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 1));
                    }
                });
                SecureSettings secureSettings = this.mSecureSettings;
                secureSettings.registerContentObserverForUser(secureSettings.getUriFor("lock_screen_show_qr_code_scanner"), false, this.mQRCodeScannerPreferenceObserver.get(Integer.valueOf(userId)), userId);
            }
        }
    }

    public final void removeCallback(Object obj) {
        Callback callback = (Callback) obj;
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.remove(callback);
            }
        }
    }

    public final void updateQRCodeScannerActivityDetails() {
        boolean z;
        ArrayList arrayList;
        Objects.requireNonNull(this.mDeviceConfigProxy);
        String string = DeviceConfig.getString("systemui", "default_qr_code_scanner", "");
        if (Objects.equals(string, "")) {
            string = this.mContext.getResources().getString(C1777R.string.def_qr_code_component);
        }
        String str = this.mQRCodeScannerActivity;
        Intent intent = new Intent();
        if (string != null) {
            intent.setComponent(ComponentName.unflattenFromString(string));
            intent.addFlags(335544320);
        }
        if (intent.getComponent() == null) {
            z = false;
        } else {
            z = !this.mContext.getPackageManager().queryIntentActivities(intent, 537698816).isEmpty();
        }
        if (z) {
            this.mQRCodeScannerActivity = string;
            this.mIntent = intent;
        } else {
            this.mQRCodeScannerActivity = null;
            this.mIntent = null;
        }
        if (!Objects.equals(this.mQRCodeScannerActivity, str)) {
            synchronized (this.mCallbacks) {
                arrayList = (ArrayList) this.mCallbacks.clone();
            }
            arrayList.forEach(QSTileHost$$ExternalSyntheticLambda4.INSTANCE$2);
        }
    }

    public final void updateQRCodeScannerPreferenceDetails(boolean z) {
        ArrayList arrayList;
        if (this.mConfigEnableLockScreenButton) {
            boolean z2 = this.mQRCodeScannerEnabled;
            boolean z3 = false;
            if (this.mSecureSettings.getIntForUser("lock_screen_show_qr_code_scanner", 0, this.mUserTracker.getUserId()) != 0) {
                z3 = true;
            }
            this.mQRCodeScannerEnabled = z3;
            if (z) {
                this.mSecureSettings.putStringForUser("show_qr_code_scanner_setting", this.mQRCodeScannerActivity, this.mUserTracker.getUserId());
            }
            if (!Objects.equals(Boolean.valueOf(this.mQRCodeScannerEnabled), Boolean.valueOf(z2))) {
                synchronized (this.mCallbacks) {
                    arrayList = (ArrayList) this.mCallbacks.clone();
                }
                arrayList.forEach(QSTileHost$$ExternalSyntheticLambda3.INSTANCE$2);
            }
        }
    }

    public QRCodeScannerController(Context context, Executor executor, SecureSettings secureSettings, DeviceConfigProxy deviceConfigProxy, UserTracker userTracker) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mUserTracker = userTracker;
        this.mConfigEnableLockScreenButton = context.getResources().getBoolean(R.bool.config_enableQrCodeScannerOnLockScreen);
    }

    public final void registerQRCodeScannerChangeObservers(int... iArr) {
        if (isCameraAvailable()) {
            for (int i : iArr) {
                if (i == 0) {
                    this.mDefaultQRCodeScannerChangeEvents.incrementAndGet();
                    if (this.mOnDefaultQRCodeScannerChangedListener == null) {
                        this.mExecutor.execute(new WifiEntry$$ExternalSyntheticLambda2(this, 3));
                        LocationControllerImpl$$ExternalSyntheticLambda0 locationControllerImpl$$ExternalSyntheticLambda0 = new LocationControllerImpl$$ExternalSyntheticLambda0(this, 1);
                        this.mOnDefaultQRCodeScannerChangedListener = locationControllerImpl$$ExternalSyntheticLambda0;
                        DeviceConfigProxy deviceConfigProxy = this.mDeviceConfigProxy;
                        Executor executor = this.mExecutor;
                        Objects.requireNonNull(deviceConfigProxy);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", executor, locationControllerImpl$$ExternalSyntheticLambda0);
                    }
                } else if (i != 1) {
                    KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Unrecognised event: ", i, "QRCodeScannerController");
                } else {
                    this.mQRCodeScannerPreferenceChangeEvents.incrementAndGet();
                    registerQRCodePreferenceObserver();
                    if (this.mUserChangedListener == null) {
                        C09772 r2 = new UserTracker.Callback() {
                            public final void onUserChanged(int i) {
                                QRCodeScannerController.this.registerQRCodePreferenceObserver();
                                QRCodeScannerController.this.updateQRCodeScannerPreferenceDetails(true);
                            }
                        };
                        this.mUserChangedListener = r2;
                        this.mUserTracker.addCallback(r2, this.mExecutor);
                    }
                }
            }
        }
    }

    public final void unregisterQRCodeScannerChangeObservers(int... iArr) {
        if (isCameraAvailable()) {
            for (int i : iArr) {
                if (i != 0) {
                    if (i != 1) {
                        KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Unrecognised event: ", i, "QRCodeScannerController");
                    } else if (this.mUserTracker != null && this.mQRCodeScannerPreferenceChangeEvents.decrementAndGet() == 0) {
                        if (this.mConfigEnableLockScreenButton) {
                            this.mQRCodeScannerPreferenceObserver.forEach(new QRCodeScannerController$$ExternalSyntheticLambda0(this));
                            this.mQRCodeScannerPreferenceObserver = new HashMap<>();
                            this.mSecureSettings.putStringForUser("show_qr_code_scanner_setting", (String) null, this.mUserTracker.getUserId());
                        }
                        this.mUserTracker.removeCallback(this.mUserChangedListener);
                        this.mUserChangedListener = null;
                        this.mQRCodeScannerEnabled = false;
                    }
                } else if (this.mOnDefaultQRCodeScannerChangedListener != null && this.mDefaultQRCodeScannerChangeEvents.decrementAndGet() == 0) {
                    DeviceConfigProxy deviceConfigProxy = this.mDeviceConfigProxy;
                    LocationControllerImpl$$ExternalSyntheticLambda0 locationControllerImpl$$ExternalSyntheticLambda0 = this.mOnDefaultQRCodeScannerChangedListener;
                    Objects.requireNonNull(deviceConfigProxy);
                    DeviceConfig.removeOnPropertiesChangedListener(locationControllerImpl$$ExternalSyntheticLambda0);
                    this.mOnDefaultQRCodeScannerChangedListener = null;
                    this.mQRCodeScannerActivity = null;
                    this.mIntent = null;
                }
            }
        }
    }
}
