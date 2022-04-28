package com.android.systemui.screenrecord;

import android.util.Log;
import android.util.MathUtils;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenInternalAudioRecorder$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ScreenInternalAudioRecorder f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ScreenInternalAudioRecorder$$ExternalSyntheticLambda0(ScreenInternalAudioRecorder screenInternalAudioRecorder, int i) {
        this.f$0 = screenInternalAudioRecorder;
        this.f$1 = i;
    }

    public final void run() {
        short[] sArr;
        int i;
        int i2;
        int i3;
        ScreenInternalAudioRecorder screenInternalAudioRecorder = this.f$0;
        int i4 = this.f$1;
        Objects.requireNonNull(screenInternalAudioRecorder);
        byte[] bArr = new byte[i4];
        short[] sArr2 = null;
        if (screenInternalAudioRecorder.mMic) {
            int i5 = i4 / 2;
            sArr2 = new short[i5];
            sArr = new short[i5];
        } else {
            sArr = null;
        }
        int i6 = 0;
        int i7 = 0;
        short s = 0;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            if (screenInternalAudioRecorder.mMic) {
                int read = screenInternalAudioRecorder.mAudioRecord.read(sArr2, i6, sArr2.length - i6);
                int read2 = screenInternalAudioRecorder.mAudioRecordMic.read(sArr, i7, sArr.length - i7);
                if (read < 0 && read2 < 0) {
                    break;
                }
                if (read < 0) {
                    Arrays.fill(sArr2, s);
                    i6 = i7;
                    read = read2;
                }
                if (read2 < 0) {
                    Arrays.fill(sArr, s);
                    i7 = i6;
                    read2 = read;
                }
                i8 = read + i6;
                i9 = read2 + i7;
                int min = Math.min(i8, i9);
                i = min * 2;
                short s2 = s;
                while (true) {
                    i3 = -32768;
                    if (s2 >= min) {
                        break;
                    }
                    sArr[s2] = (short) MathUtils.constrain((int) (((float) sArr[s2]) * 1.4f), -32768, 32767);
                    s2++;
                }
                int i10 = 0;
                while (i10 < min) {
                    short constrain = (short) MathUtils.constrain(sArr2[i10] + sArr[i10], i3, 32767);
                    int i11 = i10 * 2;
                    bArr[i11] = (byte) (constrain & 255);
                    bArr[i11 + 1] = (byte) ((constrain >> 8) & 255);
                    i10++;
                    i3 = -32768;
                }
                for (int i12 = 0; i12 < i6 - min; i12++) {
                    sArr2[i12] = sArr2[min + i12];
                }
                for (int i13 = 0; i13 < i7 - min; i13++) {
                    sArr[i13] = sArr[min + i13];
                }
                i6 = i8 - min;
                i7 = i9 - min;
                i2 = 0;
            } else {
                i = screenInternalAudioRecorder.mAudioRecord.read(bArr, 0, i4);
                i2 = 0;
            }
            if (i < 0) {
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("read error ", i, ", shorts internal: ", i8, ", shorts mic: ");
                m.append(i9);
                Log.e("ScreenAudioRecorder", m.toString());
                break;
            }
            while (true) {
                if (i <= 0) {
                    break;
                }
                int dequeueInputBuffer = screenInternalAudioRecorder.mCodec.dequeueInputBuffer(500);
                if (dequeueInputBuffer < 0) {
                    screenInternalAudioRecorder.writeOutput();
                    break;
                }
                ByteBuffer inputBuffer = screenInternalAudioRecorder.mCodec.getInputBuffer(dequeueInputBuffer);
                inputBuffer.clear();
                int capacity = inputBuffer.capacity();
                if (i <= capacity) {
                    capacity = i;
                }
                i -= capacity;
                inputBuffer.put(bArr, i2, capacity);
                i2 += capacity;
                byte[] bArr2 = bArr;
                screenInternalAudioRecorder.mCodec.queueInputBuffer(dequeueInputBuffer, 0, capacity, screenInternalAudioRecorder.mPresentationTime, 0);
                long j = screenInternalAudioRecorder.mTotalBytes + ((long) (capacity + 0));
                screenInternalAudioRecorder.mTotalBytes = j;
                Objects.requireNonNull(screenInternalAudioRecorder.mConfig);
                screenInternalAudioRecorder.mPresentationTime = ((j / 2) * 1000000) / ((long) 44100);
                screenInternalAudioRecorder.writeOutput();
                sArr = sArr;
                bArr = bArr2;
            }
            s = 0;
            sArr = sArr;
            bArr = bArr;
        }
        screenInternalAudioRecorder.mCodec.queueInputBuffer(screenInternalAudioRecorder.mCodec.dequeueInputBuffer(500), 0, 0, screenInternalAudioRecorder.mPresentationTime, 4);
        screenInternalAudioRecorder.writeOutput();
    }
}
