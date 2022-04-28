package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.google.android.setupcompat.logging.CustomEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

public final class ConstraintSet {
    public static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    public static SparseIntArray mapToConstant;
    public HashMap<Integer, Constraint> mConstraints = new HashMap<>();
    public boolean mForceId = true;
    public HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap<>();

    public static class Constraint {
        public final Layout layout = new Layout();
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<>();
        public int mViewId;
        public final Motion motion = new Motion();
        public final PropertySet propertySet = new PropertySet();
        public final Transform transform = new Transform();

        public final void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            Layout layout2 = this.layout;
            layoutParams.leftToLeft = layout2.leftToLeft;
            layoutParams.leftToRight = layout2.leftToRight;
            layoutParams.rightToLeft = layout2.rightToLeft;
            layoutParams.rightToRight = layout2.rightToRight;
            layoutParams.topToTop = layout2.topToTop;
            layoutParams.topToBottom = layout2.topToBottom;
            layoutParams.bottomToTop = layout2.bottomToTop;
            layoutParams.bottomToBottom = layout2.bottomToBottom;
            layoutParams.baselineToBaseline = layout2.baselineToBaseline;
            layoutParams.startToEnd = layout2.startToEnd;
            layoutParams.startToStart = layout2.startToStart;
            layoutParams.endToStart = layout2.endToStart;
            layoutParams.endToEnd = layout2.endToEnd;
            layoutParams.leftMargin = layout2.leftMargin;
            layoutParams.rightMargin = layout2.rightMargin;
            layoutParams.topMargin = layout2.topMargin;
            layoutParams.bottomMargin = layout2.bottomMargin;
            layoutParams.goneStartMargin = layout2.goneStartMargin;
            layoutParams.goneEndMargin = layout2.goneEndMargin;
            layoutParams.goneTopMargin = layout2.goneTopMargin;
            layoutParams.goneBottomMargin = layout2.goneBottomMargin;
            layoutParams.horizontalBias = layout2.horizontalBias;
            layoutParams.verticalBias = layout2.verticalBias;
            layoutParams.circleConstraint = layout2.circleConstraint;
            layoutParams.circleRadius = layout2.circleRadius;
            layoutParams.circleAngle = layout2.circleAngle;
            layoutParams.dimensionRatio = layout2.dimensionRatio;
            layoutParams.editorAbsoluteX = layout2.editorAbsoluteX;
            layoutParams.editorAbsoluteY = layout2.editorAbsoluteY;
            layoutParams.verticalWeight = layout2.verticalWeight;
            layoutParams.horizontalWeight = layout2.horizontalWeight;
            layoutParams.verticalChainStyle = layout2.verticalChainStyle;
            layoutParams.horizontalChainStyle = layout2.horizontalChainStyle;
            layoutParams.constrainedWidth = layout2.constrainedWidth;
            layoutParams.constrainedHeight = layout2.constrainedHeight;
            layoutParams.matchConstraintDefaultWidth = layout2.widthDefault;
            layoutParams.matchConstraintDefaultHeight = layout2.heightDefault;
            layoutParams.matchConstraintMaxWidth = layout2.widthMax;
            layoutParams.matchConstraintMaxHeight = layout2.heightMax;
            layoutParams.matchConstraintMinWidth = layout2.widthMin;
            layoutParams.matchConstraintMinHeight = layout2.heightMin;
            layoutParams.matchConstraintPercentWidth = layout2.widthPercent;
            layoutParams.matchConstraintPercentHeight = layout2.heightPercent;
            layoutParams.orientation = layout2.orientation;
            layoutParams.guidePercent = layout2.guidePercent;
            layoutParams.guideBegin = layout2.guideBegin;
            layoutParams.guideEnd = layout2.guideEnd;
            layoutParams.width = layout2.mWidth;
            layoutParams.height = layout2.mHeight;
            String str = layout2.mConstraintTag;
            if (str != null) {
                layoutParams.constraintTag = str;
            }
            layoutParams.setMarginStart(layout2.startMargin);
            layoutParams.setMarginEnd(this.layout.endMargin);
            layoutParams.validate();
        }

        public final Object clone() throws CloneNotSupportedException {
            Constraint constraint = new Constraint();
            constraint.layout.copyFrom(this.layout);
            constraint.motion.copyFrom(this.motion);
            PropertySet propertySet2 = constraint.propertySet;
            PropertySet propertySet3 = this.propertySet;
            Objects.requireNonNull(propertySet2);
            propertySet2.mApply = propertySet3.mApply;
            propertySet2.visibility = propertySet3.visibility;
            propertySet2.alpha = propertySet3.alpha;
            propertySet2.mProgress = propertySet3.mProgress;
            propertySet2.mVisibilityMode = propertySet3.mVisibilityMode;
            constraint.transform.copyFrom(this.transform);
            constraint.mViewId = this.mViewId;
            return constraint;
        }

        public final void fillFrom(int i, ConstraintLayout.LayoutParams layoutParams) {
            this.mViewId = i;
            Layout layout2 = this.layout;
            layout2.leftToLeft = layoutParams.leftToLeft;
            layout2.leftToRight = layoutParams.leftToRight;
            layout2.rightToLeft = layoutParams.rightToLeft;
            layout2.rightToRight = layoutParams.rightToRight;
            layout2.topToTop = layoutParams.topToTop;
            layout2.topToBottom = layoutParams.topToBottom;
            layout2.bottomToTop = layoutParams.bottomToTop;
            layout2.bottomToBottom = layoutParams.bottomToBottom;
            layout2.baselineToBaseline = layoutParams.baselineToBaseline;
            layout2.startToEnd = layoutParams.startToEnd;
            layout2.startToStart = layoutParams.startToStart;
            layout2.endToStart = layoutParams.endToStart;
            layout2.endToEnd = layoutParams.endToEnd;
            layout2.horizontalBias = layoutParams.horizontalBias;
            layout2.verticalBias = layoutParams.verticalBias;
            layout2.dimensionRatio = layoutParams.dimensionRatio;
            layout2.circleConstraint = layoutParams.circleConstraint;
            layout2.circleRadius = layoutParams.circleRadius;
            layout2.circleAngle = layoutParams.circleAngle;
            layout2.editorAbsoluteX = layoutParams.editorAbsoluteX;
            layout2.editorAbsoluteY = layoutParams.editorAbsoluteY;
            layout2.orientation = layoutParams.orientation;
            layout2.guidePercent = layoutParams.guidePercent;
            layout2.guideBegin = layoutParams.guideBegin;
            layout2.guideEnd = layoutParams.guideEnd;
            layout2.mWidth = layoutParams.width;
            layout2.mHeight = layoutParams.height;
            layout2.leftMargin = layoutParams.leftMargin;
            layout2.rightMargin = layoutParams.rightMargin;
            layout2.topMargin = layoutParams.topMargin;
            layout2.bottomMargin = layoutParams.bottomMargin;
            layout2.verticalWeight = layoutParams.verticalWeight;
            layout2.horizontalWeight = layoutParams.horizontalWeight;
            layout2.verticalChainStyle = layoutParams.verticalChainStyle;
            layout2.horizontalChainStyle = layoutParams.horizontalChainStyle;
            layout2.constrainedWidth = layoutParams.constrainedWidth;
            layout2.constrainedHeight = layoutParams.constrainedHeight;
            layout2.widthDefault = layoutParams.matchConstraintDefaultWidth;
            layout2.heightDefault = layoutParams.matchConstraintDefaultHeight;
            layout2.widthMax = layoutParams.matchConstraintMaxWidth;
            layout2.heightMax = layoutParams.matchConstraintMaxHeight;
            layout2.widthMin = layoutParams.matchConstraintMinWidth;
            layout2.heightMin = layoutParams.matchConstraintMinHeight;
            layout2.widthPercent = layoutParams.matchConstraintPercentWidth;
            layout2.heightPercent = layoutParams.matchConstraintPercentHeight;
            layout2.mConstraintTag = layoutParams.constraintTag;
            layout2.goneTopMargin = layoutParams.goneTopMargin;
            layout2.goneBottomMargin = layoutParams.goneBottomMargin;
            layout2.goneLeftMargin = layoutParams.goneLeftMargin;
            layout2.goneRightMargin = layoutParams.goneRightMargin;
            layout2.goneStartMargin = layoutParams.goneStartMargin;
            layout2.goneEndMargin = layoutParams.goneEndMargin;
            layout2.endMargin = layoutParams.getMarginEnd();
            this.layout.startMargin = layoutParams.getMarginStart();
        }

