package com.android.keyguard;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.text.PositionedGlyphs;
import android.text.Layout;
import android.text.TextPaint;
import android.util.SparseArray;
import com.android.keyguard.TextInterpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TextAnimator.kt */
public final class TextAnimator {
    public ValueAnimator animator;
    public final Function0<Unit> invalidateCallback;
    public TextInterpolator textInterpolator;
    public final SparseArray<Typeface> typefaceCache = new SparseArray<>();

    public final void setTextStyle(int i, float f, Integer num, boolean z, long j, TimeInterpolator timeInterpolator, long j2, Runnable runnable) {
        boolean z2;
        long j3;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        int i2 = i;
        float f2 = f;
        TimeInterpolator timeInterpolator2 = timeInterpolator;
        Runnable runnable2 = runnable;
        if (z) {
            this.animator.cancel();
            this.textInterpolator.rebase();
        }
        if (f2 >= 0.0f) {
            TextInterpolator textInterpolator2 = this.textInterpolator;
            Objects.requireNonNull(textInterpolator2);
            textInterpolator2.targetPaint.setTextSize(f2);
        }
        if (i2 >= 0) {
            TextInterpolator textInterpolator3 = this.textInterpolator;
            Objects.requireNonNull(textInterpolator3);
            TextPaint textPaint = textInterpolator3.targetPaint;
            SparseArray sparseArray = this.typefaceCache;
            TextAnimator$setTextStyle$1 textAnimator$setTextStyle$1 = new TextAnimator$setTextStyle$1(this, i2);
            Object obj = sparseArray.get(i2);
            if (obj == null) {
                obj = textAnimator$setTextStyle$1.invoke();
                sparseArray.put(i2, obj);
            }
            textPaint.setTypeface((Typeface) obj);
        }
        if (num != null) {
            TextInterpolator textInterpolator4 = this.textInterpolator;
            Objects.requireNonNull(textInterpolator4);
            textInterpolator4.targetPaint.setColor(num.intValue());
        }
        TextInterpolator textInterpolator5 = this.textInterpolator;
        Objects.requireNonNull(textInterpolator5);
        ArrayList shapeText = TextInterpolator.shapeText(textInterpolator5.layout, textInterpolator5.targetPaint);
        if (shapeText.size() == textInterpolator5.lines.size()) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            List<TextInterpolator.Line> list = textInterpolator5.lines;
            Iterator<T> it = list.iterator();
            Iterator it2 = shapeText.iterator();
            int i3 = 10;
            ArrayList arrayList = new ArrayList(Math.min(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10), CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(shapeText, 10)));
            while (it.hasNext() && it2.hasNext()) {
                T next = it.next();
                List list2 = (List) it2.next();
                TextInterpolator.Line line = (TextInterpolator.Line) next;
                Objects.requireNonNull(line);
                List<TextInterpolator.Run> list3 = line.runs;
                Iterator<T> it3 = list3.iterator();
                Iterator it4 = list2.iterator();
                ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list3, i3), CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list2, i3)));
                while (it3.hasNext() && it4.hasNext()) {
                    T next2 = it3.next();
                    PositionedGlyphs positionedGlyphs = (PositionedGlyphs) it4.next();
                    TextInterpolator.Run run = (TextInterpolator.Run) next2;
                    int glyphCount = positionedGlyphs.glyphCount();
                    Objects.requireNonNull(run);
                    if (glyphCount == run.glyphIds.length) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        for (TextInterpolator.FontRun fontRun : run.fontRuns) {
                            Objects.requireNonNull(fontRun);
                            Font font = positionedGlyphs.getFont(fontRun.start);
                            int i4 = fontRun.start;
                            int i5 = fontRun.end;
                            while (i4 < i5) {
                                int i6 = i4 + 1;
                                Iterator<T> it5 = it;
                                int glyphId = positionedGlyphs.getGlyphId(fontRun.start);
                                Iterator it6 = it2;
                                int[] iArr = run.glyphIds;
                                int i7 = i5;
                                int i8 = fontRun.start;
                                if (glyphId == iArr[i8]) {
                                    z5 = true;
                                } else {
                                    z5 = false;
                                }
                                if (z5) {
                                    if (font == positionedGlyphs.getFont(i4)) {
                                        z6 = true;
                                    } else {
                                        z6 = false;
                                    }
                                    if (z6) {
                                        i4 = i6;
                                        it = it5;
                                        it2 = it6;
                                        i5 = i7;
                                    } else {
                                        throw new IllegalArgumentException(("The new layout has different font run. " + font + " vs " + positionedGlyphs.getFont(i4) + " at " + i4).toString());
                                    }
                                } else {
                                    throw new IllegalArgumentException(Intrinsics.stringPlus("The new layout has different glyph ID at ", Integer.valueOf(i8)).toString());
                                }
                            }
                            Iterator<T> it7 = it;
                            Iterator it8 = it2;
                            Font font2 = fontRun.baseFont;
                            if (font.getTtcIndex() == font2.getTtcIndex() && font.getSourceIdentifier() == font2.getSourceIdentifier()) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (z4) {
                                fontRun.targetFont = font;
                                it = it7;
                                it2 = it8;
                            } else {
                                throw new IllegalArgumentException(("New font cannot be interpolated with existing font. " + font + ", " + fontRun.baseFont).toString());
                            }
                        }
                        Iterator<T> it9 = it;
                        Iterator it10 = it2;
                        int length = run.baseX.length;
                        for (int i9 = 0; i9 < length; i9++) {
                            run.targetX[i9] = positionedGlyphs.getGlyphX(i9);
                            run.targetY[i9] = positionedGlyphs.getGlyphY(i9);
                        }
                        arrayList2.add(Unit.INSTANCE);
                        it = it9;
                        it2 = it10;
                    } else {
                        throw new IllegalArgumentException("The new layout has different glyph count.".toString());
                    }
                }
                arrayList.add(arrayList2);
                it = it;
                it2 = it2;
                i3 = 10;
            }
            if (z) {
                this.animator.setStartDelay(j2);
                ValueAnimator valueAnimator = this.animator;
                if (j == -1) {
                    j3 = 300;
                } else {
                    j3 = j;
                }
                valueAnimator.setDuration(j3);
                if (timeInterpolator2 != null) {
                    this.animator.setInterpolator(timeInterpolator2);
                }
                if (runnable2 != null) {
                    this.animator.addListener(new TextAnimator$setTextStyle$listener$1(runnable2, this));
                }
                this.animator.start();
                return;
            }
            TextInterpolator textInterpolator6 = this.textInterpolator;
            Objects.requireNonNull(textInterpolator6);
            textInterpolator6.progress = 1.0f;
            this.textInterpolator.rebase();
            this.invalidateCallback.invoke();
            return;
        }
        throw new IllegalStateException("The new layout result has different line count.".toString());
    }

    public TextAnimator(Layout layout, Function0<Unit> function0) {
        this.invalidateCallback = function0;
        this.textInterpolator = new TextInterpolator(layout);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f});
        ofFloat.setDuration(300);
        ofFloat.addUpdateListener(new TextAnimator$animator$1$1(this));
        ofFloat.addListener(new TextAnimator$animator$1$2(this));
        this.animator = ofFloat;
    }
}
