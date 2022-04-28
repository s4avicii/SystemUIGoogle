package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.internal.widget.IMessagingLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.TransformState;
import java.util.ArrayList;
import java.util.HashMap;

public final class MessagingLayoutTransformState extends TransformState {
    public static Pools.SimplePool<MessagingLayoutTransformState> sInstancePool = new Pools.SimplePool<>(40);
    public HashMap<MessagingGroup, MessagingGroup> mGroupMap = new HashMap<>();
    public IMessagingLayout mMessagingLayout;
    public float mRelativeTranslationOffset;

    public static boolean isGone(View view) {
        if (view == null || view.getVisibility() == 8 || view.getParent() == null || view.getWidth() == 0) {
            return true;
        }
        MessagingLinearLayout.LayoutParams layoutParams = view.getLayoutParams();
        return (layoutParams instanceof MessagingLinearLayout.LayoutParams) && layoutParams.hide;
    }

    public final void resetTransformedView() {
        super.resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    View childAt = messageContainer.getChildAt(i2);
                    if (!isGone(childAt)) {
                        resetTransformedView(childAt);
                        TransformState.setClippingDeactivated(childAt, false);
                    }
                }
                resetTransformedView(messagingGroup.getAvatar());
                resetTransformedView(messagingGroup.getSenderView());
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    resetTransformedView(isolatedMessage);
                }
                TransformState.setClippingDeactivated(messagingGroup.getAvatar(), false);
                TransformState.setClippingDeactivated(messagingGroup.getSenderView(), false);
                messagingGroup.setTranslationY(0.0f);
                messagingGroup.getMessageContainer().setTranslationY(0.0f);
                messagingGroup.getSenderView().setTranslationY(0.0f);
            }
            messagingGroup.setClippingDisabled(false);
            messagingGroup.updateClipRect();
        }
        this.mMessagingLayout.setMessagingClippingDisabled(false);
    }

    public final void setVisible(boolean z, boolean z2) {
        super.setVisible(z, z2);
        resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    setVisible(messageContainer.getChildAt(i2), z, z2);
                }
                setVisible(messagingGroup.getAvatar(), z, z2);
                setVisible(messagingGroup.getSenderView(), z, z2);
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    setVisible(isolatedMessage, z, z2);
                }
            }
        }
    }

    public static ArrayList filterHiddenGroups(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        int i = 0;
        while (i < arrayList2.size()) {
            if (isGone((MessagingGroup) arrayList2.get(i))) {
                arrayList2.remove(i);
                i--;
            }
            i++;
        }
        return arrayList2;
    }

    public final void appear(View view, float f) {
        if (view != null && view.getVisibility() != 8) {
            TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
            createFrom.appear(f, (TransformableView) null);
            createFrom.recycle();
        }
    }

    public final void disappear(View view, float f) {
        if (view != null && view.getVisibility() != 8) {
            TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
            createFrom.disappear(f, (TransformableView) null);
            createFrom.recycle();
        }
    }

    public final int transformView(float f, boolean z, View view, View view2, boolean z2, boolean z3) {
        boolean z4;
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        if (z3) {
            createFrom.mDefaultInterpolator = Interpolators.LINEAR;
        }
        int i = 0;
        if (!z2 || isGone(view2)) {
            z4 = false;
        } else {
            z4 = true;
        }
        createFrom.mSameAsAny = z4;
        if (z) {
            if (view2 != null) {
                TransformState createFrom2 = TransformState.createFrom(view2, this.mTransformInfo);
                if (!isGone(view2)) {
                    createFrom.transformViewTo(createFrom2, f);
                } else {
                    if (!isGone(view)) {
                        createFrom.disappear(f, (TransformableView) null);
                    }
                    createFrom.transformViewTo(createFrom2, 16, (ViewTransformationHelper.CustomTransformation) null, f);
                }
                i = createFrom.getLaidOutLocationOnScreen()[1] - createFrom2.getLaidOutLocationOnScreen()[1];
                createFrom2.recycle();
            } else {
                createFrom.disappear(f, (TransformableView) null);
            }
        } else if (view2 != null) {
            TransformState createFrom3 = TransformState.createFrom(view2, this.mTransformInfo);
            if (!isGone(view2)) {
                createFrom.transformViewFrom(createFrom3, f);
            } else {
                if (!isGone(view)) {
                    createFrom.appear(f, (TransformableView) null);
                }
                createFrom.transformViewFrom(createFrom3, 16, (ViewTransformationHelper.CustomTransformation) null, f);
            }
            i = createFrom.getLaidOutLocationOnScreen()[1] - createFrom3.getLaidOutLocationOnScreen()[1];
            createFrom3.recycle();
        } else {
            createFrom.appear(f, (TransformableView) null);
        }
        createFrom.recycle();
        return i;
    }

    public final void transformViewFrom(TransformState transformState, float f) {
        if (transformState instanceof MessagingLayoutTransformState) {
            transformViewInternal((MessagingLayoutTransformState) transformState, f, false);
        } else {
            super.transformViewFrom(transformState, f);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:142:0x0323 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x019b  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01b7  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01c8  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01fc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void transformViewInternal(com.android.systemui.statusbar.notification.MessagingLayoutTransformState r31, float r32, boolean r33) {
        /*
            r30 = this;
            r7 = r30
            r30.ensureVisible()
            com.android.internal.widget.IMessagingLayout r0 = r7.mMessagingLayout
            java.util.ArrayList r0 = r0.getMessagingGroups()
            java.util.ArrayList r8 = filterHiddenGroups(r0)
            r0 = r31
            com.android.internal.widget.IMessagingLayout r0 = r0.mMessagingLayout
            java.util.ArrayList r0 = r0.getMessagingGroups()
            java.util.ArrayList r0 = filterHiddenGroups(r0)
            java.util.HashMap<com.android.internal.widget.MessagingGroup, com.android.internal.widget.MessagingGroup> r1 = r7.mGroupMap
            r1.clear()
            int r1 = r8.size()
            int r1 = r1 + -1
            r2 = 2147483647(0x7fffffff, float:NaN)
        L_0x0029:
            if (r1 < 0) goto L_0x005b
            java.lang.Object r3 = r8.get(r1)
            com.android.internal.widget.MessagingGroup r3 = (com.android.internal.widget.MessagingGroup) r3
            int r4 = r0.size()
            int r4 = java.lang.Math.min(r4, r2)
            int r4 = r4 + -1
            r9 = 0
            r10 = 0
        L_0x003d:
            if (r4 < 0) goto L_0x0051
            java.lang.Object r5 = r0.get(r4)
            com.android.internal.widget.MessagingGroup r5 = (com.android.internal.widget.MessagingGroup) r5
            int r6 = r3.calculateGroupCompatibility(r5)
            if (r6 <= r10) goto L_0x004e
            r2 = r4
            r9 = r5
            r10 = r6
        L_0x004e:
            int r4 = r4 + -1
            goto L_0x003d
        L_0x0051:
            if (r9 == 0) goto L_0x0058
            java.util.HashMap<com.android.internal.widget.MessagingGroup, com.android.internal.widget.MessagingGroup> r4 = r7.mGroupMap
            r4.put(r3, r9)
        L_0x0058:
            int r1 = r1 + -1
            goto L_0x0029
        L_0x005b:
            java.util.HashMap<com.android.internal.widget.MessagingGroup, com.android.internal.widget.MessagingGroup> r11 = r7.mGroupMap
            int r0 = r8.size()
            r12 = 1
            int r0 = r0 - r12
            r13 = 0
            r14 = r0
            r16 = r13
            r15 = 0
        L_0x0068:
            if (r14 < 0) goto L_0x0327
            java.lang.Object r0 = r8.get(r14)
            r6 = r0
            com.android.internal.widget.MessagingGroup r6 = (com.android.internal.widget.MessagingGroup) r6
            java.lang.Object r0 = r11.get(r6)
            r17 = r0
            com.android.internal.widget.MessagingGroup r17 = (com.android.internal.widget.MessagingGroup) r17
            boolean r0 = isGone(r6)
            if (r0 != 0) goto L_0x0323
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r17 == 0) goto L_0x0216
            com.android.internal.widget.MessagingImageMessage r0 = r17.getIsolatedMessage()
            if (r0 != 0) goto L_0x00a2
            com.android.systemui.statusbar.notification.TransformState$TransformInfo r0 = r7.mTransformInfo
            com.android.systemui.statusbar.ViewTransformationHelper r0 = (com.android.systemui.statusbar.ViewTransformationHelper) r0
            java.util.Objects.requireNonNull(r0)
            android.animation.ValueAnimator r0 = r0.mViewTransformationAnimation
            if (r0 == 0) goto L_0x009c
            boolean r0 = r0.isRunning()
            if (r0 == 0) goto L_0x009c
            r0 = r12
            goto L_0x009d
        L_0x009c:
            r0 = 0
        L_0x009d:
            if (r0 != 0) goto L_0x00a2
            r18 = r12
            goto L_0x00a4
        L_0x00a2:
            r18 = 0
        L_0x00a4:
            android.widget.TextView r19 = r6.getSenderView()
            android.widget.TextView r4 = r17.getSenderView()
            android.text.Layout r0 = r19.getLayout()
            if (r0 == 0) goto L_0x00bf
            int r1 = r0.getLineCount()
            int r1 = r1 - r12
            int r0 = r0.getEllipsisCount(r1)
            if (r0 <= 0) goto L_0x00bf
            r0 = r12
            goto L_0x00c0
        L_0x00bf:
            r0 = 0
        L_0x00c0:
            android.text.Layout r1 = r4.getLayout()
            if (r1 == 0) goto L_0x00d3
            int r2 = r1.getLineCount()
            int r2 = r2 - r12
            int r1 = r1.getEllipsisCount(r2)
            if (r1 <= 0) goto L_0x00d3
            r1 = r12
            goto L_0x00d4
        L_0x00d3:
            r1 = 0
        L_0x00d4:
            if (r0 == r1) goto L_0x00d8
            r0 = r12
            goto L_0x00d9
        L_0x00d8:
            r0 = 0
        L_0x00d9:
            r20 = r0 ^ 1
            r0 = r30
            r1 = r32
            r2 = r33
            r3 = r19
            r9 = r5
            r5 = r20
            r20 = r6
            r6 = r18
            r0.transformView(r1, r2, r3, r4, r5, r6)
            android.view.View r3 = r20.getAvatar()
            android.view.View r4 = r17.getAvatar()
            r5 = 1
            int r6 = r0.transformView(r1, r2, r3, r4, r5, r6)
            java.util.List r5 = r20.getMessages()
            java.util.List r4 = r17.getMessages()
            r22 = r12
            r21 = r13
            r3 = 0
        L_0x0107:
            int r0 = r5.size()
            if (r3 >= r0) goto L_0x01f4
            int r0 = r5.size()
            int r0 = r0 - r12
            int r0 = r0 - r3
            java.lang.Object r0 = r5.get(r0)
            com.android.internal.widget.MessagingMessage r0 = (com.android.internal.widget.MessagingMessage) r0
            android.view.View r2 = r0.getView()
            boolean r0 = isGone(r2)
            if (r0 == 0) goto L_0x012e
            r27 = r3
            r28 = r4
            r29 = r5
            r9 = r6
            r3 = r20
            goto L_0x01e6
        L_0x012e:
            int r0 = r4.size()
            int r0 = r0 - r12
            int r0 = r0 - r3
            if (r0 < 0) goto L_0x0149
            java.lang.Object r0 = r4.get(r0)
            com.android.internal.widget.MessagingMessage r0 = (com.android.internal.widget.MessagingMessage) r0
            android.view.View r0 = r0.getView()
            boolean r1 = isGone(r0)
            if (r1 == 0) goto L_0x0147
            goto L_0x0149
        L_0x0147:
            r1 = r0
            goto L_0x014a
        L_0x0149:
            r1 = 0
        L_0x014a:
            if (r1 != 0) goto L_0x0171
            int r0 = (r21 > r13 ? 1 : (r21 == r13 ? 0 : -1))
            if (r0 >= 0) goto L_0x0171
            int r0 = r2.getTop()
            int r23 = r2.getHeight()
            int r0 = r23 + r0
            float r0 = (float) r0
            float r0 = r0 + r21
            int r10 = r2.getHeight()
            float r10 = (float) r10
            float r0 = r0 / r10
            float r0 = java.lang.Math.min(r9, r0)
            float r0 = java.lang.Math.max(r13, r0)
            if (r33 == 0) goto L_0x016f
            float r0 = r9 - r0
        L_0x016f:
            r10 = r0
            goto L_0x0173
        L_0x0171:
            r10 = r32
        L_0x0173:
            r24 = 0
            r0 = r30
            r25 = r1
            r1 = r10
            r26 = r2
            r2 = r33
            r27 = r3
            r3 = r26
            r28 = r4
            r4 = r25
            r29 = r5
            r5 = r24
            r9 = r6
            r6 = r18
            int r0 = r0.transformView(r1, r2, r3, r4, r5, r6)
            com.android.internal.widget.MessagingImageMessage r1 = r17.getIsolatedMessage()
            r2 = r25
            if (r1 != r2) goto L_0x019b
            r1 = r12
            goto L_0x019c
        L_0x019b:
            r1 = 0
        L_0x019c:
            int r3 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r3 != 0) goto L_0x01b3
            if (r1 != 0) goto L_0x01a8
            boolean r3 = r17.isSingleLine()
            if (r3 == 0) goto L_0x01b3
        L_0x01a8:
            r3 = r20
            r3.setClippingDisabled(r12)
            com.android.internal.widget.IMessagingLayout r4 = r7.mMessagingLayout
            r4.setMessagingClippingDisabled(r12)
            goto L_0x01b5
        L_0x01b3:
            r3 = r20
        L_0x01b5:
            if (r2 != 0) goto L_0x01c8
            if (r22 == 0) goto L_0x01bd
            float r21 = r19.getTranslationY()
        L_0x01bd:
            r0 = r21
            r4 = r26
            r4.setTranslationY(r0)
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r4, r12)
            goto L_0x01e2
        L_0x01c8:
            r4 = r26
            com.android.internal.widget.MessagingImageMessage r5 = r3.getIsolatedMessage()
            if (r5 == r4) goto L_0x01e4
            if (r1 == 0) goto L_0x01d3
            goto L_0x01e4
        L_0x01d3:
            if (r33 == 0) goto L_0x01de
            float r1 = r2.getTranslationY()
            float r0 = (float) r0
            float r1 = r1 - r0
            r21 = r1
            goto L_0x01e4
        L_0x01de:
            float r0 = r4.getTranslationY()
        L_0x01e2:
            r21 = r0
        L_0x01e4:
            r22 = 0
        L_0x01e6:
            int r0 = r27 + 1
            r20 = r3
            r6 = r9
            r4 = r28
            r5 = r29
            r9 = 1065353216(0x3f800000, float:1.0)
            r3 = r0
            goto L_0x0107
        L_0x01f4:
            r9 = r6
            r3 = r20
            r3.updateClipRect()
            if (r15 != 0) goto L_0x0323
            if (r33 == 0) goto L_0x0209
            android.view.View r0 = r17.getAvatar()
            float r0 = r0.getTranslationY()
            float r1 = (float) r9
            float r0 = r0 - r1
            goto L_0x0211
        L_0x0209:
            android.view.View r0 = r3.getAvatar()
            float r0 = r0.getTranslationY()
        L_0x0211:
            r16 = r0
            r15 = r3
            goto L_0x0323
        L_0x0216:
            r3 = r6
            if (r15 == 0) goto L_0x029a
            if (r33 == 0) goto L_0x0220
            float r0 = r7.mRelativeTranslationOffset
            float r0 = r0 * r32
            goto L_0x0227
        L_0x0220:
            r0 = 1065353216(0x3f800000, float:1.0)
            float r5 = r0 - r32
            float r0 = r7.mRelativeTranslationOffset
            float r0 = r0 * r5
        L_0x0227:
            android.widget.TextView r1 = r3.getSenderView()
            int r1 = r1.getVisibility()
            r2 = 8
            r4 = 1056964608(0x3f000000, float:0.5)
            if (r1 == r2) goto L_0x0236
            float r0 = r0 * r4
        L_0x0236:
            com.android.internal.widget.MessagingLinearLayout r1 = r3.getMessageContainer()
            r1.setTranslationY(r0)
            android.widget.TextView r1 = r3.getSenderView()
            r1.setTranslationY(r0)
            r0 = 1063675494(0x3f666666, float:0.9)
            float r0 = r0 * r16
            r3.setTranslationY(r0)
            int r0 = r3.getTop()
            float r0 = (float) r0
            float r0 = r0 + r16
            com.android.systemui.statusbar.notification.TransformState$TransformInfo r1 = r7.mTransformInfo
            com.android.systemui.statusbar.ViewTransformationHelper r1 = (com.android.systemui.statusbar.ViewTransformationHelper) r1
            java.util.Objects.requireNonNull(r1)
            android.animation.ValueAnimator r1 = r1.mViewTransformationAnimation
            if (r1 == 0) goto L_0x0266
            boolean r1 = r1.isRunning()
            if (r1 == 0) goto L_0x0266
            r1 = r12
            goto L_0x0267
        L_0x0266:
            r1 = 0
        L_0x0267:
            if (r1 != 0) goto L_0x0276
            int r1 = r3.getHeight()
            int r1 = -r1
            float r1 = (float) r1
            float r1 = r1 * r4
            float r0 = r0 - r1
            float r1 = java.lang.Math.abs(r1)
            goto L_0x028a
        L_0x0276:
            int r1 = r3.getHeight()
            int r1 = -r1
            float r1 = (float) r1
            r2 = 1061158912(0x3f400000, float:0.75)
            float r1 = r1 * r2
            float r0 = r0 - r1
            float r1 = java.lang.Math.abs(r1)
            int r2 = r3.getTop()
            float r2 = (float) r2
            float r1 = r1 + r2
        L_0x028a:
            float r0 = r0 / r1
            r1 = 1065353216(0x3f800000, float:1.0)
            float r0 = java.lang.Math.min(r1, r0)
            float r0 = java.lang.Math.max(r13, r0)
            if (r33 == 0) goto L_0x029c
            float r0 = r1 - r0
            goto L_0x029c
        L_0x029a:
            r0 = r32
        L_0x029c:
            if (r33 == 0) goto L_0x02e1
            com.android.internal.widget.MessagingLinearLayout r1 = r3.getMessageContainer()
            r2 = 0
        L_0x02a3:
            int r4 = r1.getChildCount()
            if (r2 >= r4) goto L_0x02bd
            android.view.View r4 = r1.getChildAt(r2)
            boolean r5 = isGone(r4)
            if (r5 == 0) goto L_0x02b4
            goto L_0x02ba
        L_0x02b4:
            r7.disappear(r4, r0)
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r4, r12)
        L_0x02ba:
            int r2 = r2 + 1
            goto L_0x02a3
        L_0x02bd:
            android.view.View r1 = r3.getAvatar()
            r7.disappear(r1, r0)
            android.widget.TextView r1 = r3.getSenderView()
            r7.disappear(r1, r0)
            com.android.internal.widget.MessagingImageMessage r1 = r3.getIsolatedMessage()
            r7.disappear(r1, r0)
            android.widget.TextView r0 = r3.getSenderView()
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r0, r12)
            android.view.View r0 = r3.getAvatar()
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r0, r12)
            goto L_0x0323
        L_0x02e1:
            com.android.internal.widget.MessagingLinearLayout r1 = r3.getMessageContainer()
            r2 = 0
        L_0x02e6:
            int r4 = r1.getChildCount()
            if (r2 >= r4) goto L_0x0300
            android.view.View r4 = r1.getChildAt(r2)
            boolean r5 = isGone(r4)
            if (r5 == 0) goto L_0x02f7
            goto L_0x02fd
        L_0x02f7:
            r7.appear(r4, r0)
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r4, r12)
        L_0x02fd:
            int r2 = r2 + 1
            goto L_0x02e6
        L_0x0300:
            android.view.View r1 = r3.getAvatar()
            r7.appear(r1, r0)
            android.widget.TextView r1 = r3.getSenderView()
            r7.appear(r1, r0)
            com.android.internal.widget.MessagingImageMessage r1 = r3.getIsolatedMessage()
            r7.appear(r1, r0)
            android.widget.TextView r0 = r3.getSenderView()
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r0, r12)
            android.view.View r0 = r3.getAvatar()
            com.android.systemui.statusbar.notification.TransformState.setClippingDeactivated(r0, r12)
        L_0x0323:
            int r14 = r14 + -1
            goto L_0x0068
        L_0x0327:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.MessagingLayoutTransformState.transformViewInternal(com.android.systemui.statusbar.notification.MessagingLayoutTransformState, float, boolean):void");
    }

    public final boolean transformViewTo(TransformState transformState, float f) {
        if (!(transformState instanceof MessagingLayoutTransformState)) {
            return super.transformViewTo(transformState, f);
        }
        transformViewInternal((MessagingLayoutTransformState) transformState, f, true);
        return true;
    }

    public final void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        MessagingLinearLayout messagingLinearLayout = this.mTransformedView;
        if (messagingLinearLayout instanceof MessagingLinearLayout) {
            this.mMessagingLayout = messagingLinearLayout.getMessagingLayout();
            this.mRelativeTranslationOffset = view.getContext().getResources().getDisplayMetrics().density * 8.0f;
        }
    }

    public final void prepareFadeIn() {
        resetTransformedView();
        setVisible(true, false);
    }

    public final void recycle() {
        super.recycle();
        this.mGroupMap.clear();
        sInstancePool.release(this);
    }

    public final void reset() {
        super.reset();
        this.mMessagingLayout = null;
    }

    public final void setVisible(View view, boolean z, boolean z2) {
        if (!isGone(view) && !MessagingPropertyAnimator.isAnimatingAlpha(view)) {
            TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
            createFrom.setVisible(z, z2);
            createFrom.recycle();
        }
    }

    public final void resetTransformedView(View view) {
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.resetTransformedView();
        createFrom.recycle();
    }
}
