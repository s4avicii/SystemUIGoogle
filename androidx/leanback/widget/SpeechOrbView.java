package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import androidx.leanback.widget.SearchOrbView;
import com.android.p012wm.shell.C1777R;

public class SpeechOrbView extends SearchOrbView {
    public SearchOrbView.Colors mNotListeningOrbColors;

    public SpeechOrbView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final int getLayoutResourceId() {
        return C1777R.layout.lb_speech_orb;
    }

    public SpeechOrbView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SpeechOrbView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Resources resources = context.getResources();
        resources.getFraction(C1777R.fraction.lb_search_bar_speech_orb_max_level_zoom, 1, 1);
        this.mNotListeningOrbColors = new SearchOrbView.Colors(resources.getColor(C1777R.color.lb_speech_orb_not_recording), resources.getColor(C1777R.color.lb_speech_orb_not_recording_pulsed), resources.getColor(C1777R.color.lb_speech_orb_not_recording_icon));
        new SearchOrbView.Colors(resources.getColor(C1777R.color.lb_speech_orb_recording), resources.getColor(C1777R.color.lb_speech_orb_recording), 0);
        SearchOrbView.Colors colors = this.mNotListeningOrbColors;
        this.mColors = colors;
        this.mIcon.setColorFilter(colors.iconColor);
        if (this.mColorAnimator == null) {
            int i2 = this.mColors.color;
            if (this.mSearchOrbView.getBackground() instanceof GradientDrawable) {
                ((GradientDrawable) this.mSearchOrbView.getBackground()).setColor(i2);
            }
        } else {
            this.mColorAnimationEnabled = true;
            updateColorAnimator();
        }
        this.mIcon.setImageDrawable(getResources().getDrawable(C1777R.C1778drawable.lb_ic_search_mic_out));
        animateOnFocus(hasFocus());
        this.mSearchOrbView.setScaleX(1.0f);
        this.mSearchOrbView.setScaleY(1.0f);
    }
}
