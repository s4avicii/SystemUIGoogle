package com.android.systemui.sensorprivacy.television;

import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda2;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda5;
import com.android.systemui.p008tv.TvBottomSheetActivity;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;

public class TvUnblockSensorActivity extends TvBottomSheetActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mSensor = -1;
    public TvUnblockSensorActivity$$ExternalSyntheticLambda0 mSensorPrivacyCallback;
    public final IndividualSensorPrivacyController mSensorPrivacyController;

    public final void onPause() {
        this.mSensorPrivacyController.removeCallback(this.mSensorPrivacyCallback);
        super.onPause();
    }

    public TvUnblockSensorActivity(IndividualSensorPrivacyController individualSensorPrivacyController) {
        this.mSensorPrivacyController = individualSensorPrivacyController;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        if (getIntent().getBooleanExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, false)) {
            this.mSensor = Integer.MAX_VALUE;
        } else {
            this.mSensor = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, -1);
        }
        if (this.mSensor == -1) {
            Log.v("TvUnblockSensorActivity", "Invalid extras");
            finish();
            return;
        }
        this.mSensorPrivacyCallback = new TvUnblockSensorActivity$$ExternalSyntheticLambda0(this);
        TextView textView = (TextView) findViewById(C1777R.C1779id.bottom_sheet_title);
        TextView textView2 = (TextView) findViewById(C1777R.C1779id.bottom_sheet_body);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.bottom_sheet_icon);
        ImageView imageView2 = (ImageView) findViewById(C1777R.C1779id.bottom_sheet_second_icon);
        Button button = (Button) findViewById(C1777R.C1779id.bottom_sheet_positive_button);
        Button button2 = (Button) findViewById(C1777R.C1779id.bottom_sheet_negative_button);
        int i = this.mSensor;
        if (i == 1) {
            textView.setText(C1777R.string.sensor_privacy_start_use_mic_dialog_title);
            textView2.setText(C1777R.string.sensor_privacy_start_use_mic_dialog_content);
            imageView.setImageResource(17303155);
            imageView2.setVisibility(8);
        } else if (i != 2) {
            textView.setText(C1777R.string.sensor_privacy_start_use_mic_camera_dialog_title);
            textView2.setText(C1777R.string.sensor_privacy_start_use_mic_camera_dialog_content);
            imageView.setImageResource(17303150);
            imageView2.setImageResource(17303155);
        } else {
            textView.setText(C1777R.string.sensor_privacy_start_use_camera_dialog_title);
            textView2.setText(C1777R.string.sensor_privacy_start_use_camera_dialog_content);
            imageView.setImageResource(17303150);
            imageView2.setVisibility(8);
        }
        button.setText(17041455);
        button.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda5(this, 1));
        button2.setText(17039360);
        button2.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda2(this, 1));
    }

    public final void onResume() {
        super.onResume();
        this.mSensorPrivacyController.addCallback(this.mSensorPrivacyCallback);
    }
}
