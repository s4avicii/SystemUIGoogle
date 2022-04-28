package com.android.systemui.screenrecord;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenMediaRecorder {
    public ScreenInternalAudioRecorder mAudio;
    public ScreenRecordingAudioSource mAudioSource;
    public Context mContext;
    public Surface mInputSurface;
    public MediaRecorder.OnInfoListener mListener;
    public MediaProjection mMediaProjection;
    public MediaRecorder mMediaRecorder;
    public File mTempAudioFile;
    public File mTempVideoFile;
    public int mUser;
    public VirtualDisplay mVirtualDisplay;

    public class SavedRecording {
        public Bitmap mThumbnailBitmap;
        public Uri mUri;

        public SavedRecording(Uri uri, File file, Size size) {
            this.mUri = uri;
            try {
                this.mThumbnailBitmap = ThumbnailUtils.createVideoThumbnail(file, size, (CancellationSignal) null);
            } catch (IOException e) {
                Log.e("ScreenMediaRecorder", "Error creating thumbnail", e);
            }
        }
    }

    public final SavedRecording save() throws IOException {
        String format = new SimpleDateFormat("'screen-'yyyyMMdd-HHmmss'.mp4'").format(new Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", format);
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("date_added", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri insert = contentResolver.insert(MediaStore.Video.Media.getContentUri("external_primary"), contentValues);
        Log.d("ScreenMediaRecorder", insert.toString());
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        if (screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL) {
            try {
                Log.d("ScreenMediaRecorder", "muxing recording");
                File createTempFile = File.createTempFile("temp", ".mp4", this.mContext.getCacheDir());
                new ScreenRecordingMuxer(createTempFile.getAbsolutePath(), this.mTempVideoFile.getAbsolutePath(), this.mTempAudioFile.getAbsolutePath()).mux();
                this.mTempVideoFile.delete();
                this.mTempVideoFile = createTempFile;
            } catch (IOException e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("muxing recording ");
                m.append(e.getMessage());
                Log.e("ScreenMediaRecorder", m.toString());
                e.printStackTrace();
            }
        }
        OutputStream openOutputStream = contentResolver.openOutputStream(insert, "w");
        Files.copy(this.mTempVideoFile.toPath(), openOutputStream);
        openOutputStream.close();
        File file = this.mTempAudioFile;
        if (file != null) {
            file.delete();
        }
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        SavedRecording savedRecording = new SavedRecording(insert, this.mTempVideoFile, new Size(displayMetrics.widthPixels, displayMetrics.heightPixels));
        this.mTempVideoFile.delete();
        return savedRecording;
    }

    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v16 */
    /* JADX WARNING: type inference failed for: r4v19 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void start() throws java.io.IOException, android.os.RemoteException, java.lang.RuntimeException {
        /*
            r26 = this;
            r0 = r26
            java.lang.String r1 = "ScreenMediaRecorder"
            java.lang.String r2 = "start recording"
            android.util.Log.d(r1, r2)
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r1 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.MIC_AND_INTERNAL
            java.lang.String r2 = "media_projection"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)
            android.media.projection.IMediaProjectionManager r2 = android.media.projection.IMediaProjectionManager.Stub.asInterface(r2)
            int r3 = r0.mUser
            android.content.Context r4 = r0.mContext
            java.lang.String r4 = r4.getPackageName()
            r5 = 0
            android.media.projection.IMediaProjection r2 = r2.createProjection(r3, r4, r5, r5)
            android.os.IBinder r2 = r2.asBinder()
            android.media.projection.MediaProjection r3 = new android.media.projection.MediaProjection
            android.content.Context r4 = r0.mContext
            android.media.projection.IMediaProjection r2 = android.media.projection.IMediaProjection.Stub.asInterface(r2)
            r3.<init>(r4, r2)
            r0.mMediaProjection = r3
            android.content.Context r2 = r0.mContext
            java.io.File r2 = r2.getCacheDir()
            r2.mkdirs()
            java.lang.String r3 = "temp"
            java.lang.String r4 = ".mp4"
            java.io.File r2 = java.io.File.createTempFile(r3, r4, r2)
            r0.mTempVideoFile = r2
            android.media.MediaRecorder r2 = new android.media.MediaRecorder
            r2.<init>()
            r0.mMediaRecorder = r2
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r4 = r0.mAudioSource
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r6 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.MIC
            if (r4 != r6) goto L_0x0058
            r2.setAudioSource(r5)
        L_0x0058:
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r4 = 2
            r2.setVideoSource(r4)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r2.setOutputFormat(r4)
            android.util.DisplayMetrics r2 = new android.util.DisplayMetrics
            r2.<init>()
            android.content.Context r4 = r0.mContext
            java.lang.String r5 = "window"
            java.lang.Object r4 = r4.getSystemService(r5)
            android.view.WindowManager r4 = (android.view.WindowManager) r4
            android.view.Display r5 = r4.getDefaultDisplay()
            r5.getRealMetrics(r2)
            android.view.Display r4 = r4.getDefaultDisplay()
            float r4 = r4.getRefreshRate()
            int r4 = (int) r4
            int r5 = r2.widthPixels
            int r7 = r2.heightPixels
            java.lang.String r8 = "video/avc"
            android.media.MediaCodec r9 = android.media.MediaCodec.createDecoderByType(r8)
            android.media.MediaCodecInfo r10 = r9.getCodecInfo()
            android.media.MediaCodecInfo$CodecCapabilities r8 = r10.getCapabilitiesForType(r8)
            android.media.MediaCodecInfo$VideoCapabilities r8 = r8.getVideoCapabilities()
            r9.release()
            android.util.Range r9 = r8.getSupportedWidths()
            java.lang.Comparable r9 = r9.getUpper()
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            android.util.Range r10 = r8.getSupportedHeights()
            java.lang.Comparable r10 = r10.getUpper()
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            int r11 = r8.getWidthAlignment()
            int r11 = r5 % r11
            if (r11 == 0) goto L_0x00ca
            int r11 = r8.getWidthAlignment()
            int r11 = r5 % r11
            int r11 = r5 - r11
            goto L_0x00cb
        L_0x00ca:
            r11 = r5
        L_0x00cb:
            int r12 = r8.getHeightAlignment()
            int r12 = r7 % r12
            if (r12 == 0) goto L_0x00dc
            int r12 = r8.getHeightAlignment()
            int r12 = r7 % r12
            int r12 = r7 - r12
            goto L_0x00dd
        L_0x00dc:
            r12 = r7
        L_0x00dd:
            java.lang.String r13 = "ScreenMediaRecorder"
            r14 = 3
            r15 = 1
            if (r9 < r11) goto L_0x0112
            if (r10 < r12) goto L_0x0112
            boolean r16 = r8.isSizeSupported(r11, r12)
            if (r16 == 0) goto L_0x0112
            android.util.Range r5 = r8.getSupportedFrameRatesFor(r11, r12)
            java.lang.Comparable r5 = r5.getUpper()
            java.lang.Double r5 = (java.lang.Double) r5
            int r5 = r5.intValue()
            if (r5 >= r4) goto L_0x00fc
            r4 = r5
        L_0x00fc:
            java.lang.String r5 = "Screen size supported at rate "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r5, r4, r13)
            int[] r5 = new int[r14]
            r7 = 0
            r5[r7] = r11
            r5[r15] = r12
            r7 = 2
            r5[r7] = r4
            r4 = 0
            r16 = r2
            r2 = r5
            r5 = r1
            goto L_0x0189
        L_0x0112:
            double r11 = (double) r9
            double r14 = (double) r5
            double r11 = r11 / r14
            double r9 = (double) r10
            r5 = r1
            r16 = r2
            double r1 = (double) r7
            double r9 = r9 / r1
            double r9 = java.lang.Math.min(r11, r9)
            double r14 = r14 * r9
            int r7 = (int) r14
            double r1 = r1 * r9
            int r1 = (int) r1
            int r2 = r8.getWidthAlignment()
            int r2 = r7 % r2
            if (r2 == 0) goto L_0x0132
            int r2 = r8.getWidthAlignment()
            int r2 = r7 % r2
            int r7 = r7 - r2
        L_0x0132:
            int r2 = r8.getHeightAlignment()
            int r2 = r1 % r2
            if (r2 == 0) goto L_0x0141
            int r2 = r8.getHeightAlignment()
            int r2 = r1 % r2
            int r1 = r1 - r2
        L_0x0141:
            android.util.Range r2 = r8.getSupportedFrameRatesFor(r7, r1)
            java.lang.Comparable r2 = r2.getUpper()
            java.lang.Double r2 = (java.lang.Double) r2
            int r2 = r2.intValue()
            if (r2 >= r4) goto L_0x0152
            r4 = r2
        L_0x0152:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r8 = "Resized by "
            r2.append(r8)
            r2.append(r9)
            java.lang.String r8 = ": "
            r2.append(r8)
            r2.append(r7)
            java.lang.String r8 = ", "
            r2.append(r8)
            r2.append(r1)
            r2.append(r8)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r13, r2)
            r2 = 3
            int[] r2 = new int[r2]
            r8 = 0
            r2[r8] = r7
            r15 = 1
            r2[r15] = r1
            r7 = 2
            r2[r7] = r4
            r4 = r8
        L_0x0189:
            r1 = r2[r4]
            r8 = r2[r15]
            r2 = r2[r7]
            int r9 = r1 * r8
            int r9 = r9 * r2
            int r9 = r9 / 30
            int r9 = r9 * 6
            android.media.MediaRecorder r10 = r0.mMediaRecorder
            r10.setVideoEncoder(r7)
            android.media.MediaRecorder r7 = r0.mMediaRecorder
            r10 = 8
            r11 = 256(0x100, float:3.59E-43)
            r7.setVideoEncodingProfileLevel(r10, r11)
            android.media.MediaRecorder r7 = r0.mMediaRecorder
            r7.setVideoSize(r1, r8)
            android.media.MediaRecorder r7 = r0.mMediaRecorder
            r7.setVideoFrameRate(r2)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r2.setVideoEncodingBitRate(r9)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r7 = 3600000(0x36ee80, float:5.044674E-39)
            r2.setMaxDuration(r7)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r9 = 5000000000(0x12a05f200, double:2.470328229E-314)
            r2.setMaxFileSize(r9)
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r2 = r0.mAudioSource
            if (r2 != r6) goto L_0x01e5
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r6 = 4
            r2.setAudioEncoder(r6)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r6 = 1
            r2.setAudioChannels(r6)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r6 = 196000(0x2fda0, float:2.74654E-40)
            r2.setAudioEncodingBitRate(r6)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r6 = 44100(0xac44, float:6.1797E-41)
            r2.setAudioSamplingRate(r6)
        L_0x01e5:
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            java.io.File r6 = r0.mTempVideoFile
            r2.setOutputFile(r6)
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            r2.prepare()
            android.media.MediaRecorder r2 = r0.mMediaRecorder
            android.view.Surface r2 = r2.getSurface()
            r0.mInputSurface = r2
            android.media.projection.MediaProjection r6 = r0.mMediaProjection
            r7 = r16
            int r7 = r7.densityDpi
            r22 = 16
            r24 = 0
            r25 = 0
            java.lang.String r18 = "Recording Display"
            r17 = r6
            r19 = r1
            r20 = r8
            r21 = r7
            r23 = r2
            android.hardware.display.VirtualDisplay r1 = r17.createVirtualDisplay(r18, r19, r20, r21, r22, r23, r24, r25)
            r0.mVirtualDisplay = r1
            android.media.MediaRecorder r1 = r0.mMediaRecorder
            android.media.MediaRecorder$OnInfoListener r2 = r0.mListener
            r1.setOnInfoListener(r2)
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r1 = r0.mAudioSource
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r2 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.INTERNAL
            if (r1 == r2) goto L_0x0226
            if (r1 != r5) goto L_0x0246
        L_0x0226:
            android.content.Context r1 = r0.mContext
            java.io.File r1 = r1.getCacheDir()
            java.lang.String r6 = ".aac"
            java.io.File r1 = java.io.File.createTempFile(r3, r6, r1)
            r0.mTempAudioFile = r1
            com.android.systemui.screenrecord.ScreenInternalAudioRecorder r3 = new com.android.systemui.screenrecord.ScreenInternalAudioRecorder
            java.lang.String r1 = r1.getAbsolutePath()
            android.media.projection.MediaProjection r6 = r0.mMediaProjection
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r7 = r0.mAudioSource
            if (r7 != r5) goto L_0x0241
            r4 = 1
        L_0x0241:
            r3.<init>(r1, r6, r4)
            r0.mAudio = r3
        L_0x0246:
            android.media.MediaRecorder r1 = r0.mMediaRecorder
            r1.start()
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r1 = r0.mAudioSource
            if (r1 == r2) goto L_0x0251
            if (r1 != r5) goto L_0x02b0
        L_0x0251:
            com.android.systemui.screenrecord.ScreenInternalAudioRecorder r1 = r0.mAudio
            java.util.Objects.requireNonNull(r1)
            monitor-enter(r1)
            boolean r0 = r1.mStarted     // Catch:{ all -> 0x02b9 }
            if (r0 == 0) goto L_0x026f
            java.lang.Thread r0 = r1.mThread     // Catch:{ all -> 0x02b9 }
            if (r0 != 0) goto L_0x0267
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x02b9 }
            java.lang.String r2 = "Recording stopped and can't restart (single use)"
            r0.<init>(r2)     // Catch:{ all -> 0x02b9 }
            throw r0     // Catch:{ all -> 0x02b9 }
        L_0x0267:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x02b9 }
            java.lang.String r2 = "Recording already started"
            r0.<init>(r2)     // Catch:{ all -> 0x02b9 }
            throw r0     // Catch:{ all -> 0x02b9 }
        L_0x026f:
            r0 = 1
            r1.mStarted = r0     // Catch:{ all -> 0x02b9 }
            android.media.AudioRecord r0 = r1.mAudioRecord     // Catch:{ all -> 0x02b9 }
            r0.startRecording()     // Catch:{ all -> 0x02b9 }
            boolean r0 = r1.mMic     // Catch:{ all -> 0x02b9 }
            if (r0 == 0) goto L_0x0280
            android.media.AudioRecord r0 = r1.mAudioRecordMic     // Catch:{ all -> 0x02b9 }
            r0.startRecording()     // Catch:{ all -> 0x02b9 }
        L_0x0280:
            java.lang.String r0 = "ScreenAudioRecorder"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b9 }
            r2.<init>()     // Catch:{ all -> 0x02b9 }
            java.lang.String r3 = "channel count "
            r2.append(r3)     // Catch:{ all -> 0x02b9 }
            android.media.AudioRecord r3 = r1.mAudioRecord     // Catch:{ all -> 0x02b9 }
            int r3 = r3.getChannelCount()     // Catch:{ all -> 0x02b9 }
            r2.append(r3)     // Catch:{ all -> 0x02b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x02b9 }
            android.util.Log.d(r0, r2)     // Catch:{ all -> 0x02b9 }
            android.media.MediaCodec r0 = r1.mCodec     // Catch:{ all -> 0x02b9 }
            r0.start()     // Catch:{ all -> 0x02b9 }
            android.media.AudioRecord r0 = r1.mAudioRecord     // Catch:{ all -> 0x02b9 }
            int r0 = r0.getRecordingState()     // Catch:{ all -> 0x02b9 }
            r2 = 3
            if (r0 != r2) goto L_0x02b1
            java.lang.Thread r0 = r1.mThread     // Catch:{ all -> 0x02b9 }
            r0.start()     // Catch:{ all -> 0x02b9 }
            monitor-exit(r1)
        L_0x02b0:
            return
        L_0x02b1:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x02b9 }
            java.lang.String r2 = "Audio recording failed to start"
            r0.<init>(r2)     // Catch:{ all -> 0x02b9 }
            throw r0     // Catch:{ all -> 0x02b9 }
        L_0x02b9:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenrecord.ScreenMediaRecorder.start():void");
    }

    public ScreenMediaRecorder(Context context, int i, ScreenRecordingAudioSource screenRecordingAudioSource, MediaRecorder.OnInfoListener onInfoListener) {
        this.mContext = context;
        this.mUser = i;
        this.mListener = onInfoListener;
        this.mAudioSource = screenRecordingAudioSource;
    }
}
