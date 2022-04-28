package com.android.systemui.classifier;

import android.view.MotionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class TimeLimitedMotionEventBuffer implements List<MotionEvent> {
    public final LinkedList<MotionEvent> mMotionEvents = new LinkedList<>();

    public class Iter implements ListIterator<MotionEvent> {
        public final ListIterator<MotionEvent> mIterator;

        public final void add(Object obj) {
            MotionEvent motionEvent = (MotionEvent) obj;
            throw new UnsupportedOperationException();
        }

        public final boolean hasNext() {
            return this.mIterator.hasNext();
        }

        public final boolean hasPrevious() {
            return this.mIterator.hasPrevious();
        }

        public final Object next() {
            return this.mIterator.next();
        }

        public final int nextIndex() {
            return this.mIterator.nextIndex();
        }

        public final Object previous() {
            return this.mIterator.previous();
        }

        public final int previousIndex() {
            return this.mIterator.previousIndex();
        }

        public final void remove() {
            this.mIterator.remove();
        }

        public final void set(Object obj) {
            MotionEvent motionEvent = (MotionEvent) obj;
            throw new UnsupportedOperationException();
        }

        public Iter(TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer, int i) {
            this.mIterator = timeLimitedMotionEventBuffer.mMotionEvents.listIterator(i);
        }
    }

    public final void add(int i, Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        throw new UnsupportedOperationException();
    }

    public final boolean addAll(Collection<? extends MotionEvent> collection) {
        boolean addAll = this.mMotionEvents.addAll(collection);
        ejectOldEvents();
        return addAll;
    }

    public final ListIterator<MotionEvent> listIterator() {
        return new Iter(this, 0);
    }

    public final Object remove(int i) {
        return this.mMotionEvents.remove(i);
    }

    public final Object[] toArray() {
        return this.mMotionEvents.toArray();
    }

    public final void clear() {
        this.mMotionEvents.clear();
    }

    public final boolean contains(Object obj) {
        return this.mMotionEvents.contains(obj);
    }

    public final boolean containsAll(Collection<?> collection) {
        return this.mMotionEvents.containsAll(collection);
    }

    public final void ejectOldEvents() {
        if (!this.mMotionEvents.isEmpty()) {
            ListIterator<MotionEvent> listIterator = listIterator();
            long eventTime = this.mMotionEvents.getLast().getEventTime();
            while (true) {
                Iter iter = (Iter) listIterator;
                if (iter.hasNext()) {
                    MotionEvent motionEvent = (MotionEvent) iter.next();
                    if (eventTime - motionEvent.getEventTime() > 1000) {
                        iter.remove();
                        motionEvent.recycle();
                    }
                } else {
                    return;
                }
            }
        }
    }

    public final boolean equals(Object obj) {
        return this.mMotionEvents.equals(obj);
    }

    public final Object get(int i) {
        return this.mMotionEvents.get(i);
    }

    public final int hashCode() {
        return this.mMotionEvents.hashCode();
    }

    public final int indexOf(Object obj) {
        return this.mMotionEvents.indexOf(obj);
    }

    public final boolean isEmpty() {
        return this.mMotionEvents.isEmpty();
    }

    public final Iterator<MotionEvent> iterator() {
        return this.mMotionEvents.iterator();
    }

    public final int lastIndexOf(Object obj) {
        return this.mMotionEvents.lastIndexOf(obj);
    }

    public final ListIterator<MotionEvent> listIterator(int i) {
        return new Iter(this, i);
    }

    public final boolean remove(Object obj) {
        return this.mMotionEvents.remove(obj);
    }

    public final boolean removeAll(Collection<?> collection) {
        return this.mMotionEvents.removeAll(collection);
    }

    public final boolean retainAll(Collection<?> collection) {
        return this.mMotionEvents.retainAll(collection);
    }

    public final Object set(int i, Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        throw new UnsupportedOperationException();
    }

    public final int size() {
        return this.mMotionEvents.size();
    }

    public final List<MotionEvent> subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    public final <T> T[] toArray(T[] tArr) {
        return this.mMotionEvents.toArray(tArr);
    }

    public final boolean add(Object obj) {
        boolean add = this.mMotionEvents.add((MotionEvent) obj);
        ejectOldEvents();
        return add;
    }

    public final boolean addAll(int i, Collection<? extends MotionEvent> collection) {
        throw new UnsupportedOperationException();
    }
}
