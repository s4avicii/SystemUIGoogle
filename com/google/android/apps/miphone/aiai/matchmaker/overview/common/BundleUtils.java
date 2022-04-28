package com.google.android.apps.miphone.aiai.matchmaker.overview.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$AppActionSuggestion;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$AppIcon;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$AppIconType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$AppPackage;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$ContentGroup;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$Contents;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$ExecutionInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$SearchSuggestion;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$Selection;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$ContentRect;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$InteractionType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$SetupInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Stats;
import com.google.android.setupcompat.partnerconfig.ResourceEntry;
import java.util.ArrayList;
import java.util.Iterator;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;

public final class BundleUtils {
    public static Bundle createClassificationsRequest(String str, String str2, int i, long j, @Nullable Bundle bundle, @Nullable InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext, ContentParcelables$Contents contentParcelables$Contents) {
        String str3;
        Bundle bundle2;
        Bundle bundle3;
        String str4;
        String str5;
        Iterator it;
        Bundle bundle4;
        Iterator it2;
        String str6;
        String str7;
        Bundle bundle5;
        ContentParcelables$Contents contentParcelables$Contents2 = contentParcelables$Contents;
        Bundle bundle6 = new Bundle();
        bundle6.putString("PackageName", str);
        bundle6.putString("ActivityName", str2);
        bundle6.putInt("TaskId", i);
        bundle6.putLong("CaptureTimestampMs", j);
        bundle6.putBundle("AssistBundle", bundle);
        Bundle bundle7 = new Bundle();
        String str8 = "id";
        bundle7.putString(str8, contentParcelables$Contents2.f125id);
        bundle7.putLong("screenSessionId", contentParcelables$Contents2.screenSessionId);
        String str9 = "opaquePayload";
        ArrayList arrayList = null;
        if (contentParcelables$Contents2.contentGroups == null) {
            bundle7.putParcelableArrayList("contentGroups", (ArrayList) null);
            bundle2 = bundle6;
            str3 = str9;
        } else {
            ArrayList arrayList2 = new ArrayList(contentParcelables$Contents2.contentGroups.size());
            Iterator it3 = contentParcelables$Contents2.contentGroups.iterator();
            while (it3.hasNext()) {
                ContentParcelables$ContentGroup contentParcelables$ContentGroup = (ContentParcelables$ContentGroup) it3.next();
                if (contentParcelables$ContentGroup == null) {
                    arrayList2.add(arrayList);
                    bundle4 = bundle6;
                    str5 = str8;
                    str4 = str9;
                    it = it3;
                } else {
                    Bundle bundle8 = new Bundle();
                    if (contentParcelables$ContentGroup.contentRects == null) {
                        bundle8.putParcelableArrayList("contentRects", arrayList);
                    } else {
                        ArrayList arrayList3 = new ArrayList(contentParcelables$ContentGroup.contentRects.size());
                        Iterator it4 = contentParcelables$ContentGroup.contentRects.iterator();
                        while (it4.hasNext()) {
                            SuggestParcelables$ContentRect suggestParcelables$ContentRect = (SuggestParcelables$ContentRect) it4.next();
                            if (suggestParcelables$ContentRect == null) {
                                arrayList3.add(arrayList);
                            } else {
                                arrayList3.add(suggestParcelables$ContentRect.writeToBundle());
                            }
                        }
                        bundle8.putParcelableArrayList("contentRects", arrayList3);
                    }
                    if (contentParcelables$ContentGroup.selections == null) {
                        bundle8.putParcelableArrayList("selections", arrayList);
                        bundle4 = bundle6;
                        it = it3;
                    } else {
                        ArrayList arrayList4 = new ArrayList(contentParcelables$ContentGroup.selections.size());
                        Iterator it5 = contentParcelables$ContentGroup.selections.iterator();
                        while (it5.hasNext()) {
                            ContentParcelables$Selection contentParcelables$Selection = (ContentParcelables$Selection) it5.next();
                            if (contentParcelables$Selection == null) {
                                arrayList4.add(arrayList);
                            } else {
                                Bundle bundle9 = new Bundle();
                                Iterator it6 = it3;
                                Iterator it7 = it5;
                                if (contentParcelables$Selection.rectIndices == null) {
                                    bundle9.putIntegerArrayList("rectIndices", (ArrayList) null);
                                    bundle5 = bundle6;
                                } else {
                                    bundle5 = bundle6;
                                    bundle9.putIntegerArrayList("rectIndices", new ArrayList(contentParcelables$Selection.rectIndices));
                                }
                                bundle9.putString(str8, contentParcelables$Selection.f126id);
                                bundle9.putBoolean("isSmartSelection", contentParcelables$Selection.isSmartSelection);
                                bundle9.putInt("suggestedPresentationMode", contentParcelables$Selection.suggestedPresentationMode);
                                bundle9.putString(str9, contentParcelables$Selection.opaquePayload);
                                SuggestParcelables$InteractionType suggestParcelables$InteractionType = contentParcelables$Selection.interactionType;
                                if (suggestParcelables$InteractionType == null) {
                                    bundle9.putBundle("interactionType", (Bundle) null);
                                } else {
                                    Bundle bundle10 = new Bundle();
                                    bundle10.putInt("value", suggestParcelables$InteractionType.value);
                                    bundle9.putBundle("interactionType", bundle10);
                                }
                                bundle9.putInt("contentGroupIndex", contentParcelables$Selection.contentGroupIndex);
                                arrayList4.add(bundle9);
                                it3 = it6;
                                it5 = it7;
                                bundle6 = bundle5;
                                arrayList = null;
                            }
                        }
                        bundle4 = bundle6;
                        it = it3;
                        bundle8.putParcelableArrayList("selections", arrayList4);
                    }
                    bundle8.putString("text", contentParcelables$ContentGroup.text);
                    bundle8.putInt("numLines", contentParcelables$ContentGroup.numLines);
                    if (contentParcelables$ContentGroup.searchSuggestions == null) {
                        bundle8.putParcelableArrayList("searchSuggestions", (ArrayList) null);
                        str5 = str8;
                        str4 = str9;
                    } else {
                        ArrayList arrayList5 = new ArrayList(contentParcelables$ContentGroup.searchSuggestions.size());
                        Iterator it8 = contentParcelables$ContentGroup.searchSuggestions.iterator();
                        while (it8.hasNext()) {
                            ContentParcelables$SearchSuggestion contentParcelables$SearchSuggestion = (ContentParcelables$SearchSuggestion) it8.next();
                            if (contentParcelables$SearchSuggestion == null) {
                                arrayList5.add((Object) null);
                            } else {
                                Bundle bundle11 = new Bundle();
                                ContentParcelables$AppActionSuggestion contentParcelables$AppActionSuggestion = contentParcelables$SearchSuggestion.appActionSuggestion;
                                if (contentParcelables$AppActionSuggestion == null) {
                                    bundle11.putBundle("appActionSuggestion", (Bundle) null);
                                    str6 = str8;
                                    it2 = it8;
                                } else {
                                    Bundle bundle12 = new Bundle();
                                    str6 = str8;
                                    it2 = it8;
                                    bundle12.putString("displayText", contentParcelables$AppActionSuggestion.displayText);
                                    bundle12.putString("subtitle", contentParcelables$AppActionSuggestion.subtitle);
                                    bundle11.putBundle("appActionSuggestion", bundle12);
                                }
                                ContentParcelables$AppIcon contentParcelables$AppIcon = contentParcelables$SearchSuggestion.appIcon;
                                if (contentParcelables$AppIcon == null) {
                                    bundle11.putBundle("appIcon", (Bundle) null);
                                    str7 = str9;
                                } else {
                                    Bundle bundle13 = new Bundle();
                                    bundle13.putString("iconUri", contentParcelables$AppIcon.iconUri);
                                    ContentParcelables$AppPackage contentParcelables$AppPackage = contentParcelables$AppIcon.appPackage;
                                    if (contentParcelables$AppPackage == null) {
                                        str7 = str9;
                                        bundle13.putBundle("appPackage", (Bundle) null);
                                    } else {
                                        str7 = str9;
                                        Bundle bundle14 = new Bundle();
                                        bundle14.putString(ResourceEntry.KEY_PACKAGE_NAME, contentParcelables$AppPackage.packageName);
                                        bundle13.putBundle("appPackage", bundle14);
                                    }
                                    ContentParcelables$AppIconType contentParcelables$AppIconType = contentParcelables$AppIcon.appIconType;
                                    if (contentParcelables$AppIconType == null) {
                                        bundle13.putBundle("appIconType", (Bundle) null);
                                    } else {
                                        Bundle bundle15 = new Bundle();
                                        bundle15.putInt("value", contentParcelables$AppIconType.value);
                                        bundle13.putBundle("appIconType", bundle15);
                                    }
                                    bundle11.putBundle("appIcon", bundle13);
                                }
                                ContentParcelables$ExecutionInfo contentParcelables$ExecutionInfo = contentParcelables$SearchSuggestion.executionInfo;
                                if (contentParcelables$ExecutionInfo == null) {
                                    bundle11.putBundle("executionInfo", (Bundle) null);
                                } else {
                                    Bundle bundle16 = new Bundle();
                                    bundle16.putString("deeplinkUri", contentParcelables$ExecutionInfo.deeplinkUri);
                                    bundle11.putBundle("executionInfo", bundle16);
                                }
                                bundle11.putFloat("confScore", contentParcelables$SearchSuggestion.confScore);
                                arrayList5.add(bundle11);
                                str8 = str6;
                                it8 = it2;
                                str9 = str7;
                                ContentParcelables$Contents contentParcelables$Contents3 = contentParcelables$Contents;
                            }
                        }
                        str5 = str8;
                        str4 = str9;
                        bundle8.putParcelableArrayList("searchSuggestions", arrayList5);
                    }
                    arrayList2.add(bundle8);
                }
                it3 = it;
                str8 = str5;
                str9 = str4;
                ContentParcelables$Contents contentParcelables$Contents4 = contentParcelables$Contents;
                bundle6 = bundle4;
                arrayList = null;
            }
            bundle2 = bundle6;
            str3 = str9;
            bundle7.putParcelableArrayList("contentGroups", arrayList2);
            contentParcelables$Contents2 = contentParcelables$Contents;
        }
        SuggestParcelables$Stats suggestParcelables$Stats = contentParcelables$Contents2.stats;
        if (suggestParcelables$Stats == null) {
            bundle3 = null;
            bundle7.putBundle("stats", (Bundle) null);
        } else {
            bundle3 = null;
            bundle7.putBundle("stats", suggestParcelables$Stats.writeToBundle());
        }
        if (contentParcelables$Contents2.debugInfo == null) {
            bundle7.putBundle("debugInfo", bundle3);
        } else {
            bundle7.putBundle("debugInfo", new Bundle());
        }
        bundle7.putString(str3, contentParcelables$Contents2.opaquePayload);
        SuggestParcelables$SetupInfo suggestParcelables$SetupInfo = contentParcelables$Contents2.setupInfo;
        if (suggestParcelables$SetupInfo == null) {
            bundle7.putBundle("setupInfo", bundle3);
        } else {
            bundle7.putBundle("setupInfo", suggestParcelables$SetupInfo.writeToBundle());
        }
        bundle7.putString("contentUri", contentParcelables$Contents2.contentUri);
        Bundle bundle17 = bundle2;
        bundle17.putBundle("Contents", bundle7);
        if (interactionContextParcelables$InteractionContext == null) {
            bundle17.putBundle("InteractionContext", (Bundle) null);
        } else {
            bundle17.putBundle("InteractionContext", interactionContextParcelables$InteractionContext.writeToBundle());
        }
        bundle17.putInt("Version", 4);
        bundle17.putInt("BundleTypedVersion", 3);
        return bundle17;
    }

