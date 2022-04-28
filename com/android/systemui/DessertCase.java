package com.android.systemui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Slog;
import com.android.systemui.DessertCaseView;
import java.util.Objects;

public class DessertCase extends Activity {
    public DessertCaseView mView;

    public final void onPause() {
        super.onPause();
        DessertCaseView dessertCaseView = this.mView;
        Objects.requireNonNull(dessertCaseView);
        dessertCaseView.mStarted = false;
        dessertCaseView.mHandler.removeCallbacks(dessertCaseView.mJuggle);
    }

    public final void onResume() {
        super.onResume();
        this.mView.postDelayed(new Runnable() {
            public final void run() {
                DessertCase.this.mView.start();
            }
        }, 1000);
    }

    public final void onStart() {
        super.onStart();
        PackageManager packageManager = getPackageManager();
        ComponentName componentName = new ComponentName(this, DessertCaseDream.class);
        if (packageManager.getComponentEnabledSetting(componentName) != 1) {
            Slog.v("DessertCase", "ACHIEVEMENT UNLOCKED");
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
        }
        this.mView = new DessertCaseView(this, (AttributeSet) null);
        DessertCaseView.RescalingContainer rescalingContainer = new DessertCaseView.RescalingContainer(this);
        DessertCaseView dessertCaseView = this.mView;
        rescalingContainer.addView(dessertCaseView);
        rescalingContainer.mView = dessertCaseView;
        setContentView(rescalingContainer);
    }
}
