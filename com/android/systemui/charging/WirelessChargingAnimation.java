package com.android.systemui.charging;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;

public final class WirelessChargingAnimation {
    public static final boolean DEBUG = Log.isLoggable("WirelessChargingView", 3);
    public static WirelessChargingView mPreviousWirelessChargingView;
    public final WirelessChargingView mCurrentWirelessChargingView;

    public interface Callback {
    }

    public static class WirelessChargingView {
        public Callback mCallback;
        public final C07141 mHandler;
        public WirelessChargingLayout mNextView;
        public final WindowManager.LayoutParams mParams;
        public final UiEventLogger mUiEventLogger;
        public WirelessChargingLayout mView;
        public WindowManager mWM;

        public enum WirelessChargingRippleEvent implements UiEventLogger.UiEventEnum {
            ;
            
            private final int mInt;

            /* access modifiers changed from: public */
            WirelessChargingRippleEvent() {
                this.mInt = 830;
            }

            public final int getId() {
                return this.mInt;
            }
        }

        public final void handleHide() {
            boolean z = WirelessChargingAnimation.DEBUG;
            if (z) {
                Slog.d("WirelessChargingView", "HANDLE HIDE: " + this + " mView=" + this.mView);
            }
            WirelessChargingLayout wirelessChargingLayout = this.mView;
            if (wirelessChargingLayout != null) {
                if (wirelessChargingLayout.getParent() != null) {
                    if (z) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("REMOVE! ");
                        m.append(this.mView);
                        m.append(" in ");
                        m.append(this);
                        Slog.d("WirelessChargingView", m.toString());
                    }
                    Callback callback = this.mCallback;
                    if (callback != null) {
                        StatusBar.C15536 r0 = (StatusBar.C15536) callback;
                        Objects.requireNonNull(r0);
                        StatusBar.this.mNotificationShadeWindowController.setRequestTopUi(false, "StatusBar");
                    }
                    this.mWM.removeViewImmediate(this.mView);
                }
                this.mView = null;
            }
        }

        public final void hide(long j) {
            this.mHandler.removeMessages(1);
            if (WirelessChargingAnimation.DEBUG) {
                Slog.d("WirelessChargingView", "HIDE: " + this);
            }
            C07141 r3 = this.mHandler;
            r3.sendMessageDelayed(Message.obtain(r3, 1), j);
        }

        public WirelessChargingView(Context context, int i, int i2, StatusBar.C15536 r5, UiEventLoggerImpl uiEventLoggerImpl) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            this.mCallback = r5;
            this.mNextView = new WirelessChargingLayout(context, i, i2);
            this.mUiEventLogger = uiEventLoggerImpl;
            layoutParams.height = -1;
            layoutParams.width = -1;
            layoutParams.format = -3;
            layoutParams.type = 2009;
            layoutParams.setTitle("Charging Animation");
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.setFitInsetsTypes(0);
            layoutParams.flags = 24;
            layoutParams.setTrustedOverlay();
            Looper myLooper = Looper.myLooper();
            if (myLooper != null) {
                this.mHandler = new Handler(myLooper) {
                    public final void handleMessage(Message message) {
                        int i = message.what;
                        if (i == 0) {
                            WirelessChargingView wirelessChargingView = WirelessChargingView.this;
                            Objects.requireNonNull(wirelessChargingView);
                            boolean z = WirelessChargingAnimation.DEBUG;
                            if (z) {
                                Slog.d("WirelessChargingView", "HANDLE SHOW: " + wirelessChargingView + " mView=" + wirelessChargingView.mView + " mNextView=" + wirelessChargingView.mNextView);
                            }
                            if (wirelessChargingView.mView != wirelessChargingView.mNextView) {
                                wirelessChargingView.handleHide();
                                WirelessChargingLayout wirelessChargingLayout = wirelessChargingView.mNextView;
                                wirelessChargingView.mView = wirelessChargingLayout;
                                Context applicationContext = wirelessChargingLayout.getContext().getApplicationContext();
                                String opPackageName = wirelessChargingView.mView.getContext().getOpPackageName();
                                if (applicationContext == null) {
                                    applicationContext = wirelessChargingView.mView.getContext();
                                }
                                wirelessChargingView.mWM = (WindowManager) applicationContext.getSystemService("window");
                                WindowManager.LayoutParams layoutParams = wirelessChargingView.mParams;
                                layoutParams.packageName = opPackageName;
                                layoutParams.hideTimeoutMilliseconds = 1500;
                                if (wirelessChargingView.mView.getParent() != null) {
                                    if (z) {
                                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("REMOVE! ");
                                        m.append(wirelessChargingView.mView);
                                        m.append(" in ");
                                        m.append(wirelessChargingView);
                                        Slog.d("WirelessChargingView", m.toString());
                                    }
                                    wirelessChargingView.mWM.removeView(wirelessChargingView.mView);
                                }
                                if (z) {
                                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ADD! ");
                                    m2.append(wirelessChargingView.mView);
                                    m2.append(" in ");
                                    m2.append(wirelessChargingView);
                                    Slog.d("WirelessChargingView", m2.toString());
                                }
                                try {
                                    Callback callback = wirelessChargingView.mCallback;
                                    if (callback != null) {
                                        StatusBar.this.mNotificationShadeWindowController.setRequestTopUi(true, "StatusBar");
                                    }
                                    wirelessChargingView.mWM.addView(wirelessChargingView.mView, wirelessChargingView.mParams);
                                    wirelessChargingView.mUiEventLogger.log(WirelessChargingRippleEvent.WIRELESS_RIPPLE_PLAYED);
                                } catch (WindowManager.BadTokenException e) {
                                    Slog.d("WirelessChargingView", "Unable to add wireless charging view. " + e);
                                }
                            }
                        } else if (i == 1) {
                            WirelessChargingView.this.handleHide();
                            WirelessChargingView.this.mNextView = null;
                        }
                    }
                };
                return;
            }
            throw new RuntimeException("Can't display wireless animation on a thread that has not called Looper.prepare()");
        }
    }

    public WirelessChargingAnimation(Context context, int i, int i2, StatusBar.C15536 r11, UiEventLoggerImpl uiEventLoggerImpl) {
        this.mCurrentWirelessChargingView = new WirelessChargingView(context, i, i2, r11, uiEventLoggerImpl);
    }
}
