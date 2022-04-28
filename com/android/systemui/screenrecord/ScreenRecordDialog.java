package com.android.systemui.screenrecord;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0;
import com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0;
import java.util.Arrays;
import java.util.List;

public final class ScreenRecordDialog extends SystemUIDialog {
    public static final List<ScreenRecordingAudioSource> MODES = Arrays.asList(new ScreenRecordingAudioSource[]{ScreenRecordingAudioSource.INTERNAL, ScreenRecordingAudioSource.MIC, ScreenRecordingAudioSource.MIC_AND_INTERNAL});
    public Switch mAudioSwitch;
    public final RecordingController mController;
    public final Runnable mOnStartRecordingClicked;
    public Spinner mOptions;
    public Switch mTapsSwitch;
    public final UserContextProvider mUserContextProvider;

    public ScreenRecordDialog(Context context, RecordingController recordingController, UserContextProvider userContextProvider, StandardWifiEntry$$ExternalSyntheticLambda0 standardWifiEntry$$ExternalSyntheticLambda0) {
        super(context);
        this.mController = recordingController;
        this.mUserContextProvider = userContextProvider;
        this.mOnStartRecordingClicked = standardWifiEntry$$ExternalSyntheticLambda0;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addPrivateFlags(16);
        window.setGravity(17);
        setTitle(C1777R.string.screenrecord_name);
        setContentView(C1777R.layout.screen_record_dialog);
        ((TextView) findViewById(C1777R.C1779id.button_cancel)).setOnClickListener(new AvatarPickerActivity$$ExternalSyntheticLambda0(this, 1));
        ((TextView) findViewById(C1777R.C1779id.button_start)).setOnClickListener(new AvatarPickerActivity$$ExternalSyntheticLambda1(this, 1));
        this.mAudioSwitch = (Switch) findViewById(C1777R.C1779id.screenrecord_audio_switch);
        this.mTapsSwitch = (Switch) findViewById(C1777R.C1779id.screenrecord_taps_switch);
        this.mOptions = (Spinner) findViewById(C1777R.C1779id.screen_recording_options);
        ScreenRecordingAdapter screenRecordingAdapter = new ScreenRecordingAdapter(getContext().getApplicationContext(), MODES);
        screenRecordingAdapter.setDropDownViewResource(17367049);
        this.mOptions.setAdapter(screenRecordingAdapter);
        this.mOptions.setOnItemClickListenerInt(new ScreenRecordDialog$$ExternalSyntheticLambda0(this));
    }
}
