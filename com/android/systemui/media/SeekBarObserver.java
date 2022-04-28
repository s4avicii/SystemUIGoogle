package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.media.SeekBarViewModel;
import java.util.Objects;

/* compiled from: SeekBarObserver.kt */
public final class SeekBarObserver implements Observer<SeekBarViewModel.Progress> {
    public final MediaViewHolder holder;
    public final int seekBarDisabledHeight;
    public final int seekBarDisabledVerticalPadding;
    public final int seekBarEnabledMaxHeight;
    public final int seekBarEnabledVerticalPadding;

    public final void onChanged(Object obj) {
        int i;
        SeekBarViewModel.Progress progress = (SeekBarViewModel.Progress) obj;
        if (!progress.enabled) {
            MediaViewHolder mediaViewHolder = this.holder;
            Objects.requireNonNull(mediaViewHolder);
            if (mediaViewHolder.seekBar.getMaxHeight() != this.seekBarDisabledHeight) {
                MediaViewHolder mediaViewHolder2 = this.holder;
                Objects.requireNonNull(mediaViewHolder2);
                mediaViewHolder2.seekBar.setMaxHeight(this.seekBarDisabledHeight);
                setVerticalPadding(this.seekBarDisabledVerticalPadding);
            }
            MediaViewHolder mediaViewHolder3 = this.holder;
            Objects.requireNonNull(mediaViewHolder3);
            mediaViewHolder3.seekBar.setEnabled(false);
            MediaViewHolder mediaViewHolder4 = this.holder;
            Objects.requireNonNull(mediaViewHolder4);
            mediaViewHolder4.seekBar.getThumb().setAlpha(0);
            MediaViewHolder mediaViewHolder5 = this.holder;
            Objects.requireNonNull(mediaViewHolder5);
            mediaViewHolder5.seekBar.setProgress(0);
            TextView elapsedTimeView = this.holder.getElapsedTimeView();
            if (elapsedTimeView != null) {
                elapsedTimeView.setText("");
            }
            TextView totalTimeView = this.holder.getTotalTimeView();
            if (totalTimeView != null) {
                totalTimeView.setText("");
            }
            MediaViewHolder mediaViewHolder6 = this.holder;
            Objects.requireNonNull(mediaViewHolder6);
            mediaViewHolder6.seekBar.setContentDescription("");
            return;
        }
        MediaViewHolder mediaViewHolder7 = this.holder;
        Objects.requireNonNull(mediaViewHolder7);
        Drawable thumb = mediaViewHolder7.seekBar.getThumb();
        if (progress.seekAvailable) {
            i = 255;
        } else {
            i = 0;
        }
        thumb.setAlpha(i);
        MediaViewHolder mediaViewHolder8 = this.holder;
        Objects.requireNonNull(mediaViewHolder8);
        mediaViewHolder8.seekBar.setEnabled(progress.seekAvailable);
        MediaViewHolder mediaViewHolder9 = this.holder;
        Objects.requireNonNull(mediaViewHolder9);
        if (mediaViewHolder9.seekBar.getMaxHeight() != this.seekBarEnabledMaxHeight) {
            MediaViewHolder mediaViewHolder10 = this.holder;
            Objects.requireNonNull(mediaViewHolder10);
            mediaViewHolder10.seekBar.setMaxHeight(this.seekBarEnabledMaxHeight);
            setVerticalPadding(this.seekBarEnabledVerticalPadding);
        }
        MediaViewHolder mediaViewHolder11 = this.holder;
        Objects.requireNonNull(mediaViewHolder11);
        mediaViewHolder11.seekBar.setMax(progress.duration);
        String formatElapsedTime = DateUtils.formatElapsedTime(((long) progress.duration) / 1000);
        TextView totalTimeView2 = this.holder.getTotalTimeView();
        if (totalTimeView2 != null) {
            totalTimeView2.setText(formatElapsedTime);
        }
        Integer num = progress.elapsedTime;
        if (num != null) {
            int intValue = num.intValue();
            MediaViewHolder mediaViewHolder12 = this.holder;
            Objects.requireNonNull(mediaViewHolder12);
            mediaViewHolder12.seekBar.setProgress(intValue);
            String formatElapsedTime2 = DateUtils.formatElapsedTime(((long) intValue) / 1000);
            TextView elapsedTimeView2 = this.holder.getElapsedTimeView();
            if (elapsedTimeView2 != null) {
                elapsedTimeView2.setText(formatElapsedTime2);
            }
            MediaViewHolder mediaViewHolder13 = this.holder;
            Objects.requireNonNull(mediaViewHolder13);
            SeekBar seekBar = mediaViewHolder13.seekBar;
            MediaViewHolder mediaViewHolder14 = this.holder;
            Objects.requireNonNull(mediaViewHolder14);
            seekBar.setContentDescription(mediaViewHolder14.seekBar.getContext().getString(C1777R.string.controls_media_seekbar_description, new Object[]{formatElapsedTime2, formatElapsedTime}));
        }
    }

    public final void setVerticalPadding(int i) {
        MediaViewHolder mediaViewHolder = this.holder;
        Objects.requireNonNull(mediaViewHolder);
        int paddingLeft = mediaViewHolder.seekBar.getPaddingLeft();
        MediaViewHolder mediaViewHolder2 = this.holder;
        Objects.requireNonNull(mediaViewHolder2);
        int paddingRight = mediaViewHolder2.seekBar.getPaddingRight();
        MediaViewHolder mediaViewHolder3 = this.holder;
        Objects.requireNonNull(mediaViewHolder3);
        int paddingBottom = mediaViewHolder3.seekBar.getPaddingBottom();
        MediaViewHolder mediaViewHolder4 = this.holder;
        Objects.requireNonNull(mediaViewHolder4);
        mediaViewHolder4.seekBar.setPadding(paddingLeft, i, paddingRight, paddingBottom);
    }

    public SeekBarObserver(MediaViewHolder mediaViewHolder, boolean z) {
        int i;
        int i2;
        this.holder = mediaViewHolder;
        this.seekBarEnabledMaxHeight = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_enabled_seekbar_height);
        this.seekBarDisabledHeight = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_disabled_seekbar_height);
        if (z) {
            i = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_session_enabled_seekbar_vertical_padding);
        } else {
            i = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_enabled_seekbar_vertical_padding);
        }
        this.seekBarEnabledVerticalPadding = i;
        if (z) {
            i2 = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_session_disabled_seekbar_vertical_padding);
        } else {
            i2 = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_disabled_seekbar_vertical_padding);
        }
        this.seekBarDisabledVerticalPadding = i2;
    }
}
