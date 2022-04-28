package com.android.keyguard;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.fonts.Font;
import android.graphics.text.PositionedGlyphs;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextShaper;
import android.util.MathUtils;
import androidx.exifinterface.media.C0155xe8491b12;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TextInterpolator.kt */
public final class TextInterpolator {
    public final TextPaint basePaint;
    public final FontInterpolator fontInterpolator = new FontInterpolator();
    public Layout layout;
    public List<Line> lines = EmptyList.INSTANCE;
    public float progress;
    public final TextPaint targetPaint;
    public final TextPaint tmpDrawPaint = new TextPaint();
    public float[] tmpPositionArray = new float[20];

    /* compiled from: TextInterpolator.kt */
    public static final class FontRun {
        public Font baseFont;
        public final int end;
        public final int start;
        public Font targetFont;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FontRun)) {
                return false;
            }
            FontRun fontRun = (FontRun) obj;
            return this.start == fontRun.start && this.end == fontRun.end && Intrinsics.areEqual(this.baseFont, fontRun.baseFont) && Intrinsics.areEqual(this.targetFont, fontRun.targetFont);
        }

        public final int hashCode() {
            int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.end, Integer.hashCode(this.start) * 31, 31);
            return this.targetFont.hashCode() + ((this.baseFont.hashCode() + m) * 31);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("FontRun(start=");
            m.append(this.start);
            m.append(", end=");
            m.append(this.end);
            m.append(", baseFont=");
            m.append(this.baseFont);
            m.append(", targetFont=");
            m.append(this.targetFont);
            m.append(')');
            return m.toString();
        }

        public FontRun(int i, int i2, Font font, Font font2) {
            this.start = i;
            this.end = i2;
            this.baseFont = font;
            this.targetFont = font2;
        }
    }

    public final void shapeText(Layout layout2) {
        ArrayList arrayList;
        ArrayList arrayList2;
        Iterator it;
        Iterator it2;
        Iterator it3;
        Iterator it4;
        float[] fArr;
        float[] fArr2;
        PositionedGlyphs positionedGlyphs;
        Layout layout3 = layout2;
        ArrayList shapeText = shapeText(layout3, this.basePaint);
        ArrayList shapeText2 = shapeText(layout3, this.targetPaint);
        if (shapeText.size() == shapeText2.size()) {
            Iterator it5 = shapeText.iterator();
            Iterator it6 = shapeText2.iterator();
            int i = 10;
            ArrayList arrayList3 = new ArrayList(Math.min(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(shapeText, 10), CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(shapeText2, 10)));
            int i2 = 0;
            TextInterpolator textInterpolator = this;
            while (it5.hasNext() && it6.hasNext()) {
                Object next = it5.next();
                List list = (List) it6.next();
                List list2 = (List) next;
                Iterator it7 = list2.iterator();
                Iterator it8 = list.iterator();
                ArrayList arrayList4 = new ArrayList(Math.min(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list2, i), CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, i)));
                while (it7.hasNext() && it8.hasNext()) {
                    Object next2 = it7.next();
                    PositionedGlyphs positionedGlyphs2 = (PositionedGlyphs) it8.next();
                    PositionedGlyphs positionedGlyphs3 = (PositionedGlyphs) next2;
                    if (positionedGlyphs3.glyphCount() == positionedGlyphs2.glyphCount()) {
                        int glyphCount = positionedGlyphs3.glyphCount();
                        int[] iArr = new int[glyphCount];
                        int i3 = 0;
                        while (i3 < glyphCount) {
                            int glyphId = positionedGlyphs3.getGlyphId(i3);
                            if (glyphId == positionedGlyphs2.getGlyphId(i3)) {
                                iArr[i3] = glyphId;
                                i3++;
                            } else {
                                StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("Inconsistent glyph ID at ", i3, " in line ");
                                m.append(textInterpolator.lines.size());
                                throw new IllegalArgumentException(m.toString().toString());
                            }
                        }
                        float[] fArr3 = new float[glyphCount];
                        for (int i4 = 0; i4 < glyphCount; i4++) {
                            fArr3[i4] = positionedGlyphs3.getGlyphX(i4);
                        }
                        float[] fArr4 = new float[glyphCount];
                        for (int i5 = 0; i5 < glyphCount; i5++) {
                            fArr4[i5] = positionedGlyphs3.getGlyphY(i5);
                        }
                        float[] fArr5 = new float[glyphCount];
                        for (int i6 = 0; i6 < glyphCount; i6++) {
                            fArr5[i6] = positionedGlyphs2.getGlyphX(i6);
                        }
                        float[] fArr6 = new float[glyphCount];
                        for (int i7 = 0; i7 < glyphCount; i7++) {
                            fArr6[i7] = positionedGlyphs2.getGlyphY(i7);
                        }
                        ArrayList arrayList5 = new ArrayList();
                        if (glyphCount != 0) {
                            int i8 = i2;
                            it4 = it5;
                            Font font = positionedGlyphs3.getFont(0);
                            Font font2 = positionedGlyphs2.getFont(0);
                            it3 = it6;
                            it2 = it7;
                            it = it8;
                            fArr2 = fArr6;
                            if (font.getTtcIndex() == font2.getTtcIndex() && font.getSourceIdentifier() == font2.getSourceIdentifier()) {
                                arrayList2 = arrayList3;
                                arrayList = arrayList4;
                                int i9 = 1;
                                int i10 = 0;
                                Font font3 = font;
                                int i11 = i8;
                                while (i9 < glyphCount) {
                                    int i12 = i9 + 1;
                                    float[] fArr7 = fArr5;
                                    Font font4 = positionedGlyphs3.getFont(i9);
                                    PositionedGlyphs positionedGlyphs4 = positionedGlyphs3;
                                    Font font5 = positionedGlyphs2.getFont(i9);
                                    if (font3 != font4) {
                                        if (font2 != font5) {
                                            positionedGlyphs = positionedGlyphs2;
                                            arrayList5.add(new FontRun(i10, i9, font3, font2));
                                            int max = Math.max(i11, i9 - i10);
                                            if (font4.getTtcIndex() == font5.getTtcIndex() && font4.getSourceIdentifier() == font5.getSourceIdentifier()) {
                                                i11 = max;
                                                font3 = font4;
                                                font2 = font5;
                                                i10 = i9;
                                            } else {
                                                throw new IllegalArgumentException(("Cannot interpolate font at " + i9 + " (" + font4 + " vs " + font5 + ')').toString());
                                            }
                                        } else {
                                            throw new IllegalArgumentException(C0155xe8491b12.m16m("Base font has changed at ", i9, " but target font has not changed.").toString());
                                        }
                                    } else {
                                        positionedGlyphs = positionedGlyphs2;
                                        if (!(font2 == font5)) {
                                            throw new IllegalArgumentException(C0155xe8491b12.m16m("Base font has not changed at ", i9, " but target font has changed.").toString());
                                        }
                                    }
                                    i9 = i12;
                                    fArr5 = fArr7;
                                    positionedGlyphs3 = positionedGlyphs4;
                                    positionedGlyphs2 = positionedGlyphs;
                                }
                                fArr = fArr5;
                                arrayList5.add(new FontRun(i10, glyphCount, font3, font2));
                                i2 = Math.max(i11, glyphCount - i10);
                            } else {
                                throw new IllegalArgumentException(("Cannot interpolate font at " + 0 + " (" + font + " vs " + font2 + ')').toString());
                            }
                        } else {
                            int i13 = i2;
                            fArr = fArr5;
                            it4 = it5;
                            it3 = it6;
                            arrayList2 = arrayList3;
                            it2 = it7;
                            it = it8;
                            arrayList = arrayList4;
                            fArr2 = fArr6;
                        }
                        ArrayList arrayList6 = arrayList;
                        arrayList6.add(new Run(iArr, fArr3, fArr4, fArr, fArr2, arrayList5));
                        textInterpolator = this;
                        arrayList4 = arrayList6;
                        it5 = it4;
                        it6 = it3;
                        it7 = it2;
                        it8 = it;
                        arrayList3 = arrayList2;
                        TextInterpolator textInterpolator2 = textInterpolator;
                    } else {
                        throw new IllegalArgumentException(Intrinsics.stringPlus("Inconsistent glyph count at line ", Integer.valueOf(textInterpolator.lines.size())).toString());
                    }
                }
                ArrayList arrayList7 = arrayList3;
                arrayList7.add(new Line(arrayList4));
                i = 10;
                i2 = i2;
                arrayList3 = arrayList7;
                it5 = it5;
                it6 = it6;
            }
            textInterpolator.lines = arrayList3;
            int i14 = i2 * 2;
            if (textInterpolator.tmpPositionArray.length < i14) {
                textInterpolator.tmpPositionArray = new float[i14];
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The new layout result has different line count.".toString());
    }

    /* compiled from: TextInterpolator.kt */
    public static final class Line {
        public final List<Run> runs;

        public Line(ArrayList arrayList) {
            this.runs = arrayList;
        }
    }

    /* compiled from: TextInterpolator.kt */
    public static final class Run {
        public final float[] baseX;
        public final float[] baseY;
        public final List<FontRun> fontRuns;
        public final int[] glyphIds;
        public final float[] targetX;
        public final float[] targetY;

        public Run(int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, ArrayList arrayList) {
            this.glyphIds = iArr;
            this.baseX = fArr;
            this.baseY = fArr2;
            this.targetX = fArr3;
            this.targetY = fArr4;
            this.fontRuns = arrayList;
        }
    }

    public final void drawFontRun(Canvas canvas, Run run, FontRun fontRun, TextPaint textPaint) {
        Run run2 = run;
        FontRun fontRun2 = fontRun;
        Objects.requireNonNull(fontRun);
        int i = fontRun2.start;
        int i2 = fontRun2.end;
        int i3 = 0;
        while (i < i2) {
            int i4 = i3 + 1;
            this.tmpPositionArray[i3] = MathUtils.lerp(run2.baseX[i], run2.targetX[i], this.progress);
            this.tmpPositionArray[i4] = MathUtils.lerp(run2.baseY[i], run2.targetY[i], this.progress);
            i++;
            i3 = i4 + 1;
        }
        int[] iArr = run2.glyphIds;
        int i5 = fontRun2.start;
        canvas.drawGlyphs(iArr, i5, this.tmpPositionArray, 0, fontRun2.end - i5, this.fontInterpolator.lerp(fontRun2.baseFont, fontRun2.targetFont, this.progress), textPaint);
    }

    public final void rebase() {
        boolean z;
        float f = this.progress;
        boolean z2 = true;
        if (f == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            if (f != 1.0f) {
                z2 = false;
            }
            if (z2) {
                this.basePaint.set(this.targetPaint);
            } else {
                lerp(this.basePaint, this.targetPaint, f, this.tmpDrawPaint);
                this.basePaint.set(this.tmpDrawPaint);
            }
            for (Line line : this.lines) {
                Objects.requireNonNull(line);
                for (Run run : line.runs) {
                    Objects.requireNonNull(run);
                    int length = run.baseX.length;
                    for (int i = 0; i < length; i++) {
                        float[] fArr = run.baseX;
                        fArr[i] = MathUtils.lerp(fArr[i], run.targetX[i], this.progress);
                        float[] fArr2 = run.baseY;
                        fArr2[i] = MathUtils.lerp(fArr2[i], run.targetY[i], this.progress);
                    }
                    for (FontRun fontRun : run.fontRuns) {
                        FontInterpolator fontInterpolator2 = this.fontInterpolator;
                        Objects.requireNonNull(fontRun);
                        fontRun.baseFont = fontInterpolator2.lerp(fontRun.baseFont, fontRun.targetFont, this.progress);
                    }
                }
            }
            this.progress = 0.0f;
        }
    }

    public TextInterpolator(Layout layout2) {
        this.basePaint = new TextPaint(layout2.getPaint());
        this.targetPaint = new TextPaint(layout2.getPaint());
        this.layout = layout2;
        shapeText(layout2);
    }

    public static void lerp(TextPaint textPaint, TextPaint textPaint2, float f, TextPaint textPaint3) {
        textPaint3.set(textPaint);
        textPaint3.setTextSize(MathUtils.lerp(textPaint.getTextSize(), textPaint2.getTextSize(), f));
        textPaint3.setColor(ColorUtils.blendARGB(textPaint.getColor(), textPaint2.getColor(), f));
    }

    public static ArrayList shapeText(Layout layout2, TextPaint textPaint) {
        ArrayList arrayList = new ArrayList();
        int lineCount = layout2.getLineCount();
        int i = 0;
        while (i < lineCount) {
            int i2 = i + 1;
            int lineStart = layout2.getLineStart(i);
            int lineEnd = layout2.getLineEnd(i) - lineStart;
            ArrayList arrayList2 = new ArrayList();
            TextShaper.shapeText(layout2.getText(), lineStart, lineEnd, layout2.getTextDirectionHeuristic(), textPaint, new TextInterpolator$shapeText$3(arrayList2));
            arrayList.add(arrayList2);
            i = i2;
        }
        return arrayList;
    }
}
