package com.android.systemui.p006qs.tiles;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.UserHandle;
import android.service.notification.Condition;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.internal.policy.PhoneWindow;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.notification.EnableZenModeDialog;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.volume.VolumeDialogImpl;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.DndTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DndTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DndTile$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DndTile dndTile = (DndTile) this.f$0;
                View view = (View) this.f$1;
                Objects.requireNonNull(dndTile);
                EnableZenModeDialog enableZenModeDialog = new EnableZenModeDialog(dndTile.mContext, dndTile.mQSZenDialogMetricsLogger);
                enableZenModeDialog.mNotificationManager = (NotificationManager) enableZenModeDialog.mContext.getSystemService("notification");
                enableZenModeDialog.mForeverId = Condition.newId(enableZenModeDialog.mContext).appendPath("forever").build();
                enableZenModeDialog.mAlarmManager = (AlarmManager) enableZenModeDialog.mContext.getSystemService("alarm");
                enableZenModeDialog.mUserId = enableZenModeDialog.mContext.getUserId();
                AlertDialog.Builder positiveButton = new AlertDialog.Builder(enableZenModeDialog.mContext, 2132018183).setTitle(C1777R.string.zen_mode_settings_turn_on_dialog_title).setPositiveButton(C1777R.string.zen_mode_enable_dialog_turn_on, new DialogInterface.OnClickListener() {
                    public final void onClick(
/*
Method generation error in method: com.android.settingslib.notification.EnableZenModeDialog.1.onClick(android.content.DialogInterface, int):void, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.settingslib.notification.EnableZenModeDialog.1.onClick(android.content.DialogInterface, int):void, class status: UNLOADED
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
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:64)
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
                positiveButton.setNeutralButton(C1777R.string.cancel, (DialogInterface.OnClickListener) null);
                if (enableZenModeDialog.mLayoutInflater == null) {
                    enableZenModeDialog.mLayoutInflater = new PhoneWindow(enableZenModeDialog.mContext).getLayoutInflater();
                }
                View inflate = enableZenModeDialog.mLayoutInflater.inflate(C1777R.layout.zen_mode_turn_on_dialog_container, (ViewGroup) null);
                ScrollView scrollView = (ScrollView) inflate.findViewById(C1777R.C1779id.container);
                enableZenModeDialog.mZenRadioGroup = (RadioGroup) scrollView.findViewById(C1777R.C1779id.zen_radio_buttons);
                enableZenModeDialog.mZenRadioGroupContent = (LinearLayout) scrollView.findViewById(C1777R.C1779id.zen_radio_buttons_content);
                enableZenModeDialog.mZenAlarmWarning = (TextView) scrollView.findViewById(C1777R.C1779id.zen_alarm_warning);
                for (int i = 0; i < 3; i++) {
                    View inflate2 = enableZenModeDialog.mLayoutInflater.inflate(C1777R.layout.zen_mode_radio_button, enableZenModeDialog.mZenRadioGroup, false);
                    enableZenModeDialog.mZenRadioGroup.addView(inflate2);
                    inflate2.setId(i);
                    View inflate3 = enableZenModeDialog.mLayoutInflater.inflate(C1777R.layout.zen_mode_condition, enableZenModeDialog.mZenRadioGroupContent, false);
                    inflate3.setId(i + 3);
                    enableZenModeDialog.mZenRadioGroupContent.addView(inflate3);
                }
                int childCount = enableZenModeDialog.mZenRadioGroupContent.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    enableZenModeDialog.mZenRadioGroupContent.getChildAt(i2).setVisibility(8);
                }
                enableZenModeDialog.mZenAlarmWarning.setVisibility(8);
                enableZenModeDialog.bindConditions(enableZenModeDialog.forever());
                positiveButton.setView(inflate);
                AlertDialog create = positiveButton.create();
                SystemUIDialog.applyFlags(create);
                SystemUIDialog.setShowForAllUsers(create);
                SystemUIDialog.registerDismissListener(create);
                create.create();
                create.getWindow().setLayout(SystemUIDialog.getDefaultDialogWidth(create), -2);
                if (view != null) {
                    dndTile.mDialogLaunchAnimator.showFromView(create, view, false);
                    return;
                } else {
                    create.show();
                    return;
                }
            case 1:
                AutoTileManager autoTileManager = (AutoTileManager) this.f$0;
                Objects.requireNonNull(autoTileManager);
                autoTileManager.changeUser((UserHandle) this.f$1);
                return;
            default:
                ImageButton imageButton = (ImageButton) this.f$1;
                String str = VolumeDialogImpl.TAG;
                Objects.requireNonNull((VolumeDialogImpl) this.f$0);
                if (imageButton != null) {
                    imageButton.setPressed(true);
                    imageButton.postOnAnimationDelayed(new LockIconViewController$$ExternalSyntheticLambda1(imageButton, 7), 200);
                    return;
                }
                return;
        }
    }
}
