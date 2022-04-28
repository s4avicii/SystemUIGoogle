package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public final class DependencyGraph {
    public ConstraintWidgetContainer container;
    public ConstraintWidgetContainer mContainer;
    public ArrayList<RunGroup> mGroups;
    public BasicMeasure.Measure mMeasure;
    public BasicMeasure.Measurer mMeasurer;
    public boolean mNeedBuildGraph = true;
    public boolean mNeedRedoMeasures = true;
    public ArrayList<WidgetRun> mRuns = new ArrayList<>();

    public final void applyGroup(DependencyNode dependencyNode, int i, int i2, ArrayList arrayList, RunGroup runGroup) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun.runGroup == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            if (widgetRun != constraintWidgetContainer.horizontalRun && widgetRun != constraintWidgetContainer.verticalRun) {
                if (runGroup == null) {
                    runGroup = new RunGroup(widgetRun);
                    arrayList.add(runGroup);
                }
                widgetRun.runGroup = runGroup;
                runGroup.runs.add(widgetRun);
                Iterator it = widgetRun.start.dependencies.iterator();
                while (it.hasNext()) {
                    Dependency dependency = (Dependency) it.next();
                    if (dependency instanceof DependencyNode) {
                        applyGroup((DependencyNode) dependency, i, 0, arrayList, runGroup);
                    }
                }
                Iterator it2 = widgetRun.end.dependencies.iterator();
                while (it2.hasNext()) {
                    Dependency dependency2 = (Dependency) it2.next();
                    if (dependency2 instanceof DependencyNode) {
                        applyGroup((DependencyNode) dependency2, i, 1, arrayList, runGroup);
                    }
                }
                if (i == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                    Iterator it3 = ((VerticalWidgetRun) widgetRun).baseline.dependencies.iterator();
                    while (it3.hasNext()) {
                        Dependency dependency3 = (Dependency) it3.next();
                        if (dependency3 instanceof DependencyNode) {
                            applyGroup((DependencyNode) dependency3, i, 2, arrayList, runGroup);
                        }
                    }
                }
                Iterator it4 = widgetRun.start.targets.iterator();
                while (it4.hasNext()) {
                    applyGroup((DependencyNode) it4.next(), i, 0, arrayList, runGroup);
                }
                Iterator it5 = widgetRun.end.targets.iterator();
                while (it5.hasNext()) {
                    applyGroup((DependencyNode) it5.next(), i, 1, arrayList, runGroup);
                }
                if (i == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                    Iterator it6 = ((VerticalWidgetRun) widgetRun).baseline.targets.iterator();
                    while (it6.hasNext()) {
                        applyGroup((DependencyNode) it6.next(), i, 2, arrayList, runGroup);
                    }
                }
            }
        }
    }

    public final void basicMeasureWidgets(ConstraintWidgetContainer constraintWidgetContainer) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        int i;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        int i2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.FIXED;
        Iterator<ConstraintWidget> it = constraintWidgetContainer2.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = next.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = dimensionBehaviourArr[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = dimensionBehaviourArr[1];
            if (next.mVisibility == 8) {
                next.measured = true;
            } else {
                float f = next.mMatchConstraintPercentWidth;
                if (f < 1.0f && dimensionBehaviour7 == dimensionBehaviour4) {
                    next.mMatchConstraintDefaultWidth = 2;
                }
                float f2 = next.mMatchConstraintPercentHeight;
                if (f2 < 1.0f && dimensionBehaviour8 == dimensionBehaviour4) {
                    next.mMatchConstraintDefaultHeight = 2;
                }
                if (next.mDimensionRatio > 0.0f) {
                    if (dimensionBehaviour7 == dimensionBehaviour4 && (dimensionBehaviour8 == dimensionBehaviour5 || dimensionBehaviour8 == dimensionBehaviour6)) {
                        next.mMatchConstraintDefaultWidth = 3;
                    } else if (dimensionBehaviour8 == dimensionBehaviour4 && (dimensionBehaviour7 == dimensionBehaviour5 || dimensionBehaviour7 == dimensionBehaviour6)) {
                        next.mMatchConstraintDefaultHeight = 3;
                    } else if (dimensionBehaviour7 == dimensionBehaviour4 && dimensionBehaviour8 == dimensionBehaviour4) {
                        if (next.mMatchConstraintDefaultWidth == 0) {
                            next.mMatchConstraintDefaultWidth = 3;
                        }
                        if (next.mMatchConstraintDefaultHeight == 0) {
                            next.mMatchConstraintDefaultHeight = 3;
                        }
                    }
                }
                if (dimensionBehaviour7 == dimensionBehaviour4 && next.mMatchConstraintDefaultWidth == 1 && (next.mLeft.mTarget == null || next.mRight.mTarget == null)) {
                    dimensionBehaviour7 = dimensionBehaviour5;
                }
                if (dimensionBehaviour8 == dimensionBehaviour4 && next.mMatchConstraintDefaultHeight == 1 && (next.mTop.mTarget == null || next.mBottom.mTarget == null)) {
                    dimensionBehaviour = dimensionBehaviour5;
                } else {
                    dimensionBehaviour = dimensionBehaviour8;
                }
                HorizontalWidgetRun horizontalWidgetRun = next.horizontalRun;
                horizontalWidgetRun.dimensionBehavior = dimensionBehaviour7;
                int i3 = next.mMatchConstraintDefaultWidth;
                horizontalWidgetRun.matchConstraintsType = i3;
                VerticalWidgetRun verticalWidgetRun = next.verticalRun;
                verticalWidgetRun.dimensionBehavior = dimensionBehaviour;
                int i4 = next.mMatchConstraintDefaultHeight;
                verticalWidgetRun.matchConstraintsType = i4;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if ((dimensionBehaviour7 == dimensionBehaviour9 || dimensionBehaviour7 == dimensionBehaviour6 || dimensionBehaviour7 == dimensionBehaviour5) && (dimensionBehaviour == dimensionBehaviour9 || dimensionBehaviour == dimensionBehaviour6 || dimensionBehaviour == dimensionBehaviour5)) {
                    int width = next.getWidth();
                    if (dimensionBehaviour7 == dimensionBehaviour9) {
                        i = (constraintWidgetContainer.getWidth() - next.mLeft.mMargin) - next.mRight.mMargin;
                        dimensionBehaviour2 = dimensionBehaviour6;
                    } else {
                        dimensionBehaviour2 = dimensionBehaviour7;
                        i = width;
                    }
                    int height = next.getHeight();
                    if (dimensionBehaviour == dimensionBehaviour9) {
                        i2 = (constraintWidgetContainer.getHeight() - next.mTop.mMargin) - next.mBottom.mMargin;
                        dimensionBehaviour3 = dimensionBehaviour6;
                    } else {
                        i2 = height;
                        dimensionBehaviour3 = dimensionBehaviour;
                    }
                    ConstraintWidget constraintWidget = next;
                    measure(next, dimensionBehaviour2, i, dimensionBehaviour3, i2);
                    constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                    constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                    constraintWidget.measured = true;
                } else {
                    if (dimensionBehaviour7 == dimensionBehaviour4 && (dimensionBehaviour == dimensionBehaviour5 || dimensionBehaviour == dimensionBehaviour6)) {
                        if (i3 == 3) {
                            if (dimensionBehaviour == dimensionBehaviour5) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                            }
                            int height2 = next.getHeight();
                            measure(next, dimensionBehaviour6, (int) ((((float) height2) * next.mDimensionRatio) + 0.5f), dimensionBehaviour6, height2);
                            next.horizontalRun.dimension.resolve(next.getWidth());
                            next.verticalRun.dimension.resolve(next.getHeight());
                            next.measured = true;
                        } else if (i3 == 1) {
                            measure(next, dimensionBehaviour5, 0, dimensionBehaviour, 0);
                            next.horizontalRun.dimension.wrapValue = next.getWidth();
                        } else if (i3 == 2) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer2.mListDimensionBehaviors;
                            if (dimensionBehaviourArr2[0] == dimensionBehaviour6 || dimensionBehaviourArr2[0] == dimensionBehaviour9) {
                                measure(next, dimensionBehaviour6, (int) ((f * ((float) constraintWidgetContainer.getWidth())) + 0.5f), dimensionBehaviour, next.getHeight());
                                next.horizontalRun.dimension.resolve(next.getWidth());
                                next.verticalRun.dimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        } else {
                            ConstraintAnchor[] constraintAnchorArr = next.mListAnchors;
                            if (constraintAnchorArr[0].mTarget == null || constraintAnchorArr[1].mTarget == null) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour, 0);
                                next.horizontalRun.dimension.resolve(next.getWidth());
                                next.verticalRun.dimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        }
                    }
                    if (dimensionBehaviour == dimensionBehaviour4 && (dimensionBehaviour7 == dimensionBehaviour5 || dimensionBehaviour7 == dimensionBehaviour6)) {
                        if (i4 == 3) {
                            if (dimensionBehaviour7 == dimensionBehaviour5) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                            }
                            int width2 = next.getWidth();
                            float f3 = next.mDimensionRatio;
                            if (next.mDimensionRatioSide == -1) {
                                f3 = 1.0f / f3;
                            }
                            measure(next, dimensionBehaviour6, width2, dimensionBehaviour6, (int) ((((float) width2) * f3) + 0.5f));
                            next.horizontalRun.dimension.resolve(next.getWidth());
                            next.verticalRun.dimension.resolve(next.getHeight());
                            next.measured = true;
                        } else if (i4 == 1) {
                            measure(next, dimensionBehaviour7, 0, dimensionBehaviour5, 0);
                            next.verticalRun.dimension.wrapValue = next.getHeight();
                        } else if (i4 == 2) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr3 = constraintWidgetContainer2.mListDimensionBehaviors;
                            if (dimensionBehaviourArr3[1] == dimensionBehaviour6 || dimensionBehaviourArr3[1] == dimensionBehaviour9) {
                                measure(next, dimensionBehaviour7, next.getWidth(), dimensionBehaviour6, (int) ((f2 * ((float) constraintWidgetContainer.getHeight())) + 0.5f));
                                next.horizontalRun.dimension.resolve(next.getWidth());
                                next.verticalRun.dimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        } else {
                            ConstraintAnchor[] constraintAnchorArr2 = next.mListAnchors;
                            if (constraintAnchorArr2[2].mTarget == null || constraintAnchorArr2[3].mTarget == null) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour, 0);
                                next.horizontalRun.dimension.resolve(next.getWidth());
                                next.verticalRun.dimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        }
                    }
                    if (dimensionBehaviour7 == dimensionBehaviour4 && dimensionBehaviour == dimensionBehaviour4) {
                        if (i3 == 1 || i4 == 1) {
                            measure(next, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                            next.horizontalRun.dimension.wrapValue = next.getWidth();
                            next.verticalRun.dimension.wrapValue = next.getHeight();
                        } else if (i4 == 2 && i3 == 2) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr4 = constraintWidgetContainer2.mListDimensionBehaviors;
                            if ((dimensionBehaviourArr4[0] == dimensionBehaviour6 || dimensionBehaviourArr4[0] == dimensionBehaviour6) && (dimensionBehaviourArr4[1] == dimensionBehaviour6 || dimensionBehaviourArr4[1] == dimensionBehaviour6)) {
                                ConstraintWidget constraintWidget2 = next;
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = dimensionBehaviour6;
                                measure(constraintWidget2, dimensionBehaviour10, (int) ((f * ((float) constraintWidgetContainer.getWidth())) + 0.5f), dimensionBehaviour6, (int) ((f2 * ((float) constraintWidgetContainer.getHeight())) + 0.5f));
                                next.horizontalRun.dimension.resolve(next.getWidth());
                                next.verticalRun.dimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public final void buildGraph() {
        ArrayList<WidgetRun> arrayList = this.mRuns;
        arrayList.clear();
        this.mContainer.horizontalRun.clear();
        this.mContainer.verticalRun.clear();
        arrayList.add(this.mContainer.horizontalRun);
        arrayList.add(this.mContainer.verticalRun);
        Iterator<ConstraintWidget> it = this.mContainer.mChildren.iterator();
        HashSet hashSet = null;
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            if (next instanceof Guideline) {
                arrayList.add(new GuidelineReference(next));
            } else {
                if (next.isInHorizontalChain()) {
                    if (next.horizontalChainRun == null) {
                        next.horizontalChainRun = new ChainRun(next, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(next.horizontalChainRun);
                } else {
                    arrayList.add(next.horizontalRun);
                }
                if (next.isInVerticalChain()) {
                    if (next.verticalChainRun == null) {
                        next.verticalChainRun = new ChainRun(next, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(next.verticalChainRun);
                } else {
                    arrayList.add(next.verticalRun);
                }
                if (next instanceof HelperWidget) {
                    arrayList.add(new HelperReferences(next));
                }
            }
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        Iterator<WidgetRun> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            it2.next().clear();
        }
        Iterator<WidgetRun> it3 = arrayList.iterator();
        while (it3.hasNext()) {
            WidgetRun next2 = it3.next();
            if (next2.widget != this.mContainer) {
                next2.apply();
            }
        }
        this.mGroups.clear();
        findGroup(this.container.horizontalRun, 0, this.mGroups);
        findGroup(this.container.verticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public final int computeWrap(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        WidgetRun widgetRun;
        WidgetRun widgetRun2;
        long j;
        int i2;
        float f;
        long j2;
        DependencyGraph dependencyGraph = this;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        int i3 = i;
        int size = dependencyGraph.mGroups.size();
        long j3 = 0;
        int i4 = 0;
        long j4 = 0;
        while (i4 < size) {
            RunGroup runGroup = dependencyGraph.mGroups.get(i4);
            Objects.requireNonNull(runGroup);
            WidgetRun widgetRun3 = runGroup.firstRun;
            if (widgetRun3 instanceof ChainRun) {
                if (((ChainRun) widgetRun3).orientation != i3) {
                    j3 = Math.max(j3, j4);
                    i4++;
                    j4 = 0;
                    dependencyGraph = this;
                    constraintWidgetContainer2 = constraintWidgetContainer;
                }
            } else if (i3 == 0) {
                if (!(widgetRun3 instanceof HorizontalWidgetRun)) {
                    j3 = Math.max(j3, j4);
                    i4++;
                    j4 = 0;
                    dependencyGraph = this;
                    constraintWidgetContainer2 = constraintWidgetContainer;
                }
            } else if (!(widgetRun3 instanceof VerticalWidgetRun)) {
                j3 = Math.max(j3, j4);
                i4++;
                j4 = 0;
                dependencyGraph = this;
                constraintWidgetContainer2 = constraintWidgetContainer;
            }
            if (i3 == 0) {
                widgetRun = constraintWidgetContainer2.horizontalRun;
            } else {
                widgetRun = constraintWidgetContainer2.verticalRun;
            }
            DependencyNode dependencyNode = widgetRun.start;
            if (i3 == 0) {
                widgetRun2 = constraintWidgetContainer2.horizontalRun;
            } else {
                widgetRun2 = constraintWidgetContainer2.verticalRun;
            }
            DependencyNode dependencyNode2 = widgetRun2.end;
            boolean contains = widgetRun3.start.targets.contains(dependencyNode);
            boolean contains2 = runGroup.firstRun.end.targets.contains(dependencyNode2);
            long wrapDimension = runGroup.firstRun.getWrapDimension();
            if (!contains || !contains2) {
                if (contains) {
                    DependencyNode dependencyNode3 = runGroup.firstRun.start;
                    j4 = Math.max(RunGroup.traverseStart(dependencyNode3, (long) dependencyNode3.margin), ((long) runGroup.firstRun.start.margin) + wrapDimension);
                } else if (contains2) {
                    DependencyNode dependencyNode4 = runGroup.firstRun.end;
                    j4 = Math.max(-RunGroup.traverseEnd(dependencyNode4, (long) dependencyNode4.margin), ((long) (-runGroup.firstRun.end.margin)) + wrapDimension);
                } else {
                    WidgetRun widgetRun4 = runGroup.firstRun;
                    j = ((long) widgetRun4.start.margin) + widgetRun4.getWrapDimension();
                    i2 = runGroup.firstRun.end.margin;
                }
                j3 = Math.max(j3, j4);
                i4++;
                j4 = 0;
                dependencyGraph = this;
                constraintWidgetContainer2 = constraintWidgetContainer;
            } else {
                long traverseStart = RunGroup.traverseStart(runGroup.firstRun.start, j4);
                long traverseEnd = RunGroup.traverseEnd(runGroup.firstRun.end, j4);
                long j5 = traverseStart - wrapDimension;
                WidgetRun widgetRun5 = runGroup.firstRun;
                int i5 = widgetRun5.end.margin;
                if (j5 >= ((long) (-i5))) {
                    j5 += (long) i5;
                }
                long j6 = (long) widgetRun5.start.margin;
                long j7 = ((-traverseEnd) - wrapDimension) - j6;
                if (j7 >= j6) {
                    j7 -= j6;
                }
                ConstraintWidget constraintWidget = widgetRun5.widget;
                if (i3 == 0) {
                    f = constraintWidget.mHorizontalBiasPercent;
                } else if (i3 == 1) {
                    f = constraintWidget.mVerticalBiasPercent;
                } else {
                    Objects.requireNonNull(constraintWidget);
                    f = -1.0f;
                }
                if (f > 0.0f) {
                    j2 = (long) ((((float) j5) / (1.0f - f)) + (((float) j7) / f));
                } else {
                    j2 = 0;
                }
                float f2 = (float) j2;
                long m = ((long) ((f2 * f) + 0.5f)) + wrapDimension + ((long) MotionController$$ExternalSyntheticOutline0.m7m(1.0f, f, f2, 0.5f));
                WidgetRun widgetRun6 = runGroup.firstRun;
                j = ((long) widgetRun6.start.margin) + m;
                i2 = widgetRun6.end.margin;
            }
            j4 = j - ((long) i2);
            j3 = Math.max(j3, j4);
            i4++;
            j4 = 0;
            dependencyGraph = this;
            constraintWidgetContainer2 = constraintWidgetContainer;
        }
        return (int) j3;
    }

    public final void findGroup(WidgetRun widgetRun, int i, ArrayList<RunGroup> arrayList) {
        Iterator it = widgetRun.start.dependencies.iterator();
        while (it.hasNext()) {
            Dependency dependency = (Dependency) it.next();
            if (dependency instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency, i, 0, arrayList, (RunGroup) null);
            } else if (dependency instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency).start, i, 0, arrayList, (RunGroup) null);
            }
        }
        Iterator it2 = widgetRun.end.dependencies.iterator();
        while (it2.hasNext()) {
            Dependency dependency2 = (Dependency) it2.next();
            if (dependency2 instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency2, i, 1, arrayList, (RunGroup) null);
            } else if (dependency2 instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency2).end, i, 1, arrayList, (RunGroup) null);
            }
        }
        if (i == 1) {
            Iterator it3 = ((VerticalWidgetRun) widgetRun).baseline.dependencies.iterator();
            while (it3.hasNext()) {
                Dependency dependency3 = (Dependency) it3.next();
                if (dependency3 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency3, i, 2, arrayList, (RunGroup) null);
                }
            }
        }
    }

    public final void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        boolean z;
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i;
        measure.verticalDimension = i2;
        ((ConstraintLayout.Measurer) this.mMeasurer).measure(constraintWidget, measure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        BasicMeasure.Measure measure2 = this.mMeasure;
        constraintWidget.hasBaseline = measure2.measuredHasBaseline;
        int i3 = measure2.measuredBaseline;
        constraintWidget.mBaselineDistance = i3;
        if (i3 > 0) {
            z = true;
        } else {
            z = false;
        }
        constraintWidget.hasBaseline = z;
    }

    public final void measureWidgets() {
        boolean z;
        BaselineDimensionDependency baselineDimensionDependency;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        Iterator<ConstraintWidget> it = this.container.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            if (!next.measured) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = next.mListDimensionBehaviors;
                boolean z2 = false;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[1];
                int i = next.mMatchConstraintDefaultWidth;
                int i2 = next.mMatchConstraintDefaultHeight;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour3 == dimensionBehaviour5 || (dimensionBehaviour3 == dimensionBehaviour2 && i == 1)) {
                    z = true;
                } else {
                    z = false;
                }
                if (dimensionBehaviour4 == dimensionBehaviour5 || (dimensionBehaviour4 == dimensionBehaviour2 && i2 == 1)) {
                    z2 = true;
                }
                DimensionDependency dimensionDependency = next.horizontalRun.dimension;
                boolean z3 = dimensionDependency.resolved;
                DimensionDependency dimensionDependency2 = next.verticalRun.dimension;
                boolean z4 = dimensionDependency2.resolved;
                if (z3 && z4) {
                    measure(next, dimensionBehaviour, dimensionDependency.value, dimensionBehaviour, dimensionDependency2.value);
                    next.measured = true;
                } else if (z3 && z2) {
                    measure(next, dimensionBehaviour, dimensionDependency.value, dimensionBehaviour5, dimensionDependency2.value);
                    if (dimensionBehaviour4 == dimensionBehaviour2) {
                        next.verticalRun.dimension.wrapValue = next.getHeight();
                    } else {
                        next.verticalRun.dimension.resolve(next.getHeight());
                        next.measured = true;
                    }
                } else if (z4 && z) {
                    measure(next, dimensionBehaviour5, dimensionDependency.value, dimensionBehaviour, dimensionDependency2.value);
                    if (dimensionBehaviour3 == dimensionBehaviour2) {
                        next.horizontalRun.dimension.wrapValue = next.getWidth();
                    } else {
                        next.horizontalRun.dimension.resolve(next.getWidth());
                        next.measured = true;
                    }
                }
                if (next.measured && (baselineDimensionDependency = next.verticalRun.baselineDimension) != null) {
                    baselineDimensionDependency.resolve(next.mBaselineDistance);
                }
            }
        }
    }

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        new ArrayList();
        this.mMeasurer = null;
        this.mMeasure = new BasicMeasure.Measure();
        this.mGroups = new ArrayList<>();
        this.container = constraintWidgetContainer;
        this.mContainer = constraintWidgetContainer;
    }
}
