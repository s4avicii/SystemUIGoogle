package com.android.systemui.statusbar.notification.stack;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Trace;
import android.util.SparseArray;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceBuilderIterator;

/* compiled from: NotificationSectionsManager.kt */
public final class NotificationSectionsManager implements StackScrollAlgorithm.SectionProvider {
    public final SectionHeaderController alertingHeaderController;
    public final ConfigurationController configurationController;
    public final NotificationSectionsManager$configurationListener$1 configurationListener = new NotificationSectionsManager$configurationListener$1(this);
    public final SectionHeaderController incomingHeaderController;
    public boolean initialized;
    public final KeyguardMediaController keyguardMediaController;
    public final NotificationSectionsLogger logger;
    public final MediaContainerController mediaContainerController;
    public final NotifPipelineFlags notifPipelineFlags;
    public NotificationStackScrollLayout parent;
    public final SectionHeaderController peopleHeaderController;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;
    public final SectionHeaderController silentHeaderController;
    public final StatusBarStateController statusBarStateController;

    /* compiled from: NotificationSectionsManager.kt */
    public static abstract class SectionBounds {

        /* compiled from: NotificationSectionsManager.kt */
        public static final class Many extends SectionBounds {
            public final ExpandableView first;
            public final ExpandableView last;

            public Many(ExpandableView expandableView, ExpandableView expandableView2) {
                super(0);
                this.first = expandableView;
                this.last = expandableView2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Many)) {
                    return false;
                }
                Many many = (Many) obj;
                return Intrinsics.areEqual(this.first, many.first) && Intrinsics.areEqual(this.last, many.last);
            }

            public final int hashCode() {
                return this.last.hashCode() + (this.first.hashCode() * 31);
            }

