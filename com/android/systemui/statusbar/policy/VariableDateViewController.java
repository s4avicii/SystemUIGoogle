package com.android.systemui.statusbar.policy;

import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.SystemClock;
import java.util.Date;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewController extends ViewController<VariableDateView> {
    public final BroadcastDispatcher broadcastDispatcher;
    public Date currentTime = new Date();
    public DateFormat dateFormat;
    public String datePattern;
    public final VariableDateViewController$intentReceiver$1 intentReceiver = new VariableDateViewController$intentReceiver$1(this);
    public String lastText = "";
    public int lastWidth = Integer.MAX_VALUE;
    public final VariableDateViewController$onMeasureListener$1 onMeasureListener = new VariableDateViewController$onMeasureListener$1(this);
    public final SystemClock systemClock;
    public final Handler timeTickHandler;

    public final void onViewDetached() {
        this.dateFormat = null;
        VariableDateView variableDateView = (VariableDateView) this.mView;
        Objects.requireNonNull(variableDateView);
        variableDateView.onMeasureListener = null;
        this.broadcastDispatcher.unregisterReceiver(this.intentReceiver);
    }

    /* compiled from: VariableDateViewController.kt */
    public static final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final Handler handler;
        public final SystemClock systemClock;

        public Factory(SystemClock systemClock2, BroadcastDispatcher broadcastDispatcher2, Handler handler2) {
            this.systemClock = systemClock2;
            this.broadcastDispatcher = broadcastDispatcher2;
            this.handler = handler2;
        }
    }

    public final void changePattern(String str) {
        boolean z;
        if (!str.equals(this.datePattern) && !Intrinsics.areEqual(this.datePattern, str)) {
            this.datePattern = str;
            this.dateFormat = null;
            T t = this.mView;
            if (t == null || !t.isAttachedToWindow()) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                VariableDateViewController$datePattern$1 variableDateViewController$datePattern$1 = new VariableDateViewController$datePattern$1(this);
                Handler handler = ((VariableDateView) this.mView).getHandler();
                if (handler != null) {
                    handler.post(new VariableDateViewControllerKt$sam$java_lang_Runnable$0(variableDateViewController$datePattern$1));
                }
            }
        }
    }

    public final void onViewAttached() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.intentReceiver, intentFilter, new HandlerExecutor(this.timeTickHandler), UserHandle.SYSTEM, 16);
        VariableDateViewController$onViewAttached$1 variableDateViewController$onViewAttached$1 = new VariableDateViewController$onViewAttached$1(this);
        Handler handler = ((VariableDateView) this.mView).getHandler();
        if (handler != null) {
            handler.post(new VariableDateViewControllerKt$sam$java_lang_Runnable$0(variableDateViewController$onViewAttached$1));
        }
        VariableDateView variableDateView = (VariableDateView) this.mView;
        VariableDateViewController$onMeasureListener$1 variableDateViewController$onMeasureListener$1 = this.onMeasureListener;
        Objects.requireNonNull(variableDateView);
        variableDateView.onMeasureListener = variableDateViewController$onMeasureListener$1;
    }

    public VariableDateViewController(SystemClock systemClock2, BroadcastDispatcher broadcastDispatcher2, Handler handler, VariableDateView variableDateView) {
        super(variableDateView);
        this.systemClock = systemClock2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.timeTickHandler = handler;
        this.datePattern = variableDateView.longerPattern;
    }

    public static final void access$updateClock(VariableDateViewController variableDateViewController) {
        Objects.requireNonNull(variableDateViewController);
        if (variableDateViewController.dateFormat == null) {
            variableDateViewController.dateFormat = VariableDateViewControllerKt.getFormatFromPattern(variableDateViewController.datePattern);
        }
        variableDateViewController.currentTime.setTime(variableDateViewController.systemClock.currentTimeMillis());
        Date date = variableDateViewController.currentTime;
        DateFormat dateFormat2 = variableDateViewController.dateFormat;
        Intrinsics.checkNotNull(dateFormat2);
        String textForFormat = VariableDateViewControllerKt.getTextForFormat(date, dateFormat2);
        if (!Intrinsics.areEqual(textForFormat, variableDateViewController.lastText)) {
            ((VariableDateView) variableDateViewController.mView).setText(textForFormat);
            variableDateViewController.lastText = textForFormat;
        }
    }
}
