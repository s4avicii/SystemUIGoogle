package com.android.systemui.colorextraction;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Handler;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.colorextraction.types.Tonal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public final class SysuiColorExtractor extends ColorExtractor implements Dumpable, ConfigurationController.ConfigurationListener {
    public final ColorExtractor.GradientColors mBackdropColors;
    public boolean mHasMediaArtwork;
    public final ColorExtractor.GradientColors mNeutralColorsLock;
    public final Tonal mTonal;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("SysuiColorExtractor:");
        printWriter.println("  Current wallpaper colors:");
        printWriter.println("    system: " + this.mSystemColors);
        printWriter.println("    lock: " + this.mLockColors);
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "  Gradients:", "    system: ");
        m.append(Arrays.toString((ColorExtractor.GradientColors[]) this.mGradientColors.get(1)));
        printWriter.println(m.toString());
        printWriter.println("    lock: " + Arrays.toString((ColorExtractor.GradientColors[]) this.mGradientColors.get(2)));
        printWriter.println("  Neutral colors: " + this.mNeutralColorsLock);
        printWriter.println("  Has media backdrop: " + this.mHasMediaArtwork);
    }

    public final ColorExtractor.GradientColors getColors(int i, int i2) {
        if (!this.mHasMediaArtwork || (i & 2) == 0) {
            return SysuiColorExtractor.super.getColors(i, i2);
        }
        return this.mBackdropColors;
    }

    public final void setHasMediaArtwork(boolean z) {
        if (this.mHasMediaArtwork != z) {
            this.mHasMediaArtwork = z;
            triggerColorsChanged(2);
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.android.systemui.Dumpable, android.app.WallpaperManager$OnColorsChangedListener, java.lang.Object, com.android.systemui.colorextraction.SysuiColorExtractor] */
    @VisibleForTesting
    public SysuiColorExtractor(Context context, ExtractionType extractionType, ConfigurationController configurationController, WallpaperManager wallpaperManager, DumpManager dumpManager, boolean z) {
        super(context, extractionType, z, wallpaperManager);
        Tonal tonal;
        if (extractionType instanceof Tonal) {
            tonal = (Tonal) extractionType;
        } else {
            tonal = new Tonal(context);
        }
        this.mTonal = tonal;
        this.mNeutralColorsLock = new ColorExtractor.GradientColors();
        configurationController.addCallback(this);
        Class<SysuiColorExtractor> cls = SysuiColorExtractor.class;
        dumpManager.registerDumpable("SysuiColorExtractor", this);
        ColorExtractor.GradientColors gradientColors = new ColorExtractor.GradientColors();
        this.mBackdropColors = gradientColors;
        gradientColors.setMainColor(-16777216);
        if (wallpaperManager.isWallpaperSupported()) {
            wallpaperManager.removeOnColorsChangedListener(this);
            wallpaperManager.addOnColorsChangedListener(this, (Handler) null, -1);
        }
    }

    public final void extractWallpaperColors() {
        ColorExtractor.GradientColors gradientColors;
        SysuiColorExtractor.super.extractWallpaperColors();
        Tonal tonal = this.mTonal;
        if (tonal != null && (gradientColors = this.mNeutralColorsLock) != null) {
            WallpaperColors wallpaperColors = this.mLockColors;
            if (wallpaperColors == null) {
                wallpaperColors = this.mSystemColors;
            }
            tonal.applyFallback(wallpaperColors, gradientColors);
        }
    }

    public final void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
        if (i2 == KeyguardUpdateMonitor.getCurrentUser()) {
            if ((i & 2) != 0) {
                this.mTonal.applyFallback(wallpaperColors, this.mNeutralColorsLock);
            }
            onColorsChanged(wallpaperColors, i);
        }
    }

    public final void onUiModeChanged() {
        extractWallpaperColors();
        triggerColorsChanged(3);
    }
}
