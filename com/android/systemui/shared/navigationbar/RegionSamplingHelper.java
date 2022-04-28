package com.android.systemui.shared.navigationbar;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Handler;
import android.view.CompositionSamplingListener;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1;
import com.android.systemui.p006qs.tiles.QuickAccessWalletTile$$ExternalSyntheticLambda0;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;

@TargetApi(29)
public final class RegionSamplingHelper implements View.OnAttachStateChangeListener, View.OnLayoutChangeListener {
    public final Executor mBackgroundExecutor;
    public final SamplingCallback mCallback;
    public final SysuiCompositionSamplingListener mCompositionSamplingListener;
    public float mCurrentMedianLuma;
    public boolean mFirstSamplingAfterStart;
    public final Handler mHandler;
    public boolean mIsDestroyed;
    public float mLastMedianLuma;
    public final Rect mRegisteredSamplingBounds;
    public SurfaceControl mRegisteredStopLayer;
    public C11132 mRemoveDrawRunnable;
    public final View mSampledView;
    public boolean mSamplingEnabled;
    public final C11143 mSamplingListener;
    public boolean mSamplingListenerRegistered;
    public final Rect mSamplingRequestBounds;
    public C11121 mUpdateOnDraw;
    public boolean mWaitingOnDraw;
    public boolean mWindowHasBlurs;
    public boolean mWindowVisible;
    public SurfaceControl mWrappedStopLayer;

    public interface SamplingCallback {
        Rect getSampledRegion();

        boolean isSamplingEnabled() {
            return true;
        }

        void onRegionDarknessChanged(boolean z);
    }

