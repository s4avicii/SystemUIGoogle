package com.android.systemui.screenrecord;

import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.util.Log;
import android.view.Surface;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public final class ScreenInternalAudioRecorder {
    public AudioRecord mAudioRecord;
    public AudioRecord mAudioRecordMic;
    public MediaCodec mCodec;
    public Config mConfig = new Config();
    public MediaProjection mMediaProjection;
    public boolean mMic;
    public MediaMuxer mMuxer;
    public long mPresentationTime;
    public boolean mStarted;
    public Thread mThread;
    public long mTotalBytes;
    public int mTrackId = -1;

    public static class Config {
        public final String toString() {
            return "channelMask=4\n   encoding=2\n sampleRate=44100\n bufferSize=131072\n privileged=true\n legacy app looback=false";
        }
    }

    public final void writeOutput() {
        while (true) {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int dequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(bufferInfo, 500);
            if (dequeueOutputBuffer == -2) {
                this.mTrackId = this.mMuxer.addTrack(this.mCodec.getOutputFormat());
                this.mMuxer.start();
            } else if (dequeueOutputBuffer != -1 && this.mTrackId >= 0) {
                ByteBuffer outputBuffer = this.mCodec.getOutputBuffer(dequeueOutputBuffer);
                if ((bufferInfo.flags & 2) == 0 || bufferInfo.size == 0) {
                    this.mMuxer.writeSampleData(this.mTrackId, outputBuffer, bufferInfo);
                }
                this.mCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
            } else {
                return;
            }
        }
    }

    public ScreenInternalAudioRecorder(String str, MediaProjection mediaProjection, boolean z) throws IOException {
        this.mMic = z;
        this.mMuxer = new MediaMuxer(str, 0);
        this.mMediaProjection = mediaProjection;
        DialogFragment$$ExternalSyntheticOutline0.m17m("creating audio file ", str, "ScreenAudioRecorder");
        Objects.requireNonNull(this.mConfig);
        Objects.requireNonNull(this.mConfig);
        Objects.requireNonNull(this.mConfig);
        int minBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2) * 2;
        Log.d("ScreenAudioRecorder", "audio buffer size: " + minBufferSize);
        AudioFormat.Builder builder = new AudioFormat.Builder();
        Objects.requireNonNull(this.mConfig);
        AudioFormat.Builder encoding = builder.setEncoding(2);
        Objects.requireNonNull(this.mConfig);
        AudioFormat.Builder sampleRate = encoding.setSampleRate(44100);
        Objects.requireNonNull(this.mConfig);
        AudioFormat build = sampleRate.setChannelMask(4).build();
        this.mAudioRecord = new AudioRecord.Builder().setAudioFormat(build).setAudioPlaybackCaptureConfig(new AudioPlaybackCaptureConfiguration.Builder(this.mMediaProjection).addMatchingUsage(1).addMatchingUsage(0).addMatchingUsage(14).build()).build();
        if (this.mMic) {
            Objects.requireNonNull(this.mConfig);
            Objects.requireNonNull(this.mConfig);
            this.mAudioRecordMic = new AudioRecord(7, 44100, 16, 2, minBufferSize);
        }
        this.mCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        Objects.requireNonNull(this.mConfig);
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", 44100, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        Objects.requireNonNull(this.mConfig);
        createAudioFormat.setInteger("bitrate", 196000);
        Objects.requireNonNull(this.mConfig);
        createAudioFormat.setInteger("pcm-encoding", 2);
        this.mCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mThread = new Thread(new ScreenInternalAudioRecorder$$ExternalSyntheticLambda0(this, minBufferSize));
    }
}
