package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: PlayerSessionViewHolder.kt */
public final class PlayerSessionViewHolder extends MediaViewHolder {
    public static final Set<Integer> controlsIds;
    public static final Set<Integer> gutsIds = SetsKt__SetsKt.setOf(Integer.valueOf(C1777R.C1779id.remove_text), Integer.valueOf(C1777R.C1779id.cancel), Integer.valueOf(C1777R.C1779id.dismiss), Integer.valueOf(C1777R.C1779id.settings));
    public final ImageButton actionEnd;
    public final ImageButton actionNext;
    public final ImageButton actionPlayPause;
    public final ImageButton actionPrev;
    public final ImageButton actionStart;

    static {
        Integer valueOf = Integer.valueOf(C1777R.C1779id.icon);
        controlsIds = SetsKt__SetsKt.setOf(valueOf, Integer.valueOf(C1777R.C1779id.app_name), Integer.valueOf(C1777R.C1779id.header_title), Integer.valueOf(C1777R.C1779id.header_artist), Integer.valueOf(C1777R.C1779id.media_seamless), Integer.valueOf(C1777R.C1779id.media_progress_bar), Integer.valueOf(C1777R.C1779id.actionPlayPause), Integer.valueOf(C1777R.C1779id.actionNext), Integer.valueOf(C1777R.C1779id.actionPrev), Integer.valueOf(C1777R.C1779id.actionStart), Integer.valueOf(C1777R.C1779id.actionEnd), valueOf);
    }

    public PlayerSessionViewHolder(View view) {
        super(view);
        ImageButton imageButton = (ImageButton) view.requireViewById(C1777R.C1779id.actionPlayPause);
        this.actionPlayPause = imageButton;
        ImageButton imageButton2 = (ImageButton) view.requireViewById(C1777R.C1779id.actionNext);
        this.actionNext = imageButton2;
        ImageButton imageButton3 = (ImageButton) view.requireViewById(C1777R.C1779id.actionPrev);
        this.actionPrev = imageButton3;
        ImageButton imageButton4 = (ImageButton) view.requireViewById(C1777R.C1779id.actionStart);
        this.actionStart = imageButton4;
        ImageButton imageButton5 = (ImageButton) view.requireViewById(C1777R.C1779id.actionEnd);
        this.actionEnd = imageButton5;
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
        if (i == C1777R.C1779id.actionPlayPause) {
            return this.actionPlayPause;
        }
        if (i == C1777R.C1779id.actionNext) {
            return this.actionNext;
        }
        if (i == C1777R.C1779id.actionPrev) {
            return this.actionPrev;
        }
        if (i == C1777R.C1779id.actionStart) {
            return this.actionStart;
        }
        if (i == C1777R.C1779id.actionEnd) {
            return this.actionEnd;
        }
        throw new IllegalArgumentException();
    }
}
