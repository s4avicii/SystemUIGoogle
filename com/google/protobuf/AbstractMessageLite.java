package com.google.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLite;

public abstract class AbstractMessageLite<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> implements MessageLite {
    public int memoizedHashCode = 0;

    public static abstract class Builder<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> implements MessageLite.Builder {
        public final GeneratedMessageLite.Builder mergeFrom(MessageLite messageLite) {
            GeneratedMessageLite.Builder builder = (GeneratedMessageLite.Builder) this;
            if (builder.defaultInstance.getClass().isInstance(messageLite)) {
                builder.mergeFrom((GeneratedMessageLite) ((AbstractMessageLite) messageLite));
                return builder;
            }
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
    }
}
