package com.android.systemui.screenrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.List;

public final class ScreenRecordingAdapter extends ArrayAdapter<ScreenRecordingAudioSource> {
    public LinearLayout mInternalOption;
    public LinearLayout mMicAndInternalOption;
    public LinearLayout mMicOption;
    public LinearLayout mSelectedInternal = getSelected(C1777R.string.screenrecord_device_audio_label);
    public LinearLayout mSelectedMic = getSelected(C1777R.string.screenrecord_mic_label);
    public LinearLayout mSelectedMicAndInternal = getSelected(C1777R.string.screenrecord_device_audio_and_mic_label);

    public ScreenRecordingAdapter(Context context, List list) {
        super(context, 17367049, list);
        LinearLayout option = getOption(C1777R.string.screenrecord_mic_label, 0);
        this.mMicOption = option;
        option.removeViewAt(1);
        LinearLayout option2 = getOption(C1777R.string.screenrecord_device_audio_and_mic_label, 0);
        this.mMicAndInternalOption = option2;
        option2.removeViewAt(1);
        this.mInternalOption = getOption(C1777R.string.screenrecord_device_audio_label, C1777R.string.screenrecord_device_audio_description);
    }

    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        int ordinal = ((ScreenRecordingAudioSource) getItem(i)).ordinal();
        if (ordinal == 1) {
            return this.mInternalOption;
        }
        if (ordinal == 2) {
            return this.mMicOption;
        }
        if (ordinal != 3) {
            return super.getDropDownView(i, view, viewGroup);
        }
        return this.mMicAndInternalOption;
    }

    public final LinearLayout getOption(int i, int i2) {
        LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1777R.layout.screen_record_dialog_audio_source, (ViewGroup) null, false);
        ((TextView) linearLayout.findViewById(C1777R.C1779id.screen_recording_dialog_source_text)).setText(i);
        if (i2 != 0) {
            ((TextView) linearLayout.findViewById(C1777R.C1779id.screen_recording_dialog_source_description)).setText(i2);
        }
        return linearLayout;
    }

    public final LinearLayout getSelected(int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(C1777R.layout.screen_record_dialog_audio_source_selected, (ViewGroup) null, false);
        ((TextView) linearLayout.findViewById(C1777R.C1779id.screen_recording_dialog_source_text)).setText(i);
        return linearLayout;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        int ordinal = ((ScreenRecordingAudioSource) getItem(i)).ordinal();
        if (ordinal == 1) {
            return this.mSelectedInternal;
        }
        if (ordinal == 2) {
            return this.mSelectedMic;
        }
        if (ordinal != 3) {
            return super.getView(i, view, viewGroup);
        }
        return this.mSelectedMicAndInternal;
    }
}