    public static class SysuiCompositionSamplingListener {
    }

    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor) {
        this(view, samplingCallback, view.getContext().getMainExecutor(), executor, new SysuiCompositionSamplingListener());
    }

    public final void stop() {
        this.mSamplingEnabled = false;
        updateSamplingListener();
    }

    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor, Executor executor2, SysuiCompositionSamplingListener sysuiCompositionSamplingListener) {
        this.mHandler = new Handler();
        this.mSamplingRequestBounds = new Rect();
        this.mRegisteredSamplingBounds = new Rect();
        this.mSamplingEnabled = false;
        this.mSamplingListenerRegistered = false;
        this.mRegisteredStopLayer = null;
        this.mWrappedStopLayer = null;
        this.mUpdateOnDraw = new ViewTreeObserver.OnDrawListener() {
            public final void onDraw() {
                RegionSamplingHelper regionSamplingHelper = RegionSamplingHelper.this;
                regionSamplingHelper.mHandler.post(regionSamplingHelper.mRemoveDrawRunnable);
                RegionSamplingHelper regionSamplingHelper2 = RegionSamplingHelper.this;
                Objects.requireNonNull(regionSamplingHelper2);
                if (regionSamplingHelper2.mWaitingOnDraw) {
                    regionSamplingHelper2.mWaitingOnDraw = false;
                    regionSamplingHelper2.updateSamplingListener();
                }
            }
        };
        this.mRemoveDrawRunnable = new Runnable() {
            public final void run() {
                RegionSamplingHelper.this.mSampledView.getViewTreeObserver().removeOnDrawListener(RegionSamplingHelper.this.mUpdateOnDraw);
            }
        };
        this.mBackgroundExecutor = executor2;
        this.mCompositionSamplingListener = sysuiCompositionSamplingListener;
        this.mSamplingListener = new CompositionSamplingListener(executor) {
            public final void onSampleCollected(float f) {
                boolean z;
                RegionSamplingHelper regionSamplingHelper = RegionSamplingHelper.this;
                if (regionSamplingHelper.mSamplingEnabled) {
                    Objects.requireNonNull(regionSamplingHelper);
                    regionSamplingHelper.mCurrentMedianLuma = f;
                    if (Math.abs(f - regionSamplingHelper.mLastMedianLuma) > 0.05f) {
                        SamplingCallback samplingCallback = regionSamplingHelper.mCallback;
                        if (f < 0.5f) {
                            z = true;
                        } else {
                            z = false;
                        }
                        samplingCallback.onRegionDarknessChanged(z);
                        regionSamplingHelper.mLastMedianLuma = f;
                    }
                }
            }
        };
        this.mSampledView = view;
        view.addOnAttachStateChangeListener(this);
        view.addOnLayoutChangeListener(this);
        this.mCallback = samplingCallback;
    }

    public final void dump(PrintWriter printWriter) {
        Object obj;
        printWriter.println("RegionSamplingHelper:");
        printWriter.println("" + "\tsampleView isAttached: " + this.mSampledView.isAttachedToWindow());
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append("\tsampleView isScValid: ");
        if (this.mSampledView.isAttachedToWindow()) {
            obj = Boolean.valueOf(this.mSampledView.getViewRootImpl().getSurfaceControl().isValid());
        } else {
            obj = "notAttached";
        }
        sb.append(obj);
        printWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append("\tmSamplingEnabled: ");
        StringBuilder m = RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(sb2, this.mSamplingEnabled, printWriter, "", "\tmSamplingListenerRegistered: "), this.mSamplingListenerRegistered, printWriter, "", "\tmSamplingRequestBounds: ");
        m.append(this.mSamplingRequestBounds);
        printWriter.println(m.toString());
        printWriter.println("" + "\tmRegisteredSamplingBounds: " + this.mRegisteredSamplingBounds);
        printWriter.println("" + "\tmLastMedianLuma: " + this.mLastMedianLuma);
        printWriter.println("" + "\tmCurrentMedianLuma: " + this.mCurrentMedianLuma);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        sb3.append("\tmWindowVisible: ");
        StringBuilder m2 = RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(sb3, this.mWindowVisible, printWriter, "", "\tmWindowHasBlurs: "), this.mWindowHasBlurs, printWriter, "", "\tmWaitingOnDraw: "), this.mWaitingOnDraw, printWriter, "", "\tmRegisteredStopLayer: ");
        m2.append(this.mRegisteredStopLayer);
        printWriter.println(m2.toString());
        printWriter.println("" + "\tmWrappedStopLayer: " + this.mWrappedStopLayer);
        printWriter.println("" + "\tmIsDestroyed: " + this.mIsDestroyed);
    }

    public final void start(Rect rect) {
        if (this.mCallback.isSamplingEnabled()) {
            if (rect != null) {
                this.mSamplingRequestBounds.set(rect);
            }
            this.mSamplingEnabled = true;
            this.mLastMedianLuma = -1.0f;
            this.mFirstSamplingAfterStart = true;
            updateSamplingListener();
        }
    }

    public final void unregisterSamplingListener() {
        if (this.mSamplingListenerRegistered) {
            this.mSamplingListenerRegistered = false;
            SurfaceControl surfaceControl = this.mWrappedStopLayer;
            this.mRegisteredStopLayer = null;
            this.mWrappedStopLayer = null;
            this.mRegisteredSamplingBounds.setEmpty();
            this.mBackgroundExecutor.execute(new KeyguardPatternView$$ExternalSyntheticLambda0(this, surfaceControl, 4));
        }
    }

    public final void updateSamplingListener() {
        boolean z;
        SurfaceControl surfaceControl;
        if (!this.mSamplingEnabled || this.mSamplingRequestBounds.isEmpty() || !this.mWindowVisible || this.mWindowHasBlurs || (!this.mSampledView.isAttachedToWindow() && !this.mFirstSamplingAfterStart)) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            ViewRootImpl viewRootImpl = this.mSampledView.getViewRootImpl();
            SurfaceControl surfaceControl2 = null;
            if (viewRootImpl != null) {
                surfaceControl = viewRootImpl.getSurfaceControl();
            } else {
                surfaceControl = null;
            }
            if (surfaceControl != null && surfaceControl.isValid()) {
                surfaceControl2 = surfaceControl;
            } else if (!this.mWaitingOnDraw) {
                this.mWaitingOnDraw = true;
                if (this.mHandler.hasCallbacks(this.mRemoveDrawRunnable)) {
                    this.mHandler.removeCallbacks(this.mRemoveDrawRunnable);
                } else {
                    this.mSampledView.getViewTreeObserver().addOnDrawListener(this.mUpdateOnDraw);
                }
            }
            if (!this.mSamplingRequestBounds.equals(this.mRegisteredSamplingBounds) || this.mRegisteredStopLayer != surfaceControl2) {
                unregisterSamplingListener();
                this.mSamplingListenerRegistered = true;
                SurfaceControl wrap = wrap(surfaceControl2);
                this.mBackgroundExecutor.execute(new QuickAccessWalletTile$$ExternalSyntheticLambda0(this, wrap, 2));
                this.mRegisteredSamplingBounds.set(this.mSamplingRequestBounds);
                this.mRegisteredStopLayer = surfaceControl2;
                this.mWrappedStopLayer = wrap;
            }
            this.mFirstSamplingAfterStart = false;
            return;
        }
        unregisterSamplingListener();
    }

    public final void updateSamplingRect() {
        Rect sampledRegion = this.mCallback.getSampledRegion();
        if (!this.mSamplingRequestBounds.equals(sampledRegion)) {
            this.mSamplingRequestBounds.set(sampledRegion);
            updateSamplingListener();
        }
    }

    public SurfaceControl wrap(SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            return null;
        }
        return new SurfaceControl(surfaceControl, "regionSampling");
    }

    public final void onViewDetachedFromWindow(View view) {
        stop();
        Executor executor = this.mBackgroundExecutor;
        C11143 r0 = this.mSamplingListener;
        Objects.requireNonNull(r0);
        executor.execute(new QSTileImpl$$ExternalSyntheticLambda1(r0, 2));
        this.mIsDestroyed = true;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateSamplingRect();
    }

    public final void onViewAttachedToWindow(View view) {
        updateSamplingListener();
    }
}
