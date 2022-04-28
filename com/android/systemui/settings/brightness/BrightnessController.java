package com.android.systemui.settings.brightness;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Log;
import android.util.MathUtils;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$anim;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.settings.brightness.ToggleSlider;
import java.util.Objects;

public final class BrightnessController implements ToggleSlider.Listener, MirroredBrightnessController {
    public static final Uri BRIGHTNESS_FOR_VR_FLOAT_URI = Settings.System.getUriFor("screen_brightness_for_vr_float");
    public static final Uri BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
    public volatile boolean mAutomatic;
    public final Handler mBackgroundHandler;
    public float mBrightnessMax = 1.0f;
    public float mBrightnessMin = 0.0f;
    public final BrightnessObserver mBrightnessObserver;
    public final Context mContext;
    public final ToggleSlider mControl;
    public boolean mControlValueInitialized;
    public final int mDisplayId;
    public final C10981 mDisplayListener = new DisplayManager.DisplayListener() {
        public final void onDisplayAdded(int i) {
        }

        public final void onDisplayRemoved(int i) {
        }

        public final void onDisplayChanged(int i) {
            BrightnessController brightnessController = BrightnessController.this;
            brightnessController.mBackgroundHandler.post(brightnessController.mUpdateSliderRunnable);
        }
    };
    public final DisplayManager mDisplayManager;
    public boolean mExternalChange;
    public final C11057 mHandler;
    public volatile boolean mIsVrModeEnabled;
    public boolean mListening;
    public final float mMaximumBacklightForVr;
    public final float mMinimumBacklightForVr;
    public ValueAnimator mSliderAnimator;
    public final C11002 mStartListeningRunnable = new Runnable() {
        public final void run() {
            BrightnessController brightnessController = BrightnessController.this;
            if (!brightnessController.mListening) {
                brightnessController.mListening = true;
                IVrManager iVrManager = brightnessController.mVrManager;
                if (iVrManager != null) {
                    try {
                        iVrManager.registerListener(brightnessController.mVrStateCallbacks);
                        BrightnessController brightnessController2 = BrightnessController.this;
                        brightnessController2.mIsVrModeEnabled = brightnessController2.mVrManager.getVrModeState();
                    } catch (RemoteException e) {
                        Log.e("StatusBar.BrightnessController", "Failed to register VR mode state listener: ", e);
                    }
                }
                BrightnessObserver brightnessObserver = BrightnessController.this.mBrightnessObserver;
                Objects.requireNonNull(brightnessObserver);
                ContentResolver contentResolver = BrightnessController.this.mContext.getContentResolver();
                contentResolver.unregisterContentObserver(brightnessObserver);
                contentResolver.registerContentObserver(BrightnessController.BRIGHTNESS_MODE_URI, false, brightnessObserver, -1);
                contentResolver.registerContentObserver(BrightnessController.BRIGHTNESS_FOR_VR_FLOAT_URI, false, brightnessObserver, -1);
                BrightnessController brightnessController3 = BrightnessController.this;
                brightnessController3.mDisplayManager.registerDisplayListener(brightnessController3.mDisplayListener, brightnessController3.mHandler, 8);
                BrightnessController.this.mUserTracker.startTracking();
                BrightnessController.this.mUpdateModeRunnable.run();
                BrightnessController.this.mUpdateSliderRunnable.run();
                BrightnessController.this.mHandler.sendEmptyMessage(2);
            }
        }
    };
    public final C11013 mStopListeningRunnable = new Runnable() {
        public final void run() {
            BrightnessController brightnessController = BrightnessController.this;
            if (brightnessController.mListening) {
                brightnessController.mListening = false;
                IVrManager iVrManager = brightnessController.mVrManager;
                if (iVrManager != null) {
                    try {
                        iVrManager.unregisterListener(brightnessController.mVrStateCallbacks);
                    } catch (RemoteException e) {
                        Log.e("StatusBar.BrightnessController", "Failed to unregister VR mode state listener: ", e);
                    }
                }
                BrightnessObserver brightnessObserver = BrightnessController.this.mBrightnessObserver;
                Objects.requireNonNull(brightnessObserver);
                BrightnessController.this.mContext.getContentResolver().unregisterContentObserver(brightnessObserver);
                BrightnessController brightnessController2 = BrightnessController.this;
                brightnessController2.mDisplayManager.unregisterDisplayListener(brightnessController2.mDisplayListener);
                BrightnessController.this.mUserTracker.stopTracking();
                BrightnessController.this.mHandler.sendEmptyMessage(3);
            }
        }
    };
    public final C11024 mUpdateModeRunnable = new Runnable() {
        public final void run() {
            boolean z = false;
            int intForUser = Settings.System.getIntForUser(BrightnessController.this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2);
            BrightnessController brightnessController = BrightnessController.this;
            if (intForUser != 0) {
                z = true;
            }
            brightnessController.mAutomatic = z;
        }
    };
    public final C11035 mUpdateSliderRunnable = new Runnable() {
        public final void run() {
            boolean z = BrightnessController.this.mIsVrModeEnabled;
            BrightnessInfo brightnessInfo = BrightnessController.this.mContext.getDisplay().getBrightnessInfo();
            if (brightnessInfo != null) {
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mBrightnessMax = brightnessInfo.brightnessMaximum;
                brightnessController.mBrightnessMin = brightnessInfo.brightnessMinimum;
                BrightnessController.this.mHandler.obtainMessage(1, Float.floatToIntBits(brightnessInfo.brightness), z ? 1 : 0).sendToTarget();
            }
        }
    };
    public final C11068 mUserTracker;
    public final IVrManager mVrManager;
    public final C11046 mVrStateCallbacks = new IVrStateCallbacks.Stub() {
        public final void onVrStateChanged(boolean z) {
            BrightnessController.this.mHandler.obtainMessage(4, z ? 1 : 0, 0).sendToTarget();
        }
    };

