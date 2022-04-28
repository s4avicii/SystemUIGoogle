package com.android.systemui.sensorprivacy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.os.Handler;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: SensorUseStartedActivity.kt */
public final class SensorUseStartedActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler bgHandler;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final KeyguardStateController keyguardStateController;
    public SensorUseDialog mDialog;
    public int sensor = -1;
    public final IndividualSensorPrivacyController sensorPrivacyController;
    public IndividualSensorPrivacyController.Callback sensorPrivacyListener;
    public String sensorUsePackageName;
    public boolean unsuppressImmediately;

    public final void onBackPressed() {
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        String str = null;
        if (i == -2) {
            this.unsuppressImmediately = false;
            String str2 = this.sensorUsePackageName;
            if (str2 != null) {
                str = str2;
            }
            FrameworkStatsLog.write(382, 2, str);
        } else if (i == -1) {
            if (!this.keyguardStateController.isMethodSecure() || !this.keyguardStateController.isShowing()) {
                disableSensorPrivacy();
                String str3 = this.sensorUsePackageName;
                if (str3 != null) {
                    str = str3;
                }
                FrameworkStatsLog.write(382, 1, str);
            } else {
                this.keyguardDismissUtil.executeWhenUnlocked(new SensorUseStartedActivity$onClick$1(this), false, true);
            }
        }
        finish();
    }

    public final void disableSensorPrivacy() {
        int i = this.sensor;
        if (i == Integer.MAX_VALUE) {
            this.sensorPrivacyController.setSensorBlocked(3, 1, false);
            this.sensorPrivacyController.setSensorBlocked(3, 2, false);
        } else {
            this.sensorPrivacyController.setSensorBlocked(3, i, false);
        }
        this.unsuppressImmediately = true;
        setResult(-1);
    }

    public final void setSuppressed(boolean z) {
        int i = this.sensor;
        if (i == Integer.MAX_VALUE) {
            this.sensorPrivacyController.suppressSensorPrivacyReminders(1, z);
            this.sensorPrivacyController.suppressSensorPrivacyReminders(2, z);
            return;
        }
        this.sensorPrivacyController.suppressSensorPrivacyReminders(i, z);
    }

    public SensorUseStartedActivity(IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController2, KeyguardDismissUtil keyguardDismissUtil2, Handler handler) {
        this.sensorPrivacyController = individualSensorPrivacyController;
        this.keyguardStateController = keyguardStateController2;
        this.keyguardDismissUtil = keyguardDismissUtil2;
        this.bgHandler = handler;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShowWhenLocked(true);
        setFinishOnTouchOutside(false);
        setResult(0);
        String stringExtra = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
        if (stringExtra != null) {
            this.sensorUsePackageName = stringExtra;
            if (getIntent().getBooleanExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, false)) {
                this.sensor = Integer.MAX_VALUE;
                SensorUseStartedActivity$onCreate$1 sensorUseStartedActivity$onCreate$1 = new SensorUseStartedActivity$onCreate$1(this);
                this.sensorPrivacyListener = sensorUseStartedActivity$onCreate$1;
                this.sensorPrivacyController.addCallback(sensorUseStartedActivity$onCreate$1);
                if (!this.sensorPrivacyController.isSensorBlocked(1) && !this.sensorPrivacyController.isSensorBlocked(2)) {
                    finish();
                    return;
                }
            } else {
                int intExtra = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, -1);
                if (intExtra == -1) {
                    finish();
                    return;
                }
                this.sensor = intExtra;
                SensorUseStartedActivity$onCreate$3 sensorUseStartedActivity$onCreate$3 = new SensorUseStartedActivity$onCreate$3(this);
                this.sensorPrivacyListener = sensorUseStartedActivity$onCreate$3;
                this.sensorPrivacyController.addCallback(sensorUseStartedActivity$onCreate$3);
                if (!this.sensorPrivacyController.isSensorBlocked(this.sensor)) {
                    finish();
                    return;
                }
            }
            SensorUseDialog sensorUseDialog = new SensorUseDialog(this, this.sensor, this, this);
            this.mDialog = sensorUseDialog;
            sensorUseDialog.show();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        SensorUseDialog sensorUseDialog = this.mDialog;
        if (sensorUseDialog != null) {
            sensorUseDialog.dismiss();
        }
        IndividualSensorPrivacyController individualSensorPrivacyController = this.sensorPrivacyController;
        IndividualSensorPrivacyController.Callback callback = this.sensorPrivacyListener;
        if (callback == null) {
            callback = null;
        }
        individualSensorPrivacyController.removeCallback(callback);
    }

    public final void onNewIntent(Intent intent) {
        setIntent(intent);
        recreate();
    }

    public final void onStart() {
        super.onStart();
        setSuppressed(true);
        this.unsuppressImmediately = false;
    }

    public final void onStop() {
        super.onStop();
        if (this.unsuppressImmediately) {
            setSuppressed(false);
        } else {
            this.bgHandler.postDelayed(new SensorUseStartedActivity$onStop$1(this), 2000);
        }
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    static {
        Class<SensorUseStartedActivity> cls = SensorUseStartedActivity.class;
    }
}
