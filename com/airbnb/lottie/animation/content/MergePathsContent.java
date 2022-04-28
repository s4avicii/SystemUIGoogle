package com.airbnb.lottie.animation.content;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Path;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TargetApi(19)
public final class MergePathsContent implements PathContent, GreedyContent {
    public final Path firstPath = new Path();
    public final MergePaths mergePaths;
    public final Path path = new Path();
    public final ArrayList pathContents = new ArrayList();
    public final Path remainderPath = new Path();

    public final void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < this.pathContents.size(); i++) {
            ((PathContent) this.pathContents.get(i)).setContents(list, list2);
        }
    }

    public final Path getPath() {
        this.path.reset();
        MergePaths mergePaths2 = this.mergePaths;
        Objects.requireNonNull(mergePaths2);
        if (mergePaths2.hidden) {
            return this.path;
        }
        MergePaths mergePaths3 = this.mergePaths;
        Objects.requireNonNull(mergePaths3);
        int ordinal = mergePaths3.mode.ordinal();
        if (ordinal == 0) {
            for (int i = 0; i < this.pathContents.size(); i++) {
                this.path.addPath(((PathContent) this.pathContents.get(i)).getPath());
            }
        } else if (ordinal == 1) {
            opFirstPathWithRest(Path.Op.UNION);
        } else if (ordinal == 2) {
            opFirstPathWithRest(Path.Op.REVERSE_DIFFERENCE);
        } else if (ordinal == 3) {
            opFirstPathWithRest(Path.Op.INTERSECT);
        } else if (ordinal == 4) {
            opFirstPathWithRest(Path.Op.XOR);
        }
        return this.path;
    }

    @TargetApi(19)
    public final void opFirstPathWithRest(Path.Op op) {
        Matrix matrix;
        Matrix matrix2;
        this.remainderPath.reset();
        this.firstPath.reset();
        for (int size = this.pathContents.size() - 1; size >= 1; size--) {
            PathContent pathContent = (PathContent) this.pathContents.get(size);
            if (pathContent instanceof ContentGroup) {
                ContentGroup contentGroup = (ContentGroup) pathContent;
                ArrayList arrayList = (ArrayList) contentGroup.getPathList();
                for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                    Path path2 = ((PathContent) arrayList.get(size2)).getPath();
                    TransformKeyframeAnimation transformKeyframeAnimation = contentGroup.transformAnimation;
                    if (transformKeyframeAnimation != null) {
                        matrix2 = transformKeyframeAnimation.getMatrix();
                    } else {
                        contentGroup.matrix.reset();
                        matrix2 = contentGroup.matrix;
                    }
                    path2.transform(matrix2);
                    this.remainderPath.addPath(path2);
                }
            } else {
                this.remainderPath.addPath(pathContent.getPath());
            }
        }
        int i = 0;
        PathContent pathContent2 = (PathContent) this.pathContents.get(0);
        if (pathContent2 instanceof ContentGroup) {
            ContentGroup contentGroup2 = (ContentGroup) pathContent2;
            List<PathContent> pathList = contentGroup2.getPathList();
            while (true) {
                ArrayList arrayList2 = (ArrayList) pathList;
                if (i >= arrayList2.size()) {
                    break;
                }
                Path path3 = ((PathContent) arrayList2.get(i)).getPath();
                TransformKeyframeAnimation transformKeyframeAnimation2 = contentGroup2.transformAnimation;
                if (transformKeyframeAnimation2 != null) {
                    matrix = transformKeyframeAnimation2.getMatrix();
                } else {
                    contentGroup2.matrix.reset();
                    matrix = contentGroup2.matrix;
                }
                path3.transform(matrix);
                this.firstPath.addPath(path3);
                i++;
            }
        } else {
            this.firstPath.set(pathContent2.getPath());
        }
        this.path.op(this.firstPath, this.remainderPath, op);
    }

    public MergePathsContent(MergePaths mergePaths2) {
        Objects.requireNonNull(mergePaths2);
        this.mergePaths = mergePaths2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:3:0x000a, LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void absorbContent(java.util.ListIterator<com.airbnb.lottie.animation.content.Content> r3) {
        /*
            r2 = this;
        L_0x0000:
            boolean r0 = r3.hasPrevious()
            if (r0 == 0) goto L_0x000d
            java.lang.Object r0 = r3.previous()
            if (r0 == r2) goto L_0x000d
            goto L_0x0000
        L_0x000d:
            boolean r0 = r3.hasPrevious()
            if (r0 == 0) goto L_0x0028
            java.lang.Object r0 = r3.previous()
            com.airbnb.lottie.animation.content.Content r0 = (com.airbnb.lottie.animation.content.Content) r0
            boolean r1 = r0 instanceof com.airbnb.lottie.animation.content.PathContent
            if (r1 == 0) goto L_0x000d
            java.util.ArrayList r1 = r2.pathContents
            com.airbnb.lottie.animation.content.PathContent r0 = (com.airbnb.lottie.animation.content.PathContent) r0
            r1.add(r0)
            r3.remove()
            goto L_0x000d
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.MergePathsContent.absorbContent(java.util.ListIterator):void");
    }
}