        public final void fillFromConstraints(int i, Constraints.LayoutParams layoutParams) {
            fillFrom(i, layoutParams);
            this.propertySet.alpha = layoutParams.alpha;
            Transform transform2 = this.transform;
            transform2.rotation = layoutParams.rotation;
            transform2.rotationX = layoutParams.rotationX;
            transform2.rotationY = layoutParams.rotationY;
            transform2.scaleX = layoutParams.scaleX;
            transform2.scaleY = layoutParams.scaleY;
            transform2.transformPivotX = layoutParams.transformPivotX;
            transform2.transformPivotY = layoutParams.transformPivotY;
            transform2.translationX = layoutParams.translationX;
            transform2.translationY = layoutParams.translationY;
            transform2.translationZ = layoutParams.translationZ;
            transform2.elevation = layoutParams.elevation;
            transform2.applyElevation = layoutParams.applyElevation;
        }
    }

    public static class Layout {
        public static SparseIntArray mapToConstant;
        public int baselineToBaseline = -1;
        public int bottomMargin = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public float circleAngle = 0.0f;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        public String dimensionRatio = null;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endMargin = -1;
        public int endToEnd = -1;
        public int endToStart = -1;
        public int goneBottomMargin = -1;
        public int goneEndMargin = -1;
        public int goneLeftMargin = -1;
        public int goneRightMargin = -1;
        public int goneStartMargin = -1;
        public int goneTopMargin = -1;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public int heightDefault = 0;
        public int heightMax = -1;
        public int heightMin = -1;
        public float heightPercent = 1.0f;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        public float horizontalWeight = -1.0f;
        public int leftMargin = -1;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public boolean mApply = false;
        public boolean mBarrierAllowsGoneWidgets = true;
        public int mBarrierDirection = -1;
        public int mBarrierMargin = 0;
        public String mConstraintTag;
        public int mHeight;
        public int mHelperType = -1;
        public boolean mIsGuideline = false;
        public String mReferenceIdString;
        public int[] mReferenceIds;
        public int mWidth;
        public int orientation = -1;
        public int rightMargin = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int startMargin = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topMargin = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        public float verticalWeight = -1.0f;
        public int widthDefault = 0;
        public int widthMax = -1;
        public int widthMin = -1;
        public float widthPercent = 1.0f;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(38, 24);
            mapToConstant.append(39, 25);
            mapToConstant.append(41, 28);
            mapToConstant.append(42, 29);
            mapToConstant.append(47, 35);
            mapToConstant.append(46, 34);
            mapToConstant.append(20, 4);
            mapToConstant.append(19, 3);
            mapToConstant.append(17, 1);
            mapToConstant.append(55, 6);
            mapToConstant.append(56, 7);
            mapToConstant.append(27, 17);
            mapToConstant.append(28, 18);
            mapToConstant.append(29, 19);
            mapToConstant.append(0, 26);
            mapToConstant.append(43, 31);
            mapToConstant.append(44, 32);
            mapToConstant.append(26, 10);
            mapToConstant.append(25, 9);
            mapToConstant.append(59, 13);
            mapToConstant.append(62, 16);
            mapToConstant.append(60, 14);
            mapToConstant.append(57, 11);
            mapToConstant.append(61, 15);
            mapToConstant.append(58, 12);
            mapToConstant.append(50, 38);
            mapToConstant.append(36, 37);
            mapToConstant.append(35, 39);
            mapToConstant.append(49, 40);
            mapToConstant.append(34, 20);
            mapToConstant.append(48, 36);
            mapToConstant.append(24, 5);
            mapToConstant.append(37, 76);
            mapToConstant.append(45, 76);
            mapToConstant.append(40, 76);
            mapToConstant.append(18, 76);
            mapToConstant.append(16, 76);
            mapToConstant.append(3, 23);
            mapToConstant.append(5, 27);
            mapToConstant.append(7, 30);
            mapToConstant.append(8, 8);
            mapToConstant.append(4, 33);
            mapToConstant.append(6, 2);
            mapToConstant.append(1, 22);
            mapToConstant.append(2, 21);
            mapToConstant.append(21, 61);
            mapToConstant.append(23, 62);
            mapToConstant.append(22, 63);
            mapToConstant.append(54, 69);
            mapToConstant.append(33, 70);
            mapToConstant.append(12, 71);
            mapToConstant.append(10, 72);
            mapToConstant.append(11, 73);
            mapToConstant.append(13, 74);
            mapToConstant.append(9, 75);
        }

        public final void copyFrom(Layout layout) {
            this.mIsGuideline = layout.mIsGuideline;
            this.mWidth = layout.mWidth;
            this.mApply = layout.mApply;
            this.mHeight = layout.mHeight;
            this.guideBegin = layout.guideBegin;
            this.guideEnd = layout.guideEnd;
            this.guidePercent = layout.guidePercent;
            this.leftToLeft = layout.leftToLeft;
            this.leftToRight = layout.leftToRight;
            this.rightToLeft = layout.rightToLeft;
            this.rightToRight = layout.rightToRight;
            this.topToTop = layout.topToTop;
            this.topToBottom = layout.topToBottom;
            this.bottomToTop = layout.bottomToTop;
            this.bottomToBottom = layout.bottomToBottom;
            this.baselineToBaseline = layout.baselineToBaseline;
            this.startToEnd = layout.startToEnd;
            this.startToStart = layout.startToStart;
            this.endToStart = layout.endToStart;
            this.endToEnd = layout.endToEnd;
            this.horizontalBias = layout.horizontalBias;
            this.verticalBias = layout.verticalBias;
            this.dimensionRatio = layout.dimensionRatio;
            this.circleConstraint = layout.circleConstraint;
            this.circleRadius = layout.circleRadius;
            this.circleAngle = layout.circleAngle;
            this.editorAbsoluteX = layout.editorAbsoluteX;
            this.editorAbsoluteY = layout.editorAbsoluteY;
            this.orientation = layout.orientation;
            this.leftMargin = layout.leftMargin;
            this.rightMargin = layout.rightMargin;
            this.topMargin = layout.topMargin;
            this.bottomMargin = layout.bottomMargin;
            this.endMargin = layout.endMargin;
            this.startMargin = layout.startMargin;
            this.goneLeftMargin = layout.goneLeftMargin;
            this.goneTopMargin = layout.goneTopMargin;
            this.goneRightMargin = layout.goneRightMargin;
            this.goneBottomMargin = layout.goneBottomMargin;
            this.goneEndMargin = layout.goneEndMargin;
            this.goneStartMargin = layout.goneStartMargin;
            this.verticalWeight = layout.verticalWeight;
            this.horizontalWeight = layout.horizontalWeight;
            this.horizontalChainStyle = layout.horizontalChainStyle;
            this.verticalChainStyle = layout.verticalChainStyle;
            this.widthDefault = layout.widthDefault;
            this.heightDefault = layout.heightDefault;
            this.widthMax = layout.widthMax;
            this.heightMax = layout.heightMax;
            this.widthMin = layout.widthMin;
            this.heightMin = layout.heightMin;
            this.widthPercent = layout.widthPercent;
            this.heightPercent = layout.heightPercent;
            this.mBarrierDirection = layout.mBarrierDirection;
            this.mBarrierMargin = layout.mBarrierMargin;
            this.mHelperType = layout.mHelperType;
            this.mConstraintTag = layout.mConstraintTag;
            int[] iArr = layout.mReferenceIds;
            if (iArr != null) {
                this.mReferenceIds = Arrays.copyOf(iArr, iArr.length);
            } else {
                this.mReferenceIds = null;
            }
            this.mReferenceIdString = layout.mReferenceIdString;
            this.constrainedWidth = layout.constrainedWidth;
            this.constrainedHeight = layout.constrainedHeight;
            this.mBarrierAllowsGoneWidgets = layout.mBarrierAllowsGoneWidgets;
        }

        public final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Layout);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                int i2 = mapToConstant.get(index);
                if (i2 == 80) {
                    this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                } else if (i2 != 81) {
                    switch (i2) {
                        case 1:
                            this.baselineToBaseline = ConstraintSet.lookupID(obtainStyledAttributes, index, this.baselineToBaseline);
                            break;
                        case 2:
                            this.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.bottomMargin);
                            break;
                        case 3:
                            this.bottomToBottom = ConstraintSet.lookupID(obtainStyledAttributes, index, this.bottomToBottom);
                            break;
                        case 4:
                            this.bottomToTop = ConstraintSet.lookupID(obtainStyledAttributes, index, this.bottomToTop);
                            break;
                        case 5:
                            this.dimensionRatio = obtainStyledAttributes.getString(index);
                            break;
                        case FalsingManager.VERSION /*6*/:
                            this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                            break;
                        case 7:
                            this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                            break;
                        case 8:
                            this.endMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.endMargin);
                            break;
                        case 9:
                            this.endToEnd = ConstraintSet.lookupID(obtainStyledAttributes, index, this.endToEnd);
                            break;
                        case 10:
                            this.endToStart = ConstraintSet.lookupID(obtainStyledAttributes, index, this.endToStart);
                            break;
                        case QSTileImpl.C1034H.STALE /*11*/:
                            this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                            break;
                        case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                            this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                            break;
                        case C0961QS.VERSION /*13*/:
                            this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                            break;
                        case 14:
                            this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                            break;
                        case 15:
                            this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                            break;
                        case 16:
                            this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                            break;
                        case 17:
                            this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                            break;
                        case 18:
                            this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                            break;
                        case 19:
                            this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                            break;
                        case 20:
                            this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                            break;
                        case 21:
                            this.mHeight = obtainStyledAttributes.getLayoutDimension(index, this.mHeight);
                            break;
                        case 22:
                            this.mWidth = obtainStyledAttributes.getLayoutDimension(index, this.mWidth);
                            break;
                        case 23:
                            this.leftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.leftMargin);
                            break;
                        case 24:
                            this.leftToLeft = ConstraintSet.lookupID(obtainStyledAttributes, index, this.leftToLeft);
                            break;
                        case 25:
                            this.leftToRight = ConstraintSet.lookupID(obtainStyledAttributes, index, this.leftToRight);
                            break;
                        case 26:
                            this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                            break;
                        case 27:
                            this.rightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.rightMargin);
                            break;
                        case 28:
                            this.rightToLeft = ConstraintSet.lookupID(obtainStyledAttributes, index, this.rightToLeft);
                            break;
                        case 29:
                            this.rightToRight = ConstraintSet.lookupID(obtainStyledAttributes, index, this.rightToRight);
                            break;
                        case 30:
                            this.startMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.startMargin);
                            break;
                        case 31:
                            this.startToEnd = ConstraintSet.lookupID(obtainStyledAttributes, index, this.startToEnd);
                            break;
                        case 32:
                            this.startToStart = ConstraintSet.lookupID(obtainStyledAttributes, index, this.startToStart);
                            break;
                        case 33:
                            this.topMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.topMargin);
                            break;
                        case 34:
                            this.topToBottom = ConstraintSet.lookupID(obtainStyledAttributes, index, this.topToBottom);
                            break;
                        case 35:
                            this.topToTop = ConstraintSet.lookupID(obtainStyledAttributes, index, this.topToTop);
                            break;
                        case 36:
                            this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                            break;
                        case 37:
                            this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                            break;
                        case 38:
                            this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                            break;
                        case 39:
                            this.horizontalChainStyle = obtainStyledAttributes.getInt(index, this.horizontalChainStyle);
                            break;
                        case SwipeRefreshLayout.CIRCLE_DIAMETER /*40*/:
                            this.verticalChainStyle = obtainStyledAttributes.getInt(index, this.verticalChainStyle);
                            break;
                        default:
                            switch (i2) {
                                case 54:
                                    this.widthDefault = obtainStyledAttributes.getInt(index, this.widthDefault);
                                    break;
                                case 55:
                                    this.heightDefault = obtainStyledAttributes.getInt(index, this.heightDefault);
                                    break;
                                case SwipeRefreshLayout.CIRCLE_DIAMETER_LARGE /*56*/:
                                    this.widthMax = obtainStyledAttributes.getDimensionPixelSize(index, this.widthMax);
                                    break;
                                case 57:
                                    this.heightMax = obtainStyledAttributes.getDimensionPixelSize(index, this.heightMax);
                                    break;
                                case 58:
                                    this.widthMin = obtainStyledAttributes.getDimensionPixelSize(index, this.widthMin);
                                    break;
                                case 59:
                                    this.heightMin = obtainStyledAttributes.getDimensionPixelSize(index, this.heightMin);
                                    break;
                                default:
                                    switch (i2) {
                                        case 61:
                                            this.circleConstraint = ConstraintSet.lookupID(obtainStyledAttributes, index, this.circleConstraint);
                                            break;
                                        case 62:
                                            this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                                            break;
                                        case 63:
                                            this.circleAngle = obtainStyledAttributes.getFloat(index, this.circleAngle);
                                            break;
                                        default:
                                            switch (i2) {
                                                case 69:
                                                    this.widthPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                                                    break;
                                                case 70:
                                                    this.heightPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                                                    break;
                                                case 71:
                                                    Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                                                    break;
                                                case 72:
                                                    this.mBarrierDirection = obtainStyledAttributes.getInt(index, this.mBarrierDirection);
                                                    break;
                                                case 73:
                                                    this.mBarrierMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.mBarrierMargin);
                                                    break;
                                                case 74:
                                                    this.mReferenceIdString = obtainStyledAttributes.getString(index);
                                                    break;
                                                case 75:
                                                    this.mBarrierAllowsGoneWidgets = obtainStyledAttributes.getBoolean(index, this.mBarrierAllowsGoneWidgets);
                                                    break;
                                                case 76:
                                                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("unused attribute 0x");
                                                    m.append(Integer.toHexString(index));
                                                    m.append("   ");
                                                    m.append(mapToConstant.get(index));
                                                    Log.w("ConstraintSet", m.toString());
                                                    break;
                                                case 77:
                                                    this.mConstraintTag = obtainStyledAttributes.getString(index);
                                                    break;
                                                default:
                                                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown attribute 0x");
                                                    m2.append(Integer.toHexString(index));
                                                    m2.append("   ");
                                                    m2.append(mapToConstant.get(index));
                                                    Log.w("ConstraintSet", m2.toString());
                                                    break;
                                            }
                                    }
                            }
                    }
                } else {
                    this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public static class Motion {
        public static SparseIntArray mapToConstant;
        public int mAnimateRelativeTo = -1;
        public boolean mApply = false;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public int mPathMotionArc = -1;
        public float mPathRotate = Float.NaN;
        public String mTransitionEasing = null;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(2, 1);
            mapToConstant.append(4, 2);
            mapToConstant.append(5, 3);
            mapToConstant.append(1, 4);
            mapToConstant.append(0, 5);
            mapToConstant.append(3, 6);
        }

        public final void copyFrom(Motion motion) {
            this.mApply = motion.mApply;
            this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
            this.mTransitionEasing = motion.mTransitionEasing;
            this.mPathMotionArc = motion.mPathMotionArc;
            this.mDrawPath = motion.mDrawPath;
            this.mPathRotate = motion.mPathRotate;
            this.mMotionStagger = motion.mMotionStagger;
        }

        public final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Motion);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (mapToConstant.get(index)) {
                    case 1:
                        this.mPathRotate = obtainStyledAttributes.getFloat(index, this.mPathRotate);
                        break;
                    case 2:
                        this.mPathMotionArc = obtainStyledAttributes.getInt(index, this.mPathMotionArc);
                        break;
                    case 3:
                        if (obtainStyledAttributes.peekValue(index).type != 3) {
                            this.mTransitionEasing = Easing.NAMED_EASING[obtainStyledAttributes.getInteger(index, 0)];
                            break;
                        } else {
                            this.mTransitionEasing = obtainStyledAttributes.getString(index);
                            break;
                        }
                    case 4:
                        this.mDrawPath = obtainStyledAttributes.getInt(index, 0);
                        break;
                    case 5:
                        this.mAnimateRelativeTo = ConstraintSet.lookupID(obtainStyledAttributes, index, this.mAnimateRelativeTo);
                        break;
                    case FalsingManager.VERSION /*6*/:
                        this.mMotionStagger = obtainStyledAttributes.getFloat(index, this.mMotionStagger);
                        break;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public static class PropertySet {
        public float alpha = 1.0f;
        public boolean mApply = false;
        public float mProgress = Float.NaN;
        public int mVisibilityMode = 0;
        public int visibility = 0;

        public final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PropertySet);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 1) {
                    this.alpha = obtainStyledAttributes.getFloat(index, this.alpha);
                } else if (index == 0) {
                    int i2 = obtainStyledAttributes.getInt(index, this.visibility);
                    this.visibility = i2;
                    this.visibility = ConstraintSet.VISIBILITY_FLAGS[i2];
                } else if (index == 4) {
                    this.mVisibilityMode = obtainStyledAttributes.getInt(index, this.mVisibilityMode);
                } else if (index == 3) {
                    this.mProgress = obtainStyledAttributes.getFloat(index, this.mProgress);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public static class Transform {
        public static SparseIntArray mapToConstant;
        public boolean applyElevation = false;
        public float elevation = 0.0f;
        public boolean mApply = false;
        public float rotation = 0.0f;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public float transformPivotX = Float.NaN;
        public float transformPivotY = Float.NaN;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(6, 1);
            mapToConstant.append(7, 2);
            mapToConstant.append(8, 3);
            mapToConstant.append(4, 4);
            mapToConstant.append(5, 5);
            mapToConstant.append(0, 6);
            mapToConstant.append(1, 7);
            mapToConstant.append(2, 8);
            mapToConstant.append(3, 9);
            mapToConstant.append(9, 10);
            mapToConstant.append(10, 11);
        }

        public final void copyFrom(Transform transform) {
            this.mApply = transform.mApply;
            this.rotation = transform.rotation;
            this.rotationX = transform.rotationX;
            this.rotationY = transform.rotationY;
            this.scaleX = transform.scaleX;
            this.scaleY = transform.scaleY;
            this.transformPivotX = transform.transformPivotX;
            this.transformPivotY = transform.transformPivotY;
            this.translationX = transform.translationX;
            this.translationY = transform.translationY;
            this.translationZ = transform.translationZ;
            this.applyElevation = transform.applyElevation;
            this.elevation = transform.elevation;
        }

        public final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Transform);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (mapToConstant.get(index)) {
                    case 1:
                        this.rotation = obtainStyledAttributes.getFloat(index, this.rotation);
                        break;
                    case 2:
                        this.rotationX = obtainStyledAttributes.getFloat(index, this.rotationX);
                        break;
                    case 3:
                        this.rotationY = obtainStyledAttributes.getFloat(index, this.rotationY);
                        break;
                    case 4:
                        this.scaleX = obtainStyledAttributes.getFloat(index, this.scaleX);
                        break;
                    case 5:
                        this.scaleY = obtainStyledAttributes.getFloat(index, this.scaleY);
                        break;
                    case FalsingManager.VERSION /*6*/:
                        this.transformPivotX = obtainStyledAttributes.getFloat(index, this.transformPivotX);
                        break;
                    case 7:
                        this.transformPivotY = obtainStyledAttributes.getFloat(index, this.transformPivotY);
                        break;
                    case 8:
                        this.translationX = obtainStyledAttributes.getDimension(index, this.translationX);
                        break;
                    case 9:
                        this.translationY = obtainStyledAttributes.getDimension(index, this.translationY);
                        break;
                    case 10:
                        this.translationZ = obtainStyledAttributes.getDimension(index, this.translationZ);
                        break;
                    case QSTileImpl.C1034H.STALE /*11*/:
                        this.applyElevation = true;
                        this.elevation = obtainStyledAttributes.getDimension(index, this.elevation);
                        break;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        mapToConstant = sparseIntArray;
        sparseIntArray.append(78, 25);
        mapToConstant.append(79, 26);
        mapToConstant.append(81, 29);
        mapToConstant.append(82, 30);
        mapToConstant.append(88, 36);
        mapToConstant.append(87, 35);
        mapToConstant.append(60, 4);
        mapToConstant.append(59, 3);
        mapToConstant.append(57, 1);
        mapToConstant.append(96, 6);
        mapToConstant.append(97, 7);
        mapToConstant.append(67, 17);
        mapToConstant.append(68, 18);
        mapToConstant.append(69, 19);
        mapToConstant.append(0, 27);
        mapToConstant.append(83, 32);
        mapToConstant.append(84, 33);
        mapToConstant.append(66, 10);
        mapToConstant.append(65, 9);
        mapToConstant.append(100, 13);
        mapToConstant.append(103, 16);
        mapToConstant.append(101, 14);
        mapToConstant.append(98, 11);
        mapToConstant.append(102, 15);
        mapToConstant.append(99, 12);
        mapToConstant.append(91, 40);
        mapToConstant.append(76, 39);
        mapToConstant.append(75, 41);
        mapToConstant.append(90, 42);
        mapToConstant.append(74, 20);
        mapToConstant.append(89, 37);
        mapToConstant.append(64, 5);
        mapToConstant.append(77, 82);
        mapToConstant.append(86, 82);
        mapToConstant.append(80, 82);
        mapToConstant.append(58, 82);
        mapToConstant.append(56, 82);
        mapToConstant.append(5, 24);
        mapToConstant.append(7, 28);
        mapToConstant.append(25, 31);
        mapToConstant.append(26, 8);
        mapToConstant.append(6, 34);
        mapToConstant.append(8, 2);
        mapToConstant.append(3, 23);
        mapToConstant.append(4, 21);
        mapToConstant.append(2, 22);
        mapToConstant.append(15, 43);
        mapToConstant.append(28, 44);
        mapToConstant.append(23, 45);
        mapToConstant.append(24, 46);
        mapToConstant.append(22, 60);
        mapToConstant.append(20, 47);
        mapToConstant.append(21, 48);
        mapToConstant.append(16, 49);
        mapToConstant.append(17, 50);
        mapToConstant.append(18, 51);
        mapToConstant.append(19, 52);
        mapToConstant.append(27, 53);
        mapToConstant.append(92, 54);
        mapToConstant.append(70, 55);
        mapToConstant.append(93, 56);
        mapToConstant.append(71, 57);
        mapToConstant.append(94, 58);
        mapToConstant.append(72, 59);
        mapToConstant.append(61, 61);
        mapToConstant.append(63, 62);
        mapToConstant.append(62, 63);
        mapToConstant.append(29, 64);
        mapToConstant.append(108, 65);
        mapToConstant.append(35, 66);
        mapToConstant.append(109, 67);
        mapToConstant.append(104, 79);
        mapToConstant.append(1, 38);
        mapToConstant.append(107, 68);
        mapToConstant.append(95, 69);
        mapToConstant.append(73, 70);
        mapToConstant.append(33, 71);
        mapToConstant.append(31, 72);
        mapToConstant.append(32, 73);
        mapToConstant.append(34, 74);
        mapToConstant.append(30, 75);
        mapToConstant.append(105, 76);
        mapToConstant.append(85, 77);
        mapToConstant.append(110, 78);
        mapToConstant.append(55, 80);
        mapToConstant.append(54, 81);
    }

    public static String sideToString(int i) {
        switch (i) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return "top";
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case FalsingManager.VERSION /*6*/:
                return "start";
            case 7:
                return "end";
            default:
                return "undefined";
        }
    }

    public final void load(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    Constraint fillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xml));
                    if (name.equalsIgnoreCase("Guideline")) {
                        fillFromAttributeList.layout.mIsGuideline = true;
                    }
                    this.mConstraints.put(Integer.valueOf(fillFromAttributeList.mViewId), fillFromAttributeList);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static int[] convertReferenceString(Barrier barrier, String str) {
        int i;
        Object designInformation;
        String[] split = str.split(",");
        Context context = barrier.getContext();
        int[] iArr = new int[split.length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < split.length) {
            String trim = split[i2].trim();
            try {
                i = R$id.class.getField(trim).getInt((Object) null);
            } catch (Exception unused) {
                i = 0;
            }
            if (i == 0) {
                i = context.getResources().getIdentifier(trim, "id", context.getPackageName());
            }
            if (i == 0 && barrier.isInEditMode() && (barrier.getParent() instanceof ConstraintLayout) && (designInformation = ((ConstraintLayout) barrier.getParent()).getDesignInformation(trim)) != null && (designInformation instanceof Integer)) {
                i = ((Integer) designInformation).intValue();
            }
            iArr[i3] = i;
            i2++;
            i3++;
        }
        if (i3 != split.length) {
            return Arrays.copyOf(iArr, i3);
        }
        return iArr;
    }

    public static Constraint fillFromAttributeList(Context context, AttributeSet attributeSet) {
        Constraint constraint = new Constraint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Constraint);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index != 1) {
                constraint.motion.mApply = true;
                constraint.layout.mApply = true;
                constraint.propertySet.mApply = true;
                constraint.transform.mApply = true;
            }
            switch (mapToConstant.get(index)) {
                case 1:
                    Layout layout = constraint.layout;
                    layout.baselineToBaseline = lookupID(obtainStyledAttributes, index, layout.baselineToBaseline);
                    break;
                case 2:
                    Layout layout2 = constraint.layout;
                    layout2.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout2.bottomMargin);
                    break;
                case 3:
                    Layout layout3 = constraint.layout;
                    layout3.bottomToBottom = lookupID(obtainStyledAttributes, index, layout3.bottomToBottom);
                    break;
                case 4:
                    Layout layout4 = constraint.layout;
                    layout4.bottomToTop = lookupID(obtainStyledAttributes, index, layout4.bottomToTop);
                    break;
                case 5:
                    constraint.layout.dimensionRatio = obtainStyledAttributes.getString(index);
                    break;
                case FalsingManager.VERSION /*6*/:
                    Layout layout5 = constraint.layout;
                    layout5.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, layout5.editorAbsoluteX);
                    break;
                case 7:
                    Layout layout6 = constraint.layout;
                    layout6.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, layout6.editorAbsoluteY);
                    break;
                case 8:
                    Layout layout7 = constraint.layout;
                    layout7.endMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout7.endMargin);
                    break;
                case 9:
                    Layout layout8 = constraint.layout;
                    layout8.endToEnd = lookupID(obtainStyledAttributes, index, layout8.endToEnd);
                    break;
                case 10:
                    Layout layout9 = constraint.layout;
                    layout9.endToStart = lookupID(obtainStyledAttributes, index, layout9.endToStart);
                    break;
                case QSTileImpl.C1034H.STALE /*11*/:
                    Layout layout10 = constraint.layout;
                    layout10.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout10.goneBottomMargin);
                    break;
                case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                    Layout layout11 = constraint.layout;
                    layout11.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout11.goneEndMargin);
                    break;
                case C0961QS.VERSION /*13*/:
                    Layout layout12 = constraint.layout;
                    layout12.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout12.goneLeftMargin);
                    break;
                case 14:
                    Layout layout13 = constraint.layout;
                    layout13.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout13.goneRightMargin);
                    break;
                case 15:
                    Layout layout14 = constraint.layout;
                    layout14.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout14.goneStartMargin);
                    break;
                case 16:
                    Layout layout15 = constraint.layout;
                    layout15.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout15.goneTopMargin);
                    break;
                case 17:
                    Layout layout16 = constraint.layout;
                    layout16.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, layout16.guideBegin);
                    break;
                case 18:
                    Layout layout17 = constraint.layout;
                    layout17.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, layout17.guideEnd);
                    break;
                case 19:
                    Layout layout18 = constraint.layout;
                    layout18.guidePercent = obtainStyledAttributes.getFloat(index, layout18.guidePercent);
                    break;
                case 20:
                    Layout layout19 = constraint.layout;
                    layout19.horizontalBias = obtainStyledAttributes.getFloat(index, layout19.horizontalBias);
                    break;
                case 21:
                    Layout layout20 = constraint.layout;
                    layout20.mHeight = obtainStyledAttributes.getLayoutDimension(index, layout20.mHeight);
                    break;
                case 22:
                    PropertySet propertySet = constraint.propertySet;
                    propertySet.visibility = obtainStyledAttributes.getInt(index, propertySet.visibility);
                    PropertySet propertySet2 = constraint.propertySet;
                    propertySet2.visibility = VISIBILITY_FLAGS[propertySet2.visibility];
                    break;
                case 23:
                    Layout layout21 = constraint.layout;
                    layout21.mWidth = obtainStyledAttributes.getLayoutDimension(index, layout21.mWidth);
                    break;
                case 24:
                    Layout layout22 = constraint.layout;
                    layout22.leftMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout22.leftMargin);
                    break;
                case 25:
                    Layout layout23 = constraint.layout;
                    layout23.leftToLeft = lookupID(obtainStyledAttributes, index, layout23.leftToLeft);
                    break;
                case 26:
                    Layout layout24 = constraint.layout;
                    layout24.leftToRight = lookupID(obtainStyledAttributes, index, layout24.leftToRight);
                    break;
                case 27:
                    Layout layout25 = constraint.layout;
                    layout25.orientation = obtainStyledAttributes.getInt(index, layout25.orientation);
                    break;
                case 28:
                    Layout layout26 = constraint.layout;
                    layout26.rightMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout26.rightMargin);
                    break;
                case 29:
                    Layout layout27 = constraint.layout;
                    layout27.rightToLeft = lookupID(obtainStyledAttributes, index, layout27.rightToLeft);
                    break;
                case 30:
                    Layout layout28 = constraint.layout;
                    layout28.rightToRight = lookupID(obtainStyledAttributes, index, layout28.rightToRight);
                    break;
                case 31:
                    Layout layout29 = constraint.layout;
                    layout29.startMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout29.startMargin);
                    break;
                case 32:
                    Layout layout30 = constraint.layout;
                    layout30.startToEnd = lookupID(obtainStyledAttributes, index, layout30.startToEnd);
                    break;
                case 33:
                    Layout layout31 = constraint.layout;
                    layout31.startToStart = lookupID(obtainStyledAttributes, index, layout31.startToStart);
                    break;
                case 34:
                    Layout layout32 = constraint.layout;
                    layout32.topMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout32.topMargin);
                    break;
                case 35:
                    Layout layout33 = constraint.layout;
                    layout33.topToBottom = lookupID(obtainStyledAttributes, index, layout33.topToBottom);
                    break;
                case 36:
                    Layout layout34 = constraint.layout;
                    layout34.topToTop = lookupID(obtainStyledAttributes, index, layout34.topToTop);
                    break;
                case 37:
                    Layout layout35 = constraint.layout;
                    layout35.verticalBias = obtainStyledAttributes.getFloat(index, layout35.verticalBias);
                    break;
                case 38:
                    constraint.mViewId = obtainStyledAttributes.getResourceId(index, constraint.mViewId);
                    break;
                case 39:
                    Layout layout36 = constraint.layout;
                    layout36.horizontalWeight = obtainStyledAttributes.getFloat(index, layout36.horizontalWeight);
                    break;
                case SwipeRefreshLayout.CIRCLE_DIAMETER /*40*/:
                    Layout layout37 = constraint.layout;
                    layout37.verticalWeight = obtainStyledAttributes.getFloat(index, layout37.verticalWeight);
                    break;
                case 41:
                    Layout layout38 = constraint.layout;
                    layout38.horizontalChainStyle = obtainStyledAttributes.getInt(index, layout38.horizontalChainStyle);
                    break;
                case 42:
                    Layout layout39 = constraint.layout;
                    layout39.verticalChainStyle = obtainStyledAttributes.getInt(index, layout39.verticalChainStyle);
                    break;
                case 43:
                    PropertySet propertySet3 = constraint.propertySet;
                    propertySet3.alpha = obtainStyledAttributes.getFloat(index, propertySet3.alpha);
                    break;
                case 44:
                    Transform transform = constraint.transform;
                    transform.applyElevation = true;
                    transform.elevation = obtainStyledAttributes.getDimension(index, transform.elevation);
                    break;
                case 45:
                    Transform transform2 = constraint.transform;
                    transform2.rotationX = obtainStyledAttributes.getFloat(index, transform2.rotationX);
                    break;
                case 46:
                    Transform transform3 = constraint.transform;
                    transform3.rotationY = obtainStyledAttributes.getFloat(index, transform3.rotationY);
                    break;
                case 47:
                    Transform transform4 = constraint.transform;
                    transform4.scaleX = obtainStyledAttributes.getFloat(index, transform4.scaleX);
                    break;
                case 48:
                    Transform transform5 = constraint.transform;
                    transform5.scaleY = obtainStyledAttributes.getFloat(index, transform5.scaleY);
                    break;
                case 49:
                    Transform transform6 = constraint.transform;
                    transform6.transformPivotX = obtainStyledAttributes.getFloat(index, transform6.transformPivotX);
                    break;
                case CustomEvent.MAX_STR_LENGTH /*50*/:
                    Transform transform7 = constraint.transform;
                    transform7.transformPivotY = obtainStyledAttributes.getFloat(index, transform7.transformPivotY);
                    break;
                case 51:
                    Transform transform8 = constraint.transform;
                    transform8.translationX = obtainStyledAttributes.getDimension(index, transform8.translationX);
                    break;
                case 52:
                    Transform transform9 = constraint.transform;
                    transform9.translationY = obtainStyledAttributes.getDimension(index, transform9.translationY);
                    break;
                case 53:
                    Transform transform10 = constraint.transform;
                    transform10.translationZ = obtainStyledAttributes.getDimension(index, transform10.translationZ);
                    break;
                case 54:
                    Layout layout40 = constraint.layout;
                    layout40.widthDefault = obtainStyledAttributes.getInt(index, layout40.widthDefault);
                    break;
                case 55:
                    Layout layout41 = constraint.layout;
                    layout41.heightDefault = obtainStyledAttributes.getInt(index, layout41.heightDefault);
                    break;
                case SwipeRefreshLayout.CIRCLE_DIAMETER_LARGE /*56*/:
                    Layout layout42 = constraint.layout;
                    layout42.widthMax = obtainStyledAttributes.getDimensionPixelSize(index, layout42.widthMax);
                    break;
                case 57:
                    Layout layout43 = constraint.layout;
                    layout43.heightMax = obtainStyledAttributes.getDimensionPixelSize(index, layout43.heightMax);
                    break;
                case 58:
                    Layout layout44 = constraint.layout;
                    layout44.widthMin = obtainStyledAttributes.getDimensionPixelSize(index, layout44.widthMin);
                    break;
                case 59:
                    Layout layout45 = constraint.layout;
                    layout45.heightMin = obtainStyledAttributes.getDimensionPixelSize(index, layout45.heightMin);
                    break;
                case 60:
                    Transform transform11 = constraint.transform;
                    transform11.rotation = obtainStyledAttributes.getFloat(index, transform11.rotation);
                    break;
                case 61:
                    Layout layout46 = constraint.layout;
                    layout46.circleConstraint = lookupID(obtainStyledAttributes, index, layout46.circleConstraint);
                    break;
                case 62:
                    Layout layout47 = constraint.layout;
                    layout47.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, layout47.circleRadius);
                    break;
                case 63:
                    Layout layout48 = constraint.layout;
                    layout48.circleAngle = obtainStyledAttributes.getFloat(index, layout48.circleAngle);
                    break;
                case 64:
                    Motion motion = constraint.motion;
                    motion.mAnimateRelativeTo = lookupID(obtainStyledAttributes, index, motion.mAnimateRelativeTo);
                    break;
                case 65:
                    if (obtainStyledAttributes.peekValue(index).type != 3) {
                        constraint.motion.mTransitionEasing = Easing.NAMED_EASING[obtainStyledAttributes.getInteger(index, 0)];
                        break;
                    } else {
                        constraint.motion.mTransitionEasing = obtainStyledAttributes.getString(index);
                        break;
                    }
                case 66:
                    constraint.motion.mDrawPath = obtainStyledAttributes.getInt(index, 0);
                    break;
                case 67:
                    Motion motion2 = constraint.motion;
                    motion2.mPathRotate = obtainStyledAttributes.getFloat(index, motion2.mPathRotate);
                    break;
                case 68:
                    PropertySet propertySet4 = constraint.propertySet;
                    propertySet4.mProgress = obtainStyledAttributes.getFloat(index, propertySet4.mProgress);
                    break;
                case 69:
                    constraint.layout.widthPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                    break;
                case 70:
                    constraint.layout.heightPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                    break;
                case 71:
                    Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    Layout layout49 = constraint.layout;
                    layout49.mBarrierDirection = obtainStyledAttributes.getInt(index, layout49.mBarrierDirection);
                    break;
                case 73:
                    Layout layout50 = constraint.layout;
                    layout50.mBarrierMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout50.mBarrierMargin);
                    break;
                case 74:
                    constraint.layout.mReferenceIdString = obtainStyledAttributes.getString(index);
                    break;
                case 75:
                    Layout layout51 = constraint.layout;
                    layout51.mBarrierAllowsGoneWidgets = obtainStyledAttributes.getBoolean(index, layout51.mBarrierAllowsGoneWidgets);
                    break;
                case 76:
                    Motion motion3 = constraint.motion;
                    motion3.mPathMotionArc = obtainStyledAttributes.getInt(index, motion3.mPathMotionArc);
                    break;
                case 77:
                    constraint.layout.mConstraintTag = obtainStyledAttributes.getString(index);
                    break;
                case 78:
                    PropertySet propertySet5 = constraint.propertySet;
                    propertySet5.mVisibilityMode = obtainStyledAttributes.getInt(index, propertySet5.mVisibilityMode);
                    break;
                case 79:
                    Motion motion4 = constraint.motion;
                    motion4.mMotionStagger = obtainStyledAttributes.getFloat(index, motion4.mMotionStagger);
                    break;
                case 80:
                    Layout layout52 = constraint.layout;
                    layout52.constrainedWidth = obtainStyledAttributes.getBoolean(index, layout52.constrainedWidth);
                    break;
                case 81:
                    Layout layout53 = constraint.layout;
                    layout53.constrainedHeight = obtainStyledAttributes.getBoolean(index, layout53.constrainedHeight);
                    break;
                case 82:
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("unused attribute 0x");
                    m.append(Integer.toHexString(index));
                    m.append("   ");
                    m.append(mapToConstant.get(index));
                    Log.w("ConstraintSet", m.toString());
                    break;
                default:
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown attribute 0x");
                    m2.append(Integer.toHexString(index));
                    m2.append("   ");
                    m2.append(mapToConstant.get(index));
                    Log.w("ConstraintSet", m2.toString());
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        return constraint;
    }

    public final void clone(ConstraintLayout constraintLayout) {
        ConstraintSet constraintSet = this;
        int childCount = constraintLayout.getChildCount();
        constraintSet.mConstraints.clear();
        int i = 0;
        while (i < childCount) {
            View childAt = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (!constraintSet.mForceId || id != -1) {
                if (!constraintSet.mConstraints.containsKey(Integer.valueOf(id))) {
                    constraintSet.mConstraints.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = constraintSet.mConstraints.get(Integer.valueOf(id));
                HashMap<String, ConstraintAttribute> hashMap = constraintSet.mSavedAttributes;
                HashMap<String, ConstraintAttribute> hashMap2 = new HashMap<>();
                Class<?> cls = childAt.getClass();
                for (String next : hashMap.keySet()) {
                    ConstraintAttribute constraintAttribute = hashMap.get(next);
                    try {
                        if (next.equals("BackgroundColor")) {
                            hashMap2.put(next, new ConstraintAttribute(constraintAttribute, Integer.valueOf(((ColorDrawable) childAt.getBackground()).getColor())));
                        } else {
                            try {
                                hashMap2.put(next, new ConstraintAttribute(constraintAttribute, cls.getMethod("getMap" + next, new Class[0]).invoke(childAt, new Object[0])));
                            } catch (NoSuchMethodException e) {
                                e = e;
                            } catch (IllegalAccessException e2) {
                                e = e2;
                                e.printStackTrace();
                            } catch (InvocationTargetException e3) {
                                e = e3;
                                e.printStackTrace();
                            }
                        }
                    } catch (NoSuchMethodException e4) {
                        e = e4;
                        e.printStackTrace();
                    } catch (IllegalAccessException e5) {
                        e = e5;
                        e.printStackTrace();
                    } catch (InvocationTargetException e6) {
                        e = e6;
                        e.printStackTrace();
                    }
                }
                constraint.mCustomConstraints = hashMap2;
                constraint.fillFrom(id, layoutParams);
                constraint.propertySet.visibility = childAt.getVisibility();
                constraint.propertySet.alpha = childAt.getAlpha();
                constraint.transform.rotation = childAt.getRotation();
                constraint.transform.rotationX = childAt.getRotationX();
                constraint.transform.rotationY = childAt.getRotationY();
                constraint.transform.scaleX = childAt.getScaleX();
                constraint.transform.scaleY = childAt.getScaleY();
                float pivotX = childAt.getPivotX();
                float pivotY = childAt.getPivotY();
                if (!(((double) pivotX) == 0.0d && ((double) pivotY) == 0.0d)) {
                    Transform transform = constraint.transform;
                    transform.transformPivotX = pivotX;
                    transform.transformPivotY = pivotY;
                }
                constraint.transform.translationX = childAt.getTranslationX();
                constraint.transform.translationY = childAt.getTranslationY();
                constraint.transform.translationZ = childAt.getTranslationZ();
                Transform transform2 = constraint.transform;
                if (transform2.applyElevation) {
                    transform2.elevation = childAt.getElevation();
                }
                if (childAt instanceof Barrier) {
                    Barrier barrier = (Barrier) childAt;
                    Layout layout = constraint.layout;
                    Barrier barrier2 = barrier.mBarrier;
                    Objects.requireNonNull(barrier2);
                    layout.mBarrierAllowsGoneWidgets = barrier2.mAllowsGoneWidget;
                    constraint.layout.mReferenceIds = Arrays.copyOf(barrier.mIds, barrier.mCount);
                    Layout layout2 = constraint.layout;
                    layout2.mBarrierDirection = barrier.mIndicatedType;
                    Barrier barrier3 = barrier.mBarrier;
                    Objects.requireNonNull(barrier3);
                    layout2.mBarrierMargin = barrier3.mMargin;
                }
                i++;
                constraintSet = this;
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    public final void connect(int i, int i2, int i3, int i4) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
            this.mConstraints.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    Layout layout = constraint.layout;
                    layout.leftToLeft = i3;
                    layout.leftToRight = -1;
                    return;
                } else if (i4 == 2) {
                    Layout layout2 = constraint.layout;
                    layout2.leftToRight = i3;
                    layout2.leftToLeft = -1;
                    return;
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("left to ");
                    m.append(sideToString(i4));
                    m.append(" undefined");
                    throw new IllegalArgumentException(m.toString());
                }
            case 2:
                if (i4 == 1) {
                    Layout layout3 = constraint.layout;
                    layout3.rightToLeft = i3;
                    layout3.rightToRight = -1;
                    return;
                } else if (i4 == 2) {
                    Layout layout4 = constraint.layout;
                    layout4.rightToRight = i3;
                    layout4.rightToLeft = -1;
                    return;
                } else {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("right to ");
                    m2.append(sideToString(i4));
                    m2.append(" undefined");
                    throw new IllegalArgumentException(m2.toString());
                }
            case 3:
                if (i4 == 3) {
                    Layout layout5 = constraint.layout;
                    layout5.topToTop = i3;
                    layout5.topToBottom = -1;
                    layout5.baselineToBaseline = -1;
                    return;
                } else if (i4 == 4) {
                    Layout layout6 = constraint.layout;
                    layout6.topToBottom = i3;
                    layout6.topToTop = -1;
                    layout6.baselineToBaseline = -1;
                    return;
                } else {
                    StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("right to ");
                    m3.append(sideToString(i4));
                    m3.append(" undefined");
                    throw new IllegalArgumentException(m3.toString());
                }
            case 4:
                if (i4 == 4) {
                    Layout layout7 = constraint.layout;
                    layout7.bottomToBottom = i3;
                    layout7.bottomToTop = -1;
                    layout7.baselineToBaseline = -1;
                    return;
                } else if (i4 == 3) {
                    Layout layout8 = constraint.layout;
                    layout8.bottomToTop = i3;
                    layout8.bottomToBottom = -1;
                    layout8.baselineToBaseline = -1;
                    return;
                } else {
                    StringBuilder m4 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("right to ");
                    m4.append(sideToString(i4));
                    m4.append(" undefined");
                    throw new IllegalArgumentException(m4.toString());
                }
            case 5:
                if (i4 == 5) {
                    Layout layout9 = constraint.layout;
                    layout9.baselineToBaseline = i3;
                    layout9.bottomToBottom = -1;
                    layout9.bottomToTop = -1;
                    layout9.topToTop = -1;
                    layout9.topToBottom = -1;
                    return;
                }
                StringBuilder m5 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("right to ");
                m5.append(sideToString(i4));
                m5.append(" undefined");
                throw new IllegalArgumentException(m5.toString());
            case FalsingManager.VERSION /*6*/:
                if (i4 == 6) {
                    Layout layout10 = constraint.layout;
                    layout10.startToStart = i3;
                    layout10.startToEnd = -1;
                    return;
                } else if (i4 == 7) {
                    Layout layout11 = constraint.layout;
                    layout11.startToEnd = i3;
                    layout11.startToStart = -1;
                    return;
                } else {
                    StringBuilder m6 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("right to ");
                    m6.append(sideToString(i4));
                    m6.append(" undefined");
                    throw new IllegalArgumentException(m6.toString());
                }
            case 7:
                if (i4 == 7) {
                    Layout layout12 = constraint.layout;
                    layout12.endToEnd = i3;
                    layout12.endToStart = -1;
                    return;
                } else if (i4 == 6) {
                    Layout layout13 = constraint.layout;
                    layout13.endToStart = i3;
                    layout13.endToEnd = -1;
                    return;
                } else {
                    StringBuilder m7 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("right to ");
                    m7.append(sideToString(i4));
                    m7.append(" undefined");
                    throw new IllegalArgumentException(m7.toString());
                }
            default:
                throw new IllegalArgumentException(sideToString(i2) + " to " + sideToString(i4) + " unknown");
        }
    }

    public final Constraint get(int i) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
            this.mConstraints.put(Integer.valueOf(i), new Constraint());
        }
        return this.mConstraints.get(Integer.valueOf(i));
    }

    public final Constraint getConstraint(int i) {
        if (this.mConstraints.containsKey(Integer.valueOf(i))) {
            return this.mConstraints.get(Integer.valueOf(i));
        }
        return null;
    }

    public static int lookupID(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        if (resourceId == -1) {
            return typedArray.getInt(i, -1);
        }
        return resourceId;
    }

    public final void applyCustomAttributes(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("id unknown ");
                m.append(Debug.getName(childAt));
                Log.v("ConstraintSet", m.toString());
            } else if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            } else if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                ConstraintAttribute.setAttributes(childAt, this.mConstraints.get(Integer.valueOf(id)).mCustomConstraints);
            }
        }
    }

    public final void applyTo(ConstraintLayout constraintLayout) {
        applyToInternal(constraintLayout);
        constraintLayout.mConstraintSet = null;
        constraintLayout.requestLayout();
    }

    public final void applyToInternal(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.mConstraints.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("id unknown ");
                m.append(Debug.getName(childAt));
                Log.w("ConstraintSet", m.toString());
            } else if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            } else if (id != -1) {
                if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                    hashSet.remove(Integer.valueOf(id));
                    Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                    if (childAt instanceof Barrier) {
                        constraint.layout.mHelperType = 1;
                    }
                    int i2 = constraint.layout.mHelperType;
                    if (i2 != -1 && i2 == 1) {
                        Barrier barrier = (Barrier) childAt;
                        barrier.setId(id);
                        Layout layout = constraint.layout;
                        barrier.mIndicatedType = layout.mBarrierDirection;
                        int i3 = layout.mBarrierMargin;
                        Barrier barrier2 = barrier.mBarrier;
                        Objects.requireNonNull(barrier2);
                        barrier2.mMargin = i3;
                        boolean z = constraint.layout.mBarrierAllowsGoneWidgets;
                        Barrier barrier3 = barrier.mBarrier;
                        Objects.requireNonNull(barrier3);
                        barrier3.mAllowsGoneWidget = z;
                        Layout layout2 = constraint.layout;
                        int[] iArr = layout2.mReferenceIds;
                        if (iArr != null) {
                            barrier.setReferencedIds(iArr);
                        } else {
                            String str = layout2.mReferenceIdString;
                            if (str != null) {
                                layout2.mReferenceIds = convertReferenceString(barrier, str);
                                barrier.setReferencedIds(constraint.layout.mReferenceIds);
                            }
                        }
                    }
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                    layoutParams.validate();
                    constraint.applyTo(layoutParams);
                    ConstraintAttribute.setAttributes(childAt, constraint.mCustomConstraints);
                    childAt.setLayoutParams(layoutParams);
                    PropertySet propertySet = constraint.propertySet;
                    if (propertySet.mVisibilityMode == 0) {
                        childAt.setVisibility(propertySet.visibility);
                    }
                    childAt.setAlpha(constraint.propertySet.alpha);
                    childAt.setRotation(constraint.transform.rotation);
                    childAt.setRotationX(constraint.transform.rotationX);
                    childAt.setRotationY(constraint.transform.rotationY);
                    childAt.setScaleX(constraint.transform.scaleX);
                    childAt.setScaleY(constraint.transform.scaleY);
                    if (!Float.isNaN(constraint.transform.transformPivotX)) {
                        childAt.setPivotX(constraint.transform.transformPivotX);
                    }
                    if (!Float.isNaN(constraint.transform.transformPivotY)) {
                        childAt.setPivotY(constraint.transform.transformPivotY);
                    }
                    childAt.setTranslationX(constraint.transform.translationX);
                    childAt.setTranslationY(constraint.transform.translationY);
                    childAt.setTranslationZ(constraint.transform.translationZ);
                    Transform transform = constraint.transform;
                    if (transform.applyElevation) {
                        childAt.setElevation(transform.elevation);
                    }
                } else {
                    Log.v("ConstraintSet", "WARNING NO CONSTRAINTS for view " + id);
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = this.mConstraints.get(num);
            int i4 = constraint2.layout.mHelperType;
            if (i4 != -1 && i4 == 1) {
                Barrier barrier4 = new Barrier(constraintLayout.getContext());
                barrier4.setId(num.intValue());
                Layout layout3 = constraint2.layout;
                int[] iArr2 = layout3.mReferenceIds;
                if (iArr2 != null) {
                    barrier4.setReferencedIds(iArr2);
                } else {
                    String str2 = layout3.mReferenceIdString;
                    if (str2 != null) {
                        layout3.mReferenceIds = convertReferenceString(barrier4, str2);
                        barrier4.setReferencedIds(constraint2.layout.mReferenceIds);
                    }
                }
                Layout layout4 = constraint2.layout;
                barrier4.mIndicatedType = layout4.mBarrierDirection;
                int i5 = layout4.mBarrierMargin;
                Barrier barrier5 = barrier4.mBarrier;
                Objects.requireNonNull(barrier5);
                barrier5.mMargin = i5;
                ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(-2, -2);
                barrier4.validateParams();
                constraint2.applyTo(layoutParams2);
                constraintLayout.addView(barrier4, layoutParams2);
            }
            if (constraint2.layout.mIsGuideline) {
                Guideline guideline = new Guideline(constraintLayout.getContext());
                guideline.setId(num.intValue());
                ConstraintLayout.LayoutParams layoutParams3 = new ConstraintLayout.LayoutParams(-2, -2);
                constraint2.applyTo(layoutParams3);
                constraintLayout.addView(guideline, layoutParams3);
            }
        }
    }

    public final void setMargin(int i, int i2, int i3) {
        Constraint constraint = get(i);
        switch (i2) {
            case 1:
                constraint.layout.leftMargin = i3;
                return;
            case 2:
                constraint.layout.rightMargin = i3;
                return;
            case 3:
                constraint.layout.topMargin = i3;
                return;
            case 4:
                constraint.layout.bottomMargin = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case FalsingManager.VERSION /*6*/:
                constraint.layout.startMargin = i3;
                return;
            case 7:
                constraint.layout.endMargin = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0179, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void load(android.content.Context r10, android.content.res.XmlResourceParser r11) {
        /*
            r9 = this;
            int r0 = r11.getEventType()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r1 = 0
            r2 = r1
        L_0x0006:
            r3 = 1
            if (r0 == r3) goto L_0x0188
            if (r0 == 0) goto L_0x0176
            java.lang.String r4 = "Constraint"
            r5 = 3
            r6 = 2
            if (r0 == r6) goto L_0x0036
            if (r0 == r5) goto L_0x0015
            goto L_0x0179
        L_0x0015:
            java.lang.String r0 = r11.getName()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.String r3 = "ConstraintSet"
            boolean r3 = r3.equals(r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r3 == 0) goto L_0x0022
            return
        L_0x0022:
            boolean r0 = r0.equalsIgnoreCase(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0179
            java.util.HashMap<java.lang.Integer, androidx.constraintlayout.widget.ConstraintSet$Constraint> r0 = r9.mConstraints     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            int r3 = r2.mViewId     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r0.put(r3, r2)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r2 = r1
            goto L_0x0179
        L_0x0036:
            java.lang.String r0 = r11.getName()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r7 = -1
            int r8 = r0.hashCode()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            switch(r8) {
                case -2025855158: goto L_0x0086;
                case -1984451626: goto L_0x007c;
                case -1269513683: goto L_0x0073;
                case -1238332596: goto L_0x0069;
                case -71750448: goto L_0x005f;
                case 1331510167: goto L_0x0055;
                case 1791837707: goto L_0x004b;
                case 1803088381: goto L_0x0043;
                default: goto L_0x0042;
            }     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x0042:
            goto L_0x0090
        L_0x0043:
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = 0
            goto L_0x0091
        L_0x004b:
            java.lang.String r4 = "CustomAttribute"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = 7
            goto L_0x0091
        L_0x0055:
            java.lang.String r4 = "Barrier"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = r6
            goto L_0x0091
        L_0x005f:
            java.lang.String r4 = "Guideline"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = r3
            goto L_0x0091
        L_0x0069:
            java.lang.String r4 = "Transform"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = 4
            goto L_0x0091
        L_0x0073:
            java.lang.String r4 = "PropertySet"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            goto L_0x0091
        L_0x007c:
            java.lang.String r4 = "Motion"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = 6
            goto L_0x0091
        L_0x0086:
            java.lang.String r4 = "Layout"
            boolean r0 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            if (r0 == 0) goto L_0x0090
            r5 = 5
            goto L_0x0091
        L_0x0090:
            r5 = r7
        L_0x0091:
            java.lang.String r0 = "XML parser error must be within a Constraint "
            switch(r5) {
                case 0: goto L_0x016c;
                case 1: goto L_0x015d;
                case 2: goto L_0x0150;
                case 3: goto L_0x012b;
                case 4: goto L_0x0106;
                case 5: goto L_0x00e0;
                case 6: goto L_0x00ba;
                case 7: goto L_0x0098;
                default: goto L_0x0096;
            }
        L_0x0096:
            goto L_0x0179
        L_0x0098:
            if (r2 == 0) goto L_0x00a1
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r0 = r2.mCustomConstraints     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            androidx.constraintlayout.widget.ConstraintAttribute.parse(r10, r11, r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0179
        L_0x00a1:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            int r11 = r11.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r9.<init>(r10)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            throw r9     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x00ba:
            if (r2 == 0) goto L_0x00c7
            androidx.constraintlayout.widget.ConstraintSet$Motion r0 = r2.motion     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r0.fillFromAttributeList(r10, r3)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0179
        L_0x00c7:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            int r11 = r11.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r9.<init>(r10)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            throw r9     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x00e0:
            if (r2 == 0) goto L_0x00ed
            androidx.constraintlayout.widget.ConstraintSet$Layout r0 = r2.layout     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r0.fillFromAttributeList(r10, r3)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0179
        L_0x00ed:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            int r11 = r11.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r9.<init>(r10)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            throw r9     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x0106:
            if (r2 == 0) goto L_0x0112
            androidx.constraintlayout.widget.ConstraintSet$Transform r0 = r2.transform     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r0.fillFromAttributeList(r10, r3)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0179
        L_0x0112:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            int r11 = r11.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r9.<init>(r10)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            throw r9     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x012b:
            if (r2 == 0) goto L_0x0137
            androidx.constraintlayout.widget.ConstraintSet$PropertySet r0 = r2.propertySet     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r0.fillFromAttributeList(r10, r3)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0179
        L_0x0137:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.<init>()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            int r11 = r11.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r10.append(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            java.lang.String r10 = r10.toString()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r9.<init>(r10)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            throw r9     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x0150:
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r0 = fillFromAttributeList(r10, r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            androidx.constraintlayout.widget.ConstraintSet$Layout r2 = r0.layout     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r2.mHelperType = r3     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0174
        L_0x015d:
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r0 = fillFromAttributeList(r10, r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            androidx.constraintlayout.widget.ConstraintSet$Layout r2 = r0.layout     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r2.mIsGuideline = r3     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            r2.mApply = r3     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0174
        L_0x016c:
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r11)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r0 = fillFromAttributeList(r10, r0)     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x0174:
            r2 = r0
            goto L_0x0179
        L_0x0176:
            r11.getName()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
        L_0x0179:
            int r0 = r11.next()     // Catch:{ XmlPullParserException -> 0x0184, IOException -> 0x017f }
            goto L_0x0006
        L_0x017f:
            r9 = move-exception
            r9.printStackTrace()
            goto L_0x0188
        L_0x0184:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0188:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintSet.load(android.content.Context, android.content.res.XmlResourceParser):void");
    }
}
