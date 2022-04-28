package com.google.android.systemui.smartspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.os.Handler;
import android.util.AttributeSet;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16;
import java.util.Locale;
import java.util.Objects;

public class IcuDateTextView extends DoubleShadowTextView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DateFormat mFormatter;
    public Handler mHandler;
    public final C23111 mIntentReceiver;
    public String mText;
    public final BubbleStackView$$ExternalSyntheticLambda16 mTicker;

    public IcuDateTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public IcuDateTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mTicker = new BubbleStackView$$ExternalSyntheticLambda16(this, 5);
        this.mIntentReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                IcuDateTextView icuDateTextView = IcuDateTextView.this;
                int i = IcuDateTextView.$r8$clinit;
                icuDateTextView.onTimeChanged(!"android.intent.action.TIME_TICK".equals(intent.getAction()));
            }
        };
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        getContext().registerReceiver(this.mIntentReceiver, intentFilter);
        onTimeChanged(true);
        this.mHandler = new Handler();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mHandler != null) {
            getContext().unregisterReceiver(this.mIntentReceiver);
            this.mHandler = null;
        }
    }

    public final void onTimeChanged(boolean z) {
        if (isShown()) {
            if (this.mFormatter == null || z) {
                DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(getContext().getString(C1777R.string.smartspace_icu_date_pattern), Locale.getDefault());
                this.mFormatter = instanceForSkeleton;
                instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
            }
            String format = this.mFormatter.format(Long.valueOf(System.currentTimeMillis()));
            if (!Objects.equals(this.mText, format)) {
                this.mText = format;
                setText(format);
                setContentDescription(format);
            }
        }
    }

    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mTicker);
            if (z) {
                this.mTicker.run();
            }
        }
    }
}
