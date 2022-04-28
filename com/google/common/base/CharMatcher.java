package com.google.common.base;

public abstract class CharMatcher {

    public static abstract class FastMatcher extends CharMatcher {
    }

    public static final class Whitespace extends NamedFastMatcher {
        static {
            Integer.numberOfLeadingZeros(31);
            new Whitespace();
        }
    }

    public String toString() {
        return super.toString();
    }

    public static abstract class NamedFastMatcher extends FastMatcher {
        public final String description = "CharMatcher.whitespace()";

        public final String toString() {
            return this.description;
        }
    }
}
