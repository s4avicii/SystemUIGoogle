package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.animation.TransitionLayout;
import java.util.Objects;

/* compiled from: MediaViewHolder.kt */
public abstract class MediaViewHolder {
    public final ImageView albumView;
    public final ImageView appIcon;
    public final TextView artistText;
    public final View cancel;
    public final TextView cancelText;
    public final ViewGroup dismiss;
    public final TextView dismissText;
    public final TextView longPressText;
    public final TransitionLayout player;
    public final ViewGroup seamless;
    public final View seamlessButton;
    public final ImageView seamlessIcon;
    public final TextView seamlessText;
    public final SeekBar seekBar;
    public final View settings;
    public final TextView settingsText;
    public final TextView titleText;

    public abstract ImageButton getAction(int i);

    public TextView getElapsedTimeView() {
        return null;
    }

    public TextView getTotalTimeView() {
        return null;
    }

    public final void marquee(boolean z) {
        Handler handler = this.longPressText.getHandler();
        if (handler == null) {
            Log.d("MediaViewHolder", "marquee while longPressText.getHandler() is null", new Exception());
        } else {
            handler.postDelayed(new MediaViewHolder$marquee$1(this, z), 500);
        }
    }

    public MediaViewHolder(View view) {
        TransitionLayout transitionLayout = (TransitionLayout) view;
        this.player = transitionLayout;
        this.albumView = (ImageView) view.requireViewById(C1777R.C1779id.album_art);
        this.appIcon = (ImageView) view.requireViewById(C1777R.C1779id.icon);
        this.titleText = (TextView) view.requireViewById(C1777R.C1779id.header_title);
        this.artistText = (TextView) view.requireViewById(C1777R.C1779id.header_artist);
        ViewGroup viewGroup = (ViewGroup) view.requireViewById(C1777R.C1779id.media_seamless);
        this.seamless = viewGroup;
        this.seamlessIcon = (ImageView) view.requireViewById(C1777R.C1779id.media_seamless_image);
        this.seamlessText = (TextView) view.requireViewById(C1777R.C1779id.media_seamless_text);
        this.seamlessButton = view.requireViewById(C1777R.C1779id.media_seamless_button);
        this.seekBar = (SeekBar) view.requireViewById(C1777R.C1779id.media_progress_bar);
        this.longPressText = (TextView) view.requireViewById(C1777R.C1779id.remove_text);
        View requireViewById = view.requireViewById(C1777R.C1779id.cancel);
        this.cancel = requireViewById;
        this.cancelText = (TextView) view.requireViewById(C1777R.C1779id.cancel_text);
        ViewGroup viewGroup2 = (ViewGroup) view.requireViewById(C1777R.C1779id.dismiss);
        this.dismiss = viewGroup2;
        this.dismissText = (TextView) view.requireViewById(C1777R.C1779id.dismiss_text);
        View requireViewById2 = view.requireViewById(C1777R.C1779id.settings);
        this.settings = requireViewById2;
        this.settingsText = (TextView) view.requireViewById(C1777R.C1779id.settings_text);
        Drawable background = transitionLayout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type com.android.systemui.media.IlluminationDrawable");
        IlluminationDrawable illuminationDrawable = (IlluminationDrawable) background;
        illuminationDrawable.registerLightSource((View) viewGroup);
        illuminationDrawable.registerLightSource(requireViewById);
        illuminationDrawable.registerLightSource((View) viewGroup2);
        illuminationDrawable.registerLightSource(requireViewById2);
    }
}
