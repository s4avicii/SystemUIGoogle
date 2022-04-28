package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.errorprone.annotations.DoNotMock;

@DoNotMock("Use ImmutableMultimap, HashMultimap, or another implementation")
public interface Multimap<K, V> {
    AbstractMapBasedMultimap.AsMap asMap();
}
