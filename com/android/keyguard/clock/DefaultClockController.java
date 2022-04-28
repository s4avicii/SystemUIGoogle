package com.android.keyguard.clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.plugins.ClockPlugin;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public final class DefaultClockController implements ClockPlugin {
    public final SysuiColorExtractor mColorExtractor;
    public final LayoutInflater mLayoutInflater;
    public final ViewPreviewer mRenderer = new ViewPreviewer();
    public final Resources mResources;
    public TextView mTextDate;
    public TextView mTextTime;
    public View mView;

    public final String getName() {
        return "default";
    }

    public final View getView() {
        return null;
    }

    public final void onDestroyView() {
        this.mView = null;
        this.mTextTime = null;
        this.mTextDate = null;
    }

    public final void onTimeTick() {
    }

    public final void onTimeZoneChanged(TimeZone timeZone) {
    }

    public final void setColorPalette(boolean z, int[] iArr) {
    }

    public final void setDarkAmount(float f) {
    }

    public final void setStyle(Paint.Style style) {
    }

    public final boolean shouldShowStatusArea() {
        return true;
    }

    public final View getBigClockView() {
        if (this.mView == null) {
            View inflate = this.mLayoutInflater.inflate(C1777R.layout.default_clock_preview, (ViewGroup) null);
            this.mView = inflate;
            this.mTextTime = (TextView) inflate.findViewById(C1777R.C1779id.time);
            this.mTextDate = (TextView) this.mView.findViewById(C1777R.C1779id.date);
        }
        return this.mView;
    }

    public final Bitmap getThumbnail() {
        return BitmapFactory.decodeResource(this.mResources, C1777R.C1778drawable.default_thumbnail);
    }

    public final String getTitle() {
        return this.mResources.getString(C1777R.string.clock_title_default);
    }

    public final void setTextColor(int i) {
        this.mTextTime.setTextColor(i);
        this.mTextDate.setTextColor(i);
    }

    public DefaultClockController(Resources resources, LayoutInflater layoutInflater, SysuiColorExtractor sysuiColorExtractor) {
        this.mResources = resources;
        this.mLayoutInflater = layoutInflater;
        this.mColorExtractor = sysuiColorExtractor;
    }

    public final Bitmap getPreview(int i, int i2) {
        View bigClockView = getBigClockView();
        setTextColor(-1);
        ColorExtractor.GradientColors colors = this.mColorExtractor.getColors(2);
        colors.supportsDarkText();
        colors.getColorPalette();
        ViewPreviewer viewPreviewer = this.mRenderer;
        Objects.requireNonNull(viewPreviewer);
        if (bigClockView == null) {
            return null;
        }
        FutureTask futureTask = new FutureTask(new Callable<Bitmap>(i, i2, bigClockView) {
            public final /* synthetic */ int val$height;
            public final /* synthetic */ View val$view;
            public final /* synthetic */ int val$width;

            {
                this.val$width = r2;
                this.val$height = r3;
                this.val$view = r4;
            }

            public final Object call() throws Exception {
                Bitmap createBitmap = Bitmap.createBitmap(this.val$width, this.val$height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawColor(-16777216);
                ViewPreviewer viewPreviewer = ViewPreviewer.this;
                View view = this.val$view;
                Objects.requireNonNull(viewPreviewer);
                ViewPreviewer.dispatchVisibilityAggregated(view, true);
                this.val$view.measure(View.MeasureSpec.makeMeasureSpec(this.val$width, 1073741824), View.MeasureSpec.makeMeasureSpec(this.val$height, 1073741824));
                this.val$view.layout(0, 0, this.val$width, this.val$height);
                this.val$view.draw(canvas);
                return createBitmap;
            }
        });
        if (Looper.myLooper() == Looper.getMainLooper()) {
            futureTask.run();
        } else {
            viewPreviewer.mMainHandler.post(futureTask);
        }
        try {
            return (Bitmap) futureTask.get();
        } catch (Exception e) {
            Log.e("ViewPreviewer", "Error completing task", e);
            return null;
        }
    }

    public final int getPreferredY(int i) {
        return i / 2;
    }
}