            public final String toString() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Many(first=");
                m.append(this.first);
                m.append(", last=");
                m.append(this.last);
                m.append(')');
                return m.toString();
            }
        }

        /* compiled from: NotificationSectionsManager.kt */
        public static final class None extends SectionBounds {
            public static final None INSTANCE = new None();

            public None() {
                super(0);
            }
        }

        /* compiled from: NotificationSectionsManager.kt */
        public static final class One extends SectionBounds {
            public final ExpandableView lone;

            public One(ExpandableView expandableView) {
                super(0);
                this.lone = expandableView;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof One) && Intrinsics.areEqual(this.lone, ((One) obj).lone);
            }

            public final int hashCode() {
                return this.lone.hashCode();
            }

            public final String toString() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("One(lone=");
                m.append(this.lone);
                m.append(')');
                return m.toString();
            }
        }

        public SectionBounds() {
        }

        public SectionBounds(int i) {
        }

        public static boolean setFirstAndLastVisibleChildren(NotificationSection notificationSection, ExpandableView expandableView, ExpandableView expandableView2) {
            boolean z;
            boolean z2;
            if (notificationSection.mFirstVisibleChild != expandableView) {
                z = true;
            } else {
                z = false;
            }
            notificationSection.mFirstVisibleChild = expandableView;
            if (notificationSection.mLastVisibleChild != expandableView2) {
                z2 = true;
            } else {
                z2 = false;
            }
            notificationSection.mLastVisibleChild = expandableView2;
            if (z || z2) {
                return true;
            }
            return false;
        }
    }

    /* compiled from: NotificationSectionsManager.kt */
    public interface SectionUpdateState<T extends ExpandableView> {
        void adjustViewPosition();

        Integer getCurrentPosition();

        Integer getTargetPosition();

        void setCurrentPosition(Integer num);

        void setTargetPosition(Integer num);
    }

    @VisibleForTesting
    public static /* synthetic */ void getAlertingHeaderView$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getIncomingHeaderView$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getMediaControlsView$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getPeopleHeaderView$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getSilentHeaderView$annotations() {
    }

    @VisibleForTesting
    public final Unit updateSectionBoundaries() {
        updateSectionBoundaries("test");
        return Unit.INSTANCE;
    }

    public final NotificationSectionsManager$decorViewHeaderState$1 decorViewHeaderState(SectionHeaderView sectionHeaderView) {
        this.notifPipelineFlags.checkLegacyPipelineEnabled();
        return new NotificationSectionsManager$decorViewHeaderState$1(new NotificationSectionsManager$expandableViewHeaderState$1(sectionHeaderView, this), sectionHeaderView);
    }

    public final SectionHeaderView getAlertingHeaderView() {
        return this.alertingHeaderController.getHeaderView();
    }

    public final MediaContainerView getMediaControlsView() {
        MediaContainerController mediaContainerController2 = this.mediaContainerController;
        Objects.requireNonNull(mediaContainerController2);
        return mediaContainerController2.mediaContainerView;
    }

    public final SectionHeaderView getPeopleHeaderView() {
        return this.peopleHeaderController.getHeaderView();
    }

    public final SectionHeaderView getSilentHeaderView() {
        return this.silentHeaderController.getHeaderView();
    }

    public final void logShadeChild(int i, View view) {
        if (view == this.incomingHeaderController.getHeaderView()) {
            NotificationSectionsLogger notificationSectionsLogger = this.logger;
            Objects.requireNonNull(notificationSectionsLogger);
            notificationSectionsLogger.logPosition(i, "INCOMING HEADER");
        } else if (view == getMediaControlsView()) {
            NotificationSectionsLogger notificationSectionsLogger2 = this.logger;
            Objects.requireNonNull(notificationSectionsLogger2);
            notificationSectionsLogger2.logPosition(i, "MEDIA CONTROLS");
        } else if (view == getPeopleHeaderView()) {
            NotificationSectionsLogger notificationSectionsLogger3 = this.logger;
            Objects.requireNonNull(notificationSectionsLogger3);
            notificationSectionsLogger3.logPosition(i, "CONVERSATIONS HEADER");
        } else if (view == getAlertingHeaderView()) {
            NotificationSectionsLogger notificationSectionsLogger4 = this.logger;
            Objects.requireNonNull(notificationSectionsLogger4);
            notificationSectionsLogger4.logPosition(i, "ALERTING HEADER");
        } else if (view == getSilentHeaderView()) {
            NotificationSectionsLogger notificationSectionsLogger5 = this.logger;
            Objects.requireNonNull(notificationSectionsLogger5);
            notificationSectionsLogger5.logPosition(i, "SILENT HEADER");
        } else if (!(view instanceof ExpandableNotificationRow)) {
            NotificationSectionsLogger notificationSectionsLogger6 = this.logger;
            Class<?> cls = view.getClass();
            Objects.requireNonNull(notificationSectionsLogger6);
            LogBuffer logBuffer = notificationSectionsLogger6.logBuffer;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationSectionsLogger$logOther$2 notificationSectionsLogger$logOther$2 = NotificationSectionsLogger$logOther$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("NotifSections", logLevel, notificationSectionsLogger$logOther$2);
                obtain.int1 = i;
                obtain.str1 = cls.getName();
                logBuffer.push(obtain);
            }
        } else {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            Objects.requireNonNull(expandableNotificationRow);
            boolean z = expandableNotificationRow.mIsHeadsUp;
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            Objects.requireNonNull(notificationEntry);
            int i2 = notificationEntry.mBucket;
            if (i2 == 2) {
                NotificationSectionsLogger notificationSectionsLogger7 = this.logger;
                Objects.requireNonNull(notificationSectionsLogger7);
                notificationSectionsLogger7.logPosition(i, "Heads Up", z);
            } else if (i2 == 4) {
                NotificationSectionsLogger notificationSectionsLogger8 = this.logger;
                Objects.requireNonNull(notificationSectionsLogger8);
                notificationSectionsLogger8.logPosition(i, "Conversation", z);
            } else if (i2 == 5) {
                NotificationSectionsLogger notificationSectionsLogger9 = this.logger;
                Objects.requireNonNull(notificationSectionsLogger9);
                notificationSectionsLogger9.logPosition(i, "Alerting", z);
            } else if (i2 == 6) {
                NotificationSectionsLogger notificationSectionsLogger10 = this.logger;
                Objects.requireNonNull(notificationSectionsLogger10);
                notificationSectionsLogger10.logPosition(i, "Silent", z);
            }
        }
    }

    public final void logShadeContents() {
        Trace.beginSection("NotifSectionsManager.logShadeContents");
        try {
            NotificationStackScrollLayout notificationStackScrollLayout = this.parent;
            if (notificationStackScrollLayout == null) {
                notificationStackScrollLayout = null;
            }
            int i = 0;
            Iterator<Object> it = ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout).iterator();
            while (true) {
                SequenceBuilderIterator sequenceBuilderIterator = (SequenceBuilderIterator) it;
                if (sequenceBuilderIterator.hasNext()) {
                    Object next = sequenceBuilderIterator.next();
                    int i2 = i + 1;
                    if (i >= 0) {
                        logShadeChild(i, (View) next);
                        i = i2;
                    } else {
                        SetsKt__SetsKt.throwIndexOverflow();
                        throw null;
                    }
                } else {
                    return;
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void reinflateViews() {
        /*
            r7 = this;
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r0 = r7.silentHeaderController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r7.parent
            r2 = 0
            if (r1 != 0) goto L_0x0008
            r1 = r2
        L_0x0008:
            r0.reinflateView(r1)
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r0 = r7.alertingHeaderController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r7.parent
            if (r1 != 0) goto L_0x0012
            r1 = r2
        L_0x0012:
            r0.reinflateView(r1)
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r0 = r7.peopleHeaderController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r7.parent
            if (r1 != 0) goto L_0x001c
            r1 = r2
        L_0x001c:
            r0.reinflateView(r1)
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r0 = r7.incomingHeaderController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r7.parent
            if (r1 != 0) goto L_0x0026
            r1 = r2
        L_0x0026:
            r0.reinflateView(r1)
            com.android.systemui.statusbar.notification.collection.render.MediaContainerController r0 = r7.mediaContainerController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r7.parent
            if (r1 != 0) goto L_0x0030
            goto L_0x0031
        L_0x0030:
            r2 = r1
        L_0x0031:
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.MediaContainerView r1 = r0.mediaContainerView
            r3 = -1
            if (r1 != 0) goto L_0x003a
            goto L_0x004b
        L_0x003a:
            r1.removeFromTransientContainer()
            android.view.ViewParent r4 = r1.getParent()
            if (r4 != r2) goto L_0x004b
            int r4 = r2.indexOfChild(r1)
            r2.removeView(r1)
            goto L_0x004c
        L_0x004b:
            r4 = r3
        L_0x004c:
            android.view.LayoutInflater r1 = r0.layoutInflater
            r5 = 2131624161(0x7f0e00e1, float:1.8875494E38)
            r6 = 0
            android.view.View r1 = r1.inflate(r5, r2, r6)
            java.lang.String r5 = "null cannot be cast to non-null type com.android.systemui.statusbar.notification.stack.MediaContainerView"
            java.util.Objects.requireNonNull(r1, r5)
            com.android.systemui.statusbar.notification.stack.MediaContainerView r1 = (com.android.systemui.statusbar.notification.stack.MediaContainerView) r1
            if (r4 == r3) goto L_0x0062
            r2.addView(r1, r4)
        L_0x0062:
            r0.mediaContainerView = r1
            com.android.systemui.media.KeyguardMediaController r0 = r7.keyguardMediaController
            com.android.systemui.statusbar.notification.stack.MediaContainerView r7 = r7.getMediaControlsView()
            r0.attachSinglePaneContainer(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.reinflateViews():void");
    }

    public final boolean updateFirstAndLastViewsForAllSections(NotificationSection[] notificationSectionArr, ArrayList arrayList) {
        SparseArray sparseArray;
        boolean z;
        Object obj;
        SectionBounds.Many many;
        SectionBounds.None none = SectionBounds.None.INSTANCE;
        int length = notificationSectionArr.length;
        if (length < 0) {
            sparseArray = new SparseArray();
        } else {
            sparseArray = new SparseArray(length);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ExpandableView expandableView = (ExpandableView) it.next();
            Integer bucket = getBucket(expandableView);
            if (bucket != null) {
                int intValue = Integer.valueOf(bucket.intValue()).intValue();
                Object obj2 = sparseArray.get(intValue);
                if (obj2 == null) {
                    obj2 = none;
                }
                SectionBounds sectionBounds = (SectionBounds) obj2;
                if (sectionBounds instanceof SectionBounds.None) {
                    obj = new SectionBounds.One(expandableView);
                } else {
                    if (sectionBounds instanceof SectionBounds.One) {
                        many = new SectionBounds.Many(((SectionBounds.One) sectionBounds).lone, expandableView);
                    } else if (sectionBounds instanceof SectionBounds.Many) {
                        many = new SectionBounds.Many(((SectionBounds.Many) sectionBounds).first, expandableView);
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                    obj = many;
                }
                sparseArray.put(intValue, obj);
            } else {
                throw new IllegalArgumentException("Cannot find section bucket for view");
            }
        }
        int length2 = notificationSectionArr.length;
        int i = 0;
        boolean z2 = false;
        while (i < length2) {
            NotificationSection notificationSection = notificationSectionArr[i];
            i++;
            Objects.requireNonNull(notificationSection);
            Object obj3 = (SectionBounds) sparseArray.get(notificationSection.mBucket);
            if (obj3 == null) {
                obj3 = SectionBounds.None.INSTANCE;
            }
            if (obj3 instanceof SectionBounds.None) {
                z = SectionBounds.setFirstAndLastVisibleChildren(notificationSection, (ExpandableView) null, (ExpandableView) null);
            } else if (obj3 instanceof SectionBounds.One) {
                ExpandableView expandableView2 = ((SectionBounds.One) obj3).lone;
                z = SectionBounds.setFirstAndLastVisibleChildren(notificationSection, expandableView2, expandableView2);
            } else if (obj3 instanceof SectionBounds.Many) {
                SectionBounds.Many many2 = (SectionBounds.Many) obj3;
                z = SectionBounds.setFirstAndLastVisibleChildren(notificationSection, many2.first, many2.last);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            if (z || z2) {
                z2 = true;
            } else {
                z2 = false;
            }
        }
        return z2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01a5, code lost:
        if (r16.intValue() != r4.mBucket) goto L_0x01a7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0188 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0196 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01b1 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01b2 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x01c6 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x01c9 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x01eb A[Catch:{ all -> 0x02f4 }, LOOP:0: B:41:0x00c5->B:141:0x01eb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0226 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0227 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x023f A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0240 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x0258 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x0259 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0271 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0272 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0299 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x029e A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x02af A[Catch:{ all -> 0x02f4 }, LOOP:2: B:191:0x02a9->B:193:0x02af, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x02d0 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x02d1 A[Catch:{ all -> 0x02f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x01ea A[EDGE_INSN: B:211:0x01ea->B:140:0x01ea ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015c A[Catch:{ all -> 0x02f4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateSectionBoundaries(java.lang.String r21) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            java.lang.String r2 = "NotifSectionsManager.update"
            android.os.Trace.beginSection(r2)
            com.android.systemui.statusbar.notification.NotifPipelineFlags r2 = r0.notifPipelineFlags     // Catch:{ all -> 0x02f4 }
            r2.checkLegacyPipelineEnabled()     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager r2 = r0.sectionsFeatureManager     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            int[] r2 = r2.getNotificationBuckets()     // Catch:{ all -> 0x02f4 }
            int r2 = r2.length     // Catch:{ all -> 0x02f4 }
            r3 = 1
            r4 = 0
            if (r2 <= r3) goto L_0x001e
            r2 = r3
            goto L_0x001f
        L_0x001e:
            r2 = r4
        L_0x001f:
            if (r2 != 0) goto L_0x0023
            goto L_0x02f0
        L_0x0023:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.log.LogBuffer r2 = r2.logBuffer     // Catch:{ all -> 0x02f4 }
            com.android.systemui.log.LogLevel r5 = com.android.systemui.log.LogLevel.DEBUG     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger$logStartSectionUpdate$2 r6 = new com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger$logStartSectionUpdate$2     // Catch:{ all -> 0x02f4 }
            r6.<init>(r1)     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            boolean r7 = r2.frozen     // Catch:{ all -> 0x02f4 }
            if (r7 != 0) goto L_0x0043
            java.lang.String r7 = "NotifSections"
            com.android.systemui.log.LogMessageImpl r5 = r2.obtain(r7, r5, r6)     // Catch:{ all -> 0x02f4 }
            r5.str1 = r1     // Catch:{ all -> 0x02f4 }
            r2.push(r5)     // Catch:{ all -> 0x02f4 }
        L_0x0043:
            com.android.systemui.plugins.statusbar.StatusBarStateController r1 = r0.statusBarStateController     // Catch:{ all -> 0x02f4 }
            int r1 = r1.getState()     // Catch:{ all -> 0x02f4 }
            if (r1 == r3) goto L_0x004d
            r1 = r3
            goto L_0x004e
        L_0x004d:
            r1 = r4
        L_0x004e:
            com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager r2 = r0.sectionsFeatureManager     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            android.content.Context r2 = r2.context     // Catch:{ all -> 0x02f4 }
            boolean r2 = com.android.systemui.util.Utils.useQsMediaPlayer(r2)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.MediaContainerView r5 = r20.getMediaControlsView()     // Catch:{ all -> 0x02f4 }
            if (r5 != 0) goto L_0x0061
            r7 = 0
            goto L_0x0066
        L_0x0061:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$expandableViewHeaderState$1 r7 = new com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$expandableViewHeaderState$1     // Catch:{ all -> 0x02f4 }
            r7.<init>(r5, r0)     // Catch:{ all -> 0x02f4 }
        L_0x0066:
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r5 = r0.incomingHeaderController     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r5 = r5.getHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r5 != 0) goto L_0x0070
            r5 = 0
            goto L_0x0074
        L_0x0070:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$decorViewHeaderState$1 r5 = r0.decorViewHeaderState(r5)     // Catch:{ all -> 0x02f4 }
        L_0x0074:
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r8 = r20.getPeopleHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r8 != 0) goto L_0x007c
            r8 = 0
            goto L_0x0080
        L_0x007c:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$decorViewHeaderState$1 r8 = r0.decorViewHeaderState(r8)     // Catch:{ all -> 0x02f4 }
        L_0x0080:
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r9 = r20.getAlertingHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r9 != 0) goto L_0x0088
            r9 = 0
            goto L_0x008c
        L_0x0088:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$decorViewHeaderState$1 r9 = r0.decorViewHeaderState(r9)     // Catch:{ all -> 0x02f4 }
        L_0x008c:
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r10 = r20.getSilentHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r10 != 0) goto L_0x0094
            r10 = 0
            goto L_0x0098
        L_0x0094:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$decorViewHeaderState$1 r10 = r0.decorViewHeaderState(r10)     // Catch:{ all -> 0x02f4 }
        L_0x0098:
            r11 = 5
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$SectionUpdateState[] r11 = new com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState[r11]     // Catch:{ all -> 0x02f4 }
            r11[r4] = r7     // Catch:{ all -> 0x02f4 }
            r11[r3] = r5     // Catch:{ all -> 0x02f4 }
            r12 = 2
            r11[r12] = r8     // Catch:{ all -> 0x02f4 }
            r13 = 3
            r11[r13] = r9     // Catch:{ all -> 0x02f4 }
            r13 = 4
            r11[r13] = r10     // Catch:{ all -> 0x02f4 }
            kotlin.sequences.Sequence r11 = kotlin.sequences.SequencesKt__SequencesKt.sequenceOf(r11)     // Catch:{ all -> 0x02f4 }
            kotlin.sequences.FilteringSequence r11 = kotlin.sequences.SequencesKt___SequencesKt.filterNotNull(r11)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r14 = r0.parent     // Catch:{ all -> 0x02f4 }
            if (r14 != 0) goto L_0x00b5
            r14 = 0
        L_0x00b5:
            int r14 = r14.getChildCount()     // Catch:{ all -> 0x02f4 }
            int r14 = r14 - r3
            r15 = 8
            r6 = -1
            if (r6 > r14) goto L_0x01f3
            r17 = r4
            r18 = r17
            r16 = 0
        L_0x00c5:
            int r3 = r14 + -1
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r4 = r0.parent     // Catch:{ all -> 0x02f4 }
            if (r4 != 0) goto L_0x00cc
            r4 = 0
        L_0x00cc:
            android.view.View r4 = r4.getChildAt(r14)     // Catch:{ all -> 0x02f4 }
            if (r4 != 0) goto L_0x00d4
            goto L_0x0141
        L_0x00d4:
            r0.logShadeChild(r14, r4)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.MediaContainerView r13 = r20.getMediaControlsView()     // Catch:{ all -> 0x02f4 }
            if (r4 != r13) goto L_0x00df
            r13 = r7
            goto L_0x0102
        L_0x00df:
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r13 = r0.incomingHeaderController     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r13 = r13.getHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r4 != r13) goto L_0x00e9
            r13 = r5
            goto L_0x0102
        L_0x00e9:
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r13 = r20.getPeopleHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r4 != r13) goto L_0x00f1
            r13 = r8
            goto L_0x0102
        L_0x00f1:
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r13 = r20.getAlertingHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r4 != r13) goto L_0x00f9
            r13 = r9
            goto L_0x0102
        L_0x00f9:
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r13 = r20.getSilentHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r4 != r13) goto L_0x0101
            r13 = r10
            goto L_0x0102
        L_0x0101:
            r13 = 0
        L_0x0102:
            if (r13 != 0) goto L_0x0105
            goto L_0x0141
        L_0x0105:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x02f4 }
            r13.setCurrentPosition(r12)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$updateSectionBoundaries$1$1$1$1 r12 = new com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$updateSectionBoundaries$1$1$1$1     // Catch:{ all -> 0x02f4 }
            r12.<init>(r13)     // Catch:{ all -> 0x02f4 }
            kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 r12 = com.android.systemui.util.ConvenienceExtensionsKt.takeUntil(r11, r12)     // Catch:{ all -> 0x02f4 }
            java.util.Iterator r12 = r12.iterator()     // Catch:{ all -> 0x02f4 }
        L_0x0119:
            r13 = r12
            kotlin.sequences.SequenceBuilderIterator r13 = (kotlin.sequences.SequenceBuilderIterator) r13     // Catch:{ all -> 0x02f4 }
            boolean r19 = r13.hasNext()     // Catch:{ all -> 0x02f4 }
            if (r19 == 0) goto L_0x0141
            java.lang.Object r13 = r13.next()     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$SectionUpdateState r13 = (com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState) r13     // Catch:{ all -> 0x02f4 }
            java.lang.Integer r19 = r13.getTargetPosition()     // Catch:{ all -> 0x02f4 }
            if (r19 != 0) goto L_0x0130
            r6 = 0
            goto L_0x013c
        L_0x0130:
            int r19 = r19.intValue()     // Catch:{ all -> 0x02f4 }
            int r19 = r19 + -1
            java.lang.Integer r19 = java.lang.Integer.valueOf(r19)     // Catch:{ all -> 0x02f4 }
            r6 = r19
        L_0x013c:
            r13.setTargetPosition(r6)     // Catch:{ all -> 0x02f4 }
            r6 = -1
            goto L_0x0119
        L_0x0141:
            boolean r6 = r4 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow     // Catch:{ all -> 0x02f4 }
            if (r6 == 0) goto L_0x0149
            r6 = r4
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6     // Catch:{ all -> 0x02f4 }
            goto L_0x014a
        L_0x0149:
            r6 = 0
        L_0x014a:
            if (r6 != 0) goto L_0x014d
            goto L_0x0159
        L_0x014d:
            int r12 = r6.getVisibility()     // Catch:{ all -> 0x02f4 }
            if (r12 != r15) goto L_0x0155
            r12 = 1
            goto L_0x0156
        L_0x0155:
            r12 = 0
        L_0x0156:
            if (r12 != 0) goto L_0x0159
            goto L_0x015a
        L_0x0159:
            r6 = 0
        L_0x015a:
            if (r17 != 0) goto L_0x0184
            if (r16 != 0) goto L_0x0160
            r12 = 0
            goto L_0x017e
        L_0x0160:
            int r12 = r16.intValue()     // Catch:{ all -> 0x02f4 }
            if (r6 != 0) goto L_0x0167
            goto L_0x016b
        L_0x0167:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r13 = r6.mEntry     // Catch:{ all -> 0x02f4 }
            if (r13 != 0) goto L_0x016d
        L_0x016b:
            r12 = 0
            goto L_0x0178
        L_0x016d:
            int r13 = r13.mBucket     // Catch:{ all -> 0x02f4 }
            if (r12 >= r13) goto L_0x0173
            r12 = 1
            goto L_0x0174
        L_0x0173:
            r12 = 0
        L_0x0174:
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x02f4 }
        L_0x0178:
            java.lang.Boolean r13 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x02f4 }
            boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r13)     // Catch:{ all -> 0x02f4 }
        L_0x017e:
            if (r12 == 0) goto L_0x0181
            goto L_0x0184
        L_0x0181:
            r17 = 0
            goto L_0x0186
        L_0x0184:
            r17 = 1
        L_0x0186:
            if (r17 == 0) goto L_0x0194
            if (r6 != 0) goto L_0x018c
            r12 = 0
            goto L_0x018e
        L_0x018c:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r12 = r6.mEntry     // Catch:{ all -> 0x02f4 }
        L_0x018e:
            if (r12 != 0) goto L_0x0191
            goto L_0x0194
        L_0x0191:
            r13 = 2
            r12.mBucket = r13     // Catch:{ all -> 0x02f4 }
        L_0x0194:
            if (r16 == 0) goto L_0x01a9
            if (r4 == 0) goto L_0x01a7
            if (r6 == 0) goto L_0x01a9
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r6.mEntry     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r4)     // Catch:{ all -> 0x02f4 }
            int r4 = r4.mBucket     // Catch:{ all -> 0x02f4 }
            int r12 = r16.intValue()     // Catch:{ all -> 0x02f4 }
            if (r12 == r4) goto L_0x01a9
        L_0x01a7:
            r4 = 1
            goto L_0x01aa
        L_0x01a9:
            r4 = 0
        L_0x01aa:
            if (r4 == 0) goto L_0x01c4
            if (r1 == 0) goto L_0x01c4
            r4 = 6
            if (r16 != 0) goto L_0x01b2
            goto L_0x01c4
        L_0x01b2:
            int r12 = r16.intValue()     // Catch:{ all -> 0x02f4 }
            if (r12 != r4) goto L_0x01c4
            if (r10 != 0) goto L_0x01bb
            goto L_0x01c4
        L_0x01bb:
            int r14 = r14 + 1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x02f4 }
            r10.setTargetPosition(r4)     // Catch:{ all -> 0x02f4 }
        L_0x01c4:
            if (r6 != 0) goto L_0x01c9
            r4 = -1
            r12 = 4
            goto L_0x01e8
        L_0x01c9:
            if (r18 != 0) goto L_0x01d9
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r6.mEntry     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r4)     // Catch:{ all -> 0x02f4 }
            int r4 = r4.mBucket     // Catch:{ all -> 0x02f4 }
            r12 = 4
            if (r4 != r12) goto L_0x01d6
            goto L_0x01da
        L_0x01d6:
            r18 = 0
            goto L_0x01dc
        L_0x01d9:
            r12 = 4
        L_0x01da:
            r18 = 1
        L_0x01dc:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r6.mEntry     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r4)     // Catch:{ all -> 0x02f4 }
            int r4 = r4.mBucket     // Catch:{ all -> 0x02f4 }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x02f4 }
            r4 = -1
        L_0x01e8:
            if (r4 <= r3) goto L_0x01eb
            goto L_0x01f4
        L_0x01eb:
            r14 = r3
            r6 = r4
            r13 = r12
            r3 = 1
            r4 = 0
            r12 = 2
            goto L_0x00c5
        L_0x01f3:
            r4 = r6
        L_0x01f4:
            if (r7 != 0) goto L_0x01f8
            r1 = 0
            goto L_0x0204
        L_0x01f8:
            if (r2 == 0) goto L_0x0200
            r1 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x02f4 }
            goto L_0x0202
        L_0x0200:
            r1 = 0
            r2 = 0
        L_0x0202:
            r7.targetPosition = r2     // Catch:{ all -> 0x02f4 }
        L_0x0204:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            java.lang.String r3 = "New header target positions:"
            r2.logStr(r3)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            if (r7 != 0) goto L_0x0210
            goto L_0x0214
        L_0x0210:
            java.lang.Integer r3 = r7.targetPosition     // Catch:{ all -> 0x02f4 }
            if (r3 != 0) goto L_0x0216
        L_0x0214:
            r3 = r4
            goto L_0x021a
        L_0x0216:
            int r3 = r3.intValue()     // Catch:{ all -> 0x02f4 }
        L_0x021a:
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            java.lang.String r6 = "MEDIA CONTROLS"
            r2.logPosition(r3, r6)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            if (r5 != 0) goto L_0x0227
            goto L_0x022d
        L_0x0227:
            java.lang.Integer r3 = r5.getTargetPosition()     // Catch:{ all -> 0x02f4 }
            if (r3 != 0) goto L_0x022f
        L_0x022d:
            r3 = r4
            goto L_0x0233
        L_0x022f:
            int r3 = r3.intValue()     // Catch:{ all -> 0x02f4 }
        L_0x0233:
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            java.lang.String r5 = "INCOMING HEADER"
            r2.logPosition(r3, r5)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            if (r8 != 0) goto L_0x0240
            goto L_0x0246
        L_0x0240:
            java.lang.Integer r3 = r8.getTargetPosition()     // Catch:{ all -> 0x02f4 }
            if (r3 != 0) goto L_0x0248
        L_0x0246:
            r3 = r4
            goto L_0x024c
        L_0x0248:
            int r3 = r3.intValue()     // Catch:{ all -> 0x02f4 }
        L_0x024c:
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            java.lang.String r5 = "CONVERSATIONS HEADER"
            r2.logPosition(r3, r5)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            if (r9 != 0) goto L_0x0259
            goto L_0x025f
        L_0x0259:
            java.lang.Integer r3 = r9.getTargetPosition()     // Catch:{ all -> 0x02f4 }
            if (r3 != 0) goto L_0x0261
        L_0x025f:
            r3 = r4
            goto L_0x0265
        L_0x0261:
            int r3 = r3.intValue()     // Catch:{ all -> 0x02f4 }
        L_0x0265:
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            java.lang.String r5 = "ALERTING HEADER"
            r2.logPosition(r3, r5)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            if (r10 != 0) goto L_0x0272
            goto L_0x0278
        L_0x0272:
            java.lang.Integer r3 = r10.getTargetPosition()     // Catch:{ all -> 0x02f4 }
            if (r3 != 0) goto L_0x027a
        L_0x0278:
            r6 = r4
            goto L_0x027e
        L_0x027a:
            int r6 = r3.intValue()     // Catch:{ all -> 0x02f4 }
        L_0x027e:
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x02f4 }
            java.lang.String r3 = "SILENT HEADER"
            r2.logPosition(r6, r3)     // Catch:{ all -> 0x02f4 }
            kotlin.sequences.SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 r2 = new kotlin.sequences.SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1     // Catch:{ all -> 0x02f4 }
            r2.<init>(r11)     // Catch:{ all -> 0x02f4 }
            boolean r3 = r2 instanceof java.util.Collection     // Catch:{ all -> 0x02f4 }
            if (r3 == 0) goto L_0x029e
            r3 = r2
            java.util.Collection r3 = (java.util.Collection) r3     // Catch:{ all -> 0x02f4 }
            int r3 = r3.size()     // Catch:{ all -> 0x02f4 }
            r4 = 1
            if (r3 > r4) goto L_0x029e
            java.util.List r2 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r2)     // Catch:{ all -> 0x02f4 }
            goto L_0x02a5
        L_0x029e:
            java.util.List r2 = kotlin.collections.CollectionsKt___CollectionsKt.toMutableList(r2)     // Catch:{ all -> 0x02f4 }
            java.util.Collections.reverse(r2)     // Catch:{ all -> 0x02f4 }
        L_0x02a5:
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x02f4 }
        L_0x02a9:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x02f4 }
            if (r3 == 0) goto L_0x02b9
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$SectionUpdateState r3 = (com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState) r3     // Catch:{ all -> 0x02f4 }
            r3.adjustViewPosition()     // Catch:{ all -> 0x02f4 }
            goto L_0x02a9
        L_0x02b9:
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            java.lang.String r3 = "Final order:"
            r2.logStr(r3)     // Catch:{ all -> 0x02f4 }
            r20.logShadeContents()     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger r2 = r0.logger     // Catch:{ all -> 0x02f4 }
            java.lang.String r3 = "Section boundary update complete"
            r2.logStr(r3)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r2 = r20.getSilentHeaderView()     // Catch:{ all -> 0x02f4 }
            if (r2 != 0) goto L_0x02d1
            goto L_0x02f0
        L_0x02d1:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.parent     // Catch:{ all -> 0x02f4 }
            if (r0 != 0) goto L_0x02d7
            r6 = 0
            goto L_0x02d8
        L_0x02d7:
            r6 = r0
        L_0x02d8:
            java.util.Objects.requireNonNull(r6)     // Catch:{ all -> 0x02f4 }
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r6.mController     // Catch:{ all -> 0x02f4 }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x02f4 }
            r3 = 2
            r4 = 1
            boolean r0 = r0.hasNotifications(r3, r4)     // Catch:{ all -> 0x02f4 }
            android.widget.ImageView r2 = r2.mClearAllButton     // Catch:{ all -> 0x02f4 }
            if (r0 == 0) goto L_0x02ec
            r4 = r1
            goto L_0x02ed
        L_0x02ec:
            r4 = r15
        L_0x02ed:
            r2.setVisibility(r4)     // Catch:{ all -> 0x02f4 }
        L_0x02f0:
            android.os.Trace.endSection()
            return
        L_0x02f4:
            r0 = move-exception
            android.os.Trace.endSection()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.updateSectionBoundaries(java.lang.String):void");
    }

    public NotificationSectionsManager(StatusBarStateController statusBarStateController2, ConfigurationController configurationController2, KeyguardMediaController keyguardMediaController2, NotificationSectionsFeatureManager notificationSectionsFeatureManager, NotificationSectionsLogger notificationSectionsLogger, NotifPipelineFlags notifPipelineFlags2, MediaContainerController mediaContainerController2, SectionHeaderController sectionHeaderController, SectionHeaderController sectionHeaderController2, SectionHeaderController sectionHeaderController3, SectionHeaderController sectionHeaderController4) {
        this.statusBarStateController = statusBarStateController2;
        this.configurationController = configurationController2;
        this.keyguardMediaController = keyguardMediaController2;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.logger = notificationSectionsLogger;
        this.notifPipelineFlags = notifPipelineFlags2;
        this.mediaContainerController = mediaContainerController2;
        this.incomingHeaderController = sectionHeaderController;
        this.peopleHeaderController = sectionHeaderController2;
        this.alertingHeaderController = sectionHeaderController3;
        this.silentHeaderController = sectionHeaderController4;
    }

    public final boolean beginsSection(View view, View view2) {
        if (view == getSilentHeaderView() || view == getMediaControlsView() || view == getPeopleHeaderView() || view == getAlertingHeaderView() || view == this.incomingHeaderController.getHeaderView() || !Intrinsics.areEqual(getBucket(view), getBucket(view2))) {
            return true;
        }
        return false;
    }

    public final Integer getBucket(View view) {
        if (view == getSilentHeaderView()) {
            return 6;
        }
        if (view == this.incomingHeaderController.getHeaderView()) {
            return 2;
        }
        if (view == getMediaControlsView()) {
            return 1;
        }
        if (view == getPeopleHeaderView()) {
            return 4;
        }
        if (view == getAlertingHeaderView()) {
            return 5;
        }
        if (!(view instanceof ExpandableNotificationRow)) {
            return null;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        return Integer.valueOf(notificationEntry.mBucket);
    }
}
