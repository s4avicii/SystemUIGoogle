package com.android.systemui.p006qs;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.util.ArraySet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;
import com.android.systemui.p006qs.QSPanelControllerBase;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import com.android.systemui.plugins.p005qs.QSTileView;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSTileRevealController */
public final class QSTileRevealController {
    public final Context mContext;
    public final Handler mHandler = new Handler();
    public final PagedTileLayout mPagedTileLayout;
    public final QSPanelController mQSPanelController;
    public final QSCustomizerController mQsCustomizerController;
    public final C10021 mRevealQsTiles = new Runnable() {
        public final void run() {
            QSTileRevealController qSTileRevealController = QSTileRevealController.this;
            PagedTileLayout pagedTileLayout = qSTileRevealController.mPagedTileLayout;
            ArraySet<String> arraySet = qSTileRevealController.mTilesToReveal;
            SuggestController$$ExternalSyntheticLambda1 suggestController$$ExternalSyntheticLambda1 = new SuggestController$$ExternalSyntheticLambda1(this, 1);
            Objects.requireNonNull(pagedTileLayout);
            if (!arraySet.isEmpty() && pagedTileLayout.mPages.size() >= 2 && pagedTileLayout.getScrollX() == 0 && pagedTileLayout.beginFakeDrag()) {
                int size = pagedTileLayout.mPages.size() - 1;
                ArrayList arrayList = new ArrayList();
                Iterator<QSPanelControllerBase.TileRecord> it = pagedTileLayout.mPages.get(size).mRecords.iterator();
                while (it.hasNext()) {
                    QSPanelControllerBase.TileRecord next = it.next();
                    if (arraySet.contains(next.tile.getTileSpec())) {
                        QSTileView qSTileView = next.tileView;
                        int size2 = arrayList.size();
                        qSTileView.setAlpha(0.0f);
                        qSTileView.setScaleX(0.0f);
                        qSTileView.setScaleY(0.0f);
                        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(qSTileView, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{1.0f}), PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{1.0f}), PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{1.0f})});
                        ofPropertyValuesHolder.setDuration(450);
                        ofPropertyValuesHolder.setStartDelay((long) (size2 * 85));
                        ofPropertyValuesHolder.setInterpolator(new OvershootInterpolator(1.3f));
                        arrayList.add(ofPropertyValuesHolder);
                    }
                }
                if (arrayList.isEmpty()) {
                    pagedTileLayout.endFakeDrag();
                    return;
                }
                AnimatorSet animatorSet = new AnimatorSet();
                pagedTileLayout.mBounceAnimatorSet = animatorSet;
                animatorSet.playTogether(arrayList);
                pagedTileLayout.mBounceAnimatorSet.addListener(new AnimatorListenerAdapter(suggestController$$ExternalSyntheticLambda1) {
                    public final /* synthetic */ Runnable val$postAnimation;

                    public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.qs.PagedTileLayout.1.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.qs.PagedTileLayout.1.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
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
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:98)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:480)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	at jadx.core.codegen.ClassGen.addInsnBody(ClassGen.java:437)
                    	at jadx.core.codegen.ClassGen.addField(ClassGen.java:378)
                    	at jadx.core.codegen.ClassGen.addFields(ClassGen.java:348)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:226)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                    
*/
                });
                pagedTileLayout.setOffscreenPageLimit(size);
                int width = pagedTileLayout.getWidth() * size;
                Scroller scroller = pagedTileLayout.mScroller;
                int scrollX = pagedTileLayout.getScrollX();
                int scrollY = pagedTileLayout.getScrollY();
                if (pagedTileLayout.isLayoutRtl()) {
                    width = -width;
                }
                scroller.startScroll(scrollX, scrollY, width, 0, 750);
                pagedTileLayout.postInvalidateOnAnimation();
            }
        }
    };
    public final ArraySet<String> mTilesToReveal = new ArraySet<>();

    /* renamed from: com.android.systemui.qs.QSTileRevealController$Factory */
    public static class Factory {
        public final Context mContext;
        public final QSCustomizerController mQsCustomizerController;

        public Factory(Context context, QSCustomizerController qSCustomizerController) {
            this.mContext = context;
            this.mQsCustomizerController = qSCustomizerController;
        }
    }

    public final void addTileSpecsToRevealed(ArraySet<String> arraySet) {
        Context context = this.mContext;
        ArraySet arraySet2 = new ArraySet(context.getSharedPreferences(context.getPackageName(), 0).getStringSet("QsTileSpecsRevealed", Collections.EMPTY_SET));
        arraySet2.addAll(arraySet);
        Context context2 = this.mContext;
        context2.getSharedPreferences(context2.getPackageName(), 0).edit().putStringSet("QsTileSpecsRevealed", arraySet2).apply();
    }

    public QSTileRevealController(Context context, QSPanelController qSPanelController, PagedTileLayout pagedTileLayout, QSCustomizerController qSCustomizerController) {
        this.mContext = context;
        this.mQSPanelController = qSPanelController;
        this.mPagedTileLayout = pagedTileLayout;
        this.mQsCustomizerController = qSCustomizerController;
    }
}