    /* JADX WARNING: type inference failed for: r0v18, types: [android.os.Bundle, java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: type inference failed for: r0v24 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x011b  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x01ba  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x02cd  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x02fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.Bundle createFeedbackRequest(@android.support.annotation.Nullable com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch r22) {
        /*
            r0 = r22
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            r2 = 6
            r3 = 0
            java.lang.String r4 = "FeedbackBatch"
            if (r0 != 0) goto L_0x0013
            r1.putBundle(r4, r3)
            r0 = r1
            goto L_0x0349
        L_0x0013:
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            java.util.List<com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback> r6 = r0.feedback
            java.lang.String r7 = "overviewSessionId"
            java.lang.String r8 = "feedback"
            if (r6 != 0) goto L_0x002a
            r5.putParcelableArrayList(r8, r3)
            r17 = r1
            r18 = r4
            r2 = r5
            goto L_0x0335
        L_0x002a:
            java.util.ArrayList r6 = new java.util.ArrayList
            java.util.List<com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback> r9 = r0.feedback
            int r9 = r9.size()
            r6.<init>(r9)
            java.util.List<com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback> r9 = r0.feedback
            java.util.Iterator r9 = r9.iterator()
        L_0x003b:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x032a
            java.lang.Object r10 = r9.next()
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback r10 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$Feedback) r10
            if (r10 != 0) goto L_0x0058
            r6.add(r3)
            r17 = r1
            r0 = r3
            r18 = r4
            r19 = r5
            r1 = r6
            r16 = r9
            goto L_0x031b
        L_0x0058:
            android.os.Bundle r11 = new android.os.Bundle
            r11.<init>()
            java.lang.Object r12 = r10.feedback
            boolean r12 = r12 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$OverviewFeedback
            java.lang.String r13 = "taskSnapshotSessionId"
            java.lang.String r14 = "userInteraction"
            java.lang.String r15 = "feedback#tag"
            r3 = 0
            if (r12 == 0) goto L_0x00a7
            r11.putInt(r15, r2)
            java.lang.Object r12 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$OverviewFeedback r12 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$OverviewFeedback) r12
            if (r12 != 0) goto L_0x007a
            r12 = 0
            r11.putBundle(r8, r12)
            goto L_0x00a7
        L_0x007a:
            r12 = 0
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            r2.putBundle(r14, r12)
            java.lang.String r12 = "overviewPresentationMode"
            r2.putInt(r12, r3)
            java.lang.String r12 = "numSelectionsSuggested"
            r2.putInt(r12, r3)
            java.lang.String r12 = "numSelectionsInitialized"
            r2.putInt(r12, r3)
            r12 = 0
            r2.putString(r7, r12)
            r2.putString(r13, r12)
            java.lang.String r3 = "primaryTaskAppComponentName"
            r2.putString(r3, r12)
            java.lang.String r3 = "taskAppComponentNameList"
            r2.putStringArrayList(r3, r12)
            r11.putBundle(r8, r2)
        L_0x00a7:
            java.lang.Object r2 = r10.feedback
            boolean r2 = r2 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$SelectionFeedback
            java.lang.String r3 = "interactionType"
            java.lang.String r12 = "selectionSessionId"
            r16 = r9
            java.lang.String r9 = "interactionSessionId"
            r17 = r1
            java.lang.String r1 = "selection"
            r18 = r4
            java.lang.String r4 = "selectedEntity"
            if (r2 == 0) goto L_0x010e
            r2 = 7
            r11.putInt(r15, r2)
            java.lang.Object r2 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$SelectionFeedback r2 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$SelectionFeedback) r2
            if (r2 != 0) goto L_0x00cf
            r2 = 0
            r11.putBundle(r8, r2)
            goto L_0x010e
        L_0x00cf:
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            java.lang.String r0 = "type"
            r19 = r5
            r5 = 0
            r2.putBundle(r0, r5)
            r2.putBundle(r4, r5)
            r2.putBundle(r1, r5)
            r2.putBundle(r14, r5)
            java.lang.String r0 = "selectionPresentationMode"
            r20 = r6
            r6 = 0
            r2.putInt(r0, r6)
            r2.putString(r7, r5)
            r2.putString(r13, r5)
            r2.putString(r9, r5)
            r2.putString(r12, r5)
            java.lang.String r0 = "screenContents"
            r2.putBundle(r0, r5)
            r2.putBundle(r3, r5)
            java.lang.String r0 = "selectionId"
            r2.putString(r0, r5)
            r11.putBundle(r8, r2)
            goto L_0x0112
        L_0x010e:
            r19 = r5
            r20 = r6
        L_0x0112:
            java.lang.Object r0 = r10.feedback
            boolean r0 = r0 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ActionFeedback
            java.lang.String r2 = "selectionType"
            if (r0 == 0) goto L_0x0172
            r0 = 8
            r11.putInt(r15, r0)
            java.lang.Object r0 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ActionFeedback r0 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ActionFeedback) r0
            if (r0 != 0) goto L_0x012b
            r0 = 0
            r11.putBundle(r8, r0)
            goto L_0x0172
        L_0x012b:
            r0 = 0
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            r5.putBundle(r2, r0)
            r5.putBundle(r4, r0)
            java.lang.String r6 = "actionShown"
            r5.putParcelableArrayList(r6, r0)
            java.lang.String r6 = "invokedAction"
            r5.putBundle(r6, r0)
            r5.putBundle(r14, r0)
            java.lang.String r6 = "actionPresentationMode"
            r21 = r14
            r14 = 0
            r5.putInt(r6, r14)
            r5.putBundle(r1, r0)
            r5.putString(r7, r0)
            r5.putString(r13, r0)
            r5.putString(r9, r0)
            r5.putString(r12, r0)
            java.lang.String r6 = "verticalTypeName"
            r5.putString(r6, r0)
            java.lang.String r6 = "actionMenuItems"
            r5.putParcelableArrayList(r6, r0)
            java.lang.String r6 = "invokedActionMenuItem"
            r5.putBundle(r6, r0)
            r5.putBundle(r3, r0)
            r11.putBundle(r8, r5)
            goto L_0x0174
        L_0x0172:
            r21 = r14
        L_0x0174:
            java.lang.Object r0 = r10.feedback
            boolean r0 = r0 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ActionGroupFeedback
            if (r0 == 0) goto L_0x01b2
            r0 = 9
            r11.putInt(r15, r0)
            java.lang.Object r0 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ActionGroupFeedback r0 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ActionGroupFeedback) r0
            if (r0 != 0) goto L_0x018a
            r0 = 0
            r11.putBundle(r8, r0)
            goto L_0x01b2
        L_0x018a:
            r0 = 0
            android.os.Bundle r3 = new android.os.Bundle
            r3.<init>()
            r3.putBundle(r2, r0)
            r3.putBundle(r4, r0)
            java.lang.String r2 = "actionGroupShown"
            r3.putParcelableArrayList(r2, r0)
            java.lang.String r2 = "actionGroup"
            r3.putBundle(r2, r0)
            r2 = r21
            r3.putBundle(r2, r0)
            java.lang.String r4 = "actionGroupPresentationMode"
            r5 = 0
            r3.putInt(r4, r5)
            r3.putBundle(r1, r0)
            r11.putBundle(r8, r3)
            goto L_0x01b4
        L_0x01b2:
            r2 = r21
        L_0x01b4:
            java.lang.Object r0 = r10.feedback
            boolean r0 = r0 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$TaskSnapshotFeedback
            if (r0 == 0) goto L_0x01e5
            r0 = 10
            r11.putInt(r15, r0)
            java.lang.Object r0 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$TaskSnapshotFeedback r0 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$TaskSnapshotFeedback) r0
            if (r0 != 0) goto L_0x01ca
            r0 = 0
            r11.putBundle(r8, r0)
            goto L_0x01e5
        L_0x01ca:
            r0 = 0
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            r1.putBundle(r2, r0)
            r1.putString(r7, r0)
            r1.putString(r13, r0)
            java.lang.String r2 = "taskAppComponentName"
            r1.putString(r2, r0)
            r1.putString(r9, r0)
            r11.putBundle(r8, r1)
        L_0x01e5:
            java.lang.Object r0 = r10.feedback
            boolean r0 = r0 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotFeedback
            if (r0 == 0) goto L_0x02c7
            r0 = 11
            r11.putInt(r15, r0)
            java.lang.Object r0 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotFeedback r0 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotFeedback) r0
            if (r0 != 0) goto L_0x01fc
            r1 = 0
            r11.putBundle(r8, r1)
            goto L_0x02c7
        L_0x01fc:
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.Object r2 = r0.screenshotFeedback
            boolean r2 = r2 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpFeedback
            java.lang.String r3 = "screenshotFeedback#tag"
            java.lang.String r4 = "screenshotFeedback"
            if (r2 == 0) goto L_0x025e
            r2 = 2
            r1.putInt(r3, r2)
            java.lang.Object r2 = r0.screenshotFeedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpFeedback r2 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpFeedback) r2
            if (r2 != 0) goto L_0x021c
            r5 = 0
            r1.putBundle(r4, r5)
            goto L_0x025e
        L_0x021c:
            r5 = 0
            android.os.Bundle r6 = new android.os.Bundle
            r6.<init>()
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOp r9 = r2.f127op
            java.lang.String r12 = "value"
            java.lang.String r13 = "op"
            if (r9 != 0) goto L_0x022f
            r6.putBundle(r13, r5)
            goto L_0x023c
        L_0x022f:
            android.os.Bundle r14 = new android.os.Bundle
            r14.<init>()
            int r9 = r9.value
            r14.putInt(r12, r9)
            r6.putBundle(r13, r14)
        L_0x023c:
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpStatus r9 = r2.status
            java.lang.String r13 = "status"
            if (r9 != 0) goto L_0x0247
            r6.putBundle(r13, r5)
            goto L_0x0254
        L_0x0247:
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            int r9 = r9.value
            r5.putInt(r12, r9)
            r6.putBundle(r13, r5)
        L_0x0254:
            long r12 = r2.durationMs
            java.lang.String r2 = "durationMs"
            r6.putLong(r2, r12)
            r1.putBundle(r4, r6)
        L_0x025e:
            java.lang.Object r2 = r0.screenshotFeedback
            boolean r2 = r2 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotActionFeedback
            if (r2 == 0) goto L_0x02bc
            r2 = 3
            r1.putInt(r3, r2)
            java.lang.Object r2 = r0.screenshotFeedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotActionFeedback r2 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotActionFeedback) r2
            if (r2 != 0) goto L_0x0273
            r3 = 0
            r1.putBundle(r4, r3)
            goto L_0x02bc
        L_0x0273:
            android.os.Bundle r3 = new android.os.Bundle
            r3.<init>()
            java.lang.String r5 = r2.actionType
            java.lang.String r6 = "actionType"
            r3.putString(r6, r5)
            boolean r5 = r2.isSmartActions
            java.lang.String r6 = "isSmartActions"
            r3.putBoolean(r6, r5)
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$QuickShareInfo r2 = r2.quickShareInfo
            java.lang.String r5 = "quickShareInfo"
            if (r2 != 0) goto L_0x0292
            r6 = 0
            r3.putBundle(r5, r6)
            goto L_0x02b9
        L_0x0292:
            android.os.Bundle r6 = new android.os.Bundle
            r6.<init>()
            java.lang.String r9 = r2.contentUri
            java.lang.String r12 = "contentUri"
            r6.putString(r12, r9)
            java.lang.String r9 = r2.targetPackageName
            java.lang.String r12 = "targetPackageName"
            r6.putString(r12, r9)
            java.lang.String r9 = r2.targetClassName
            java.lang.String r12 = "targetClassName"
            r6.putString(r12, r9)
            java.lang.String r2 = r2.targetShortcutId
            java.lang.String r9 = "targetShortcutId"
            r6.putString(r9, r2)
            r3.putBundle(r5, r6)
        L_0x02b9:
            r1.putBundle(r4, r3)
        L_0x02bc:
            java.lang.String r0 = r0.screenshotId
            java.lang.String r2 = "screenshotId"
            r1.putString(r2, r0)
            r11.putBundle(r8, r1)
        L_0x02c7:
            java.lang.Object r0 = r10.feedback
            boolean r0 = r0 instanceof com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ComposeFeedback
            if (r0 == 0) goto L_0x02fd
            r0 = 12
            r11.putInt(r15, r0)
            java.lang.Object r0 = r10.feedback
            com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ComposeFeedback r0 = (com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ComposeFeedback) r0
            if (r0 != 0) goto L_0x02dd
            r0 = 0
            r11.putBundle(r8, r0)
            goto L_0x02fe
        L_0x02dd:
            r0 = 0
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r2 = "feedbackType"
            r1.putBundle(r2, r0)
            java.lang.String r2 = "itemId"
            r3 = 0
            r1.putInt(r2, r3)
            java.lang.String r2 = "text"
            r1.putString(r2, r0)
            java.lang.String r2 = "actionFeedback"
            r1.putParcelableArrayList(r2, r0)
            r11.putBundle(r8, r1)
            goto L_0x02fe
        L_0x02fd:
            r0 = 0
        L_0x02fe:
            java.lang.String r1 = "id"
            r11.putString(r1, r0)
            r1 = 0
            java.lang.String r3 = "timestampMs"
            r11.putLong(r3, r1)
            java.lang.String r1 = "suggestionAction"
            r11.putBundle(r1, r0)
            java.lang.String r1 = "interactionContext"
            r11.putBundle(r1, r0)
            r1 = r20
            r1.add(r11)
        L_0x031b:
            r3 = r0
            r6 = r1
            r9 = r16
            r1 = r17
            r4 = r18
            r5 = r19
            r2 = 6
            r0 = r22
            goto L_0x003b
        L_0x032a:
            r17 = r1
            r18 = r4
            r2 = r5
            r1 = r6
            r2.putParcelableArrayList(r8, r1)
            r0 = r22
        L_0x0335:
            long r3 = r0.screenSessionId
            java.lang.String r1 = "screenSessionId"
            r2.putLong(r1, r3)
            java.lang.String r0 = r0.overviewSessionId
            r2.putString(r7, r0)
            r0 = r17
            r1 = r18
            r0.putBundle(r1, r2)
        L_0x0349:
            r1 = 4
            java.lang.String r2 = "Version"
            r0.putInt(r2, r1)
            java.lang.String r1 = "BundleTypedVersion"
            r2 = 6
            r0.putInt(r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils.createFeedbackRequest(com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch):android.os.Bundle");
    }

    public static Bundle createSelectionsRequest(String str, String str2, int i, long j, @Nullable InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext, @Nullable Bundle bundle, @Nullable LockFreeLinkedListKt lockFreeLinkedListKt) {
        Bundle bundle2 = new Bundle();
        bundle2.putString("PackageName", str);
        bundle2.putString("ActivityName", str2);
        bundle2.putInt("TaskId", i);
        bundle2.putLong("CaptureTimestampMs", j);
        if (interactionContextParcelables$InteractionContext == null) {
            bundle2.putBundle("InteractionContext", (Bundle) null);
        } else {
            bundle2.putBundle("InteractionContext", interactionContextParcelables$InteractionContext.writeToBundle());
        }
        bundle2.putBundle("AssistBundle", bundle);
        if (lockFreeLinkedListKt == null) {
            bundle2.putBundle("ParsedViewHierarchy", (Bundle) null);
        } else {
            Bundle bundle3 = new Bundle();
            bundle3.putLong("acquisitionStartTime", 0);
            bundle3.putLong("acquisitionEndTime", 0);
            bundle3.putBoolean("isHomeActivity", false);
            bundle3.putParcelableArrayList("windows", (ArrayList) null);
            bundle3.putBoolean("hasKnownIssues", false);
            bundle3.putString(ResourceEntry.KEY_PACKAGE_NAME, (String) null);
            bundle3.putString("activityClassName", (String) null);
            bundle3.putBundle("insetsRect", (Bundle) null);
            bundle2.putBundle("ParsedViewHierarchy", bundle3);
        }
        bundle2.putInt("Version", 4);
        bundle2.putInt("BundleTypedVersion", 3);
        return bundle2;
    }
}
