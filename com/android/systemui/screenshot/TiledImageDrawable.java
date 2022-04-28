package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.internal.util.CallbackRegistry;
import com.android.systemui.screenshot.ImageTileSet;
import java.util.Objects;

public final class TiledImageDrawable extends Drawable {
    public RenderNode mNode;
    public final ImageTileSet mTiles;

    public final int getOpacity() {
        return -1;
    }

    public final void draw(Canvas canvas) {
        RenderNode renderNode;
        RenderNode renderNode2 = this.mNode;
        if (renderNode2 == null || !renderNode2.hasDisplayList()) {
            if (this.mNode == null) {
                this.mNode = new RenderNode("TiledImageDrawable");
            }
            this.mNode.setPosition(0, 0, this.mTiles.getWidth(), this.mTiles.getHeight());
            RecordingCanvas beginRecording = this.mNode.beginRecording();
            ImageTileSet imageTileSet = this.mTiles;
            Objects.requireNonNull(imageTileSet);
            ImageTileSet imageTileSet2 = this.mTiles;
            Objects.requireNonNull(imageTileSet2);
            beginRecording.translate((float) (-imageTileSet.mRegion.getBounds().left), (float) (-imageTileSet2.mRegion.getBounds().top));
            int i = 0;
            while (true) {
                ImageTileSet imageTileSet3 = this.mTiles;
                Objects.requireNonNull(imageTileSet3);
                if (i >= imageTileSet3.mTiles.size()) {
                    this.mNode.endRecording();
                    break;
                }
                ImageTileSet imageTileSet4 = this.mTiles;
                Objects.requireNonNull(imageTileSet4);
                ImageTile imageTile = (ImageTile) imageTileSet4.mTiles.get(i);
                beginRecording.save();
                Objects.requireNonNull(imageTile);
                Rect rect = imageTile.mLocation;
                beginRecording.translate((float) rect.left, (float) rect.top);
                synchronized (imageTile) {
                    if (imageTile.mNode == null) {
                        imageTile.mNode = new RenderNode("Tile{" + Integer.toHexString(imageTile.mImage.hashCode()) + "}");
                    }
                    if (imageTile.mNode.hasDisplayList()) {
                        renderNode = imageTile.mNode;
                    } else {
                        int min = Math.min(imageTile.mImage.getWidth(), imageTile.mLocation.width());
                        int min2 = Math.min(imageTile.mImage.getHeight(), imageTile.mLocation.height());
                        imageTile.mNode.setPosition(0, 0, min, min2);
                        RecordingCanvas beginRecording2 = imageTile.mNode.beginRecording(min, min2);
                        beginRecording2.save();
                        beginRecording2.clipRect(0, 0, imageTile.mLocation.width(), imageTile.mLocation.height());
                        beginRecording2.drawBitmap(Bitmap.wrapHardwareBuffer(imageTile.mImage.getHardwareBuffer(), ImageTile.COLOR_SPACE), 0.0f, 0.0f, (Paint) null);
                        beginRecording2.restore();
                        imageTile.mNode.endRecording();
                        renderNode = imageTile.mNode;
                    }
                }
                beginRecording.drawRenderNode(renderNode);
                beginRecording.restore();
                i++;
            }
        }
        if (canvas.isHardwareAccelerated()) {
            Rect bounds = getBounds();
            canvas.save();
            canvas.clipRect(0, 0, bounds.width(), bounds.height());
            canvas.translate((float) (-bounds.left), (float) (-bounds.top));
            canvas.drawRenderNode(this.mNode);
            canvas.restore();
            return;
        }
        Log.d("TiledImageDrawable", "Canvas is not hardware accelerated. Skipping draw!");
    }

    public final int getIntrinsicHeight() {
        return this.mTiles.getHeight();
    }

    public final int getIntrinsicWidth() {
        return this.mTiles.getWidth();
    }

    public final void setAlpha(int i) {
        if (this.mNode.setAlpha(((float) i) / 255.0f)) {
            invalidateSelf();
        }
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        throw new IllegalArgumentException("not implemented");
    }

    public TiledImageDrawable(ImageTileSet imageTileSet) {
        this.mTiles = imageTileSet;
        TiledImageDrawable$$ExternalSyntheticLambda0 tiledImageDrawable$$ExternalSyntheticLambda0 = new TiledImageDrawable$$ExternalSyntheticLambda0(this);
        if (imageTileSet.mContentListeners == null) {
            imageTileSet.mContentListeners = new CallbackRegistry<>(new CallbackRegistry.NotifierCallback<ImageTileSet.OnContentChangedListener, ImageTileSet, Rect>() {
                public final void onNotifyCallback(
/*
Method generation error in method: com.android.systemui.screenshot.ImageTileSet.1.onNotifyCallback(java.lang.Object, java.lang.Object, int, java.lang.Object):void, dex: classes.dex
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.screenshot.ImageTileSet.1.onNotifyCallback(java.lang.Object, java.lang.Object, int, java.lang.Object):void, class status: UNLOADED
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
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:640)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:429)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
            });
        }
        imageTileSet.mContentListeners.add(tiledImageDrawable$$ExternalSyntheticLambda0);
    }
}
