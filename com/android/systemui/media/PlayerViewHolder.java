package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: PlayerViewHolder.kt */
public final class PlayerViewHolder extends MediaViewHolder {
    public static final Set<Integer> controlsIds;
    public static final Set<Integer> gutsIds = SetsKt__SetsKt.setOf(Integer.valueOf(C1777R.C1779id.remove_text), Integer.valueOf(C1777R.C1779id.cancel), Integer.valueOf(C1777R.C1779id.dismiss), Integer.valueOf(C1777R.C1779id.settings));
    public final ImageButton action0;
    public final ImageButton action1;
    public final ImageButton action2;
    public final ImageButton action3;
    public final ImageButton action4;
    public final TextView elapsedTimeView;
    public final ViewGroup progressTimes;
    public final TextView totalTimeView;

    static {
        Integer valueOf = Integer.valueOf(C1777R.C1779id.icon);
        controlsIds = SetsKt__SetsKt.setOf(valueOf, Integer.valueOf(C1777R.C1779id.app_name), Integer.valueOf(C1777R.C1779id.album_art), Integer.valueOf(C1777R.C1779id.header_title), Integer.valueOf(C1777R.C1779id.header_artist), Integer.valueOf(C1777R.C1779id.media_seamless), Integer.valueOf(C1777R.C1779id.notification_media_progress_time), Integer.valueOf(C1777R.C1779id.media_progress_bar), Integer.valueOf(C1777R.C1779id.action0), Integer.valueOf(C1777R.C1779id.action1), Integer.valueOf(C1777R.C1779id.action2), Integer.valueOf(C1777R.C1779id.action3), Integer.valueOf(C1777R.C1779id.action4), valueOf);
    }

    public PlayerViewHolder(View view) {
        super(view);
        this.progressTimes = (ViewGroup) view.requireViewById(C1777R.C1779id.notification_media_progress_time);
        this.elapsedTimeView = (TextView) view.requireViewById(C1777R.C1779id.media_elapsed_time);
        this.totalTimeView = (TextView) view.requireViewById(C1777R.C1779id.media_total_time);
        ImageButton imageButton = (ImageButton) view.requireViewById(C1777R.C1779id.action0);
        this.action0 = imageButton;
        ImageButton imageButton2 = (ImageButton) view.requireViewById(C1777R.C1779id.action1);
        this.action1 = imageButton2;
        ImageButton imageButton3 = (ImageButton) view.requireViewById(C1777R.C1779id.action2);
        this.action2 = imageButton3;
        ImageButton imageButton4 = (ImageButton) view.requireViewById(C1777R.C1779id.action3);
        this.action3 = imageButton4;
        ImageButton imageButton5 = (ImageButton) view.requireViewById(C1777R.C1779id.action4);
        this.action4 = imageButton5;
        Drawable background = this.player.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type com.android.systemui.media.IlluminationDrawable");
        IlluminationDrawable illuminationDrawable = (IlluminationDrawable) background;
        illuminationDrawable.registerLightSource((View) imageButton);
        illuminationDrawable.registerLightSource((View) imageButton2);
        illuminationDrawable.registerLightSource((View) imageButton3);
        illuminationDrawable.registerLightSource((View) imageButton4);
        illuminationDrawable.registerLightSource((View) imageButton5);
    }

    public final ImageButton getAction(int i) {
        if (i == C1777R.C1779id.action0) {
            return this.action0;
        }
        if (i == C1777R.C1779id.action1) {
            return this.action1;
        }
        if (i == C1777R.C1779id.action2) {
            return this.action2;
        }
        if (i == C1777R.C1779id.action3) {
            return this.action3;
        }
        if (i == C1777R.C1779id.action4) {
            return this.action4;
        }
        throw new IllegalArgumentException();
    }

    public final TextView getElapsedTimeView() {
        return this.elapsedTimeView;
    }

    public final TextView getTotalTimeView() {
        return this.totalTimeView;
    }
}