    public class BrightnessObserver extends ContentObserver {
        public BrightnessObserver(C11057 r2) {
            super(r2);
        }

        public final void onChange(boolean z, Uri uri) {
            if (!z) {
                if (BrightnessController.BRIGHTNESS_MODE_URI.equals(uri)) {
                    BrightnessController brightnessController = BrightnessController.this;
                    brightnessController.mBackgroundHandler.post(brightnessController.mUpdateModeRunnable);
                    BrightnessController brightnessController2 = BrightnessController.this;
                    brightnessController2.mBackgroundHandler.post(brightnessController2.mUpdateSliderRunnable);
                } else if (BrightnessController.BRIGHTNESS_FOR_VR_FLOAT_URI.equals(uri)) {
                    BrightnessController brightnessController3 = BrightnessController.this;
                    brightnessController3.mBackgroundHandler.post(brightnessController3.mUpdateSliderRunnable);
                } else {
                    BrightnessController brightnessController4 = BrightnessController.this;
                    brightnessController4.mBackgroundHandler.post(brightnessController4.mUpdateModeRunnable);
                    BrightnessController brightnessController5 = BrightnessController.this;
                    brightnessController5.mBackgroundHandler.post(brightnessController5.mUpdateSliderRunnable);
                }
            }
        }
    }

    public static class Factory {
        public final Handler mBackgroundHandler;
        public final BroadcastDispatcher mBroadcastDispatcher;
        public final Context mContext;

        public Factory(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler) {
            this.mContext = context;
            this.mBroadcastDispatcher = broadcastDispatcher;
            this.mBackgroundHandler = handler;
        }
    }

