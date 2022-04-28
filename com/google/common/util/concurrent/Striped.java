package com.google.common.util.concurrent;

public abstract class Striped<L> {

    public static class LargeLazyStriped<L> extends PowerOfTwoStriped<L> {
    }

    public static abstract class PowerOfTwoStriped<L> extends Striped<L> {
    }

    public static class SmallLazyStriped<L> extends PowerOfTwoStriped<L> {
    }

    private Striped() {
    }
}
