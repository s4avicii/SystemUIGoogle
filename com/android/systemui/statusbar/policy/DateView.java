package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16;
import com.android.systemui.Dependency;
import com.android.systemui.R$styleable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import java.util.Date;
import java.util.Locale;

public class DateView extends TextView {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Date mCurrentTime = new Date();
    public DateFormat mDateFormat;
    public String mDatePattern;
    public C16101 mIntentReceiver = new BroadcastReceiver() {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final void onReceive(Context context, Intent intent) {
            Handler handler = DateView.this.getHandler();
            if (handler != null) {
                String action = intent.getAction();
                if ("android.intent.action.TIME_TICK".equals(action) || "android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action) || "android.intent.action.LOCALE_CHANGED".equals(action)) {
                    if ("android.intent.action.LOCALE_CHANGED".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                        handler.post(new BubbleStackView$$ExternalSyntheticLambda16(this, 4));
                    }
                    handler.post(new StatusBar$$ExternalSyntheticLambda18(this, 11));
                }
            }
        }
    };
    public String mLastText;

    public final void updateClock() {
        if (this.mDateFormat == null) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.mDatePattern, Locale.getDefault());
            instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
            this.mDateFormat = instanceForSkeleton;
        }
        this.mCurrentTime.setTime(System.currentTimeMillis());
        String format = this.mDateFormat.format(this.mCurrentTime);
        if (!format.equals(this.mLastText)) {
            setText(format);
            this.mLastText = format;
        }
    }

    /* JADX INFO: finally extract failed */
    public DateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DateView, 0, 0);
        try {
            this.mDatePattern = obtainStyledAttributes.getString(0);
            obtainStyledAttributes.recycle();
            if (this.mDatePattern == null) {
                this.mDatePattern = getContext().getString(C1777R.string.system_ui_date_pattern);
            }
            this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mIntentReceiver, intentFilter, (Handler) Dependency.get(Dependency.TIME_TICK_HANDLER));
        updateClock();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDateFormat = null;
        this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
    }
}
