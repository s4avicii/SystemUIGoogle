package com.google.common.collect;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Objects;

abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    private static final long serialVersionUID = 0;
    public final C endpoint;

    public static final class AboveAll extends Cut<Comparable<?>> {
        public static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0;

        public final int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : 1;
        }

        public final boolean isLessThan(Comparable<?> comparable) {
            return false;
        }

        public final String toString() {
            return "+∞";
        }

        private AboveAll() {
            super("");
        }

        public final int compareTo(Object obj) {
            if (((Cut) obj) == this) {
                return 0;
            }
            return 1;
        }

        public final void describeAsLowerBound(StringBuilder sb) {
            throw new AssertionError();
        }

        public final void describeAsUpperBound(StringBuilder sb) {
            sb.append("+∞)");
        }

        public final Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        public final int hashCode() {
            return System.identityHashCode(this);
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public final void describeAsLowerBound(StringBuilder sb) {
            sb.append('(');
            sb.append(this.endpoint);
        }

        public final void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(']');
        }

        public final int hashCode() {
            return ~this.endpoint.hashCode();
        }

        public final boolean isLessThan(C c) {
            C c2 = this.endpoint;
            Range<Comparable> range = Range.ALL;
            if (c2.compareTo(c) < 0) {
                return true;
            }
            return false;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("/");
            m.append(this.endpoint);
            m.append("\\");
            return m.toString();
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public AboveValue(C c) {
            super(c);
            Objects.requireNonNull(c);
        }
    }

    public static final class BelowAll extends Cut<Comparable<?>> {
        public static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0;

        public final int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : -1;
        }

        public final boolean isLessThan(Comparable<?> comparable) {
            return true;
        }

        public final String toString() {
            return "-∞";
        }

        private BelowAll() {
            super("");
        }

        public final int compareTo(Object obj) {
            if (((Cut) obj) == this) {
                return 0;
            }
            return -1;
        }

        public final void describeAsLowerBound(StringBuilder sb) {
            sb.append("(-∞");
        }

        public final void describeAsUpperBound(StringBuilder sb) {
            throw new AssertionError();
        }

        public final Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        public final int hashCode() {
            return System.identityHashCode(this);
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public final void describeAsLowerBound(StringBuilder sb) {
            sb.append('[');
            sb.append(this.endpoint);
        }

        public final void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(')');
        }

        public final int hashCode() {
            return this.endpoint.hashCode();
        }

        public final boolean isLessThan(C c) {
            C c2 = this.endpoint;
            Range<Comparable> range = Range.ALL;
            if (c2.compareTo(c) <= 0) {
                return true;
            }
            return false;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("\\");
            m.append(this.endpoint);
            m.append("/");
            return m.toString();
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public BelowValue(C c) {
            super(c);
            Objects.requireNonNull(c);
        }
    }

    public abstract void describeAsLowerBound(StringBuilder sb);

    public abstract void describeAsUpperBound(StringBuilder sb);

    public abstract int hashCode();

    public abstract boolean isLessThan(C c);

    public int compareTo(Cut<C> cut) {
        if (cut == BelowAll.INSTANCE) {
            return 1;
        }
        if (cut == AboveAll.INSTANCE) {
            return -1;
        }
        C c = this.endpoint;
        C c2 = cut.endpoint;
        Range<Comparable> range = Range.ALL;
        int compareTo = c.compareTo(c2);
        if (compareTo != 0) {
            return compareTo;
        }
        boolean z = this instanceof AboveValue;
        if (z == (cut instanceof AboveValue)) {
            return 0;
        }
        if (z) {
            return 1;
        }
        return -1;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Cut)) {
            return false;
        }
        try {
            if (compareTo((Cut) obj) == 0) {
                return true;
            }
            return false;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public Cut(C c) {
        this.endpoint = c;
    }

    public C endpoint() {
        return this.endpoint;
    }
}
