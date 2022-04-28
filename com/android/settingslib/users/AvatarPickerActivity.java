package com.android.settingslib.users;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3;
import com.google.android.setupcompat.template.FooterButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AvatarPickerActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AvatarAdapter mAdapter;
    public AvatarPhotoController mAvatarPhotoController;
    public FooterButton mDoneButton;
    public boolean mWaitingForActivityResult;

    public class AvatarAdapter extends RecyclerView.Adapter<AvatarViewHolder> {
        public final int mChoosePhotoPosition;
        public final List<String> mImageDescriptions;
        public final ArrayList mImageDrawables;
        public final int mPreselectedImageStartPosition;
        public final TypedArray mPreselectedImages;
        public int mSelectedPosition = -1;
        public final int mTakePhotoPosition;
        public final int[] mUserIconColors;

        /* JADX WARNING: Removed duplicated region for block: B:19:0x005e  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0060  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0065  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x0094  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x00df A[LOOP:1: B:36:0x00df->B:38:0x00e4, LOOP_START, PHI: r2 
          PHI: (r2v2 int) = (r2v1 int), (r2v3 int) binds: [B:35:0x00dc, B:38:0x00e4] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0102  */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x0114  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public AvatarAdapter() {
            /*
                r6 = this;
                com.android.settingslib.users.AvatarPickerActivity.this = r7
                r6.<init>()
                r0 = -1
                r6.mSelectedPosition = r0
                android.content.pm.PackageManager r1 = r7.getPackageManager()
                android.content.Intent r2 = new android.content.Intent
                java.lang.String r3 = "android.media.action.IMAGE_CAPTURE"
                r2.<init>(r3)
                r3 = 65536(0x10000, float:9.18355E-41)
                java.util.List r1 = r1.queryIntentActivities(r2, r3)
                int r1 = r1.size()
                r2 = 0
                r3 = 1
                if (r1 <= 0) goto L_0x0023
                r1 = r3
                goto L_0x0024
            L_0x0023:
                r1 = r2
            L_0x0024:
                android.content.Intent r4 = new android.content.Intent
                java.lang.String r5 = "android.provider.action.PICK_IMAGES"
                r4.<init>(r5)
                java.lang.String r5 = "image/*"
                r4.setType(r5)
                android.content.pm.PackageManager r5 = r7.getPackageManager()
                java.util.List r4 = r5.queryIntentActivities(r4, r2)
                int r4 = r4.size()
                if (r4 <= 0) goto L_0x0040
                r4 = r3
                goto L_0x0041
            L_0x0040:
                r4 = r2
            L_0x0041:
                if (r4 == 0) goto L_0x005b
                java.lang.Class<android.app.KeyguardManager> r4 = android.app.KeyguardManager.class
                java.lang.Object r4 = r7.getSystemService(r4)
                android.app.KeyguardManager r4 = (android.app.KeyguardManager) r4
                if (r4 == 0) goto L_0x0056
                boolean r4 = r4.isDeviceLocked()
                if (r4 == 0) goto L_0x0054
                goto L_0x0056
            L_0x0054:
                r4 = r2
                goto L_0x0057
            L_0x0056:
                r4 = r3
            L_0x0057:
                if (r4 != 0) goto L_0x005b
                r4 = r3
                goto L_0x005c
            L_0x005b:
                r4 = r2
            L_0x005c:
                if (r1 == 0) goto L_0x0060
                r5 = r2
                goto L_0x0061
            L_0x0060:
                r5 = r0
            L_0x0061:
                r6.mTakePhotoPosition = r5
                if (r4 == 0) goto L_0x006a
                if (r1 == 0) goto L_0x0069
                r0 = r3
                goto L_0x006a
            L_0x0069:
                r0 = r2
            L_0x006a:
                r6.mChoosePhotoPosition = r0
                int r1 = r1 + r4
                r6.mPreselectedImageStartPosition = r1
                android.content.res.Resources r0 = r7.getResources()
                r1 = 2130903045(0x7f030005, float:1.7412897E38)
                android.content.res.TypedArray r0 = r0.obtainTypedArray(r1)
                r6.mPreselectedImages = r0
                android.content.res.Resources r7 = r7.getResources()
                int[] r7 = com.android.internal.util.UserIcons.getUserIconColors(r7)
                r6.mUserIconColors = r7
                java.util.ArrayList r7 = new java.util.ArrayList
                r7.<init>()
                r0 = r2
            L_0x008c:
                android.content.res.TypedArray r1 = r6.mPreselectedImages
                int r1 = r1.length()
                if (r0 >= r1) goto L_0x00d8
                android.content.res.TypedArray r1 = r6.mPreselectedImages
                android.graphics.drawable.Drawable r1 = r1.getDrawable(r0)
                boolean r4 = r1 instanceof android.graphics.drawable.BitmapDrawable
                if (r4 == 0) goto L_0x00d0
                android.graphics.drawable.BitmapDrawable r1 = (android.graphics.drawable.BitmapDrawable) r1
                android.graphics.Bitmap r1 = r1.getBitmap()
                com.android.settingslib.users.AvatarPickerActivity r4 = com.android.settingslib.users.AvatarPickerActivity.this
                android.content.res.Resources r4 = r4.getResources()
                androidx.core.graphics.drawable.RoundedBitmapDrawable21 r5 = new androidx.core.graphics.drawable.RoundedBitmapDrawable21
                r5.<init>(r4, r1)
                r5.mIsCircular = r3
                r5.mApplyGravity = r3
                int r1 = r5.mBitmapHeight
                int r4 = r5.mBitmapWidth
                int r1 = java.lang.Math.min(r1, r4)
                int r1 = r1 / 2
                float r1 = (float) r1
                r5.mCornerRadius = r1
                android.graphics.Paint r1 = r5.mPaint
                android.graphics.BitmapShader r4 = r5.mBitmapShader
                r1.setShader(r4)
                r5.invalidateSelf()
                r7.add(r5)
                int r0 = r0 + 1
                goto L_0x008c
            L_0x00d0:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "Avatar drawables must be bitmaps"
                r6.<init>(r7)
                throw r6
            L_0x00d8:
                boolean r0 = r7.isEmpty()
                if (r0 != 0) goto L_0x00df
                goto L_0x00f8
            L_0x00df:
                int[] r0 = r6.mUserIconColors
                int r0 = r0.length
                if (r2 >= r0) goto L_0x00f8
                com.android.settingslib.users.AvatarPickerActivity r0 = com.android.settingslib.users.AvatarPickerActivity.this
                android.content.res.Resources r0 = r0.getResources()
                int[] r1 = r6.mUserIconColors
                r1 = r1[r2]
                android.graphics.drawable.Drawable r0 = com.android.internal.util.UserIcons.getDefaultUserIconInColor(r0, r1)
                r7.add(r0)
                int r2 = r2 + 1
                goto L_0x00df
            L_0x00f8:
                r6.mImageDrawables = r7
                android.content.res.TypedArray r7 = r6.mPreselectedImages
                int r7 = r7.length()
                if (r7 <= 0) goto L_0x0114
                com.android.settingslib.users.AvatarPickerActivity r7 = com.android.settingslib.users.AvatarPickerActivity.this
                android.content.res.Resources r7 = r7.getResources()
                r0 = 2130903044(0x7f030004, float:1.7412895E38)
                java.lang.String[] r7 = r7.getStringArray(r0)
                java.util.List r7 = java.util.Arrays.asList(r7)
                goto L_0x0115
            L_0x0114:
                r7 = 0
            L_0x0115:
                r6.mImageDescriptions = r7
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.users.AvatarPickerActivity.AvatarAdapter.<init>(com.android.settingslib.users.AvatarPickerActivity):void");
        }

        public final int getItemCount() {
            return this.mImageDrawables.size() + this.mPreselectedImageStartPosition;
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            AvatarViewHolder avatarViewHolder = (AvatarViewHolder) viewHolder;
            boolean z = true;
            if (i == this.mTakePhotoPosition) {
                avatarViewHolder.mImageView.setImageDrawable(AvatarPickerActivity.this.getDrawable(C1777R.C1778drawable.avatar_take_photo_circled));
                avatarViewHolder.mImageView.setContentDescription(AvatarPickerActivity.this.getString(C1777R.string.user_image_take_photo));
                avatarViewHolder.mImageView.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda3(this, 1));
            } else if (i == this.mChoosePhotoPosition) {
                avatarViewHolder.mImageView.setImageDrawable(AvatarPickerActivity.this.getDrawable(C1777R.C1778drawable.avatar_choose_photo_circled));
                avatarViewHolder.mImageView.setContentDescription(AvatarPickerActivity.this.getString(C1777R.string.user_image_choose_photo));
                avatarViewHolder.mImageView.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda1(this, 1));
            } else {
                int i2 = this.mPreselectedImageStartPosition;
                if (i >= i2) {
                    int i3 = i - i2;
                    if (i != this.mSelectedPosition) {
                        z = false;
                    }
                    avatarViewHolder.mImageView.setSelected(z);
                    avatarViewHolder.mImageView.setImageDrawable((Drawable) this.mImageDrawables.get(i3));
                    List<String> list = this.mImageDescriptions;
                    if (list != null) {
                        avatarViewHolder.mImageView.setContentDescription(list.get(i3));
                    } else {
                        avatarViewHolder.mImageView.setContentDescription(AvatarPickerActivity.this.getString(C1777R.string.default_user_icon_description));
                    }
                    avatarViewHolder.mImageView.setOnClickListener(new AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0(this, i));
                }
            }
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            return new AvatarViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.avatar_item, recyclerView, false));
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        Uri uri;
        boolean z;
        boolean z2;
        boolean z3 = false;
        this.mWaitingForActivityResult = false;
        AvatarPhotoController avatarPhotoController = this.mAvatarPhotoController;
        Objects.requireNonNull(avatarPhotoController);
        if (i2 == -1) {
            if (intent == null || intent.getData() == null) {
                uri = avatarPhotoController.mTakePictureUri;
            } else {
                uri = intent.getData();
            }
            if (!"content".equals(uri.getScheme())) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid pictureUri scheme: ");
                m.append(uri.getScheme());
                Log.e("AvatarPhotoController", m.toString());
                EventLog.writeEvent(1397638484, new Object[]{"172939189", -1, uri.getPath()});
                return;
            }
            switch (i) {
                case 1001:
                case 1002:
                    if (avatarPhotoController.mTakePictureUri.equals(uri)) {
                        AvatarPickerActivity avatarPickerActivity = avatarPhotoController.mActivity;
                        Intent intent2 = new Intent("com.android.camera.action.CROP");
                        intent2.setType("image/*");
                        if (avatarPickerActivity.getPackageManager().queryIntentActivities(intent2, 0).size() > 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            KeyguardManager keyguardManager = (KeyguardManager) avatarPickerActivity.getSystemService(KeyguardManager.class);
                            if (keyguardManager == null || keyguardManager.isDeviceLocked()) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (!z2) {
                                z3 = true;
                            }
                        }
                        if (z3) {
                            avatarPhotoController.cropPhoto();
                            return;
                        } else {
                            new AsyncTask<Void, Void, Bitmap>(uri) {
                                public final java.lang.Object doInBackground(
/*
Method generation error in method: com.android.settingslib.users.AvatarPhotoController.2.doInBackground(java.lang.Object[]):java.lang.Object, dex: classes.dex
                                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.settingslib.users.AvatarPhotoController.2.doInBackground(java.lang.Object[]):java.lang.Object, class status: UNLOADED
                                	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                                	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:91)
                                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:697)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:64)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                                
*/

                                public final void onPostExecute(
/*
Method generation error in method: com.android.settingslib.users.AvatarPhotoController.2.onPostExecute(java.lang.Object):void, dex: classes.dex
                                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.settingslib.users.AvatarPhotoController.2.onPostExecute(java.lang.Object):void, class status: UNLOADED
                                	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                                	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:91)
                                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:697)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:64)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                                
*/
                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[]) null);
                            return;
                        }
                    } else {
                        new AsyncTask<Void, Void, Void>(uri) {
                            public final /* synthetic */ Uri val$pictureUri;

                            public final java.lang.Object doInBackground(
/*
Method generation error in method: com.android.settingslib.users.AvatarPhotoController.1.doInBackground(java.lang.Object[]):java.lang.Object, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.settingslib.users.AvatarPhotoController.1.doInBackground(java.lang.Object[]):java.lang.Object, class status: UNLOADED
                            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:91)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:697)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:64)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            
*/

                            public final void onPostExecute(
/*
Method generation error in method: com.android.settingslib.users.AvatarPhotoController.1.onPostExecute(java.lang.Object):void, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.settingslib.users.AvatarPhotoController.1.onPostExecute(java.lang.Object):void, class status: UNLOADED
                            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:91)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:697)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:64)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            
*/
                        }.execute(new Void[0]);
                        return;
                    }
                case 1003:
                    AvatarPickerActivity avatarPickerActivity2 = avatarPhotoController.mActivity;
                    Objects.requireNonNull(avatarPickerActivity2);
                    Intent intent3 = new Intent();
                    intent3.setData(uri);
                    avatarPickerActivity2.setResult(-1, intent3);
                    avatarPickerActivity2.finish();
                    return;
                default:
                    return;
            }
        }
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.mWaitingForActivityResult = true;
        super.startActivityForResult(intent, i);
    }

    public static class AvatarViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public AvatarViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(C1777R.C1779id.avatar_image);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
        bundle.putInt("selected_position", this.mAdapter.mSelectedPosition);
        super.onSaveInstanceState(bundle);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e A[SYNTHETIC, Splitter:B:15:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x018a  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0190  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onCreate(android.os.Bundle r8) {
        /*
            r7 = this;
            super.onCreate(r8)
            com.google.android.setupcompat.util.Logger r0 = com.google.android.setupdesign.util.ThemeHelper.LOG
            android.os.Bundle r0 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyDynamicColorBundle
            java.lang.String r1 = "isDynamicColorEnabled"
            r2 = 0
            if (r0 != 0) goto L_0x0026
            r0 = 0
            android.content.ContentResolver r3 = r7.getContentResolver()     // Catch:{ IllegalArgumentException | SecurityException -> 0x001c }
            android.net.Uri r4 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.getContentUri()     // Catch:{ IllegalArgumentException | SecurityException -> 0x001c }
            android.os.Bundle r3 = r3.call(r4, r1, r0, r0)     // Catch:{ IllegalArgumentException | SecurityException -> 0x001c }
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyDynamicColorBundle = r3     // Catch:{ IllegalArgumentException | SecurityException -> 0x001c }
            goto L_0x0026
        L_0x001c:
            java.lang.String r1 = "PartnerConfigHelper"
            java.lang.String r3 = "SetupWizard dynamic color supporting status unknown; return as false."
            android.util.Log.w(r1, r3)
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyDynamicColorBundle = r0
            goto L_0x0032
        L_0x0026:
            android.os.Bundle r0 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyDynamicColorBundle
            if (r0 == 0) goto L_0x0032
            boolean r0 = r0.getBoolean(r1, r2)
            if (r0 == 0) goto L_0x0032
            r0 = 1
            goto L_0x0033
        L_0x0032:
            r0 = r2
        L_0x0033:
            if (r0 != 0) goto L_0x003e
            com.google.android.setupcompat.util.Logger r0 = com.google.android.setupdesign.util.ThemeHelper.LOG
            java.lang.String r1 = "SetupWizard does not support the dynamic color or supporting status unknown."
            r0.mo18774w(r1)
            goto L_0x0102
        L_0x003e:
            android.app.Activity r0 = com.google.android.setupcompat.PartnerCustomizationLayout.lookupActivityFromContext(r7)     // Catch:{ IllegalArgumentException -> 0x00f5 }
            android.app.Activity r1 = com.google.android.setupcompat.PartnerCustomizationLayout.lookupActivityFromContext(r7)     // Catch:{ IllegalArgumentException -> 0x00d9 }
            android.content.Intent r1 = r1.getIntent()
            boolean r1 = com.google.android.setupcompat.util.WizardManagerHelper.isAnySetupWizard(r1)
            boolean r3 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.isSetupWizardDayNightEnabled(r7)
            if (r1 == 0) goto L_0x005e
            if (r3 == 0) goto L_0x005a
            r1 = 2132017719(0x7f140237, float:1.9673724E38)
            goto L_0x0080
        L_0x005a:
            r1 = 2132017720(0x7f140238, float:1.9673726E38)
            goto L_0x0080
        L_0x005e:
            if (r3 == 0) goto L_0x0064
            r1 = 2132017731(0x7f140243, float:1.9673749E38)
            goto L_0x0067
        L_0x0064:
            r1 = 2132017732(0x7f140244, float:1.967375E38)
        L_0x0067:
            com.google.android.setupcompat.util.Logger r4 = com.google.android.setupdesign.util.ThemeHelper.LOG
            java.lang.String r5 = "Return "
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            if (r3 == 0) goto L_0x0074
            java.lang.String r3 = "SudFullDynamicColorThemeGlifV3_DayNight"
            goto L_0x0076
        L_0x0074:
            java.lang.String r3 = "SudFullDynamicColorThemeGlifV3_Light"
        L_0x0076:
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r4.atInfo(r3)
        L_0x0080:
            com.google.android.setupcompat.util.Logger r3 = com.google.android.setupdesign.util.ThemeHelper.LOG
            java.lang.String r4 = "Gets the dynamic accentColor: [Light] "
            java.lang.StringBuilder r4 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r4)
            r5 = 2131100650(0x7f0603ea, float:1.7813687E38)
            java.lang.String r5 = com.google.android.setupdesign.util.ThemeHelper.colorIntToHex(r7, r5)
            r4.append(r5)
            java.lang.String r5 = ", "
            r4.append(r5)
            r6 = 17170495(0x106003f, float:2.461209E-38)
            java.lang.String r6 = com.google.android.setupdesign.util.ThemeHelper.colorIntToHex(r7, r6)
            r4.append(r6)
            java.lang.String r6 = ", [Dark] "
            r4.append(r6)
            r6 = 2131100649(0x7f0603e9, float:1.7813685E38)
            java.lang.String r6 = com.google.android.setupdesign.util.ThemeHelper.colorIntToHex(r7, r6)
            r4.append(r6)
            r4.append(r5)
            r5 = 17170490(0x106003a, float:2.4612076E-38)
            java.lang.String r5 = com.google.android.setupdesign.util.ThemeHelper.colorIntToHex(r7, r5)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.util.Objects.requireNonNull(r3)
            r5 = 3
            java.lang.String r6 = "SetupLibrary"
            boolean r5 = android.util.Log.isLoggable(r6, r5)
            if (r5 == 0) goto L_0x00e7
            java.lang.Object r3 = r3.prefix
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r3 = r3.concat(r4)
            android.util.Log.d(r6, r3)
            goto L_0x00e7
        L_0x00d9:
            r1 = move-exception
            com.google.android.setupcompat.util.Logger r3 = com.google.android.setupdesign.util.ThemeHelper.LOG
            java.lang.String r1 = r1.getMessage()
            java.util.Objects.requireNonNull(r1)
            r3.mo18771e(r1)
            r1 = r2
        L_0x00e7:
            if (r1 == 0) goto L_0x00ed
            r0.setTheme(r1)
            goto L_0x0102
        L_0x00ed:
            com.google.android.setupcompat.util.Logger r0 = com.google.android.setupdesign.util.ThemeHelper.LOG
            java.lang.String r1 = "Error occurred on getting dynamic color theme."
            r0.mo18774w(r1)
            goto L_0x0102
        L_0x00f5:
            r0 = move-exception
            com.google.android.setupcompat.util.Logger r1 = com.google.android.setupdesign.util.ThemeHelper.LOG
            java.lang.String r0 = r0.getMessage()
            java.util.Objects.requireNonNull(r0)
            r1.mo18771e(r0)
        L_0x0102:
            r0 = 2131624011(0x7f0e004b, float:1.887519E38)
            r7.setContentView(r0)
            r0 = 2131428023(0x7f0b02b7, float:1.8477679E38)
            android.view.View r0 = r7.findViewById(r0)
            com.google.android.setupdesign.GlifLayout r0 = (com.google.android.setupdesign.GlifLayout) r0
            java.lang.Class<com.google.android.setupcompat.template.FooterBarMixin> r1 = com.google.android.setupcompat.template.FooterBarMixin.class
            com.google.android.setupcompat.template.Mixin r0 = r0.getMixin(r1)
            com.google.android.setupcompat.template.FooterBarMixin r0 = (com.google.android.setupcompat.template.FooterBarMixin) r0
            com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0 r1 = new com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
            r1.<init>(r7, r2)
            com.google.android.setupcompat.template.FooterButton r3 = new com.google.android.setupcompat.template.FooterButton
            java.lang.String r4 = "Cancel"
            r3.<init>((java.lang.String) r4, (android.view.View.OnClickListener) r1)
            com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1 r1 = new com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1
            r1.<init>(r7, r2)
            com.google.android.setupcompat.template.FooterButton r4 = new com.google.android.setupcompat.template.FooterButton
            java.lang.String r5 = "Done"
            r4.<init>((java.lang.String) r5, (android.view.View.OnClickListener) r1)
            r7.mDoneButton = r4
            r4.setEnabled(r2)
            r0.setSecondaryButton(r3)
            com.google.android.setupcompat.template.FooterButton r1 = r7.mDoneButton
            r0.setPrimaryButton(r1)
            r0 = 2131427533(0x7f0b00cd, float:1.8476685E38)
            android.view.View r0 = r7.findViewById(r0)
            androidx.recyclerview.widget.RecyclerView r0 = (androidx.recyclerview.widget.RecyclerView) r0
            com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter r1 = new com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter
            r1.<init>()
            r7.mAdapter = r1
            r0.setAdapter(r1)
            androidx.recyclerview.widget.GridLayoutManager r1 = new androidx.recyclerview.widget.GridLayoutManager
            android.content.res.Resources r3 = r7.getResources()
            r4 = 2131492870(0x7f0c0006, float:1.8609204E38)
            int r3 = r3.getInteger(r4)
            r1.<init>(r3)
            r0.setLayoutManager(r1)
            if (r8 == 0) goto L_0x017a
            java.lang.String r0 = "awaiting_result"
            boolean r0 = r8.getBoolean(r0, r2)
            r7.mWaitingForActivityResult = r0
            com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter r0 = r7.mAdapter
            r1 = -1
            java.lang.String r2 = "selected_position"
            int r8 = r8.getInt(r2, r1)
            r0.mSelectedPosition = r8
        L_0x017a:
            com.android.settingslib.users.AvatarPhotoController r8 = new com.android.settingslib.users.AvatarPhotoController
            boolean r0 = r7.mWaitingForActivityResult
            android.content.Intent r1 = r7.getIntent()
            java.lang.String r2 = "file_authority"
            java.lang.String r1 = r1.getStringExtra(r2)
            if (r1 == 0) goto L_0x0190
            r8.<init>(r7, r0, r1)
            r7.mAvatarPhotoController = r8
            return
        L_0x0190:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "File authority must be provided"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.users.AvatarPickerActivity.onCreate(android.os.Bundle):void");
    }
}
