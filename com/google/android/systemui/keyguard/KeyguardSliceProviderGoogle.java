package com.google.android.systemui.keyguard;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.builders.ListBuilder;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.util.Assert;
import com.google.android.systemui.smartspace.SmartSpaceCard;
import com.google.android.systemui.smartspace.SmartSpaceController;
import com.google.android.systemui.smartspace.SmartSpaceData;
import com.google.android.systemui.smartspace.SmartSpaceUpdateListener;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class KeyguardSliceProviderGoogle extends KeyguardSliceProvider implements SmartSpaceUpdateListener {
    public static final boolean DEBUG = Log.isLoggable("KeyguardSliceProvider", 3);
    public final Uri mCalendarUri = Uri.parse("content://com.android.systemui.keyguard/smartSpace/calendar");
    public boolean mHideSensitiveContent;
    public boolean mHideWorkContent = true;
    public SmartSpaceController mSmartSpaceController;
    public SmartSpaceData mSmartSpaceData;
    public final Uri mWeatherUri = Uri.parse("content://com.android.systemui.keyguard/smartSpace/weather");

    public static class AddShadowTask extends AsyncTask<Bitmap, Void, Bitmap> {
        public final float mBlurRadius;
        public final WeakReference<KeyguardSliceProviderGoogle> mProviderReference;
        public final SmartSpaceCard mWeatherCard;

        public final Object doInBackground(Object[] objArr) {
            Bitmap bitmap = ((Bitmap[]) objArr)[0];
            BlurMaskFilter blurMaskFilter = new BlurMaskFilter(this.mBlurRadius, BlurMaskFilter.Blur.NORMAL);
            Paint paint = new Paint();
            paint.setMaskFilter(blurMaskFilter);
            int[] iArr = new int[2];
            Bitmap extractAlpha = bitmap.extractAlpha(paint, iArr);
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint2 = new Paint();
            paint2.setAlpha(70);
            canvas.drawBitmap(extractAlpha, (float) iArr[0], (this.mBlurRadius / 2.0f) + ((float) iArr[1]), paint2);
            extractAlpha.recycle();
            paint2.setAlpha(255);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint2);
            return createBitmap;
        }

        public final void onPostExecute(Object obj) {
            KeyguardSliceProviderGoogle keyguardSliceProviderGoogle;
            Bitmap bitmap = (Bitmap) obj;
            synchronized (this) {
                SmartSpaceCard smartSpaceCard = this.mWeatherCard;
                Objects.requireNonNull(smartSpaceCard);
                smartSpaceCard.mIcon = bitmap;
                keyguardSliceProviderGoogle = this.mProviderReference.get();
            }
            if (keyguardSliceProviderGoogle != null) {
                boolean z = KeyguardSliceProviderGoogle.DEBUG;
                keyguardSliceProviderGoogle.notifyChange();
            }
        }

        public AddShadowTask(KeyguardSliceProviderGoogle keyguardSliceProviderGoogle, SmartSpaceCard smartSpaceCard) {
            this.mProviderReference = new WeakReference<>(keyguardSliceProviderGoogle);
            this.mWeatherCard = smartSpaceCard;
            this.mBlurRadius = keyguardSliceProviderGoogle.getContext().getResources().getDimension(C1777R.dimen.smartspace_icon_shadow);
        }
    }

    public final void onSensitiveModeChanged(boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        synchronized (this) {
            z3 = true;
            if (this.mHideSensitiveContent != z) {
                this.mHideSensitiveContent = z;
                if (DEBUG) {
                    Log.d("KeyguardSliceProvider", "Public mode changed, hide data: " + z);
                }
                z4 = true;
            } else {
                z4 = false;
            }
            if (this.mHideWorkContent != z2) {
                this.mHideWorkContent = z2;
                if (DEBUG) {
                    Log.d("KeyguardSliceProvider", "Public work mode changed, hide data: " + z2);
                }
            } else {
                z3 = z4;
            }
        }
        if (z3) {
            notifyChange();
        }
    }

    public final void onSmartSpaceUpdated(SmartSpaceData smartSpaceData) {
        synchronized (this) {
            this.mSmartSpaceData = smartSpaceData;
        }
        Objects.requireNonNull(smartSpaceData);
        SmartSpaceCard smartSpaceCard = smartSpaceData.mWeatherCard;
        if (smartSpaceCard == null || smartSpaceCard.mIcon == null || smartSpaceCard.mIconProcessed) {
            notifyChange();
            return;
        }
        smartSpaceCard.mIconProcessed = true;
        new AddShadowTask(this, smartSpaceCard).execute(new Bitmap[]{smartSpaceCard.mIcon});
    }

    public final void addWeather(ListBuilder listBuilder) {
        SmartSpaceData smartSpaceData = this.mSmartSpaceData;
        Objects.requireNonNull(smartSpaceData);
        SmartSpaceCard smartSpaceCard = smartSpaceData.mWeatherCard;
        if (smartSpaceCard != null && !smartSpaceCard.isExpired()) {
            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mWeatherUri);
            rowBuilder.mTitle = smartSpaceCard.substitute(true);
            rowBuilder.mTitleLoading = false;
            Bitmap bitmap = smartSpaceCard.mIcon;
            if (bitmap != null) {
                IconCompat createWithBitmap = IconCompat.createWithBitmap(bitmap);
                createWithBitmap.mTintMode = PorterDuff.Mode.DST;
                rowBuilder.addEndItem(createWithBitmap, 1);
            }
            listBuilder.addRow(rowBuilder);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00eb, code lost:
        r8 = ((androidx.slice.builders.impl.TemplateBuilderImpl) r0.mImpl).build();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00f5, code lost:
        if (DEBUG == false) goto L_0x010d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00f7, code lost:
        android.util.Log.d("KeyguardSliceProvider", "Binding slice: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x010d, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0110, code lost:
        return r8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0055 A[Catch:{ all -> 0x0077 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00c2 A[Catch:{ all -> 0x0077 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.slice.Slice onBindSlice() {
        /*
            r8 = this;
            java.lang.String r0 = "KeyguardSliceProviderGoogle#onBindSlice"
            android.os.Trace.beginSection(r0)
            androidx.slice.builders.ListBuilder r0 = new androidx.slice.builders.ListBuilder
            android.content.Context r1 = r8.getContext()
            android.net.Uri r2 = r8.mSliceUri
            r0.<init>(r1, r2)
            monitor-enter(r8)
            com.google.android.systemui.smartspace.SmartSpaceData r1 = r8.mSmartSpaceData     // Catch:{ all -> 0x0077 }
            java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x0077 }
            com.google.android.systemui.smartspace.SmartSpaceCard r1 = r1.mCurrentCard     // Catch:{ all -> 0x0077 }
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0052
            boolean r4 = r1.isExpired()     // Catch:{ all -> 0x0077 }
            if (r4 != 0) goto L_0x0052
            java.lang.String r4 = r1.substitute(r2)     // Catch:{ all -> 0x0077 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0077 }
            if (r4 != 0) goto L_0x0052
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard r4 = r1.mCard     // Catch:{ all -> 0x0077 }
            boolean r5 = r4.isSensitive     // Catch:{ all -> 0x0077 }
            if (r5 == 0) goto L_0x003c
            boolean r6 = r8.mHideSensitiveContent     // Catch:{ all -> 0x0077 }
            if (r6 != 0) goto L_0x003c
            boolean r6 = r4.isWorkProfile     // Catch:{ all -> 0x0077 }
            if (r6 != 0) goto L_0x003c
            r6 = r2
            goto L_0x003d
        L_0x003c:
            r6 = r3
        L_0x003d:
            if (r5 == 0) goto L_0x0049
            boolean r7 = r8.mHideWorkContent     // Catch:{ all -> 0x0077 }
            if (r7 != 0) goto L_0x0049
            boolean r4 = r4.isWorkProfile     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x0049
            r4 = r2
            goto L_0x004a
        L_0x0049:
            r4 = r3
        L_0x004a:
            if (r5 == 0) goto L_0x0050
            if (r6 != 0) goto L_0x0050
            if (r4 == 0) goto L_0x0052
        L_0x0050:
            r4 = r2
            goto L_0x0053
        L_0x0052:
            r4 = r3
        L_0x0053:
            if (r4 == 0) goto L_0x00c2
            java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x0077 }
            android.graphics.Bitmap r4 = r1.mIcon     // Catch:{ all -> 0x0077 }
            r5 = 0
            if (r4 != 0) goto L_0x005f
            r4 = r5
            goto L_0x0063
        L_0x005f:
            androidx.core.graphics.drawable.IconCompat r4 = androidx.core.graphics.drawable.IconCompat.createWithBitmap(r4)     // Catch:{ all -> 0x0077 }
        L_0x0063:
            android.app.PendingIntent r6 = r1.getPendingIntent()     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x007a
            if (r6 != 0) goto L_0x006c
            goto L_0x007a
        L_0x006c:
            java.lang.String r5 = r1.substitute(r2)     // Catch:{ all -> 0x0077 }
            androidx.slice.builders.SliceAction r7 = new androidx.slice.builders.SliceAction     // Catch:{ all -> 0x0077 }
            r7.<init>(r6, r4, r2, r5)     // Catch:{ all -> 0x0077 }
            r5 = r7
            goto L_0x007a
        L_0x0077:
            r0 = move-exception
            goto L_0x0111
        L_0x007a:
            androidx.slice.builders.ListBuilder$HeaderBuilder r6 = new androidx.slice.builders.ListBuilder$HeaderBuilder     // Catch:{ all -> 0x0077 }
            android.net.Uri r7 = r8.mHeaderUri     // Catch:{ all -> 0x0077 }
            r6.<init>(r7)     // Catch:{ all -> 0x0077 }
            java.lang.String r7 = r1.getFormattedTitle()     // Catch:{ all -> 0x0077 }
            r6.mTitle = r7     // Catch:{ all -> 0x0077 }
            r6.mTitleLoading = r3     // Catch:{ all -> 0x0077 }
            if (r5 == 0) goto L_0x008d
            r6.mPrimaryAction = r5     // Catch:{ all -> 0x0077 }
        L_0x008d:
            androidx.slice.builders.impl.ListBuilder r7 = r0.mImpl     // Catch:{ all -> 0x0077 }
            r7.setHeader(r6)     // Catch:{ all -> 0x0077 }
            java.lang.String r1 = r1.substitute(r3)     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x00af
            androidx.slice.builders.ListBuilder$RowBuilder r6 = new androidx.slice.builders.ListBuilder$RowBuilder     // Catch:{ all -> 0x0077 }
            android.net.Uri r7 = r8.mCalendarUri     // Catch:{ all -> 0x0077 }
            r6.<init>(r7)     // Catch:{ all -> 0x0077 }
            r6.mTitle = r1     // Catch:{ all -> 0x0077 }
            r6.mTitleLoading = r3     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x00a8
            r6.addEndItem(r4, r2)     // Catch:{ all -> 0x0077 }
        L_0x00a8:
            if (r5 == 0) goto L_0x00ac
            r6.mPrimaryAction = r5     // Catch:{ all -> 0x0077 }
        L_0x00ac:
            r0.addRow(r6)     // Catch:{ all -> 0x0077 }
        L_0x00af:
            r8.addZenModeLocked(r0)     // Catch:{ all -> 0x0077 }
            r8.addPrimaryActionLocked(r0)     // Catch:{ all -> 0x0077 }
            android.os.Trace.endSection()     // Catch:{ all -> 0x0077 }
            androidx.slice.builders.impl.ListBuilder r0 = r0.mImpl     // Catch:{ all -> 0x0077 }
            androidx.slice.builders.impl.TemplateBuilderImpl r0 = (androidx.slice.builders.impl.TemplateBuilderImpl) r0     // Catch:{ all -> 0x0077 }
            androidx.slice.Slice r0 = r0.build()     // Catch:{ all -> 0x0077 }
            monitor-exit(r8)     // Catch:{ all -> 0x0077 }
            return r0
        L_0x00c2:
            boolean r1 = r8.needsMediaLocked()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x00cc
            r8.addMediaLocked(r0)     // Catch:{ all -> 0x0077 }
            goto L_0x00de
        L_0x00cc:
            androidx.slice.builders.ListBuilder$RowBuilder r1 = new androidx.slice.builders.ListBuilder$RowBuilder     // Catch:{ all -> 0x0077 }
            android.net.Uri r2 = r8.mDateUri     // Catch:{ all -> 0x0077 }
            r1.<init>(r2)     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = r8.getFormattedDateLocked()     // Catch:{ all -> 0x0077 }
            r1.mTitle = r2     // Catch:{ all -> 0x0077 }
            r1.mTitleLoading = r3     // Catch:{ all -> 0x0077 }
            r0.addRow(r1)     // Catch:{ all -> 0x0077 }
        L_0x00de:
            r8.addWeather(r0)     // Catch:{ all -> 0x0077 }
            r8.addNextAlarmLocked(r0)     // Catch:{ all -> 0x0077 }
            r8.addZenModeLocked(r0)     // Catch:{ all -> 0x0077 }
            r8.addPrimaryActionLocked(r0)     // Catch:{ all -> 0x0077 }
            monitor-exit(r8)     // Catch:{ all -> 0x0077 }
            androidx.slice.builders.impl.ListBuilder r8 = r0.mImpl
            androidx.slice.builders.impl.TemplateBuilderImpl r8 = (androidx.slice.builders.impl.TemplateBuilderImpl) r8
            androidx.slice.Slice r8 = r8.build()
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x010d
            java.lang.String r0 = "KeyguardSliceProvider"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Binding slice: "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        L_0x010d:
            android.os.Trace.endSection()
            return r8
        L_0x0111:
            monitor-exit(r8)     // Catch:{ all -> 0x0077 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.keyguard.KeyguardSliceProviderGoogle.onBindSlice():androidx.slice.Slice");
    }

    public final void onCreateSliceProvider() {
        super.onCreateSliceProvider();
        this.mSmartSpaceData = new SmartSpaceData();
        SmartSpaceController smartSpaceController = this.mSmartSpaceController;
        Objects.requireNonNull(smartSpaceController);
        Assert.isMainThread();
        smartSpaceController.mListeners.add(this);
        SmartSpaceData smartSpaceData = smartSpaceController.mData;
        if (smartSpaceData != null) {
            onSmartSpaceUpdated(smartSpaceData);
        }
        onSensitiveModeChanged(smartSpaceController.mHidePrivateData, smartSpaceController.mHideWorkData);
    }

    public final void onDestroy() {
        super.onDestroy();
        SmartSpaceController smartSpaceController = this.mSmartSpaceController;
        Objects.requireNonNull(smartSpaceController);
        Assert.isMainThread();
        smartSpaceController.mListeners.remove(this);
    }

    public final void updateClockLocked() {
        notifyChange();
    }
}