    public final void onChanged(boolean z, int i, boolean z2) {
        float f;
        float f2;
        int i2;
        if (!this.mExternalChange) {
            ValueAnimator valueAnimator = this.mSliderAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (this.mIsVrModeEnabled) {
                i2 = 498;
                f2 = this.mMinimumBacklightForVr;
                f = this.mMaximumBacklightForVr;
            } else {
                if (this.mAutomatic) {
                    i2 = 219;
                } else {
                    i2 = 218;
                }
                f2 = this.mBrightnessMin;
                f = this.mBrightnessMax;
            }
            final float min = MathUtils.min(R$anim.convertGammaToLinearFloat(i, f2, f), f);
            if (z2) {
                MetricsLogger.action(this.mContext, i2, BrightnessSynchronizer.brightnessFloatToInt(min));
            }
            this.mDisplayManager.setTemporaryBrightness(this.mDisplayId, min);
            if (!z) {
                AsyncTask.execute(new Runnable() {
                    public final void run() {
                        BrightnessController brightnessController = BrightnessController.this;
                        brightnessController.mDisplayManager.setBrightness(brightnessController.mDisplayId, min);
                    }
                });
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0082, code lost:
        if (((com.android.systemui.settings.brightness.BrightnessSliderView) r1.mView).isVisibleToUser() == false) goto L_0x0084;
     */
    /* renamed from: -$$Nest$mupdateSlider  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m224$$Nest$mupdateSlider(com.android.systemui.settings.brightness.BrightnessController r4, float r5, boolean r6) {
        /*
            java.util.Objects.requireNonNull(r4)
            if (r6 == 0) goto L_0x000a
            float r6 = r4.mMinimumBacklightForVr
            float r0 = r4.mMaximumBacklightForVr
            goto L_0x000e
        L_0x000a:
            float r6 = r4.mBrightnessMin
            float r0 = r4.mBrightnessMax
        L_0x000e:
            android.animation.ValueAnimator r1 = r4.mSliderAnimator
            if (r1 == 0) goto L_0x001d
            boolean r1 = r1.isStarted()
            if (r1 == 0) goto L_0x001d
            android.animation.ValueAnimator r1 = r4.mSliderAnimator
            r1.cancel()
        L_0x001d:
            com.android.systemui.settings.brightness.ToggleSlider r1 = r4.mControl
            com.android.systemui.settings.brightness.BrightnessSliderController r1 = (com.android.systemui.settings.brightness.BrightnessSliderController) r1
            java.util.Objects.requireNonNull(r1)
            T r1 = r1.mView
            com.android.systemui.settings.brightness.BrightnessSliderView r1 = (com.android.systemui.settings.brightness.BrightnessSliderView) r1
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.settings.brightness.ToggleSeekBar r1 = r1.mSlider
            int r1 = r1.getProgress()
            float r1 = com.android.systemui.R$anim.convertGammaToLinearFloat(r1, r6, r0)
            boolean r1 = com.android.internal.display.BrightnessSynchronizer.floatEquals(r5, r1)
            if (r1 == 0) goto L_0x003d
            goto L_0x00dd
        L_0x003d:
            float r5 = android.util.MathUtils.norm(r6, r0, r5)
            r6 = 1094713344(0x41400000, float:12.0)
            float r5 = r5 * r6
            r6 = 1065353216(0x3f800000, float:1.0)
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L_0x0052
            float r5 = android.util.MathUtils.sqrt(r5)
            r6 = 1056964608(0x3f000000, float:0.5)
            float r5 = r5 * r6
            goto L_0x0062
        L_0x0052:
            r6 = 1043800048(0x3e371ff0, float:0.17883277)
            r0 = 1049739296(0x3e91c020, float:0.28466892)
            float r5 = r5 - r0
            float r5 = android.util.MathUtils.log(r5)
            float r5 = r5 * r6
            r6 = 1057969743(0x3f0f564f, float:0.5599107)
            float r5 = r5 + r6
        L_0x0062:
            r6 = 0
            r0 = 65535(0xffff, float:9.1834E-41)
            float r5 = android.util.MathUtils.lerp(r6, r0, r5)
            int r5 = java.lang.Math.round(r5)
            boolean r1 = r4.mControlValueInitialized
            r2 = 1
            if (r1 == 0) goto L_0x0084
            com.android.systemui.settings.brightness.ToggleSlider r1 = r4.mControl
            com.android.systemui.settings.brightness.BrightnessSliderController r1 = (com.android.systemui.settings.brightness.BrightnessSliderController) r1
            java.util.Objects.requireNonNull(r1)
            T r1 = r1.mView
            com.android.systemui.settings.brightness.BrightnessSliderView r1 = (com.android.systemui.settings.brightness.BrightnessSliderView) r1
            boolean r1 = r1.isVisibleToUser()
            if (r1 != 0) goto L_0x008d
        L_0x0084:
            com.android.systemui.settings.brightness.ToggleSlider r1 = r4.mControl
            com.android.systemui.settings.brightness.BrightnessSliderController r1 = (com.android.systemui.settings.brightness.BrightnessSliderController) r1
            r1.setValue(r5)
            r4.mControlValueInitialized = r2
        L_0x008d:
            r1 = 2
            int[] r1 = new int[r1]
            com.android.systemui.settings.brightness.ToggleSlider r3 = r4.mControl
            com.android.systemui.settings.brightness.BrightnessSliderController r3 = (com.android.systemui.settings.brightness.BrightnessSliderController) r3
            java.util.Objects.requireNonNull(r3)
            T r3 = r3.mView
            com.android.systemui.settings.brightness.BrightnessSliderView r3 = (com.android.systemui.settings.brightness.BrightnessSliderView) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.settings.brightness.ToggleSeekBar r3 = r3.mSlider
            int r3 = r3.getProgress()
            r1[r6] = r3
            r1[r2] = r5
            android.animation.ValueAnimator r6 = android.animation.ValueAnimator.ofInt(r1)
            r4.mSliderAnimator = r6
            com.android.systemui.settings.brightness.BrightnessController$$ExternalSyntheticLambda0 r1 = new com.android.systemui.settings.brightness.BrightnessController$$ExternalSyntheticLambda0
            r1.<init>(r4)
            r6.addUpdateListener(r1)
            com.android.systemui.settings.brightness.ToggleSlider r6 = r4.mControl
            com.android.systemui.settings.brightness.BrightnessSliderController r6 = (com.android.systemui.settings.brightness.BrightnessSliderController) r6
            java.util.Objects.requireNonNull(r6)
            T r6 = r6.mView
            com.android.systemui.settings.brightness.BrightnessSliderView r6 = (com.android.systemui.settings.brightness.BrightnessSliderView) r6
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.settings.brightness.ToggleSeekBar r6 = r6.mSlider
            int r6 = r6.getProgress()
            int r6 = r6 - r5
            int r5 = java.lang.Math.abs(r6)
            int r5 = r5 * 3000
            int r5 = r5 / r0
            long r5 = (long) r5
            android.animation.ValueAnimator r0 = r4.mSliderAnimator
            r0.setDuration(r5)
            android.animation.ValueAnimator r4 = r4.mSliderAnimator
            r4.start()
        L_0x00dd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.brightness.BrightnessController.m224$$Nest$mupdateSlider(com.android.systemui.settings.brightness.BrightnessController, float, boolean):void");
    }

    public BrightnessController(Context context, BrightnessSliderController brightnessSliderController, BroadcastDispatcher broadcastDispatcher, Handler handler) {
        C11057 r0 = new Handler() {
            public final void handleMessage(Message message) {
                BrightnessController brightnessController = BrightnessController.this;
                boolean z = true;
                brightnessController.mExternalChange = true;
                try {
                    int i = message.what;
                    if (i == 1) {
                        float intBitsToFloat = Float.intBitsToFloat(message.arg1);
                        if (message.arg2 == 0) {
                            z = false;
                        }
                        BrightnessController.m224$$Nest$mupdateSlider(brightnessController, intBitsToFloat, z);
                    } else if (i == 2) {
                        BrightnessSliderController brightnessSliderController = (BrightnessSliderController) brightnessController.mControl;
                        Objects.requireNonNull(brightnessSliderController);
                        brightnessSliderController.mListener = brightnessController;
                    } else if (i == 3) {
                        BrightnessSliderController brightnessSliderController2 = (BrightnessSliderController) brightnessController.mControl;
                        Objects.requireNonNull(brightnessSliderController2);
                        brightnessSliderController2.mListener = null;
                    } else if (i != 4) {
                        super.handleMessage(message);
                    } else {
                        if (message.arg1 == 0) {
                            z = false;
                        }
                        if (brightnessController.mIsVrModeEnabled != z) {
                            brightnessController.mIsVrModeEnabled = z;
                            brightnessController.mBackgroundHandler.post(brightnessController.mUpdateSliderRunnable);
                        }
                    }
                } finally {
                    BrightnessController.this.mExternalChange = false;
                }
            }
        };
        this.mHandler = r0;
        this.mContext = context;
        this.mControl = brightnessSliderController;
        brightnessSliderController.setMax(65535);
        this.mBackgroundHandler = handler;
        this.mUserTracker = new CurrentUserTracker(broadcastDispatcher) {
            public final void onUserSwitched(int i) {
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mBackgroundHandler.post(brightnessController.mUpdateModeRunnable);
                BrightnessController brightnessController2 = BrightnessController.this;
                brightnessController2.mBackgroundHandler.post(brightnessController2.mUpdateSliderRunnable);
            }
        };
        this.mBrightnessObserver = new BrightnessObserver(r0);
        this.mDisplayId = context.getDisplayId();
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mMinimumBacklightForVr = powerManager.getBrightnessConstraint(5);
        this.mMaximumBacklightForVr = powerManager.getBrightnessConstraint(6);
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mVrManager = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
    }
}
